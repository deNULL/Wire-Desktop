package ru.denull.wire.model;

import ru.denull.mtproto.DataService;
import ru.denull.wire.stub.tl.TUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ContactManager {
    private static final String TAG = "ContactManager";
    public static final String TABLE_NAME = "contact";
    public static final String _ID = "_id"; // = client_id if it exists on this device, else -user_id
    public static final String COLUMN_NAME_USER_ID = "user_id";
    public static final String COLUMN_NAME_MUTUAL = "flags";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_USER_ID + " INTEGER," +
                    COLUMN_NAME_MUTUAL + " INTEGER" +
                    " )";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DataService service;
    SQLiteDatabase db;
    public HashMap<Integer, Integer> loaded = new HashMap<Integer, Integer>(); // client_id (or -user_id) => user_id
    public String hash = "";
    public HashMap<Integer, ClientContact> local = new HashMap<Integer, ClientContact>(); // client_id => ClientContact
    public boolean loading = false;

    public static final ClientContact empty = new ClientContact(0, "", "", "");

    public static class ClientContact implements Comparable<ClientContact> {
        public long client_id;
        public String phone;
        public String first_name, last_name;

        public ClientContact(long client_id, String phone, String first_name, String last_name) {
            super();
            this.client_id = client_id;
            this.phone = phone;
            this.first_name = first_name;
            this.last_name = last_name;
        }

        // order by last names
        public int compareTo(ClientContact another) {
            return (last_name + " " + first_name).compareTo(another.last_name + " " + another.first_name);
        }
    }

    public ContactManager(DataService service, SQLiteDatabase db) {
        this.service = service;
        this.db = db;
        //this.cr = service.getContentResolver();
    }

    public void load() {
        loaded.clear();

        Cursor cursor = db.query(TABLE_NAME, new String[]{_ID, COLUMN_NAME_USER_ID}, null, null, null, null, null);
        cursor.moveToFirst();


        while (!cursor.isAfterLast()) {
            int client_id = cursor.getInt(0);
            int user_id = cursor.getInt(1);

            loaded.put(client_id, user_id);
            cursor.moveToNext();
        }
        cursor.close();

        updateHash();
    }

    public String updateHash() {
        Integer[] tmp = loaded.values().toArray(new Integer[loaded.size()]);
        int[] ids = new int[tmp.length];
        for (int i = 0; i < loaded.size(); i++) {
            ids[i] = tmp[i];
        }
        Arrays.sort(ids);
        String joined = "";
        for (int id : ids) {
            if (joined.length() > 0) joined += ",";
            joined += id;
        }
        try { // calc hash
            MessageDigest md = MessageDigest.getInstance("MD5");
            hash = "";
            for (byte b : md.digest(joined.getBytes())) {
                hash += ((b & 0xff) < 16 ? "0" : "") + Integer.toHexString(b & 0xff);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    public void clear() {
        db.delete(TABLE_NAME, null, null);

        loaded.clear();
    }

    public void store(int user_id, int client_id, boolean mutual) {
        ContentValues values = new ContentValues();
        values.put(_ID, client_id);
        values.put(COLUMN_NAME_USER_ID, user_id);
        values.put(COLUMN_NAME_MUTUAL, mutual ? 1 : 0);
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        loaded.put(client_id, user_id);
    }

    public int get(int client_id) {
        Integer result = loaded.get(client_id);
        if (result == null) {
            Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME_USER_ID}, "_id = ?", new String[]{client_id + ""}, null, null, null, "1");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                result = cursor.getInt(0);

                loaded.put(client_id, result);
            }
        }

        return (result == null) ? 0 : result;
    }

    public TUser[] search(String query) {
        ArrayList<TUser> result = new ArrayList<TUser>();
        query = query.trim().toLowerCase();
        for (int i : loaded.values()) {
            TUser user = service.userManager.get(i);
            String name = user.first_name + " " + user.last_name;
            if (name.trim().toLowerCase().indexOf(query) > -1) {
                result.add(user);
            }
        }
        return result.toArray(new TUser[]{});
    }

    public ClientContact getLocal(int client_id) {
        ClientContact result = local.get(client_id);

        return (result == null) ? empty : result;
    }

    public void loadLocal() {
    /*Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, new String[] {
          ContactsContract.Contacts._ID,
  		ContactsContract.CommonDataKinds.Phone.NUMBER,
  		ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
  		ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME
    }, null, null, null);
    int i_id = cur.getColumnIndex(ContactsContract.Contacts._ID);
    int i_phone = cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
    int i_first_name = cur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
    int i_last_name = cur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME);
    
    if (cur.getCount() > 0) {
      cur.moveToFirst();
    	while (!cur.isAfterLast()) {
    		int _id = cur.getInt(i_id);
        local.put(_id, new ClientContact(
        	_id,
        	cur.getString(i_phone),
        	cur.getString(i_first_name),
        	cur.getString(i_last_name)
        ));
        cur.moveToNext();
    	}
    }
    cur.close();*/
    }
}
