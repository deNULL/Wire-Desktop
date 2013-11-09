package ru.denull.mtproto;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import net.sf.ehcache.CacheManager;
import ru.denull.mtproto.Auth.AuthCallback;
import ru.denull.mtproto.Auth.AuthState;
import ru.denull.wire.Utils;
import ru.denull.wire.model.*;
import tl.*;
import tl.Config;
import tl.Message;
import tl.auth.Authorization;
import tl.auth.ExportAuthorization;
import tl.auth.ExportedAuthorization;
import tl.auth.ImportAuthorization;
import tl.contacts.TForeignLink;
import tl.contacts.TMyLink;
import tl.help.GetConfig;
import tl.updates.Difference;
import tl.updates.DifferenceEmpty;
import tl.updates.DifferenceSlice;
import tl.updates.GetDifference;
import tl.updates.GetState;
import tl.updates.State;
import tl.updates.TDifference;

public class DataService {
	private static final String TAG = "DataService";
	public static final boolean DEBUG_SERVERS = false;
	public static DataService instance = null;				// still not sure if it's a good idea...
  public Server mainServer;                         // current user's DC
  public int mainServerID;
  // TODO: close hanging connections after a while
  HashMap<String, Server> servers;		              // <address:port> -> <Server>, for downloading files
  
  public Preferences pref;                          // global config (there's also local configs per each server)
  public String defaultServer;
  
  public Config dcConfig;
  
  // TODO: maybe make own subclass of ThreadPoolExecutor
  public ThreadPoolExecutor threadPool;             // for managing all threads
  public ScheduledThreadPoolExecutor scheduledPool; // for timers
  
  // TODO: fix
  //public MainActivity activity;
  
  // All data will be stored here
  public UserSelf						me;
  public SQLiteDatabase			db;
  public DialogManager			dialogManager;
  public ChatManager				chatManager;
  public UserManager				userManager;
  public ContactManager			contactManager;
  public MessageManager			messageManager;
  public FileManager        fileManager;
  public MediaManager       mediaManager;
  public TypingManager      typingManager;
  
  public CacheManager       cacheManager;
  
  public boolean networkDown = false;
  public boolean wifi, mobile;
  
  public int updates_pts, updates_date, updates_seq;
  
  public boolean storePhone = true;
  public boolean storeKeys = true;
  public boolean storeCache = true;
  
  
  public DataService() {
    servers = new HashMap<String, Server>(10);
    threadPool = new ThreadPoolExecutor(3, 10, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(200));
    scheduledPool = new ScheduledThreadPoolExecutor(1);
    
    final DataService self = this;
    /*threadPool.submit(new Runnable() { // initialize managers
			public void run() {*/
				db = (new CacheDbHelper()).getWritableDatabase();
				cacheManager = new CacheManager(getClass().getClassLoader().getResourceAsStream("/ehcache.xml"));
				dialogManager = new DialogManager(self, db);
				chatManager = new ChatManager(self, db);
				userManager = new UserManager(self, db);
				messageManager = new MessageManager(self, db);
				contactManager = new ContactManager(self, db);
				fileManager = new FileManager(self);
				mediaManager = new MediaManager(self, db);
				typingManager = new TypingManager(self);
				
				// TODO: allow to setup listeners
				/*ui(new Runnable() {
					public void run() {
						if (chatlist != null) {
							chatlist.setListAdapter(new ChatListAdapter(activity));
						}
					}
				}, true);*/
				
				// warm up cache
				dialogManager.get(0);
				for (Dialog dialog : dialogManager.loaded) {
					if (dialog.peer instanceof PeerChat) {
						chatManager.get(((PeerChat) dialog.peer).chat_id); 
					} else {
						userManager.get(((PeerUser) dialog.peer).user_id); 
					}
					messageManager.get(dialog.top_message);
				}
				contactManager.load();
				for (int uid : contactManager.loaded.values()) {
					userManager.get(uid);
				}
				contactManager.loadLocal();
				
				Notifier.enter(Notifier.CACHING_DB_STARTED);
			//}
		//});
    
    //pref = getSharedPreferences(getString(R.string.preferences_auth) + (DEBUG_SERVERS ? "-test" : "-production"), Context.MODE_PRIVATE);
    pref = Preferences.userRoot().node("wire/config" + (DEBUG_SERVERS ? "-test" : "-production"));
    setup();
  }
  
