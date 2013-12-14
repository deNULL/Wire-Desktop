package ru.denull.wire.model;

import ru.denull.mtproto.DataService;
import ru.denull.wire.stub.tl.TL;
import ru.denull.wire.stub.tl.messages.MessageEmpty;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageManager {
    private static final String TAG = "MessageManager";
    public static final String TABLE_NAME = "message";
    public static final String _ID = "_id";
    public static final String COLUMN_NAME_PEER = "peer"; // user_id or -chat_id, used for filtering
    public static final String COLUMN_NAME_BODY = "body";
    public static final String COLUMN_NAME_OUTDATED = "outdated";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_PEER + " INTEGER," +
                    COLUMN_NAME_BODY + " BLOB," +
                    COLUMN_NAME_OUTDATED + " INTEGER" +
                    " )";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    DataService service;
    SQLiteDatabase db;
    public HashMap<Integer, TMessage> loaded = new HashMap<Integer, TMessage>();

    public static final MessageEmpty empty = new MessageEmpty();

    public MessageManager(DataService service, SQLiteDatabase db) {
        this.service = service;
        this.db = db;
    }

    public void store(final TMessage[] data) {
        for (TMessage message : data) {
            loaded.put(message.id, message);
        }

        // postpone saving to db
        service.threadPool.submit(new Runnable() {
            public void run() {
                // check if the oldest message is already stored in db
                Cursor cursor = db.rawQuery("SELECT MAX(" + _ID + ") FROM " + TABLE_NAME, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    int max_id = cursor.getInt(0);
                    if (max_id < data[data.length - 1].id) { // mark the last stored message as outdated (there's gap after it)
                        ContentValues values = new ContentValues();
                        values.put(COLUMN_NAME_OUTDATED, 1);
                        db.updateWithOnConflict(TABLE_NAME, values, "_id = ?", new String[]{max_id + ""}, SQLiteDatabase.CONFLICT_IGNORE);
                    }
                }
                cursor.close();

                for (TMessage message : data) {
                    store(message);
                }
            }
        });
    }

    public void store(TMessage message) {
        if (message.id > 0) { // id = 0 => empty, id < 0 => local message (not sent yet)
            ContentValues values = new ContentValues();
            values.put(_ID, message.id);
            values.put(COLUMN_NAME_PEER, message.to_id instanceof PeerUser ?
                    (message.out ? ((PeerUser) message.to_id).user_id : message.from_id) :
                    -((PeerChat) message.to_id).chat_id);
            //values.put(COLUMN_NAME_BODY, message.writeToByteArray());
            values.put(COLUMN_NAME_OUTDATED, 0);
            db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }

        loaded.put(message.id, message);
    }

    public void store(TGeoChatMessage message) {

    }

    public TMessage get(int id) {
        TMessage result = loaded.get(id);
        if (result == null) {
            Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME_BODY}, "_id = ?", new String[]{id + ""}, null, null, null, "1");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                try {
                    result = (TMessage) TL.read(cursor.getBlob(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                loaded.put(result.id, result);
            }
            cursor.close();
        }

        return (TMessage) ((result == null) ? empty : result);
    }

    public TMessage[] getHistory(TInputPeer peer, int max_id, int limit) {
        int peer_id =
                peer instanceof InputPeerContact ? ((InputPeerContact) peer).user_id :
                        peer instanceof InputPeerForeign ? ((InputPeerForeign) peer).user_id :
                                peer instanceof InputPeerSelf ? service.me.id :
                                        peer instanceof InputPeerChat ? -((InputPeerChat) peer).chat_id : 0;
        if (peer_id == 0) return new TMessage[]{};

        Cursor cursor;
        if (max_id > 0) {
            cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME_BODY, COLUMN_NAME_OUTDATED}, "_id < ? AND peer = ?", new String[]{max_id + "", peer_id + ""}, null, null, "_id DESC", limit + "");
        } else {
            cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME_BODY, COLUMN_NAME_OUTDATED}, "peer = ?", new String[]{peer_id + ""}, null, null, "_id DESC", limit + "");
        }
        ArrayList<TMessage> result = new ArrayList<TMessage>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            if (cursor.getInt(1) == 1) break; // outdated (gap ahead), stop returning results

            try {
                result.add((TMessage) TL.read(cursor.getBlob(0)));
            } catch (Exception e) {
                e.printStackTrace();
            }

            cursor.moveToNext();
        }
        cursor.close();
        return result.toArray(new TMessage[]{});
    }
}
