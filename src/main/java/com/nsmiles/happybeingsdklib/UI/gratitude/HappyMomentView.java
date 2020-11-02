package com.nsmiles.happybeingsdklib.UI.gratitude;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.UI.HomeScreenActivity;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

/**
 * Created by Gopal on 27/05/2017
 */

public class HappyMomentView extends AppCompatActivity implements View.OnClickListener {

    private Button btnSave;
    private long dbId;
    private ImageView upoadImageView;
    private EditText feelingText;
    private EditText titleText;
    private Context mContext;
    private RelativeLayout mRelativeLayout;
    private PopupWindow mPopupWindow;
    Toolbar happy_moment_toolbar;
    SharedPreferences prefs;
    String login_status = "", user_email = "", user_id = "", diary_date_time;
    int audio_id = 0;
    String _id;
    String picturePath = "Image not supported";
    boolean user_paid = false;
    private LinearLayout adViewLayer;
    TextView happy_guide_me;
    private SQLiteDatabase db;
    MySql dbHelper;
    CommonUtils commonUtils;
    String START_DATE = "", END_DATE = "";
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z", Locale.ENGLISH);
    Intent intent;
    boolean from_diary = false;
    long current_date_milliseconds;
    Calendar calendar;
    Date today;
    byte[] pic_byte;
    int move_position=0;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_gratitude_happy_moment_layout);
        intent = getIntent();


        if (intent.hasExtra("dbId")) {
            dbId = intent.getLongExtra("dbId", 0);
        }
        commonUtils = new CommonUtils();
        dbHelper = new MySql(this, "mydb", null, MySql.version);
        db = dbHelper.getWritableDatabase();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        titleText = (EditText) findViewById(R.id.title);

        upoadImageView = (ImageView) findViewById(R.id.upoadImageview);
        happy_guide_me = (TextView) findViewById(R.id.happy_guide_me);
        feelingText = (EditText) findViewById(R.id.feeling);


        btnSave = (Button) findViewById(R.id.save_button);
        btnSave.setOnClickListener(this);
        happy_guide_me.setOnClickListener(this);
        START_DATE = df.format(Calendar.getInstance().getTime());
        mContext = getApplicationContext();
        happy_moment_toolbar = (Toolbar) findViewById(R.id.happy_moment_toolbar);
        happy_moment_toolbar.setTitle("Happy moment");
        setSupportActionBar(happy_moment_toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl_new_gratitude_happy_moment_layout);
        prefs = getSharedPreferences("HAPPY_BEING", MODE_PRIVATE);
        adViewLayer = (LinearLayout) findViewById(R.id.adViewLayer);
        adViewLayer.setVisibility(View.GONE);
        login_status = prefs.getString("user_login", "");
        user_email = prefs.getString("user_email", "");
        user_id = prefs.getString("user_id", "");

        feelingText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.feeling) {
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

        titleText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.title) {
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
            getWindow().setStatusBarColor(ContextCompat.getColor(HappyMomentView.this, R.color.hb_circle_black_light));
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        if (intent.hasExtra("IMAGE_PATH")) {
            String path = intent.getStringExtra("IMAGE_PATH");
            Bitmap bm = CommonUtils.decodeSampledBitmapFromUri(path, width, 400);
            picturePath = path;
            try {
                bm = CommonUtils.modifyOrientation(bm, path);
                String convertedBase64String = convertBitmapToBase64(bm);
                picturePath = convertedBase64String;
                upoadImageView.setImageBitmap(convertBase64toBitmap(convertedBase64String));


            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        if (intent.hasExtra("FEELINGS") && intent.hasExtra("MOMENT") && intent.hasExtra("ID")) {

            feelingText.setText(intent.getStringExtra("FEELINGS"));
            titleText.setText(intent.getStringExtra("MOMENT"));
            _id = intent.getStringExtra("ID");
            audio_id = intent.getIntExtra("AUDIO_ID", 0);
            diary_date_time = intent.getStringExtra("DATE_TIME");
            from_diary = true;
            move_position = intent.getIntExtra(AppConstants.MOVE_POSITION,0);
            btnSave.setText("UPDATE");
            try {
                if (audio_id > 0) {
                    Cursor diary_cursor = db.rawQuery("SELECT * FROM New_Gratitude_Table where _id=?",
                            new String[]{String.valueOf(audio_id)}, null);
                    if (diary_cursor.getCount() > 0) {
                        diary_cursor.moveToFirst();
                        pic_byte = diary_cursor.getBlob(diary_cursor.getColumnIndexOrThrow("pic"));
                        if (pic_byte != null) {
                            String base = new String(pic_byte);
                            picturePath = base;
                            CommonUtils.convertBase64toBitmap(HappyMomentView.this, base, upoadImageView);
                        }
                    }
                    CommonUtils.CloseCursor(diary_cursor);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public Bitmap convertBase64toBitmap(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",") + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }


    @Override
    protected void onResume() {
        today = new Date();
        calendar = Calendar.getInstance();
        calendar.setTime(today);
        current_date_milliseconds = calendar.getTimeInMillis();
        super.onResume();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Add Remaining Methods
    // Save
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.save_button) {
            if (from_diary) {
                if (checkValidation()) {
                    updateHappyMoment();
                }
            } else {
                if (checkValidation()) {
                    saveHappyMomentLocally();

                }
            }
        }

        if (id == R.id.happy_guide_me) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View customView = inflater.inflate(R.layout.signin_signup_hint_layout, null);
            mPopupWindow = new PopupWindow(
                    customView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            if (Build.VERSION.SDK_INT >= 21) {
                mPopupWindow.setElevation(5.0f);
            }
            Button closeButton = (Button) customView.findViewById(R.id.ib_close);
            TextView tvHintText = (TextView) customView.findViewById(R.id.tvHintText);
            tvHintText.setText(getResources().getString(R.string.happy_title));
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopupWindow.dismiss();
                }
            });

            mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, 0);
        }

    }

    private void updateHappyMoment() {

        try {
            ContentValues cv = new ContentValues();

            Cursor cursor = db.rawQuery("SELECT * FROM New_Gratitude_Table where email_id=? AND syncFlag=? AND date_time=?",
                    new String[]{user_email, "0", diary_date_time}, null);
            if (cursor.getCount() > 0) {
                cv.put("syncFlag", "0");
            } else {
                cv.put("syncFlag", "3");
            }
            //    cv.put("date_time", new Date().toString());
            cv.put("description", titleText.getText().toString());
            cv.put("pic", picturePath);
            cv.put("title", feelingText.getText().toString());
            db.update("New_Gratitude_Table", cv, "date_time=?", new String[]{diary_date_time});
            startActivity(new Intent(HappyMomentView.this, HomeScreenActivity.class)
                    .putExtra("FROM_SCREEN", "JOURNAL"));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveHappyMomentLocally() {

        MySql dbHelper = new MySql(this, "mydb", null, MySql.version);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("user_id", user_id);
        cv.put("date_time", new Date().toString());
        cv.put("type_of_gratitude", "HAPPY_MOMENT");
        cv.put("description", titleText.getText().toString());
        cv.put("link", "TEST_LINK_ANDROID");
        cv.put("pic", picturePath);
        cv.put("title", feelingText.getText().toString());
        cv.put("email_id", user_email);
        cv.put("express_your_gratitude", "HAPPY_MOMENT");
        cv.put("subject", "");
        cv.put("createdAt", "");
        cv.put("updatedAt", "");
        cv.put("id", current_date_milliseconds);
        cv.put("syncFlag", "0");
        long insert = db.insert("New_Gratitude_Table", null, cv);
        if (insert > 0) {
            activityCall();
            startActivity(new Intent(HappyMomentView.this, HomeScreenActivity.class)
                    .putExtra("FROM_SCREEN", "JOURNAL"));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
            finish();

        }
        db.close();
    }

    private void activityCall() {
        // Save Locally
        END_DATE = df.format(Calendar.getInstance().getTime());
        MySql dbHelper = new MySql(HappyMomentView.this, "mydb", null, MySql.version);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("date_time", START_DATE);
        cv.put("feature_name", "HappyMomentView");
        cv.put("activity_name", "HappyMomentView");
        cv.put("activity_detail", "HappyMomentView");
        cv.put("start_date_time", START_DATE);
        cv.put("end_date_time", END_DATE);
        cv.put("sync_flag", "0");
        db.insert("New_Activity_Table", null, cv);
        db.close();
    }


    private boolean checkValidation() {

        boolean returnValue = true;

        // upoadImageView validation
        // if (!CommonUtils.hasText(upoadImageView.getTag(), "Please fill the field"))
        //    returnValue = false;

        // upoadImageView validation
        if (!CommonUtils.hasText(feelingText, "Please fill the field"))
            returnValue = false;

        // titleText validation
        if (!CommonUtils.hasText(titleText, "Please fill the field"))
            returnValue = false;

        return returnValue;
    }

    @Override
    public void onBackPressed() {
        activityCall();
        super.onBackPressed();
    }
}
