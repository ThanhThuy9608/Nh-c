package com.example.thanhthuy.nhac_mp3.layalbum;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.thanhthuy.nhac_mp3.layalbum.AlbumEntity;
import com.example.thanhthuy.nhac_mp3.layalbum.AlbumScheme;

import java.util.ArrayList;

public class AlbumDAO {
    private static final String TAG = "AlbumDAO";
    private final SQLiteDatabase db;

    public AlbumDAO(SQLiteDatabase db) {
        super();
        this.db = db;
    }

    public long insertAlbum(AlbumEntity entity) {
        ContentValues values = new ContentValues();
        values.put(AlbumScheme.NAME, entity.getName());
        String query = "INSERT OR IGNORE INTO " + AlbumScheme.TABLE_ALBUM_NAME + "(" + AlbumScheme.NAME + ") VALUES ('" + entity.getName() + "')";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        cursor.close();
        return 0;
    }

    public ArrayList<AlbumEntity> getAlbum(String type) {
            ArrayList<AlbumEntity> listAlbum = new ArrayList<AlbumEntity>();
            String query = "SELECT * FROM " + AlbumScheme.TABLE_ALBUM_NAME
                    + " WHERE " + AlbumScheme.NAME + " = '" + type + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                    do {
                        AlbumEntity entity = new AlbumEntity();
                        entity.setId(cursor.getInt(0));
                entity.setName(cursor.getString(1));
                listAlbum.add(entity);
             } while (cursor.moveToNext());
         }
        return listAlbum;
    }

    public ArrayList<AlbumEntity> getAllAlbum() {
            ArrayList<AlbumEntity> listAlbum = new ArrayList<AlbumEntity>();
            String query = "SELECT * FROM " + AlbumScheme.TABLE_ALBUM_NAME;
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()) {
            do {
            AlbumEntity entity = new AlbumEntity();
            entity.setId(cursor.getInt(0));
            entity.setName(cursor.getString(1));
            listAlbum.add(entity);
            } while (cursor.moveToNext());
            }
            return listAlbum;
            }


    public long deleteTableKeysearch() {
            long result = 0;
            try {
                result = db.delete(AlbumScheme.TABLE_ALBUM_NAME, null, null);
                Log.e(TAG, "delete table keysearch successfully");
            } catch (Exception e) {
                Log.e(TAG, "delete table keysearch fail");
            }
            return result;
    }
}