  public static DataService getInstance() {
    if (instance == null) {
      instance = new DataService();
    }
    return instance;
  }
  
  public void setup() {
    me = (pref.get("user", "").length() == 0) ? null : (UserSelf) TL.read(pref.get("user", ""));
    defaultServer = pref.get("server", DEBUG_SERVERS ? "173.240.5.253:443" : "173.240.5.1:443");// "95.142.192.65:80"
    updates_pts = pref.getInt("updates_pts", -1);
    updates_date = pref.getInt("updates_date", -1);
    updates_seq = pref.getInt("updates_seq", -1);
  }
  
  public void dropDatabases() {
    mainServer.auth.loggedOut();
    CacheDbHelper.reset(db);
    try {
      pref.clear();
    } catch (BackingStoreException e) {
      e.printStackTrace();
    }
    setup();
  }
  
  public void onDestroy() {
    disconnectAll(null);
		db.close();
		Notifier.leave(Notifier.CACHING_DB_STARTED);
		instance = null;
  }
  
  public void networkChanged() {
    /*threadPool.submit(new Runnable() {
      public void run() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
        mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();

        if (networkDown && (wifi || mobile)) {
          // reconnect
          for (Server server : servers.values()) {
            server.resolveNetworkProblem(true);
          }
        }
        networkDown = !wifi && !mobile;
      }
    });*/
  }
  
  public void logged(UserSelf user) {
    me = user;
    userManager.store(user);
    if (storeKeys) {
      pref.put("user", user.writeToBase64());
      pref.put("server", mainServer.address + ":" + mainServer.port);
    }
    mainServer.auth.logged();
  }
  
  public boolean checkingState = false; // prevent multiple queries
  public void checkUpdates() {
    if (mainServer == null || checkingState) return; // TODO: check this
    
    checkingState = true;
    if (updates_pts < 0 || updates_date < 0) { // invalid (not initialized) state
      mainServer.call(new GetState(), new Server.RPCCallback<tl.updates.State>() {
        public void done(State result) {
          updates_pts = Math.max(updates_pts, result.pts);
          updates_date = Math.max(updates_date, result.date);
          updates_seq = Math.max(updates_seq, result.seq);
          pref.putInt("updates_pts", updates_pts);
          pref.putInt("updates_date", updates_date);
          pref.putInt("updates_seq", updates_seq);
          checkingState = false;
        }
        public void error(int code, String message) {
          Log.e(TAG, "Error while checking for updates");
          checkingState = false;
        }
      });
    } else {
      mainServer.call(new GetDifference(updates_pts, updates_date, 0), new Server.RPCCallback<TDifference>() {
        public void done(TDifference result) {
          checkingState = false;
          if (result instanceof DifferenceEmpty) {
            updates_date = Math.max(updates_date, ((DifferenceEmpty) result).date);
            updates_seq = Math.max(updates_seq, ((DifferenceEmpty) result).seq);
          } else
          if (result instanceof DifferenceSlice) {
            DifferenceSlice diff = (DifferenceSlice) result;
            
            chatManager.store(diff.chats);
            userManager.store(diff.users);
            
            updates_pts = Math.max(updates_pts, ((State) diff.intermediate_state).pts);
            updates_date = Math.max(updates_date, ((State) diff.intermediate_state).date);
            updates_seq = Math.max(updates_seq, ((State) diff.intermediate_state).seq);
            
            /*for (TMessage message : diff.new_messages) {
              processNewMessage(message, false);
            }
            
            for (TUpdate update : diff.other_updates) {
              processUpdate(update, false);
            }*/
            
            checkUpdates(); // request next updates
          } else
          if (result instanceof Difference) {
            Difference diff = (Difference) result;
            
            chatManager.store(diff.chats);
            userManager.store(diff.users);
            
            updates_pts = Math.max(updates_pts, ((State) diff.state).pts);
            updates_date = Math.max(updates_date, ((State) diff.state).date);
            updates_seq = Math.max(updates_seq, ((State) diff.state).seq);
            
            /*for (TMessage message : diff.new_messages) {
              processNewMessage(message, false);
            }
            
            for (TUpdate update : diff.other_updates) {
              processUpdate(update, false);
            }*/
          }
        }

        public void error(int code, String message) {
          Log.e(TAG, "Error while checking for updates");
          checkingState = false;
        }
      });
    }
  }
  
