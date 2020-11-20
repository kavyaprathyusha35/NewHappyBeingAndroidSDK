package com.nsmiles.happybeingsdklib.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nsmiles.happybeingsdklib.MindGym.AddEmotionRequest;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.ServerApiConnectors.API_Response_Listener;
import com.nsmiles.happybeingsdklib.ServerApiConnectors.ApiProvider;
import com.nsmiles.happybeingsdklib.playaudio.MindGymModel;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by Kavya on 10/27/2020.
 */

public class CommonUtils {
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    static String emotion;
    private MySql dbHelper;
    private SQLiteDatabase db;
    List<MindGymModel> JsonMindGymModelList;
    String dd, mm, simple_yyy_mm_dd;
    Date dd_date, mm_month, simple_date;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat today_dd;
    private SimpleDateFormat today_mm;

    public static void alertDialog(Activity activity, String title, String message, String button_name) {
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyAlertDialogMaterialStyle);
            //noinspection deprecation
            builder.setMessage(message)
                    .setCancelable(true)
                    .setTitle(Html.fromHtml("<font color='#F26722'>" + title + "</font>"))
                    .setPositiveButton(button_name, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public static void CloseCursor(Cursor cursor){
        if(cursor!=null){
            cursor.close();
        }
    }
    public static boolean isNetworkAvailable(Context c) {
        boolean isConnectedWifi = false;
        boolean isConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressWarnings("deprecation") NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equals(ni.getTypeName()))
                if (ni.isConnected())
                    isConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase(ni.getTypeName()))
                if (ni.isConnected())
                    isConnectedMobile = true;
        }
        return isConnectedWifi || isConnectedMobile;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static String DDMMYYYYString(String getDate) throws ParseException {

        SimpleDateFormat DDFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat default_date_format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");  //// Tue Nov 21 17:15:27 GMT+05:30 2017
        Date DDMMYYYY = null;
        String dateString = null;

        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        default_date_format.setTimeZone(gmtTime);
        if(getDate.contains("IST")){
            default_date_format = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'IST' yyyy");    //// Tue Nov 21 17:15:27 IST 2017
        }

        if(getDate.contains("+0000")){
            default_date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");    //// 2018-02-15 14:29:47 +0000
        }

        DDMMYYYY = default_date_format.parse(getDate);
        dateString = DDFormat.format(DDMMYYYY);
        return dateString;

    }

    public String getTokenId(Activity activity) {
        SdkPreferenceManager prefs = new SdkPreferenceManager(activity);
        Log.i("CommonUtils", "Access token is "+prefs.get(AppConstants.SDK_ACCESS_TOKEN, ""));
        Log.i("CommonUtils", "Access token is "+prefs.get(AppConstants.SDK_REFRESH_TOKEN, ""));
        return prefs.get(AppConstants.SDK_ACCESS_TOKEN, "");
    }

    public String getUserEmail(Activity activity) {
        SdkPreferenceManager prefs = new SdkPreferenceManager(activity);
        return prefs.get(AppConstants.SDK_EMAIL, "");
    }


    public String getExpiryDate(Activity activity) {
        SdkPreferenceManager prefs = new SdkPreferenceManager(activity);
        return prefs.get(AppConstants.SDK_EXPIRY_AT, "");
    }


    public static void showToast(Activity activity, String message) {

        try {
            WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

            Toast toast = Toast.makeText(activity, message, Toast.LENGTH_LONG);
            View toastView = toast.getView(); //This'll return the default View of the Toast.

            /* And now you can get the TextView of the default View of the Toast. */
            TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
            toastMessage.setTextSize(14);
            toastMessage.setTextColor(Color.WHITE);
            //  toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_launcher, 0, 0, 0);
            toastMessage.setGravity(Gravity.CENTER);
            toastMessage.setPadding(8, 4, 8, 4);
            toastMessage.setCompoundDrawablePadding(4);
            toastMessage.setMinWidth(width);
            toastMessage.setMinHeight(100);
            toastMessage.setMaxHeight(120);
            toastMessage.setMaxWidth(width);
            //noinspection deprecation
            toastView.setBackgroundColor(activity.getResources().getColor(R.color.toast));
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void showHowAreYouFeeling(final Activity activity, final String user, final String activity_name, final String task_name, final String from) {

        String START_DATE = "", END_DATE = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z", Locale.getDefault());
        START_DATE = df.format(Calendar.getInstance().getTime());

        /*Analytics Activity Calling*/
        END_DATE = df.format(Calendar.getInstance().getTime());
        /*Analytics Activity Calling*/


        final LinearLayout sad_layout, better_layout, calm_layout, confident_layout, motivated_layout, thankful_layout;
        final ImageView sad_img, better_img, calm_img, confident_img, motivated_img, thankful_img;
        Button save_btn;
        //  sad better  calm   confident  motivated  energised

        final Dialog d = new Dialog(activity);
        d.setContentView(R.layout.dialog_feeling_right_layout);
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // d.setTitle("Happy Being");
        d.setCancelable(false);
        d.setCanceledOnTouchOutside(false);
        if (!activity.isFinishing()) {
            d.show();
        }

        sad_layout = (LinearLayout) d.findViewById(R.id.sad_layout);
        better_layout = (LinearLayout) d.findViewById(R.id.better_layout);
        calm_layout = (LinearLayout) d.findViewById(R.id.calm_layout);
        confident_layout = (LinearLayout) d.findViewById(R.id.confident_layout);
        motivated_layout = (LinearLayout) d.findViewById(R.id.motivated_layout);
        thankful_layout = (LinearLayout) d.findViewById(R.id.thankful_layout);

        sad_img = (ImageView) d.findViewById(R.id.sad_img);
        better_img = (ImageView) d.findViewById(R.id.better_img);
        calm_img = (ImageView) d.findViewById(R.id.calm_img);
        confident_img = (ImageView) d.findViewById(R.id.confident_img);
        motivated_img = (ImageView) d.findViewById(R.id.motivated_img);
        thankful_img = (ImageView) d.findViewById(R.id.thankful_img);

        save_btn = (Button) d.findViewById(R.id.save_btn);

        sad_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "sad";
                sad_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.sad_filled));
                better_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.better_gray));
                calm_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.calm_gray));
                confident_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.confident_gray));
                motivated_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.motivated_gray));
                thankful_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.thankful_gray));
            }
        });

        better_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "better";
                sad_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.sad_gray));
                better_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.better_filled));
                calm_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.calm_gray));
                confident_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.confident_gray));
                motivated_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.motivated_gray));
                thankful_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.thankful_gray));

            }
        });

        calm_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "calm";
                sad_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.sad_gray));
                better_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.better_gray));
                calm_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.calm_filled));
                confident_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.confident_gray));
                motivated_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.motivated_gray));
                thankful_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.thankful_gray));

            }
        });

        confident_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "confident";
                sad_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.sad_gray));
                better_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.better_gray));
                calm_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.calm_gray));
                confident_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.confident_filled));
                motivated_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.motivated_gray));
                thankful_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.thankful_gray));

            }
        });

        motivated_layout.setOnClickListener(new View.OnClickListener() {
            private String emotion;

            @Override
            public void onClick(View v) {
                emotion = "motivated";
                sad_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.sad_gray));
                better_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.better_gray));
                calm_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.calm_gray));
                confident_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.confident_gray));
                motivated_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.motivated_filled));
                thankful_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.thankful_gray));

            }
        });

        thankful_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion = "energised";
                sad_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.sad_gray));
                better_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.better_gray));
                calm_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.calm_gray));
                confident_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.confident_gray));
                motivated_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.motivated_gray));
                thankful_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.thankful_filled));
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CommonUtils.isNetworkAvailable(activity)) {

                    if (emotion.length() > 0) {

                        AddEmotionRequest emotionss=new AddEmotionRequest();
                        emotionss.setUser_id(user);
                        emotionss.setDate_time(new Date().toString());
                        emotionss.setFeature(from);
                        emotionss.setEmotion1(emotion);
                        emotionss.setActivity(task_name);

                        new ApiProvider.SaveEmotions(emotionss, AppConstants.DEFAULT_TOKEN, 2, activity, "Saving...", new API_Response_Listener<String>() {

                            @Override
                            public void onComplete(String data, long request_code, String failure_code) {
                                Log.e("failure_code", failure_code);
                                if (data == null) {
                                    Log.e("data", "null");
                                } else {

                                    if (emotion.length() > 0) {
                                        MySql dbHelper = new MySql(activity, "mydb", null, MySql.version);
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                                        ContentValues cv = new ContentValues();
                                        cv.put("date_time", new Date().toString());
                                        cv.put("feature", activity_name);
                                        cv.put("activity", task_name);
                                        cv.put("emotion1", emotion);
                                        cv.put("sync_flag", "0");
                                        db.insert("New_Feelings_Table", null, cv);
                                        db.close();
                                        d.dismiss();
                                        activity.finish();
                                        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                        emotion = "";
                                    } else {
                                        CommonUtils.showToast(activity, "Please select you feelings...");
                                    }


                                }
                            }
                        }).call();

                    }else {
                        CommonUtils.showToast(activity, "Please select you feelings...");
                    }

                }
                else {
                    if (emotion.length() > 0) {
                        MySql dbHelper = new MySql(activity, "mydb", null, MySql.version);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put("date_time", new Date().toString());
                        cv.put("feature", activity_name);
                        cv.put("activity", task_name);
                        cv.put("emotion1", emotion);
                        cv.put("sync_flag", "0");
                        db.insert("New_Feelings_Table", null, cv);
                        db.close();
                        d.dismiss();
                        activity.finish();
                        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        emotion = "";
                    } else {
                        CommonUtils.showToast(activity, "Please select you feelings...");
                    }

                }


            }
        });
    }

    public static boolean checkForOtherAppsPlaying(final Context context) {
        AudioManager.OnAudioFocusChangeListener focusChangeListener =
                new AudioManager.OnAudioFocusChangeListener() {
                    public void onAudioFocusChange(int focusChange) {
                        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                        switch (focusChange) {

                            case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK):
                                // Lower the volume while ducking.
                                break;
                            case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT):
                                break;

                            case (AudioManager.AUDIOFOCUS_LOSS):
                                break;

                            case (AudioManager.AUDIOFOCUS_GAIN):
                                break;
                            default:
                                break;
                        }
                    }
                };

        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int result = am.requestAudioFocus(focusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;

    }

    public boolean getGratitudeData(Activity activity, int day_number) {
        SdkPreferenceManager sdkPreferenceManager = new SdkPreferenceManager(activity);
        int gratitude_date = sdkPreferenceManager.get("GRATITUDE_DAY", 0);
        boolean gratitude_completed = sdkPreferenceManager.get("GRATITUDE_COMPLETED", false);
        if (gratitude_date == day_number) {
            return gratitude_completed;
        }
        return false;
    }

    public void getCoachDataFromApi(final Activity activity) {


        today_dd = new SimpleDateFormat("dd", Locale.ENGLISH);
        today_mm = new SimpleDateFormat("MMM", Locale.ENGLISH);

        String parameters = "email=" + getUserEmail(activity);
        String version = "1";
        final String day = "7";
        final String unique = "&unique=true";


        parameters = parameters.concat("&version=" + version).concat("&days=" + day).concat(unique);
        if (CommonUtils.isNetworkAvailable(activity)) {

            new ApiProvider.GetMindGymData(parameters, getTokenId(activity),
                    1, new API_Response_Listener<List<MindGymModel>>() {
                @Override
                public void onComplete(List<MindGymModel> data, long request_code, String failure_code) {


                    try {
                        dbHelper = new MySql(activity, "mydb", null, MySql.version);
                        db = dbHelper.getWritableDatabase();

                        JsonMindGymModelList = new ArrayList<>();
                        if (data != null) {


                            @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM coach_offline", null);

                            if (cursor.getCount() > 0) {
                                db.delete("coach_offline", null, null);
                            }

                            for (int i = 0; i < data.size(); i++) {

                                dd_date = simpleDateFormat.parse(data.get(i).getCurrentDate());
                                dd = today_dd.format(dd_date);

                                mm_month = simpleDateFormat.parse(data.get(i).getCurrentDate());
                                mm = new SimpleDateFormat("MMMM").format(mm_month);

                                mm_month = new SimpleDateFormat("MMMM").parse(mm);
                                mm = today_mm.format(mm_month);

                                simple_date = simpleDateFormat.parse(data.get(i).getCurrentDate());
                                simple_yyy_mm_dd = simpleDateFormat.format(simple_date);

/*
                                if (i == 0) {
                                    editor = activity.getSharedPreferences("HAPPY_BEING", MODE_PRIVATE).edit();
                                    editor.putInt("COACH_NUMBER_LAST_SYNC", data.get(0).getDateDifference());
                                    editor.putString("COACH_DATE_LAST_SYNC", data.get(0).getCurrentDate());
                                    editor.apply();
                                    editor.commit();
                                }
*/
                                MindGymModel mindGymModel = new MindGymModel();
                                mindGymModel.setDateDifference(data.get(i).getDateDifference());
                                mindGymModel.setActual_server_day(data.get(i).getDateDifference());
                                mindGymModel.setDdDate(dd);
                                mindGymModel.setMmMonth(mm);
                                mindGymModel.setPlayStatus(data.get(i).getPlayStatus());
                                mindGymModel.setDataSynced(data.get(i).getDataSynced());
                                mindGymModel.setYyyyMmDdDate(simple_yyy_mm_dd);
                                mindGymModel.setCurrentDate(data.get(i).getCurrentDate());
                                mindGymModel.setPaymentStatus(data.get(i).getPaymentStatus());
                                JsonMindGymModelList.add(mindGymModel);

                                ContentValues cv = new ContentValues();
                                cv.put("SYNC_FLAG", "1");
                                cv.put("DATE_DIFFERENCE", data.get(i).getDateDifference());
                                cv.put("ACTUAL_SERVER_DATE", data.get(i).getDateDifference());
                                cv.put("DD_DATE", dd);
                                cv.put("MM_MONTH", mm);
                                cv.put("PLAY_STATUS", data.get(i).getPlayStatus());
                                cv.put("DATA_SYNCED", data.get(i).getDataSynced());
                                cv.put("YYYY_MM_DD_DATE", simple_yyy_mm_dd);
                                cv.put("CURRENT_DATEE", data.get(i).getCurrentDate());
                                cv.put("PAYMENT_STATUS", data.get(i).getPaymentStatus());
                                cv.put("FEEDBACK_POPUP", "");
                                cv.put("OFFER_40_POPUP", "");
                                cv.put("OFFER_20_POPUP", "");
                                db.insert("coach_offline", null, cv);


                            }

                            if (JsonMindGymModelList != null) {

                                if (JsonMindGymModelList.get(0).getDateDifference() == 5 ||
                                        JsonMindGymModelList.get(0).getDateDifference() == 7 &&
                                                JsonMindGymModelList.get(0).getPaymentStatus().equalsIgnoreCase("TRIAL")) {

                                } else if (JsonMindGymModelList.get(0).getDateDifference() == 10 ||
                                        JsonMindGymModelList.get(0).getDateDifference() == 11 ||
                                        JsonMindGymModelList.get(0).getDateDifference() == 12 &&
                                                JsonMindGymModelList.get(0).getPaymentStatus().equalsIgnoreCase("TRIAL")) {
                                        ContentValues contentValues = new ContentValues();
                                        if (JsonMindGymModelList.get(0).getDateDifference() == 10) {
                                            //   getMindGymAudioCount(10);
                                            contentValues.put("OFFER_40_POPUP", "10");
                                            db.update("coach_offline", contentValues, "DATE_DIFFERENCE" + "=?",
                                                    new String[]{"10"});


                                        } else if (JsonMindGymModelList.get(0).getDateDifference() == 11) {
                                            ///    getMindGymAudioCount(11);
                                            contentValues.put("OFFER_40_POPUP", "11");
                                            db.update("coach_offline", contentValues, "DATE_DIFFERENCE" + "=?",
                                                    new String[]{"11"});
                                        } else {
                                            //   getMindGymAudioCount(12);
                                            contentValues.put("OFFER_40_POPUP", "12");
                                            db.update("coach_offline", contentValues, "DATE_DIFFERENCE" + "=?",
                                                    new String[]{"12"});
                                        }

                                }
                                /////   40 percent offer
                                else if (JsonMindGymModelList.get(0).getDateDifference() == 14 &&
                                        JsonMindGymModelList.get(0).getPaymentStatus().equalsIgnoreCase("TRIAL")) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("OFFER_20_POPUP", "14");
                                    db.update("coach_offline", contentValues, "DATE_DIFFERENCE" + "=?",
                                            new String[]{"14"});
                                    // Call 20 % Api

                                }
                                /////   20 percent offer
                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }).call();


        }
    }

    public void setGratitudeData(Activity activity, int day_number, boolean completed) {
        SdkPreferenceManager sdkPreferenceManager = new SdkPreferenceManager(activity);
        sdkPreferenceManager.save("GRATITUDE_COMPLETED", completed);
        sdkPreferenceManager.save("GRATITUDE_DAY", day_number);
    }

    public static Date DDMMYYYY(String getDate) throws ParseException {

        SimpleDateFormat DDFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat default_date_format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");  //// Tue Nov 21 17:15:27 GMT+05:30 2017
        Date DDMMYYYY = null;
        String dateString = null;

        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        default_date_format.setTimeZone(gmtTime);
        if(getDate.contains("IST")){
            default_date_format = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'IST' yyyy");
        }

        if(getDate.contains("+0000")){
            default_date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");    //// 2018-02-15 14:29:47 +0000
        }

        DDMMYYYY = default_date_format.parse(getDate);
        dateString = DDFormat.format(DDMMYYYY);
        return DDFormat.parse(dateString);

    }
    public static void assignProfilePic(Activity activity, String from, String primary, String gender, ImageView imageView) {
        Glide.with(activity).load(R.drawable.emp_boy_eight).into(imageView);

    }
    public static void convertBase64toBitmap(Activity activity,String base64Str, ImageView imageView) {
        try {
            byte[] decodedBytes = Base64.decode(base64Str.substring(base64Str.indexOf(",") + 1), Base64.DEFAULT);

            imageView.setImageBitmap(BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length));
        } catch (Exception e) {
            e.printStackTrace();
            //imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.image_not_supported));
            Glide.with(activity).load(R.drawable.image_not_supported).into(imageView);

        }
    }
    public static void DDDateFormat(String getDate, TextView textView) throws ParseException {
        SimpleDateFormat DDFormat = new SimpleDateFormat("dd");
        SimpleDateFormat default_date_format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");  //// Tue Nov 21 17:15:27 GMT+05:30 2017
        Date DDDate;
        String dateString;


        if(getDate.contains("IST")){
            default_date_format = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'IST' yyyy");
        }

        if(getDate.contains("+0000")){
            default_date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");    //// 2018-02-15 14:29:47 +0000
        }

        DDDate = default_date_format.parse(getDate);
        dateString = DDFormat.format(DDDate);
        textView.setText(dateString);


    }

    public static void MMDateFormat(String getDate, TextView textView) throws ParseException {
        SimpleDateFormat DDFormat = new SimpleDateFormat("MMM");
        SimpleDateFormat default_date_format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");  //// Tue Nov 21 17:15:27 GMT+05:30 2017
        Date DDDate;
        String dateString;

        if(getDate.contains("IST")){
            default_date_format = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'IST' yyyy");
        }

        if(getDate.contains("+0000")){
            default_date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");    //// 2018-02-15 14:29:47 +0000
        }

        DDDate = default_date_format.parse(getDate);
        dateString = DDFormat.format(DDDate);
        textView.setText(dateString);

    }

    public static boolean hasText(EditText editText, String message) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                //noinspection deprecation
                editText.setError(Html.fromHtml("<font color='red'>" + message + "</font>"));
            } else {
                editText.setError(message);
            }
            return false;
        }

        return true;
    }

    public static Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {

        Bitmap bm = null;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, options);

        return bm;
    }

    private static int calculateInSampleSize(

            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }

        return inSampleSize;
    }
    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    private static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    private static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    public static void playStore(Activity activity) {

        try {
            Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                activity.startActivity(myAppLinkToMarket);
            } catch (ActivityNotFoundException e) {
                CommonUtils.showToast(activity, "unable to find market app");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setRelaxAlarm(Context context, int range) {
        /*Setting relax alarm*/
        try {
            ContentValues relaxCV = new ContentValues();
            dbHelper = new MySql(context, "mydb", null, MySql.version);
            db = dbHelper.getWritableDatabase();
            Cursor relax_cursor = db.rawQuery("SELECT * FROM RelaxAlarmNotification", null);

/*
            if (relax_cursor.getCount() > 0) {
                relax_cursor.moveToFirst();
                do {
                    relax_time = relax_cursor.getLong(relax_cursor.getColumnIndexOrThrow("alarm_time"));
                    range = relax_cursor.getInt(relax_cursor.getColumnIndexOrThrow("alarm_range"));

                    today = new Date();
                    calendar = Calendar.getInstance();
                    calendar.setTime(today);
                    current_date_milliseconds = calendar.getTimeInMillis();
                    gregorianCalendar = new GregorianCalendar();

                    if (relax_time > current_date_milliseconds) {
                        // set the alarm and break the loop
                        AlarmForRelaxOne alarmEnd = new AlarmForRelaxOne();
                        alarmEnd.cancelAlarm(context);
                        alarmEnd.setAlarm(context, relax_time);
                        alarmSetOrNot = true;
                        break;
                    }
                }
                while (relax_cursor.moveToNext());


                // checking alarm triggered or not
                // if not triggered set it to next day.

                if (!alarmSetOrNot) {
                    relax_cursor = db.rawQuery("SELECT * FROM RelaxAlarmNotification", null);
                    if (relax_cursor.getCount() > 0) {
                        relax_cursor.moveToFirst();

                        do {
                            _id = relax_cursor.getInt(relax_cursor.getColumnIndexOrThrow("_id"));
                            relax_time = relax_cursor.getLong(relax_cursor.getColumnIndexOrThrow("alarm_time"));
                            range = relax_cursor.getInt(relax_cursor.getColumnIndexOrThrow("alarm_range"));
                            gregorianCalendar.setTimeInMillis(relax_time);
                            gregorianCalendar.add(Calendar.DATE, 1);
                            relaxCV.put("alarm_range", range);
                            relaxCV.put("alarm_time", gregorianCalendar.getTimeInMillis());
                            int count = db.update("RelaxAlarmNotification", relaxCV, "_id=" + _id, null);
                        }
                        while (relax_cursor.moveToNext());
                    }
                    setRelaxAlarm(context, range);
                }

            }
*/
            relax_cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean isValid(EditText email, String message) {
        email.setError(null);
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email.getText().toString().trim();
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            return true;
        } else {
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                //noinspection deprecation
                email.setError(Html.fromHtml("<font color='red'>" + message + "</font>"));
            } else {
                email.setError(message);
            }
            return false;
        }
    }
    public String getExpiryDate(int moths_to_be_added) {
        SimpleDateFormat dateFormat;
        Calendar gc = Calendar.getInstance();
        gc.add(Calendar.MONTH, moths_to_be_added);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date tomorrow = gc.getTime();
        return dateFormat.format(tomorrow);
    }
    public String DD_MM_YYYY_T() {
        Calendar mInstance = Calendar.getInstance();
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = new Date(mInstance.getTimeInMillis());
        return dateFormat.format(date);
    }

    public static String getDateFormat(String paymentValidityDate) {
        String data = "";
        SimpleDateFormat dateFormat;
        SimpleDateFormat outputDateFormat;
        Date date;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        outputDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        try {
            date = dateFormat.parse(paymentValidityDate);
            data = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void howAreYouFeeling(final Activity activity, final String activity_name, final String task_name) {

        final ImageView feeling_image, better_img, calm_img, confident_img, motivated_img, thankful_img;
        Button save_btn;
        SeekBar seekBar;
        final TextView feelings;
        final RelativeLayout relativeLayout;
        //  sad better  calm   confident  motivated  energised
        final Dialog d = new Dialog(activity,R.style.full_screen_dialog);
        d.setContentView(R.layout.dialog_audio_done_screen);
        assert d.getWindow() != null;
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //   d.setTitle("Happy Being");
        d.setCancelable(false);
        d.setCanceledOnTouchOutside(false);
        d.show();

        seekBar=(SeekBar)d.findViewById(R.id.seekbar);
        feelings=(TextView) d.findViewById(R.id.feelings);
        feeling_image = (ImageView) d.findViewById(R.id.feeling_image);
        relativeLayout = (RelativeLayout) d.findViewById(R.id.relative);
        feeling_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.thankful_));
        feelings.setText("Thankful");
        relativeLayout.setBackgroundColor(Color.parseColor("#cee279"));
        emotion="Thankful";

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(progress <= 16){
                    feeling_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.sad_));
                    feelings.setText("sad");
                    relativeLayout.setBackgroundColor(Color.parseColor("#f7b089"));
                    emotion="sad";
                } else if(progress <= 32){
                    feeling_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.calm));
                    feelings.setText("calm");
                    relativeLayout.setBackgroundColor(Color.parseColor("#f4d3b8"));
                    emotion="calm";
                }else if(progress <= 48){
                    feeling_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.better));
                    feelings.setText("better");
                    relativeLayout.setBackgroundColor(Color.parseColor("#f9eb9b"));
                    emotion="better";

                }else if(progress <= 64){

                    feelings.setText("confident");
                    feeling_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.confident_));
                    relativeLayout.setBackgroundColor(Color.parseColor("#ebf4ba"));
                    emotion="confident";

                }else if(progress <= 80){

                    feelings.setText("motivated");
                    feeling_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.motivated_));
                    relativeLayout.setBackgroundColor(Color.parseColor("#def498"));
                    emotion="motivated";
                }else if(progress <= 100){

                    feelings.setText("Thankful");
                    feeling_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.thankful_));
                    relativeLayout.setBackgroundColor(Color.parseColor("#cee279"));
                    emotion="Thankful";
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        save_btn = (Button) d.findViewById(R.id.done_button_n);


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isNetworkAvailable(activity)) {

                    if (emotion != null && !emotion.equals("")) {

                        AddEmotionRequest emotionss=new AddEmotionRequest();
                        emotionss.setEmail(getUserEmail(activity));
                        emotionss.setDate_time(new Date().toString());
                        emotionss.setFeature("MyCoach");
                        emotionss.setEmotion1(emotion);
                        emotionss.setActivity(task_name);

                        new ApiProvider.SaveEmotions(emotionss, getTokenId(activity), 2, activity, "Saving...", new API_Response_Listener<String>() {

                            @Override
                            public void onComplete(String data, long request_code, String failure_code) {
                                Log.e("failure_code", failure_code);
                                if (data == null) {
                                    Log.e("data", "null");
                                } else {

                                    if (!emotion.equals("")) {
                                        MySql dbHelper = new MySql(activity, "mydb", null, MySql.version);
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                                        ContentValues cv = new ContentValues();
                                        cv.put("date_time", new Date().toString());
                                        cv.put("feature", activity_name);
                                        cv.put("activity", task_name);
                                        cv.put("emotion1", emotion);
                                        cv.put("sync_flag", "0");
                                        db.insert("New_Feelings_Table", null, cv);
                                        db.close();
                                        d.dismiss();
                                        activity.finish();
                                        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                        emotion = "";
                                    } else {
                                        CommonUtils.showToast(activity, "Please select you feelings...");
                                    }


                                }
                            }
                        }).call();

                    } else {
                        CommonUtils.showToast(activity, "Please select you feelings...");
                    }

                }
                else {
                    if (!emotion.equals("")) {
                        MySql dbHelper = new MySql(activity, "mydb", null, MySql.version);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put("date_time", new Date().toString());
                        cv.put("feature", activity_name);
                        cv.put("activity", task_name);
                        cv.put("emotion1", emotion);
                        cv.put("sync_flag", "0");
                        db.insert("New_Feelings_Table", null, cv);
                        db.close();
                        d.dismiss();
                        activity.finish();
                        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        emotion = "";
                    } else {
                        CommonUtils.showToast(activity, "Please select you feelings...");
                    }

                }

                activity.finish();

            }

        });
    }

}
