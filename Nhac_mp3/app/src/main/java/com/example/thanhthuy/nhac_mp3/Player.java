package com.example.thanhthuy.nhac_mp3;


import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thanhthuy.nhac_mp3.database.SongEntity;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.example.thanhthuy.nhac_mp3.MainActivity.imv_previous;
import static com.example.thanhthuy.nhac_mp3.R.id.imv_image_song;
import static com.example.thanhthuy.nhac_mp3.R.id.imv_next;
import static com.example.thanhthuy.nhac_mp3.R.id.imv_pause_play;
import static com.example.thanhthuy.nhac_mp3.R.id.linearLayourABM;


//WeakReference(tham chiếu yếu): để gữi Context truyền không bị mất

public class Player extends Service implements MediaPlayer.OnCompletionListener,
        View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    public static final String TAG = "Player Service";
    private WeakReference<ImageView> btnPlay, btnPause, btnForward, btnBackward, btnNext,
            btnPrevious, btnPlayNB;
    private WeakReference<ImageView> btnPlayA, btnNextA, btnPreviousA;
    private WeakReference<SeekBar> songProgressBar;
    private WeakReference<TextView> songTitleLabel;
    private WeakReference<TextView> songTitleLabelA;
    private WeakReference<TextView> singerLabel;
    private WeakReference<TextView> singerLabelA;
    private WeakReference<TextView> songCurrentDurationLabel;
    private WeakReference<TextView> songTotalDurationLabel;
    private WeakReference<LinearLayout> lLAppBarMain;
    private WeakReference<LinearLayout> lLAppBarMainFull;
    public static MediaPlayer mpService;
    private Handler progressBarHandler = new Handler();
    private Utilities utils;
    private boolean isShuffle = false;
    private boolean isRepeat = false;
    public static List<SongEntity> songsListingSD;
    public static int currentSongIndex = -1;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        Log.d("Player Service", "onBind");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("Play Service", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub

        Log.d("Play Service", "onCreate");


        mpService = new MediaPlayer();
        mpService.setOnCompletionListener(this);
        mpService.reset();
        mpService.setAudioStreamType(AudioManager.STREAM_MUSIC);
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("Play Service", "onStartCommand");
        utils = new Utilities();

        initUI();

        int songIndex = intent.getIntExtra("songIndex", 0);
        String stringList = intent.getStringExtra("list");
        SongEntity[] abc = new Gson().fromJson(stringList, SongEntity[].class);
        songsListingSD = new ArrayList<>(Arrays.asList(abc));
        Log.e(TAG, songsListingSD.toString());

        playSong(songIndex);
        //initNotification(songIndex);
        currentSongIndex = songIndex;
        if (currentSongIndex != -1) {
            songTitleLabel.get().setText(
                    songsListingSD.get(currentSongIndex).getTitle());

        }
        super.onStart(intent, startId);
        return START_STICKY;
    }

    private void initUI() {
        singerLabel = new WeakReference<TextView>(MainActivity.tv_bottom_name_artist);
        singerLabelA = new WeakReference<TextView>(ListSongAlbumActivity.txtTenBaiHat);
        songTitleLabel = new WeakReference<TextView>(MainActivity.tv_bottom_title_song);
        songTitleLabelA = new WeakReference<TextView>(ListSongAlbumActivity.txtTacGia);
        songCurrentDurationLabel = new WeakReference<TextView>(
                ChoiNhacActivity.textViewStart);
        songTotalDurationLabel = new WeakReference<TextView>(
                ChoiNhacActivity.textViewEnd);

        btnPlay = new WeakReference<ImageView>(MainActivity.imv_pause_play);
        btnPlayA = new WeakReference<ImageView>(ListSongAlbumActivity.imv_pause_play);

        btnNext = new WeakReference<ImageView>(MainActivity.imv_next);
        btnNextA = new WeakReference<ImageView>(ListSongAlbumActivity.imv_next);
        btnPrevious = new WeakReference<ImageView>(imv_previous);
        btnPreviousA = new WeakReference<ImageView>(ListSongAlbumActivity.imv_previous);

        lLAppBarMain = new WeakReference<LinearLayout>(MainActivity.linearLayourABM);
        lLAppBarMainFull = new WeakReference<LinearLayout>(MainActivity.linearLayourABMFull);

        btnPlay.get().setOnClickListener(this);

        btnNext.get().setOnClickListener(this);
        btnPrevious.get().setOnClickListener(this);

        lLAppBarMain.get().setOnClickListener(this);

    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case imv_pause_play:
                PLAY();
                break;

            case imv_next:
                NEXT();
                break;
            case R.id.imv_previous:
                BACK();
                break;
            case linearLayourABM:

                if (lLAppBarMainFull.get().getVisibility() == View.GONE) {
                    lLAppBarMainFull.get().setVisibility(View.VISIBLE);
                } else {
                    lLAppBarMainFull.get().setVisibility(View.GONE);
                }
        }
    }

    public void BACK() {
        if (currentSongIndex > 0) {
            playSong(currentSongIndex - 1);
            currentSongIndex = currentSongIndex - 1;
          //  initNotification(currentSongIndex);
        } else {
            // play last song
            playSong(songsListingSD.size() - 1);
            currentSongIndex = songsListingSD.size() - 1;
            //initNotification(currentSongIndex);
        }
    }

    public void NEXT() {
        // check if next song is there or not
        if (currentSongIndex < (songsListingSD.size() - 1)) {
            playSong(currentSongIndex + 1);
            currentSongIndex = currentSongIndex + 1;
           // initNotification(currentSongIndex);
//                    NotificationPanel.setTitle(currentSongIndex);
        } else {
            // play first song
            playSong(0);
            currentSongIndex = 0;
           // initNotification(currentSongIndex);
        }
    }

    public void PLAY() {
        if (currentSongIndex != -1) {
            if (mpService.isPlaying()) {
                if (mpService != null) {
                    mpService.pause();
                    btnPlay.get()
                            .setImageResource(R.drawable.ic_play);
                }
            } else {

                if (mpService != null) {
                    mpService.start();
                    btnPlay.get().setImageResource(
                            R.drawable.ic_pause);
                }
            }
        }
    }


    public void playSong(int songIndex) {
        // Play song
        if (mpService != null && mpService.isPlaying()) {
            mpService.stop();
            Log.e(TAG, "MP != Null && isPlaying");
        }
        try {
            mpService.reset();
            mpService.setDataSource(songsListingSD.get(songIndex).getImg());
            mpService.prepare();
            mpService.start();
            // Displaying Song title
            String songTitle = songsListingSD.get(songIndex).getTitle();
            songTitleLabel.get().setText(songTitle);
            String singerName = songsListingSD.get(songIndex).getArtist();
            singerLabel.get().setText(singerName);

            if (ListSongAlbumActivity.txtTenBaiHat != null) {
                songTitleLabelA.get().setText(songTitle);
                singerLabelA.get().setText(singerName);
            }
            if (mpService.isPlaying())
                btnPlay.get().setImageResource(R.drawable.ic_play);
            else
                btnPlay.get().setImageResource(R.drawable.ic_pause);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ----------------onSeekBar Change Listener------------------------------//

    /**
     * Update timer on seekbar
     */
    public void updateProgressBar() {
        progressBarHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = 0;
            try {
                totalDuration = mpService.getDuration();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            long currentDuration = 0;
            try {
                currentDuration = mpService.getCurrentPosition();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            // Displaying Total Duration time
            songTotalDurationLabel.get().setText(
                    "" + utils.milliSecondsToTimer(totalDuration));
//			// Displaying time completed playing
            songCurrentDurationLabel.get().setText(
                    "" + utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int) (utils.getProgressPercentage(currentDuration,
                    totalDuration));
            // Log.d("Progress", ""+progress);
            songProgressBar.get().setProgress(progress);
            // Running this thread after 100 milliseconds
            progressBarHandler.postDelayed(this, 100);
            // Log.d("AndroidBuildingMusicPlayerActivity","Runable  progressbar");
        }
    };

    // ----------------on Seekbar Change
    // Listener---------------------------------------//
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromTouch) {
    }

    /**
     * When user starts moving the progress handler
     */
    public void onStartTrackingTouch(SeekBar seekBar) {
        // remove message Handler from updating progress bar
        progressBarHandler.removeCallbacks(mUpdateTimeTask);
    }

    /**
     * When user stops moving the progress hanlder
     */
    public void onStopTrackingTouch(SeekBar seekBar) {
        progressBarHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mpService.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(),
                totalDuration);

        // forward or backward to certain seconds
        mpService.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
    }

    /**
     * On Song Playing completed if repeat is ON play same song again if shuffle
     * is ON play random song
     */
    public void onCompletion(MediaPlayer arg0) {

        // check for repeat is ON or OFF
        if (isRepeat) {
            // repeat is on play same song again
            playSong(currentSongIndex);
        } else if (isShuffle) {
            // shuffle is on - play a random song
            Random rand = new Random();
            currentSongIndex = rand
                    .nextInt((songsListingSD.size() - 1) - 0 + 1) + 0;
            playSong(currentSongIndex);
        } else {
            // no repeat or shuffle ON - play next song
            if (currentSongIndex < (songsListingSD.size() - 1)) {
                playSong(currentSongIndex + 1);
                currentSongIndex = currentSongIndex + 1;
            } else {
                // play first song
                playSong(0);
                currentSongIndex = 0;
            }
        }
    }

    // ---------------------------------------------------------//
    @Override
    public void onDestroy() {
        super.onDestroy();
       // Toast.makeText(getApplicationContext(), "PlayerService destroy", Toast.LENGTH_SHORT).show();
        currentSongIndex = -1;
        // Remove progress bar update Hanlder callBacks
       // noti.notificationCancel();
//        progressBarHandler.removeCallbacks(mUpdateTimeTask);
        Log.d(TAG, "Player Service Stopped");
        if (mpService != null) {
            if (mpService.isPlaying()) {
                mpService.stop();
            }
            mpService.release();
        }

    }

}
