package com.nsmiles.happybeingsdklib.broadcastnotifications;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.nsmiles.happybeingsdklib.Models.AudioDetails;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;
import com.nsmiles.happybeingsdklib.broadcast.CloseNotification;
import com.nsmiles.happybeingsdklib.broadcast.StartNotificationEvent;
import com.nsmiles.happybeingsdklib.dagger.data.PreferenceManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import static android.content.Context.MODE_PRIVATE;
import static com.nsmiles.happybeingsdklib.broadcast.NetworkChangeReceiver.ifAppIsInBackGround;

/**
 * Created by Admin on 07-12-2016.
 */

@SuppressWarnings("DefaultFileTemplate")
public class ShowMindGymAudioNotification extends BroadcastReceiver {

    int id;
    String selectedRole;
    String secondProfile;
    private String currentProfile;
    public String title;
    public SQLiteDatabase db;
    MySql dbHelper;
    CommonUtils commonUtils;
    String primary_profile;
    Context show_context;
    AudioDetails audioDetails;
    String isSignedIn;
    String download_status, sd_card_path, base_url, sub_url, audio_title, day_number, audio_number, audio_id, update_table;
    File check_file_available;
    SharedPreferences sharedPreferences;
    String contenttitle,content_text;
    Bitmap wishicon;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("AlarmForMindGymAudio", "4");

        try {
            sharedPreferences = context.getSharedPreferences(AppConstants.SHARED_HAPPY_BEING, MODE_PRIVATE);
            isSignedIn = sharedPreferences.getString("user_login", "");
            show_context = context;
            commonUtils = new CommonUtils();
            selectedRole = sharedPreferences.getString("user_role", "");
            currentProfile = selectedRole.toUpperCase();
            audioDetails = new AudioDetails();

            audioDetails = getCompletedDate(context);
            SystemClock.sleep(3000);


            int notificationId = 0;
            Intent i = new Intent(context, StartNotificationEvent.class);
            i.putExtra("notificationId", notificationId);
            i.putExtra("FROM", "COACH");

            // Send data to NotificationView Class
            PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, i,
                    PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);


            Intent dismissIntent = new Intent(context, CloseNotification.class);
            dismissIntent.putExtra("notificationId", notificationId);
            dismissIntent.putExtra("FROM", "COACH");

            PendingIntent dismissPending = PendingIntent.getBroadcast(context, 0, dismissIntent, 0);

            // Inflate the notification layout as RemoteViews
            RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
            int color = Color.BLACK;
            // Set text on a TextView in the RemoteViews programmatically.
            //contentView.setTextColor(R.id.tvNotificationTitle, ContextCompat.getColor(this, android.R.color.black));
            contentView.setTextViewText(R.id.tvNotificationMessage, "Time for today's self-care practice to achieve well-being and happiness");

            contentView.setTextViewText(R.id.first_button, "REMIND LATER");
            contentView.setTextViewText(R.id.second_button, "START");

            contentView.setViewVisibility(R.id.first_button, View.VISIBLE);
            contentView.setViewVisibility(R.id.second_button, View.VISIBLE);

