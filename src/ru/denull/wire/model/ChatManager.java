package ru.denull.wire.model;

import java.awt.Image;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import ru.denull.mtproto.DataService;
import ru.denull.mtproto.Server.RPCCallback;
import ru.denull.wire.ImagePanel;
import ru.denull.wire.Utils;
import tl.*;

public class ChatManager {
	private static final String TAG = "ChatManager";
  public static final String TABLE_NAME = "chat";
  public static final String _ID = "_id";
  public static final String COLUMN_NAME_TITLE = "title";
  public static final String COLUMN_NAME_BODY = "body";
  public static final String COLUMN_NAME_BODY_FULL = "body_full";
	public static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + TABLE_NAME + " (" +
	    		_ID + " INTEGER PRIMARY KEY," +
          COLUMN_NAME_TITLE + " TEXT," +
	    		COLUMN_NAME_BODY + " BLOB," +
          COLUMN_NAME_BODY_FULL + " BLOB" +
	    " )";
	public static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + TABLE_NAME;
	
	DataService service;
	SQLiteDatabase db;
	public HashMap<Integer, TChat> loaded = new HashMap<Integer, TChat>();
	public HashMap<Integer, ChatFull> full = new HashMap<Integer, ChatFull>();
	
	public ChatManager(DataService service, SQLiteDatabase db) {
		this.service = service;
		this.db = db;
	}
	
	public void store(TChat[] data) {
		for (TChat chat : data) {
			store(chat);
		}
	}
	
	public void store(TChat chat) {
		ContentValues values = new ContentValues();
		values.put(_ID, chat.id);
		values.put(COLUMN_NAME_TITLE, chat instanceof Chat ? ((Chat) chat).title.trim().toLowerCase() : "");
		try {
      values.put(COLUMN_NAME_BODY, chat.writeToByteArray());
    } catch (Exception e) {
      e.printStackTrace();
    }
		db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		
		loaded.put(chat.id, chat);
		//Log.i(TAG, "Storing chat #" + chat.id + ": " + chat);
	}
	
	public void store(TChatFull chatFull) {
	  ContentValues values = new ContentValues();
    values.put(_ID, ((ChatFull) chatFull).id);
    try {
      values.put(COLUMN_NAME_BODY_FULL, chatFull.writeToByteArray());
    } catch (Exception e) {
      e.printStackTrace();
    }
    db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    
    full.put(((ChatFull) chatFull).id, (ChatFull) chatFull);
	}
	
	public TChat[] search(String query) {
	  Cursor cursor = db.query(TABLE_NAME, new String[]{ COLUMN_NAME_BODY }, "title LIKE ?", new String[]{ "%" + query.trim().toLowerCase() + "%" }, null, null, null);
	  TChat[] result = new TChat[cursor.getCount()];
	  int index = 0;
	  cursor.moveToFirst();
	  while (!cursor.isAfterLast()) {
	    try {
        result[index] = (TChat) TL.read(cursor.getBlob(0));
      } catch (Exception e) {
        e.printStackTrace();
      }
	    cursor.moveToNext();
	    index++;
	  }
	  cursor.close();
	  return result;
	}
	
	public TChat get(int id) {
		TChat result = loaded.get(id);
		if (result == null) {
			Cursor cursor = db.query(TABLE_NAME, new String[]{ COLUMN_NAME_BODY }, "_id = ? AND body NOT NULL", new String[]{ id + "" }, null, null, null, "1");
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				try {
          result = (TChat) TL.read(cursor.getBlob(0));
        } catch (Exception e) {
          e.printStackTrace();
        }
				
				loaded.put(result.id, result);
			}
			cursor.close();
		}
		
		return result;
	}
	
	public ChatFull getFull(int id) {
	  ChatFull result = full.get(id);
    if (result == null) {
      Cursor cursor = db.query(TABLE_NAME, new String[]{ COLUMN_NAME_BODY_FULL }, "_id = ? AND body_full NOT NULL", new String[]{ id + "" }, null, null, null, "1");
      if (cursor.getCount() > 0) {
        cursor.moveToFirst();
        try {
          result = (ChatFull) TL.read(cursor.getBlob(0));
        } catch (Exception e) {
          e.printStackTrace();
        }
        
        full.put(id, result);
      }
      cursor.close();
    }
    
    return result;
	}

  static Image[] placeholders = new Image[8];
  public void getImage(int id, ImagePanel view, boolean big) {
    TChat chat = get(id);
    if (chat != null && chat instanceof Chat && ((Chat) chat).photo instanceof ChatPhoto) {
      if (big) {
        if (!service.fileManager.queryImage(((ChatPhoto) ((Chat) chat).photo).photo_big, view)) {
          view.setImage(getPlaceholder(id));
        }
      } else {
        if (!service.fileManager.queryImage(((ChatPhoto) ((Chat) chat).photo).photo_small, view)) {
          view.setImage(getPlaceholder(id));
        }
      }
    } else {
      view.setImage(getPlaceholder(id));
    }
  }
  
  public Image getPlaceholder(int id) {
    int index = id % 8;
    Image result = placeholders[index];
    if (result != null) return result;
    
    switch (index) {
      case 0: result = Utils.getImage("group_placeholder_red.png"); break;
      case 1: result = Utils.getImage("group_placeholder_green.png"); break;
      case 2: result = Utils.getImage("group_placeholder_yellow.png"); break;
      case 3: result = Utils.getImage("group_placeholder_blue.png"); break;
      case 4: result = Utils.getImage("group_placeholder_purple.png"); break;
      case 5: result = Utils.getImage("group_placeholder_pink.png"); break;
      case 6: result = Utils.getImage("group_placeholder_cyan.png"); break;
      case 7: result = Utils.getImage("group_placeholder_orange.png"); break;
    }
    
    placeholders[index] = result;
    return result;
  }
}
