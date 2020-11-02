package com.nsmiles.happybeingsdklib.broadcast;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.nsmiles.happybeingsdklib.broadcastnotifications.ShowMindGymAffirmationsNotification;

import java.util.Calendar;

/**
 * Created by Admin on 29-12-2016.
 */

@SuppressWarnings("DefaultFileTemplate")
public class AlarmForMindGymAffirmations extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        assert pm != null;
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire(1000);

        setNowNotificationTrigger(context);
        // setNextDayAlarm(context);
        wl.release();
    }

    public void setAlarm(Context context, long millisec) {
        Log.i("Alarm", "Evening Alarm set to "+millisec);
        AlarmManager am =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(context, AlarmForMindGymAffirmations.class);
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, 939203, notificationIntent, 0);
        assert am != null;
        am.set(AlarmManager.RTC_WAKEUP, millisec, contentIntent);
    }

    public static void cancelAlarm(Context context) {
        Intent intent = new Intent(context, AlarmForMindGymAffirmations.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.cancel(sender);
    }

    private void setNowNotificationTrigger(Context context) {
        Intent notificationIntent = new Intent(context, ShowMindGymAffirmationsNotification.class);
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        assert am != null;
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),86400000,contentIntent);
    }
}