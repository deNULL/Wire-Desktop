package ru.denull.wire.model;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import ru.denull.mtproto.DataService;
import ru.denull.mtproto.Server.RPCCallback;
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
		values.put(COLUMN_NAME_BODY, chat.writeToByteArray());
		db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		
		loaded.put(chat.id, chat);
		//Log.i(TAG, "Storing chat #" + chat.id + ": " + chat);
	}
	
	public void store(TChatFull chatFull) {
	  ContentValues values = new ContentValues();
    values.put(_ID, ((ChatFull) chatFull).id);
    values.put(COLUMN_NAME_BODY_FULL, chatFull.writeToByteArray());
    db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    
    full.put(((ChatFull) chatFull).id, (ChatFull) chatFull);
	}
	
	public TChat[] search(String query) {
	  Cursor cursor = db.query(TABLE_NAME, new String[]{ COLUMN_NAME_BODY }, "title LIKE ?", new String[]{ "%" + query.trim().toLowerCase() + "%" }, null, null, null);
	  TChat[] result = new TChat[cursor.getCount()];
	  int index = 0;
	  cursor.moveToFirst();
	  while (!cursor.isAfterLast()) {
	    result[index] = (TChat) TL.read(cursor.getBlob(0));
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
				result = (TChat) TL.read(cursor.getBlob(0));
				
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
        result = (ChatFull) TL.read(cursor.getBlob(0));
        
        full.put(id, result);
      }
      cursor.close();
    }
    
    return result;
	}

  /*static Bitmap[] placeholders = new Bitmap[8];
	public void getImage(int id, ImageView view, boolean big) {
    TChat chat = get(id);
    if (chat != null && chat instanceof Chat && ((Chat) chat).photo instanceof ChatPhoto) {
      if (big) {
        if (!service.fileManager.queryImage(((ChatPhoto) ((Chat) chat).photo).photo_big, view)) {
          view.setImageBitmap(getPlaceholder(c, id));
        }
      } else {
        if (!service.fileManager.queryImage(((ChatPhoto) ((Chat) chat).photo).photo_small, view)) {
          view.setImageBitmap(getPlaceholder(c, id));
        }
      }
    } else {
      view.setImageBitmap(getPlaceholder(c, id));
    }
  }
	
	public Bitmap getPlaceholder(Context c, int id) {
    int index = (id + ((id * 7) >> 4)) & 7;
    Bitmap result = placeholders[index];
    if (result != null) return result;
    
    switch (index) {
      case 0: result = BitmapFactory.decodeResource(c.getResources(), R.drawable.group_placeholder_red); break;
      case 1: result = BitmapFactory.decodeResource(c.getResources(), R.drawable.group_placeholder_green); break;
      case 2: result = BitmapFactory.decodeResource(c.getResources(), R.drawable.group_placeholder_yellow); break;
      case 3: result = BitmapFactory.decodeResource(c.getResources(), R.drawable.group_placeholder_blue); break;
      case 4: result = BitmapFactory.decodeResource(c.getResources(), R.drawable.group_placeholder_purple); break;
      case 5: result = BitmapFactory.decodeResource(c.getResources(), R.drawable.group_placeholder_pink); break;
      case 6: result = BitmapFactory.decodeResource(c.getResources(), R.drawable.group_placeholder_cyan); break;
      case 7: result = BitmapFactory.decodeResource(c.getResources(), R.drawable.group_placeholder_orange); break;
    }
    
    placeholders[index] = result;
    return result;
  }*/
}
