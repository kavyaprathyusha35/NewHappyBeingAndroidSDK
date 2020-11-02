package com.nsmiles.happybeingsdklib.broadcastnotifications;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nsmiles.happybeingsdklib.MindGym.RelaxAudioModel;
import com.nsmiles.happybeingsdklib.MindGym.RelaxUtils;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.UI.HomeScreenActivity;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;
import com.nsmiles.happybeingsdklib.dagger.application.BaseApplication;
import com.nsmiles.happybeingsdklib.dagger.data.DataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import androidx.core.app.NotificationCompat;

/**
 * Created by Sukumar on 9/7/2018.
 */

public class ShowRelaxBigNotification extends BroadcastReceiver {

    private SQLiteDatabase db;
    private MySql dbHelper;
    private RelaxUtils relaxUtils;
    private List<RelaxAudioModel> relaxAudioModelList;
    private RelaxAudioModel relaxAudio;
    private Random audioRandomGenerator;
    private List<RelaxAudioModel> audioList;


    NotificationCompat.Builder notificationBuilder;
    Bitmap relax_image;
    NotificationCompat.BigPictureStyle bigPictureStyle;
    Intent resultIntent;
    TaskStackBuilder TSB;
    NotificationManager mNotificationManager;
    PendingIntent resultPendingIntent;

    @Inject
    DataManager dataManager;



    @Override
    public void onReceive(Context context, Intent intent) {

        ((BaseApplication)context.getApplicationContext()).getmApplicationApiComponent().inject(this);
        dbHelper = new MySql(context, "mydb", null, MySql.version);
        db = dbHelper.getWritableDatabase();
        relaxUtils = new RelaxUtils();
        loadAudioList(context);
    }

    private void loadAudioList(Context context) {

        try {
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM relax_audio", null);
            if (cursor.getCount() == 0) {
                relaxUtils.insertAllRelaxAudio(context);
            }
            cursor = db.rawQuery("SELECT * FROM relax_audio", null);
            if (cursor.getCount() > 0) {
                relaxAudioModelList = new ArrayList<>();
                cursor.moveToFirst();
                do {
                    relaxAudio = new RelaxAudioModel();
                    relaxAudio.setAudio_title(cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_TITLE")));
                    relaxAudio.setAudio_image(cursor.getInt(cursor.getColumnIndexOrThrow("DRAWABLE_IMAGE")));
                    relaxAudio.setAudio_base_url(cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_BASE_URL")));
                    relaxAudio.setAudio_sub_url(cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_SUB_URL")));
                    relaxAudio.setSd_card_path(cursor.getString(cursor.getColumnIndexOrThrow("SD_CARD_PATH")));
                    relaxAudio.setDownload_status(cursor.getString(cursor.getColumnIndexOrThrow("DOWNLOAD_STATUS")));
                    relaxAudio.setAudio_number(cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_NUMBER")));
                    relaxAudio.setId(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("_ID"))));
                    relaxAudio.setDuration(cursor.getString(cursor.getColumnIndexOrThrow("DURATION")));
                    relaxAudio.setAudio_description(cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_DESCRIPTION")));
                 //   CommonUtils.showLogInforamtion(getClass().getSimpleName(), "AUDIO_TITLE", cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_TITLE")), false);
                    relaxAudioModelList.add(relaxAudio);
                }
                while (cursor.moveToNext());
                CommonUtils.CloseCursor(cursor);
            }


            if (relaxAudioModelList != null && relaxAudioModelList.size() > 0) {

                audioRandomGenerator = new Random();
                relaxAudio = new RelaxAudioModel();
                relaxAudio = getRandomAudioFile();
                audioList = new ArrayList<>();
                audioList.add(relaxAudio);


                notificationBuilder = new NotificationCompat.Builder(context, AppConstants.CHANNEL_ID);
                notificationBuilder.setSmallIcon(R.drawable.hb_db_tr);
                notificationBuilder.setContentTitle("Want to relax and rejuvenate?");
                /* notificationBuilder.setContentText("Set Content text");
                notificationBuilder.setTicker("Set Ticker text");*/

                relax_image = BitmapFactory.decodeResource(context.getResources(), audioList.get(0).getAudio_image());
                bigPictureStyle = new NotificationCompat.BigPictureStyle().bigPicture(relax_image);
                bigPictureStyle.setSummaryText(audioList.get(0).getAudio_title());
                notificationBuilder.setStyle(bigPictureStyle);

                resultIntent = new Intent(context, HomeScreenActivity.class);
                resultIntent.putExtra("GO_RELAX", "RELAX");
                resultIntent.putExtra("AUDIO_ID" , audioList.get(0).getId());
                resultIntent.putExtra("AUDIO_NUMBER", audioList.get(0).getAudio_number());
                resultIntent.putExtra("FAVOURITE", checkFavourite(context,audioList.get(0).getAudio_number()));
                TSB = TaskStackBuilder.create(context);
                TSB.addParentStack(HomeScreenActivity.class);

               /* if(dataManager.get(AppConstants.HB_USER_LOGIN_STATUS, "").equalsIgnoreCase("true")){
                }
                else{
                    resultIntent = new Intent(context, Splash_Activity.class);
                    TSB = TaskStackBuilder.create(context);
                    TSB.addParentStack(Splash_Activity.class);
                }*/

                // Adds the Intent that starts the Activity to the top of the stack
                TSB.addNextIntent(resultIntent);
                resultPendingIntent = TSB.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                notificationBuilder.setContentIntent(resultPendingIntent);
                notificationBuilder.setAutoCancel(true);
                mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                // mId allows you to update the notification later on.
                assert mNotificationManager != null;
                mNotificationManager.notify(0, notificationBuilder.build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String checkFavourite(Context context, String audio_number) {

        String favorite = "false";
        try {
            dbHelper = new MySql(context, "mydb", null, MySql.version);
            db = dbHelper.getWritableDatabase();
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM relax_favourite WHERE AUDIO_NUMBER=? AND EMAIL=?",
                    new String[]{audio_number, dataManager.get(AppConstants.SDK_EMAIL,"")});
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                favorite = cursor.getString(cursor.getColumnIndexOrThrow("FAVOURITE"));
                CommonUtils.CloseCursor(cursor);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return favorite;
    }

    private RelaxAudioModel getRandomAudioFile() {
        int index = audioRandomGenerator.nextInt(relaxAudioModelList.size());
        RelaxAudioModel item = relaxAudioModelList.get(index);
        System.out.println("Managers choice this week" + item + "our recommendation to you");
        return item;
    }
}
