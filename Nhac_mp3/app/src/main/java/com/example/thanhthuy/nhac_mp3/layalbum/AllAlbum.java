package com.example.thanhthuy.nhac_mp3.layalbum;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.thanhthuy.nhac_mp3.database.Database;

import java.util.ArrayList;

/**
 * Created by ThanhThuy on 09/04/2017.
 */

public class AllAlbum {

    private Context context;

    public AllAlbum(Context context) {
        this.context = context;
    }

    public long insertKeysearch(AlbumEntity albumEntity){
        Database createDb = new Database(context);
        SQLiteDatabase db = createDb.open();
        AlbumDAO albumDAO = new AlbumDAO(db);
        long result = albumDAO.insertAlbum(albumEntity);
        db.close();
        createDb.close();
        return result;
    }

    public ArrayList<AlbumEntity> getAlbumByName(String type){
        Database createDatabase = new Database(context);
        SQLiteDatabase db = createDatabase.open();
        AlbumDAO albumDAO = new AlbumDAO(db);
        ArrayList<AlbumEntity> listAlbum = albumDAO.getAlbum(type);
        db.close();
        createDatabase.close();
        return listAlbum;

    }
    // DAO: Data Access Object
    public ArrayList<AlbumEntity> getAllAlbum(){
        Database createDatabase = new Database(context);
        SQLiteDatabase db = createDatabase.open();
        AlbumDAO albumDAO = new AlbumDAO(db);
        ArrayList<AlbumEntity> listAlbum = albumDAO.getAllAlbum();
        db.close();
        createDatabase.close();
        return listAlbum;

    }



    public void deleteTableKeysearch() {
        Database createDb = new Database(context);
        SQLiteDatabase db = createDb.open();
        AlbumDAO albumDAO = new AlbumDAO(db);
        albumDAO.deleteTableKeysearch();
        db.close();
        createDb.close();
    }
}
