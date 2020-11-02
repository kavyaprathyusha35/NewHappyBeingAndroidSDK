package com.nsmiles.happybeingsdklib.MindGym;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.MyCoach.CoachModel;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.ServerApiConnectors.API_Response_Listener;
import com.nsmiles.happybeingsdklib.ServerApiConnectors.ApiProvider;
import com.nsmiles.happybeingsdklib.Services.DownloadService;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;
import com.nsmiles.happybeingsdklib.Utils.SdkPreferenceManager;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import io.feeeei.circleseekbar.CircleSeekBar;

public class PlayRelaxAudioActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {

    ImageView play_audio_img, download_image, pause_audio_img;
    TextView audio_name_tv,audio_day_title;
    ProgressBar download_progress;
    private CircleSeekBar play_progress;
    Intent intent;
    String emotion;
    File file;
    MediaPlayer mediaPlayer;
    Uri audioUri;
    CommonUtils commonUtils;
    MySql dbHelper;
    SQLiteDatabase db;
    boolean Microphone_Plugged_in=false;
    Activity activity;
    Context context;
    String login_status = "", user_email = "", user_id = "";
    String audio_title = "", audio_base_url = "", audio_sub_url = "", sd_card_path = "", download_status = "", audio_id, audio_number, fav_status, description="";
    public static final String MESSAGE_PROGRESS = "message_progress";
    String favorite;
    LinearLayout repeat_layout,done_layout;


