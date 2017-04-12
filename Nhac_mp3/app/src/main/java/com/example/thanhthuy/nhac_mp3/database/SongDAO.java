package com.example.thanhthuy.nhac_mp3.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class SongDAO {
    private static final String TAG = "SongDAO";
    private final SQLiteDatabase db;

    public SongDAO(SQLiteDatabase db) {
        super();
        this.db = db;
    }

    public long insertSong(SongEntity entity) {
        ContentValues values = new ContentValues();
        values.put(SongScheme.TITLE, entity.getTitle());
        values.put(SongScheme.ARTIST, entity.getArtist());
        values.put(SongScheme.DURATION, entity.getDuration());
        values.put(SongScheme.IMAGE, entity.getImg());
        values.put(SongScheme.ALBUM_ID, entity.getIdAlbum());
        values.put(SongScheme.STATUS, 1);
        if (db.insert(SongScheme.TABLE_SONG_NAME, null, values) == -1) {
            Log.e(TAG, "bug insert keysearch");
        } else {
            Log.e(TAG, "insert keysearch successfully");
        }
        return 0;
    }

    public ArrayList<SongEntity> getAll() {
        ArrayList<SongEntity> listSong = new ArrayList<SongEntity>();
        String query = "SELECT * FROM " + SongScheme.TABLE_SONG_NAME + " WHERE " + SongScheme.STATUS + "=1";
//
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                SongEntity entity = new SongEntity();
                entity.setId(cursor.getInt(0));
                entity.setTitle(cursor.getString(1));
                entity.setArtist(cursor.getString(2));
                entity.setDuration(cursor.getInt(3));
                entity.setImg(cursor.getString(4));
                entity.setIdAlbum(cursor.getInt(5));
                listSong.add(entity);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listSong;
    }


    public ArrayList<SongEntity> getSongByIdAlbum(int idAlbum) {
        ArrayList<SongEntity> listSong = new ArrayList<SongEntity>();
        String query = "SELECT * FROM " + SongScheme.TABLE_SONG_NAME + " WHERE "
                + SongScheme.ALBUM_ID + "='" + idAlbum + "' AND " + SongScheme.STATUS + "=1";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                SongEntity entity = new SongEntity();
                entity.setId(cursor.getInt(0));
                entity.setTitle(cursor.getString(1));
                entity.setArtist(cursor.getString(2));
                entity.setDuration(cursor.getInt(3));
                entity.setImg(cursor.getString(4));
                entity.setIdAlbum(cursor.getInt(5));
                listSong.add(entity);
            } while (cursor.moveToNext());
        }
        return listSong;
    }


    public ArrayList<SongEntity> getSongByIdPlayList(int idP) {
        ArrayList<SongEntity> listSong = new ArrayList<SongEntity>();
        String query = "SELECT * FROM " + SongScheme.TABLE_SONG_NAME + " WHERE "
                + SongScheme.ROWID + " IN( SELECT DISTINCT " + SongPlaylistScheme.ID_SONG
                + " FROM " + SongPlaylistScheme.TABLE_SONGPLAYLIST_NAME + " WHERE "
                +SongPlaylistScheme.ID_PLAYLIST+" = "+idP +")";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                SongEntity entity = new SongEntity();
                entity.setId(cursor.getInt(0));
                entity.setTitle(cursor.getString(1));
                entity.setArtist(cursor.getString(2));
                entity.setDuration(cursor.getInt(3));
                entity.setImg(cursor.getString(4));
                entity.setIdAlbum(cursor.getInt(5));
                listSong.add(entity);
            } while (cursor.moveToNext());
        }
        return listSong;
    }


    public ArrayList<SongEntity> getSongById(int idSong) {
        ArrayList<SongEntity> listSong = new ArrayList<SongEntity>();
        String query = "SELECT * FROM " + SongScheme.TABLE_SONG_NAME + " WHERE " + SongScheme.ROWID + "='" + idSong + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                SongEntity entity = new SongEntity();
                entity.setId(cursor.getInt(0));
                entity.setTitle(cursor.getString(1));
                entity.setArtist(cursor.getString(2));
                entity.setDuration(cursor.getInt(3));
                entity.setImg(cursor.getString(4));
                entity.setIdAlbum(cursor.getInt(5));
                listSong.add(entity);
            } while (cursor.moveToNext());
        }
        return listSong;
    }


    public long deleteTableKeysearch() {
        long result = 0;
        try {
            result = db.delete(SongScheme.TABLE_SONG_NAME, null, null);
            Log.e(TAG, "delete table keysearch successfully");
        } catch (Exception e) {
            Log.e(TAG, "delete table keysearch fail");
        }
        return result;
    }
}
