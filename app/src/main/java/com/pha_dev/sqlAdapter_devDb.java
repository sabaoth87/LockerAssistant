package com.pha_dev;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Tom on 2017-04-18.
 */

/**
 * Android Application Development for Dummies
 * <p>
 * Creating you Application's SQLite Database
 * Page 276
 * <p>
 * Based on RemindersDbAdapter
 */

public class sqlAdapter_devDb {

    public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static final String KEY_ENTRY_DATE = "entryDate";
    public static final String KEY_ENTRY_SUBJECT = "entrySubject";
    public static final String KEY_ENTRY_STATUS = "entryStatus";
    public static final String KEY_ROWID = "_id";
    private static final String TAG = "PHA:: SQLDev ";
    private static final String DATABASE_NAME = "devEntries";
    private static final String DATABASE_TABLE = "devTickets";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " ("
                    + KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_TITLE + " text not null, "
                    + KEY_BODY + " text not null, "
                    + KEY_ENTRY_DATE + " text not null, "
                    + KEY_ENTRY_SUBJECT + " text not null, "
                    + KEY_ENTRY_STATUS + " text not null);";
    private final Context mCtx;
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    public sqlAdapter_devDb(Context ctx) {
        this.mCtx = ctx;
    }

    public sqlAdapter_devDb open() throws SQLException {
        Log.v(TAG, "Opening an SQLite Adapter - " + DATABASE_TABLE);
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        Log.v(TAG, "Closing an SQLite Adapter - " + DATABASE_TABLE);
        mDbHelper.close();
    }

    public long createEntry(String title,
                            String body,
                            String entryDate,
                            String entrySubject,
                            String entryStatus) {
        ContentValues initialValues = new ContentValues();
        Log.v(TAG, "Adding an entry... - " + DATABASE_TABLE);
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_BODY, body);
        initialValues.put(KEY_ENTRY_DATE, entryDate);
        initialValues.put(KEY_ENTRY_SUBJECT, entrySubject);
        initialValues.put(KEY_ENTRY_STATUS, entryStatus);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }


    public boolean deleteEntry(long rowId) {
        Log.v(TAG, "Deleting an entry... - " + DATABASE_TABLE);
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor fetchAllEntries() {
        Log.v(TAG, "Fetching all entries - " + DATABASE_TABLE);
        return mDb.query(DATABASE_TABLE, new String[]{
                KEY_ROWID, KEY_TITLE, KEY_BODY, KEY_ENTRY_DATE,
                KEY_ENTRY_SUBJECT}, null, null, null, null, null, null);
    }

    public Cursor fetchEntry(long rowId) throws SQLException {
        Log.v(TAG, "Fetching entry " + rowId + "- " + DATABASE_TABLE);
        Cursor mCursor =
                mDb.query(true, DATABASE_TABLE, new String[]{
                        KEY_ROWID, KEY_TITLE, KEY_BODY, KEY_ENTRY_DATE,
                        KEY_ENTRY_SUBJECT, KEY_ENTRY_STATUS}, KEY_ROWID +
                        "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateEntry(long rowId,
                               String title,
                               String body,
                               String entryDate,
                               String entrySubject,
                               String status) {
        ContentValues args = new ContentValues();
        Log.v(TAG, "Updating entry " + rowId + " - " + DATABASE_TABLE);
        args.put(KEY_TITLE, title);
        args.put(KEY_BODY, body);
        args.put(KEY_ENTRY_DATE, entryDate);
        args.put(KEY_ENTRY_SUBJECT, entrySubject);
        args.put(KEY_ENTRY_STATUS, status);

        return
                mDb.update(DATABASE_TABLE, args,
                        KEY_ROWID + "=" + rowId,
                        null) > 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            Log.v(TAG, "About to create " + DATABASE_TABLE + " now");
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            /**
             * Not used at this point, but can be implemented
             * in the future with ALTER scripts
             */
        }

    }
}