  public void processUpdates(TUpdates updates) {
    if (updates_pts < 0 || updates_date < 0 || updates_seq < 0) { // not yet ready to process updates, need to get state first
      checkUpdates();
      return; 
    }
    
    if (updates instanceof UpdatesTooLong) {
      checkUpdates();
    } else
    if (updates instanceof UpdateShort) {
      updates_date = ((UpdateShort) updates).date;
      processUpdate(((UpdateShort) updates).update, true);
    } else
    if (updates instanceof UpdateShortMessage) {
      UpdateShortMessage upd = (UpdateShortMessage) updates; 
      if (upd.seq > updates_seq + 1) {
        checkUpdates();
        return;
      }
      
      updates_pts = Math.max(updates_pts, upd.pts);
      updates_date = Math.max(updates_date, upd.date);
      updates_seq = Math.max(updates_seq, upd.seq);
      processNewMessage(new Message(upd.id, upd.from_id, new PeerUser(me == null ? 0 : me.id), false, true, upd.date, upd.message, new MessageMediaEmpty()), true);
    } else
    if (updates instanceof UpdateShortChatMessage) {
      UpdateShortChatMessage upd = (UpdateShortChatMessage) updates;
      if (upd.seq > updates_seq + 1) {
        checkUpdates();
        return;
      }
      
      updates_pts = Math.max(updates_pts, upd.pts);
      updates_date = Math.max(updates_date, upd.date);
      updates_seq = Math.max(updates_seq, upd.seq);
      processNewMessage(new Message(upd.id, upd.from_id, new PeerChat(upd.chat_id), false, true, upd.date, upd.message, new MessageMediaEmpty()), true);
    } else
    if (updates instanceof Updates) {
      Updates upd = (Updates) updates;
      if (upd.seq > updates_seq + 1) {
        checkUpdates();
        return;
      }
      
      updates_date = Math.max(updates_date, upd.date);
      updates_seq = Math.max(updates_seq, upd.seq);
      
      chatManager.store(upd.chats);
      userManager.store(upd.users);
      
      for (TUpdate update : upd.updates) {
        processUpdate(update, true);
      }
    } else
    if (updates instanceof UpdatesCombined) {
      UpdatesCombined upd = (UpdatesCombined) updates;
      if (upd.seq_start > updates_seq + 1) {
        checkUpdates();
        return;
      }
      
      updates_date = Math.max(updates_date, upd.date);
      updates_seq = Math.max(updates_seq, upd.seq);
      
      chatManager.store(upd.chats);
      userManager.store(upd.users);
      
      for (TUpdate update : upd.updates) {
        processUpdate(update, true);
      }
    }
  }
  
