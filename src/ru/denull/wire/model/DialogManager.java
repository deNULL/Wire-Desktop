package ru.denull.wire.model;

import static ru.denull.mtproto.CryptoUtils.SHA1;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.swing.*;

import ru.denull.mtproto.DataService;
import ru.denull.mtproto.Server;
import ru.denull.mtproto.Server.RPCCallback;
import ru.denull.wire.Utils;
import tl.*;
import tl.TMessage;
import tl.messages.*;

public class DialogManager extends AbstractListModel {
  private static final long serialVersionUID = 5885327904689475365L;
  private DataService service;
  
  boolean loading = false, loadingFromCache = false;
  boolean initializing = false;
  boolean cachedData = true;
  int total = -1;
  int loaded = 0;
  int first_id = 0;

  public LinkedList<Dialog> all = new LinkedList<Dialog>();
  public LinkedList<EncryptedDialog> encrypted = new LinkedList<EncryptedDialog>();
  public LinkedList<Dialog> filtered = all;
  
  public HashMap<Integer, EncryptedDialog> chats = new HashMap<Integer, EncryptedDialog>();
  String filterQuery = null;
  
  public int totalUnread = 0;
  
  static final Comparator<Dialog> topMessageComparator = new Comparator<Dialog>() {
    public int compare(Dialog a, Dialog b) {
      return Integer.valueOf(Math.abs(b.top_message)).compareTo(Math.abs(a.top_message));
    }
  };
  
  public class EncryptedDialog extends Dialog {
    public TEncryptedChat chat;
    public byte[] a_or_b;
    public byte[] dh_prime;
    public byte[] key;

    public EncryptedDialog(TPeer peer, int top_message, int unread_count, TEncryptedChat chat, byte[] a_or_b, byte[] dh_prime, byte[] key) {
      super(peer, top_message, unread_count);
      this.chat = chat;
      this.a_or_b = a_or_b;
      this.dh_prime = dh_prime;
      this.key = key;
    }
    
    public EncryptedDialog(ByteBuffer buffer) throws Exception {
      super(buffer);
      chat = (TEncryptedChat) TL.read(buffer);
      a_or_b = new byte[256];
      buffer.get(a_or_b, 0, 256);
      dh_prime = new byte[256];
      buffer.get(dh_prime, 0, 256);
      key = new byte[256];
      buffer.get(key, 0, 256);
    }
    
    public ByteBuffer writeTo(ByteBuffer buffer) throws Exception {
      super.writeTo(buffer, false);
      chat.writeTo(buffer, true);
      buffer.put(a_or_b);
      buffer.put(dh_prime);
      buffer.put(key);
      return buffer;
    }
    
    public int getSize() throws Exception {
      return length() + chat.length(true) + 256 + 256 + 256;
    }
  }
  
  public DialogManager(DataService service) {
    this.service = service;
    
    //peer_id = Utils.getPeerID(peer, service.me);
    //reloadDialogs();
    reloadEncrypted();
  }

  public Object getElementAt(int index) {
    return (index < filtered.size() ? filtered.get(index) : (filterQuery == null ? "У вас ещё нет диалогов" : "Ничего не найдено"));
  }

  public int getSize() {
    return Math.max(1, filtered.size());
  }
  
  public boolean isEmpty() {
    return filtered.isEmpty();
  }

  public void filter(String query) {
    filter(query, false);
  }
  
  public void filter(String query, boolean force) {
    
    
    if (query != null) {
      query = query.trim().toLowerCase();
    }
    
    if (!force && filterQuery != null && filterQuery.equals(query)) {
      return;
    }

    filtered = new LinkedList<Dialog>();
    
    if (query == null || query.length() == 0) {
      filtered.addAll(all);
      filtered.addAll(encrypted);
      Collections.sort(filtered, topMessageComparator);
      
      filterQuery = null;
      fireContentsChanged(this, 0, getSize() - 1);
      return;
    }
    
    filterList(all, query);
    filterList(encrypted, query);
    Collections.sort(filtered, topMessageComparator);
    filterQuery = query;
    fireContentsChanged(this, 0, getSize() - 1);
  }
  
  public void filterList(LinkedList<? extends Dialog> list, String query) {
    for (Dialog dialog : list) {
      boolean matches = false;
      if (dialog.peer instanceof PeerChat) {
        matches = service.chatManager.get(dialog.peer.chat_id).title.toLowerCase().indexOf(query) >= 0;
      } else {
        TUser user = service.userManager.get(dialog.peer.user_id);
        matches = (user.first_name + " " + user.last_name).toLowerCase().indexOf(query) >= 0 || (user.last_name + " " + user.first_name).toLowerCase().indexOf(query) >= 0;
      }
      
      if (matches) {
        filtered.add(dialog);
      }
    }
  }
  
