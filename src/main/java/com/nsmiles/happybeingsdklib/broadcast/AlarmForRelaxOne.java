package com.nsmiles.happybeingsdklib.broadcast;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.PowerManager;

import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;
import com.nsmiles.happybeingsdklib.broadcastnotifications.ShowRelaxBigNotification;

import java.util.Calendar;

/**
 * Created by Admin on 29-12-2016.
 */

@SuppressWarnings("DefaultFileTemplate")
public class AlarmForRelaxOne extends BroadcastReceiver
{
    private SQLiteDatabase db;
    MySql dbHelper;
    int range;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        try {
            //Log.i("Alarm","Is activity visible "+ Foreground.get().isForeground());
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            assert pm != null;
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
            wl.acquire(1000);
            //Log.i("Alarm","In on receive of alarm");
            setnowNotificationTrigger(context);
            setNetDayAlarm(context);
            wl.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setNetDayAlarm(Context c) {

        try {
            CommonUtils commonUtils;
            commonUtils = new CommonUtils();
            dbHelper = new MySql(c, "mydb", null, MySql.version);
            db = dbHelper.getWritableDatabase();
            Cursor relax_cursor = db.rawQuery("SELECT * FROM RelaxAlarmNotification", null);
            if(relax_cursor.getCount()>0){
                relax_cursor.moveToLast();
                range = relax_cursor.getInt(relax_cursor.getColumnIndexOrThrow("alarm_range"));
                commonUtils.setRelaxAlarm(c,range);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAlarm(Context context, long millisec)
    {
        try {
            AlarmManager am =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(context, AlarmForRelaxOne.class);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
            am.set(AlarmManager.RTC_WAKEUP, millisec, pi);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setnowNotificationTrigger(Context context) {
        //Log.i("Alarm","In set alarms method");
        try {
            Intent notificationIntent = new Intent(context, ShowRelaxBigNotification.class);
            PendingIntent contentIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, 0);

            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            // Set the alarm's trigger time to 12:00 .m.
            assert am != null;
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),86400000,contentIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cancelAlarm(Context context)
    {
        try {
            Intent intent = new Intent(context, AlarmForRelaxOne.class);
            PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(sender);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}