  public OnUpdateListener updateListener = null;
  public interface OnUpdateListener {
    public void onNewMessage(TMessage message, boolean fresh);
    public void onMessageID(int id, long random_id, boolean fresh);
    public void onReadMessages(int[] messages, boolean fresh);
    public void onDeleteMessages(int[] messages, boolean fresh);
    public void onRestoreMessages(int[] messages, boolean fresh);
    public void onUserTyping(int user_id, boolean fresh);
    public void onChatUserTyping(int chat_id, int user_id, boolean fresh);
    public void onChatParticipants(TChatParticipants participants, boolean fresh);
    public void onUserStatus(int user_id, TUserStatus status, boolean fresh);
    public void onUserName(int user_id, String first_name, String last_name, boolean fresh);
    public void onUserPhoto(int user_id, TUserProfilePhoto photo, boolean fresh);
    public void onContactRegistered(int user_id, int date, boolean fresh);
    public void onContactLink(int user_id, TMyLink my_link, TForeignLink foreign_link, boolean fresh);
    public void onActivation(int user_id, boolean fresh);
    public void onNewAuthorization(long auth_key_id, int date, String device, String location, boolean fresh);
  }
  public void processUpdate(TUpdate update, boolean fresh) {
    //Log.i(TAG, "New " + (fresh ? "(fresh) " : "") + "update: " + update);
    if (update instanceof UpdateNewMessage) {
      updates_pts = Math.max(updates_pts, ((UpdateNewMessage) update).pts);
      processNewMessage(((UpdateNewMessage) update).message, fresh);
    } else if (update instanceof UpdateMessageID) {

      if (updateListener != null)
        updateListener.onMessageID(((UpdateMessageID) update).id, ((UpdateMessageID) update).random_id, fresh);
    } else if (update instanceof UpdateReadMessages) {
      updates_pts = Math.max(updates_pts, ((UpdateReadMessages) update).pts);
      int[] messages = ((UpdateReadMessages) update).messages;
      for (int message_id : messages) {
        TMessage message = messageManager.get(message_id);
        if (message.unread) {
          message.unread = false;
          messageManager.store(message);
          
          // TODO: update dialog
        }
      }
      
      if (updateListener != null)
        updateListener.onReadMessages(messages, fresh);
    } else if (update instanceof UpdateDeleteMessages) {
      updates_pts = Math.max(updates_pts, ((UpdateDeleteMessages) update).pts);

      if (updateListener != null)
        updateListener.onDeleteMessages(((UpdateDeleteMessages) update).messages, fresh);
    } else if (update instanceof UpdateRestoreMessages) {
      updates_pts = Math.max(updates_pts, ((UpdateDeleteMessages) update).pts);

      if (updateListener != null)
        updateListener.onRestoreMessages(((UpdateRestoreMessages) update).messages, fresh);
    } else if (update instanceof UpdateUserTyping) {
      typingManager.userTyping(((UpdateUserTyping) update).user_id);
      if (updateListener != null)
        updateListener.onUserTyping(((UpdateUserTyping) update).user_id, fresh);
    } else if (update instanceof UpdateChatUserTyping) {
      typingManager.userTyping(-((UpdateChatUserTyping) update).chat_id, ((UpdateChatUserTyping) update).user_id);
      if (updateListener != null)
        updateListener.onChatUserTyping(((UpdateChatUserTyping) update).chat_id, ((UpdateChatUserTyping) update).user_id, fresh);
    } else if (update instanceof UpdateChatParticipants) {

      if (updateListener != null)
        updateListener.onChatParticipants(((UpdateChatParticipants) update).participants, fresh);
    } else if (update instanceof UpdateUserStatus) {
      int user_id = ((UpdateUserStatus) update).user_id;
      TUserStatus status = ((UpdateUserStatus) update).status;
      TUser user = userManager.get(user_id);
      if (!(user instanceof UserEmpty)) {
        user.status = status;
        userManager.store(user);
      }
      
      if (updateListener != null)
        updateListener.onUserStatus(user_id, status, fresh);
    } else if (update instanceof UpdateUserName) {
      int user_id = ((UpdateUserName) update).user_id;
      String first_name = ((UpdateUserName) update).first_name;
      String last_name = ((UpdateUserName) update).last_name;
      TUser user = userManager.get(user_id);
      if (!(user instanceof UserEmpty)) {
        user.first_name = first_name;
        user.last_name = last_name;
        userManager.store(user);
      }

      if (updateListener != null)
        updateListener.onUserName(user_id, first_name, last_name, fresh);
    } else if (update instanceof UpdateUserPhoto) {
      int user_id = ((UpdateUserPhoto) update).user_id;
      TUserProfilePhoto photo = ((UpdateUserPhoto) update).photo;
      TUser user = userManager.get(user_id);
      if (!(user instanceof UserEmpty)) {
        user.photo = photo;
        userManager.store(user);
      }

      if (updateListener != null)
        updateListener.onUserPhoto(user_id, photo, fresh);
    } else if (update instanceof UpdateContactRegistered) {

      if (updateListener != null)
        updateListener.onContactRegistered(((UpdateContactRegistered) update).user_id, ((UpdateContactRegistered) update).date, fresh);
    } else if (update instanceof UpdateContactLink) {

      if (updateListener != null)
        updateListener.onContactLink(((UpdateContactLink) update).user_id, ((UpdateContactLink) update).my_link, ((UpdateContactLink) update).foreign_link, fresh);
    } else if (update instanceof UpdateActivation) {

      if (updateListener != null)
        updateListener.onActivation(((UpdateActivation) update).user_id, fresh);
    } else if (update instanceof UpdateNewAuthorization) {

      if (updateListener != null)
        updateListener.onNewAuthorization(((UpdateNewAuthorization) update).auth_key_id, ((UpdateNewAuthorization) update).date, ((UpdateNewAuthorization) update).device, ((UpdateNewAuthorization) update).location, fresh);
    }
  }
  
