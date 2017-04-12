package com.example.thanhthuy.nhac_mp3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.thanhthuy.nhac_mp3.SongPlayListEntity;
import com.example.thanhthuy.nhac_mp3.adapter.PlayListEntity;

import java.util.ArrayList;


public class SongPlayListIDR {

    private Context context;
    public long insertPlayList(String name){
        Database createDb = new Database(context);
        SQLiteDatabase db = createDb.open();
        PlayListDAO playListDAO = new PlayListDAO(db);
        long result = playListDAO.insertPlayList(name);
        db.close();
        createDb.close();
        Log.e("xem result", String.valueOf(result));
        return result;
    }

    public SongPlayListIDR(Context context) {
        this.context = context;
    }

    public long insertKeysearch(SongPlayListEntity songPlayListEntity){
        Database createDb = new Database(context);
        SQLiteDatabase db = createDb.open();
        SongPlayListDAO songPlayListDAO = new SongPlayListDAO(db);
        long result = songPlayListDAO.insertSongPlayList(songPlayListEntity);
        db.close();
        createDb.close();
        return result;
    }

    public ArrayList<SongPlayListEntity> getByIdPlayList(int idP){
        Database createDb = new Database(context);
        SQLiteDatabase db = createDb.open();
        SongPlayListDAO songPlayListDAO = new SongPlayListDAO(db);
        ArrayList<SongPlayListEntity> result = songPlayListDAO.getByIdPlayList(idP);
        db.close();
        createDb.close();
        return result;
    }

    public void deleteTableKeysearch() {
        Database createDb = new Database(context);
        SQLiteDatabase db = createDb.open();
        SongPlayListDAO songPlayListDAO = new SongPlayListDAO(db);
        songPlayListDAO.deleteTableKeysearch();
        db.close();
        createDb.close();
    }

    public int getIdByName(String name) {
        Database createDb = new Database(context);
        SQLiteDatabase db = createDb.open();
        PlayListDAO playListDAO = new PlayListDAO(db);
        int result = playListDAO.getIdByName(name);
        db.close();
        createDb.close();
        return result;
    }
}
