package com.nsmiles.happybeingsdklib.MindGym;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;

import java.io.File;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class VentoutRecording extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener, CompoundButton.OnCheckedChangeListener
        , MediaPlayer.OnCompletionListener {
    private SeekBar seekBar;
    private TextView recordingTimer;
    private ToggleButton toggleButton, playOrPause;
    Boolean isRecording;
    int recordTime, playTime;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer, mediaPlayer1;
    Handler handler;
    private String recordFilePath;
    public static final String STORAGE_DIR = "recordings";
    private Button done_button;
    TextView vent_record_title,instruction;
    Button start_button_vent_record;
    RelativeLayout record_layout;
    Toolbar toolbar;

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vent_out_recording);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Vent in this safe place");
        setSupportActionBar(toolbar);
        vent_record_title = (TextView) findViewById(R.id.vent_record_title);
        start_button_vent_record = (Button) findViewById(R.id.start_button_vent_record);
        record_layout = (RelativeLayout) findViewById(R.id.record_layout);


        toggleButton = (ToggleButton) findViewById(R.id.start_button);
         toggleButton.setOnCheckedChangeListener(this);
        playOrPause = (ToggleButton) findViewById(R.id.play_button);
        playOrPause.setOnCheckedChangeListener(this);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        recordingTimer = (TextView) findViewById(R.id.txttime);
        instruction = (TextView) findViewById(R.id.instruction);
        done_button = (Button) findViewById(R.id.done_button);
        done_button.setOnClickListener(this);
        start_button_vent_record.setOnClickListener(this);
        handler = new Handler();
        toggleButton.setVisibility(View.VISIBLE);
        playOrPause.setVisibility(View.GONE);
        done_button.setVisibility(View.GONE);
        isRecording = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.text_item_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.text_item);
        menuItem.setVisible(false);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.done_button) {
            //TODO delete the audio file and start animation of delete image
            delete();
        }
        if (id == R.id.start_button_vent_record) {

            record_layout.setVisibility(View.VISIBLE);
            start_button_vent_record.setVisibility(View.GONE);
            vent_record_title.setVisibility(View.GONE);
            instruction.setVisibility(View.VISIBLE);
        }
    }

    private void delete() {
        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        fade_out.setAnimationListener(this);
        AnimationSet s = new AnimationSet(false);
        s.addAnimation(fade_in);
        int duration = 2500;
        rotate.setDuration((long) duration);
        rotate.setStartOffset(fade_in.getDuration());
        s.addAnimation(rotate);

        fade_out.setStartOffset(fade_in.getDuration() + rotate.getDuration());
        s.addAnimation(fade_out);

        s.setFillAfter(true);
        Log.i("Ventoutrecording", "File path is " + recordFilePath);
        if (recordFilePath != null && !recordFilePath.equals(""))
            deleteFile(recordFilePath);
        ImageView deleteImageView = (ImageView) findViewById(R.id.deletion_icon);
        deleteImageView.setVisibility(View.VISIBLE);
        record_layout.setVisibility(View.GONE);
        deleteImageView.startAnimation(s);
        mediaPlayer1 = MediaPlayer.create(this, R.raw.trash);
        mediaPlayer1.start();
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (mediaPlayer1 != null) {
            mediaPlayer1.stop();
        }
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public boolean deleteFile(String selectedFilePath) {
        boolean deleted = false;
        File file = new File(selectedFilePath);
        Log.i("Ventoutrecording", "File exists is " + file.exists());
        if (file.exists()) {
            deleted = file.delete();
        }
        return deleted;
    }

    public void startRecording() {
        File file = null;
        CommonUtils.checkForOtherAppsPlaying(this);
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        String storagePath = Environment.getExternalStorageDirectory() + "/" + STORAGE_DIR;
        String fileName = Long.toString(System.currentTimeMillis());
        final File dir = new File(storagePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            file = File.createTempFile(fileName, ".amr", dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file != null) {
            recordFilePath = file.getAbsolutePath();
            Log.i("VentOutRecording", "File path is " + recordFilePath);
            mediaRecorder.setOutputFile(file.getAbsolutePath());
        }
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        if (!isRecording) {
            //Create MediaRecorder and initialize audio source, output format, and audio encoder
            // Starting record time
            recordTime = 0;
            // Show TextView that displays record time
            recordingTimer.setVisibility(TextView.VISIBLE);
            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IllegalStateException ie) {
                CommonUtils.showToast(VentoutRecording.this, "Not able to record..");
            } catch (IOException e) {
                Log.e("LOG_TAG", "prepare failed");
                Log.e("LOG_TAG", "" + e);
            }
            isRecording = true;
            handler.post(UpdateRecordTime);
        }
    }

    public void stopRecording() {
        if (isRecording) {
            // Stop recording and release resource
            try {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
            } catch (IllegalStateException ie) {
                Log.i("VentOut", "IllegalStateException");
            }
            // Change isRecording flag to false
            isRecording = false;
            // Hide TextView that shows record time
            recordingTimer.setVisibility(TextView.GONE);

        }
    }

    public void playIt() {
        // Create MediaPlayer object
        mediaPlayer = new MediaPlayer();
        // set start time
        playTime = 0;
        // Reset max and progress of the SeekBar
        seekBar.setMax(recordTime);
        seekBar.setProgress(0);
        try {
            // Initialize the player and start playing the audio
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setDataSource(recordFilePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
            // Post the play progress
            handler.post(UpdatePlayTime);
        } catch (IOException e) {
            Log.e("LOG_TAG", "prepare failed");
        }
    }

    Runnable UpdateRecordTime = new Runnable() {
        public void run() {
            if (isRecording) {
                seekBar.setMax(100);
                recordingTimer.setText(String.valueOf(recordTime));
                recordTime += 1;
                seekBar.setProgress(recordTime);
                // Delay 1s before next call
                handler.postDelayed(this, 1000);
            }
        }
    };
    Runnable UpdatePlayTime = new Runnable() {
        public void run() {
            if (mediaPlayer.isPlaying()) {
                recordingTimer.setVisibility(View.VISIBLE);
                recordingTimer.setText(String.valueOf(playTime));
                // Update play time and SeekBar
                playTime += 1;
                seekBar.setProgress(playTime);
                // Delay 1s before next call
                handler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (id == R.id.start_button) {
            if (!isRecording) {
                if (permissionsCheck(this)) {
                    startRecording();
                } else {
                    toggleButton.setChecked(false);
                    askForPermission(Manifest.permission.RECORD_AUDIO, 2);
                }
            } else {

                stopRecording();
                instruction.setText(R.string.vent_record_STOP);
                playOrPause.setVisibility(View.VISIBLE);
                done_button.setVisibility(View.VISIBLE);
                toggleButton.setVisibility(View.INVISIBLE);
            }
        } else if (id == R.id.play_button) {
            //Log.i("VentOut","MediaPlayer "+mediaPlayer);
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    playIt();
                }
            } else {
                playIt();
            }
        }
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            }
        }
    }

    private boolean permissionsCheck(Context context) {
        boolean permissionsGranted = true;
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
                    == PackageManager.PERMISSION_GRANTED) {
                return checkForStoragePermission();
            } else {
                permissionsGranted = false;
                checkForStoragePermission();
            }

        }
        return permissionsGranted;
    }

    private boolean checkForStoragePermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    111);

            return false;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        try {
            if (mediaPlayer != null) {
                toggleButton.setVisibility(View.GONE);
                playOrPause.setVisibility(View.GONE);
                playTime = 0;
                recordingTimer.setText(String.valueOf(playTime));
                seekBar.setProgress(0);
                done_button.setVisibility(View.GONE);
                delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}