package ru.denull.wire.model;

import java.nio.ByteBuffer;
import java.util.*;

import ru.denull.mtproto.DataService;
import ru.denull.wire.Utils;
import tl.*;

public class DialogManager {
	private static final String TAG = "DialogManager";
  public static final String TABLE_NAME = "dialog";
  public static final String _ID = "_id";
  public static final String COLUMN_NAME_PEER = "peer";
  public static final String COLUMN_NAME_TOP_MESSAGE = "top_message";
  public static final String COLUMN_NAME_UNREAD_COUNT = "unread_count";
	public static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + TABLE_NAME + " (" +
	    		_ID + " INTEGER PRIMARY KEY," +
	    		COLUMN_NAME_PEER + " INTEGER," +
	    		COLUMN_NAME_TOP_MESSAGE + " INTEGER," +
	    		COLUMN_NAME_UNREAD_COUNT + " INTEGER" +
	    " )";
	public static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + TABLE_NAME;
	
	public DataService service;
	SQLiteDatabase db;
	public LinkedList<Dialog> loaded = new LinkedList<Dialog>();
	public int total = -1;
	public boolean loading = false;
	
	public static final Dialog empty = new Dialog(new PeerUser(0), 0, 0);
	
	public DialogManager(DataService service, SQLiteDatabase db) {
		this.service = service;
		this.db = db;
    load();
	}
	
	public Dialog get(int index) {
		if (index < loaded.size()) {
			return loaded.get(index);
		}
		
		return empty;
		
		/*if (total > -1 && index >= total) {
			return empty;
		}
	
		if (index < loaded.size()) {
			return loaded.get(index);
		} else {
			return empty;
		}*/
	}
	
	public void addMessage(TMessage message) {
	  int index = 0;
	  for (Dialog dialog : loaded) {
	    if ((dialog.peer instanceof PeerUser && message.to_id instanceof PeerUser && ((PeerUser) dialog.peer).user_id == message.from_id) ||
	        (dialog.peer instanceof PeerChat && message.to_id instanceof PeerChat && ((PeerChat) dialog.peer).chat_id == ((PeerChat) message.to_id).chat_id)) {
	      break; 
	    }
	    index++;
	  }
	  
	  Dialog dialog;
	  if (index < loaded.size()) {
	    if (message.id > 0 && loaded.get(index).top_message > message.id) return;
      dialog = loaded.remove(index);
      dialog.top_message = message.id;
      if (!message.out) {
        dialog.unread_count++;
      }
	  } else {
	    dialog = new Dialog(message.to_id instanceof PeerChat ? message.to_id : new PeerUser(message.from_id), message.id, 1);
	  }
	  
	  loaded.addFirst(dialog);
	  // TODO: store at DB
	}
	
	public void resetUnread(TInputPeer peer) {
	  int peer_id = Utils.getPeerID(peer, service.me);
	  for (Dialog dialog : loaded) {
      if ((dialog.peer instanceof PeerUser && ((PeerUser) dialog.peer).user_id == peer_id) ||
          (dialog.peer instanceof PeerChat && ((PeerChat) dialog.peer).chat_id == -peer_id)) {
        dialog.unread_count = 0;
        break;
      }
    }
	}
	
	public void load() {
    Cursor cursor = db.query(TABLE_NAME, new String[]{ COLUMN_NAME_PEER, COLUMN_NAME_TOP_MESSAGE, COLUMN_NAME_UNREAD_COUNT }, null, null, null, null, null, "30");
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      int peer = cursor.getInt(0);
      
      loaded.add(new Dialog((peer > 0) ? new PeerUser(peer) : new PeerChat(-peer), cursor.getInt(1), cursor.getInt(2)));
      cursor.moveToNext();
    }
    cursor.close();
	}
	
	public void store(TDialog[] dialogs, boolean reset) {
		if (reset) {
			db.delete(TABLE_NAME, null, null);
			loaded.clear();
		}
		
		for (TDialog dialog : dialogs) {
			ContentValues values = new ContentValues();
			values.put(COLUMN_NAME_PEER, ((Dialog) dialog).peer instanceof PeerUser ? 
					((PeerUser) ((Dialog) dialog).peer).user_id :
					-((PeerChat) ((Dialog) dialog).peer).chat_id);
			values.put(COLUMN_NAME_TOP_MESSAGE, ((Dialog) dialog).top_message);
			values.put(COLUMN_NAME_UNREAD_COUNT, ((Dialog) dialog).unread_count);
			db.insert(TABLE_NAME, null, values);
			
			loaded.add((Dialog) dialog);
		}
	}
}
