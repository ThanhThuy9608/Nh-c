package com.example.thanhthuy.nhac_mp3;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thanhthuy.nhac_mp3.database.PlayListIDU;
import com.example.thanhthuy.nhac_mp3.laymusic.SongMusic;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.thanhthuy.nhac_mp3.MainActivity.currSong;
import static com.example.thanhthuy.nhac_mp3.MainActivity.currSongPosition;
import static com.example.thanhthuy.nhac_mp3.MainActivity.songMdlList;
import static com.example.thanhthuy.nhac_mp3.Player.currentSongIndex;
import static com.example.thanhthuy.nhac_mp3.Player.mpService;

public class ChoiNhacActivity extends AppCompatActivity {

    ImageButton ibPlay, ibNext, ibPrevious, ibLapLai, ibNgauNhien, ibStop;
    public static SeekBar seekBarSong;
    private RotateAnimation anim;
    public static TextView textViewStart, textViewEnd;
    TextView tvTenBaiHat;
    ImageView imger_view_album, mBackgroundImage;
    private MediaPlayer mp;
    private SongMusic[] arrSong;
    private Player player;
    private double startTime = 0;
    private double finalTime = 0;
    public static int oneTimeOnly = 0;
    public static String path;

    int position;
    Intent intent;
    private boolean isShuffle = false;
    private IntentFilter intentFilter;
    SongMusic itemPlay;
    private android.os.Handler myHandler = new android.os.Handler();

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mp.getCurrentPosition();
            textViewStart.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekBarSong.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }

    };

    public void playSong(int index){
        try{
            mp.start();
            String ten = intent.getStringExtra("ten");

            tvTenBaiHat.setText(ten);
            index = Integer.parseInt(ten);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return;
    }
    private void setContentView(MediaPlayer mp, int posSeekBar, int posPlaying) {
        tvTenBaiHat.setText(songMdlList.get(posPlaying).getNameMusic());
        updateSeekBar(mp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choi_nhac);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.ic_back);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(mpService,0, currentSongIndex);

        currSongPosition = intent.getIntExtra("position", 0);
        startTime = intent.getIntExtra("startTime", 0);
        intent = getIntent();
        String jsonListMusic = intent.getStringExtra("arr_song");
        arrSong = new Gson().fromJson(jsonListMusic, SongMusic[].class);

        for (int i = 0; i < arrSong.length; i++) {
            arrSong[i] = (SongMusic) arrSong[i];
        }

        position = intent.getIntExtra("position", 0);
        itemPlay = (SongMusic) arrSong[position];
        mp = MediaPlayer.create(this, Uri.parse(itemPlay.getPath()));
        tvTenBaiHat = (TextView) findViewById(R.id.tvTenbaihat);

        mp.start();

        imger_view_album = (ImageView) findViewById(R.id.image_view_album);
        mBackgroundImage = (ImageView) findViewById(R.id.background_image);
        textViewStart = (TextView) findViewById(R.id.textViewStart);
        textViewEnd = (TextView) findViewById(R.id.textViewEndchoinhac);
        ibPlay = (ImageButton) findViewById(R.id.imageButtonPlayChoiNhac);
        ibStop = (ImageButton) findViewById(R.id.imageButtonStop1);
        ibPrevious = (ImageButton) findViewById(R.id.imageButtonBackchoinhac);
        ibLapLai = (ImageButton) findViewById(R.id.imageLapLai);
        ibNgauNhien = (ImageButton) findViewById(R.id.imageButtonNgauNhien);
        ibNext = (ImageButton) findViewById(R.id.imageButtonNext1);
        seekBarSong = (SeekBar) findViewById(R.id.seekBarSong);
        seekBarSong.setMax(mp.getDuration());
        imger_view_album = (ImageView) findViewById(R.id.image_view_album);



        playThisSongCN(songMdlList, currSongPosition, (int) startTime);
        updateSeekBar(mpService);
        //ibPlay.setVisibility(View.INVISIBLE);

        seekBarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mp.seekTo(progress);
                    seekBar.setProgress(progress);
                } else {

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ibPlay.setVisibility(View.INVISIBLE);
        finalTime = mp.getDuration();
        startTime = mp.getCurrentPosition();

        if (oneTimeOnly == 0) {
            seekBarSong.setMax((int) finalTime);
            oneTimeOnly = 1;
        }

        textViewEnd.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
        );

        textViewStart.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
        );

        seekBarSong.setProgress((int) startTime);
        myHandler.postDelayed(UpdateSongTime, 100);
        // myHandler.postDelayed(UpdateSongTime1, 100);
        // view.startLayoutAnimation();

        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ibStop.setVisibility(View.VISIBLE);