  public void updateContents() {
    fireContentsChanged(this, 0, getSize() - 1);
  }

  public void updateContents(int index) {
    fireContentsChanged(this, index, index);
  }
  
  public void reloadDialogs() {
    boolean force = true;
    final boolean cachedData = false;
    
    if (service.mainServer == null/* || !service.mainServer.transport.isConnected()*/){
      //Log.i(TAG, "Not yet connected, stop");
      return;
    }
    //Log.i(TAG, "loading = " + service.dialogManager.loading);
    if (service.dialogManager.loading) return;
    if (!force && service.dialogManager.total > -1 && all.size() >= service.dialogManager.total) return;
    
    service.dialogManager.loading = true;
    service.mainServer.call(new GetDialogs(cachedData ? 0 : all.size(), 0, 100), new RPCCallback<TDialogs>() {
      public void done(TDialogs result) {
        if (result instanceof Dialogs) {
          Dialogs dialogs = (Dialogs) result;
          service.dialogManager.total = dialogs.dialogs.length;
          store(dialogs.dialogs, true);
          service.chatManager.store(dialogs.chats);
          service.userManager.store(dialogs.users);
          service.messageManager.store(dialogs.messages);
        } else {
          DialogsSlice dialogs = (DialogsSlice) result;
          service.dialogManager.total = dialogs.count;
          store(dialogs.dialogs, cachedData);
          service.chatManager.store(dialogs.chats);
          service.userManager.store(dialogs.users);
          service.messageManager.store(dialogs.messages);
        }
        service.dialogManager.loading = false;
        //cachedData = false;
        
        /*dialogList.setModel(new AbstractListModel() {
          String[] values = new String[service.dialogManager.total];
          public int getSize() {
            return values.length;
          }
          public Object getElementAt(int index) {
            return values[index];
          }
        });*/
        //fireContentsChanged(list, 0, service.dialogManager.loaded.size() - 1);
        
        filter(filterQuery, true);
      }
      public void error(int code, String message) {
        //Log.e(TAG, "Error while loading dialogs");
      }
    });
  }
  
