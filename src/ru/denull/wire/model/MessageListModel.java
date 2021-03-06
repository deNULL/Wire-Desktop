package ru.denull.wire.model;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.SwingUtilities;

import ru.denull.mtproto.DataService;
import ru.denull.mtproto.Server;
import ru.denull.mtproto.Server.RPCCallback;
import ru.denull.wire.MessageCellRenderer;
import ru.denull.wire.Utils;
import ru.denull.wire.model.DialogManager.EncryptedDialog;
import tl.*;
import tl.messages.GetHistory;
import tl.messages.Messages;
import tl.messages.MessagesSlice;
import tl.messages.ReadHistory;
import tl.messages.TMessages;

public class MessageListModel extends AbstractListModel {
  private static final long serialVersionUID = 5885327904689475365L;
  public static final int PRELOAD_FROM_CACHE_COUNT = 20;
  public static final int LOAD_FROM_CACHE_COUNT = 50;
  public static final int LOAD_FROM_CACHE_DISTANCE = 10;
  public static final int PRELOAD_COUNT = 80;
  public static final int LOAD_COUNT = 50;
  public static final int LOAD_DISTANCE = 20;
  private DataService service;
  private JList list;
  private TInputPeer peer;
  private EncryptedDialog encrypted = null;
  private int encryptedPos = -1;
  
  LinkedList<Object> items = new LinkedList<Object>();
  public boolean loading = false;
  boolean loadingFromCache = false;
  boolean initializing = true;
  boolean cachedData = true;
  boolean starting = true;
  int total = -1;
  int loaded = 0;
  int first_id = 0;
  
  boolean scrollToLast = true;
  boolean updatingScroll = false;

  public MessageListModel(DataService service, JList list, TInputPeer peer) {
    this(service, list, peer, null);
  }
  public MessageListModel(DataService service, JList list, TInputPeer peer, EncryptedDialog encrypted) {
    this.service = service;
    this.peer = peer;
    this.list = list;
    this.encrypted = encrypted;
    
    //peer_id = Utils.getPeerID(peer, service.me);
    load();
    loadFromCache();
  }

  public Object getElementAt(int index) {
    if (encrypted != null && !(encrypted.chat instanceof EncryptedChat)) {
      return Utils.getEncryptedChatStatus(encrypted.chat);
    }
    return (peer == null) ? "Выберите диалог" : ((index < items.size()) ? items.get(index) : ((loading || loadingFromCache || initializing) ? "Загрузка..." : "Нет сообщений"));
  }

  public int getSize() {
    if (encrypted != null && !(encrypted.chat instanceof EncryptedChat)) {
      return 1;
    }
    return Math.max(1, items.size());
  }

  /*public int getItemViewType(int position) {
    Object item = getItem(position);
    if (item instanceof Integer) { // separator
      return 4;
    } else {
      TMessage message = (TMessage) item;
      if (message instanceof MessageService) {
        return 4;
      }
      
      if (message.out) {
        return (message.media instanceof MessageMediaEmpty) ? 2 : 3;
      } else {
        return (message.media instanceof MessageMediaEmpty) ? 0 : 1;
      }
    }
  }

  public int getViewTypeCount() {
    return 5; // 0: message/in, 1: attach/in, 2: message/out, 3: attach/out, 4: date separator
  }

  public boolean isEnabled(int position) {
    return !(getItem(position) instanceof Integer);
  }*/
  
  public boolean scrolled(int firstVisible, int lastVisible) {
    if (updatingScroll) return true;
    
    if (lastVisible > -1) {
      scrollToLast = (lastVisible >= getSize() - 1);
      //System.out.println("firstVis: " + firstVisible + ", lastVisible: " + lastVisible + ", size: " + getSize());
    }
    
    if (loading || loadingFromCache || initializing) return true;
    if (total > -1 && loaded >= total) {
      return false;
    }
    
    if (cachedData) {
      if (firstVisible < LOAD_FROM_CACHE_DISTANCE) {
        loadFromCache();
      }
    } else {
      if (firstVisible < LOAD_DISTANCE) {
        load();
      }
    }
    
    return true;
  }
  
  private void loadFromCache() {
    if (!cachedData || loadingFromCache || peer == null) return;
    
    loadingFromCache = true;
    //System.out.println("Loading history from cache up to id " + first_id);
    service.threadPool.submit(new Runnable() {
      public void run() {
        if (cachedData) { // still allowed to query data from DB
          final TMessage[] data = service.messageManager.getHistory(peer, first_id, PRELOAD_FROM_CACHE_COUNT);
          
          loadingFromCache = false;
          if (cachedData) {
            add(data);
            if (data.length < PRELOAD_FROM_CACHE_COUNT) { // found outdated message or just end of cache
              cachedData = false;
              //System.out.println("Gap in cache or cache just ended, will now load from server");
            }
            
            //checkForPreload();
          }
        } else {
          loadingFromCache = false;
        }
      }
    });
  }
  
