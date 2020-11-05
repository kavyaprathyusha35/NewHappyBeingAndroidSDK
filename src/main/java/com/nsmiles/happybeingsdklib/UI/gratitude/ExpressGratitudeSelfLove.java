package com.nsmiles.happybeingsdklib.UI.gratitude;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.ServerApiConnectors.API_Response_Listener;
import com.nsmiles.happybeingsdklib.ServerApiConnectors.ApiProvider;
import com.nsmiles.happybeingsdklib.UI.HomeScreenActivity;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class ExpressGratitudeSelfLove extends AppCompatActivity implements View.OnClickListener {

    Activity activity;
    CommonUtils commonUtils;
    String START_DATE = "", END_DATE = "", user_email, user_id, diary_date_time;
    int _id;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z", Locale.ENGLISH);
    Toolbar express_love_toolbar;
    EditText express_your_gratitude;
    Button save_button;
    Intent intent;
    boolean from_diary = false;
    long current_date_milliseconds;
    Calendar calendar;
    Date today;
    private SQLiteDatabase db;
    MySql dbHelper;
    TextView hint_text_all;
    LinearLayout adViewLayer;
    boolean user_paid = false;
    ImageView gratitude_img;
    int move_position=0;
    private int gratitude_date = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_gratitude_self_love);
        activity = ExpressGratitudeSelfLove.this;
        START_DATE = df.format(Calendar.getInstance().getTime());
        intent = getIntent();
        init();
        setNameAndText();
    }

    private void init() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        express_love_toolbar = (Toolbar) findViewById(R.id.express_love_toolbar);
        express_your_gratitude = (EditText) findViewById(R.id.express_your_gratitude);
        save_button = (Button) findViewById(R.id.save_button);
        save_button.setOnClickListener(this);
        hint_text_all = (TextView) findViewById(R.id.hint_text_all);
        gratitude_img = (ImageView) findViewById(R.id.gratitude_img);
        adViewLayer = (LinearLayout) findViewById(R.id.adViewLayer);
        express_love_toolbar.setTitle("Self love");
        setSupportActionBar(express_love_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        express_your_gratitude.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.express_your_gratitude) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(ExpressGratitudeSelfLove.this ,R.color.hb_circle_black_light));
        }
    }


    @Override
    protected void onResume() {
        try {
            commonUtils = new CommonUtils();
            user_email = commonUtils.getUserEmail(activity);
            adViewLayer.setVisibility(View.GONE);
            if (intent.hasExtra("SELF") && intent.hasExtra("ID")) {
                express_your_gratitude.setText(intent.getStringExtra("SELF"));
                _id = intent.getIntExtra("ID",0);
                diary_date_time = intent.getStringExtra("DATE_TIME");
                from_diary = true;
                save_button.setText("UPDATE");
                move_position = intent.getIntExtra(AppConstants.MOVE_POSITION,0);
            }
            if (intent.hasExtra("GRATITUDE_DATE")) {
                gratitude_date = intent.getIntExtra("GRATITUDE_DATE", 0);
            }
            today = new Date();
            calendar = Calendar.getInstance();
            calendar.setTime(today);
            current_date_milliseconds = calendar.getTimeInMillis();

            dbHelper = new MySql(this, "mydb", null, MySql.version);
            db = dbHelper.getWritableDatabase();

            CommonUtils.assignProfilePic(activity, "SELF", "", "", gratitude_img);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onResume();
    }

    @Override
    public void onBackPressed() {
        goToGratitudeJournalListFragment();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            goToGratitudeJournalListFragment();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void goToGratitudeJournalListFragment() {

        if (intent.hasExtra("NAME")) {
            String name = intent.getStringExtra("NAME");

            if (name.equals(AppConstants.ABUDANCE_JOURNAL)) {
               finish();
            } else if (name.equals(AppConstants.MANIFEST_JOURNAL)) {
                finish();
            }else if (name.equals(AppConstants.TO_SELF)){

                Intent i=new Intent(this, HomeScreenActivity.class);
                i.putExtra("key","value");
                this.startActivity(i);
                finish();
            }else if (name.equals(AppConstants.TO_OTHERS)){
                finish();
            }

        }


    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.save_button) {
            if (from_diary) {
                if (checkValidation()) {
                    CommonUtils commonUtils = new CommonUtils();
                    commonUtils.setGratitudeData(activity, gratitude_date, true);

                    if (CommonUtils.isNetworkAvailable(this)) {
                        updateExpressGratitude();
                    } else {
                        updateSelfLoveLocally();
                    }

                }
            } else {
                if (checkValidation()) {
                    if (CommonUtils.isNetworkAvailable(this)) {
                        saveExpressGratitude();
                    } else {
                        saveExpressLocally("0");
                    }
                }
            }
        }
    }

    private void updateSelfLoveLocally() {

        try {
            ContentValues cv = new ContentValues();

            @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM New_Gratitude_Table where email_id=? AND syncFlag=? AND date_time=?",
                    new String[]{user_email, "0", diary_date_time}, null);

            if (cursor.getCount() > 0) {
                cv.put("syncFlag", "0");
            } else {
                cv.put("syncFlag", "3");
            }
        //    cv.put("date_time", new Date().toString());
            cv.put("description", express_your_gratitude.getText().toString());
            cv.put("express_your_gratitude", express_your_gratitude.getText().toString());
            db.update("New_Gratitude_Table", cv, "date_time=?",
                    new String[]{diary_date_time});
            activityCall();
            activity.startActivity(new Intent(activity, HomeScreenActivity.class)
                    .putExtra("FROM_SCREEN", "JOURNAL").putExtra(AppConstants.MOVE_POSITION, move_position));
            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            activity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setNameAndText() {
        if (intent.hasExtra("NAME")) {
            String name = intent.getStringExtra("NAME");
            if (name.equals(AppConstants.ABUDANCE_JOURNAL)) {
                hint_text_all.setText(R.string.abudance_hint_text);
                express_love_toolbar.setTitle(AppConstants.ABUDANCE_JOURNAL);
            } else if (name.equals(AppConstants.MANIFEST_JOURNAL)) {
                hint_text_all.setText(R.string.manifest_hint_text);
                express_love_toolbar.setTitle(AppConstants.MANIFEST_JOURNAL);

            }
        }

    }


    private void saveExpressGratitude() {

        try {
            if (CommonUtils.isNetworkAvailable(activity)) {
                Log.i("Express","In save express to server");
                SelfLoveData selfLoveData = new SelfLoveData();
                selfLoveData.setUser_id(user_id); // "59252e6c12354bed8e9b750e");
                selfLoveData.setDate_time(new Date().toString());
                selfLoveData.setType_of_gratitude("SELF_LOVE");
                selfLoveData.setDescription(express_your_gratitude.getText().toString());
                selfLoveData.setLink(user_email);
                selfLoveData.setPic("");
                selfLoveData.setTitle(express_love_toolbar.getTitle().toString());
                selfLoveData.setEmail(user_email);
                selfLoveData.setSubject("");

                // CALL API to save data
                new ApiProvider.SaveSelfLove(selfLoveData, commonUtils.getTokenId(activity), 2, this, "Saving...", new API_Response_Listener<String>() {

                    @Override
                    public void onComplete(String data, long request_code, String failure_code) {
                        Log.e("failure_code", failure_code);
                        if (data == null) {
                            saveExpressLocally("0");
                        } else {
                            saveExpressLocally("1");
                        }
                    }
                }).call();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        activityCall();
//        setResult(RESULT_OK);
//        finish();
    }

    private void saveExpressLocally(String sync_flag) {


        Log.i("Gratitude", "In save express locally"+sync_flag);
        MySql dbHelper = new MySql(this, "mydb", null, MySql.version);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_id", user_id);
        cv.put("date_time", new Date().toString());
        cv.put("type_of_gratitude", "SELF_LOVE");
        cv.put("description", express_your_gratitude.getText().toString());
        cv.put("link", user_email);
        cv.put("pic", "");
        cv.put("title", express_love_toolbar.getTitle().toString());
        cv.put("email_id", user_email);
        cv.put("express_your_gratitude", express_your_gratitude.getText().toString());
        cv.put("subject", "");
        cv.put("createdAt", "");
        cv.put("updatedAt", "");
        cv.put("id", current_date_milliseconds);
        cv.put("syncFlag", sync_flag);

        long insert = db.insert("New_Gratitude_Table", null, cv);
        if (insert > 0) {
            activityCall();
            activity.startActivity(new Intent(activity, HomeScreenActivity.class)
                    .putExtra("FROM_SCREEN", "JOURNAL"));
            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            activity.finish();
        }
        db.close();
    }


    private void activityCall() {
        // Save Locally
        END_DATE = df.format(Calendar.getInstance().getTime());
        MySql dbHelper = new MySql(activity, "mydb", null, MySql.version);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_id", "");
        cv.put("date_time", START_DATE);
        cv.put("feature_name", "SELF_LOVE");
        cv.put("activity_name", "SELF_LOVE");
        cv.put("activity_detail", "SELF_LOVE");
        cv.put("start_date_time", START_DATE);
        cv.put("end_date_time", END_DATE);
        cv.put("sync_flag", "0");
        db.insert("New_Activity_Table", null, cv);
        db.close();
    }

    private boolean checkValidation() {

        boolean returnValue = true;

        if (!CommonUtils.hasText(express_your_gratitude, "Please fill the field"))
            returnValue = false;

        return returnValue;
    }


    private void updateExpressGratitude() {

        try {
            if (CommonUtils.isNetworkAvailable(activity)) {
                Log.i("Express","In save express to server");
                SelfLoveData selfLoveData = new SelfLoveData();
                selfLoveData.setUser_id(user_id); // "59252e6c12354bed8e9b750e");
                selfLoveData.setDate_time(new Date().toString());
                selfLoveData.setType_of_gratitude("SELF_LOVE");
                selfLoveData.setDescription(express_your_gratitude.getText().toString());
                selfLoveData.setLink(user_email);
                selfLoveData.setPic("");
                selfLoveData.setTitle(express_love_toolbar.getTitle().toString());
                selfLoveData.setEmail(user_email);
                selfLoveData.setSubject("");

                // CALL API to save data
                new ApiProvider.UpdateSelfLove(selfLoveData, commonUtils.getTokenId(activity), 2, this, "Updating...", new API_Response_Listener<String>() {

                    @Override
                    public void onComplete(String data, long request_code, String failure_code) {
                        Log.e("failure_code", failure_code);
                        if (data == null) {
                            Log.e("data", "null");
                        } else {
                            updateSelfLoveLocally();
                        }
                    }
                }).call();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        activityCall();
//        setResult(RESULT_OK,new Intent().putExtra(AppConstants.MOVE_POSITION, move_position));
//        finish();
    }
}
