package com.example.thanhthuy.nhac_mp3.layalbum;


import android.database.sqlite.SQLiteDatabase;

import com.example.thanhthuy.nhac_mp3.database.IScheme;

public class AlbumScheme implements IScheme {
	public static final String TABLE_ALBUM_NAME = "Album";
	public static final String ID = "rowid";
	public static final String NAME = "name";

	public static final String DATABASE_CREATE_ALBUM = "Create Table "
			+ TABLE_ALBUM_NAME + "(" + ID + " integer primary key, "
			 + NAME + " text unique)";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE_ALBUM);
	}
}
