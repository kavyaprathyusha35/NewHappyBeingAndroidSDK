package com.nsmiles.happybeingsdklib.broadcast;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.nsmiles.happybeingsdklib.broadcastnotifications.ShowDailyReportNotification;

import java.util.Calendar;

/**
 * Created by Sukumar
 */

@SuppressWarnings("DefaultFileTemplate")
public class AlarmForDailyGoalReport extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        assert pm != null;
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire(1000);
        setNowNotificationTrigger(context);
        wl.release();
    }


    private void setNowNotificationTrigger(Context context) {
        Intent notificationIntent = new Intent(context, ShowDailyReportNotification.class);
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),contentIntent);
    }



    public void setAlarm(Context context, long millisec) {
        AlarmManager am =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(context, AlarmForDailyGoalReport.class);
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, 0);
        am.set(AlarmManager.RTC_WAKEUP, millisec, contentIntent);
    }

    public static void cancelAlarm(Context context) {
        Intent intent = new Intent(context, AlarmForDailyGoalReport.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

}