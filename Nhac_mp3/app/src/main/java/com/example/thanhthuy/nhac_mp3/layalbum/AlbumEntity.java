package com.example.thanhthuy.nhac_mp3.layalbum;

public class AlbumEntity {
    private int id;
    private String name;

    public AlbumEntity() {
    }

    public AlbumEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
