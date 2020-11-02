package com.nsmiles.happybeingsdklib.UI;


import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.channguyen.rsv.RangeSliderView;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;
import com.nsmiles.happybeingsdklib.broadcast.AlarmForHappyMoment;
import com.nsmiles.happybeingsdklib.broadcast.AlarmForMindGymAffirmations;
import com.nsmiles.happybeingsdklib.broadcast.AlarmForMindGymAudio;
import com.nsmiles.happybeingsdklib.broadcast.AlarmForRelaxOne;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;


public class SettingsLayout extends AppCompatActivity implements View.OnClickListener {

    public int final_start_hour, final_end_hour, final_start_min, final_end_min, final_start_year, final_start_AM_Or_PM,
            final_end_year, final_end_AM_OR_PM, final_start_month, final_end_month, final_start_dayOfMonth, final_end_dayOfMonth;
    public int relax_final_start_hour, relax_final_end_hour, relax_final_start_min, relax_final_end_min, relax_final_start_year,
            relax_final_start_AM_Or_PM, relax_final_end_year, relax_final_end_AM_OR_PM, relax_final_start_month,
            relax_final_end_month, relax_final_start_dayOfMonth, relax_final_end_dayOfMonth;
    SharedPreferences sharedPreferences;
    private boolean dailyGymStartIsChanged = false, dailyGymEndIsChanged = false, relaxStartIsChaged = false, relaxEndIsChanged = false;
    private SQLiteDatabase db;
    private int id = 0;
    String user_login = "false";
    Toolbar alert_toolbar;
    int mHour, mMinute;
    String am_pm;
    public int relax_start_am_pm, relax_end_am_pm;
    int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    int minutes = Calendar.getInstance().get(Calendar.MINUTE);
    // boolean isSignedIn = false;
    RangeSliderView setting_slider;
    int slider_position = 0;
    Spinner repeat_spinner;
    List<String> range = new ArrayList<>();
    String strHrsToShowStart, strHrsToShowEnd;
    MySql dbHelper;
    CommonUtils commonUtils;
    long dailyGymStartTimeLong, dailyGymEndTimeLong, relaxStartTimeLong, relaxEndTimeLong;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    TextView daily_gym_start_hour, daily_gym_start_time_min, daily_gym_start_format;
    TextView daily_gym_end_hour, daily_gym_end_min, daily_gym_end_format;
    TextView relax_start_time_hour;
    TextView relax_end_time_hour;
    LinearLayout daily_gym_start_layout, daily_gym_end_layout;


    LinearLayout coach_mor_layout, coach_eve_layout, my_coach_notifications_layout, mindspa_notifications_layout;
    ImageView coach_toggle_on, coach_toggle_off;
    boolean coach_settings_on_off = true;


    LinearLayout relax_layout;
    ImageView relax_toggle_on, relax_toggle_off;
    boolean relax_settings_on_off = true;


