package com.nsmiles.happybeingsdklib.Affimations;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.ServerApiConnectors.API_Response_Listener;
import com.nsmiles.happybeingsdklib.ServerApiConnectors.ApiProvider;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;
import com.nsmiles.happybeingsdklib.Utils.SdkPreferenceManager;

import java.util.Date;


/**
 * Created by Kavya on 3/19/2020.
 */
public class AffirmationScreen extends Activity implements Animation.AnimationListener {


    TextView affirmationTextView;
    RelativeLayout affirmation_parent;
    ScrollView introduction_layout;
    Button start_affirmations;
    int affirmationDayFromIntent = 0, affirmationDayIndb = 0, affirmationDateNumber = 0, affirmationTextNumber = 0;
    MySql sqlDatabase;
    Animation fade_out, fade_in;
    String[] affirmations = {};
    private CommonUtils commonUtils;
    String id,  audio_base_url,  audio_sub_url,  download_status = "", sd_card_path,audio_number;
    private MediaPlayer mMediaPlayer;
    SdkPreferenceManager sdkPreferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_affirmation_layout);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        introduction_layout = findViewById(R.id.introduction_layout);
        start_affirmations = findViewById(R.id.start_affirmations);
        affirmationTextView = findViewById(R.id.tv_mind_gym_affirmation_desc);
        affirmation_parent = findViewById(R.id.affirmation_parent);
        sqlDatabase = new MySql(this,"mydb", null, MySql.version);
        commonUtils = new CommonUtils();
        sdkPreferenceManager = new SdkPreferenceManager(this);
        Intent intent = getIntent();
        if (intent.hasExtra("Affirmation_day")) {
            affirmationDayFromIntent = intent.getIntExtra("Affirmation_day", 0);
        }
        //pickRandomImageForBackground();

        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fade_in.setAnimationListener(this);
        fade_out.setAnimationListener(this);
        if (intent.hasExtra("FROM_BOOSTER")) {
            showAffirmationOfDate(30);
        }
        else {
            getValuesFromDB();
        }
       // start_affirmations.setOnClickListener(this);
        setTheAffirmationText();
    }

    @Override
    protected void onPause() {
        Log.i("Affirmation", "In on Pause");
        pauseBackgroundAudio();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("Affirmation", "In on stop");
        stopBackgroundAudio();
        super.onStop();
    }


    private void getValuesFromDB() {
        MySql dbHelper = new MySql(this, "mydb", null, MySql.version);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Affirmation_Data", null);
        Log.i("NewAffirmation", "Affirmation data table is " + cursor.getCount());
        if (cursor.getCount() > 0) {
            // return true;
            cursor.moveToFirst();
            affirmationDateNumber = cursor.getInt(cursor.getColumnIndex("AFFIRMATION_DATE_NUMBER"));
            affirmationDayIndb = cursor.getInt(cursor.getColumnIndex("AFFIRMATION_DAY"));

        }
        cursor.close();
        db.close();
    }

    private void setTheAffirmationText() {
        MySql dbHelper = new MySql(this, "mydb", null, MySql.version);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if (affirmationDayFromIntent < 20) {
            Log.i("NewAffirmation", "Affirmation dayFrom intent "+affirmationDayFromIntent+" Affirmation from db "+affirmationDayIndb);
            affirmationDateNumber = affirmationDayFromIntent;
        }
        else {
            int affirmation_from_pref = sdkPreferenceManager.get("AFFIRMATION_DAY", 0);
            affirmationDateNumber = affirmation_from_pref;
            affirmation_from_pref = affirmation_from_pref + 1 ;
            if (affirmation_from_pref > 20) {
                affirmation_from_pref = 0;
            }
            sdkPreferenceManager.save("AFFIRMATION_DAY", affirmation_from_pref);
        }

        Log.i("NewAffirmation", "Affirmation date is "+affirmationDateNumber);
        showAffirmationOfDate(affirmationDateNumber);
    }


    private void showAffirmationOfDate(int affirmationDateNumber) {
        AffirmationDataClass affirmationDataClass = new AffirmationDataClass();
        affirmations = affirmationDataClass.getAffirmations(affirmationDateNumber);
        Log.i("NewAffirmation", "Affirmations size is "+affirmations.length);
        affirmationTextView.setText(affirmations[affirmationTextNumber]);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                affirmationTextView.startAnimation(fade_out);
                //scrollingImageView.start();
            }
        }, 9000);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (animation == fade_in) {
            //scrollingImageView.stop();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    affirmationTextView.startAnimation(fade_out);
                }
            }, 9000);

        }

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == fade_out) {
            //scrollingImageView.start();
            if (affirmationTextNumber < affirmations.length - 1) {
                affirmationTextNumber++;
                affirmationTextView.setText(affirmations[affirmationTextNumber]);
                affirmationTextView.startAnimation(fade_in);
            }
            else {
                finishAffirmation();
                //TODO: end of the affirmations
                Log.i("NewAffirmation", "Text completed******");
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private void finishAffirmation() {

        if (CommonUtils.isNetworkAvailable(this)) {
            //Push affirmations to server
            AffirmationModel affirmationModel = new AffirmationModel();
            affirmationModel.setUser_id("user_id");
            affirmationModel.setDate_time(new Date().toString());
            affirmationModel.setEmail(sdkPreferenceManager.get(AppConstants.SDK_EMAIL, ""));
            affirmationModel.setTitle(affirmations[0]);
            affirmationModel.setMind_gym_type("AFFIRMATION");
            affirmationModel.setDescription(affirmations[0]);
            affirmationModel.setUrl(affirmations[0]);
            affirmationModel.setMycoach_completed_days(Integer.toString(affirmationDayFromIntent));
            affirmationModel.setCompleted_date(new Date().toString());
            new ApiProvider.SaveAffirmationData(affirmationModel, commonUtils.getTokenId(AffirmationScreen.this), 2, new API_Response_Listener<String>() {

                @Override
                public void onComplete(String data, long request_code, String failure_code) {
                    MySql dbHelper = new MySql(AffirmationScreen.this, "mydb", null, MySql.version);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    if (data != null) {
                        if (data.equalsIgnoreCase("true")) {
                            Log.d(getClass().getSimpleName(), "true");
                        } else {
                            saveItToLocalDB();
                        }

                    }
                }
            }).call();
        }
        else {
            saveItToLocalDB();
        }
        finish();
    }


    private void saveItToLocalDB() {
        MySql dbHelper = new MySql(AffirmationScreen.this, "mydb", null, MySql.version);   // MySql.version
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_id", "user_id");
        cv.put("server_id", "");
        cv.put("date_time", new Date().toString());
        cv.put("email", sdkPreferenceManager.get(AppConstants.SDK_EMAIL, ""));
        cv.put("title", affirmations[0]);
        cv.put("mind_gym_type", "AFFIRMATION");
        cv.put("description", affirmations[0]);
        cv.put("url", affirmations[0]);
        cv.put("completed_date", affirmationDayFromIntent);
        cv.put("mycoach_completed_date", new Date().toString());
        cv.put("sync_flag", "0");
        db.insert("New_MyCoach_Affirmation_Table", null, cv);
        db.close();
    }

    private void playBackgroundAudio() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer = MediaPlayer.create(this, R.raw.affirmations_background);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(true);
        // mMediaPlayer.setVolume(0.1f, 0.1f);
        mMediaPlayer.start();

    }

    private void pauseBackgroundAudio() {
        Log.i("Affirmation", "In pause audio "+mMediaPlayer);
        if (mMediaPlayer != null) {
            Log.i("Affirmation","Media player is playing"+mMediaPlayer.isPlaying());
            mMediaPlayer.stop();
        }
    }
    private void stopBackgroundAudio() {
        Log.i("Affirmation", "In pause audio "+mMediaPlayer);
        if (mMediaPlayer != null) {
            Log.i("Affirmation","Media player is playing"+mMediaPlayer.isPlaying());
            mMediaPlayer.stop();
        }
    }

//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.start_affirmations) {
//            affirmation_parent.setVisibility(View.VISIBLE);
//            introduction_layout.setVisibility(View.GONE);
//            setTheAffirmationText();
//            playBackgroundAudio();
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
            playBackgroundAudio();
    }
}

