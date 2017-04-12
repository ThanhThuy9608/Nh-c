package com.example.thanhthuy.nhac_mp3.database;


import android.database.sqlite.SQLiteDatabase;

public class PlaylistScheme implements IScheme {
	public static final String TABLE_PLAYLIST_NAME = "Playlist";
	public static final String ID = "rowid";
	public static final String NAME = "name";
	public static final String STATTUS = "status";

	public static final String DATABASE_CREATE_PLAYLIST = "Create Table "
			+ TABLE_PLAYLIST_NAME + "(" + ID + " integer primary key, "
			 + NAME + " text unique, "+STATTUS+" integer)";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE_PLAYLIST);
	}

}
