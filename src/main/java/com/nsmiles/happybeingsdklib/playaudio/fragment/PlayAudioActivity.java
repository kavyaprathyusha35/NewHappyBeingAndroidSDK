package com.nsmiles.happybeingsdklib.playaudio.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.playaudio.playimplementation.PlayImplementation;
import com.nsmiles.happybeingsdklib.playaudio.playview.PlayView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.feeeei.circleseekbar.CircleSeekBar;


public class PlayAudioActivity extends AppCompatActivity implements View.OnClickListener, PlayView {
    PlayImplementation playImplementation;
    PlayView playView;
    Activity activity;
    ImageView play_audio_img, pause_audio_img, download_image,fav_img;
    TextView audio_day_tv,audio_day_title, audio_name_tv, time_remaining_tv, total_time_tv;
    CircleSeekBar play_progress;
    ProgressBar download_progress;
    RelativeLayout timer_layout;
    View timer_view;
    ImageView back_icon_img;
    SeekBar progress_wave;
    ImageView headphone;
    TextView audio_main_title;
    private SeekBar seekbar;
    LinearLayout repeat_layout,done_layout;
    BroadcastReceiver broadcastReceiver;
    boolean Microphone_Plugged_in=false;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_play_audio);





        playView = PlayAudioActivity.this;
        activity = PlayAudioActivity.this;
        audio_day_tv = (TextView) findViewById(R.id.audio_day_tv);
        audio_day_title = (TextView) findViewById(R.id.audio_day_title);
        repeat_layout = (LinearLayout) findViewById(R.id.repeat_layout);
        done_layout = (LinearLayout) findViewById(R.id.done_layout);
        audio_name_tv = (TextView) findViewById(R.id.audio_name_tv);
        play_audio_img = (ImageView) findViewById(R.id.play_audio_img);
        pause_audio_img = (ImageView) findViewById(R.id.pause_audio_img);
        download_image = (ImageView) findViewById(R.id.download_image);
        fav_img = (ImageView) findViewById(R.id.fav_img);
        download_progress = (ProgressBar) findViewById(R.id.download_progress);
        play_progress = (CircleSeekBar) findViewById(R.id.play_progress);
        time_remaining_tv = (TextView) findViewById(R.id.time_remaining_tv);
        total_time_tv = (TextView) findViewById(R.id.total_time_tv);
        timer_layout = (RelativeLayout) findViewById(R.id.timer_layout);
        // timer_view = (View) findViewById(R.id.timer_view);
        back_icon_img = (ImageView) findViewById(R.id.back_icon_img);
        progress_wave = (SeekBar) findViewById(R.id.progress_wave);

        progress_wave.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        headphone = (ImageView) findViewById(R.id.headphone);
        audio_main_title = (TextView) findViewById(R.id.audio_main_title);

        headphone.setImageDrawable(getResources().getDrawable(R.drawable.m3));
        audio_main_title.setText("Connect Your Head Phone");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        play_audio_img.setOnClickListener(this);
        pause_audio_img.setOnClickListener(this);
        download_image.setOnClickListener(this);
        back_icon_img.setOnClickListener(this);
        repeat_layout.setOnClickListener(this);
        done_layout.setOnClickListener(this);

        seekbar = (SeekBar)findViewById(R.id.seekBar);
        seekbar.setClickable(false);

        playImplementation = new PlayImplementation(playView, activity, getIntent(), play_progress, PlayAudioActivity.this, progress_wave, seekbar);

        playImplementation.hideAllFields();
        playImplementation.getAudioArgumentDetails();

        if(PlayAudioActivity.class.getSimpleName().equals("PlayAudioActivity")){

            fav_img.setVisibility(View.GONE);
        }

        //COde for detect headphone

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                int iii;
                if (Intent.ACTION_HEADSET_PLUG.equals(action)) {
                    iii = intent.getIntExtra("state", -1);

                    if(!Microphone_Plugged_in){

                        if (iii == 0) {
                            headphone.setImageDrawable(getResources().getDrawable(R.drawable.m3));
                            audio_main_title.setText("Connect Your Head Phone");
                        }
                        if (iii == 1) {
                            headphone.setImageDrawable(getResources().getDrawable(R.drawable.m2));
                            audio_main_title.setText("Please find a quite and comfortable place to sit down");
                        }

                    }

                }
            }
        };

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.play_audio_img) {
            playImplementation.playAudio();
        } else if (id == R.id.pause_audio_img) {
            playImplementation.pauseAudio();
        } else if (id == R.id.download_image) {
            playImplementation.downloadAudio();
        } else if (id == R.id.back_icon_img) {
            playImplementation.backIconPressed();
        } else if (id == R.id.repeat_layout) {
            playImplementation.repeataudio();
        } else if (id == R.id.done_layout) {
            playImplementation.doneaudio();
        }
    }

    @Override
    public void displayDownload() {
        download_image.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayDownloadProgress() {
        download_progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayPlay() {
        pause_audio_img.setVisibility(View.INVISIBLE);
        play_audio_img.setVisibility(View.VISIBLE);
        play_progress.setVisibility(View.GONE);
        progress_wave.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayPause() {
        pause_audio_img.setVisibility(View.VISIBLE);
        play_audio_img.setVisibility(View.INVISIBLE);
        headphone.setImageDrawable(getResources().getDrawable(R.drawable.m1));
        audio_main_title.setText("");
        Microphone_Plugged_in=true;

    }

    @Override
    public void displayPlayProgress() {
        play_progress.setVisibility(View.GONE);
    }

    @Override
    public void displayAllTimer() {
        timer_layout.setVisibility(View.VISIBLE);
        // timer_view.setVisibility(View.VISIBLE);
        time_remaining_tv.setVisibility(View.VISIBLE);
        total_time_tv.setVisibility(View.VISIBLE);
        progress_wave.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableDownload() {
        download_image.setClickable(false);
    }

    @Override
    public void displayRepeatDOne() {

        repeat_layout.setVisibility(View.VISIBLE);
        done_layout.setVisibility(View.VISIBLE);

    }

    @Override
    public void hidedisplayRepeatDOne() {

        repeat_layout.setVisibility(View.GONE);
        done_layout.setVisibility(View.GONE);

    }

    @Override
    public void hideAllField() {
        play_audio_img.setVisibility(View.INVISIBLE);
        pause_audio_img.setVisibility(View.INVISIBLE);
        play_progress.setVisibility(View.INVISIBLE);
        time_remaining_tv.setVisibility(View.INVISIBLE);
        total_time_tv.setVisibility(View.INVISIBLE);
        download_image.setVisibility(View.INVISIBLE);
        download_progress.setVisibility(View.INVISIBLE);
        timer_layout.setVisibility(View.INVISIBLE);
        // timer_view.setVisibility(View.INVISIBLE);
        progress_wave.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setAudioName(String audio_name) {
        audio_day_title.setText(audio_name);
        audio_day_tv.setText("My Coach");
    }

    @Override
    public void setAudioDay(String description) {
        audio_name_tv.setText(description);
    }

    @Override
    public void setTotalDuration(String total_duration) {
        total_time_tv.setText(total_duration);
    }

    @Override
    public void setRemainingDuration(String remaining_duration) {
        time_remaining_tv.setText(remaining_duration);
    }

    @Override
    public void updateProgressWave(int progress) {
        progress_wave.setProgress(progress);
    }

    @Override
    public void hideDownloadAll() {
        download_image.setVisibility(View.INVISIBLE);
        download_progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progress_wave.setVisibility(View.GONE);
        play_progress.setVisibility(View.GONE);
    }

    @Override
    public void hideDayAfter31() {
        //audio_day_tv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {

        playImplementation.backIconPressed();

    }

    @Override
    protected void onPause() {
        unregisterReceiver(broadcastReceiver);
        playImplementation.onPause();
        super.onPause();

    }

    @Override
    protected void onStop() {
        playImplementation.onStop();
        super.onStop();

    }

    @Override
    protected void onRestart() {
        playImplementation.onRestart();
        super.onRestart();

    }

    @Override
    protected void onResume() {
        IntentFilter receiverFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(broadcastReceiver, receiverFilter);

        playImplementation.onResume();
        super.onResume();

    }


    @Override
    protected void onDestroy() {
        playImplementation.onDestroy();
        super.onDestroy();

    }



}
