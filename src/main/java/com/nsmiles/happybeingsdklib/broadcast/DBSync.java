package com.nsmiles.happybeingsdklib.broadcast;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nsmiles.happybeingsdklib.Affimations.AffirmationModel;
import com.nsmiles.happybeingsdklib.MindGym.AddEmotionRequest;
import com.nsmiles.happybeingsdklib.Models.AddActivityRequest;
import com.nsmiles.happybeingsdklib.Models.AlertPreference;
import com.nsmiles.happybeingsdklib.Models.AssessmentScore;
import com.nsmiles.happybeingsdklib.MyCoach.CoachModel;
import com.nsmiles.happybeingsdklib.ServerApiConnectors.API_Response_Listener;
import com.nsmiles.happybeingsdklib.ServerApiConnectors.ApiProvider;
import com.nsmiles.happybeingsdklib.UI.gratitude.SelfLoveData;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;
import com.nsmiles.happybeingsdklib.dagger.data.PreferenceManager;
import com.nsmiles.happybeingsdklib.diaryfragment.DiaryData;

import java.util.List;


/**
 * Created by Gopal on 31-08-2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public class DBSync extends BroadcastReceiver {

    private SharedPreferences sharedPreferences;
    private PreferenceManager preferenceManager;
    private String token;
    // public SQLiteDatabase db;
    private SelfLoveData selfLoveData;
    private final AddEmotionRequest addEmotionRequest = new AddEmotionRequest();
    private final AlertPreference alertPreference = new AlertPreference();

    private final AssessmentScore assessmentScore = new AssessmentScore();
    private final AddActivityRequest addActivityRequest = new AddActivityRequest();
    CommonUtils commonUtils;
    String user_email, user_primaryProfile, user_secondaryProfile, user_user_id, user_login_status, user_gender;
    List<DiaryData> diaryDataList;
    String del_id, update_id, user_id, date_time, type_of_gratitude, description, link, pic, title, email, subject, express_your_gratitude;
    int push_id;

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            Log.i("Start", "onReceive");

            try {
                preferenceManager = new PreferenceManager(context);
                commonUtils = new CommonUtils();
                sharedPreferences = context.getSharedPreferences("HAPPY_BEING", Context.MODE_PRIVATE);
                user_email = sharedPreferences.getString("user_email", "");
                user_primaryProfile = sharedPreferences.getString("user_role", "");
                user_secondaryProfile = sharedPreferences.getString("secondaryValue", "");
                token = sharedPreferences.getString("user_token", "");
                user_user_id = sharedPreferences.getString("user_id", "");
                user_login_status = sharedPreferences.getString("user_login", "");

                if (CommonUtils.isNetworkAvailable(context)) {
                    if (user_login_status.equalsIgnoreCase("true")) {
                        pushGratitudeData(context);
                        deleteGratitudeData(context);
                        updateGratitudeData(context);
                        pushFeelingData(context);
                        pushAffirmationData(context);
                        user_primaryProfile = user_primaryProfile.toUpperCase();
                        pushAlertPreference(context, user_email);
                    }
                    pushActivityData(context);


                } else {
                    Log.i("No Internet Connection", "onPerformSync");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            Log.i("End", "onPerformSync");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void pushAffirmationData(Context context) {
        MySql dbHelper = new MySql(context, "mydb", null, MySql.version);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            @SuppressLint("Recycle") final Cursor cursor = db.rawQuery("SELECT * FROM New_MyCoach_Affirmation_Table WHERE sync_flag=?", new String[]{"0"});
            Log.i("DBsync", "In db sync affirmations count is "+cursor.getCount());
            if (cursor.getCount() > 0) {
                boolean affirmationStarted = preferenceManager.get("AFFIRMATIONS_STARTED", false);
                if (!affirmationStarted) {
                    do {
                        cursor.moveToFirst();
                        preferenceManager.add("AFFIRMATIONS_STARTED", true);
                        String typeOfEvent = cursor.getString(cursor.getColumnIndexOrThrow("mind_gym_type"));
                        Log.i("DBSync", "TYPE IS "+typeOfEvent);
                        if (typeOfEvent != null && typeOfEvent.equals("AFFIRMATION")) {
                            final AffirmationModel affirmationModel = new AffirmationModel();
                            affirmationModel.set_id(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
                            affirmationModel.setUser_id(cursor.getString(cursor.getColumnIndexOrThrow("user_id")));
                            affirmationModel.setDate_time(cursor.getString(cursor.getColumnIndexOrThrow("date_time")));
                            affirmationModel.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                            affirmationModel.setPrimary_profile(cursor.getString(cursor.getColumnIndexOrThrow("primary_profile")));
                            affirmationModel.setSecondary_profile(cursor.getString(cursor.getColumnIndexOrThrow("secondary_profile")));
                            affirmationModel.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                            affirmationModel.setMind_gym_type(cursor.getString(cursor.getColumnIndexOrThrow("mind_gym_type")));
                            affirmationModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                            affirmationModel.setUrl(cursor.getString(cursor.getColumnIndexOrThrow("url")));
                            affirmationModel.setMycoach_completed_days(cursor.getString(cursor.getColumnIndexOrThrow("completed_date")));
                            affirmationModel.setCompleted_date(cursor.getString(cursor.getColumnIndexOrThrow("mycoach_completed_date")));
                            affirmationModel.setSync_flag(cursor.getString(cursor.getColumnIndexOrThrow("sync_flag")));
                            Log.d(getClass().getSimpleName(), affirmationModel.getSync_flag());
                            token = sharedPreferences.getString("user_token", "");
                            new ApiProvider.SaveAffirmationData(affirmationModel, token, 2, new API_Response_Listener<String>() {

                                @Override
                                public void onComplete(String data, long request_code, String failure_code) {
                                    if (data != null) {
                                        if (data.equalsIgnoreCase("true")) {
                                            Log.d(getClass().getSimpleName(), "true");
                                            ContentValues cv = new ContentValues();
                                            cv.put("sync_flag", "1");
                                            int updated = db.update("New_MyCoach_Affirmation_Table", cv, "_id" + "=?", new String[]{String.valueOf(affirmationModel.get_id())});
                                            Log.i("DBSync", "Updated is "+updated);
                                            preferenceManager.add("AFFIRMATIONS_STARTED", false);

                                        } else {
                                            Log.d(getClass().getSimpleName(), "LIVE DATA INSERTED");
                                        }
                                    }
                                }
                            }).call();
                        }
                        else {
                            final CoachModel coachModel = new CoachModel();
                            coachModel.set_id(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
                            coachModel.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                            coachModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                            coachModel.setUrl(cursor.getString(cursor.getColumnIndexOrThrow("url")));
                            coachModel.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                            coachModel.setCompleted_date(cursor.getString(cursor.getColumnIndexOrThrow("mycoach_completed_date")));
                            token = sharedPreferences.getString("user_token", "");
                            new ApiProvider.SaveRelaxAudioData(coachModel, token, 2, new API_Response_Listener<String>() {
                                @Override
                                public void onComplete(String data, long request_code, String failure_code) {
                                    if (data != null) {
                                        Log.i("Dbsync", "Data is "+data);
                                        if (data.equalsIgnoreCase("true")) {
                                            Log.d(getClass().getSimpleName(), "true");
                                            ContentValues cv = new ContentValues();
                                            cv.put("sync_flag", "1");
                                            preferenceManager.add("AFFIRMATIONS_STARTED", false);
                                            int updated = db.update("New_MyCoach_Affirmation_Table", cv, "_id" + "=?", new String[]{String.valueOf(coachModel.get_id())});
                                            Log.i("Dbsync", "Updated is "+updated);
                                        } else {
                                            Log.d(getClass().getSimpleName(), "LIVE DATA INSERTED");
                                        }
                                    }
                                }
                            }).call();
                        }
                    } while (cursor.moveToNext());

                }

            }
            cursor.close();

        }
    }


    // Push Activity MultipleReportData
    private void pushActivityData(Context pushActivityDataContext) {

        try {
            MySql dbHelper = new MySql(pushActivityDataContext, "mydb", null, MySql.version);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if (db.isOpen()) {
                int tableId;
                String uid, dateTime, deviceId, feature_name, activity_name, activity_detail, startDate, endDate, device_os;

                // get data from local db
                @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM New_Activity_Table WHERE sync_flag=?", new String[]{"0"});

                Log.i("New_Activity_Table", String.valueOf(cursor.getCount()));

                if (cursor.getCount() > 0) {
                    Log.i(getClass().getSimpleName(), String.valueOf(cursor.getCount()));
                        cursor.moveToFirst();
                        tableId = cursor.getInt(cursor.getColumnIndex("_id"));
                        uid = cursor.getString(cursor.getColumnIndex("user_id"));
                        dateTime = cursor.getString(cursor.getColumnIndex("date_time"));
                        deviceId = cursor.getString(cursor.getColumnIndex("device_id"));
                        device_os = cursor.getString(cursor.getColumnIndex("device_os"));
                        feature_name = cursor.getString(cursor.getColumnIndex("feature_name"));
                        activity_name = cursor.getString(cursor.getColumnIndex("activity_name"));
                        activity_detail = cursor.getString(cursor.getColumnIndex("activity_detail"));

                        startDate = cursor.getString(cursor.getColumnIndex("start_date_time"));
                        endDate = cursor.getString(cursor.getColumnIndex("start_date_time"));

                        addActivityRequest.setId(tableId);
                        addActivityRequest.setUser_id(uid);
                        addActivityRequest.setDate_time(dateTime);
                        addActivityRequest.setDevice_id(deviceId);
                        addActivityRequest.setDevice_os(device_os);
                        addActivityRequest.setFeature_name(feature_name);
                        addActivityRequest.setActivity_name(activity_name);
                        addActivityRequest.setActivity_detail(activity_detail);
                        addActivityRequest.setStart_date_time(startDate);
                        addActivityRequest.setEnd_date_time(endDate);

                        pushActivityDataActivity(pushActivityDataContext, tableId, 1, cursor);

                    }
                } else {
                    Log.i(getClass().getSimpleName(), "Sync Completed");
                }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pushActivityDataActivity(final Context pushActivityDataContext, final int tableId, final int localOrLive, final Cursor cursor) {
        try {
            token = sharedPreferences.getString("user_token", "");
            // CALL API to save data
/*
            new ApiProvider.SaveActivity(addActivityRequest, token, 2, new API_Response_Listener<String>() {

                @Override
                public void onComplete(String data, long request_code, String failure_code) {

                    MySql dbHelper = new MySql(pushActivityDataContext, "mydb", null, MySql.version);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    if (db.isOpen()) {
                        if (data != null) {
                            if (data.equalsIgnoreCase("true")) {
                                Log.d(getClass().getSimpleName(), "true");
                                if (localOrLive > 0) {
                                    ContentValues cv = new ContentValues();
                                    cv.put("sync_flag", "1");
                                    db.execSQL("UPDATE New_Activity_Table SET sync_flag='1' WHERE _id= " + tableId);
                                    preferenceManager.add("ACTIVITY_CALLED", false);

                                } else {
                                    Log.d(getClass().getSimpleName(), "LIVE DATA INSERTED");
                                }
                            }
                        }
                    }
                    db.close();
                }
            }).call();
*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    // Push Gratitude MultipleReportData
    private void pushGratitudeData(final Context pushGratitudeDataContext) {
        Log.i("DBSync", "In push gratitude data");
        try {
            MySql dbHelper = new MySql(pushGratitudeDataContext, "mydb", null, MySql.version);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if (db.isOpen()) {
                String user_id, date_time, type_of_gratitude, description, link, pic, title, email, subject, express_your_gratitude, sync_flag;


                @SuppressLint("Recycle") final Cursor cursor = db.rawQuery("SELECT * FROM New_Gratitude_Table WHERE syncFlag=?", new String[]{"0"});
                if (cursor.getCount() > 0) {
                    if (!preferenceManager.get("GRATITUDE_STARTED", false)) {
                        preferenceManager.add("GRATITUDE_STARTED", true);
                        do {
                            cursor.moveToFirst();

                            sync_flag = cursor.getString(cursor.getColumnIndexOrThrow("syncFlag"));
                            push_id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                            user_id = cursor.getString(cursor.getColumnIndexOrThrow("user_id"));
                            date_time = cursor.getString(cursor.getColumnIndexOrThrow("date_time"));
                            type_of_gratitude = cursor.getString(cursor.getColumnIndexOrThrow("type_of_gratitude"));
                            description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                            express_your_gratitude = cursor.getString(cursor.getColumnIndexOrThrow("express_your_gratitude"));
                            link = cursor.getString(cursor.getColumnIndexOrThrow("link"));
                            pic = cursor.getString(cursor.getColumnIndexOrThrow("pic"));
                            title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                            email = cursor.getString(cursor.getColumnIndexOrThrow("email_id"));
                            subject = cursor.getString(cursor.getColumnIndexOrThrow("subject"));

                            selfLoveData = new SelfLoveData();
                            selfLoveData.setUser_id(user_id);
                            selfLoveData.setDate_time(date_time);
                            selfLoveData.setType_of_gratitude(type_of_gratitude);
                            selfLoveData.setDescription(description);
                            selfLoveData.setExpress_your_gratitude(express_your_gratitude);
                            selfLoveData.setLink(link);
                            selfLoveData.setPic(pic);
                            selfLoveData.setTitle(title);
                            selfLoveData.setEmail(email);
                            selfLoveData.setSubject(subject);

                            token = sharedPreferences.getString("user_token", "");
                            new ApiProvider.SaveSelfLove(selfLoveData, token, 2, new API_Response_Listener<String>() {

                                @Override
                                public void onComplete(String data, long request_code, String failure_code) {
                                    Log.e("failure_code", failure_code);

                                    MySql dbHelper = new MySql(pushGratitudeDataContext, "mydb", null, MySql.version);
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                                    if (db.isOpen()) {
                                        if (data == null) {
                                            Log.e("data", "null");
                                        } else {
                                            if (data.equalsIgnoreCase("true")) {
                                                System.out.println("data-true");
                                                ContentValues cv = new ContentValues();
                                                cv.put("syncFlag", "1");
                                                cv.put("id", selfLoveData.getId());
                                                db.update("New_Gratitude_Table", cv, "_id=?", new String[]{String.valueOf(push_id)});
                                                Log.d(getClass().getSimpleName(), "EXPRESS  LIVE DATA UPLOADED TO SERVER");
                                                 preferenceManager.add("GRATITUDE_STARTED", false);

                                            } else {
                                                System.out.println("data-false");

                                            }

                                        }
                                    }

                                }
                            }).call();

                        } while (cursor.moveToNext());

                    }
                }
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteGratitudeData(final Context pushGratitudeDataContext) {

        try {
            MySql dbHelper = new MySql(pushGratitudeDataContext, "mydb", null, MySql.version);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if (db.isOpen()) {
                @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM New_Gratitude_Table WHERE syncFlag=?", new String[]{"2"});
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    del_id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                    selfLoveData = new SelfLoveData();
                    selfLoveData.setId(String.valueOf(del_id));
                    try {

                        token = sharedPreferences.getString("user_token", "");
                        if (CommonUtils.isNetworkAvailable(pushGratitudeDataContext)) {
                            SelfLoveData selfLoveData = new SelfLoveData();
                            selfLoveData.setId(del_id); // "59252e6c12354bed8e9b750e");

                            // CALL API to save data
                            new ApiProvider.DeleteSelfLove(selfLoveData, token, 2, null, "Deleting...", new API_Response_Listener<String>() {

                                @Override
                                public void onComplete(String data, long request_code, String failure_code) {
                                    MySql dbHelper = new MySql(pushGratitudeDataContext, "mydb", null,  MySql.version);
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                                    Log.e("failure_code", failure_code);
                                    if (data == null) {
                                        Log.e("data", "null");

                                    } else {
                                        if (db.isOpen()) {
                                            Log.d(getClass().getSimpleName(), "gratitude deleted");
                                            db.delete("New_Gratitude_Table", "id=?", new String[]{del_id});
                                        }
                                    }
                                    db.close();
                                }
                            }).call();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateGratitudeData(final Context pushGratitudeDataContext) {

        try {
            MySql dbHelper = new MySql(pushGratitudeDataContext, "mydb", null, MySql.version);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if (db.isOpen()) {

                @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM New_Gratitude_Table WHERE syncFlag=?", new String[]{"3"});
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    update_id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                    user_id = cursor.getString(cursor.getColumnIndexOrThrow("user_id"));
                    date_time = cursor.getString(cursor.getColumnIndexOrThrow("date_time"));
                    type_of_gratitude = cursor.getString(cursor.getColumnIndexOrThrow("type_of_gratitude"));
                    description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                    express_your_gratitude = cursor.getString(cursor.getColumnIndexOrThrow("express_your_gratitude"));
                    link = cursor.getString(cursor.getColumnIndexOrThrow("link"));
                    pic = cursor.getString(cursor.getColumnIndexOrThrow("pic"));
                    title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                    email = cursor.getString(cursor.getColumnIndexOrThrow("email_id"));
                    subject = cursor.getString(cursor.getColumnIndexOrThrow("subject"));

                    selfLoveData = new SelfLoveData();
                    selfLoveData.setUser_id(user_id);
                    selfLoveData.setDate_time(date_time);
                    selfLoveData.setType_of_gratitude(type_of_gratitude);
                    selfLoveData.setDescription(description);
                    selfLoveData.setExpress_your_gratitude(express_your_gratitude);
                    selfLoveData.setLink(link);
                    selfLoveData.setPic(pic);
                    selfLoveData.setTitle(title);
                    selfLoveData.setEmail(email);
                    selfLoveData.setSubject(subject);
                    selfLoveData.setId(update_id);

                    try {

                        token = sharedPreferences.getString("user_token", "");
                        new ApiProvider.UpdateSelfLove(selfLoveData, token, 2, new API_Response_Listener<String>() {

                            @Override
                            public void onComplete(String data, long request_code, String failure_code) {
                                Log.e("failure_code", failure_code);

                                MySql dbHelper = new MySql(pushGratitudeDataContext, "mydb", null, MySql.version);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();

                                if (data == null) {
                                    Log.e("data", "null");
                                } else {
                                    if (data.equalsIgnoreCase("true")) {
                                        System.out.println("data-true");
                                        if (db.isOpen()) {
                                            ContentValues cv = new ContentValues();
                                            cv.put("syncFlag", "1");
                                            db.update("New_Gratitude_Table", cv, "id=?",
                                                    new String[]{update_id});
                                        }
                                        Log.d(getClass().getSimpleName(), "EXPRESS  LIVE DATA UPLOADED TO SERVER");
                                    } else {
                                        System.out.println("data-false");
                                    }
                                }
                            }
                        }).call();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Pull Gratitude MultipleReportData
    private void pullGratitudeData(final Context pullGratitudeDataContext, final String email, final String user_id) {
        try {
            token = sharedPreferences.getString("user_token", "");

            String parameters = "email=" + email;
            // Get the values from API
/*
            new ApiProvider.ViewMyDiary(parameters, token, 2, new API_Response_Listener<List<DiaryData>>() {

                @Override
                public void onComplete(List<DiaryData> returnDiaryDataList, long request_code, String failure_code) {
                    if (returnDiaryDataList != null) {
                        if (diaryDataList != null && diaryDataList.size() > 0) {
                            diaryDataList.clear();
                        }
                        diaryDataList = new ArrayList<>();
                        diaryDataList = returnDiaryDataList;
                        if (returnDiaryDataList.size() == 0) {
                            // no MultipleReportData found, skip
                        } else {
                            // Insert the data in local db
                            // MyDiaryInfo myDiaryInfo = null;
                            // int count = 0;
                            Log.e("ViewMyDairy", "diaryDataList count" + String.valueOf(diaryDataList.size()));

                            MySql dbHelper = new MySql(pullGratitudeDataContext, "mydb", null, MySql.version);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            if (db.isOpen()) {
                                for (DiaryData diaryData : diaryDataList) {
                                    //  Log.e("count", String.valueOf(count));
                                    Cursor validateCursor = db.rawQuery("SELECT * FROM New_Gratitude_Table where email_id=?  AND date_time=?",
                                            new String[]{diaryData.getEmail(), diaryData.getDate_time()}, null);
                                    if (validateCursor.getCount() == 0) {

                                        ContentValues cv = new ContentValues();
                                        cv.put("user_id", diaryData.getUser_id());
                                        cv.put("date_time", diaryData.getDate_time());
                                        cv.put("type_of_gratitude", diaryData.getType_of_gratitude().toUpperCase());
                                        cv.put("description", diaryData.getDescription());
                                        cv.put("link", diaryData.getLink());
                                        if (diaryData.getPic() != null) {
                                            cv.put("pic", diaryData.getPic());
                                        } else {
                                            cv.put("pic", "");
                                        }
                                        cv.put("title", diaryData.getTitle());
                                        cv.put("email_id", diaryData.getEmail());
                                        if (diaryData.getExpress_your_gratitude() != null) {
                                            cv.put("express_your_gratitude", diaryData.getExpress_your_gratitude());
                                        } else {
                                            cv.put("express_your_gratitude", "");
                                        }
                                        if (diaryData.getExpress_your_gratitude() != null) {
                                            cv.put("subject", diaryData.getSubject());
                                        } else {
                                            cv.put("subject", "");
                                        }
                                        cv.put("createdAt", diaryData.getCreatedAt());
                                        cv.put("updatedAt", diaryData.getUpdatedAt());
                                        cv.put("id", diaryData.getId());
                                        cv.put("syncFlag", "1");
                                        diaryData.setDate_time(diaryData.getDate_time());

                                        long insert = db.insert("New_Gratitude_Table", null, cv);
                                    }

                                    if (validateCursor != null) {
                                        validateCursor.close();
                                    }
                                }
                            }
                            db.close();
                        }

                    }
                }

            }).call();
*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Push Alert MySettingActivity
    private void pushAlertPreference(Context pushAlertPreferenceContext, String email) {

        try {
            MySql dbHelper = new MySql(pushAlertPreferenceContext, "mydb", null, MySql.version);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if (db.isOpen()) {
                int tableId;
                String mind_gym_start_time, mind_gym_end_time, relax_start_time, relax_end_time;

                // get data from local db
                @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM NotificationsTimings WHERE sync_flag=?", new String[]{"0"});

                Log.i("NotificationsTimings", String.valueOf(cursor.getCount()));

                if (cursor.getCount() > 0) {
                    Log.i(getClass().getSimpleName(), String.valueOf(cursor.getCount()));
                    cursor.moveToLast();

                    tableId = cursor.getInt(cursor.getColumnIndex("_id"));

                    mind_gym_start_time = cursor.getString(cursor.getColumnIndex("MIND_GYM_START_TIME"));
                    mind_gym_end_time = cursor.getString(cursor.getColumnIndex("MIND_GYM_END_TIME"));
                    relax_start_time = cursor.getString(cursor.getColumnIndex("RELAX_START_TIME"));
                    relax_end_time = cursor.getString(cursor.getColumnIndex("RELAX_END_TIME"));


                    alertPreference.setId(tableId);
                    alertPreference.setEmail(email);
                    if (user_gender != null && user_gender.length() > 0) {
                        alertPreference.setGender(user_gender);
                    }
                    alertPreference.setMind_gym_start_time(mind_gym_start_time);
                    alertPreference.setMind_gym_end_time(mind_gym_end_time);
                    alertPreference.setRelax_start_time(relax_start_time);
                    alertPreference.setRelax_end_time(relax_end_time);
                    pushAlertPreferenceActivity(pushAlertPreferenceContext, tableId, 1);

                } else {
                    Log.i(getClass().getSimpleName(), "Sync Completed");
                }
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pushAlertPreferenceActivity(final Context pushAlertPreferenceContext, final int tableId, final int localOrLive) {

        try {
            token = sharedPreferences.getString("user_token", "");
/*
            new ApiProvider.SaveAlertPreference(alertPreference, token, 2, new API_Response_Listener<String>() {

                @Override
                public void onComplete(String data, long request_code, String failure_code) {

                    MySql dbHelper = new MySql(pushAlertPreferenceContext, "mydb", null, MySql.version);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    if (db.isOpen()) {
                        if (data != null) {
                            if (data.equalsIgnoreCase("true")) {
                                Log.d(getClass().getSimpleName(), "true");
                                if (localOrLive > 0) {
                                    ContentValues cv = new ContentValues();
                                    cv.put("sync_flag", "1");
                                    db.execSQL("UPDATE NotificationsTimings SET sync_flag='1' WHERE _id= " + tableId);
                                } else {
                                    Log.d(getClass().getSimpleName(), "LIVE DATA INSERTED");
                                }
                            }
                        }
                    }
                    db.close();
                }
            }).call();
*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Push Feeling MultipleReportData
    private void pushFeelingData(final Context pushFeelingDataContext) {

        try {
            MySql dbHelper = new MySql(pushFeelingDataContext, "mydb", null, MySql.version);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if (db.isOpen()) {
                int tableId;
                String uid, dateTime, deviceId, emotional, feature, activity, startDate, endDate;

                // get data from local db
                @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM New_Feelings_Table WHERE sync_flag=?", new String[]{"0"});
                // Cursor cursor = db.rawQuery("SELECT * FROM New_Feelings_Table", null);

                Log.i("New_Feelings_Table", String.valueOf(cursor.getCount()));

                if (cursor.getCount() > 0) {
                    Log.i(getClass().getSimpleName(), String.valueOf(cursor.getCount()));
                    cursor.moveToFirst();
                    tableId = cursor.getInt(cursor.getColumnIndex("_id"));
                    uid = cursor.getString(cursor.getColumnIndex("user_id"));
                    dateTime = cursor.getString(cursor.getColumnIndex("date_time"));
                    deviceId = cursor.getString(cursor.getColumnIndex("device_id"));

                    emotional = cursor.getString(cursor.getColumnIndex("emotion1"));
                    feature = cursor.getString(cursor.getColumnIndex("feature"));
                    activity = cursor.getString(cursor.getColumnIndex("activity"));

                    startDate = cursor.getString(cursor.getColumnIndex("start_date_time"));
                    endDate = cursor.getString(cursor.getColumnIndex("start_date_time"));
                    addEmotionRequest.setId(tableId);

                    if (uid == null) {
                        uid = user_user_id;
                    }
                    addEmotionRequest.setUser_id(uid);
                    addEmotionRequest.setDate_time(dateTime);
                    addEmotionRequest.setDevice_id(deviceId);
                    addEmotionRequest.setEmotion1(emotional);
                    addEmotionRequest.setFeature(feature);
                    addEmotionRequest.setActivity(activity);
                    addEmotionRequest.setStart_date_time(startDate);
                    addEmotionRequest.setEnd_date_time(endDate);

                    // call web service
                    token = sharedPreferences.getString("user_token", "");
/*
                    new ApiProvider.SaveEmotions(addEmotionRequest, token, 2, new API_Response_Listener<String>() {

                        @Override
                        public void onComplete(String data, long request_code, String failure_code) {

                            MySql dbHelper = new MySql(pushFeelingDataContext, "mydb", null, MySql.version);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            if (db.isOpen()) {
                                if (data != null) {
                                    if (data.equalsIgnoreCase("true")) {
                                        Log.d(getClass().getSimpleName(), "true");

                                        ContentValues cv = new ContentValues();
                                        cv.put("sync_flag", "1");
                                        db.execSQL("UPDATE New_Feelings_Table SET sync_flag='1' WHERE _id= " + addEmotionRequest.getId());

                                    }
                                }
                            }
                            db.close();
                        }
                    }).call();
*/


                } else {
                    Log.i(getClass().getSimpleName(), "Sync Completed");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
