package com.mal.walid.moviewalid.SqlLight;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Khaled on 2017-06-07.
 */

public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {
    public SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String DATABASE_NAME = "MoviesDB.db";
    private static final int DATABASE_VERSION = 1;
    String s;


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MOVIE_DATA = "CREATE TABLE " +
                MovieDataContract.MovieDataEntity.TABLE_NAME + "(" +
                MovieDataContract.MovieDataEntity.Movie_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieDataContract.MovieDataEntity.Movie_Name + " TEXT NOT NULL " +
                MovieDataContract.MovieDataEntity.Overview_text + " TEXT NOT NULL " +
                MovieDataContract.MovieDataEntity.image_film + " TEXT NOT NULL " +
                MovieDataContract.MovieDataEntity.Img_poster + " TEXT NOT NULL " +
                MovieDataContract.MovieDataEntity.publish_time + " NUMERIC NOT NULL " +
                MovieDataContract.MovieDataEntity.Movie_rate + " INTEGER NOT NULL " + ");";

        db.execSQL(SQL_CREATE_MOVIE_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP ATBLE IF EXISTS " + MovieDataContract.MovieDataEntity.TABLE_NAME);
        onCreate(db);

    }

    public void insert(String name,int id,int rate, String overview,String imgUrl,String time)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();

        contentValues.put(MovieDataContract.MovieDataEntity.Movie_Name, name);
        contentValues.put(MovieDataContract.MovieDataEntity.Movie_ID, id);
        contentValues.put(MovieDataContract.MovieDataEntity.Overview_text,overview);
        contentValues.put(MovieDataContract.MovieDataEntity.Img_poster,imgUrl);
        contentValues.put(MovieDataContract.MovieDataEntity.publish_time,time);
        contentValues.put(MovieDataContract.MovieDataEntity.Movie_rate,rate);

        db.insert("MoviesDB", null, contentValues);
    }

}

