package com.example.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contact.db";

    public static final String TABLE_CONTACTS = "contacts";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PHOTO = "photo";


    public static final String DATABASE_DROP = "DROP TABLE IF EXISTS " + TABLE_CONTACTS;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_CONTACTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRST_NAME + " TEXT, " +
                COLUMN_LAST_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_PHOTO + " INTEGER " +
                ");";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { recreateDb(); }

    public int addContact(User contact) {
        ContentValues contentValues = GetContentValues(contact);
        SQLiteDatabase writableDatabase = getWritableDatabase();
        final long id = writableDatabase.insert(TABLE_CONTACTS, null, contentValues);
        return (int) id;
    }


    public void updateContact(User contact) {
        ContentValues contentValues = GetContentValues(contact);
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.update(TABLE_CONTACTS, contentValues, COLUMN_ID + "=" + contact.getId(), null);
    }

    public ArrayList<User> getAllData(ArrayList<User> list) {


        SQLiteDatabase db = getWritableDatabase();


        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + TABLE_CONTACTS,null);

            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                User contactItem = new User();
                contactItem.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                contactItem.setFirst_name(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
                contactItem.setLast_name(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
                contactItem.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                contactItem.setPhone_number(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
                contactItem.setPhoto_user(cursor.getInt(cursor.getColumnIndex(COLUMN_PHOTO)));

                list.add(contactItem);
            }
            cursor.close();
        }

        return list;
    }


    public void deleteContact(final int _id) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.execSQL("DELETE FROM " + TABLE_CONTACTS + " WHERE " + COLUMN_ID + "=\"" + _id + "\";");
    }



    private ContentValues GetContentValues(User contact) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME, contact.getFirst_name());
        contentValues.put(COLUMN_LAST_NAME, contact.getLast_name());
        contentValues.put(COLUMN_EMAIL, contact.getEmail());
        contentValues.put(COLUMN_PHONE, contact.getPhone_number());
        contentValues.put(COLUMN_PHOTO, contact.getPhoto_user());
        return contentValues;
    }

    public void recreateDb() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(DATABASE_DROP);
        onCreate(db);
    }



}
