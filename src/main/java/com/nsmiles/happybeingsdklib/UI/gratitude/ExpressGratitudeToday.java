package com.nsmiles.happybeingsdklib.UI.gratitude;

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
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class ExpressGratitudeToday extends AppCompatActivity implements View.OnClickListener {

    Activity activity;
    CommonUtils commonUtils;
    LinearLayout option_layout, adViewLayer;
    Button recommendButton, save_button;
    TextView recommend_change, hint_text_all, about_today_tv;
    EditText express_your_gratitude;
    String[] items;
    Intent intent;
    Toolbar express_love_toolbar;
    String START_DATE = "", END_DATE = "", user_email, user_id, _id, diary_date_time;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z", Locale.ENGLISH);
    boolean from_diary = false;
    long current_date_milliseconds;
    Calendar calendar;
    Date today;
    private SQLiteDatabase db;
    MySql dbHelper;
    boolean user_paid = false;
    ImageView gratitude_img;
    int move_position=0;
    private int gratitude_date = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_gratitude_today);
        activity = ExpressGratitudeToday.this;
        START_DATE = df.format(Calendar.getInstance().getTime());
        commonUtils = new CommonUtils();
        init();
    }

    private void init() {


        items = new String[]{"Parents", "Family – Husband, siblings, kids, parents, Wife", "Friends", "People willing to help", "My Ability to learn",
                "My Ability to Work", "Having Money in bank", "Opportunity to get education", "Things I possess", "Things in my home",
                "Ability to hear", "Ability to see", "Ability to touch/feel", "Ability to smell", "Ability to taste", "My Heart",
                "My Lungs", "My Immune system", "My Hands", "My Legs", "My Mind", "My  health", "My Mentors", "The Sun , The moon and stars",
                "Changing Seasons", "3 top inventions", "Breathing air", "Water to drink or use", "Food to eat", "Nature",
                "People who believed in me", "Trips I made due to work or on vacation", "Ability to indulge in physical activities",
                "Books I loved to read", "Character traits I like in myself", "Strangers who helped me", "Role models", "Ability to feel safe today",
                "Technology gadgets", "Things I take for granted", "Things that make me laugh", "TV shows I enjoy", "Things am good at", "Opportunities I have/had",
                "Luxuries to Indulge", "Life lesson learnt", "People who make food for me", "Ability to enjoy Simple pleasures", "Gifts received",
                "Goals achieved", "Pleasant Memories", "Animals in planet", "My Laptop", "Internet", "My work", "Music I love", "Enemies if any",
                "Heartbreaks to have", "Mistakes to make", "My Car", "Life challenges", "A Bed to sleep", "Time", "Pain in my heart", "Pain in the body",
                "My means of transport", "Rainbows", "Ocean, River , lake", "Beach", "Freedom of speech", "Sadness if any", "Fears if any", "Drinking water",
                "Having access to food when hungry", "Facing Disappointments if any", "God ", "Snow", "Childhood", "Born as human",
                "Grandparents or grandchildren", "Entrepreneurs", "Having access to eat", "Being safe", "Nation I am born", "Nation I live",
                "Community/ neighbourhood", "Beauty Inner", "Outer beauty of my body", "Being alive", "Conveniences At home", "Conveniences at work / School",
                "The universe", "Medicines of any kind", "Protectors – army, police, defence, CID etc", "Peace and quiet in life", "Travel",
                "Changes in life", "Sleep", "Electricity", "Diversity and culture", "Humour and laughter", "Pets", "Peers", "Opportunities to volunteer",
                "Being loved", "My education and wisdom", "Generosity", "Hope for tomorrow", "Savings or financial freedom", "Tough times or failures",
                "Love in my life", "Farmers", "Hot shower bath", "Internal organs", "Hormones", "Having a partner", "Weekends", "Learning from mistakes",
                "Ability to read", "Breathing fresh air", "Freedom of religion", "Mobile phone", "Freedom", "Ability to cry", "Siblings", "My network of connections",
                "Death", "Birth", "Teeth to eat", "Music", "Dance", "Laughter", "Politicians", "Prime minister", "Chief Minister", "Principal at my school / College",
                "Teacher who taught me how to read", "Friend who stands by me", "My group of friends", "Community workers", "Garbage Collectors in my community",
                "Bees", "Fairy tales", "Creative writers", "Movies", "Entertainment channels", "Actors", "Actresses", "Dreams to achieve", "Hobbies to Indulge"
        };
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        express_love_toolbar = (Toolbar) findViewById(R.id.express_love_toolbar);
        option_layout = (LinearLayout) findViewById(R.id.option_layout);
        adViewLayer = (LinearLayout) findViewById(R.id.adViewLayer);
        recommendButton = (Button) findViewById(R.id.recommendButton);
        save_button = (Button) findViewById(R.id.save_button);
        recommend_change = (TextView) findViewById(R.id.recommend_change);
        hint_text_all = (TextView) findViewById(R.id.hint_text_all);
        about_today_tv = (TextView) findViewById(R.id.about_today_tv);
        express_your_gratitude = (EditText) findViewById(R.id.express_your_gratitude);
        gratitude_img = (ImageView) findViewById(R.id.gratitude_img);
        intent = getIntent();
       adViewLayer.setVisibility(View.GONE);
        express_love_toolbar.setTitle("Express gratitude");
       // setSupportActionBar(express_love_toolbar);
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
            getWindow().setStatusBarColor(ContextCompat.getColor(ExpressGratitudeToday.this, R.color.hb_circle_black_light));
        }
    }

    @Override
    protected void onResume() {

        try {
            if (intent.hasExtra("HIDE")) {

                if (intent.getStringExtra("HIDE").equalsIgnoreCase("RECOMMEND")) {
                    hideRecommend();
                }
                if (intent.getStringExtra("HIDE").equalsIgnoreCase("IMAGE")) {
                    hideImage();
                }
                if (intent.hasExtra("GRATITUDE_DATE")) {
                    gratitude_date = intent.getIntExtra("GRATITUDE_DATE", 0);
                }

                if (intent.hasExtra("SELF") && intent.hasExtra("ID")) {
                    from_diary = true;
                    save_button.setText("UPDATE");
                    express_your_gratitude.setText(intent.getStringExtra("SELF"));
                    _id = intent.getStringExtra("ID");
                    diary_date_time = intent.getStringExtra("DATE_TIME");

                }
            }


            user_email = commonUtils.getUserEmail(activity);

            save_button.setOnClickListener(this);
            recommend_change.setOnClickListener(this);
           // recommendButton.setOnClickListener(this);

            today = new Date();
            calendar = Calendar.getInstance();
            calendar.setTime(today);
            current_date_milliseconds = calendar.getTimeInMillis();

            dbHelper = new MySql(this, "mydb", null, MySql.version);
            db = dbHelper.getWritableDatabase();
            recommendButton.setText(items[new Random().nextInt(items.length)]);

            CommonUtils.assignProfilePic(activity, "ABOUT", "", "", gratitude_img);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onResume();
    }



    @Override
    public void onBackPressed() {
        Log.d("onBackPressed", String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));
        goToGratitudeJournalListFragment();
        /*getSupportFragmentManager().popBackStack("FLAG_JOURNAL",0);
        super.onBackPressed();*/


    }

    private void goToGratitudeJournalListFragment() {

        if (intent.hasExtra("NAME")) {
            String name = intent.getStringExtra("NAME");

            if (name.equals(AppConstants.EXPRESS_GRATITUDE)) {

                finish();

            }
        }else {

            Intent i=new Intent(this, HomeScreenActivity.class);
            i.putExtra("key","value");
            this.startActivity(i);
            finish();

        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            goToGratitudeJournalListFragment();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                        updateserverExpressGratitude();
                    } else {
                        updateExpressGratitude();
                    }
                }
            } else {
                if (checkValidation()) {
                    save_button.setClickable(false);
                    save_button.setFocusable(false);
                    if (CommonUtils.isNetworkAvailable(this)) {
                        saveExpressGratitude();
                    } else {
                        saveExpressLocally("0");
                    }
                }
            }
        } else if (id == R.id.recommend_change) {
            recommendButton.setText(items[new Random().nextInt(items.length)]);
            //            case R.id.recommendButton:
//                recommendButton.setText(items[new Random().nextInt(items.length)]);
//                break;
        }
    }

    private void hideRecommend() {

        option_layout.setVisibility(View.GONE);
        //about_today_tv
        String title = "";
        Intent intent = getIntent();
        if (intent.hasExtra("TITLE")) {
            title = intent.getStringExtra("TITLE");
        }
        if (title.equals("About my work")) {
            about_today_tv.setText("About my work");
            express_love_toolbar.setTitle("About my work");
            hint_text_all.setText(getResources().getString(R.string.about_work));

        } else if (title.equals("About today")){
            express_love_toolbar.setTitle("About today");
            hint_text_all.setText(getResources().getString(R.string.express_gratitude_radioThreeThinks));

        }
    }

    private void hideImage() {
        option_layout.setVisibility(View.VISIBLE);
        express_love_toolbar.setTitle("Guide me");
        about_today_tv.setVisibility(View.GONE);
        gratitude_img.setVisibility(View.GONE);
        hint_text_all.setText(getResources().getString(R.string.recommond_button_text));

    }

    private boolean checkValidation() {

        boolean returnValue = true;

        if (!CommonUtils.hasText(express_your_gratitude, "Please fill the field"))
            returnValue = false;

        return returnValue;
    }


    private void updateExpressGratitude() {

        try {
            if (checkValidation()) {
                ContentValues cv = new ContentValues();
                Cursor cursor = db.rawQuery("SELECT * FROM New_Gratitude_Table where email_id=? AND syncFlag=? AND date_time=?",
                        new String[]{user_email, "0", diary_date_time}, null);
                if (cursor.getCount() > 0) {
                    cv.put("syncFlag", "0");
                } else {
                    cv.put("syncFlag", "3");
                }
                //    cv.put("date_time", new Date().toString());
                cv.put("description", express_your_gratitude.getText().toString());
                cv.put("express_your_gratitude", recommendButton.getText().toString());
                db.update("New_Gratitude_Table", cv, "date_time=?", new String[]{diary_date_time});
                CommonUtils.CloseCursor(cursor);
                activityCall();
                setResult(RESULT_OK,new Intent().putExtra(AppConstants.MOVE_POSITION, move_position));
                finish();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveExpressLocally(String sync_flag) {

        MySql dbHelper = new MySql(this, "mydb", null, MySql.version);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("user_id", user_id);
        cv.put("date_time", new Date().toString());
        cv.put("type_of_gratitude", "EXPRESS_GRATITUDE");
        cv.put("description", express_your_gratitude.getText().toString());
        cv.put("link", "TEST_LINK_ANDROID");
        cv.put("pic", "");
        if (express_love_toolbar != null)
            cv.put("title", express_love_toolbar.getTitle().toString());
        cv.put("email_id", user_email);
        cv.put("express_your_gratitude", recommendButton.getText().toString());
        cv.put("subject", "");
        cv.put("createdAt", "");
        cv.put("updatedAt", "");
        cv.put("id", current_date_milliseconds);
        cv.put("syncFlag", sync_flag);
        long insert = db.insert("New_Gratitude_Table", null, cv);
        if (insert > 0) {
            activityCall();
            setResult(RESULT_OK);
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
        cv.put("date_time", START_DATE);
        cv.put("feature_name", "EXPRESS_GRATITUDE");
        cv.put("activity_name", "EXPRESS_GRATITUDE");
        cv.put("activity_detail", "EXPRESS_GRATITUDE");
        cv.put("start_date_time", START_DATE);
        cv.put("end_date_time", END_DATE);
        cv.put("sync_flag", "0");
        db.insert("New_Activity_Table", null, cv);
        db.close();
    }

    private void saveExpressGratitude() {

        try {
            if (CommonUtils.isNetworkAvailable(activity)) {
                SelfLoveData selfLoveData = new SelfLoveData();
                selfLoveData.setUser_id(user_id); // "59252e6c12354bed8e9b750e");
                selfLoveData.setDate_time(new Date().toString());
                selfLoveData.setType_of_gratitude("EXPRESS_GRATITUDE");
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

    private void updateserverExpressGratitude() {

        try {
            if (CommonUtils.isNetworkAvailable(activity)) {
                SelfLoveData selfLoveData = new SelfLoveData();
                selfLoveData.setUser_id(user_id); // "59252e6c12354bed8e9b750e");
                selfLoveData.setDate_time(new Date().toString());
                selfLoveData.setType_of_gratitude("EXPRESS_GRATITUDE");
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
                            updateExpressGratitude();
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
