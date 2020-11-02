package com.nsmiles.happybeingsdklib.UI.gratitude;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.Models.SendEmailModel;
import com.nsmiles.happybeingsdklib.Models.SendGratitudeModel;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;
import com.nsmiles.happybeingsdklib.dagger.application.BaseApplication;
import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
import com.nsmiles.happybeingsdklib.network.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ExpressGratitudeOthers extends AppCompatActivity implements View.OnClickListener {

    Activity activity;
    CommonUtils commonUtils;
    String START_DATE = "", END_DATE = "", user_email, user_id;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z", Locale.ENGLISH);
    Toolbar express_love_toolbar;
    EditText express_your_gratitude, email_et, subject_et;
    Button save_button;
    private Uri mUri;
    TextView hint_text_all;
    LinearLayout adViewLayer;
    boolean user_paid = false;
    ImageView gratitude_img;
    CompositeSubscription compositeSubscription;

    @Inject
    DataManager dataManager;

    @Inject
    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_gratitude_others);
        activity = ExpressGratitudeOthers.this;
        START_DATE = df.format(Calendar.getInstance().getTime());
        init();
    }

    private void init() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        express_love_toolbar = (Toolbar) findViewById(R.id.express_love_toolbar);
        express_your_gratitude = (EditText) findViewById(R.id.express_your_gratitude);
        save_button = (Button) findViewById(R.id.save_button);
        email_et = (EditText) findViewById(R.id.email_et);
        subject_et = (EditText) findViewById(R.id.subject_et);
        save_button.setOnClickListener(this);
        hint_text_all=  (TextView) findViewById(R.id.hint_text_all);
        adViewLayer = (LinearLayout) findViewById(R.id.adViewLayer);
        gratitude_img = (ImageView) findViewById(R.id.gratitude_img);
        express_love_toolbar.setTitle("To others");
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
            getWindow().setStatusBarColor(ContextCompat.getColor(ExpressGratitudeOthers.this ,R.color.hb_circle_black_light));
        }

        compositeSubscription = new CompositeSubscription();
        ((BaseApplication)getApplication()).getmApplicationApiComponent().inject(this);

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

    @Override
    protected void onResume() {
        commonUtils = new CommonUtils();
       // user_paid = commonUtils.getPaidStatus(activity);
        user_email = commonUtils.getUserEmail(activity);
       // user_id = commonUtils.getUserId(activity);
        adViewLayer.setVisibility(View.GONE);
        CommonUtils.assignProfilePic(activity, "OTHERS", "", "", gratitude_img);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.save_button) {
            if (checkValidation()) {
                if (dataManager.isNetworkAvailable(activity)) {
                    SendGratitudeModel sendEmail;
                    sendEmail = new SendGratitudeModel();
                    sendEmail.setTo(email_et.getText().toString());
                    sendEmail.setSubject(subject_et.getText().toString());
                    sendEmail.setBody(express_your_gratitude.getText().toString());
                    Subscription subscription = service.SendGratitudeMailToFriend(sendEmail, new Service.SendGratitudeCallBack() {
                        @Override
                        public void onSuccess(SendEmailModel sendEmailModel) {
                            dataManager.toast(activity, "Mail send successfully");
                            finish();
                        }

                        @Override
                        public void onError(String error) {

                            dataManager.toast(activity, error);
                        }

                        @Override
                        public void onCatch() {
                            dataManager.toast(activity, activity.getResources().getString(R.string.went_wrong_try_again));
                        }
                    });

                    compositeSubscription.add(subscription);

                } else {
                    dataManager.toast(activity, "Please connect to internet");
                }
            }
        }
    }


    private void activityCall() {
        // Save Locally
        END_DATE = df.format(Calendar.getInstance().getTime());
        MySql dbHelper = new MySql(activity, "mydb", null, MySql.version);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("date_time", START_DATE);
        cv.put("feature_name", "TO_OTHERS");
        cv.put("activity_name", "TO_OTHERS");
        cv.put("activity_detail", "TO_OTHERS");
        cv.put("start_date_time", START_DATE);
        cv.put("end_date_time", END_DATE);
        cv.put("sync_flag", "0");
        db.insert("New_Activity_Table", null, cv);
        db.close();
    }

    private boolean checkValidation() {

        boolean returnValue = true;

        if (!CommonUtils.isValid(email_et, "Please enter valid email id"))
            returnValue = false;

        if (subject_et.getText().toString().equalsIgnoreCase(""))
            subject_et.setText(getResources().getString(R.string.radio_radioOthers_subject));

        if (!CommonUtils.hasText(subject_et, "Please fill the field"))
            returnValue = false;

        if (!CommonUtils.hasText(express_your_gratitude, "Please fill the field"))
            returnValue = false;

        return returnValue;
    }
}
