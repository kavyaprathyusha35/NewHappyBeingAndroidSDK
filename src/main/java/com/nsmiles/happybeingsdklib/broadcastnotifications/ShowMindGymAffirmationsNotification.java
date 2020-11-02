package com.nsmiles.happybeingsdklib.broadcastnotifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.RemoteViews;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.broadcast.CloseNotification;
import com.nsmiles.happybeingsdklib.broadcast.StartNotificationEvent;
import com.nsmiles.happybeingsdklib.dagger.data.PreferenceManager;

import java.util.Calendar;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by Admin on 07-12-2016.
 */

@SuppressWarnings("DefaultFileTemplate")
public class ShowMindGymAffirmationsNotification extends BroadcastReceiver {
    String contenttitle,content_text;
    Bitmap wishicon;


    @Override
    public void onReceive(Context context, Intent intent) {

        int notificationId = 0;
        Intent i = new Intent(context, StartNotificationEvent.class);
        i.putExtra("notificationId", notificationId);
        i.putExtra("FROM", "GRATITUDE");

        // Send data to NotificationView Class
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, i,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);


        Intent dismissIntent = new Intent(context, CloseNotification.class);
        dismissIntent.putExtra("notificationId", notificationId);

        PendingIntent dismissPending = PendingIntent.getBroadcast(context, 0, dismissIntent, 0);

        // Inflate the notification layout as RemoteViews
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
        int color = Color.BLACK;
        // Set text on a TextView in the RemoteViews programmatically.
        //contentView.setTextColor(R.id.tvNotificationTitle, ContextCompat.getColor(this, android.R.color.black));


        contentView.setTextViewText(R.id.tvNotificationMessage, context.getResources().getString(R.string.how_was_your_day));
        contentView.setTextViewText(R.id.first_button, "CANCEL");
        contentView.setTextViewText(R.id.second_button, "START");

        contentView.setViewVisibility(R.id.first_button, View.INVISIBLE);
        contentView.setViewVisibility(R.id.second_button, View.VISIBLE);

        contentView.setOnClickPendingIntent(R.id.second_button, pIntent);
        contentView.setOnClickPendingIntent(R.id.first_button, dismissPending);
        Bitmap largeIconBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hb_db_tr);

        Calendar rightNow = Calendar.getInstance();
        int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
        String name = new PreferenceManager(context).get(AppConstants.SDK_NAME,"");

        if (currentHourIn24Format < 12) {
            contenttitle="Good morning "+ name;
            wishicon = BitmapFactory.decodeResource(context.getResources(), R.drawable.good_morning);
        } else if (currentHourIn24Format < 18) {
            contenttitle="Good afternoon " + name;
            wishicon = BitmapFactory.decodeResource(context.getResources(), R.drawable.good_afternoon);
        } else if (currentHourIn24Format < 24) {
            contenttitle="Good evening "+ name;
            wishicon = BitmapFactory.decodeResource(context.getResources(), R.drawable.good_evening);
        }

        if (currentHourIn24Format < 12) {

            content_text=context.getResources().getString(R.string.morning_message);

        } else if (currentHourIn24Format < 24) {


            content_text=context.getResources().getString(R.string.evening_message);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, AppConstants.CHANNEL_ID)
                .setLargeIcon(wishicon)
                // Sets the ticker text
                .setTicker(context.getResources().getString(R.string.app_name))
                // Sets the small icon for the ticker
                .setSmallIcon(R.drawable.hb_db_tr)
                .setColor(ContextCompat.getColor(context, R.color.flower_outer_color))

                // Dismiss Notification
                .setAutoCancel(true)
                .setContentTitle(contenttitle)
                .setContentText(content_text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content_text).setSummaryText("my Coach"))

                .addAction(android.R.drawable.ic_menu_view, "Start now", pIntent)
                .addAction(android.R.drawable.ic_delete, "Remind later", dismissPending)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent);
        // Set RemoteViews into Notification
//                        .setCustomBigContentView(contentView)
//                        .setContent(contentView);

        //Build the notification
        Notification notification = builder.build();
        // Use the NotificationManager to show the notification
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationmanager != null;
        notificationmanager.notify(notificationId, notification);


    }
}