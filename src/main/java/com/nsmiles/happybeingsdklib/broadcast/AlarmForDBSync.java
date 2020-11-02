package com.nsmiles.happybeingsdklib.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

/**
 * Created by Gopal on 31-08-2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public class AlarmForDBSync extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        assert pm != null;
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire(1000);
        setDBSyncTrigger(context);
        wl.release();
    }

    public void setDBSyncTrigger(Context context) {
/*
        Intent notificationIntent = new Intent(context, DBSync.class);
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, 0);

        am.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() , 1000*10 , contentIntent);
*/

    }

    public void setAlarm(Context context)
    {
/*
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, DBSync.class);
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), 1000*60, contentIntent);
*/

        // am.setRepeating(AlarmManager.RTC_WAKEUP, millisec, pi); // Millisec * Second * Minute
    }

    public static void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmForDBSync.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}