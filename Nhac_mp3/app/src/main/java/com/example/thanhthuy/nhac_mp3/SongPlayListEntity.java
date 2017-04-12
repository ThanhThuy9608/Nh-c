package com.example.thanhthuy.nhac_mp3;

/**
 * Created by TTTTT on 12/1/2016.
 */
public class SongPlayListEntity {
    private int id;
    private int idSong;
    private int idPlaylist;

    public SongPlayListEntity(int id, int idSong, int idPlaylist) {
        this.id = id;
        this.idSong = idSong;
        this.idPlaylist = idPlaylist;
    }

    public SongPlayListEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSong() {
        return idSong;
    }

    public void setIdSong(int idSong) {
        this.idSong = idSong;
    }

    public int getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(int idPlaylist) {
        this.idPlaylist = idPlaylist;
    }
}
