package ru.denull.wire.model;

import ru.denull.mtproto.DataService;
import ru.denull.wire.ImagePanel;
import ru.denull.wire.Utils;
import ru.denull.wire.model.FileManager.FileLoadingCallback;
import ru.denull.wire.stub.tl.*;

import java.awt.*;
import java.util.HashMap;

public class UserManager {
    private static final String TAG = "UserManager";
    public static final String TABLE_NAME = "user";
    public static final String _ID = "_id";
    public static final String COLUMN_NAME_BODY = "body";
    public static final String COLUMN_NAME_BODY_FULL = "body_full";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_BODY + " BLOB," +
                    COLUMN_NAME_BODY_FULL + " BLOB" +
                    " )";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    DataService service;
    SQLiteDatabase db;
    public HashMap<Integer, TUser> loaded = new HashMap<Integer, TUser>();
    public HashMap<Integer, UserFull> full = new HashMap<Integer, UserFull>();

    public static final UserEmpty empty = new UserEmpty(0);

    public UserManager(DataService service, SQLiteDatabase db) {
        this.service = service;
        this.db = db;
    }

    public void store(TUser[] data) {
        for (TUser user : data) {
            store(user);
        }
    }

    public void store(TUser user) {
        ContentValues values = new ContentValues();
        values.put(_ID, user.id);
        try {
            values.put(COLUMN_NAME_BODY, user.writeToByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        loaded.put(user.id, user);
    }

    public void store(TUserFull userFull) {
        ContentValues values = new ContentValues();
        values.put(_ID, ((UserFull) userFull).user.id);
        try {
            values.put(COLUMN_NAME_BODY, ((UserFull) userFull).user.writeToByteArray());
            values.put(COLUMN_NAME_BODY_FULL, userFull.writeToByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        full.put(((UserFull) userFull).user.id, (UserFull) userFull);
    }

    public TUser get(int id) {
        TUser result = loaded.get(id);
        if (result == null) {
            Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME_BODY}, "_id = ? AND body NOT NULL", new String[]{id + ""}, null, null, null, "1");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                try {
                    result = (TUser) TL.read(cursor.getBlob(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                loaded.put(result.id, result);
            }
            cursor.close();
        }

        return (result == null) ? empty : result;
    }

    public UserFull getFull(int id) {
        UserFull result = full.get(id);
        if (result == null) {
            Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME_BODY_FULL}, "_id = ? AND body_full NOT NULL", new String[]{id + ""}, null, null, null, "1");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                try {
                    result = (UserFull) TL.read(cursor.getBlob(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                full.put(id, result);
            }
            cursor.close();
        }

        return result;
    }


    // double cache!
    //static LruCache<Integer, Bitmap> userpicCache = new LruCache<Integer, Bitmap>(300);
    static Image[] placeholders = new Image[8];

    public void getUserpic(int id, ImagePanel view, boolean big) {
        TUser user = get(id);
        if (user != null && user.photo != null && user.photo instanceof UserProfilePhoto) {
            TFileLocation loc = big ? ((UserProfilePhoto) user.photo).photo_big : ((UserProfilePhoto) user.photo).photo_small;
            if (!service.fileManager.queryImage(loc, view)) {
                view.setImage(getPlaceholder(id));
            }
        } else {
            view.setImage(getPlaceholder(id));
        }
    }

    public void getUserpic(int id, ImagePanel view, boolean big, FileLoadingCallback callback) {
        TUser user = get(id);
        if (user != null && user.photo != null && user.photo instanceof UserProfilePhoto) {
            TFileLocation loc = big ? ((UserProfilePhoto) user.photo).photo_big : ((UserProfilePhoto) user.photo).photo_small;
            if ((service.fileManager.getState(loc) & FileManager.STATE_LOADING_MASK) != FileManager.STATE_COMPLETE) {
                view.setImage(getPlaceholder(id));
                service.fileManager.query(loc, callback);
            } else {
                service.fileManager.queryImage(loc, view);
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
            case 0:
                result = Utils.getImage("user_placeholder_red.png");
                break;
            case 1:
                result = Utils.getImage("user_placeholder_green.png");
                break;
            case 2:
                result = Utils.getImage("user_placeholder_yellow.png");
                break;
            case 3:
                result = Utils.getImage("user_placeholder_blue.png");
                break;
            case 4:
                result = Utils.getImage("user_placeholder_purple.png");
                break;
            case 5:
                result = Utils.getImage("user_placeholder_pink.png");
                break;
            case 6:
                result = Utils.getImage("user_placeholder_cyan.png");
                break;
            case 7:
                result = Utils.getImage("user_placeholder_orange.png");
                break;
        }

        placeholders[index] = result;
        return result;
    }
}