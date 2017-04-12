package com.example.thanhthuy.nhac_mp3.database;


public class SongEntity {
    private int id;
    private String title;
    private String artist;
    private int duration;
    private String name;
    private String img;
    private int idAlbum;
    private int status;

    public SongEntity() {
    }

    public SongEntity(int id,String name, String title, String artist, int duration, String img, int idAlbum, int status) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.artist = artist;
        this.duration = duration;
        this.img = img;
        this.idAlbum = idAlbum;
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
