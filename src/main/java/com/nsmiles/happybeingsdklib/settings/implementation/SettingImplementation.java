package com.nsmiles.happybeingsdklib.settings.implementation;

import android.app.Activity;
import android.content.Intent;

import com.nsmiles.happybeingsdklib.UI.SettingsLayout;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.settings.presenter.SettingPresenter;
import com.nsmiles.happybeingsdklib.settings.view.SettingView;

/**
 * Created by nSmiles on 12/16/2017.
 */

public class SettingImplementation implements SettingPresenter {

    SettingView settingView;
    Activity activity;
    CommonUtils commonUtils;
    Intent intent;


    public SettingImplementation(Activity activity, SettingView settingView, Intent intent) {
        this.activity = activity;
        this.settingView = settingView;
        this.intent = intent;
        commonUtils = new CommonUtils();

    }




    @Override
    public void goToSettings() {

        activity.startActivity(new Intent(activity, SettingsLayout.class));
        activity.finish();
    }

    @Override
    public void setValue() {

        if(intent!=null) {

            if(intent.hasExtra("SETTINGS")) {

                if (intent.getStringExtra("SETTINGS").equalsIgnoreCase("EVENING")) {

                    settingView.setTitle("Did you know?");
                    settingView.setDescriptionTitle("9 in 10 people reported improved happiness and productivity within 7 days of gratitude practice.");
                    settingView.nextTitle("Next gratitude practice tomorrow at");
                    settingView.setHour("09");
                    settingView.setMinute("30");
                    settingView.setFormat("PM");

                }
            }
        }
    }
}