  private void load() {
    // TODO: check first two conditions
    if (service.mainServer == null || !service.mainServer.transport.isConnected() || loading || peer == null) return;
    
    if (encrypted != null) {
      try {
        if (new File(Utils.getHomeDir("encrypted_chat" + encrypted.chat.id + ".dat")).exists() && encryptedPos != 0) {
          FileInputStream f = new FileInputStream(Utils.getHomeDir("encrypted_chat" + encrypted.chat.id + ".dat"));
          FileChannel ch = f.getChannel();
          MappedByteBuffer mb = ch.map(MapMode.READ_ONLY, 0L, encryptedPos < 0 ? ch.size() : encryptedPos);
          mb.order(ByteOrder.LITTLE_ENDIAN);
          
          encryptedPos = mb.capacity();
          ArrayList<TMessage> messages = new ArrayList<TMessage>();
          for (int i = 0; i < 100; i++) {
            if (encryptedPos < 8) break;
            
            mb.position(encryptedPos - 8);
            encryptedPos = (int) mb.getLong();
            
            mb.position(encryptedPos);
            int user_id = mb.getInt();
            TEncryptedMessage encrypted = (TEncryptedMessage) TL.read(mb);
            TDecryptedMessage decrypted = (TDecryptedMessage) TL.read(mb);
            
            TMessage stub = new Message((int) (Math.random() * 10000000), 0, new PeerUser(user_id), user_id == service.me.id, true, encrypted.date, decrypted.message, new MessageMediaEmpty());
            messages.add(stub);
          }
          add(messages.toArray(new TMessage[messages.size()]));
          cachedData = false;
          f.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return;
    }
    
    // prevent from loading two times at once
    loading = true;
    // TODO: use max_id instead of offset?
    //System.out.println("Loading history up to id " + first_id);
    service.mainServer.call(new GetHistory(peer, 0, first_id, (first_id == 0) ? PRELOAD_COUNT : LOAD_COUNT), new RPCCallback<TMessages>() {
      public void done(final TMessages result) {
        if (result.messages.length > 0 && result.messages[0].unread && !result.messages[0].out) {
          for (TMessage message : result.messages) {
            if (!message.out) {
              message.unread = false;
            }
          }
          service.dialogManager.resetUnread(peer);
          service.mainServer.call(new ReadHistory(peer, result.messages[0].id, 0), new Server.RPCCallback<TLObject>() {
            public void done(TLObject result) {
            }
            public void error(int code, String message) {
            }
          });
        }
        
        service.chatManager.store(result.chats);
        service.userManager.store(result.users);
        
        final boolean forceCached = false;// (service.messageManager.get(result.messages[result.messages.length - 1].id) != MessageManager.empty);
        service.messageManager.store(result.messages);
        
        if (result instanceof Messages) {
          total = result.messages.length;
        } else {
          total = ((MessagesSlice) result).count;
        }
        
        /*if (cachedData) {
          items.clear();
          cachedData = false;
        }*/
        cachedData = add(result.messages); // if loaded data overlaps data from cache, we can continue loading from cache
        if (cachedData) {
          //System.out.println("Overlaps cached data, will now load from cache");
        } else
        if (forceCached) {
          cachedData = true;
          //System.out.println("Last loaded item is already in cache, will now load from cache");
        }
        cachedData = false;
        loading = false;
        
        //checkForPreload();
      }
      public void error(int code, String message) {
        System.out.println("Error while loading messages");
      }
    });
  }
  
  // returns true if data was merged with already loaded from cache
  public boolean add(TMessage[] messages) {
    initializing = true;
    
    //scrollToLast = (list.getLastVisibleIndex() == -1) || (list.getLastVisibleIndex() >= getSize() - 1);
    //System.out.println("last: " + list.getLastVisibleIndex() + ", size: " + getSize());
    final int oldSize = items.size();    
    final Rectangle oldRect = list.getVisibleRect();
    //System.out.println(oldRect);
    
    boolean merge = false;
    LinkedList<Object> oldItems = null;
    if (!items.isEmpty() && messages.length > 0 && items.getLast() instanceof TMessage) {
      TMessage last = (TMessage) items.getLast();
      if (last.id <= messages[0].id) {
        if (last.id >= messages[messages.length - 1].id || true) { // merge (loaded and new data overlap)
          merge = true;
          while (!items.isEmpty()) {
            Object l = items.getLast();
            if (l instanceof Integer || ((TMessage) l).id >= messages[messages.length - 1].id) {
              if (l instanceof TMessage) loaded--;
              items.removeLast();
            } else {
              break;
            }
          }
          oldItems = items;
          items = new LinkedList<Object>();
        } else { // drop (there's a gap between loaded and new data)
          items.clear();
        }
      }
    }
    
    Object last = items.isEmpty() ? null : items.getFirst();
    for (TMessage message : messages) {
      //if (message instanceof Message || message instanceof MessageForwarded) {
        if (last != null) {
          if (last instanceof Integer) {
            if (Utils.sameDay((Integer) last, message.date)) {
              items.removeFirst();
            }
          } else {
            if (!Utils.sameDay(((TMessage) last).date, message.date)) {
              items.addFirst(((TMessage) last).date);
            }
          }
        }
        items.addFirst(message);
        
        //if (!merge) {
          first_id = message.id;
        //}
        last = message;
        loaded++;
      //}
    }
    
    if (last != null && last instanceof TMessage) {
      if ((merge && !oldItems.isEmpty() && Utils.sameDay(((TMessage) last).date, ((TMessage) oldItems.getLast()).date)) || !merge) {
        items.addFirst(((TMessage) last).date);
      }
    }
    
    if (merge) {
      oldItems.addAll(items);
      items = oldItems;
    }

    //fireIntervalAdded(this, oldSize, items.size() - 1);
    //fireIntervalAdded(this, 0, items.size());
    //fireContentsChanged(this, 0, items.size());
    //fireIntervalRemoved(this, 0, items.size() - 1);
    //System.out.println("oldSize: " + oldSize + ", new size: " + items.size());
    /*if (listView != null && resetScroll) {
      listView.setSelectionFromTop(scrollPos, scrollOffs);
    }*/
    final int diff = (items.size() - oldSize);
    
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        updatingScroll = true;
        int li = list.getLastVisibleIndex();
        if (getSize() - oldSize - 1 >= 0) {
          fireIntervalAdded(this, 0, getSize() - oldSize - 1);
        }
        
        if (scrollToLast) {
          if (items.size() > 0) {
            //list.ensureIndexIsVisible(items.size() - 1);
            list.scrollRectToVisible(list.getCellBounds(items.size() - 1, items.size() - 1));
            //System.out.println("scroll to last (add)");
          }
        } else
        if (diff > 0) {
          //int fi = list.getFirstVisibleIndex();
          //list.ensureIndexIsVisible(li + diff);
          Rectangle addedRect = list.getCellBounds(0, items.size() - oldSize - 1);
          //System.out.println("addedRect: " + addedRect + ", whole: " + list.getCellBounds(0, items.size() - 1) + ", visible: " + list.getVisibleRect() + ", old: " + oldRect);
          list.scrollRectToVisible(new Rectangle(oldRect.x, oldRect.y + addedRect.height, oldRect.width, oldRect.height));
         
          //System.out.println("now visible: " + list.getVisibleRect());
          //list.ensureIndexIsVisible(fi + diff);
        }
        updatingScroll = false;
        initializing = false;
        starting = false;
      }
    });
    state++;
    
