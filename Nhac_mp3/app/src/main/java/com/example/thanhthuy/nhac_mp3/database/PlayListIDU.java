package com.example.thanhthuy.nhac_mp3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.thanhthuy.nhac_mp3.adapter.PlayListEntity;

import java.util.ArrayList;


public class PlayListIDU {
    private Context context;

    public PlayListIDU(Context context) {
        this.context = context;
    }
    public ArrayList<PlayListEntity> getAll() {
        Database createDb = new Database(context);
        SQLiteDatabase db = createDb.open();
        PlayListDAO playListDAO = new PlayListDAO(db);
        ArrayList<PlayListEntity> list =  playListDAO.getAllPlayList();
        db.close();
        createDb.close();
        return list;
    }

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

    public void deleteByid(String rowid) {
        Database createDb = new Database(context);
        SQLiteDatabase db = createDb.open();
        PlayListDAO playListDAO = new PlayListDAO(db);
        playListDAO.deletePlayListById(rowid);
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

    public void hidenById(String rowid) {
        Database createDb = new Database(context);
        SQLiteDatabase db = createDb.open();
        PlayListDAO playListDAO = new PlayListDAO(db);
        playListDAO.changeStatusById(rowid);
        db.close();
        createDb.close();
    }


    public void deleteTableKeysearch() {
        Database createDb = new Database(context);
        SQLiteDatabase db = createDb.open();
        PlayListDAO playListDAO = new PlayListDAO(db);
        playListDAO.deleteTableKeysearch();
        db.close();
        createDb.close();
    }
}