//                ibPlay.setVisibility(View.INVISIBLE);
//                if (mp != null && mp.isPlaying()) {
//                    mp.pause();
//                    rotationImage(false);
//                } else {
//                    mp.start();
//                }
                player.PLAY();

            }
        });
        ibNext.setOnClickListener(new View.OnClickListener() {
            int index = 1;

            @Override
            public void onClick(View v) {

//                ibPlay.setVisibility(View.INVISIBLE);
//                ibStop.setVisibility(View.VISIBLE);
//                mp.stop();
//                mp.release();
//                index++;
//                SongMusic songMusic = arrSong[position + index];
//
//                mp = MediaPlayer.create(ChoiNhacActivity.this, Uri.parse(songMusic.getPath()));
//                mp.start();
//                playSong(index+1);
//                seekBarSong.setMax(mp.getDuration());
                startTime = 0;
                if (currSongPosition == (songMdlList.size() - 1)) {
                    Toast.makeText(getApplicationContext(), "Bạn đang ở bài hát cuối cùng của danh sách!", Toast.LENGTH_SHORT).show();
                } else {
                    playThisSongCN(songMdlList, ++currSongPosition, (int) startTime);
//                    Toast.makeText(getApplicationContext(),"Bạn đang ở bài hát " +currSongPosition+" / " +songMdlList.size(),Toast.LENGTH_SHORT).show();
                    updateSeekBar(mpService);
                }
            }
        });

        ibPrevious.setOnClickListener(new View.OnClickListener() {
            int index = 1;

            @Override
            public void onClick(View v) {
//
//                ibPlay.setVisibility(View.INVISIBLE);
//                ibStop.setVisibility(View.VISIBLE);
//                mp.stop();
//                mp.release();
//                index--;
//                SongMusic songMusic = arrSong[position + index];
//
//                mp = MediaPlayer.create(ChoiNhacActivity.this, Uri.parse(songMusic.getPath()));
//                mp.start();
//                playSong(index-1);
//                seekBarSong.setMax(mp.getDuration());
                startTime = 0;
                if (currSongPosition == 0) {
                    playThisSongCN(songMdlList, currSongPosition, (int) startTime);
                } else
                    playThisSongCN(songMdlList, --currSongPosition, (int) startTime);

                updateSeekBar(mpService);
            }
        });

        ibStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibPlay.setVisibility(View.VISIBLE);
                ibStop.setVisibility(View.INVISIBLE);
                if (mp.isPlaying()) {
                    mp.pause();
                    rotationImage(false);
                } else {
                    mp.start();
                }

            }
        });

        ibLapLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp.setLooping(true);
                Toast.makeText(getApplicationContext(), "Bài hát này sẽ được lặp lại", Toast.LENGTH_LONG).show();
                mp.start();
            }
        });
        ibNgauNhien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShuffle) {
                    isShuffle = false;
                    Toast.makeText(getApplicationContext(), "off", Toast.LENGTH_LONG).show();
                } else {
                    isShuffle = true;
                    Toast.makeText(getApplicationContext(), "on", Toast.LENGTH_LONG).show();
                }
            }
        });

        anim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(6000);

        intentFilter = new IntentFilter();
        intentFilter.addAction("ACTION_PAUSE_SONG_FROM_NOTIFICATION");
        intentFilter.addAction("ACTION_STOP_ALL");

        rotationImage(true);
        ChoiNhacActivity.this.runOnUiThread(UpdateSongTime);
    }

    public void playThisSongCN(List<SongMusic> listSong, int i, int pos) {
        stopPlaying();
        tvTenBaiHat.setText(listSong.get(i).getTitle());

        ibPlay.setVisibility(View.INVISIBLE);
        ibStop.setVisibility(View.VISIBLE);

        currSong = listSong.get(i);
        mpService = MediaPlayer.create(getApplicationContext(), Uri.parse(currSong.getPath()));
        mpService.seekTo(pos);
        mpService.start();
        currSongPosition = i;
        mpService.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                startTime = 0;
                if (currSongPosition == (songMdlList.size() - 1)) {
                    Toast.makeText(getApplicationContext(), "Bạn đang ở bài hát cuối cùng của danh sách!", Toast.LENGTH_SHORT).show();
                } else {
                    playThisSongCN(songMdlList, ++currSongPosition, (int) startTime);
//                    Toast.makeText(getApplicationContext(),"Bạn đang ở bài hát " +currSongPosition+" / " +songMdlList.size(),Toast.LENGTH_SHORT).show();
                    updateSeekBar(mpService);
                }
            }
        });
    }

    private void updateSeekBar(final MediaPlayer mpTemp) {
        if (mpTemp == null) {
            Toast.makeText(getApplicationContext(), "Không có bài hát nào", Toast.LENGTH_SHORT).show();
        } else {

            finalTime = mpTemp.getDuration();
            startTime = mpTemp.getCurrentPosition();

            seekBarSong.setMax(mpTemp.getDuration());
            seekBarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mpTemp.seekTo(progress);
                        seekBar.setProgress(progress);
                    } else {
                        Log.d("onProgressChanged", "not from User");
                    }
                /*mediaPlayer.seekTo(progress);
                seekBar.setProgress(progress);*/
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });


            if (oneTimeOnly == 0) {
                seekBarSong.setMax((int) finalTime);
                oneTimeOnly = 1;
            }
            textViewEnd.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
            );

            textViewStart.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
            );

            seekBarSong.setProgress((int) startTime);
            myHandler.postDelayed(UpdateSongTime, 100);
        }

    }

    private void stopPlaying() {
        if (mpService != null) {
            mpService.stop();
            mpService.release();
            mpService = null;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_main, menu);
        ActionBar mActionBar = getActionBar();
        if (mActionBar != null)
            mActionBar.setDisplayHomeAsUpEnabled(true);
//            case R.id.back:
//                NavUtils.navigateUpFromSameTask(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) { //app icon in action bar clicked; go back
            Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_LONG).show();
            //TODO
            // xử lý quay lại
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // khi quay lại sẽ tự dừng lại
        // mp.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mp.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    private void rotationImage(boolean show){

        if(show)
            imger_view_album.startAnimation(anim);
        else
            imger_view_album.setAnimation(null);
    }


    class BroadCastNotification extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "ACTION_STOP_ALL":
                    finishAffinity();
                    break;
                case "ACTION_PAUSE_SONG_FROM_NOTIFICATION":
                    updateButtonPlayPause(intent);
                    break;
            }
        }
    }

    private void updateButtonPlayPause(Intent intent) {
        boolean isPlaying = intent.getBooleanExtra("KEY_SEND_PAUSE", false);
        if (isPlaying) {
            rotationImage(true);
            ibPlay.setVisibility(View.VISIBLE);
            ibStop.setVisibility(View.INVISIBLE);
        } else {

            rotationImage(false);
            ibStop.setVisibility(View.VISIBLE);
            ibPlay.setVisibility(View.INVISIBLE);
        }
    }
}

