package com.example.thanhthuy.nhac_mp3.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.thanhthuy.nhac_mp3.SongPlayListEntity;

import java.util.ArrayList;

public class SongPlayListDAO {
    private static final String TAG = "SongPlayListDAO";
    private final SQLiteDatabase db;

    public SongPlayListDAO(SQLiteDatabase db) {
        super();
        this.db = db;
    }

    public long insertSongPlayList(SongPlayListEntity entity) {
        int id_S = entity.getIdSong();
        int id_P = entity.getIdPlaylist();
        int result;

        String query1 = "SELECT * FROM " + SongPlaylistScheme.TABLE_SONGPLAYLIST_NAME +
                " WHERE " + SongPlaylistScheme.ID_SONG + " = " + id_S +
                " AND " + SongPlaylistScheme.ID_PLAYLIST + " = " + id_P;

        String query = "INSERT INTO " + SongPlaylistScheme.TABLE_SONGPLAYLIST_NAME +
                "(" + SongPlaylistScheme.ID_SONG + "," + SongPlaylistScheme.ID_PLAYLIST +
                ") VALUES ("
                + entity.getIdSong() + " , " + entity.getIdPlaylist() + ")";
        Cursor cursor = db.rawQuery(query1, null);


        if (cursor.getCount()==0){
            cursor= db.rawQuery(query,null);
            result = 1;
        }
        else {
            result = 0;
        }
        Log.e(TAG, "Column/ Count/ Position  cursor = " + cursor.getColumnCount() + " /  " + cursor.getCount() +" / " +cursor.getPosition() );
        Log.e(TAG, query1+" || "+ query +"|| Result :"+ result);

        return result;
    }


    public ArrayList<SongPlayListEntity> getByIdPlayList(int idP) {
        ArrayList<SongPlayListEntity> list = new ArrayList<>();
        String query = "SELECT * FROM " + SongPlaylistScheme.TABLE_SONGPLAYLIST_NAME + " WHERE "
                + SongPlaylistScheme.ID_PLAYLIST + " = " + idP;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                SongPlayListEntity entity = new SongPlayListEntity();
                entity.setId(cursor.getInt(0));
                entity.setIdSong(cursor.getInt(1));
                entity.setIdPlaylist(cursor.getInt(2));
                list.add(entity);
            } while (cursor.moveToNext());
        }

        Log.e(TAG, query);
        return list;
    }


    public long deleteTableKeysearch() {
        long result = 0;
        try {
            result = db.delete(SongPlaylistScheme.TABLE_SONGPLAYLIST_NAME, null, null);
            Log.e(TAG, "delete table Song-playlist successfully");
        } catch (Exception e) {
            Log.e(TAG, "delete table Song-playlist fail");
        }
        return result;
    }
}
