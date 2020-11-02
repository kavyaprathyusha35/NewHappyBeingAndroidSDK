package com.nsmiles.happybeingsdklib.MindGym;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;

import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class VentOutJournal extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {
    private EditText contentEdittext;
    Button doneButton, start_button_vent;
    private MediaPlayer mediaPlayer;
    Toolbar vent_out_toolbar;
    Context mContext;
    RelativeLayout mLinearLayout;
    boolean user_paid = false;
    SharedPreferences prefs;
    String emotion="";
    Activity activity;
    private ImageView deleteImageView;
    LinearLayout vent_layout_one, vent_layout_two, vent_layout_three, vent_layout_four, vent_layout_five;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vent_out_journal);
        vent_out_toolbar = (Toolbar) findViewById(R.id.vent_out_toolbar);
        mLinearLayout = (RelativeLayout) findViewById(R.id.vent_relative_layout);

        vent_layout_one = (LinearLayout) findViewById(R.id.vent_layout_one);
        vent_layout_two = (LinearLayout) findViewById(R.id.vent_layout_two);
        vent_layout_three = (LinearLayout) findViewById(R.id.vent_layout_three);
        vent_layout_four = (LinearLayout) findViewById(R.id.vent_layout_four);
        vent_layout_five = (LinearLayout) findViewById(R.id.vent_layout_five);
        deleteImageView = findViewById(R.id.deletion_icon);
        activity = VentOutJournal.this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        vent_out_toolbar.setTitle("De-stress journal");
//        setSupportActionBar(vent_out_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mContext = getApplicationContext();
        contentEdittext = (EditText) findViewById(R.id.content_editext);
        doneButton = (Button) findViewById(R.id.done_button);
        start_button_vent = (Button) findViewById(R.id.start_button_vent);
        doneButton.setOnClickListener(this);
        start_button_vent.setOnClickListener(this);

        contentEdittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (v.getId() == R.id.content_editext) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(VentOutJournal.this ,R.color.hb_circle_black_light));
        }
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
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (mediaPlayer != null)
            mediaPlayer.stop();
        doneButton.setVisibility(View.GONE);
        contentEdittext.setVisibility(View.GONE);
        showHowAreYouFeeling();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_button_vent) {

            try {
                start_button_vent.setVisibility(View.GONE);
                vent_layout_one.setVisibility(View.GONE);
                vent_layout_two.setVisibility(View.GONE);
                vent_layout_three.setVisibility(View.GONE);
                vent_layout_four.setVisibility(View.GONE);
                vent_layout_five.setVisibility(View.GONE);

                contentEdittext.setVisibility(View.VISIBLE);
                doneButton.setVisibility(View.VISIBLE);
                contentEdittext.requestFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (v.getId() == R.id.done_button) {
            try {
                if (!contentEdittext.getText().toString().equals("")) {
                    if(doneButton.getText().equals("DONE")){
                        doneButton.setText("DELETE");
                        doneButton.setBackground(getResources().getDrawable(R.drawable.button_red));
                        contentEdittext.setFocusable(false);
                    }
                    else {
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
                        deleteImageView.setVisibility(View.VISIBLE);
                        deleteImageView.startAnimation(s);
                        mediaPlayer = MediaPlayer.create(this, R.raw.trash);
                        mediaPlayer.start();
                    }
                } else {
                    finish();
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    public void showHowAreYouFeeling() {


        final LinearLayout sad_layout, better_layout, calm_layout, confident_layout, motivated_layout, thankful_layout;
        final ImageView sad_img, better_img, calm_img, confident_img, motivated_img, thankful_img;
        Button save_btn;
        //  sad better  calm   confident  motivated  energised

        final Dialog d = new Dialog(activity);
        d.setContentView(R.layout.dialog_feeling_right_layout);
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // d.setTitle("Happy Being");
        d.setCancelable(false);
        d.setCanceledOnTouchOutside(false);
        d.show();

        sad_layout = (LinearLayout) d.findViewById(R.id.sad_layout);
        better_layout = (LinearLayout) d.findViewById(R.id.better_layout);
        calm_layout = (LinearLayout) d.findViewById(R.id.calm_layout);
        confident_layout = (LinearLayout) d.findViewById(R.id.confident_layout);
        motivated_layout = (LinearLayout) d.findViewById(R.id.motivated_layout);
        thankful_layout = (LinearLayout) d.findViewById(R.id.thankful_layout);

        sad_img = (ImageView) d.findViewById(R.id.sad_img);
        better_img = (ImageView) d.findViewById(R.id.better_img);
        calm_img = (ImageView) d.findViewById(R.id.calm_img);
        confident_img = (ImageView) d.findViewById(R.id.confident_img);
        motivated_img = (ImageView) d.findViewById(R.id.motivated_img);
        thankful_img = (ImageView) d.findViewById(R.id.thankful_img);

        save_btn = (Button) d.findViewById(R.id.save_btn);

        sad_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "sad";
                sad_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.sad_filled));
                better_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.better_gray));
                calm_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.calm_gray));
                confident_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.confident_gray));
                motivated_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.motivated_gray));
                thankful_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.thankful_gray));
            }
        });

        better_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "better";
                sad_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.sad_gray));
                better_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.better_filled));
                calm_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.calm_gray));
                confident_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.confident_gray));
                motivated_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.motivated_gray));
                thankful_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.thankful_gray));

            }
        });

        calm_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "calm";
                sad_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.sad_gray));
                better_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.better_gray));
                calm_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.calm_filled));
                confident_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.confident_gray));
                motivated_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.motivated_gray));
                thankful_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.thankful_gray));

            }
        });

        confident_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "confident";
                sad_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.sad_gray));
                better_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.better_gray));
                calm_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.calm_gray));
                confident_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.confident_filled));
                motivated_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.motivated_gray));
                thankful_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.thankful_gray));

            }
        });

        motivated_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "motivated";
                sad_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.sad_gray));
                better_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.better_gray));
                calm_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.calm_gray));
                confident_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.confident_gray));
                motivated_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.motivated_filled));
                thankful_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.thankful_gray));

            }
        });

        thankful_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "energised";
                sad_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.sad_gray));
                better_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.better_gray));
                calm_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.calm_gray));
                confident_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.confident_gray));
                motivated_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.motivated_gray));
                thankful_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.thankful_filled));
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (emotion==null || emotion.length() > 0) {
                        MySql dbHelper = new MySql(activity, "mydb", null, MySql.version);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put("date_time", new Date().toString());
                        cv.put("feature", "DE_STRESS_JOURNAL");
                        cv.put("activity", "DE_STRESS_JOURNAL");
                        cv.put("emotion1", emotion);
                        cv.put("sync_flag", "0");
                        db.insert("New_Feelings_Table", null, cv);
                        db.close();
                        d.dismiss();
                        resultPopup();
                    } else {
                        CommonUtils.showToast(activity, "Please select you feelings...");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void resultPopup() {

        try {
            Button save_btn;
            final Dialog d = new Dialog(activity);
            d.setContentView(R.layout.de_stress_tips);
            d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            d.setCancelable(false);
            d.setCanceledOnTouchOutside(false);
            d.show();
            save_btn = (Button) d.findViewById(R.id.save_btn);

            save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}