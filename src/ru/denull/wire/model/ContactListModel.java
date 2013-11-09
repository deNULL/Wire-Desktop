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
import tl.contacts.Contacts;
import tl.contacts.GetContacts;
import tl.contacts.TContacts;
import tl.messages.*;

public class ContactListModel extends AbstractListModel {
  private static final long serialVersionUID = 4456613715657495764L;
  private DataService service;
  private JList list;
  
  boolean loading = false, loadingFromCache = false;
  boolean initializing = false;
  boolean cachedData = true;
  int total = -1;
  int loaded = 0;
  int first_id = 0;
  ArrayList<Integer> items = new ArrayList<Integer>();
  
  public ContactListModel(DataService service, JList list) {
    this.service = service;
    this.list = list;
    
    //peer_id = Utils.getPeerID(peer, service.me);
    //reloadDialogs();
  }

  public Object getElementAt(int index) {
    return items.get(index);
  }

  public int getSize() {
    return items.size();
  }
  
  public void updateContents() {
    fireContentsChanged(this, 0, getSize() - 1);
  }
  
  public void reload() {
    boolean force = true;
    final boolean cachedData = false;
    
    if (service.mainServer == null/* || !service.mainServer.transport.isConnected()*/){
      //Log.i(TAG, "Not yet connected, stop");
      return;
    }
    //Log.i(TAG, "loading = " + service.dialogManager.loading);
    if (service.contactManager.loading) return;
    
    service.mainServer.call(new GetContacts(service.contactManager.updateHash()), new RPCCallback<TContacts>() {
      public void done(TContacts result) {
        if (result instanceof Contacts) {
          items.clear();
          for (TContact contact : ((Contacts) result).contacts) {
            service.contactManager.store(
              ((Contact) contact).user_id,
              -((Contact) contact).user_id,
              ((Contact) contact).mutual); 
            items.add(contact.user_id);
          }
          service.userManager.store(((Contacts) result).users);
        }

        fireContentsChanged(list, 0, getSize() - 1);
      }
      public void error(int code, String message) {
        //Log.e(TAG, "Unable to get contacts");
      }
    });
   
  }
  
}
