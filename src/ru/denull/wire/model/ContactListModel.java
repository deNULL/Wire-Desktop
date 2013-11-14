package ru.denull.wire.model;

import java.util.*;

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
  ArrayList<Integer> filtered = null;
  String filterQuery = null;
  String missingText = "<html>У вас нет ни одного контакта.<br/>Вы можете импортировать их в меню «Контакты».</html>";
  
  public ContactListModel(DataService service, JList list) {
    this.service = service;
    this.list = list;
    
    //peer_id = Utils.getPeerID(peer, service.me);
    //reloadDialogs();
  }
  
  public void setMissingText(String text) {
    missingText = text;
  }

  public Object getElementAt(int index) {
    return (filtered == null) ? (index < items.size() ? items.get(index) : missingText) : (index < filtered.size() ? filtered.get(index) : "Ничего не найдено");
  }

  public int getSize() {
    return Math.max(1, (filtered == null) ? items.size() : filtered.size());
  }
  
  public boolean isEmpty() {
    return (filtered == null) ? items.isEmpty() : filtered.isEmpty();
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
    
    filtered = new ArrayList<Integer>();
    for (Integer user_id : items) {
      TUser user = service.userManager.get(user_id);
      boolean matches = (user.first_name + " " + user.last_name).toLowerCase().indexOf(query) >= 0 || (user.last_name + " " + user.first_name).toLowerCase().indexOf(query) >= 0;
      
      if (matches) {
        filtered.add(user_id);
      }
    }
    filterQuery = query;
    fireContentsChanged(this, 0, getSize() - 1);
  }
  
  public void updateContents() {
    fireContentsChanged(this, 0, getSize() - 1);
  }
  
  public void updateContents(int user_id) {
    if (isEmpty()) return;
    for (int i = 0; i < getSize(); i++) {
      if ((Integer) getElementAt(i) == user_id) {
        fireContentsChanged(this, i, i);
        return;
      }
    }
  }
  
  public void add(int user_id, boolean unique) {
    if (unique) {
      for (Integer id : items) {
        if (id == user_id) {
          return;
        }
      }
    }
    
    items.add(user_id);
    sort();
    filter(filterQuery, true);
  }

  public void add(TChatParticipant[] participants) {
    if (participants == null) return;
    
    for (TChatParticipant part : participants) {
      items.add(part.user_id);
    }
    sort();
    filter(filterQuery, true);
  }
  
  public void remove(int index) {
    items.remove(index);
    sort();
    filter(filterQuery, true);
  }
  
  public void removeUser(int user_id) {
    items.remove((Integer) user_id);
    sort();
    filter(filterQuery, true);
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
        sort();
        filter(filterQuery, true);
      }
      public void error(int code, String message) {
        //Log.e(TAG, "Unable to get contacts");
      }
    });
   
  }
  
  public void sort() {
    Collections.sort(items, new Comparator<Integer>() {
      public int compare(Integer a, Integer b) {
        TUser userA = service.userManager.get(a);
        TUser userB = service.userManager.get(b);
        return (userA.last_name + " " + userA.first_name).compareTo(userB.last_name + " " + userB.first_name);
      }
    });
  }
  
}
