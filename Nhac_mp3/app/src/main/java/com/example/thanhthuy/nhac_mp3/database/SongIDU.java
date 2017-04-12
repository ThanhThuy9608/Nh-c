package com.example.thanhthuy.nhac_mp3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by ThanhThuy on 09/04/2017.
 */

public class SongIDU {
    private Context context;
    public SongIDU(Context context){
        this.context = context;
    }

    public long insertKeysearch(SongEntity songEntity){
        Database database = new Database(context);
        SQLiteDatabase db = database.open();
        SongDAO songDAO = new SongDAO(db);
        long result = songDAO.insertSong(songEntity);
        db.close();
        database.close();
        return result;
    }

    public ArrayList<SongEntity> getAllSong(){
        Database database = new Database(context);
        SQLiteDatabase db = database.open();
        SongDAO songDAO = new SongDAO(db);
        ArrayList<SongEntity> list = songDAO.getAll();
        db.close();
        database.close();
        return list;
    }

    public ArrayList<SongEntity> getSongByIdAlbum(int id){
        Database database = new Database(context);
        SQLiteDatabase db = database.open();
        SongDAO songDAO =  new SongDAO(db);
        ArrayList<SongEntity> listSong = songDAO.getSongByIdAlbum(id);
        db.close();
        database.close();
        return listSong;
    }
    public ArrayList<SongEntity> getSongByIdPlayList(int id) {
        Database createDb = new Database(context);
        SQLiteDatabase db = createDb.open();
        SongDAO songDAO = new SongDAO(db);
        ArrayList<SongEntity> listSong = songDAO.getSongByIdPlayList(id);
        db.close();
        createDb.close();
        return listSong;
    }

    public ArrayList<SongEntity> getSongById(int id) {
        Database createDb = new Database(context);
        SQLiteDatabase db = createDb.open();
        SongDAO songDAO = new SongDAO(db);
        ArrayList<SongEntity> listSong = songDAO.getSongById(id);
        db.close();
        createDb.close();
        return listSong;
    }
    public void deleteTableKeysearch() {
        Database createDb = new Database(context);
        SQLiteDatabase db = createDb.open();
        SongDAO songDAO = new SongDAO(db);
        songDAO.deleteTableKeysearch();
        db.close();
        createDb.close();
    }

}
