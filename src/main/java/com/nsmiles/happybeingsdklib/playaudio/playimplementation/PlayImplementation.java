package com.nsmiles.happybeingsdklib.playaudio.playimplementation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
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
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.MindGym.AddEmotionRequest;
import com.nsmiles.happybeingsdklib.MindGym.AudioDownload;
import com.nsmiles.happybeingsdklib.MindGym.MediaControlReceiver;
import com.nsmiles.happybeingsdklib.MyCoach.CoachModel;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.ServerApiConnectors.API_Response_Listener;
import com.nsmiles.happybeingsdklib.ServerApiConnectors.ApiProvider;
import com.nsmiles.happybeingsdklib.Services.CoachDownloadService;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;
import com.nsmiles.happybeingsdklib.playaudio.fragment.PlayAudioActivity;
import com.nsmiles.happybeingsdklib.playaudio.playpresenter.PlayPresenter;
import com.nsmiles.happybeingsdklib.playaudio.playview.PlayView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import io.feeeei.circleseekbar.CircleSeekBar;


public class PlayImplementation implements PlayPresenter, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {


    private PlayView playView;
    private Activity activity;
    private Intent intent;
    private String bundle_table_name, relax_coach, audio_name, simple_date, actual_day, play_status, bundle_audio_number, bundle_day_number, audio_id;
    private boolean bundle_replay = false;
    private MySql dbHelper;
    private SQLiteDatabase db;
    @SuppressLint("Recycle")
    Cursor cursor;
    private String download_status, sd_card_path, base_url, sub_url, audio_title, audio_desc, day_number, audio_number;
    private File check_file_available;
    private MediaPlayer mediaPlayer;
    private Uri audioUri;
    private TelephonyManager mTelephonyMgr;
    private Handler durationHandler = new Handler();
    private double duration, timeRemaining;
    private int totalDuration = 0, convert_to_seconds = 0;
    private int timeElapsed = 0;
    private static boolean active = false;
    private boolean isPlaying = false;
    public static final String MESSAGE_PROGRESS = "message_progress";
    private File file;
    private CircleSeekBar play_progress;
    PlayAudioActivity playAudioFragment;

    SeekBar progressBar;
    CommonUtils commonUtils;
    static String emotion = "";
    CoachModel coachModel = new CoachModel();
    SeekBar seekBar;
    private double startTime = 0;
    private double finalTime = 0;
    public static int oneTimeOnly = 0;
    int current_time = 0;
    AudioManager manager;
    int server_day;
    private int bundle_id;
    String user_id;