    private boolean fromOnPause = false;
    static boolean active = false;
    boolean isPlaying = false;
    boolean user_paid = false;
    Handler durationHandler = new Handler();
    private double duration, timeRemaining;
    int totalDuration = 0, convert_to_seconds = 0;
    int timeElapsed = 0;
    int current_time = 0;
    SeekBar progress_wave;
    RelativeLayout timer_layout;
    TextView time_remaining_tv;
    TextView total_time_tv, audio_day_tv, audio_main_title;
    ImageView back_icon_img;
    ImageView fav_img, headphone;
    BroadcastReceiver broadcastReceiver1;
    String START_DATE = "", END_DATE = "";
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z", Locale.getDefault());
    SdkPreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_play_audio);
        init();
        preferenceManager = new SdkPreferenceManager(this);
        checkAudioAvailability();


        broadcastReceiver1 = new BroadcastReceiver() {
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

    private void init() {




        try {
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            registerReceiver();
            commonUtils = new CommonUtils();
            activity = PlayRelaxAudioActivity.this;
            context = PlayRelaxAudioActivity.this;
            user_email = commonUtils.getUserEmail(activity);
            audio_day_title = findViewById(R.id.audio_day_title);
            audio_name_tv = (TextView) findViewById(R.id.audio_name_tv);
            play_audio_img = (ImageView) findViewById(R.id.play_audio_img);
            pause_audio_img = (ImageView) findViewById(R.id.pause_audio_img);
            download_image = (ImageView) findViewById(R.id.download_image);
            download_progress = (ProgressBar) findViewById(R.id.download_progress);
            play_progress = (CircleSeekBar) findViewById(R.id.play_progress);
            progress_wave = (SeekBar) findViewById(R.id.progress_wave);


            progress_wave.setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

            timer_layout = findViewById(R.id.timer_layout);
            time_remaining_tv = (TextView) findViewById(R.id.time_remaining_tv);
            total_time_tv = (TextView) findViewById(R.id.total_time_tv);
            START_DATE = df.format(Calendar.getInstance().getTime());
            active = true;
            headphone = (ImageView) findViewById(R.id.headphone);
            audio_main_title = findViewById(R.id.audio_main_title);
            repeat_layout = (LinearLayout) findViewById(R.id.repeat_layout);
            done_layout = (LinearLayout) findViewById(R.id.done_layout);
            headphone.setImageDrawable(getResources().getDrawable(R.drawable.m3));
            audio_main_title.setText("Connect Your Head Phone");

            play_audio_img.setOnClickListener(this);
            pause_audio_img.setOnClickListener(this);
            download_image.setOnClickListener(this);
            repeat_layout.setOnClickListener(this);
            done_layout.setOnClickListener(this);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            back_icon_img = (ImageView) findViewById(R.id.back_icon_img);
            back_icon_img.setOnClickListener(this);

            fav_img =  findViewById(R.id.fav_img);
            fav_img.setOnClickListener(this);
            audio_day_tv = findViewById(R.id.audio_day_tv);
            audio_day_tv.setText("MindSpa");

            play_progress.setCurProcess(0);
            //    play_progress.setUnreachedColor(getResources().getColor(R.color.app_orange));
            //    play_progress.setReachedColor(getResources().getColor(R.color.white));
            //    play_progress.setPointerColor(getResources().getColor(R.color.app_orange));


            play_progress.setOnSeekBarChangeListener(new CircleSeekBar.OnSeekBarChangeListener() {
                @Override
                public void onChanged(CircleSeekBar seekbar, int curValue) {
                    //   mTextView.setText("value:" + curValue);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private AudioManager.OnAudioFocusChangeListener focusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    switch (focusChange) {

                        case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK):
                            // Lower the volume while ducking.
                            mediaPlayer.setVolume(0.2f, 0.2f);
                            break;
                        case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT):
                            pauseMedia();
                            break;

                        case (AudioManager.AUDIOFOCUS_LOSS):
                            stopMedia();
                            ComponentName component = new ComponentName(PlayRelaxAudioActivity.this, MediaControlReceiver.class);
                            am.unregisterMediaButtonEventReceiver(component);
                            break;

                        case (AudioManager.AUDIOFOCUS_GAIN):
                            // Return the volume to normal and resume if paused.
                            mediaPlayer.setVolume(1f, 1f);
                            mediaPlayer.start();
                            break;
                        default:
                            break;
                    }
                }
            };


    private void checkAudioAvailability() {


        try {
            intent = getIntent();
            if (intent.hasExtra("AUDIO_ID") && intent.hasExtra("AUDIO_NUMBER")) {
                audio_id = intent.getStringExtra("AUDIO_ID");
                audio_number = intent.getStringExtra("AUDIO_NUMBER");
                fav_img = findViewById(R.id.fav_img);
                if (intent.hasExtra("FAVOURITE")) {
                    fav_status = intent.getStringExtra("FAVOURITE");
                    if (fav_status.equalsIgnoreCase("true")) {
                        fav_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav_large_filled));
                    } else {
                        fav_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav));
                    }
                }

                dbHelper = new MySql(this, "mydb", null, MySql.version);
                db = dbHelper.getWritableDatabase();

                @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM relax_audio WHERE _ID=? AND AUDIO_NUMBER=?", new String[]{audio_id, audio_number});
                if (cursor.getCount() > 0) {
                    cursor.moveToLast();
                    audio_title = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_TITLE"));
                    audio_base_url = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_BASE_URL"));
                    audio_sub_url = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_SUB_URL"));
                    sd_card_path = cursor.getString(cursor.getColumnIndexOrThrow("SD_CARD_PATH"));
                    download_status = cursor.getString(cursor.getColumnIndexOrThrow("DOWNLOAD_STATUS"));
                    description = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_DESCRIPTION"));

                    if (description != null) {
                        audio_name_tv.setText(description);
                    }

                    if (audio_title != null) {
                        audio_day_title.setText(audio_title);
                    }
                } else {
                    finish();
                    CommonUtils.showToast(activity, "Audio not available");
                }

            } else {
                finish();
                CommonUtils.showToast(activity, "Audio not available");
            }
            /// Audio File Available
            if (download_status.equalsIgnoreCase("1")) {

                file = new File(sd_card_path);
                Log.i("Play", "sd card path is "+sd_card_path);
                if (file.exists()) {

                    long length = file.length();
                    System.out.println("File Path : " + length +" Bytes");

                    length = length / (1024*1024);
                    System.out.println("File Path : " + length +" MB");

                    if (mediaPlayer == null) {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setOnCompletionListener(this);
                        mediaPlayer.setOnErrorListener(this);
                        mediaPlayer.setOnPreparedListener(this);
                        mediaPlayer.setOnBufferingUpdateListener(this);
                        mediaPlayer.reset();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        audioUri = Uri.parse(sd_card_path);
                        try {
                            mediaPlayer.setDataSource(PlayRelaxAudioActivity.this, audioUri);
                            mediaPlayer.prepare();
                            pause_audio_img.setVisibility(View.GONE);
                            download_image.setVisibility(View.GONE);
                            download_progress.setVisibility(View.GONE);
                            play_progress.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }  //  File exists if end block

                } else {
                    pause_audio_img.setVisibility(View.GONE);
                    play_audio_img.setVisibility(View.GONE);
                    play_progress.setVisibility(View.GONE);
                    download_image.setVisibility(View.VISIBLE);

                }  // File not exist else end block

            } /// download status 1 if end block

            else {
                pause_audio_img.setVisibility(View.GONE);
                play_audio_img.setVisibility(View.GONE);
                play_progress.setVisibility(View.GONE);
                download_image.setVisibility(View.VISIBLE);

            }  /// download status else end block


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        Log.i("Relax Fragment", "In on resume");
        active = true;
        IntentFilter receiverFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(broadcastReceiver1, receiverFilter);
        int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        if(android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            }
        }
        super.onResume();
    }

    @Override
    protected void onRestart() {
        try {
            active = true;
            if (mediaPlayer != null) {
                playMedia();
            } else {
                finish();
            }
            super.onRestart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onPause() {
        Log.i("AudioScreen", "IN on pause of activity");
        unregisterReceiver(broadcastReceiver1);
        fromOnPause = true;
        pauseMedia();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        active = false;
        stopMedia();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.play_audio_img) {
            try {
                if (!sd_card_path.equalsIgnoreCase("")) {

                    if (mediaPlayer == null) {
                        Log.i("Play", "PlayRelax audio sd card path is " + sd_card_path);
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setOnCompletionListener(this);
                        mediaPlayer.setOnErrorListener(this);
                        mediaPlayer.setOnPreparedListener(this);
                        mediaPlayer.setOnBufferingUpdateListener(this);
                        mediaPlayer.reset();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        audioUri = Uri.parse(sd_card_path);
                        mediaPlayer.setDataSource(PlayRelaxAudioActivity.this, audioUri);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        play_audio_img.setVisibility(View.GONE);
                        pause_audio_img.setVisibility(View.VISIBLE);
                        headphone.setImageDrawable(getResources().getDrawable(R.drawable.m1));
                        audio_main_title.setText("");

                        play_progress.setVisibility(View.VISIBLE);
                    }
                    playMedia();

                } else {
                    pause_audio_img.setVisibility(View.GONE);
                    play_audio_img.setVisibility(View.GONE);
                    play_progress.setVisibility(View.GONE);
                    download_image.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.pause_audio_img) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                isPlaying = false;
                play_audio_img.setVisibility(View.VISIBLE);
                pause_audio_img.setVisibility(View.GONE);
            }
        } else if (id == R.id.download_image) {
            if (CommonUtils.isNetworkAvailable(PlayRelaxAudioActivity.this)) {
                download_progress.setVisibility(View.VISIBLE);
                download_image.setClickable(false);
                download_image.setFocusable(false);
                Intent intent = new Intent(this, DownloadService.class);
                intent.putExtra("URL", audio_sub_url);
                intent.putExtra("FILE_NAME", audio_title);
                intent.putExtra("AUDIO_BASE_URL", audio_base_url);
                intent.putExtra("AUDIO_ID", audio_id);
                startService(intent);
            } else {
                CommonUtils.showToast(PlayRelaxAudioActivity.this, getResources().getString(R.string.no_network));
            }
        } else if (id == R.id.back_icon_img) {
            if (durationHandler != null) {
                durationHandler.removeCallbacks(updateSeekBarTime);
            }
            finish();
        } else if (id == R.id.fav_img) {
            addOrRemoveFavourite();
        } else if (id == R.id.repeat_layout) {
            try {
                if (mediaPlayer == null) {
                    Log.i("Play", "PlayRelax audio sd card path is " + sd_card_path);
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setOnCompletionListener(this);
                    mediaPlayer.setOnErrorListener(this);
                    mediaPlayer.setOnPreparedListener(this);
                    mediaPlayer.setOnBufferingUpdateListener(this);
                    mediaPlayer.reset();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    audioUri = Uri.parse(sd_card_path);
                    mediaPlayer.setDataSource(PlayRelaxAudioActivity.this, audioUri);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    play_audio_img.setVisibility(View.GONE);
                    pause_audio_img.setVisibility(View.VISIBLE);
                    headphone.setImageDrawable(getResources().getDrawable(R.drawable.m1));
                    audio_main_title.setText("");
                    repeat_layout.setVisibility(View.GONE);
                    done_layout.setVisibility(View.GONE);
                    play_progress.setVisibility(View.VISIBLE);
                }
                playMedia();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.done_layout) {
            howAreYouFeeling(activity, "MY_GUIDE_ANDROID", audio_title);
        }
    }


    private void addOrRemoveFavourite() {

        try {
            dbHelper = new MySql(this, "mydb", null, MySql.version);
            db = dbHelper.getWritableDatabase();

            @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM relax_favourite WHERE AUDIO_NUMBER=?", new String[]{audio_number});
            if (cursor.getCount() > 0) {
                // if available remove it from list
                cursor.moveToLast();
                favorite = cursor.getString(cursor.getColumnIndexOrThrow("FAVOURITE"));
                db.delete("relax_favourite", "EMAIL" + " = ? AND AUDIO_NUMBER=? ", new String[]{user_email, audio_number});
                fav_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav_large));
            } else {
                // add it from list
                dbHelper = new MySql(this, "mydb", null, MySql.version);
                db = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("EMAIL", commonUtils.getUserEmail(activity));
                cv.put("FAVOURITE", "true");
                cv.put("AUDIO_NUMBER", audio_number);
                cv.put("AUDIO_TITLE", audio_title);
                db.insert("relax_favourite", null, cv);
                fav_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav_large_filled));
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    @Override
    public void onBackPressed() {
        active = false;
        stopMedia();
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            finish();


        } else {
            getFragmentManager().popBackStack();
        }

    }


    @Override
    protected void onStop() {
        Log.i("AudioScreen", "IN on stop method");
        active = false;
        if (!fromOnPause)
            stopMedia();
        super.onStop();
    }

    private Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            if (mediaPlayer != null) {
                if (active) {
                    updateProgress();
                    durationHandler.postDelayed(this, 100);
                }
            }
        }
    };

    private void updateProgress() {
        //  Log.i("updateProgress", "Enter");
        timeElapsed = mediaPlayer.getCurrentPosition();
        progress_wave.setProgress(timeElapsed);
        timeRemaining = duration - timeElapsed;
        current_time = mediaPlayer.getCurrentPosition();

        if (current_time > 0) {
            setRemainingTime(current_time);
        }
        //   Log.i("updateProgress", "Exit");
       // updateCircle();
    }


    private void setRemainingTime(final int time) {
    /*    time_remaining_tv.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) time),
                TimeUnit.MILLISECONDS.toSeconds((long) time)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) time))));
*/
    /*

        time_remaining_tv.setText(String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))));

    */


        time_remaining_tv.setText(String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(time) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))));

    }


    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.i("onCompletion", "Enter");
        try {
            durationHandler.removeCallbacks(updateSeekBarTime);
            stopMedia();
            play_audio_img.setVisibility(View.VISIBLE);
            pause_audio_img.setVisibility(View.GONE);
            play_progress.setVisibility(View.GONE);
            updateStatusOfAudioToServer();
            repeat_layout.setVisibility(View.VISIBLE);
            done_layout.setVisibility(View.VISIBLE);
//            CommonUtils.showHowAreYouFeeling(PlayRelaxAudioActivity.this, commonUtils.getUserId(PlayRelaxAudioActivity.this),
//                    "RELAX_RELIEVE_ANDROID", audio_name_tv.getText().toString(), "RELAX");

        } catch (Exception e) {
            Log.e("Exception", "" + e);
            e.printStackTrace();
        }
        Log.i("onCompletion", "Exit");
    }

    private void updateStatusOfAudioToServer() {
        Log.i("PlayRelax", "In update tp server");
        if (CommonUtils.isNetworkAvailable(this)) {
            CoachModel coachModel = new CoachModel();
            coachModel.setEmail(preferenceManager.get(AppConstants.SDK_EMAIL, ""));
            coachModel.setDescription(description);
            coachModel.setUrl(audio_base_url+audio_sub_url);
            coachModel.setTitle(audio_title);
            coachModel.setCompleted_date(new Date().toString());
            new ApiProvider.SaveRelaxAudioData(coachModel, commonUtils.getTokenId(PlayRelaxAudioActivity.this), 22, new API_Response_Listener<String>() {
                @Override
                public void onComplete(String data, long request_code, String failure_code) {
                    Log.i("PlayRelac", "In on complete of audio");
                    if (data == null) {
                        saveItToLocalDB();
                    }
                }
            }).call();

        }
        else {
            saveItToLocalDB();
        }
    }

    private void saveItToLocalDB() {
        MySql dbHelper = new MySql(this, "mydb", null, MySql.version);   // MySql.version
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_id", "user_id");
        cv.put("email", preferenceManager.get(AppConstants.SDK_EMAIL, ""));
        cv.put("title", audio_title);
        cv.put("mind_gym_type", "RELAX_AUDIO");
        cv.put("description", description);
        cv.put("url", audio_base_url+audio_sub_url);
        cv.put("mycoach_completed_date", new Date().toString());
        cv.put("sync_flag", "0");
        db.insert("New_MyCoach_Affirmation_Table", null, cv);
        db.close();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        //Invoked when there has been an error during an asynchronous operation
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Log.d("MediaPlayer Error", "MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK " + extra);
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Log.d("MediaPlayer Error", "MEDIA ERROR SERVER DIED " + extra);
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Log.d("MediaPlayer Error", "MEDIA ERROR UNKNOWN " + extra);
                break;
        }
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        totalDuration = mediaPlayer.getDuration();
        timer_layout.setVisibility(View.VISIBLE);
        progress_wave.setMax(totalDuration);
        duration = mediaPlayer.getDuration();
        convert_to_seconds = (totalDuration / 1000);
        total_time_tv.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) duration),
                TimeUnit.MILLISECONDS.toSeconds((long) duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) duration))));
        //   start();
    }

    private void playMedia() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isPlaying = true;
            Microphone_Plugged_in=true;
            play_progress.setVisibility(View.VISIBLE);
            play_audio_img.setVisibility(View.GONE);
            pause_audio_img.setVisibility(View.VISIBLE);
            repeat_layout.setVisibility(View.GONE);
            done_layout.setVisibility(View.GONE);
            headphone.setImageDrawable(getResources().getDrawable(R.drawable.m1));
            audio_main_title.setText("");
            durationHandler.postDelayed(updateSeekBarTime, 100);
        }
    }

    private void pauseMedia() {
        Log.i("AudioScreen", "IN pause media method");
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                isPlaying = false;
                play_audio_img.setVisibility(View.VISIBLE);
                pause_audio_img.setVisibility(View.GONE);
                durationHandler.removeCallbacks(updateSeekBarTime);
            }
        }

    }

    private void stopMedia() {
        Log.i("AudioScreen", "IN stop media method");
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                mediaPlayer.stop();
                isPlaying = false;
                durationHandler.removeCallbacks(updateSeekBarTime);
            }
        }
    }


    private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            // Test for incoming call, dialing call, active or on hold
            if (state == TelephonyManager.CALL_STATE_RINGING || state == TelephonyManager.CALL_STATE_OFFHOOK) {
                pauseMedia();  // Put here the code to stop your music
            }
            super.onCallStateChanged(state, incomingNumber);
        }


    };


    private void registerReceiver() {

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                if (intent.getAction().equals(MESSAGE_PROGRESS)) {

                    AudioDownload download = intent.getParcelableExtra("download");

                    if (download.getProgress() == 100) {

                        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM relax_audio WHERE _ID=? AND AUDIO_NUMBER=?", new String[]{audio_id, audio_number});
                        if (cursor.getCount() > 0) {
                            cursor.moveToLast();

                            sd_card_path = cursor.getString(cursor.getColumnIndexOrThrow("SD_CARD_PATH"));
                            download_status = cursor.getString(cursor.getColumnIndexOrThrow("DOWNLOAD_STATUS"));
                            file = new File(sd_card_path);
                            if (file.exists()) {

                                // activity is alive
                                if (active) {
                                    play_audio_img.setVisibility(View.VISIBLE);

                                    if (mediaPlayer == null) {
                                        mediaPlayer = new MediaPlayer();
                                        mediaPlayer.setOnCompletionListener(PlayRelaxAudioActivity.this);
                                        mediaPlayer.setOnErrorListener(PlayRelaxAudioActivity.this);
                                        mediaPlayer.setOnPreparedListener(PlayRelaxAudioActivity.this);
                                        mediaPlayer.setOnBufferingUpdateListener(PlayRelaxAudioActivity.this);
                                        mediaPlayer.reset();
                                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                        audioUri = Uri.parse(sd_card_path);
                                        try {
                                            mediaPlayer.setDataSource(PlayRelaxAudioActivity.this, audioUri);
                                            mediaPlayer.prepare();
                                            download_image.setVisibility(View.GONE);
                                            download_progress.setVisibility(View.GONE);
                                            play_audio_img.setVisibility(View.VISIBLE);
                                            play_progress.setVisibility(View.VISIBLE);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                } //  Activity alive
                            }  //  File exists if end block

                            else {
                                download_image.setVisibility(View.VISIBLE);

                            }  // File not exist else end block
                        }

                    } else {
                        // update progress bar... currently cant able to get the audio file size, in the audio source need to add the size information
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void howAreYouFeeling(final Activity activity, final String activity_name, final String task_name) {

        final ImageView feeling_image, better_img, calm_img, confident_img, motivated_img, thankful_img;
        Button save_btn;
        SeekBar seekBar;
        final TextView feelings;
        final RelativeLayout relativeLayout;
        //  sad better  calm   confident  motivated  energised
        final Dialog d = new Dialog(activity,R.style.full_screen_dialog);
        d.setContentView(R.layout.dialog_audio_done_screen);
        assert d.getWindow() != null;
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //   d.setTitle("Happy Being");
        d.setCancelable(false);
        d.setCanceledOnTouchOutside(false);
        d.show();

        seekBar=(SeekBar)d.findViewById(R.id.seekbar);
        feelings=(TextView) d.findViewById(R.id.feelings);
        feeling_image = (ImageView) d.findViewById(R.id.feeling_image);
        relativeLayout = (RelativeLayout) d.findViewById(R.id.relative);
        feeling_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.thankful_));
        feelings.setText("Thankful");
        relativeLayout.setBackgroundColor(Color.parseColor("#cee279"));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(progress<=16){

                    feeling_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.sad_));
                    feelings.setText("sad");
                    relativeLayout.setBackgroundColor(Color.parseColor("#f7b089"));
                    emotion="sad";

                }else if(progress<=32){

                    feeling_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.calm));
                    feelings.setText("calm");
                    relativeLayout.setBackgroundColor(Color.parseColor("#f4d3b8"));
                    emotion="calm";



                }else if(progress<=48){

                    feeling_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.better));
                    feelings.setText("better");
                    relativeLayout.setBackgroundColor(Color.parseColor("#f9eb9b"));
                    emotion="better";

                }else if(progress<=64){

                    feelings.setText("confident");
                    feeling_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.confident_));
                    relativeLayout.setBackgroundColor(Color.parseColor("#ebf4ba"));
                    emotion="confident";

                }else if(progress<=80){

                    feelings.setText("motivated");
                    feeling_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.motivated_));
                    relativeLayout.setBackgroundColor(Color.parseColor("#def498"));
                    emotion="motivated";
                }else if(progress<=100){

                    feelings.setText("Thankful");
                    feeling_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.thankful_));
                    relativeLayout.setBackgroundColor(Color.parseColor("#cee279"));
                    emotion="Thankful";
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        save_btn = (Button) d.findViewById(R.id.done_button_n);


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isNetworkAvailable(activity)) {

                    if (emotion.length() > 0) {

                        AddEmotionRequest emotionss=new AddEmotionRequest();
                        emotionss.setUser_id(user_id);
                        emotionss.setDate_time(new Date().toString());
                        emotionss.setFeature("RELAX");
                        emotionss.setEmotion1(emotion);
                        emotionss.setActivity(task_name);

                        new ApiProvider.SaveEmotions(emotionss, AppConstants.DEFAULT_TOKEN, 2, activity, "Saving...", new API_Response_Listener<String>() {

                            @Override
                            public void onComplete(String data, long request_code, String failure_code) {
                                Log.e("failure_code", failure_code);
                                if (data == null) {
                                    Log.e("data", "null");
                                } else {

                                    if (emotion.length() > 0) {
                                        MySql dbHelper = new MySql(activity, "mydb", null, MySql.version);
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                                        ContentValues cv = new ContentValues();
                                        cv.put("date_time", new Date().toString());
                                        cv.put("feature", activity_name);
                                        cv.put("activity", task_name);
                                        cv.put("emotion1", emotion);
                                        cv.put("sync_flag", "0");
                                        db.insert("New_Feelings_Table", null, cv);
                                        db.close();
                                        d.dismiss();
                                        activity.finish();
                                        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                        emotion = "";
                                    } else {
                                        CommonUtils.showToast(activity, "Please select you feelings...");
                                    }


                                }
                            }
                        }).call();

                    }else {
                        CommonUtils.showToast(activity, "Please select you feelings...");
                    }

                }
                else {
                    if (emotion.length() > 0) {
                        MySql dbHelper = new MySql(activity, "mydb", null, MySql.version);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put("date_time", new Date().toString());
                        cv.put("feature", activity_name);
                        cv.put("activity", task_name);
                        cv.put("emotion1", emotion);
                        cv.put("sync_flag", "0");
                        db.insert("New_Feelings_Table", null, cv);
                        db.close();
                        d.dismiss();
                        activity.finish();
                        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        emotion = "";
                    } else {
                        CommonUtils.showToast(activity, "Please select you feelings...");
                    }

                }

                activity.finish();

            }

        });
    }

}

