package com.example.thanhthuy.nhac_mp3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database {

	public static final String TAG = "DBAdapter";
	private SQLiteDatabase sqliteDb;

	private SQLiteOpenHelper dbHelper;

	private static final String DATABASE_NAME = "DBMUSIC";
	private static final int DATABASE_VERSION = 1;

	public Database(Context ctx) {
		this.dbHelper = new DBAdapterHelper(ctx);
	}

	public static Database create(Context ctx) {
		return new Database(ctx);
	}

	public synchronized SQLiteDatabase open() {
		if (sqliteDb == null || !sqliteDb.isOpen()) {
			sqliteDb = dbHelper.getWritableDatabase();
		}
		return sqliteDb;
	}

	public void close() {
		if (sqliteDb != null && sqliteDb.isOpen()) {
			sqliteDb.close();
		}

	}

	private static class DBAdapterHelper extends SQLiteOpenHelper {

		public DBAdapterHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			SchemeFactory schemeFactory = new SchemeFactory();

			IScheme song = schemeFactory.getScheme(EScheme.Song);
			song.onCreate(db);

			IScheme album = schemeFactory.getScheme(EScheme.Album);
			album.onCreate(db);

			IScheme playlist = schemeFactory.getScheme(EScheme.PlayList);
			playlist.onCreate(db);

			IScheme songPlaylist = schemeFactory.getScheme(EScheme.Song_Playlist);
			songPlaylist.onCreate(db);

			Log.d(TAG,"Create DB Succes");
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}

	}

}
