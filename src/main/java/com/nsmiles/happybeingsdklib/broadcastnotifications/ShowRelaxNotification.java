package com.nsmiles.happybeingsdklib.broadcastnotifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.broadcast.StartNotificationEvent;

import androidx.core.app.NotificationCompat;

/**
 * Created by Admin on 07-12-2016.
 */

@SuppressWarnings("DefaultFileTemplate")
public class ShowRelaxNotification extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        int notificationId = 0;
        Intent i = new Intent(context, StartNotificationEvent.class);
        i.putExtra("notificationId", notificationId);
        i.putExtra("FROM", "RELAX");

        // Send data to NotificationView Class
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, i,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);


       /* Intent dismissIntent = new Intent(context, EmotionGoodActivity.class);
        dismissIntent.putExtra("notificationId", notificationId);
        dismissIntent.putExtra("FROM", "RELAX");

        PendingIntent dismissPending = PendingIntent.getActivity(context, 0, dismissIntent, 0);

        // Inflate the notification layout as RemoteViews
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
        int color = Color.BLACK;
        // Set text on a TextView in the RemoteViews programmatically.
        //contentView.setTextColor(R.id.tvNotificationTitle, ContextCompat.getColor(this, android.R.color.black));


        contentView.setTextViewText(R.id.tvNotificationMessage, "Want to relax and rejuvenate?");
        contentView.setTextViewText(R.id.first_button, "NO, FEELING GOOD");
        contentView.setTextViewText(R.id.second_button, "YES, HELP RELAX NOW");

        contentView.setViewVisibility(R.id.first_button, View.VISIBLE);
        contentView.setViewVisibility(R.id.second_button, View.VISIBLE);

        contentView.setOnClickPendingIntent(R.id.second_button, pIntent);
        contentView.setOnClickPendingIntent(R.id.first_button, dismissPending);
        Bitmap largeIconBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.app_logo);
*/

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, AppConstants.CHANNEL_ID)
                // Set Icon
//                .setLargeIcon(largeIconBitmap)
                // Sets the ticker text
                .setTicker(context.getResources().getString(R.string.app_name))
                // Sets the small icon for the ticker
                .setSmallIcon(R.drawable.app_logo)
                // Dismiss Notification
                .setAutoCancel(true)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent);
                // Set RemoteViews into Notification
               // .setCustomBigContentView(contentView)
                //.setContent(contentView);

        //Build the notification
        Notification notification = builder.build();
        // Use the NotificationManager to show the notification
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationmanager != null;
        notificationmanager.notify(notificationId, notification);
    }
}