    return merge;
  }
  public void addMessage(TMessage message) {    
    Object first = items.isEmpty() ? null : items.getLast();
    if (first == null || (first instanceof TMessage && !Utils.sameDay(((TMessage) first).date, message.date))) {
      items.add(message.date);
    }
    items.add(message);
    state++;

    fireIntervalAdded(this, items.size() - 1, items.size() - 1);
    if (scrollToLast) {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          updatingScroll = true;
          //list.ensureIndexIsVisible(items.size() - 1);
          list.scrollRectToVisible(list.getCellBounds(getSize() - 1, getSize() - 1));
          updatingScroll = false;
          scrollToLast = true;
          //System.out.println("scroll to last (addMessage)");
        }
      });
    }
  }
  public void addMessage(TEncryptedMessage encrypted, TDecryptedMessage decrypted) {
    TMessage stub = new Message((int) (Math.random() * 10000000), 0, new PeerUser(service.me.id), false, true, encrypted.date, decrypted.message, new MessageMediaEmpty());
    //service.messageManager.nextMessageID++;
    addMessage(stub);
  }

  public void updateContents() {
    state++;
    fireContentsChanged(this, 0, getSize() - 1);
  }
  
  public void updateContents(int index) {
    fireContentsChanged(this, index, index);
  }

  private long state = 0;
  public long getState() {
    return state;
  }

  public void updateContentsID(int id) {
    //System.out.println("upd " + id);
    for (int i = getSize() - 1; i >= 0; i--) {
      Object o = getElementAt(i);
      if (o instanceof TMessage && ((TMessage) o).id == id) {
        updateContents(i);
        //System.out.println("upd " + i + ":" + id);
        return;
      }
    }
  }

  public void updateContentsID(int[] messages) {
    if (messages.length == 0) return;
    MessageCellRenderer renderer = (MessageCellRenderer) list.getCellRenderer();
    HashSet<Integer> ids = new HashSet<Integer>();
    for (int id : messages) {
      ids.add(id);
      renderer.cache.remove(id);
    }
    //int st = -1, en = -1;
    for (Object o : items) {
      if (o instanceof TMessage && ids.contains(((TMessage) o).id)) {
        ((TMessage) o).unread = false;
      }
    }
    fireContentsChanged(this, 0, getSize() - 1);
  }
}
