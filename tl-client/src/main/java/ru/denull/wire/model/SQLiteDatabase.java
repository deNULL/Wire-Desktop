package ru.denull.wire.model;

public class SQLiteDatabase {

    public static final String CONFLICT_REPLACE = null;
    public static final String CONFLICT_IGNORE = null;

    public void execSQL(String query) {
        // TODO Auto-generated method stub

    }

    public void insertWithOnConflict(String table, Object object,
                                     ContentValues values, String strategy) {
        // TODO Auto-generated method stub

    }

    public Cursor query(String tableName, String[] columns, String where,
                        String[] values, Object object, Object object2, Object object3) {
        // TODO Auto-generated method stub
        return new Cursor();
    }

    public Cursor query(String tableName, String[] columns, String where,
                        String[] values, Object object, Object object2, Object object3,
                        String limit) {
        // TODO Auto-generated method stub
        return new Cursor();
    }

    public void delete(String tableName, Object object, Object object2) {
        // TODO Auto-generated method stub

    }

    public void insert(String tableName, Object object, ContentValues values) {
        // TODO Auto-generated method stub

    }

    public void close() {
        // TODO Auto-generated method stub

    }

    public Cursor rawQuery(String string, Object object) {
        // TODO Auto-generated method stub
        return new Cursor();
    }

    public void updateWithOnConflict(String tableName, ContentValues values,
                                     String string, String[] strings, String conflictIgnore) {
        // TODO Auto-generated method stub

    }

}
