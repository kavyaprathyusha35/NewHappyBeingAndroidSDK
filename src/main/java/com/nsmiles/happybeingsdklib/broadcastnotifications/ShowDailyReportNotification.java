package com.nsmiles.happybeingsdklib.broadcastnotifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.RemoteViews;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.broadcast.CloseNotification;
import com.nsmiles.happybeingsdklib.broadcast.StartNotificationEvent;

import java.util.Calendar;
import java.util.Date;

import androidx.core.app.NotificationCompat;

/**
 * Created by Admin on 07-12-2016.
 */

@SuppressWarnings("DefaultFileTemplate")
public class ShowDailyReportNotification extends BroadcastReceiver {


    public String title;
    public SQLiteDatabase db;
    CommonUtils commonUtils;
    String primary_profile,user_email;
    long id,weekly_date_milliseconds,current_date_milliseconds;
    Date today = new Date();
    Calendar calendar;

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            int notificationId = 0;
            Intent i = new Intent(context, StartNotificationEvent.class);
            i.putExtra("notificationId", notificationId);
            i.putExtra("FROM", "DAILY");

            // Send data to NotificationView Class
            PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, i,
                    PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);


            Intent dismissIntent = new Intent(context, CloseNotification.class);
            dismissIntent.putExtra("notificationId", notificationId);
            dismissIntent.putExtra("FROM", "DAILY");

            PendingIntent dismissPending = PendingIntent.getBroadcast(context, 0, dismissIntent, 0);

            // Inflate the notification layout as RemoteViews
            RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
            // Set text on a TextView in the RemoteViews programmatically.
            //contentView.setTextColor(R.id.tvNotificationTitle, ContextCompat.getColor(this, android.R.color.black));


            contentView.setTextViewText(R.id.tvNotificationMessage, context.getResources().getString(R.string.let_us_work));
            contentView.setTextViewText(R.id.first_button, "");
            contentView.setTextViewText(R.id.second_button, "START");

            contentView.setViewVisibility(R.id.first_button, View.INVISIBLE);
            contentView.setViewVisibility(R.id.second_button, View.VISIBLE);

            contentView.setOnClickPendingIntent(R.id.second_button, pIntent);
            contentView.setOnClickPendingIntent(R.id.first_button, dismissPending);
            Bitmap largeIconBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.app_logo);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, AppConstants.CHANNEL_ID)
                    // Set Icon
                    .setLargeIcon(largeIconBitmap)
                    // Sets the ticker text
                    .setTicker(context.getResources().getString(R.string.app_name))
                    // Sets the small icon for the ticker
                    .setSmallIcon(R.drawable.app_logo)
                    // Dismiss Notification
                    .setAutoCancel(true)
                    // Set PendingIntent into Notification
                    .setContentIntent(pIntent)
                    // Set RemoteViews into Notification
                    .setCustomBigContentView(contentView)
                    .setContent(contentView);

            //Build the notification
            Notification notification = builder.build();
            // Use the NotificationManager to show the notification
            NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            assert notificationmanager != null;
            notificationmanager.notify(notificationId, notification);
            setAlarm(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAlarm(Context context) {

        try {
         //   user_email = commonUtils.getUserEmail(context);
           // primary_profile = commonUtils.getPrimaryProfile(context);
            Cursor weekly = db.rawQuery("SELECT * FROM goal_daily_alarm_table where user_email=? AND primary_profile=? ORDER BY time_milliseconds",
                    new String[]{user_email, primary_profile}, null);

            if (weekly.getCount() > 0) {
                weekly.moveToFirst();
                weekly_date_milliseconds = weekly.getLong(weekly.getColumnIndexOrThrow("time_milliseconds"));
                id = weekly.getInt(weekly.getColumnIndexOrThrow("auto_id"));
                today = new Date();
                calendar = Calendar.getInstance();
                calendar.setTime(today);
                current_date_milliseconds = calendar.getTimeInMillis();

                if (weekly_date_milliseconds <= current_date_milliseconds) {

                    db.delete("goal_alarm_table", "user_email" + " = ? AND primary_profile=? AND auto_id=?",
                            new String[]{user_email, primary_profile, String.valueOf(id)});
                    setAlarm(context);
                } else {

/*
                    AlarmForWeeklyReport alarm = new AlarmForWeeklyReport();
                    alarm.setAlarm(context, weekly_date_milliseconds);
*/
                }
            }

            CommonUtils.CloseCursor(weekly);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