  public void processNewMessage(TMessage message, boolean fresh) {
    boolean exists = !(messageManager.get(message.id) instanceof MessageEmpty);
    messageManager.store(message);
    
    if (exists) {
      return;
    }
    
    dialogManager.addMessage(message);
    if (message.to_id instanceof PeerChat) {
      typingManager.userTyping(-((PeerChat) message.to_id).chat_id, message.from_id, false);
    } else if (message.to_id instanceof PeerUser) {
      typingManager.userTyping(message.from_id, false);
    }
    if (updateListener != null) {
      updateListener.onNewMessage(message, fresh);
    } else { // app paused, show notification
      if (fresh) {
        //
      }
    }
  }
  
  // Connect to given socket
  public class ConnectTask implements Runnable {
    String address;
    int port;
    boolean main;
    boolean forceReconnect;
    ConnectTaskCallback callback;
    
    public ConnectTask(String address, int port, boolean main, boolean forceReconnect, ConnectTaskCallback callback) {
      super();
      this.address = address;
      this.port = port;
      this.main = main;
      this.forceReconnect = forceReconnect;
      this.callback = callback;
    }

    public void run() {
      String serverId = address + ":" + port;
      Server server = null;
      
      // try to get from socket pool 
      if (servers.containsKey(serverId)) {
        server = servers.get(serverId);
        
        if (forceReconnect) {
          try {
						server.reconnect();
					} catch (IOException e) {
						e.printStackTrace();
						if (callback != null) {
	            callback.error(500, "Unable to reconnect");
	          }
					}
        }
      }
      
      // make new connection
      if (server == null) {
        try {
          server = new Server(DataService.this, address, port);
        } catch (IOException e) {
          e.printStackTrace();
          if (callback != null) {
            callback.error(500, "Unable to connect");
          }
          return;
        }
        
        servers.put(serverId, server);
      }
      
      // set as our main socket
      if (main) {
      	if (mainServer != null) {
      		mainServer.disconnect();
      	}
        
        mainServer = server;
      }
      
      if (callback != null) {
        callback.done(server);
      }
    }
  }
  public interface ConnectTaskCallback {
    public void done(Server server);
    public void error(int code, String message);
  }
  public void connect(String address, int port, boolean main, boolean forceReconnect, ConnectTaskCallback callback) {
    if (!main && !forceReconnect && servers.containsKey(address + ":" + port)) {
      callback.done(servers.get(address + ":" + port));
      return;
    }
    
    threadPool.submit(new ConnectTask(address, port, main, forceReconnect, callback));
  }
  public void connect(boolean forceReconnect, ConnectTaskCallback callback) {
    String[] server = defaultServer.split(":");
    connect(server[0], Integer.parseInt(server[1]), true, forceReconnect, callback);
  }
  public void connect(final int dc_id, final boolean main, final boolean forceReconnect, final ConnectTaskCallback callback) {
    if (dcConfig != null) {
      for (TDcOption opt : dcConfig.dc_options) {
        DcOption option = (DcOption) opt;
        if (mainServer != null && option.ip_address.equals(mainServer.address) && option.port == mainServer.port) {
          mainServerID = option.id;
        }
      }
      for (TDcOption opt : dcConfig.dc_options) {
        final DcOption option = (DcOption) opt;
        if (option.id == dc_id) {
          connect(option.ip_address, option.port, main, forceReconnect, callback);
          return;
        }
      }
    } else
    if (mainServer != null) {
      mainServer.call(new GetConfig(), new Server.RPCCallback<Config>() {
        public void done(Config result) {
          dcConfig = result;
          connect(dc_id, main, forceReconnect, callback);
        }
        public void error(int code, String message) {
          callback.error(code, message);
        }
      });
    }
  }
  public void connectAndPrepare(boolean forceReconnect, final boolean forceRegenerateKey, final AuthCallback callback) {
    connect(forceReconnect, new ConnectTaskCallback() {      
      public void done(Server server) {
        server.auth.generateKey(forceRegenerateKey, callback);
      }
      
      public void error(int code, String message) {
        callback.error();
      }
    });
  }
  public void connectAndPrepare(final int dc_id, final boolean main, final boolean forceReconnect, final boolean forceRegenerateKey, final AuthCallback callback) {
    if (mainServerID == 0 || dc_id == mainServerID) {
      connect(dc_id, main, forceReconnect, new ConnectTaskCallback() {      
        public void done(Server server) {
          server.auth.generateKey(forceRegenerateKey, callback);
        }
        public void error(int code, String message) {
          callback.error();
        }
      });
    } else {
      mainServer.call(new ExportAuthorization(dc_id), new Server.RPCCallback<ExportedAuthorization>() {
        public void done(final ExportedAuthorization result) {
          connect(dc_id, main, forceReconnect, new ConnectTaskCallback() {      
            public void done(Server server) {
              if (server.auth.authorized) {
                server.auth.generateKey(forceRegenerateKey, callback);
              } else {
                server.auth.generateKey(forceRegenerateKey, new AuthCallback() {
                  public void done(final Server server, final byte[] auth_key) {
                    server.call(new ImportAuthorization(result.id, result.bytes), new Server.RPCCallback<Authorization>() {
                      public void done(Authorization result) {
                        server.auth.logged();
                        callback.done(server, auth_key);
                      }
                      public void error(int code, String message) {
                        callback.error();
                      }
                    });
                  }
                  public void error() {
                    callback.error();
                  }
                });
              }
            }
            
            public void error(int code, String message) {
              callback.error();
            }
          });
        }
        public void error(int code, String message) {
          callback.error();
        }
      });
    }
  }
  
  
  
