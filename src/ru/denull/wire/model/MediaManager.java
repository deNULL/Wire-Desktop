package ru.denull.wire.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import ru.denull.mtproto.DataService;
import ru.denull.wire.Utils;
import tl.*;

public class MediaManager {
	private static final String TAG = "MediaManager";
  public static final String TABLE_NAME = "media";
  public static final String _ID = "_id";
  public static final String COLUMN_NAME_PEER = "peer"; // user_id or -chat_id, used for filtering
  public static final String COLUMN_NAME_BODY = "body"; // TMessageMedia
  public static final String COLUMN_NAME_TYPE = "type"; // 0 = photo, 1 = video
	public static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + TABLE_NAME + " (" +
	    		_ID + " INTEGER PRIMARY KEY," +
          COLUMN_NAME_PEER + " INTEGER," +
	    		COLUMN_NAME_BODY + " BLOB," +
          COLUMN_NAME_TYPE + " INTEGER" +
	    " )";
	public static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + TABLE_NAME;
	
	DataService service;
	SQLiteDatabase db;
	public HashMap<Integer, ArrayList<TMessage>> loaded = new HashMap<Integer, ArrayList<TMessage>>();
	
	public static final MessageEmpty empty = new MessageEmpty(0);
	
	public MediaManager(DataService service, SQLiteDatabase db) {
		this.service = service;
		this.db = db;
	}
	
	public ArrayList<TMessage> get(TInputPeer peer, boolean photo, boolean video) {
	  int peer_id = Utils.getPeerID(peer, service.me);
	  ArrayList<TMessage> result = loaded.get(peer_id);
	  if (result == null) {
	    Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_NAME_BODY }, "peer = ?", new String[] { peer_id + "" }, null, null, "_id ASC");
	    result = new ArrayList<TMessage>(cursor.getCount());
	    if (cursor.getCount() > 0) {
	      cursor.moveToFirst();
	      while (!cursor.isAfterLast()) {
	        try {
            result.add((TMessage) TL.read(cursor.getBlob(0)));
          } catch (Exception e) {
            e.printStackTrace();
          }
	        cursor.moveToNext();
	      }
	    }
	    cursor.close();
	    loaded.put(peer_id, result);
	  } else {
	    ArrayList<TMessage> copy = new ArrayList<TMessage>(result.size());
      copy.addAll(result);
      result = copy;
	  }
	  
	  return result;
	}
	
	// TODO: that's all wrong, rewrite all
	public void store(final TInputPeer peer, final TMessage[] messages) {
	  int peer_id = Utils.getPeerID(peer, service.me);
	  HashSet<Integer> exists = new HashSet<Integer>();
	  ArrayList<TMessage> result = loaded.get(peer_id);
	  if (result == null) {
	    result = new ArrayList<TMessage>(messages.length);
	  } else {
	    for (TMessage message : result) {
	      exists.add(message.id);
	    }
	  }
	  
	  final ArrayList<TMessage> adding = new ArrayList<TMessage>();
	  for (TMessage message : messages) {
	    if (message.media != null && !exists.contains(message.id)) {
        adding.add(message);
      }
	  }
	  result.addAll(adding);
	  Collections.sort(result, new Comparator<TMessage>() {
      public int compare(TMessage lhs, TMessage rhs) {
        return (lhs.id - rhs.id);
      }
    });
	  loaded.put(peer_id, result);
	  
	  // postpone saving to db
    service.threadPool.submit(new Runnable() {
      public void run() {
        for (TMessage message : adding) {
          store(peer, message);
        }
      }
    });
	}
	
	public void store(TInputPeer peer, TMessage message) {
	  int peer_id = Utils.getPeerID(peer, service.me);
	  
	  ContentValues values = new ContentValues();
    values.put(_ID, message.id);
    values.put(COLUMN_NAME_PEER, peer_id);
    try {
      values.put(COLUMN_NAME_BODY, message.writeToByteArray());
    } catch (Exception e) {
      e.printStackTrace();
    }
    values.put(COLUMN_NAME_TYPE, (message.media instanceof MessageMediaPhoto) ? 0 : 1);
    db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
	}
}
