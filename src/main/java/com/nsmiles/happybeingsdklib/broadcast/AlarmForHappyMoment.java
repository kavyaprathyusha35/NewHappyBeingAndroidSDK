package com.nsmiles.happybeingsdklib.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;

import com.nsmiles.happybeingsdklib.Models.HappyMomentModel;
import com.nsmiles.happybeingsdklib.broadcastnotifications.ShowHappyMomentNotification;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.core.content.ContextCompat;

/**
 * Created by Admin on 29-12-2016.
 */

@SuppressWarnings("DefaultFileTemplate")
public class AlarmForHappyMoment extends BroadcastReceiver

{

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        assert pm != null;
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire(1000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            storage_Version_check(context);
        }
        else {
            setNowNotificationTrigger(context);
        }
        // setNextDayAlarm(context);
        wl.release();
    }


    public void setAlarm(Context context, long millisec) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(context, AlarmForHappyMoment.class);
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, millisec, 1000 * 60 * 60 * 24, contentIntent);
    }

    public static void cancelAlarm(Context context) {
        Intent intent = new Intent(context, AlarmForHappyMoment.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    private void setNowNotificationTrigger(Context context) {

        HappyMomentModel happyMomentModel = loadCameraPhotoData(context);
        if (happyMomentModel.isAvailableStatus() && happyMomentModel.getFilePath() != null && happyMomentModel.getFilePath().length() > 0) {
            Intent notificationIntent = new Intent(context, ShowHappyMomentNotification.class);
            PendingIntent contentIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, 0);
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), contentIntent);
        }
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



    private void storage_Version_check(Context context) {
        try {
            if (ContextCompat.checkSelfPermission(context,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                setNowNotificationTrigger(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(getClass().getSimpleName(), e.toString());
        }
    }
}