  // Read from given socket
  public class ReadTask implements Runnable {
    Server server;
    ReadTaskCallback callback;
    
    public ReadTask(Server server, ReadTaskCallback callback) {
      super();
      this.server = server;
      this.callback = callback;
    }

    public void run() {
      if (server == null || !server.transport.isConnected()) {
        if (callback != null) {
          callback.error(500, "Socket is not connected");
        }
        return;
      }
      
      while (!Thread.interrupted()) {
        try {
          ByteBuffer buffer = server.transport.receive();
          //Log.d(TAG, "<< " + server.address + " << " + buffer.limit() + " bytes");
          
          if (callback != null) {
            callback.incoming(buffer);
          }
        } catch (ClosedByInterruptException e) {
          break;
        } catch (ClosedChannelException e) {
          //break;
        } catch (SocketException e) {
        	Log.w(TAG, "Socket exception: " + e.getMessage());
        	while (!Thread.interrupted() && !server.resolveNetworkProblem(false)) {
        		// restoring connection
        	}
        } catch (IOException e) { // Connection reset by peer
          Log.w(TAG, "Socket exception: " + e.getMessage());
          while (!Thread.interrupted() && !server.resolveNetworkProblem(false)) {
            // restoring connection
          }
        } catch (Exception e) {
          e.printStackTrace();
          if (callback != null) {
            callback.error(500, e.getMessage());
          }
        }
      }
      
      if (callback != null) {
        callback.done(server);
      }
    }
  }
  public interface ReadTaskCallback {
    public void done(Server server);
    public void incoming(ByteBuffer packet);
    public void error(int code, String message);    
  }
  public void read(String address, int port, ReadTaskCallback callback) {
    String serverId = address + ":" + port;
    if (!servers.containsKey(serverId)) {
      return;
    }
    
    read(servers.get(serverId), callback);
  }
  public void read(Server server, ReadTaskCallback callback) {
    threadPool.submit(new ReadTask(server, callback));
  }
  
  
  
