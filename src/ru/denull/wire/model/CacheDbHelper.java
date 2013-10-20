package ru.denull.wire.model;

public class CacheDbHelper {
	private static final String TAG = "CacheDbHelper";
	public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "cache.db";

  public CacheDbHelper() {
    //super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }
  
  public static void setup(SQLiteDatabase db) {
    db.execSQL(DialogManager.SQL_CREATE_ENTRIES);
    db.execSQL(ChatManager.SQL_CREATE_ENTRIES);
    db.execSQL(UserManager.SQL_CREATE_ENTRIES);
    db.execSQL(ContactManager.SQL_CREATE_ENTRIES);
    db.execSQL(MessageManager.SQL_CREATE_ENTRIES);
    db.execSQL(MediaManager.SQL_CREATE_ENTRIES);
  }
  public static void reset(SQLiteDatabase db) {
    db.execSQL(DialogManager.SQL_DELETE_ENTRIES);
    db.execSQL(ChatManager.SQL_DELETE_ENTRIES);
    db.execSQL(UserManager.SQL_DELETE_ENTRIES);
    db.execSQL(ContactManager.SQL_DELETE_ENTRIES);
    db.execSQL(MessageManager.SQL_DELETE_ENTRIES);
    db.execSQL(MediaManager.SQL_DELETE_ENTRIES);
    setup(db);
  }
  
  public void onCreate(SQLiteDatabase db) {
    setup(db);
  }

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		reset(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    onUpgrade(db, oldVersion, newVersion);
	}

  public SQLiteDatabase getWritableDatabase() {
    return new SQLiteDatabase();
  }
}
