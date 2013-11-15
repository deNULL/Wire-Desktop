package ru.denull.wire.model;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.SwingUtilities;

import ru.denull.mtproto.DataService;
import ru.denull.mtproto.Server;
import ru.denull.mtproto.Server.RPCCallback;
import ru.denull.wire.Utils;
import tl.*;
import tl.TMessage;
import tl.messages.*;

public class DialogListModel extends AbstractListModel {
  private static final long serialVersionUID = 5885327904689475365L;
  private DataService service;
  private JList list;
  
  boolean loading = false, loadingFromCache = false;
  boolean initializing = false;
  boolean cachedData = true;
  int total = -1;
  int loaded = 0;
  int first_id = 0;
  
  LinkedList<Dialog> filtered = null;
  String filterQuery = null;
  
  public DialogListModel(DataService service, JList list) {
    this.service = service;
    this.list = list;
    
    //peer_id = Utils.getPeerID(peer, service.me);
    //reloadDialogs();
  }

  public Object getElementAt(int index) {
    return (filtered == null) ? (index < service.dialogManager.loaded.size() ? service.dialogManager.loaded.get(index) : "У вас ещё нет диалогов") : (index < filtered.size() ? filtered.get(index) : "Ничего не найдено");
  }

  public int getSize() {
    return Math.max(1, (filtered == null) ? service.dialogManager.loaded.size() : filtered.size());
  }
  
  public boolean isEmpty() {
    return (filtered == null) ? service.dialogManager.loaded.isEmpty() : filtered.isEmpty();
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
    
    if (query == null || query.length() == 0) {
      filtered = null;
      filterQuery = null;
      fireContentsChanged(this, 0, getSize() - 1);
      return;
    }
    
    filtered = new LinkedList<Dialog>();
    for (Dialog dialog : service.dialogManager.loaded) {
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
    filterQuery = query;
    fireContentsChanged(this, 0, getSize() - 1);
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
    if (!force && service.dialogManager.total > -1 && service.dialogManager.loaded.size() >= service.dialogManager.total) return;
    
    service.dialogManager.loading = true;
    service.mainServer.call(new GetDialogs(cachedData ? 0 : service.dialogManager.loaded.size(), 0, 100), new RPCCallback<TDialogs>() {
      public void done(TDialogs result) {
        if (result instanceof Dialogs) {
          Dialogs dialogs = (Dialogs) result;
          service.dialogManager.total = dialogs.dialogs.length;
          service.dialogManager.store(dialogs.dialogs, true);
          service.chatManager.store(dialogs.chats);
          service.userManager.store(dialogs.users);
          service.messageManager.store(dialogs.messages);
        } else {
          DialogsSlice dialogs = (DialogsSlice) result;
          service.dialogManager.total = dialogs.count;
          service.dialogManager.store(dialogs.dialogs, cachedData);
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
  
  public void addMessage(TMessage message) {
    service.dialogManager.addMessage(message);
    
  }
  
}
