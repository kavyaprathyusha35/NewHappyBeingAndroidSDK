package com.nsmiles.happybeingsdklib.gratitude.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.gratitude.implementation.GratitudeDailyImplementation;
import com.nsmiles.happybeingsdklib.gratitude.view.GratitudeDailyView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class GratitudeDailyActivity extends AppCompatActivity implements GratitudeDailyView, View.OnClickListener {

    GratitudeDailyView gratitudeDailyView;
    GratitudeDailyImplementation gratitudeDailyImplementation;
    Activity activity;
    Context context;
    TextView gratitude_content;
    ImageView gratitude_img;
    EditText gratitude_edt;
    Button gratitude_btn;
    Intent myIntent;
    ImageView deletion_icon;
    TextView data_tv;
    ImageView edit_eclipse;
    private ScrollView scroll_view;
    private int day_numer;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gratitude_daily_fragment);
        gratitudeDailyView = GratitudeDailyActivity.this;
        activity = GratitudeDailyActivity.this;
        context = GratitudeDailyActivity.this;
        scroll_view = (ScrollView) findViewById(R.id.scroll_view);
        gratitude_content = (TextView) findViewById(R.id.gratitude_content);
        gratitude_img = (ImageView) findViewById(R.id.gratitude_img);
        gratitude_edt = (EditText) findViewById(R.id.gratitude_edt);
        gratitude_btn = (Button) findViewById(R.id.gratitude_btn);
        deletion_icon = (ImageView) findViewById(R.id.deletion_icon);
        edit_eclipse = (ImageView) findViewById(R.id.edit_eclipse);
        data_tv = (TextView) findViewById(R.id.data_tv);
        gratitude_btn.setOnClickListener(this);
        edit_eclipse.setOnClickListener(this);
        scroll_view.setOnClickListener(this);
        myIntent = getIntent();
        if (myIntent.hasExtra("DAY")) {
            day_numer = myIntent.getIntExtra("DAY", 0);
        }
        if (myIntent.hasExtra("GRATITUDE_DATE")) {
            day_numer = myIntent.getIntExtra("GRATITUDE_DATE", 0);
        }
        gratitudeDailyImplementation = new GratitudeDailyImplementation(gratitudeDailyView, activity, myIntent,deletion_icon);
        gratitudeDailyImplementation.loadGratitudeDailyData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(GratitudeDailyActivity.this ,R.color.hb_circle_black_light));
        }

    }

    @Override
    public void onBackPressed() {
        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
        super.onBackPressed();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @Override
    public void setGratitudeData(int loadImage,  String content) {
        gratitude_img.setImageResource(loadImage);
        gratitude_content.setText(content);
    }

    @Override
    public void showDeleteIcon() {
        deletion_icon.setVisibility(View.VISIBLE);
    }

    @Override
    public void changeBtnName() {
        gratitude_btn.setText("DELETE");
    }

    @Override
    public void showEditHideTextView(String gratitude_data) {

        data_tv.setVisibility(View.GONE);
        gratitude_edt.setVisibility(View.VISIBLE);
        gratitude_edt.setText(gratitude_data);
        gratitude_btn.setVisibility(View.VISIBLE);
        gratitude_btn.setText("UPDATE");
    }


    @Override
    public void onClick(View v) {
        CommonUtils commonUtils = new CommonUtils();
        int id =v.getId();
        if (id == R.id.gratitude_btn) {
            gratitudeDailyImplementation.saveGratitudeData(gratitude_edt);
            commonUtils.setGratitudeData(this, day_numer, true);
        } else if (id == R.id.edit_eclipse) {
            gratitudeDailyImplementation.Edit();
            commonUtils.setGratitudeData(this, day_numer, true);
        } else if (id == R.id.scroll_view) {
            scroll_view.smoothScrollTo(0, scroll_view.getBottom());
        }
    }
}