  public void reloadEncrypted() {
    encrypted.clear();
    try {
      if (new File(Utils.getHomeDir("encrypted_chats.dat")).exists()) {
        FileInputStream f = new FileInputStream(Utils.getHomeDir("encrypted_chats.dat"));
        FileChannel ch = f.getChannel( );
        MappedByteBuffer mb = ch.map( MapMode.READ_ONLY, 0L, ch.size());
        mb.order(ByteOrder.LITTLE_ENDIAN);
        while (mb.hasRemaining()) {
          EncryptedDialog d = new EncryptedDialog(mb); 
          encrypted.push(d);
          chats.put(d.chat.id, d);
        }
        f.close();
      }
      
      filter(filterQuery, true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void saveEncrypted() {
    try {
      FileOutputStream f = new FileOutputStream(Utils.getHomeDir("encrypted_chats.dat"), false);
      FileChannel ch = f.getChannel();
      
      int capacity = 0;
      for (EncryptedDialog dialog : encrypted) {
        capacity += dialog.getSize();
      }
      
      ByteBuffer buf = ByteBuffer.allocate(capacity);
      buf.order(ByteOrder.LITTLE_ENDIAN);
      for (EncryptedDialog dialog : encrypted) {
        dialog.writeTo(buf);
      }
      
      buf.flip();
      ch.write(buf);
      f.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void addMessage(TMessage message, int chat_id) {
    int index = 0;
    Dialog enc = chats.get(chat_id);
    for (Dialog dialog : all) {
      if (dialog == enc) {
        break;
      }
      if ((dialog.peer instanceof PeerUser && message.to_id instanceof PeerUser && (dialog.peer.user_id == message.from_id || dialog.peer.user_id == message.to_id.user_id)) ||
          (dialog.peer instanceof PeerChat && message.to_id instanceof PeerChat && dialog.peer.chat_id == message.to_id.chat_id)) {
        break; 
      }
      index++;
    }
    
    Dialog dialog;
    if (index < all.size()) {
      if (message.id > 0 && all.get(index).top_message > message.id) return;
      dialog = all.remove(index);
      dialog.top_message = message.id;
      if (!message.out) {
        dialog.unread_count++;
        updateUnreadCount(totalUnread + 1);
      }
    } else {
      dialog = new Dialog(message.to_id instanceof PeerChat ? message.to_id : new PeerUser(message.out ? message.to_id.user_id : message.from_id), message.id, 1);
    }
    
    all.addFirst(dialog);
    // TODO: store at DB
  }
  public void addMessage(TMessage message) {
    addMessage(message, -1);
  }
  
  public void store(TDialog[] dialogs, boolean reset) {
    if (reset) {
      all.clear();
      totalUnread = 0;
    }
    
    for (TDialog dialog : dialogs) {      
      all.add((Dialog) dialog);
      totalUnread += dialog.unread_count;
    }
  }
  
  public void resetUnread(TInputPeer peer) {
    int peer_id = Utils.getPeerID(peer, service.me);
    for (Dialog dialog : all) {
      if ((dialog.peer instanceof PeerUser && ((PeerUser) dialog.peer).user_id == peer_id) ||
          (dialog.peer instanceof PeerChat && ((PeerChat) dialog.peer).chat_id == -peer_id)) {
        updateUnreadCount(Math.max(0, totalUnread - dialog.unread_count));
        dialog.unread_count = 0;
        break;
      }
    }
  }
  
  public void updateUnreadCount(int unread) {
    totalUnread = unread;
    
    //Application app = Application.getApplication();
    //app.setDockIconBadge();  
    if (System.getProperty("os.name").contains("Mac")) {
      try {
        Object app = 
          Class.forName("com.apple.eawt.Application")
          .getMethod("getApplication", (Class[]) null)
          .invoke(null, (Object[]) null);
        
        app.getClass()
          .getMethod("setDockIconBadge", new Class[] { String.class })
          .invoke(app, new Object[] { totalUnread > 0 ? Utils.toCount(totalUnread) : "" });
      } catch (Exception e) {
        //fail quietly
      }
    }
  }

  public void addEncryptedChat(int user_id, TEncryptedChat chat, byte[] a_or_b, byte[] dh_prime) {
    EncryptedDialog d = new EncryptedDialog(new PeerUser(user_id), -service.messageManager.nextMessageID, 0, chat, a_or_b, dh_prime, new byte[256]); 
    encrypted.push(d);
    chats.put(d.chat.id, d);
    saveEncrypted();
    filter(filterQuery, true);
  }

  public void updateEncryptedChat(TEncryptedChat chat) {
    int user_id = (chat.admin_id == service.me.id) ? chat.participant_id : chat.admin_id;
    EncryptedDialog d = chats.get(chat.id);
    if (d == null) {
      addEncryptedChat(user_id, chat, new byte[256], new byte[256]); 
      return;
    }
    
    d.chat = chat;
    if (chat instanceof EncryptedChat && chat.admin_id == service.me.id) {
      BigInteger g_b = new BigInteger(1, chat.g_a_or_b);
      BigInteger a = new BigInteger(1, d.a_or_b);
      BigInteger dh_prime = new BigInteger(1, d.dh_prime);
      
      d.key = g_b.modPow(a, dh_prime).toByteArray();
      for (int i = 0; i < d.key.length; i++) {
        d.key[i] ^= chat.nonce[i];
      }
      
      ByteBuffer tmp;
      try {
        tmp = ByteBuffer.wrap(SHA1(d.key));
        tmp.order(ByteOrder.LITTLE_ENDIAN);
        
        long key_fingerprint = tmp.getLong(12);
        
        if (key_fingerprint != chat.key_fingerprint) {
          encrypted.remove(d);
          chats.remove(d.chat.id);
          JOptionPane.showMessageDialog(null, "Ошибка создания секретного чата: отпечаток ключа некорректен.");
          return;
        }
      } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
      }
    }
    saveEncrypted();
    filter(filterQuery, true);
  }

  public void addEncryptedMessage(int chat_id, int user_id, TEncryptedMessage encrypted, TDecryptedMessage message) {
    try {
      FileOutputStream f = new FileOutputStream(Utils.getHomeDir("encrypted_chat" + chat_id + ".dat"), true);
      FileChannel ch = f.getChannel();
      
      long size = ch.size();
      
      ByteBuffer buf = ByteBuffer.allocate(4 + encrypted.length(true) + message.length(true) + 8);
      buf.order(ByteOrder.LITTLE_ENDIAN);
      buf.putInt(user_id);
      encrypted.writeTo(buf, true);
      message.writeTo(buf, true);
      buf.putLong(size);
      buf.flip();
      ch.write(buf);
      f.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
}
