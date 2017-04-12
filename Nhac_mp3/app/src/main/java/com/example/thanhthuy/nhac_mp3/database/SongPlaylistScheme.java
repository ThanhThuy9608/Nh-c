package com.example.thanhthuy.nhac_mp3.database;


import android.database.sqlite.SQLiteDatabase;

public class SongPlaylistScheme implements IScheme {
	public static final String TABLE_SONGPLAYLIST_NAME = "SongPlaylist";
	public static final String ID = "rowid";
	public static final String ID_SONG = "id_song";
	public static final String ID_PLAYLIST = "id_playlist";

	public static final String DATABASE_CREATE_PLAYLIST = "Create Table "
			+ TABLE_SONGPLAYLIST_NAME + "(" + ID + " integer primary key, "
			+ ID_SONG + " integer, " + ID_PLAYLIST + " integer)";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE_PLAYLIST);
	}

}
