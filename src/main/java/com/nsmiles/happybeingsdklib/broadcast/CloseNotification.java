package com.nsmiles.happybeingsdklib.broadcast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.nsmiles.happybeingsdklib.Utils.MySql;

import java.util.Date;

/**
 * Created by nSmiles on 11/20/2017.
 */

public class CloseNotification extends BroadcastReceiver {

    int notificationId = 0;
    String from;
    SQLiteDatabase db;
    MySql dbHelper;


    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.hasExtra("notificationId")) {
            notificationId = intent.getIntExtra("notificationId", 0);
        }
        if (intent.hasExtra("FROM")) {
            from = intent.getStringExtra("FROM");
        }
        if (from != null && from.length() > 0) {

            if (from.equalsIgnoreCase("RELAX")) {
                dbHelper = new MySql(context, "mydb", null, MySql.version);
                db = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("date_time", new Date().toString());
                cv.put("start_date_time", new Date().toString());
                cv.put("end_date_time", new Date().toString());
                cv.put("activity", "Relax_Feeling_Good");
                cv.put("emotion1", "good");
                cv.put("sync_flag", "0");
                db.insert("New_Feelings_Table", null, cv);
            }
        }
        /* Do what you want were.
        ..............
        ..............
         if you want cancel notification*/
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationId);
    }
}
