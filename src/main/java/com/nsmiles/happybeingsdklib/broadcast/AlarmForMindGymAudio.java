package com.nsmiles.happybeingsdklib.broadcast;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.nsmiles.happybeingsdklib.broadcastnotifications.ShowMindGymAudioNotification;

import java.util.Calendar;

/**
 * Created by Admin on 29-12-2016.
 */

@SuppressWarnings("DefaultFileTemplate")
public class AlarmForMindGymAudio extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        assert pm != null;
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire(1000);
        Log.i("AlarmForMindGymAudio", "2");
        // setResettingOfAudioFiles(context);
        setNowNotificationTrigger(context);
        // setNextDayAlarm(context);
        wl.release();
    }

    private void setNowNotificationTrigger(Context context) {
        Log.i("AlarmForMindGymAudio", "3");
        Intent notificationIntent = new Intent(context, ShowMindGymAudioNotification.class);
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        assert am != null;
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),86400000,contentIntent);
    }

    public void setAlarm(Context context, long millisec) {
        AlarmManager am =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(context, AlarmForMindGymAudio.class);
        Log.i("AlarmForMindGym", "Morning alarm set to  ******"+millisec);
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, 821338, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        assert am != null;
        am.set(AlarmManager.RTC_WAKEUP, millisec, contentIntent);
    }

    public static void cancelAlarm(Context context) {
        Intent intent = new Intent(context, AlarmForMindGymAudio.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

}