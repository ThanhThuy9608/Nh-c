package com.example.thanhthuy.nhac_mp3.layalbum;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhThuy on 09/04/2017.
 */

public class Albums {
    public List<AlbumEntity> getAlbum(Context context) {
        ContentResolver cr = context.getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cur = cr.query(uri, null, selection, null, sortOrder);
        int count = 0;
        List<AlbumEntity> listSongAlbum = new ArrayList<>();
        if (cur != null) {
            count = cur.getCount();

            if (count > 0) {
                while (cur.moveToNext()) {

                    int displayname = Integer.parseInt(cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
                    String album = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    ///Log.d("data", path);
                    AlbumEntity songMdl = new AlbumEntity(displayname, album);
                    listSongAlbum.add(songMdl);

                }

            }
        }

        cur.close();
        return listSongAlbum;
    }

}
