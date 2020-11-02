package com.nsmiles.happybeingsdklib.settings.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.settings.implementation.SettingImplementation;
import com.nsmiles.happybeingsdklib.settings.view.SettingView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

/**
 * Created by nSmiles on 12/16/2017.
 */
// settings_fragment


public class MySettingActivity extends AppCompatActivity implements View.OnClickListener, SettingView {

    TextView did_tv, desc_tv, tomorrow_tv, hour_tv, min_tv, format_tv;
    Button save_btn;
    SettingView settingView;
    SettingImplementation settingImplementation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        did_tv = (TextView) findViewById(R.id.did_tv);
        desc_tv = (TextView) findViewById(R.id.desc_tv);
        tomorrow_tv = (TextView) findViewById(R.id.tomorrow_tv);
        hour_tv = (TextView) findViewById(R.id.hour_tv);
        min_tv = (TextView) findViewById(R.id.min_tv);
        format_tv = (TextView) findViewById(R.id.format_tv);
        save_btn = (Button) findViewById(R.id.save_btn);

        hour_tv.setOnClickListener(this);
        min_tv.setOnClickListener(this);
        format_tv.setOnClickListener(this);
        save_btn.setOnClickListener(this);
        settingView = MySettingActivity.this;
        settingImplementation = new SettingImplementation(MySettingActivity.this, settingView,getIntent());
        settingImplementation.setValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(MySettingActivity.this, R.color.hb_circle_black_light));
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.save_btn) {
            settingImplementation.goToSettings();
        } else if (id == R.id.hour_tv) {
            settingImplementation.goToSettings();
        } else if (id == R.id.min_tv) {
            settingImplementation.goToSettings();
        } else if (id == R.id.format_tv) {
            settingImplementation.goToSettings();
        }

    }

    @Override
    public void setDescriptionTitle(String desc) {
        desc_tv.setText(desc);
    }

    @Override
    public void setHour(String hour) {
        hour_tv.setText(hour);
    }

    @Override
    public void setMinute(String minute) {
        min_tv.setText(minute);
    }

    @Override
    public void setFormat(String format) {
        format_tv.setText(format);
    }

    @Override
    public void setTitle(String title) {

        did_tv.setText(title);
    }

    @Override
    public void nextTitle(String next) {
        tomorrow_tv.setText(next);
    }
}
