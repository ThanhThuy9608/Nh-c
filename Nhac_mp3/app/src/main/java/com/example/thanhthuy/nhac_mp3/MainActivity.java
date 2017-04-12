package com.example.thanhthuy.nhac_mp3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thanhthuy.nhac_mp3.adapter.FragmentPagerAdapter;
import com.example.thanhthuy.nhac_mp3.database.AlbumIDU;
import com.example.thanhthuy.nhac_mp3.database.PlayListIDU;
import com.example.thanhthuy.nhac_mp3.database.SongIDU;
import com.example.thanhthuy.nhac_mp3.layalbum.AlbumEntity;
import com.example.thanhthuy.nhac_mp3.database.SongEntity;
import com.example.thanhthuy.nhac_mp3.laymusic.SongMusic;
import com.example.thanhthuy.nhac_mp3.laymusic.Songs;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager pager;
    TabLayout tabLayout;
    public static SongMusic currSong;
    public static int currSongPosition = 0;
    public static List<SongMusic> songMdlList;
    public static List<SongEntity> listSongEntity;
    public SongIDU songIDU;
    public AlbumIDU albumIDU;
    public ArrayList<AlbumEntity> listAlbum;
    public static ImageView imv_image_song;
    public static LinearLayout linearLayourABM;
    public static LinearLayout linearLayourABMFull;
    public static TextView tv_bottom_title_song, tv_bottom_name_artist ;
    public static ImageView imv_pause_play, imv_next, imv_previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FacebookSdk.sdkInitialize(getApplicationContext(), new FacebookSdk.InitializeCallback() {
            @Override
            public void onInitialized() {
                accessToken = AccessToken.getCurrentAccessToken();
                if (accessToken == null) {
                    Log.d(TAG, "not log in yet");
                } else {
                    Log.d(TAG, "Logged in");
                    Intent main = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(main);
                }
            }
        });
        imv_image_song = (ImageView) findViewById(R.id.imv_image_song);
        tv_bottom_name_artist = (TextView) findViewById(R.id.tv_bottom_name_artist);
        linearLayourABM = (LinearLayout) findViewById(R.id.linearLayourABM);
        tv_bottom_title_song = (TextView) findViewById(R.id.tv_bottom_title_song);
        linearLayourABMFull = (LinearLayout) findViewById(R.id.linearLayourABMFull);

        getAllSong();
        intitViewPager();
        // loadTabs();
        imv_previous = (ImageView) findViewById(R.id.imv_previous);
        imv_pause_play = (ImageView) findViewById(R.id.imv_pause_play);
        imv_next = (ImageView) findViewById(R.id.imv_next);

        insertAlbumIntoDB();
        insertSongIntoDB();

        SongIDU songIDU = new SongIDU(getApplicationContext());
        listSongEntity = songIDU.getAllSong();

        imv_pause_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if ((mpCurr != null)) {
//                    mpCurr.start();
//                } else
//                    playThisSong(songMdlList, currSongPosition);
                Player playerService =new Player();
                playerService.PLAY();
            }

        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void getAllSong() {
        Songs song = new Songs();
        songMdlList = song.getMusic(getApplicationContext());
    }
    public void insertAlbumIntoDB() {

        AlbumIDU albumService = new AlbumIDU(MainActivity.this);
        albumService.deleteTableKeysearch();
        for (int i = 0; i < songMdlList.size(); i++) {
            AlbumEntity albumEntity = new AlbumEntity();
            albumEntity.setName(songMdlList.get(i).getAlbum());
            albumService.insertKeysearch(albumEntity);
        }
    }

    public void insertSongIntoDB(){
        songIDU = new SongIDU(MainActivity.this);
        songIDU.deleteTableKeysearch();
        for(int i=0; i<songMdlList.size(); i++){
            SongEntity entity = new SongEntity();
            entity.setTitle(songMdlList.get(i).getTitle());
            entity.setArtist(songMdlList.get(i).getArtist());
            entity.setDuration(parseInt(songMdlList.get(i).getDuration()));
            entity.setImg(songMdlList.get(i).getPath());
            albumIDU = new AlbumIDU(this);
            listAlbum = albumIDU.getAlbumByName(songMdlList.get(i).getAlbum());
            entity.setIdAlbum(listAlbum.get(0).getId());
            entity.setStatus(1);
            songIDU.insertKeysearch(entity);
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        moveTaskToBack(true);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

/**
    public void loadTabs(){
        final TabHost tab  = (TabHost) findViewById(android.R.id.tabhost);
        // gọi lệnh setup
        tab.setup();
        TabHost.TabSpec spec;
        // Tạo tab1
        spec = tab.newTabSpec("t1");
        spec.setContent(R.id.tabMusic);
        spec.setIndicator("Bài hát");
        tab.addTab(spec);

        // Tạo tab2

        spec = tab.newTabSpec("t1");
        spec.setContent(R.id.tabAlbum);
        spec.setIndicator("Album");
        tab.addTab(spec);

        // Thiết lâp mặc định
        tab.setCurrentTab(0);
    }
    */

    public void intitViewPager(){
        pager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        FragmentManager manager = getSupportFragmentManager();
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(manager);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // displaySelectedScreen(id);
        if (id == R.id.nav_Music) {
            Toast.makeText(this, "Music", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_Album) {

            Toast.makeText(this, "Music", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_caidat) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_lq) {

        } else if (id == R.id.nav_close) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
