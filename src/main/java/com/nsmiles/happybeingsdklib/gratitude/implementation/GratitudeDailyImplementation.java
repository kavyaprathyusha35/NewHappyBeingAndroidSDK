package com.nsmiles.happybeingsdklib.gratitude.implementation;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.UI.HomeScreenActivity;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;
import com.nsmiles.happybeingsdklib.gratitude.presenter.GratitudeDailyPresenter;
import com.nsmiles.happybeingsdklib.gratitude.view.GratitudeDailyView;
import com.nsmiles.happybeingsdklib.mycoachfragment.DailyGratitude;

import java.util.Date;

import androidx.appcompat.app.AlertDialog;

/**
 * Created by nSmiles on 11/25/2017.
 */

public class GratitudeDailyImplementation implements Animation.AnimationListener, GratitudeDailyPresenter {


    int dayPosition = 0;
    GratitudeDailyView view;
    Activity activity;
    CommonUtils commonUtils;
    DailyGratitude dailyGratitude;
    Intent intent;
    String action_type="DAILY_GRATITUDE";
    private MediaPlayer mediaPlayer;
    ImageView deleteImageView;
    private SQLiteDatabase db;
    MySql dbHelper;
    String diary_date_time;
    boolean update_flag = false;

    public GratitudeDailyImplementation(GratitudeDailyView view, Activity activity, Intent intent, ImageView deleteImageView) {
        this.view = view;
        this.activity = activity;
        this.intent = intent;
        this.deleteImageView = deleteImageView;
        commonUtils = new CommonUtils();
        dbHelper = new MySql(activity, "mydb", null, MySql.version);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void loadGratitudeDailyData() {

        try {
            if (intent.hasExtra("DAY")) {
                dayPosition = intent.getIntExtra("DAY", 0);
            }

            if (intent.hasExtra("DATE_TIME") && intent.hasExtra("" +
                    "ID") && intent.hasExtra("DAY") && intent.hasExtra("GENDER")
                     && intent.hasExtra("TITLE") && intent.hasExtra("DATA")) {


                diary_date_time = intent.getStringExtra("DATE_TIME");
                dayPosition = intent.getIntExtra("DAY",0);
                dailyGratitude = new DailyGratitude();
                dailyGratitude.getDailyData("", dayPosition,"", "");
                view.showEditHideTextView(intent.getStringExtra("DATA"));
                update_flag = true;
                view.setGratitudeData(dailyGratitude.getGratitude_image(), dailyGratitude.getGratitude_content());


            } else {
                dailyGratitude = new DailyGratitude();
                dailyGratitude.getDailyData("", dayPosition,"", "");
                action_type = dailyGratitude.getGratitude_type();
                if (action_type.equalsIgnoreCase("CRUSH")) {
                    view.changeBtnName();
                }
                view.setGratitudeData(dailyGratitude.getGratitude_image(), dailyGratitude.getGratitude_content());


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveGratitudeData(EditText editText) {

        try {
            if(checkValidation(editText)) {
                if (update_flag) {
                    updateData(editText.getText().toString());
                } else {
                    saveDailyDataLocally(editText.getText().toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkValidation(EditText editText) {

        boolean returnValue = true;

        if (!CommonUtils.hasText(editText, "Please fill the field"))
            returnValue = false;

        return returnValue;
    }



    private void saveDailyDataLocally(String gratitude_data) {
        try {
            MySql dbHelper = new MySql(activity, "mydb", null, MySql.version);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if (db.isOpen()) {

                ContentValues cv = new ContentValues();
                cv.put("date_time", new Date().toString());
                cv.put("type_of_gratitude", action_type); // CRUSH
                cv.put("description", dailyGratitude.getGratitude_content());
                cv.put("link", "");
                cv.put("pic", "");
                cv.put("title", "TEST_TITLE_ANDROID");
                cv.put("email_id", commonUtils.getUserEmail(activity));
                cv.put("express_your_gratitude", gratitude_data);
                cv.put("subject", dailyGratitude.getGratitude_day_no());
                cv.put("createdAt", new Date().toString());
                cv.put("updatedAt", new Date().toString());
                cv.put("id", "");
                cv.put("syncFlag", "0");
                long insert = db.insert("New_Gratitude_Table", null, cv);


                if (action_type.contains("CRUSH")) {
                    Animation fade_in = AnimationUtils.loadAnimation(activity, R.anim.fade_in);
                    Animation fade_out = AnimationUtils.loadAnimation(activity, R.anim.fade_out);
                    Animation rotate = AnimationUtils.loadAnimation(activity, R.anim.rotate);
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
                    view.showDeleteIcon();
                    deleteImageView.startAnimation(s);
                    mediaPlayer = MediaPlayer.create(activity, R.raw.trash);
                    mediaPlayer.start();
                } else {
                    if (insert > 0) {
                        CommonUtils.showHowAreYouFeeling(activity, "",
                                "MY_GUIDE_ANDROID", dailyGratitude.getGratitude_content(), "GRATITUDE");

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (mediaPlayer != null)
            mediaPlayer.stop();
        CommonUtils.showHowAreYouFeeling(activity, "",
                "MY_GUIDE_ANDROID", dailyGratitude.getGratitude_content(), "GRATITUDE");
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void Edit() {
        try {
            final CharSequence[] items = {"Edit", "Delete"};
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("");

            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Edit")) {
                        update_flag = true;
                        view.showEditHideTextView(intent.getStringExtra("DATA"));
                    } else if (items[item].equals("Delete")) {
                        deleteExpressGratitude();
                    }
                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateData(final String editText) {
        try {
            ContentValues cv = new ContentValues();
            Cursor cursor = db.rawQuery("SELECT * FROM New_Gratitude_Table where email_id=? AND syncFlag=? AND date_time=?",
                    new String[]{commonUtils.getUserEmail(activity), "0", diary_date_time}, null);
            if (cursor.getCount() > 0) {
                cv.put("syncFlag", "0");
            } else {
                cv.put("syncFlag", "3");
            }
            cv.put("express_your_gratitude", editText);
            db.update("New_Gratitude_Table", cv, "date_time=?",
                    new String[]{diary_date_time});
            activity.startActivity(new Intent(activity, HomeScreenActivity.class)
                    .putExtra("GO_DIARY", "DIARY"));
            activity.finishAffinity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void deleteExpressGratitude() {
        try {
            ContentValues cv = new ContentValues();
            cv.put("syncFlag", "2");
            db.update("New_Gratitude_Table", cv, "date_time=?", new String[]{diary_date_time});
            activity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
