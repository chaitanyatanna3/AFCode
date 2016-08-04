package com.example.chaitanya.afapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseData extends SQLiteOpenHelper {

    public SQLiteDatabase db;
    public static final String DATABASE_NAME_DATA_SAVED = "Data.db";
    public static final String TABLE_NAME_NEW = "SavedData";
    public static final String TITLE_COLUMN_NAME = "title";
    public static final String DESCRIPTION_COLUMN_NAME = "description";
    public static final String FOOTER_COLUMN_NAME = "footer";
    public static final String IMAGE_COLUMN_NAME = "image";
    public static final String URL_COLUMN_NAME = "url";


    public DatabaseData(Context context){
        super(context, DATABASE_NAME_DATA_SAVED, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_NEW + " (" +
                "title text, description text, footer text, image text, url text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS " + TABLE_NAME_NEW);
        onCreate(db);
    }

    /**
     methods for the new Database
     */

    public boolean insertDataToBeSaved(String title, String description, String footer, String image, String url){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("footer", footer);
        contentValues.put("image", image);
        contentValues.put("url", url);
        db.insert(TABLE_NAME_NEW, null, contentValues);
        return true;
    }

    public int numberOfRows(){
        db = this.getReadableDatabase();
        int numofRows = (int) DatabaseUtils.queryNumEntries(db,TABLE_NAME_NEW);
        return numofRows;
    }

    public Cursor getDataFromSavedTitle(String title){
        db =  this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " +TABLE_NAME_NEW+ " WHERE "+ TITLE_COLUMN_NAME + "=" + "\'" + title + "\'" + "", null);
        return res;
    }

    public ArrayList<String> getAllData(){
        ArrayList<String> titleArray = new ArrayList<String>();
        db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME_NEW, null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            titleArray.add(res.getString(res.getColumnIndex(TITLE_COLUMN_NAME)));
            res.moveToNext();
        }
        res.close();
        db.close();
        return titleArray;
    }

    public ArrayList<String> getImage() {
        ArrayList<String> imageArray = new ArrayList<>();
        db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME_NEW, null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            imageArray.add(res.getString(res.getColumnIndex(IMAGE_COLUMN_NAME)));

            res.moveToNext();
        }
        res.close();
        db.close();
        return imageArray;
    }

    public boolean checkIsDataAlreadyInDBorNot(String title) {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_NEW + " WHERE " + TITLE_COLUMN_NAME + " =" + "\'" + title + "\'" + "", null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
