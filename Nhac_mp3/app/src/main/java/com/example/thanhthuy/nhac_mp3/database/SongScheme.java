package com.example.thanhthuy.nhac_mp3.database;

import android.database.sqlite.SQLiteDatabase;

public class SongScheme implements IScheme {
    public static final String TABLE_SONG_NAME = "Song";
    public static final String ROWID = "rowid";
    public static final String TITLE = "title";
    public static final String ARTIST = "artist";
    public static final String DURATION = "duration";
    public static final String IMAGE = "image";
    public static final String ALBUM_ID = "album_id";
    public static final String STATUS = "status";


    public static final String DATABASE_CREATE_SONG = "Create Table "
            + TABLE_SONG_NAME + "(" + ROWID + " integer PRIMARY KEY, "
            + TITLE + " text, " + ARTIST + " text, " + DURATION + " integer, " + IMAGE + " text, " + ALBUM_ID + " integer, " + STATUS + " integer)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SONG);
    }
}
