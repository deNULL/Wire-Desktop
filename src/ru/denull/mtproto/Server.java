package ru.denull.mtproto;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.denull.mtproto.Auth.AuthCallback;
import ru.denull.mtproto.Auth.AuthState;
import ru.denull.mtproto.DataService.ConnectTaskCallback;
import ru.denull.mtproto.DataService.ReadTaskCallback;
import ru.denull.mtproto.DataService.WriteTaskCallback;
import ru.denull.mtproto.Server.RPCCallback;
import ru.denull.wire.model.Config;
import tl.*;
import tl.help.GetConfig;
import static ru.denull.mtproto.CryptoUtils.*;

public class Server implements ReadTaskCallback {
	private static final String TAG = "Server";
	
	public Preferences pref;
	
	public DataService service;
	public Transport transport;
	public String address;
	public int port;
	
	public Auth auth;
	private long session_id = 1;
	public long old_session_id = 0;
	
	private int client_seqno = 1;
	
	private long minimal_message_id = 0; // used to prevent duplicate msg_ids when sending lots of requests simultaneously
	
	public int time_diff;
	
	public boolean timer_running = false;
	public boolean resolving_problem = false;
	public long last_problem_time = 0;
	public int resolve_try = 0;
	
	
	public Server(DataService service, String address, int port) throws IOException {
		this.service = service;
		this.address = address;
		this.port = port;
		reconnect();
		
		Random rand = new Random();
		session_id = rand.nextLong();
		//Log.i(TAG, "Using session #" + session_id);
		System.out.println("New connection to server " + address + ":" + port + " (session " + session_id + ")");
		
		//pref = service.getSharedPreferences(service.getString(R.string.preferences_auth) + "-" + address + ":" + port, Context.MODE_PRIVATE);
		pref = Preferences.userRoot().node("wire/server/" + address + ":" + port);
		old_session_id = pref.getLong("session_id", 0);
		pref.putLong("session_id", session_id);
		try {
      pref.sync();
    } catch (BackingStoreException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
		auth = new Auth(this, pref);
		
		// Start reading
		service.read(this, this);
	}
	
	public void disconnect() {
		if (transport != null) {
			transport.close();
		}
	}
	
	public void reconnect() throws IOException {
		disconnect();
		transport = new TCPTransport(address, port);
	}
	
	// true if problem fixed
	// rightNow = true: instantly fails if there was another try not too long ago
	// rightNow = false: if already tried to reconnect, sleeps for some time and tries then
	public boolean resolveNetworkProblem(boolean rightNow) {
		resolving_problem = true;
		
		long time = System.currentTimeMillis();
		if (time - last_problem_time > 120000) {
			resolve_try = 0;
		}
		last_problem_time = time;
		resolve_try++;
		long time_to_sleep = 0;
		/*if (service.networkDown) {
			time_to_sleep = 300000;
		} else
		if (resolve_try > 20) {
			time_to_sleep = 60000;
		} else
		if (resolve_try > 10) {
			time_to_sleep = 30000;
		} else
		if (resolve_try > 7) {
			time_to_sleep = 20000;
		} else
		if (resolve_try > 5) {
			time_to_sleep = 10000;
		} else
		if (resolve_try > 3) {
			time_to_sleep = 4000;
		} else*/
		if (resolve_try > 2) {
			time_to_sleep = 2000;
		} else
		if (resolve_try > 1) {
			time_to_sleep = 1000;
		}
		
		if (time_to_sleep > 0) {
			if (rightNow) {
				//Log.i(TAG, "Unable to fix network problem right now...");
				return false;
			}
			
			try {
				//Log.i(TAG, "Will try to fix problem after " + time_to_sleep + "ms");
				Thread.sleep(time_to_sleep);
			} catch (InterruptedException e) {
				//Log.i(TAG, "Thread was interrupted...");
				return false;
			}
		}
		
		try {
			//Log.i(TAG, "Resolving problem...");
			if (resolving_problem) { // we may have resolved it from another thread while this was sleeping
				reconnect();
				//Log.i(TAG, "Successfully restored connection");
			} else {
				//Log.i(TAG, "Ah. Already resolved it, nice.");
			}
			resolve_try = 0;
			return true;
		} catch (IOException e) {
			//Log.i(TAG, "Unable to fix network problem...");
			return false;
		}
	}
	
	private ArrayList<RPCCall> queuedCalls = new ArrayList<RPCCall>();		// messages that will be sent after next sendQueued call
	private ArrayList<RPCCall> sentCalls = new ArrayList<RPCCall>();			// messages waiting for result
  
	public class RPCCall {
    TLObject payload;
    
	  boolean encrypted;
	  boolean significant;
	  
	  RPCCallback callback;
	  
	  // filled when sent
	  long sent_time = 0;
	  long message_id = 0;
	  
    public RPCCall(TLObject payload, boolean encrypted, boolean significant, RPCCallback callback) {
      super();
      this.payload = payload;
      this.encrypted = encrypted;
      this.significant = significant;
      this.callback = callback;
    }
	}
	public interface RPCCallback<T extends TLObject> {
		public void done(T result);
		public void error(int code, String message);
	}
	
	public void sendQueued() throws Exception {
		if (queuedCalls.size() == 0) {
			return;
		}

		long message_id = (long)((System.currentTimeMillis() + time_diff * 1000) * 4294967.296) & ~3L;
		if (queuedCalls.size() == 1) {
			// send single call, no need to pack it into container
		  final RPCCall call = queuedCalls.get(0);
		  if (call.encrypted) {
		  	if (auth.state == AuthState.FAILED) {
		  		call.callback.error(501, "auth_key missing");
		  		queuedCalls = new ArrayList<RPCCall>();
		  		return;
		  	} else
		  	if (auth.state != AuthState.COMPLETE){
		  		return;
		  	}
		  }
		  
			final Message message = call.encrypted ?
		    new EncryptedMessage(
		    		Math.max(minimal_message_id, message_id),
		        call.payload,
		        session_id,
		        auth.auth_key_id,
		        auth.server_salt,
		        client_seqno,
		        call.significant) :
		          
		    new Message(
		    		Math.max(minimal_message_id, message_id),
		        call.payload);

		  minimal_message_id = Math.max(minimal_message_id, message_id) + 4;
	    if (call.significant) {
	      client_seqno++;
	    }
	    
	    service.write(this, message.encrypt(auth.auth_key), new WriteTaskCallback() {
        public void error(int code, String message) {
          //Log.e(TAG, "Unable to send call " + call.payload + ": " + message);
          if (call.callback != null) {
            sentCalls.remove(call);
          }
          
          queuedCalls.add(call);
        }
        
        public void done(Server server) {
          // ok
        }
      });
	    //Log.i(TAG, "=> " + Long.toHexString(message.message_id) + ": " + message.payload);
	    if (call.callback != null) {
        call.sent_time = System.currentTimeMillis();
        call.message_id = message.message_id;
        sentCalls.add(call);
      }
			queuedCalls = new ArrayList<RPCCall>();
		} else {
			// pack all queuedCalls into one container and send them
			final ArrayList<TransportMessage> messages = new ArrayList<TransportMessage>(queuedCalls.size());
			ArrayList<RPCCall> unprocessedCalls = new ArrayList<RPCCall>();
      final ArrayList<RPCCall> processedCalls = new ArrayList<RPCCall>();
			for (int i = 0; i < queuedCalls.size(); i++) {
			  RPCCall call = queuedCalls.get(i);
			  if (call.encrypted) {
			  	if (auth.state == AuthState.FAILED) {
			  		call.callback.error(501, "auth_key missing");
			  		continue;
			  	} else
			  	if (auth.state != AuthState.COMPLETE){
			  		unprocessedCalls.add(call);
			  		continue;
			  	}
			  }
			  processedCalls.add(call);
			  
			  TransportMessage message = new TransportMessage(
          Math.max(minimal_message_id, message_id),
          (client_seqno << 1) + (call.significant ? 1 : 0),
          call.payload.length(true),
          call.payload
        );
				messages.add(message);
				//System.out.println("==> " + Long.toHexString(messages[i].msg_id) + ": " + call.payload);
				
				minimal_message_id = Math.max(minimal_message_id, message_id) + 4;
				if (call.significant) {
				  client_seqno++;
				}
				
				if (call.callback != null) {
		    	call.sent_time = System.currentTimeMillis();
		    	call.message_id = message.msg_id;
		      sentCalls.add(call);
	      }
			}
			
			if (unprocessedCalls.size() == 0) {
  			Message message = 
  	      new EncryptedMessage(
  	      		Math.max(minimal_message_id, message_id),
  	          new MsgContainer(messages.toArray(new TransportMessage[messages.size()])),
  	          session_id,
  	          auth.auth_key_id,
  	          auth.server_salt,
  	          client_seqno,
  	          false);
  
  			minimal_message_id = Math.max(minimal_message_id, message_id) + 4;
  			
  	    service.write(this, message.encrypt(auth.auth_key), new WriteTaskCallback() {
          public void error(int code, String message) {
            //Log.e(TAG, "Unable to send calls " + new MsgContainer(messages.toArray(new TransportMessage[messages.size()])) + ": " + message);
            for (RPCCall call : processedCalls) {
              if (call.callback != null) {
                sentCalls.remove(call);
              }
              
              queuedCalls.add(call);
            }
          }
          
          public void done(Server server) {
            // ok
          }
        });
        //Log.i(TAG, "=> " + Long.toString(message.message_id, 16) + ": " + message.payload);
			} else {
			  //Log.w(TAG, "There are unsent calls (" + unprocessedCalls.toString() + ") due to incomplete auth process");
			  
			  for (TransportMessage transport : messages) {
			    Message message = 
		          new Message(
		              Math.max(minimal_message_id, message_id),
		              transport);
		  
	        minimal_message_id = Math.max(minimal_message_id, message_id) + 4;
	        //Log.i(TAG, "=> " + Long.toString(message.message_id, 16) + ": " + message.payload);
	        service.write(this, message.encrypt(auth.auth_key), new WriteTaskCallback() {
	          public void error(int code, String message) {
	            //Log.e(TAG, "Unable to send call");
	          }
	          
	          public void done(Server server) {
	            // ok
	          }
	        });
			  }
			}

			queuedCalls = unprocessedCalls;
		}
		
		if (queuedCalls.size() > 0 && !timer_running) {
			service.scheduledPool.schedule(new Callable<Boolean>() {
				public Boolean call() throws Exception {
					timer_running = false;
					sendQueued();
					return true;
				}
			}, 15, TimeUnit.SECONDS);
			timer_running = true;
		}
	}
	
	public void queue(TLObject object) throws Exception {
	  queue(object, true, false, null);
	}
	public void queue(TLObject object, boolean encrypted, boolean significant, RPCCallback response) throws Exception {
		queuedCalls.add(new RPCCall(object, encrypted, significant, response));
		
		if (queuedCalls.size() > 15) {
			sendQueued();
		} else
		if (!timer_running) {
			service.scheduledPool.schedule(new Callable<Boolean>() {
				public Boolean call() throws Exception {
					timer_running = false;
					sendQueued();
					return true;
				}
			}, 15, TimeUnit.SECONDS);
			timer_running = true;
		}
	}
	
	public void send(TLObject object) throws Exception {
		send(object, true, true, null);
	}
	
	public void send(TLObject object, boolean encrypted) throws Exception {
		send(object, encrypted, encrypted, null);
	}
	
	public void send(TLObject object, boolean encrypted, boolean significant, RPCCallback callback) throws Exception {
	  queue(object, encrypted, significant, callback);
	  sendQueued();
	}

	
	private boolean initPerformed = false;
	public void call(TLFunction request, RPCCallback callback, boolean latestLayer) {
		try {
		  if (latestLayer && !initPerformed) {
		    send(new InvokeWithLayer9(new InitConnection(Config.api_id, Config.getDeviceModel(), Config.getSystemVersion(), Config.app_version, "ru", request)), true, true, callback);
		  } else {
		    send(request, true, true, callback);
		  }			
		} catch (Exception e) {
			e.printStackTrace();
			callback.error(0, e.getMessage());
		}
	}
	
	public void call(TLFunction request, RPCCallback callback) {
    call(request, callback, true);
  }
	
	private void processMessage(TLObject message, long msg_id) throws Exception {
	  //Log.i(TAG, "<= " + message);
		ArrayList<Long> confirm = new ArrayList<Long>();
		if (message instanceof MsgContainer) {
			for (TLObject child : ((MsgContainer) message).messages) {
				if ((((TransportMessage) child).seqno & 1) == 1) {
					confirm.add(((TransportMessage) child).msg_id);
				}
				
				//Log.i(TAG, "<== " + Long.toString(((TransportMessage) child).msg_id, 16) + ": " + ((TransportMessage) child).body);
				processMessage(((TransportMessage) child).body, ((TransportMessage) child).msg_id);
			}
		} else
		if (message instanceof BadMsgNotification) {
			Log.w(TAG, "Bad message: " + ((BadMsgNotification) message).error_code);
			
			if (((BadMsgNotification) message).error_code == 16 || ((BadMsgNotification) message).error_code == 17) {
			  System.out.println("Invalid msg_id (was " + ((BadMsgNotification) message).bad_msg_id + ", received " + msg_id + "), old time_diff: " + time_diff);
			  time_diff = (int) (((msg_id & ~3L) / 4294967.296 - System.currentTimeMillis()) / 1000);
			  System.out.println("new time_diff: " + time_diff);
			  
			  for (RPCCall call : sentCalls) {
	        if (call.message_id == ((BadMsgNotification) message).bad_msg_id) {
	          sentCalls.remove(call);
	          
	          send(call.payload, call.encrypted, call.significant, call.callback);
	          break;
	        }
	      }
			}
		} else
		if (message instanceof BadServerSalt) {
			//Log.w(TAG, "Bad server salt, updating");
			BadServerSalt result = (BadServerSalt) message;
			auth.server_salt = result.new_server_salt;
			pref.putLong("server_salt", auth.server_salt);
			
			// resend broken call
			for (RPCCall call : sentCalls) {
				if (call.message_id == result.bad_msg_id) {
					sentCalls.remove(call);
					
					send(call.payload, call.encrypted, call.significant, call.callback);
					break;
				}
			}
		} else
		if (message instanceof RpcResult) {
			RpcResult result = ((RpcResult) message);
			//Log.i(TAG, "<<< " + result.result);

			for (final RPCCall call : sentCalls) {
				if (call.message_id == result.req_msg_id) {
					if (result.result instanceof RpcError) {
						RpcError error = ((RpcError) result.result);
						//Log.e(TAG, "<= " + result.result);
						
						Pattern p = Pattern.compile("MIGRATE_(\\d+)");
						Matcher m = p.matcher(error.error_message);
						if (error.error_code == 303 && m.find()) { // MIGRATE
							final int dc = Integer.parseInt(m.group(1));
							Log.e(TAG, "Reconnecting");
							service.mainServerID = dc;
							service.connectAndPrepare(dc, true, false, false, new AuthCallback() {
								public void done(Server server, byte[] auth_key) {
									// ...and perform call once again
								  service.mainServerID = dc;
									try {
										server.send(call.payload, call.encrypted, call.significant, call.callback);
									} catch (Exception e) {
										e.printStackTrace();
										call.callback.error(0, "Error while resending request");
									}
								}

								public void error() {
									Log.e(TAG, "Unable to generate new auth key");
									call.callback.error(0, "Unable to generate new auth key");
								}
							});
						} else {
              call.callback.error(error.error_code, error.error_message);
            }
          } else {
            call.callback.done(result.result);
          }
					
					sentCalls.remove(call);
					break;
				}
			}
		} else
		if (message instanceof TUpdates) {
		  service.processUpdates((TUpdates) message);
		} else {
		  //Log.i(TAG, "Unprocessed message: " + message.toString());
		}
		
		if (confirm.size() > 0) {
			long[] array = new long[confirm.size()];
			for (int i = 0; i < array.length; i++) {
				array[i] = confirm.get(i);
			}
			queue(new MsgsAck(array));
		}
	}

	public void done(Server server) {
		disconnect();
	}

	public void incoming(ByteBuffer packet) {
		try {
			Message message = Message.decrypt(packet, auth.auth_key, auth.auth_key_id);
			if (message == null) {
				return;
			}
			
			if (message.error != 0) {
				Log.e(TAG, "<= error " + message.error);
			} else {
				//Log.i(TAG, "<= " + Long.toString(message.message_id, 16) + ": " + message.payload);
			}
			
			// process message
			if (auth.state != AuthState.NONE && auth.state != AuthState.FAILED && auth.state != AuthState.COMPLETE) {
				auth.generateKey(message.payload);
			}
			
			processMessage(message.payload, message.message_id);
			
			if (message instanceof EncryptedMessage) {
				if ((((EncryptedMessage) message).seq_no & 1) == 1) {
					queue(new MsgsAck(new long[]{ message.message_id }));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void error(int code, String message) {
		// 
	}
}