            contentView.setOnClickPendingIntent(R.id.second_button, pIntent);
            contentView.setOnClickPendingIntent(R.id.first_button, dismissPending);
            Bitmap largeIconBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hb_db_tr);
            Log.d("ifAppIsInBackGround(?)", String.valueOf(ifAppIsInBackGround()));

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


            if (ifAppIsInBackGround()) {
                Log.d("ifAppIsInBackGround(Y)", String.valueOf(ifAppIsInBackGround()));

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, AppConstants.CHANNEL_ID)

                        // Set Icon
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @SuppressLint("Recycle")
    private AudioDetails getCompletedDate(Context context) {
        SimpleDateFormat yyy_mm_dd_format;
        Date today, server_date;
        int previous_day;
        String server_date_txt, today_txt;
        today = new Date();

        try {

            yyy_mm_dd_format = new SimpleDateFormat("yyyy-MM-dd");
            today_txt = yyy_mm_dd_format.format(today);
            today = (Date) yyy_mm_dd_format.parse(today_txt);

            previous_day = sharedPreferences.getInt("COACH_NUMBER_LAST_SYNC", -1);
            server_date_txt = sharedPreferences.getString("COACH_DATE_LAST_SYNC", "");
            server_date = (Date) yyy_mm_dd_format.parse(server_date_txt);

            if (today.equals(server_date)) {
                Log.e("bbbbbbbb", "aaaa  equal     " + today_txt);
            } else {
                int daysdiff = 0;
                long diff = today.getTime() - server_date.getTime();
                long diffDays = (diff / (1000 * 60 * 60 * 24));
                daysdiff = (int) diffDays;
                previous_day = previous_day + daysdiff;
                Log.e("bbbbbbbb", "aaaa not equal     " + previous_day);
            }


            if (previous_day > 0) {


                if (previous_day <= 40) {
                    // relax
                    getRelaxCoachAudio(previous_day, context);
                } else {
                    if (previous_day > 31) {
                        previous_day = previous_day % 31;
                    }
                    audioDetails = getDetails(previous_day, context);
                    // based on profile
                }
            } else {
                getRelaxCoachAudio(1, context);
                // relax first audio
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioDetails;
    }


    private AudioDetails getRelaxCoachAudio(int position, Context context) {

        try {
            @SuppressLint("Recycle") Cursor cursor;
            switch (currentProfile) {

                case "STUDENT": // STUDENT
                case "LOOKING_JOB": // LOOKING_JOB
                    dbHelper = new MySql(context, "mydb", null, MySql.version);
                    db = dbHelper.getWritableDatabase();
                    cursor = db.rawQuery("SELECT * FROM relax_coach_student_job", null);

                    if (cursor != null && cursor.getCount() > 0) {
                        if (position >= cursor.getCount()) {
                            cursor.moveToFirst();
                        } else {
                            cursor.moveToPosition(position - 1);
                        }
                        audio_id = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("_ID")));
                        audio_title = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_TITLE"));
                        base_url = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_BASE_URL"));
                        sub_url = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_SUB_URL"));
                        download_status = cursor.getString(cursor.getColumnIndexOrThrow("DOWNLOAD_STATUS"));
                        sd_card_path = cursor.getString(cursor.getColumnIndexOrThrow("SD_CARD_PATH"));
                        day_number = cursor.getString(cursor.getColumnIndexOrThrow("DAY_NUMBER"));
                        audio_number = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_NUMBER"));
                        update_table = "relax_coach_student_job";
                        audioDetails.setAudio_name(audio_title);
                        if (download_status.equalsIgnoreCase("0")) {
                            // Call Download
                            downloadAudio(context, sub_url, audio_title, base_url, audio_id, update_table);
                        } else {
                            if (download_status.equalsIgnoreCase("1") && sd_card_path != null) {
                                check_file_available = new File(sd_card_path);
                                if (check_file_available.exists()) {
                                    // Record Available
                                } else {
                                    // Call Download
                                    downloadAudio(context, sub_url, audio_title, base_url, audio_id, update_table);
                                }

                            } else {
                                // Call Download
                                downloadAudio(context, sub_url, audio_title, base_url, audio_id, update_table);
                            }
                        }
                    }
                    CommonUtils.CloseCursor(cursor);
                    break;


                case "EMPLOYED": // EMPLOYED
                case "STAFF": // STAFF
                case "HOMEMAKER": // HOMEMAKER
                case "SENIORCITIZEN": // SENIORCITIZEN
                case "DEPRESSION":
                case "CHRONIC":
                case "CONCEIVE":
                case "PREGNANT":
                case "CAREGIVE":
                case "EXAM":
                case "MENTAL_HEALTH":
                case "PAIN_RELIEF":

                default:
                    dbHelper = new MySql(context, "mydb", null, MySql.version);
                    db = dbHelper.getWritableDatabase();
                    cursor = db.rawQuery("SELECT * FROM relax_coach_others", null);

                    if (cursor != null && cursor.getCount() > 0) {
                        if (position >= cursor.getCount()) {
                            cursor.moveToFirst();
                        } else {
                            cursor.moveToPosition(position - 1);
                        }
                        audio_id = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("_ID")));
                        audio_title = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_TITLE"));
                        base_url = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_BASE_URL"));
                        sub_url = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_SUB_URL"));
                        download_status = cursor.getString(cursor.getColumnIndexOrThrow("DOWNLOAD_STATUS"));
                        sd_card_path = cursor.getString(cursor.getColumnIndexOrThrow("SD_CARD_PATH"));
                        day_number = cursor.getString(cursor.getColumnIndexOrThrow("DAY_NUMBER"));
                        audio_number = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_NUMBER"));
                        update_table = "relax_coach_others";
                        audioDetails.setAudio_name(audio_title);
                        if (download_status.equalsIgnoreCase("0")) {
                            // Call Download
                            downloadAudio(context, sub_url, audio_title, base_url, audio_id, update_table);
                        } else {
                            if (download_status.equalsIgnoreCase("1") && sd_card_path != null) {
                                check_file_available = new File(sd_card_path);
                                if (check_file_available.exists()) {
                                    // Record Available
                                } else {
                                    // Call Download
                                    downloadAudio(context, sub_url, audio_title, base_url, audio_id, update_table);
                                }

                            } else {
                                // Call Download
                                downloadAudio(context, sub_url, audio_title, base_url, audio_id, update_table);
                            }
                        }
                    }
                    CommonUtils.CloseCursor(cursor);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return audioDetails;
    }


    private AudioDetails getDetails(int position, Context context) {

        try {
            @SuppressLint("Recycle") Cursor cursor;
            switch (currentProfile) {

                case "STUDENT": // STUDENT
                case "LOOKING_JOB": // LOOKING_JOB
                    dbHelper = new MySql(context, "mydb", null, MySql.version);
                    db = dbHelper.getWritableDatabase();
                    cursor = db.rawQuery("SELECT * FROM coach_audio_student_job", null);

                    if (cursor != null && cursor.getCount() > 0) {
                        if (position >= cursor.getCount()) {
                            cursor.moveToFirst();
                        } else {
                            cursor.moveToPosition(position - 1);
                        }
                        audio_id = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("_ID")));
                        audio_title = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_TITLE"));
                        base_url = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_BASE_URL"));
                        sub_url = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_SUB_URL"));
                        download_status = cursor.getString(cursor.getColumnIndexOrThrow("DOWNLOAD_STATUS"));
                        sd_card_path = cursor.getString(cursor.getColumnIndexOrThrow("SD_CARD_PATH"));
                        day_number = cursor.getString(cursor.getColumnIndexOrThrow("DAY_NUMBER"));
                        audio_number = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_NUMBER"));
                        update_table = "coach_audio_student_job";
                        audioDetails.setAudio_name(audio_title);
                        if (download_status.equalsIgnoreCase("0")) {
                            // Call Download
                            downloadAudio(context, sub_url, audio_title, base_url, audio_id, update_table);
                        } else {
                            if (download_status.equalsIgnoreCase("1") && sd_card_path != null) {
                                check_file_available = new File(sd_card_path);
                                if (check_file_available.exists()) {
                                    // Record Available
                                } else {
                                    // Call Download
                                    downloadAudio(context, sub_url, audio_title, base_url, audio_id, update_table);
                                }

                            } else {
                                // Call Download
                                downloadAudio(context, sub_url, audio_title, base_url, audio_id, update_table);
                            }
                        }
                    }
                    CommonUtils.CloseCursor(cursor);
                    break;


                case "EMPLOYED": // EMPLOYED
                case "STAFF": // STAFF
                case "HOMEMAKER": // HOMEMAKER
                case "SENIORCITIZEN": // SENIORCITIZEN
                case "DEPRESSION":
                case "CHRONIC":
                case "CONCEIVE":
                case "PREGNANT":
                case "CAREGIVE":
                case "EXAM":
                case "MENTAL_HEALTH":
                case "PAIN_RELIEF":

                default:
                    dbHelper = new MySql(context, "mydb", null, MySql.version);
                    db = dbHelper.getWritableDatabase();
                    cursor = db.rawQuery("SELECT * FROM coach_audio_all_other", null);

                    if (cursor != null && cursor.getCount() > 0) {
                        if (position >= cursor.getCount()) {
                            cursor.moveToFirst();
                        } else {
                            cursor.moveToPosition(position - 1);
                        }
                        audio_id = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("_ID")));
                        audio_title = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_TITLE"));
                        base_url = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_BASE_URL"));
                        sub_url = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_SUB_URL"));
                        download_status = cursor.getString(cursor.getColumnIndexOrThrow("DOWNLOAD_STATUS"));
                        sd_card_path = cursor.getString(cursor.getColumnIndexOrThrow("SD_CARD_PATH"));
                        day_number = cursor.getString(cursor.getColumnIndexOrThrow("DAY_NUMBER"));
                        audio_number = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_NUMBER"));
                        update_table = "coach_audio_all_other";
                        audioDetails.setAudio_name(audio_title);
                        if (download_status.equalsIgnoreCase("0")) {
                            // Call Download
                            downloadAudio(context, sub_url, audio_title, base_url, audio_id, update_table);
                        } else {
                            if (download_status.equalsIgnoreCase("1") && sd_card_path != null) {
                                check_file_available = new File(sd_card_path);
                                if (check_file_available.exists()) {
                                    // Record Available
                                } else {
                                    // Call Download
                                    downloadAudio(context, sub_url, audio_title, base_url, audio_id, update_table);
                                }

                            } else {
                                // Call Download
                                downloadAudio(context, sub_url, audio_title, base_url, audio_id, update_table);
                            }
                        }
                    }
                    CommonUtils.CloseCursor(cursor);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return audioDetails;
    }

    private void downloadAudio(Context context, String sub, String file, String base, String id, String table) {

        try {
            if (CommonUtils.isNetworkAvailable(context)) {
/*
                if (commonUtils.getPaidStatus(context)) {
                    Intent intent = new Intent(context, CoachDownloadService.class);
                    intent.putExtra("URL", sub);
                    intent.putExtra("FILE_NAME", file);
                    intent.putExtra("AUDIO_BASE_URL", base);
                    intent.putExtra("AUDIO_NUMBER", audio_number);
                    intent.putExtra("UPDATE_TABLE", table);
                    context.startService(intent);
                }
*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}