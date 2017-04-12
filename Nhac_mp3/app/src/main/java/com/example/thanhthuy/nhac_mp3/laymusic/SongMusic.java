package com.example.thanhthuy.nhac_mp3.laymusic;

import android.os.Parcel;
import android.os.Parcelable;

import static android.R.attr.name;

/**
 * Parcelable được sử dụng để gửi dữ liệu (dạng Object) giữa các activity với nhau thông qua Bunble gửi cùng Intent
 */

public class SongMusic implements Parcelable {

    private String path;
    private String nameMusic;
    private String album;
    private String artist;
    private String duration;
    private String title;


    public SongMusic(String path, String nameMusic, String album, String artist, String duration, String title) {
        this.path = path;
        this.nameMusic = nameMusic;
        this.album = album;
        this.artist = artist;
        this.duration = duration;
        this.title = title;
    }

    protected SongMusic(Parcel in) {
        nameMusic = in.readString();
        artist = in.readString();
        duration = in.readString();
        title = in.readString();
        album = in.readString();
        path = in.readString();
    }

    public static final Creator<SongMusic> CREATOR = new Creator<SongMusic>() {
        @Override
        public SongMusic createFromParcel(Parcel in) {
            return new SongMusic(in);
        }

        @Override
        public SongMusic[] newArray(int size) {
            return new SongMusic[size];
        }
    };

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setNameMusic(String nameMusic) {
        this.nameMusic = nameMusic;
    }

    public String getNameMusic() {
        return nameMusic;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameMusic);
        dest.writeString(artist);
        dest.writeString(duration);
        dest.writeString(title);
        dest.writeString(album);
    }

    public String getid(int songIndex) {
        return null;
    }


}
