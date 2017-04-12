package com.example.thanhthuy.nhac_mp3.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.thanhthuy.nhac_mp3.adapter.PlayListEntity;

import java.util.ArrayList;

public class PlayListDAO {
    private static final String TAG = "PlayListDAO";
    private final SQLiteDatabase db;

    public PlayListDAO(SQLiteDatabase db) {
        super();
        this.db = db;
    }

    public long insertPlayList(String name) {

//        if(db.insert(PlaylistScheme.TABLE_PLAYLIST_NAME, null, values) == -1){
//            Log.e(TAG, "bug insert Playlist");
//        } else {
//            Log.e(TAG, "insert Playlist successfully");
//        }

        String query = "INSERT OR IGNORE INTO " + PlaylistScheme.TABLE_PLAYLIST_NAME + "(" + PlaylistScheme.NAME + ","+ PlaylistScheme.STATTUS+ ") VALUES ('" + name + "', 1 )";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        cursor.close();

        Log.e(TAG, query);
        return 0;
    }
    public ArrayList<PlayListEntity> getAllPlayList(){
        ArrayList<PlayListEntity> listAlbum = new ArrayList<PlayListEntity>();
        String query = "SELECT * FROM " + PlaylistScheme.TABLE_PLAYLIST_NAME+ " WHERE " +PlaylistScheme.STATTUS+" = 1" ;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                PlayListEntity entity = new PlayListEntity();
                entity.setId(cursor.getInt(0));
                entity.setName(cursor.getString(1));
                entity.setStatus(cursor.getInt(2));
                listAlbum.add(entity);
            } while (cursor.moveToNext());
        }

        Log.e(TAG, query);
        return listAlbum;
    }


    public int getIdByName(String name){
        int rowId;
        ArrayList<PlayListEntity> listAlbum = new ArrayList<PlayListEntity>();
        String query = "SELECT "+PlaylistScheme.ID+" FROM " + PlaylistScheme.TABLE_PLAYLIST_NAME+
                " WHERE " +PlaylistScheme.NAME+" ='"+name+"'" ;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
//            do {
//                PlayListEntity entity = new PlayListEntity();
//                entity.setId(cursor.getInt(0));
//                entity.setName(cursor.getString(1));
//                entity.setStatus(cursor.getInt(2));
//                listAlbum.add(entity);
//            } while (cursor.moveToNext());
            rowId = cursor.getInt(0);

        Log.e(TAG, query +"| | RowID: "+ rowId);
        return rowId;
    }




    public long changeStatusById(String id) {
        ContentValues values = new ContentValues();
        values.put(PlaylistScheme.NAME, id);

        String query = "UPDATE " + PlaylistScheme.TABLE_PLAYLIST_NAME + " SET "+PlaylistScheme.STATTUS+" = 0 WHERE "
                + PlaylistScheme.ID + " = " + id + "";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        cursor.close();
        Log.e(TAG, query);
        return 0;
    }

    public long deletePlayListById(String id) {
        ContentValues values = new ContentValues();
        values.put(PlaylistScheme.NAME, id);

        String query = "DELETE FROM " + PlaylistScheme.TABLE_PLAYLIST_NAME + " WHERE " + PlaylistScheme.ID + " = " + id + "";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        cursor.close();
        Log.e(TAG, query);
        return 0;
    }


    public long deleteTableKeysearch() {
        long result = 0;
        try {
            result = db.delete(PlaylistScheme.TABLE_PLAYLIST_NAME, null, null);
            Log.e(TAG, "delete table keysearch successfully");
        } catch (Exception e) {
            Log.e(TAG, "delete table keysearch fail");
        }
        return result;
    }
}
