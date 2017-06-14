package com.mal.walid.moviewalid.SqlLite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mal.walid.moviewalid.model.MovieObj;

import java.util.ArrayList;

/**
 * Created by Khaled on 2017-06-07.
 */

public class SQLite extends android.database.sqlite.SQLiteOpenHelper {
    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String DATABASE_NAME = "MoviesDB.db";
    private static final int DATABASE_VERSION = 1;

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MOVIE_DATA = "CREATE TABLE " +
                MovieDataContract.MovieDataEntity.TABLE_NAME + "(" +
                MovieDataContract.MovieDataEntity.Movie_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieDataContract.MovieDataEntity.Movie_Name + " TEXT NOT NULL, " +
                MovieDataContract.MovieDataEntity.Overview_text + " TEXT NOT NULL, " +
                MovieDataContract.MovieDataEntity.image_film + " TEXT NOT NULL, " +
                MovieDataContract.MovieDataEntity.Img_poster + " TEXT NOT NULL, " +
                MovieDataContract.MovieDataEntity.publish_time + " NUMERIC NOT NULL, " +
                MovieDataContract.MovieDataEntity.Movie_rate + " INTEGER NOT NULL " + ");";

        db.execSQL(SQL_CREATE_MOVIE_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP ATBLE IF EXISTS " + MovieDataContract.MovieDataEntity.TABLE_NAME);
        onCreate(db);

    }

    public void insert(String name,int id,int rate, String overview,String imgPosterUrl,String imgUrl,String time)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();

        contentValues.put(MovieDataContract.MovieDataEntity.Movie_Name, name);
        contentValues.put(MovieDataContract.MovieDataEntity.Movie_ID, id);
        contentValues.put(MovieDataContract.MovieDataEntity.Overview_text,overview);
        contentValues.put(MovieDataContract.MovieDataEntity.Img_poster,imgPosterUrl);
        contentValues.put(MovieDataContract.MovieDataEntity.image_film,imgUrl);
        contentValues.put(MovieDataContract.MovieDataEntity.publish_time,time);
        contentValues.put(MovieDataContract.MovieDataEntity.Movie_rate,rate);

        db.insert(MovieDataContract.MovieDataEntity.TABLE_NAME, null, contentValues);
    }

    public ArrayList getAllMovies() {
        ArrayList Movies = new ArrayList();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT  * FROM MovieData", null);
        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            MovieObj obj = new MovieObj();

            obj.setMovie_id(Integer.parseInt(cursor.getString(0)));
            obj.setMovie_name(cursor.getString(1));
            obj.setOverview_text(cursor.getString(2));
            obj.setImage_film(cursor.getString(3));
            obj.setImg_poster(cursor.getString(4));
            obj.setPublish_time(cursor.getString(5));
            obj.setMovie_rate(Integer.parseInt(cursor.getString(6)));

            Movies.add(obj);
        }

        return Movies;
    }

    public ArrayList getMovie(int id) {
        ArrayList Movies = new ArrayList();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT  * FROM MovieData WHERE " + MovieDataContract.MovieDataEntity.Movie_ID + "=" + id, null);
        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            MovieObj obj = new MovieObj();

            obj.setMovie_id(Integer.parseInt(cursor.getString(0)));
            obj.setMovie_name(cursor.getString(1));
            obj.setOverview_text(cursor.getString(2));
            obj.setImage_film(cursor.getString(3));
            obj.setImg_poster(cursor.getString(4));
            obj.setPublish_time(cursor.getString(5));
            obj.setMovie_rate(Integer.parseInt(cursor.getString(6)));

            Movies.add(obj);
        }

        return Movies;
    }

    public MovieObj getMovieData(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ MovieDataContract.MovieDataEntity.TABLE_NAME + " WHERE "
                + MovieDataContract.MovieDataEntity.Movie_ID + " = " + MovieDataContract.MovieDataEntity.Movie_ID + " LIKE  '"+ id +"%'", null);

        MovieObj obj = new MovieObj();

        while (cursor.moveToNext()) {

            obj.setMovie_id(Integer.parseInt(cursor.getString(0)));
            obj.setMovie_name(cursor.getString(1));
            obj.setOverview_text(cursor.getString(2));
            obj.setImage_film(cursor.getString(3));
            obj.setImg_poster(cursor.getString(4));
            obj.setPublish_time(cursor.getString(5));
            obj.setMovie_rate(Integer.parseInt(cursor.getString(6)));
        }

        return obj;
    }
}