    SharedPreferences.Editor editor;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        initSettingsLayout();
        commonUtils = new CommonUtils();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(SettingsLayout.this, R.color.hb_circle_black_light));
        }
    }

    private void initSettingsLayout() {

        setting_slider = (RangeSliderView) findViewById(R.id.setting_slider);
        repeat_spinner = (Spinner) findViewById(R.id.repeat_spinner);

        daily_gym_start_layout = (LinearLayout) findViewById(R.id.daily_gym_start_layout);
        daily_gym_start_hour = (TextView) findViewById(R.id.daily_gym_start_hour);
        daily_gym_start_time_min = (TextView) findViewById(R.id.daily_gym_start_time_min);
        daily_gym_start_format = (TextView) findViewById(R.id.daily_gym_start_format);


        daily_gym_end_layout = (LinearLayout) findViewById(R.id.daily_gym_end_layout);
        daily_gym_end_hour = (TextView) findViewById(R.id.daily_gym_end_hour);
        daily_gym_end_min = (TextView) findViewById(R.id.daily_gym_end_min);
        daily_gym_end_format = (TextView) findViewById(R.id.daily_gym_end_format);


        relax_start_time_hour = (TextView) findViewById(R.id.relax_start_time_hour);
        relax_end_time_hour = (TextView) findViewById(R.id.relax_end_time_hour);


        coach_mor_layout = (LinearLayout) findViewById(R.id.coach_mor_layout);
        coach_eve_layout = (LinearLayout) findViewById(R.id.coach_eve_layout);
        coach_toggle_on = (ImageView) findViewById(R.id.coach_toggle_on);
        coach_toggle_off = (ImageView) findViewById(R.id.coach_toggle_off);

        relax_layout = (LinearLayout) findViewById(R.id.relax_layout);
        relax_toggle_on = (ImageView) findViewById(R.id.relax_toggle_on);
        relax_toggle_off = (ImageView) findViewById(R.id.relax_toggle_off);
        mindspa_notifications_layout = findViewById(R.id.mindspa_notifications_layout);
        my_coach_notifications_layout = findViewById(R.id.my_coach_notifications_layout);
        Intent intent = getIntent();
        if (intent.hasExtra("FROM_SCREEN")) {
            String fromScreen = intent.getStringExtra("FROM_SCREEN");
            if (fromScreen.equals("CoachReminders")) {
                mindspa_notifications_layout.setVisibility(View.GONE);
                my_coach_notifications_layout.setVisibility(View.VISIBLE);
            }
            else if (fromScreen.equals("MindSpaRminders")) {
                mindspa_notifications_layout.setVisibility(View.VISIBLE);
                my_coach_notifications_layout.setVisibility(View.GONE);
            }
        }
        daily_gym_start_layout.setOnClickListener(this);
        daily_gym_end_layout.setOnClickListener(this);
        relax_start_time_hour.setOnClickListener(this);
        relax_end_time_hour.setOnClickListener(this);

        coach_toggle_on.setOnClickListener(this);
        coach_toggle_off.setOnClickListener(this);

        relax_toggle_on.setOnClickListener(this);
        relax_toggle_off.setOnClickListener(this);

        alert_toolbar = (Toolbar) findViewById(R.id.alert_toolbar);
        alert_toolbar.setTitle("Reminders");

        setSupportActionBar(alert_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        final RangeSliderView.OnSlideListener listener = new RangeSliderView.OnSlideListener() {
            @Override
            public void onSlide(int index) {
                //    CommonUtils.showToast(SettingsLayout.this, "" + index);
                //    slider_position = index;
            }
        };

        setting_slider.setOnSlideListener(listener);


        range.add("2");
        range.add("3");
        range.add("4");
        range.add("5");
        range.add("6");
        range.add("7");
        range.add("8");


        try {
            ArrayAdapter adapter = new ArrayAdapter(SettingsLayout.this, R.layout.support_simple_spinner_dropdown_item, range);
            repeat_spinner.setAdapter(adapter);

            repeat_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    slider_position = position + 1;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void coachStartAllBroadCastListener() {
        ComponentName receiver;
        PackageManager pm;
        Log.d("AlarmForMindGymAudio","1");
        Intent intent = new Intent(this, AlarmForMindGymAudio.class);
        sendBroadcast(intent);
        Intent intent1 = new Intent(this, AlarmForMindGymAffirmations.class);
        sendBroadcast(intent1);

    }

    private void coachStopAllBroadCastListener() {
        ComponentName receiver;
        PackageManager pm;

        receiver = new ComponentName(this, AlarmForMindGymAudio.class);
        pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        receiver = new ComponentName(this, AlarmForMindGymAffirmations.class);
        pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);


    }

    private void relaxStartAllBroadCastListener() {
        ComponentName receiver;
        PackageManager pm;

        receiver = new ComponentName(this, AlarmForRelaxOne.class);
        pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

    }

    private void relaxStopAllBroadCastListener() {
        ComponentName receiver;
        PackageManager pm;

        receiver = new ComponentName(this, AlarmForRelaxOne.class);
        pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

    }


    @Override
    protected void onResume() {
        sharedPreferences = getSharedPreferences("HAPPY_BEING", MODE_PRIVATE);
        user_login = sharedPreferences.getString(AppConstants.IS_SIGNED_IN, "");

        dbHelper = new MySql(this, "mydb", null, MySql.version);
        db = dbHelper.getWritableDatabase();


        coach_settings_on_off = sharedPreferences.getBoolean(AppConstants.COACH_SETTINGS_TOGGLE, true);
        relax_settings_on_off = sharedPreferences.getBoolean(AppConstants.RELAX_SETTINGS_TOGGLE, true);

        if (coach_settings_on_off) {
            coach_toggle_on.setVisibility(View.VISIBLE);
            coach_mor_layout.setVisibility(View.VISIBLE);
            coach_eve_layout.setVisibility(View.VISIBLE);
            coach_toggle_off.setVisibility(View.GONE);
        } else {
            coach_toggle_on.setVisibility(View.GONE);
            coach_mor_layout.setVisibility(View.GONE);
            coach_eve_layout.setVisibility(View.GONE);
            coach_toggle_off.setVisibility(View.VISIBLE);
        }

        if (relax_settings_on_off) {
            relax_toggle_on.setVisibility(View.VISIBLE);
            relax_layout.setVisibility(View.VISIBLE);
            relax_toggle_off.setVisibility(View.GONE);
        } else {
            relax_toggle_on.setVisibility(View.GONE);
            relax_layout.setVisibility(View.GONE);
            relax_toggle_off.setVisibility(View.VISIBLE);
        }

        initTimeVariables();
        initSpinner();

        /*Setting Saved time*/


        updateTime(daily_gym_start_hour, daily_gym_start_time_min, daily_gym_start_format, final_start_hour, final_start_min, final_start_AM_Or_PM);
        updateTime(daily_gym_end_hour, daily_gym_end_min, daily_gym_end_format, final_end_hour, final_end_min, final_end_AM_OR_PM);

        updateTime(relax_start_time_hour, relax_final_start_hour, relax_final_start_min, relax_final_start_AM_Or_PM);
        updateTime(relax_end_time_hour, relax_final_end_hour, relax_final_end_min, relax_final_end_AM_OR_PM);


        relax_start_am_pm = relax_final_start_AM_Or_PM;
        relax_end_am_pm = relax_final_end_AM_OR_PM;

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        } else if (id == R.id.save_menu) {
            if (user_login.equalsIgnoreCase("true")) {
                editor = getSharedPreferences("HAPPY_BEING", MODE_PRIVATE).edit();
                editor.putBoolean(AppConstants.COACH_SETTINGS_TOGGLE, coach_settings_on_off);
                editor.apply();
                editor.commit();
                if (coach_settings_on_off) {
                    coachStartAllBroadCastListener();
                } else {
                    coachStopAllBroadCastListener();
                }


                editor.putBoolean(AppConstants.RELAX_SETTINGS_TOGGLE, relax_settings_on_off);
                editor.apply();
                editor.commit();

                if (relax_settings_on_off) {
                    relaxStartAllBroadCastListener();
                } else {
                    relaxStopAllBroadCastListener();
                }

                saveToPreferences();
            } else {
                AlertDialog.Builder builder1 = new
                        AlertDialog.Builder(SettingsLayout.this);
                builder1.setTitle("Happy Being");
                builder1.setMessage("Do you want sign in ?.");
                builder1.setCancelable(false);
                builder1.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder1.setNegativeButton(
                        "NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        })
                ;
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.coach_toggle_on) {
            coach_mor_layout.setVisibility(View.GONE);
            coach_eve_layout.setVisibility(View.GONE);
            coach_toggle_on.setVisibility(View.GONE);
            coach_toggle_off.setVisibility(View.VISIBLE);
            coach_settings_on_off = false;
        } else if (id == R.id.coach_toggle_off) {
            coach_mor_layout.setVisibility(View.VISIBLE);
            coach_eve_layout.setVisibility(View.VISIBLE);
            coach_toggle_on.setVisibility(View.VISIBLE);
            coach_toggle_off.setVisibility(View.GONE);
            coach_settings_on_off = true;
        } else if (id == R.id.relax_toggle_on) {
            relax_layout.setVisibility(View.VISIBLE);
            relax_toggle_on.setVisibility(View.GONE);
            relax_toggle_off.setVisibility(View.VISIBLE);
            relax_settings_on_off = false;
        } else if (id == R.id.relax_toggle_off) {
            relax_toggle_off.setVisibility(View.GONE);
            relax_layout.setVisibility(View.GONE);
            relax_toggle_on.setVisibility(View.VISIBLE);
            relax_settings_on_off = true;
        } else if (id == R.id.daily_gym_start_layout) {/*Daily My Coach Alerts Morning*/
            final TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            //  onTimeSet(view,Calendar.HOUR_OF_DAY,Calendar.MINUTE);

                            final_start_hour = hourOfDay;
                            final_start_min = minute;
                            dailyGymStartIsChanged = true;

                            Calendar datetime = Calendar.getInstance();
                            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            datetime.set(Calendar.MINUTE, minute);

                            if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                                am_pm = "AM";
                            else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                                am_pm = "PM";
                            String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime.get(Calendar.HOUR) + "";
                            String m = String.valueOf(minute);
                            //      CommonUtils.showToast(SettingsLayout.this, strHrsToShow + ":" + m + "" + am_pm);

                            String.valueOf(hourOfDay + ":" + minute);

                            updateTime(daily_gym_start_hour, daily_gym_start_time_min, daily_gym_start_format, hourOfDay, minute, am_pm);

                        }
                    }, mHour, mMinute, false);


            timePickerDialog.updateTime(hour, minutes);
            timePickerDialog.show();
        } else if (id == R.id.daily_gym_end_layout) {/*Daily My Coach Alerts Evening*/
            final TimePickerDialog timePickerDialog1 = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            final_end_hour = hourOfDay;
                            final_end_min = minute;
                            dailyGymEndIsChanged = true;

                            Calendar datetime = Calendar.getInstance();
                            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            datetime.set(Calendar.MINUTE, minute);

                            if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                                am_pm = "AM";
                            else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                                am_pm = "PM";
                            String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime.get(Calendar.HOUR) + "";
                            String m = String.valueOf(minute);
                            //                CommonUtils.showToast(SettingsLayout.this, strHrsToShow + ":" + m + "" + am_pm);

                            //      String.valueOf(hourOfDay + ":" + minute);

                            updateTime(daily_gym_end_hour, daily_gym_end_min, daily_gym_end_format, hourOfDay, minute, am_pm);


                        }
                    }, mHour, mMinute, false);
            timePickerDialog1.updateTime(hour, minutes);
            timePickerDialog1.show();
        } else if (id == R.id.relax_start_time_hour) {/*Relax and Relieve Alerts Morning*/
            final TimePickerDialog timePickerDialog2 = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            relax_final_start_hour = hourOfDay;
                            relax_final_start_min = minute;
                            relaxStartIsChaged = true;

                            Calendar datetime = Calendar.getInstance();
                            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            datetime.set(Calendar.MINUTE, minute);

                            if (datetime.get(Calendar.AM_PM) == Calendar.AM) {
                                am_pm = "AM";
                                relax_start_am_pm = 0;
                            } else if (datetime.get(Calendar.AM_PM) == Calendar.PM) {
                                am_pm = "PM";
                                relax_start_am_pm = 1;
                            }
                            strHrsToShowStart = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime.get(Calendar.HOUR) + "";
                            String m = String.valueOf(minute);
                            //            CommonUtils.showToast(SettingsLayout.this, strHrsToShow + ":" + m + "" + am_pm);

                            //    String.valueOf(hourOfDay + ":" + minute);

                            updateTime(relax_start_time_hour, hourOfDay, minute, am_pm);

                        }
                    }, mHour, mMinute, false);
            timePickerDialog2.updateTime(hour, minutes);
            timePickerDialog2.show();
        } else if (id == R.id.relax_end_time_hour) {/*Relax and Relieve Alerts Evening*/
            final TimePickerDialog timePickerDialog3 = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            relax_final_end_hour = hourOfDay;
                            relax_final_end_min = minute;
                            relaxEndIsChanged = true;

                            Calendar datetime = Calendar.getInstance();
                            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            datetime.set(Calendar.MINUTE, minute);

                            if (datetime.get(Calendar.AM_PM) == Calendar.AM) {
                                am_pm = "AM";
                                relax_end_am_pm = 0;
                            } else if (datetime.get(Calendar.AM_PM) == Calendar.PM) {
                                am_pm = "PM";
                                relax_end_am_pm = 1;
                            }
                            strHrsToShowEnd = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime.get(Calendar.HOUR) + "";
                            String m = String.valueOf(minute);
                            //                CommonUtils.showToast(SettingsLayout.this, strHrsToShow + ":" + m + "" + am_pm);

                            //     String.valueOf(hourOfDay + ":" + minute);

                            updateTime(relax_end_time_hour, hourOfDay, minute, am_pm);

                        }
                    }, mHour, mMinute, false);
            timePickerDialog3.updateTime(hour, minutes);
            timePickerDialog3.show();
        }
    }

    /*Saving all the data to shared preference if the user is not logged in showing dialog box */
    private void saveToPreferences() {

        try {
            ContentValues cv = new ContentValues();

            /// coach start
            if (dailyGymStartIsChanged) {
                Calendar calender = new GregorianCalendar(final_start_year, final_start_month, final_start_dayOfMonth, final_start_hour, final_start_min);
                dailyGymStartTimeLong = calender.getTimeInMillis();
                cv.put("MIND_GYM_START_TIME", calender.getTimeInMillis());

/*
                CommonUtils.showLogInforamtion(getClass().getSimpleName(), "MIND_GYM_START_TIME in save preference", calender.getTimeInMillis(), true);
                CommonUtils.showLogInforamtion(getClass().getSimpleName(), "MIND_GYM_START_TIME in save preference ", df.format(calender.getTime()), true);
*/

            }
            if (dailyGymEndIsChanged) {
                Calendar calender1 = new GregorianCalendar(final_end_year, final_end_month, final_end_dayOfMonth, final_end_hour, final_end_min);
                dailyGymEndTimeLong = calender1.getTimeInMillis();
                cv.put("MIND_GYM_END_TIME", calender1.getTimeInMillis());

                calender1.getTimeInMillis();
                calender1.add(Calendar.HOUR, -2);
                cv.put("HAPPY_MOMENT_TIME", calender1.getTimeInMillis());

/*
                CommonUtils.showLogInforamtion(getClass().getSimpleName(), "MIND_GYM_END_TIME", calender1.getTimeInMillis(), true);
                CommonUtils.showLogInforamtion(getClass().getSimpleName(), "MIND_GYM_END_TIME", df.format(calender1.getTime()), true);
*/
            }
            /// coach end


            /// relax start
            if (relaxStartIsChaged) {
                Calendar calender2 = new GregorianCalendar(relax_final_start_year, relax_final_start_month, relax_final_start_dayOfMonth, relax_final_start_hour, relax_final_start_min);
                relaxStartTimeLong = calender2.getTimeInMillis();
                cv.put("RELAX_START_TIME", calender2.getTimeInMillis());

/*
                CommonUtils.showLogInforamtion(getClass().getSimpleName(), "RELAX_START_TIME", calender2.getTimeInMillis(), true);
                CommonUtils.showLogInforamtion(getClass().getSimpleName(), "RELAX_START_TIME", df.format(calender2.getTime()), true);
*/
            }
            if (relaxEndIsChanged) {
                Calendar calender3 = new GregorianCalendar(relax_final_end_year, relax_final_end_month, relax_final_end_dayOfMonth, relax_final_end_hour, relax_final_end_min);
                relaxEndTimeLong = calender3.getTimeInMillis();
                cv.put("RELAX_END_TIME", calender3.getTimeInMillis());

/*
                CommonUtils.showLogInforamtion(getClass().getSimpleName(), "RELAX_END_TIME", calender3.getTimeInMillis(), true);
                CommonUtils.showLogInforamtion(getClass().getSimpleName(), "RELAX_END_TIME", df.format(calender3.getTime()), true);
*/
            }

            /// relax end

            if (dailyGymStartIsChanged || dailyGymEndIsChanged || relaxStartIsChaged || relaxEndIsChanged) {
                cv.put("sync_flag", "0");
                int count = db.update("NotificationsTimings", cv, "_id=" + id, null);   // Update setting in sqlite database
                Log.i("SettingsLayout", "Update the table "+count);
            }

            /// relax end


            /// relax start
            if (relax_layout.getVisibility() == View.VISIBLE) {
                List<Long> startTimes = new ArrayList<>();
                long randomValue = 0;
                SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss a");

                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR, relax_final_start_hour);
                if (relaxStartIsChaged) {
                    c.set(Calendar.HOUR, Integer.parseInt(strHrsToShowStart));
                }

                c.set(Calendar.MINUTE, relax_final_start_min);
                c.set(Calendar.AM_PM, relax_start_am_pm);

                String Start = df.format(c.getTime());
                long StartM = c.getTimeInMillis();


                Calendar ce = Calendar.getInstance();
                ce.set(Calendar.HOUR, relax_final_end_hour);

                if (relaxEndIsChanged) {
                    ce.set(Calendar.HOUR, Integer.parseInt(strHrsToShowEnd));
                }
                ce.set(Calendar.MINUTE, relax_final_end_min);
                ce.set(Calendar.AM_PM, relax_end_am_pm);

                String End = df.format(ce.getTime());
                long EndM = ce.getTimeInMillis();

                Random random = new Random();
                long newValue = 0;
                long interval = (EndM - StartM) / slider_position;

                db.execSQL("DELETE FROM RelaxAlarmNotification");

                for (int i = 0; i < slider_position; i++) {

                    newValue = (long) (random.nextDouble() * (interval * (i + 1)));
                    randomValue = StartM + newValue;
                    Log.d("rrrrrraaaa", randomValue + "");
                    startTimes.add(randomValue);
                    Date myDate = new Date(randomValue);
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss a", Locale.ENGLISH);
                    String myTime = formatter.format(myDate);
                    Log.d("rrrrrraaaammmmmmm", myTime + "");


                    dbHelper = new MySql(SettingsLayout.this, "mydb", null, MySql.version);
                    db = dbHelper.getWritableDatabase();
                    ContentValues relaxCV = new ContentValues();
                    relaxCV.put("alarm_range", slider_position);
                    relaxCV.put("alarm_time", randomValue);
                    db.insert("RelaxAlarmNotification", null, relaxCV);
                }
            }
            //// relax end


            /// coach alarm start
            if (coach_mor_layout.getVisibility() == View.VISIBLE && coach_eve_layout.getVisibility() == View.VISIBLE) {
                scheduleAlarms();
            } else if (coach_mor_layout.getVisibility() == View.GONE && coach_eve_layout.getVisibility() == View.GONE && relax_layout.getVisibility() == View.VISIBLE) {
                setRelaxAlarm();
            }

            if (coach_mor_layout.getVisibility() == View.GONE && coach_eve_layout.getVisibility() == View.GONE && relax_layout.getVisibility() == View.GONE) {
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }


        } catch (Exception e) {
            e.printStackTrace();
            finish();
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }


    }

    private void initSpinner() {

        Cursor cursor = db.rawQuery("SELECT * FROM RelaxAlarmNotification", null);
        int alarm_range = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            alarm_range = cursor.getInt(cursor.getColumnIndexOrThrow("alarm_range"));
        }
        setting_slider.setInitialIndex(alarm_range);
        slider_position = alarm_range - 1;
        repeat_spinner.setSelection(slider_position);
        cursor.close();
    }

    public void initTimeVariables() {

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM NotificationsTimings", null);
            long dailyGymTime = 0, dailyGymEndTime = 0, relaxStartTime = 0, relaxEndTime = 0;

            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                dailyGymTime = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("MIND_GYM_START_TIME")));
                dailyGymEndTime = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("MIND_GYM_END_TIME")));
                dailyGymStartTimeLong = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("MIND_GYM_START_TIME")));
                dailyGymEndTimeLong = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("MIND_GYM_END_TIME")));
                relaxStartTime = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("RELAX_START_TIME")));
                relaxEndTime = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("RELAX_END_TIME")));
            }


            //Log.i("SettingsAlarm", "INIT:::::::: daiily gym time "+dailyGymTime);
            Log.i("SettingsAlarm", "INIT:::::::: daiily gym end time "+dailyGymTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTimeInMillis(dailyGymTime);
            final_start_hour = calendar1.get(Calendar.HOUR);
            final_start_min = calendar1.get(Calendar.MINUTE);
            final_start_month = calendar1.get(Calendar.MONTH);
            calendar1.add(Calendar.DAY_OF_MONTH, -1);
            final_start_dayOfMonth = calendar1.get(Calendar.DAY_OF_MONTH);
            final_start_year = calendar1.get(Calendar.YEAR);
            final_start_AM_Or_PM = calendar1.get(Calendar.AM_PM);

            calendar1.setTimeInMillis(dailyGymEndTime);
            final_end_hour = calendar1.get(Calendar.HOUR);
            final_end_min = calendar1.get(Calendar.MINUTE);
            final_end_month = calendar1.get(Calendar.MONTH);
            calendar1.add(Calendar.DAY_OF_MONTH, -1);
            final_end_dayOfMonth = calendar1.get(Calendar.DAY_OF_MONTH);
            final_end_year = calendar1.get(Calendar.YEAR);
            final_end_AM_OR_PM = calendar1.get(Calendar.AM_PM);

            calendar1.setTimeInMillis(relaxStartTime);
            relax_final_start_hour = calendar1.get(Calendar.HOUR);
            relax_final_start_min = calendar1.get(Calendar.MINUTE);
            relax_final_start_month = calendar1.get(Calendar.MONTH);
            calendar1.add(Calendar.DAY_OF_MONTH, -1);
            relax_final_start_dayOfMonth = calendar1.get(Calendar.DAY_OF_MONTH);
            relax_final_start_year = calendar1.get(Calendar.YEAR);
            relax_final_start_AM_Or_PM = calendar1.get(Calendar.AM_PM);

            calendar1.setTimeInMillis(relaxEndTime);
            relax_final_end_hour = calendar1.get(Calendar.HOUR);
            relax_final_end_min = calendar1.get(Calendar.MINUTE);
            relax_final_end_month = calendar1.get(Calendar.MONTH);
            calendar1.add(Calendar.DAY_OF_MONTH, -1);
            relax_final_end_dayOfMonth = calendar1.get(Calendar.DAY_OF_MONTH);
            relax_final_end_year = calendar1.get(Calendar.YEAR);
            relax_final_end_AM_OR_PM = calendar1.get(Calendar.AM_PM);

            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTime(TextView hour, TextView min, TextView format, int hours, int mins, int amOrPm) {
        // Log.i("MySettingActivity", "Hours is "+hours+" Min is "+mins+" am or pm is "+amOrPm);
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";

        if (amOrPm != -1) {
            if (amOrPm == 0) {
                timeSet = "AM";
            } else {
                timeSet = "PM";
            }
        }

        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        String aTime = new
                StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        hour.setText(String.valueOf(hours));
        min.setText(minutes);
        format.setText(timeSet);
    }

    public void updateTime(TextView hour, TextView min, TextView format, int hours, int mins, String amOrPm) {
        // Log.i("MySettingActivity", "Hours is "+hours+" Min is "+mins+" am or pm is "+amOrPm);
        String timeSet = amOrPm;

        if (hours > 12) {
            hours -= 12;

        } else if (hours == 0) {
            hours += 12;

        } else if (hours == 12)
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        String aTime = new
                StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        hour.setText(String.valueOf(hours));
        min.setText(minutes);
        format.setText(timeSet);


    }


    public void updateTime(TextView hour, int hours, int mins, int amOrPm) {

        Log.i("AlarmForMindGymAudio", "8");

        // Log.i("MySettingActivity", "Hours is "+hours+" Min is "+mins+" am or pm is "+amOrPm);
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";

        if (amOrPm != -1) {
            if (amOrPm == 0) {
                timeSet = "AM";
            } else {
                timeSet = "PM";
            }
        }

        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        String aTime = new
                StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        hour.setText(aTime);


    }

    public void updateTime(TextView hour, int hours, int mins, String amOrPm) {
        Log.i("AlarmForMindGymAudio", "7");

        // Log.i("MySettingActivity", "Hours is "+hours+" Min is "+mins+" am or pm is "+amOrPm);
        String timeSet = amOrPm;

        if (hours > 12) {
            hours -= 12;

        } else if (hours == 0) {
            hours += 12;

        } else if (hours == 12)
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        String aTime = new
                StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        hour.setText(aTime);


    }

    private void scheduleAlarms() {
        Log.i("AlarmForMindGymAudio", "6");

        Calendar scheduleCalender = Calendar.getInstance();
        ContentValues contentValues = new ContentValues();
        boolean canUpdate = false;
        Date currentTime = new Date();
        long currentMillisecond;
        scheduleCalender.setTime(currentTime);
        currentMillisecond = scheduleCalender.getTimeInMillis();

        Date currentDate, reminderDate;


        Cursor cursor = db.rawQuery("SELECT * FROM NotificationsTimings", null);
        long dailyGymTime = 0, dailyGymEndTime = 0, happyMomentTime = 0, relaxStartTime = 0, relaxEndTime = 0;

        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            id = cursor.getInt(cursor.getColumnIndex("_id"));

            dailyGymTime = Long.parseLong(cursor.getString(cursor.getColumnIndex("MIND_GYM_START_TIME")));
/*
            CommonUtils.showLogInforamtion(getClass().getSimpleName(), "MIND_GYM_START_TIME DATABASE", dailyGymTime, true);
            CommonUtils.showLogInforamtion(getClass().getSimpleName(), "MIND_GYM_START_TIME DATABASE", df.format(dailyGymTime), true);

*/

            dailyGymEndTime = Long.parseLong(cursor.getString(cursor.getColumnIndex("MIND_GYM_END_TIME")));
/*
            CommonUtils.showLogInforamtion(getClass().getSimpleName(), "MIND_GYM_END_TIME DATABASE", dailyGymEndTime, true);
            CommonUtils.showLogInforamtion(getClass().getSimpleName(), "MIND_GYM_END_TIME DATABASE", df.format(dailyGymEndTime), true);

*/
            /*HAPPY_MOMENT_TIME*/
            happyMomentTime = Long.parseLong(cursor.getString(cursor.getColumnIndex("HAPPY_MOMENT_TIME")));
/*
            CommonUtils.showLogInforamtion(getClass().getSimpleName(), "HAPPY_MOMENT_TIME DATABASE", happyMomentTime, true);
            CommonUtils.showLogInforamtion(getClass().getSimpleName(), "HAPPY_MOMENT_TIME DATABASE", df.format(happyMomentTime), true);
*/
            /*HAPPY_MOMENT_TIME*/

            relaxStartTime = Long.parseLong(cursor.getString(cursor.getColumnIndex("RELAX_START_TIME")));
/*
            CommonUtils.showLogInforamtion(getClass().getSimpleName(), "RELAX_START_TIME DATABASE", relaxStartTime, true);
            CommonUtils.showLogInforamtion(getClass().getSimpleName(), "RELAX_START_TIME DATABASE", df.format(relaxStartTime), true);
*/


            relaxEndTime = Long.parseLong(cursor.getString(cursor.getColumnIndex("RELAX_END_TIME")));
/*
            CommonUtils.showLogInforamtion(getClass().getSimpleName(), "RELAX_END_TIME DATABASE", df.format(relaxEndTime), true);
            CommonUtils.showLogInforamtion(getClass().getSimpleName(), "RELAX_END_TIME DATABASE", relaxEndTime, true);
*/
        }

        AlarmForMindGymAffirmations.cancelAlarm(this);
        AlarmForMindGymAudio.cancelAlarm(this);
        AlarmForHappyMoment.cancelAlarm(this);

        // Relax Morning Notification
        scheduleCalender = Calendar.getInstance();
        scheduleCalender.setTimeInMillis(dailyGymTime);
        reminderDate = scheduleCalender.getTime();

        scheduleCalender.setTimeInMillis(currentMillisecond);
        currentDate = scheduleCalender.getTime();

        if(reminderDate.after(currentDate)) {
            AlarmForMindGymAudio alarmForMindGymAudio = new AlarmForMindGymAudio();
            alarmForMindGymAudio.setAlarm(this, dailyGymTime);
        }
        else {
            scheduleCalender.setTimeInMillis(dailyGymTime);
            scheduleCalender.add(Calendar.DATE, 1);
            AlarmForMindGymAudio alarmForMindGymAudio = new AlarmForMindGymAudio();
            alarmForMindGymAudio.setAlarm(this, scheduleCalender.getTimeInMillis());
            contentValues.put("MIND_GYM_START_TIME", scheduleCalender.getTimeInMillis());
            canUpdate = true;
        }

        // Gratitude Evening Notification
        scheduleCalender = Calendar.getInstance();
        scheduleCalender.setTimeInMillis(dailyGymEndTime);
        reminderDate = scheduleCalender.getTime();

        scheduleCalender.setTimeInMillis(currentMillisecond);
        currentDate = scheduleCalender.getTime();

        if(reminderDate.after(currentDate)) {
            AlarmForMindGymAffirmations alarmForMindGymAffirmations = new AlarmForMindGymAffirmations();
            alarmForMindGymAffirmations.setAlarm(this, dailyGymEndTime);
        } else {
            scheduleCalender.setTimeInMillis(dailyGymEndTime);
            scheduleCalender.add(Calendar.DATE, 1);
            AlarmForMindGymAffirmations alarmForMindGymAffirmations = new AlarmForMindGymAffirmations();
            alarmForMindGymAffirmations.setAlarm(this, scheduleCalender.getTimeInMillis());
            contentValues.put("MIND_GYM_END_TIME", scheduleCalender.getTimeInMillis());
            canUpdate = true;
        }

        //Happy moment view
        scheduleCalender = Calendar.getInstance();
        scheduleCalender.setTimeInMillis(happyMomentTime);
        reminderDate = scheduleCalender.getTime();

        scheduleCalender.setTimeInMillis(currentMillisecond);
        currentDate = scheduleCalender.getTime();


        if(reminderDate.after(currentDate)) {
            AlarmForHappyMoment alarmForHappyMoment = new AlarmForHappyMoment();
            alarmForHappyMoment.setAlarm(this, happyMomentTime);
        }
        else {
            AlarmForHappyMoment alarmForHappyMoment = new AlarmForHappyMoment();
            scheduleCalender.setTimeInMillis(happyMomentTime);
            scheduleCalender.add(Calendar.DATE, 1);
            alarmForHappyMoment.setAlarm(this, scheduleCalender.getTimeInMillis());
            contentValues.put("HAPPY_MOMENT_TIME", scheduleCalender.getTimeInMillis());
            canUpdate = true;
        }

        if (canUpdate) {
            contentValues.put("sync_flag", "0");
            long test = db.update("NotificationsTimings", contentValues, "_id=" + id, null);
        }

        if (relax_layout.getVisibility() == View.VISIBLE) {
            setRelaxAlarm();
        } else {
            finish();
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
        CommonUtils.CloseCursor(cursor);

    }


    public void setRelaxAlarm() {
        /*Setting relax alarm*/
        try {
            long relax_time, current_date_milliseconds;
            Date today;
            Calendar calendar;
            Calendar gregorianCalendar;
            boolean alarmSetOrNot = false;
            int _id, range;

            ContentValues relaxCV = new ContentValues();
            dbHelper = new MySql(SettingsLayout.this, "mydb", null, MySql.version);
            db = dbHelper.getWritableDatabase();
            Cursor relax_cursor = db.rawQuery("SELECT * FROM RelaxAlarmNotification", null);

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
                        AlarmForRelaxOne.cancelAlarm(SettingsLayout.this);
                        alarmEnd.setAlarm(SettingsLayout.this, relax_time);
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
                    setRelaxAlarm();
                }

            }
            CommonUtils.CloseCursor(relax_cursor);
            finish();
            overridePendingTransition(R.anim.right_in, R.anim.left_out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}