  // Write to given socket
  
  public class WriteTask implements Runnable {
    Server server;
    ByteBuffer buffer;
    WriteTaskCallback callback;
    
    public WriteTask(Server server, ByteBuffer buffer, WriteTaskCallback callback) {
      super();
      this.server = server;
      this.buffer = buffer;
      this.callback = callback;
    }

    public void run() {
      if (server == null || !server.transport.isConnected()) {
        if (callback != null) {
          callback.error(500, "Socket is not connected");
        }
        return;
      }
      
      try {
        server.transport.send(buffer);
      } catch (SocketException e) {
      	Log.w(TAG, "Socket exception: " + e.getMessage());
      	if (server.resolveNetworkProblem(true)) {
      		try {
						server.transport.send(buffer);
					} catch (Exception e1) {
						callback.error(500, e.getMessage());
					}
      	} else {
      		callback.error(500, e.getMessage());
      	}
      } catch (Exception e) {
        callback.error(500, e.getMessage());
      }
      
      if (callback != null) {
        callback.done(server);
      }
    }
  }
  public interface WriteTaskCallback {
    public void done(Server server);
    public void error(int code, String message);    
  }
  public void write(String address, int port, ByteBuffer buffer, WriteTaskCallback callback) {
    String serverId = address + ":" + port;
    if (!servers.containsKey(serverId)) {
      return;
    }
    
    write(servers.get(serverId), buffer, callback);
  }
  public void write(Server server, ByteBuffer buffer, WriteTaskCallback callback) {
    //Log.d(TAG, ">> " + server.address + " >> " + buffer.limit() + " bytes");
    threadPool.submit(new WriteTask(server, buffer, callback));
  }
  
  
  
  
  // Disconnect from given socket
  public class DisconnectTask implements Runnable {
    Server[] server;
    DisconnectTaskCallback callback;
    
    public DisconnectTask(Server[] server, DisconnectTaskCallback callback) {
      super();
      this.server = server;
      this.callback = callback;
    }

    public void run() {
      for (Server s : server) {
        if (s != null) {
          s.disconnect();
        }
      }
      
      if (callback != null) {
        callback.done();
      }
    }
  }
  public interface DisconnectTaskCallback {
    public void done();
    public void error(int code, String message);
  }
  public void disconnect(String address, int port, DisconnectTaskCallback callback) {
    String serverId = address + ":" + port;
    if (!servers.containsKey(serverId)) {
      return;
    }
    
    disconnect(servers.get(serverId), callback);
  }
  public void disconnect(Server server, DisconnectTaskCallback callback) {
    disconnect(new Server[]{ server }, callback); 
  }
  public void disconnect(Server[] server, DisconnectTaskCallback callback) {
    threadPool.submit(new DisconnectTask(server, callback));
  }
  public void disconnectAll(DisconnectTaskCallback callback) {
    disconnect((Server[]) servers.values().toArray(new Server[0]), callback);
  }

  public File getCacheDir() {
    // TODO Auto-generated method stub
    return new File(System.getProperty("java.io.tmpdir") + "/wire");
  }

  // TODO: implement possibility not to store data
  public void setStoragePolicy(boolean storePhone, boolean storeKeys, boolean storeCache) {
    this.storePhone = storePhone;
    this.storeKeys = storeKeys;
    this.storeCache = storeCache;
    
    // If we already stored something, we need to clear it
    if (!storePhone) {
      pref.remove("phone");
    }
    if (!storeKeys) {
      try {
        pref.clear();
      } catch (BackingStoreException e) {
        e.printStackTrace();
      }
    }
    if (!storeCache) {
      cacheManager.clearAll();
    }
  }

  public void setPhone(String string) {
    // TODO Auto-generated method stub
    
  }
}
