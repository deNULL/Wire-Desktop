package ru.denull.wire.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import ru.denull.mtproto.DataService;
import ru.denull.mtproto.Server;
import ru.denull.wire.Utils;
import tl.TInputPeer;
import tl.TLObject;
import tl.TUser;
import tl.messages.SetTyping;

public class TypingManager {
  DataService service;
  public TypingCallback callback = null;
  public HashMap<Integer, Integer> typed = new HashMap<Integer, Integer>();
  public HashMap<Integer, Boolean> users = new HashMap<Integer, Boolean>();
  public HashMap<Integer, ScheduledFuture<?>> usersFuture = new HashMap<Integer, ScheduledFuture<?>>();
  public HashMap<Integer, HashSet<Integer>> chats = new HashMap<Integer, HashSet<Integer>>();
  public HashMap<Integer, HashMap<Integer, ScheduledFuture<?>>> chatsFuture = new HashMap<Integer, HashMap<Integer,ScheduledFuture<?>>>();
  
  public interface TypingCallback {
    public void update(int peer_id);
  }

  public TypingManager(DataService service) {
    this.service = service;
  }
  
  // called on each keypress
  public void startTyping(TInputPeer peer) {
    if (peer == null) return;
    
    int peer_id = Utils.getPeerID(peer, service.me);
    if (typed.get(peer_id) != null && System.currentTimeMillis() / 1000 - typed.get(peer_id) < 4) {
      return;
    }
    
    typed.put(peer_id, (int) (System.currentTimeMillis() / 1000));
    service.mainServer.call(new SetTyping(peer, true), new Server.RPCCallback<TLObject>() {
      public void done(TLObject result) {
        
      }
      public void error(int code, String message) {
        
      }
    });
  }
  
  // called when user definitely stopped typing (closed keyboard, moved from fragment)
  public void stopTyping(TInputPeer peer) {
    if (peer == null) return;
    
    int peer_id = Utils.getPeerID(peer, service.me);
    if (System.currentTimeMillis() / 1000 - typed.get(peer_id) > 5) {
      return;
    }
    typed.remove(Utils.getPeerID(peer, service.me));
    service.mainServer.call(new SetTyping(peer, false), new Server.RPCCallback<TLObject>() {
      public void done(TLObject result) {
        
      }
      public void error(int code, String message) {
        
      }
    });
  }
  
  public void userEncryptedTyping(int chat_id) {
    userTyping(-chat_id, true);
  }
  public void userTyping(int user_id) {
    userTyping(user_id, true);
  }
  public void userTyping(final int user_id, boolean isTyping) {
    if (isTyping) {
      ScheduledFuture<?> future = usersFuture.get(user_id);
      if (future != null) {
        usersFuture.remove(user_id);
        future.cancel(true);
      }
      
      usersFuture.put(user_id, service.scheduledPool.schedule(new Runnable() {
        public void run() {
          userTyping(user_id, false);
        }
      }, 5, TimeUnit.SECONDS));
    }
    
    users.put(user_id, isTyping);
    
    if (callback != null) {
      callback.update(user_id);
    }
  }
  
  
  public void userTyping(int peer_id, int user_id) {
    userTyping(peer_id, user_id, true);
  }
  public void userTyping(final int peer_id, final int user_id, boolean isTyping) {
    if (isTyping) {
      if (chatsFuture.get(peer_id) != null) {
        ScheduledFuture<?> future = chatsFuture.get(peer_id).get(user_id);
        if (future != null) {
          usersFuture.remove(user_id);
          future.cancel(true);
        }
      } else {
        chatsFuture.put(peer_id, new HashMap<Integer, ScheduledFuture<?>>());
      }
      
      chatsFuture.get(peer_id).put(user_id, service.scheduledPool.schedule(new Runnable() {
        public void run() {
          userTyping(peer_id, user_id, false);
        }
      }, 5, TimeUnit.SECONDS));
    }
    
    if (chats.get(peer_id) == null) {
      chats.put(peer_id, new HashSet<Integer>());
    }
    if (isTyping) {
      chats.get(peer_id).add(user_id);
    } else {
      chats.get(peer_id).remove(user_id);
    }
    
    if (callback != null) {
      callback.update(peer_id);
    }
  }
  
  // checks if user is typing now
  public boolean isTyping(int user_id) {
    return users.containsKey(user_id) && users.get(user_id);
  }
  
  // return null if nobody in chat is typing, formatted string otherwise
  public String areTyping(int chat_id) {
    HashSet<Integer> typing = chats.get(-chat_id);
    if (typing == null || typing.size() == 0) {
      return null;
    }
    
    if (typing.size() > 2) {
      return typing.size() + " участника печатают...";
    }
    
    int num = 0;
    String result = "";
    for (int user_id : typing) {
      TUser user = service.userManager.get(user_id);
      if (num > 0) {
        result += ", ";
      }
      result += user.first_name;
      num++;
    }
    return result + (typing.size() == 1 ? " печатает" : " печатают") + "...";
  }
  
  // null if not typing, formatted string otherwise
  public String getStatus(int peer_id, boolean full) {
    if (peer_id > 0) {
      if (!isTyping(peer_id)) {
        return null;
      }
      
      if (!full) {
        return "печатает...";
      }
      
      TUser user = service.userManager.get(peer_id);
      return user.first_name + " " + user.last_name + " печатает...";
    } else {
      return areTyping(-peer_id);
    }
  }
}
