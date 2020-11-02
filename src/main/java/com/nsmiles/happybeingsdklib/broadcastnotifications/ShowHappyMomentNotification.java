package com.nsmiles.happybeingsdklib.broadcastnotifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.nsmiles.happybeingsdklib.Models.HappyMomentModel;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.SdkPreferenceManager;
import com.nsmiles.happybeingsdklib.broadcast.CloseNotification;
import com.nsmiles.happybeingsdklib.broadcast.StartNotificationEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.core.app.NotificationCompat;

/**
 * Created by Admin on 07-12-2016.
 */

@SuppressWarnings("DefaultFileTemplate")
public class ShowHappyMomentNotification extends BroadcastReceiver {
    String contenttitle;
    Bitmap wishicon;



    @Override
    public void onReceive(Context context, Intent intent) {

        int notificationId = 0;
        Intent i = new Intent(context, StartNotificationEvent.class);
        i.putExtra("notificationId", notificationId);
        i.putExtra("FROM", "HAPPY_MOMENT");

        // Send data to NotificationView Class
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, i,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);


        Intent dismissIntent = new Intent(context, CloseNotification.class);
        dismissIntent.putExtra("notificationId", notificationId);

        PendingIntent dismissPending = PendingIntent.getBroadcast(context, 0, dismissIntent, 0);

        // Inflate the notification layout as RemoteViews
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_happy_notification);
        int color = Color.BLACK;
        // Set text on a TextView in the RemoteViews programmatically.
        //contentView.setTextColor(R.id.tvNotificationTitle, ContextCompat.getColor(this, android.R.color.black));


        contentView.setTextViewText(R.id.tvNotificationMessage, "Capture your happy moments in the happiness journal.");
        contentView.setTextViewText(R.id.first_button, "CANCEL");
        contentView.setTextViewText(R.id.second_button, "START");

        HappyMomentModel happyMomentModel = loadCameraPhotoData(context);
        if(happyMomentModel!=null){

            if(happyMomentModel.isAvailableStatus() && happyMomentModel.getFilePath().length()>0){
                Bitmap bm = CommonUtils.decodeSampledBitmapFromUri(happyMomentModel.getFilePath(), 0, 30);
                contentView.setImageViewBitmap(R.id.happy_moment_image, bm);
            }
        }

        contentView.setViewVisibility(R.id.first_button, View.INVISIBLE);
        contentView.setViewVisibility(R.id.second_button, View.VISIBLE);

        contentView.setOnClickPendingIntent(R.id.second_button, pIntent);
        contentView.setOnClickPendingIntent(R.id.first_button, dismissPending);
        Bitmap largeIconBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hb_db_tr);

        Calendar rightNow = Calendar.getInstance();
        int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
        String name = new SdkPreferenceManager(context).get(AppConstants.SDK_NAME,"");

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


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, AppConstants.CHANNEL_ID)
                // Set Icon
                .setLargeIcon(largeIconBitmap)
                // Sets the ticker text
                .setTicker(context.getResources().getString(R.string.app_name))
                // Sets the small icon for the ticker
                .setSmallIcon(R.drawable.hb_db_tr)
                // Dismiss Notification
                .setAutoCancel(true)
                // Set PendingIntent into Notification
                .setContentTitle(contenttitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getResources().getString(R.string.happy_message)).setSummaryText("journal"))


                .addAction(android.R.drawable.ic_menu_view, "START", pIntent)
                .addAction(android.R.drawable.ic_delete, "CANCEL", dismissPending)
                .setContentIntent(pIntent);

        //Build the notification
        Notification notification = builder.build();
        // Use the NotificationManager to show the notification
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationmanager != null;
        notificationmanager.notify(notificationId, notification);


    }

    private HappyMomentModel loadCameraPhotoData(Context activity) {

        String[] projection, selectionArgs;
        String selection, CAMERA = "Camera", phoneDate, cameraDate;
        Cursor mImageCursor;
        Calendar mInstance;
        Date mDate, imageDate;
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("E MMM dd yyyy");
        boolean dataAvailable = false;
        HappyMomentModel happyMomentModel = new HappyMomentModel();
        happyMomentModel.setAvailableStatus(false);


        projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DATA
        };

        selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " = ?";

        selectionArgs = new String[]{CAMERA};


        mImageCursor = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, null);

        if (mImageCursor != null && mImageCursor.getCount() == 0) {
            mImageCursor = activity.getContentResolver().query(MediaStore.Images.Media.INTERNAL_CONTENT_URI, projection, selection, selectionArgs, null);
        }

        if (mImageCursor != null && mImageCursor.getCount() > 0) {
            mImageCursor.moveToLast();
            Log.d("TestActivity", mImageCursor.getString(0) + "    " + mImageCursor.getString(1));
            Log.d("TestActivity 2", mImageCursor.getString(2) + "    " + mImageCursor.getString(3));
            Log.d("TestActivity image path", mImageCursor.getString(4));

            mInstance = Calendar.getInstance();
            mInstance.getTimeInMillis();
            mDate = mInstance.getTime();
            phoneDate = simpleDateFormat.format(mDate);


            Log.d("TestActivity 3 ", mDate.toString());
            Log.d("TestActivity 4 ", simpleDateFormat.format(mDate));

            mInstance.setTimeInMillis(Long.parseLong(mImageCursor.getString(3)));
            imageDate = mInstance.getTime();
            cameraDate = simpleDateFormat.format(imageDate);


            Log.d("TestActivity 5 ", imageDate.toString());
            Log.d("TestActivity 6 ", simpleDateFormat.format(imageDate));


            if (phoneDate.trim().equalsIgnoreCase(cameraDate.trim())) {
                Log.d("TestActivity 7 ", "Get into Happy Moment Screen");
                dataAvailable = true;

                happyMomentModel = new HappyMomentModel();
                happyMomentModel.setAvailableStatus(true);
                happyMomentModel.setFilePath(mImageCursor.getString(4));


            } else {
                Log.d("TestActivity 8 ", "Not match");
            }
        } else {
            Log.d("TestActivity 9 ", "No Camera MultipleReportData Available");
        }


        if (mImageCursor != null)
            mImageCursor.close();

        return happyMomentModel;

    }
}