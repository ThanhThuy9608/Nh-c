package com.example.thanhthuy.nhac_mp3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thanhthuy.nhac_mp3.adapter.BaiHatAdapter;
import com.example.thanhthuy.nhac_mp3.database.SongEntity;
import com.example.thanhthuy.nhac_mp3.database.SongIDU;
import com.example.thanhthuy.nhac_mp3.laymusic.SongMusic;
import com.google.gson.Gson;

import java.util.List;

public class ListSongAlbumActivity extends AppCompatActivity {
    public List<SongMusic> listSongAlbum;
    public List<SongEntity> listSongEntityAlbum;
    public static int idAlbum, idPlayList;
    public SongIDU songIDU;
    private ListView lvSong;

    public static TextView txtTenBaiHat, txtTacGia;
    public static ImageView imv_pause_play, imv_previous, imv_next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song_album);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.ic_back);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTenBaiHat = (TextView) findViewById(R.id.tv_bottom_title_song);
        txtTacGia = (TextView) findViewById(R.id.tv_bottom_name_artist);
        imv_pause_play = (ImageView) findViewById(R.id.imv_pause_play);
        imv_previous = (ImageView) findViewById(R.id.imv_previous);
        imv_next = (ImageView) findViewById(R.id.imv_next);

        songIDU = new SongIDU(getApplicationContext());

        lvSong = (ListView) findViewById(R.id.listViewBaiHatAlbum);
        idAlbum = getIntent().getIntExtra("idAlbum", -1);
        idPlayList = getIntent().getIntExtra("idPlayList", -1);

        if (idAlbum != -1) {
            listSongEntityAlbum = songIDU.getSongByIdAlbum(idAlbum);

            lvSong.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

                    final EShPre shPre = EShPre.gIns(getApplicationContext());
                    CharSequence[] choose = null;
                    final String idSong;
                    idSong = String.valueOf(listSongEntityAlbum.get(position).getId());
                    if (shPre.checkFavoriteById(idSong)) {
                        choose = new CharSequence[]{"Thêm vào Playlist"};
                    } else {
                        choose = new CharSequence[]{"Thêm vào Playlist"};
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(ListSongAlbumActivity.this);
                    builder.setItems(choose, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int pos) {
                            if (pos == 0) {
                            } else if (pos == 1) {

                                if (shPre.checkFavoriteById(idSong)) {
                                    shPre.removeFavoriteById(idSong);
                                } else {
                                    shPre.setFavorite(String.valueOf(idSong));
                                }
                            }
                        }
                    });
                    builder.show();

                    return false;
                }
            });

        } else if (idPlayList != -1) {
            listSongEntityAlbum = songIDU.getSongByIdPlayList(idPlayList);
        }

        BaiHatAdapter adapter = new BaiHatAdapter(getApplicationContext(), R.layout.bai_hat, listSongEntityAlbum);
        lvSong.setAdapter(adapter);
        final String listSerializedToJson = new Gson().toJson(listSongEntityAlbum);
        lvSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  //
                  Intent intent = new Intent(getApplicationContext(), Player.class);
                  intent.putExtra("songIndex", position);
                  intent.putExtra("list", listSerializedToJson);
                  startService(intent);

              }
          }
        );
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) { //app icon in action bar clicked; go back
            Toast.makeText(getApplicationContext(), "Quay lại", Toast.LENGTH_LONG).show();
            //TODO
            // xử lý quay lại
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
