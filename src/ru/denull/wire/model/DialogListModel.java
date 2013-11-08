package ru.denull.wire.model;

import java.util.LinkedList;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.SwingUtilities;

import ru.denull.mtproto.DataService;
import ru.denull.mtproto.Server;
import ru.denull.mtproto.Server.RPCCallback;
import ru.denull.wire.Utils;
import tl.TInputPeer;
import tl.TLObject;
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
  
  public DialogListModel(DataService service, JList list) {
    this.service = service;
    this.list = list;
    
    //peer_id = Utils.getPeerID(peer, service.me);
    //reloadDialogs();
  }

  public Object getElementAt(int index) {
    return service.dialogManager.loaded.get(index);
  }

  public int getSize() {
    return service.dialogManager.loaded.size();
  }
  
  public void reloadDialogs() {
    boolean force = true;
    final boolean cachedData = false;
    
    if (service.mainServer == null || !service.mainServer.transport.isConnected()){
      //Log.i(TAG, "Not yet connected, stop");
      return;
    }
    //Log.i(TAG, "loading = " + service.dialogManager.loading);
    if (service.dialogManager.loading) return;
    if (!force && service.dialogManager.total > -1 && service.dialogManager.loaded.size() >= service.dialogManager.total) return;
    
    service.dialogManager.loading = true;
    service.mainServer.call(new GetDialogs(cachedData ? 0 : service.dialogManager.loaded.size(), 0, 30), new RPCCallback<TDialogs>() {
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
        fireContentsChanged(list, 0, service.dialogManager.loaded.size() - 1);
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