    public PlayImplementation(PlayView playView, Activity activity, Intent intent,
                              CircleSeekBar play_progress, PlayAudioActivity playAudioFragment, SeekBar progressBar, SeekBar seekbar) {
        this.playView = playView;
        this.activity = activity;
        this.intent = intent;
        commonUtils = new CommonUtils();

        this.play_progress = play_progress;
        this.playAudioFragment = playAudioFragment;
        this.progressBar = progressBar;
        this.seekBar = seekbar;
        mTelephonyMgr = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        manager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        registerReceiver();
        active = true;
        //user_id=commonUtils.getUserId(activity);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if (mediaPlayer != null && mediaPlayer.isPlaying()) {

                    if (b) {
                        mediaPlayer.seekTo(i);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private AudioManager.OnAudioFocusChangeListener focusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    AudioManager am = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
                    switch (focusChange) {

                        case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK):
                            // Lower the volume while ducking.
                            if (mediaPlayer != null)
                                mediaPlayer.setVolume(0.2f, 0.2f);
                            break;
                        case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT):
                            pauseMedia();
                            break;

                        case (AudioManager.AUDIOFOCUS_LOSS):
                            stopMedia();
                            ComponentName component = new ComponentName(activity, MediaControlReceiver.class);
                            assert am != null;
                            am.unregisterMediaButtonEventReceiver(component);
                            break;

                        case (AudioManager.AUDIOFOCUS_GAIN):
                            // Return the volume to normal and resume if paused.
                            if (mediaPlayer != null) {
                                mediaPlayer.setVolume(1f, 1f);
                                mediaPlayer.start();
                            }
                            break;
                        default:
                            break;
                    }
                }
            };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int value = msg.what;
            play_progress.setCurProcess(value);
        }
    };

    @Override
    public void getAudioArgumentDetails() {

        try {
            if(mediaPlayer!=null){
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer=null;
            }

            if (intent != null) {
                bundle_id = intent.getIntExtra("ID", 0);
                bundle_table_name = intent.getStringExtra("TABLE");
                bundle_day_number = intent.getStringExtra("DAY_NUMBER");
                bundle_audio_number = intent.getStringExtra("AUDIO_NUMBER");
                bundle_replay = intent.getBooleanExtra("REPLAY", false);
                relax_coach = intent.getStringExtra("RELAX_COACH");
                audio_name = intent.getStringExtra("AUDIO_NAME");
                simple_date = intent.getStringExtra("SIMPLE_DATE");
                actual_day = intent.getStringExtra("ACTUAL_DAY");
                play_status = intent.getStringExtra("PLAY_STATUS");
             //   CommonUtils.showLogInforamtion(getClass().getSimpleName(), "ssssssiiiiiiii", audio_name, true);

                server_day = Integer.parseInt(actual_day);
/*
                if (server_day > 33) {
                    playView.hideDayAfter31();
                }
*/

                dbHelper = new MySql(activity, "mydb", null, MySql.version);
                db = dbHelper.getWritableDatabase();

                if (relax_coach.equalsIgnoreCase("COACH")) {
                    cursor = db.rawQuery("SELECT * FROM " + bundle_table_name + " WHERE AUDIO_NUMBER=?", new String[]{bundle_audio_number});
                    if (cursor.getCount() > 0) {
                        cursor.moveToLast();
                        audio_id = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("_ID")));
                        audio_title = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_TITLE"));
                        audio_desc = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_DESCRIPTION"));
                        base_url = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_BASE_URL"));
                        sub_url = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_SUB_URL"));
                        download_status = cursor.getString(cursor.getColumnIndexOrThrow("DOWNLOAD_STATUS"));
                        sd_card_path = cursor.getString(cursor.getColumnIndexOrThrow("SD_CARD_PATH"));
                        day_number = cursor.getString(cursor.getColumnIndexOrThrow("DAY_NUMBER"));
                        audio_number = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_NUMBER"));
                        playView.setAudioDay(audio_desc);
                        playView.setAudioName(audio_title);


                        if (sd_card_path != null && sd_card_path.length() > 0) {
                            check_file_available = new File(sd_card_path);
                            if (check_file_available.exists()) {
                                playView.displayPlay();


                                if (mediaPlayer == null) {
                                    mediaPlayer = new MediaPlayer();
                                    mediaPlayer.setOnCompletionListener(this);
                                    mediaPlayer.setOnErrorListener(this);
                                    mediaPlayer.setOnPreparedListener(this);
                                    mediaPlayer.setOnBufferingUpdateListener(this);
                                    mediaPlayer.reset();
                                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                 //   audio_triggered = true;
                                    audioUri = Uri.parse(sd_card_path);
                                    try {
                                        mediaPlayer.setDataSource(activity, audioUri);
                                        mediaPlayer.prepare();
                                        playView.hideAllField();
                                        playView.displayPlay();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                playView.hideAllField();
                                playView.displayDownload();
                            }
                        } else {
                            playView.hideAllField();
                            playView.displayDownload();
                        }

                    }
                }

            }

        } catch (Exception e) {
            playView.hideAllField();
            playView.displayDownload();
            e.printStackTrace();
        }
    }

    public byte[] read(File file) throws IOException {

        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
        } finally {
            try {
                if (ous != null)
                    ous.close();
            } catch (IOException e) {
            }

            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
        return ous.toByteArray();
    }

    @Override
    public void hideAllFields() {

        playView.hideAllField();
    }

    private void initMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //audio_triggered = true;
        }
    }

    @Override
    public void playAudio() {


        try {
            if (mediaPlayer==null) {
                initMediaPlayer();
                audioUri = Uri.parse(sd_card_path);
                mediaPlayer.setDataSource(activity, audioUri);
                mediaPlayer.prepare();
                mediaPlayer.start();
                playView.displayPause();
                playView.displayPlayProgress();
            }
                playMedia();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pauseAudio() {

        pauseMedia();
    }

    @Override
    public void downloadAudio() {
        try {
            //   download_image.setClickable(false);
            //    download_image.setFocusable(false);
            if (CommonUtils.isNetworkAvailable(activity)) {
                playView.disableDownload();
                playView.displayDownloadProgress();
                Log.i("CoauchDownload", "From play implementation"+sub_url+",baase"+base_url);
                if (sub_url != null && audio_number != null) {
                    Intent intent = new Intent(activity, CoachDownloadService.class);
                    intent.putExtra("URL", sub_url);
                    intent.putExtra("FILE_NAME", audio_title);
                    intent.putExtra("AUDIO_BASE_URL", base_url);
                    intent.putExtra("AUDIO_NUMBER", audio_number); //AUDIO_NUMBER   // AUDIO_ID
                    intent.putExtra("UPDATE_TABLE", bundle_table_name);
                    activity.startService(intent);
                    //startSplashScreenService();
                }
            } else {
                CommonUtils.showToast(activity, activity.getResources().getString(R.string.no_network));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void backIconPressed() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                isPlaying = false;
            }
            isMyServiceRunning(CoachDownloadService.class);
        }
        durationHandler.removeCallbacks(updateSeekBarTime);
        activity.finish();
        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                activity.stopService(new Intent(activity, CoachDownloadService.class));
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRestart() {
        Log.i("Play", "On restart");
        try {
            active = true;
            if(mediaPlayer!=null) {
                playMedia();
            }
            else {
                activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStop() {
        active = false;
        pauseMedia();
        durationHandler.removeCallbacks(updateSeekBarTime);
    }

    @Override
    public void onPause() {
        active = false;
        pauseMedia();
        durationHandler.removeCallbacks(updateSeekBarTime);
    }

    @Override
    public void onDestroy() {
        active = false;
        stopMedia();
        durationHandler.removeCallbacks(updateSeekBarTime);
    }

    @Override
    public void onResume() {
        Log.i("Relax Fragment", "In on resume");
        active = true;
        try {
            int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
            if(android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                } else {
                    mTelephonyMgr.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
                }
            }
            else {
                mTelephonyMgr.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
            }
            if (manager.isMusicActive()) {
                Log.d(getClass().getSimpleName(), "playing audio from some other application");
                // Request audio focus for playback
                int result = manager.requestAudioFocus(focusChangeListener,
// Use the music stream.
                        AudioManager.STREAM_MUSIC,
// Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
// other app had stopped playing song now , so u can do u stuff now .
                }
            }

        }
        catch (SecurityException se) {
            se.printStackTrace();
        }
    }

    @Override
    public void doneaudio() {


        howAreYouFeeling(activity, "MY_GUIDE_ANDROID", audio_title);

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


    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.i("onCompletion", "Enter");
        try {
            // show popup


            durationHandler.removeCallbacks(updateSeekBarTime);
            stopMedia();
            mediaPlayer = null;
            playView.hideProgress();
            playView.displayPlay();
            playView.displayRepeatDOne();

            insertCoachAudioEntry();

        } catch (Exception e) {
            Log.e("Exception", "" + e);
            e.printStackTrace();
        }
        Log.i("onCompletion", "Exit");
    }


    private void insertCoachAudioEntry() {
        int completed_days = 0;
        dbHelper = new MySql(activity, "mydb", null, MySql.version);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM New_MyCoach_Audio_Table where email=?",
                new String[]{commonUtils.getUserEmail(activity)}, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            String daysCompleted = cursor.getString(cursor.getColumnIndexOrThrow("mycoach_completed_days"));
            completed_days = Integer.parseInt(daysCompleted) + 1;
        } else {
            completed_days = 1;
        }
        ContentValues cv = new ContentValues();
        cv.put("server_id", "");
        cv.put("date_time", new Date().toString());
        cv.put("email", commonUtils.getUserEmail(activity));
        play_status = "true";
        cv.put("title", audio_title);
        cv.put("mind_gym_type", "AUDIO");
        cv.put("description", audio_title);
        cv.put("url", sub_url);
        cv.put("repeat", play_status);
        cv.put("duration", 0);
        cv.put("mycoach_completed_days", actual_day);
        cv.put("mycoach_completed_date", simple_date);
        cv.put("sync_flag", "0");
        db.insert("New_MyCoach_Audio_Table", null, cv);

        @SuppressLint("Recycle") Cursor cursor1 = db.rawQuery("SELECT * FROM " + bundle_table_name + " WHERE AUDIO_NUMBER=?",
                new String[]{audio_number});

        ContentValues contentValues = new ContentValues();

        if (CommonUtils.isNetworkAvailable(activity)) {
            pushMyCoachData();
        } else {
         //   howAreYouFeeling(activity, "MY_GUIDE_ANDROID", audio_title);
        }
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
        progressBar.setMax(totalDuration);
        duration = mediaPlayer.getDuration();
        convert_to_seconds = (totalDuration / 1000);
        playView.displayAllTimer();
        //  playView.setTotalDuration(String.valueOf(totalDuration));
        playView.setTotalDuration(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) duration),
                TimeUnit.MILLISECONDS.toSeconds((long) duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) duration))));
    }

    private void playMedia() {

        try {
                   playView.displayPlay();

                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    isPlaying = true;
                    durationHandler.postDelayed(updateSeekBarTime, 100);
                    playView.displayPause();
                    playView.displayPlayProgress();
                    finalTime = mediaPlayer.getDuration();
                    startTime = mediaPlayer.getCurrentPosition();
                    playView.hidedisplayRepeatDOne();

                }

        } catch (Exception e) {
            e.printStackTrace();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer=null;
            activity.finish();
        }
    }

    private void pauseMedia() {
        playView.displayPlay();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                isPlaying = false;
                durationHandler.removeCallbacks(updateSeekBarTime);
            }
        }

    }

    private void stopMedia() {
        playView.displayPlay();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                isPlaying = false;
            }
        }
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
        current_time = mediaPlayer.getCurrentPosition();
        seekBar.setProgress((int) timeElapsed);
        playView.updateProgressWave(timeElapsed);
        // Log.i("TAG", "Time Elapsed " + timeElapsed);
        timeRemaining = duration - timeElapsed;

        if (timeRemaining > 0) {
            //      setRemainingTime(timeRemaining);
            setRemainingTime(current_time);
        }
        //   Log.i("updateProgress", "Exit");
        updateCircle();
    }

    private void setRemainingTime(final int time) {
        Log.i("setRemainingTime", "Enter");
/*        playView.setRemainingDuration(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining),
                TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
        //     audio_timer.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
        Log.i("setRemainingTime", "Exit");*/

        playView.setRemainingDuration(String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(time) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))));


    }

    private void updateCircle() {


        play_progress.setMaxProcess(convert_to_seconds);
        new Thread() {
            @Override
            public void run() {
                int ti = (timeElapsed / 1000);
                mHandler.sendEmptyMessage(ti);
                SystemClock.sleep(1000);
            }
        }.start();

    }


    private void registerReceiver() {

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(activity);
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
                    AudioDownload retrofit_audio_number = intent.getParcelableExtra("audio_number");

                 //   CommonUtils.showLogInforamtion(getClass().getSimpleName(), "eeeee" , retrofit_audio_number.getAudio_number() , true);

                    if (download.getProgress() == 100) {

                       if(audio_number.equalsIgnoreCase(download.getAudio_number())){

                           @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + bundle_table_name + " WHERE AUDIO_NUMBER=?",
                                   new String[]{audio_number});
                           if (cursor.getCount() > 0) {
                               cursor.moveToLast();

                               sd_card_path = cursor.getString(cursor.getColumnIndexOrThrow("SD_CARD_PATH"));
                               download_status = cursor.getString(cursor.getColumnIndexOrThrow("DOWNLOAD_STATUS"));
                               file = new File(sd_card_path);
                               if (file.exists()) {

                                   if (active) {
                                       initMediaPlayer();
                                       audioUri = Uri.parse(sd_card_path);
                                       try {
                                           mediaPlayer.setDataSource(activity, audioUri);
                                           mediaPlayer.prepare();
                                           playView.hideAllField();
                                           playView.displayPlay();

                                       } catch (Exception e) {
                                           e.printStackTrace();
                                       }


                                   }
                               }
                               //  File exists if end block
                               else {
                                   //  download_image.setVisibility(View.VISIBLE);

                               }  // File not exist else end block
                           }

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


    private void pushMyCoachData() {
            Log.i("PlayImplementation", "In push my coach data method");
        try {
            dbHelper = new MySql(activity, "mydb", null, MySql.version);
            db = dbHelper.getWritableDatabase();

            if (db.isOpen()) {
                // get data from local db
                @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM New_MyCoach_Audio_Table WHERE sync_flag=?", new String[]{"0"});

                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    coachModel.set_id(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
                    coachModel.setUser_id(cursor.getString(cursor.getColumnIndexOrThrow("user_id")));
                    coachModel.setDate_time(cursor.getString(cursor.getColumnIndexOrThrow("date_time")));
                    coachModel.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                    coachModel.setPrimary_profile(cursor.getString(cursor.getColumnIndexOrThrow("primary_profile")));
                    coachModel.setSecondary_profile(cursor.getString(cursor.getColumnIndexOrThrow("secondary_profile")));
                    coachModel.setMind_gym_type(cursor.getString(cursor.getColumnIndexOrThrow("mind_gym_type")));
                    coachModel.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                    coachModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                    coachModel.setUrl(cursor.getString(cursor.getColumnIndexOrThrow("url")));
                    coachModel.setRepeat(cursor.getString(cursor.getColumnIndexOrThrow("repeat")));
                    coachModel.setMycoach_completed_days(cursor.getString(cursor.getColumnIndexOrThrow("mycoach_completed_days")));
                    coachModel.setCompleted_date(cursor.getString(cursor.getColumnIndexOrThrow("mycoach_completed_date")));
                    coachModel.setSync_flag(cursor.getString(cursor.getColumnIndexOrThrow("sync_flag")));
                    Log.d(getClass().getSimpleName(), coachModel.getSync_flag());

                    new ApiProvider.SaveMyCoachData(coachModel, commonUtils.getTokenId(activity), 2, activity, "Loading...", new API_Response_Listener<String>() {

                        @Override
                        public void onComplete(String data, long request_code, String failure_code) {

                            MySql dbHelper = new MySql(activity, "mydb", null, MySql.version);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();

                            if (data != null) {
                                if (data.equalsIgnoreCase("true")) {
                                    Log.d(getClass().getSimpleName(), "true");
                                    ContentValues cv = new ContentValues();
                                    cv.put("sync_flag", "1");
                                    db.update("New_MyCoach_Audio_Table", cv, "_id" + "=?", new String[]{String.valueOf(coachModel.get_id())});

                                    //call update coach api
                                    commonUtils.getCoachDataFromApi(activity);
                                    Log.i("PlayImplementation", "Showing dialog after completing uploading");
                                  //  howAreYouFeeling(activity, "MY_GUIDE_ANDROID", audio_title);
                                } else {
                                    Log.d(getClass().getSimpleName(), "LIVE DATA INSERTED");
                                }

                            } else {
                              //  howAreYouFeeling(activity, "MY_GUIDE_ANDROID", audio_title);
                            }
                        }
                    }).call();
                    Log.i("PlayImplementation", "showing ************");
                   // howAreYouFeeling(activity, "MY_GUIDE_ANDROID", audio_title);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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
        emotion="Thankful";

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

                    if (emotion.equals("")) {

                        AddEmotionRequest emotionss=new AddEmotionRequest();
                        emotionss.setEmail(commonUtils.getUserEmail(activity));
                        emotionss.setDate_time(new Date().toString());
                        emotionss.setFeature("RELAX");
                        emotionss.setEmotion1(emotion);
                        emotionss.setActivity(task_name);

                        new ApiProvider.SaveEmotions(emotionss, commonUtils.getTokenId(activity), 2, activity, "Saving...", new API_Response_Listener<String>() {

                            @Override
                            public void onComplete(String data, long request_code, String failure_code) {
                                Log.e("failure_code", failure_code);
                                if (data == null) {
                                    Log.e("data", "null");
                                } else {

                                    if (emotion.equals("")) {
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
                    if (emotion.equals("")) {
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

    @Override
    public void repeataudio() {

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //   audio_triggered = true;
            audioUri = Uri.parse(sd_card_path);
            try {
                mediaPlayer.setDataSource(activity, audioUri);
                mediaPlayer.prepare();
                mediaPlayer.start();
                isPlaying = true;
                durationHandler.postDelayed(updateSeekBarTime, 100);
                playView.displayPause();
                playView.displayPlayProgress();
                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();
                playView.hidedisplayRepeatDOne();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
