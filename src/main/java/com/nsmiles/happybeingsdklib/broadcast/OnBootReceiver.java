package com.nsmiles.happybeingsdklib.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;

import java.util.Calendar;

public class OnBootReceiver extends BroadcastReceiver {

    CommonUtils commonUtils;
    private SQLiteDatabase db;
    MySql dbHelper;
    int range;
    @Override
    public void onReceive(final Context context, Intent intent) {

        try {

            dbHelper = new MySql(context, "mydb", null, MySql.version);
            db = dbHelper.getWritableDatabase();
            commonUtils = new CommonUtils();
            relaxAlarm(context);
            scheduleAlarms(context);
           // commonUtils.setDailyAlarm(context);
            //commonUtils.setWeeklyAlarm(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void relaxAlarm(Context context) {

        try {
            Cursor relax_cursor = db.rawQuery("SELECT * FROM RelaxAlarmNotification", null);
            if(relax_cursor.getCount()>0){
                relax_cursor.moveToLast();
                range = relax_cursor.getInt(relax_cursor.getColumnIndexOrThrow("alarm_range"));
                commonUtils.setRelaxAlarm(context,range);
            }
            relax_cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scheduleAlarms(Context context) {

        try {

            Calendar calendar;
// Log.i("SettingsAlarm", "IN schedule alarms");
            AlarmForMindGymAffirmations.cancelAlarm(context);
            AlarmForMindGymAudio.cancelAlarm(context);
            long mindgymAudioTime = 0, mindGymAffirmationsTime = 0, happyMomentTime = 0, relaxStartTime = 0, relaxEndTime = 0;
            //Alarm for 6 am
            //Log.i("SettingsAlarm", "Mind Gym audio ::::CUrrent time "+System.currentTimeMillis()+" 8 am time "+mindgymAudioTime);
            Cursor cursor = db.rawQuery("SELECT * FROM NotificationsTimings", null);
            if (cursor.getCount() > 0) {
            cursor.moveToLast();
            mindgymAudioTime = Long.parseLong(cursor.getString(cursor.getColumnIndex("MIND_GYM_START_TIME")));
            mindGymAffirmationsTime = Long.parseLong(cursor.getString(cursor.getColumnIndex("MIND_GYM_END_TIME")));
            happyMomentTime = Long.parseLong(cursor.getString(cursor.getColumnIndex("HAPPY_MOMENT_TIME")));
        }
            cursor.close();


            if (mindgymAudioTime > System.currentTimeMillis()) {
            //Log.i("SettingsAlarm","MindGym Audio ::::::IN if loop");
            AlarmForMindGymAudio alarmForMindGymAudio = new AlarmForMindGymAudio();
            alarmForMindGymAudio.setAlarm(context, mindgymAudioTime);
        } else {
                calendar = Calendar.getInstance();
                calendar.setTimeInMillis(mindgymAudioTime);
                calendar.add(Calendar.DATE, 1);
            //Log.i("SettingsAlarm", "MindGym Audio ::::::In else loop"+calendar1.getTimeInMillis());
            AlarmForMindGymAudio alarmForMindGymAudio = new AlarmForMindGymAudio();
            alarmForMindGymAudio.setAlarm(context, calendar.getTimeInMillis());
        }

            //Alarm for 9 30 pm
            calendar = Calendar.getInstance();
            ///Log.i("SettingsAlarm", "CUrrent time "+System.currentTimeMillis()+" 8 am time "+mindGymAffirmationsTime);
            if (mindGymAffirmationsTime > System.currentTimeMillis()) {
            // Log.i("SettingsAlarm", "MindGym Affirmations ::::::IN if loop");
            AlarmForMindGymAffirmations alarmForMindGymAffirmations = new AlarmForMindGymAffirmations();
            alarmForMindGymAffirmations.setAlarm(context, mindGymAffirmationsTime);
        } else {
                calendar.setTimeInMillis(mindGymAffirmationsTime);
                calendar.add(Calendar.DATE, 1);
            //Log.i("SettingsAlarm", "MindGym Affirmations ::::::IN else loop"+calendar2.getTimeInMillis());
            AlarmForMindGymAffirmations alarmForMindGymAffirmations = new AlarmForMindGymAffirmations();
            alarmForMindGymAffirmations.setAlarm(context, calendar.getTimeInMillis());
        }


        /*Happy Moment View*/
            calendar = Calendar.getInstance();
            AlarmForHappyMoment alarmForHappyMoment = new AlarmForHappyMoment();
            if(happyMomentTime > System.currentTimeMillis()){
                alarmForHappyMoment.setAlarm(context, happyMomentTime);
            }
            else {
                calendar.setTimeInMillis(happyMomentTime);
                calendar.add(Calendar.DATE,1);
                alarmForHappyMoment.setAlarm(context, calendar.getTimeInMillis());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}