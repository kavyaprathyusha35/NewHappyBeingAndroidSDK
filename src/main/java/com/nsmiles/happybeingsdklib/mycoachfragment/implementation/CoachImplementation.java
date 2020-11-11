package com.nsmiles.happybeingsdklib.mycoachfragment.implementation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nsmiles.happybeingsdklib.MyCoach.CoachModel;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.ServerApiConnectors.API_Response_Listener;
import com.nsmiles.happybeingsdklib.ServerApiConnectors.ApiProvider;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.GridSpacingItemDecoration;
import com.nsmiles.happybeingsdklib.Utils.MySql;
import com.nsmiles.happybeingsdklib.Utils.SdkPreferenceManager;
import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
import com.nsmiles.happybeingsdklib.dagger.data.PreferenceManager;
import com.nsmiles.happybeingsdklib.mycoachfragment.CoachAudioDetails;
import com.nsmiles.happybeingsdklib.mycoachfragment.ForceUpdateChecker;
import com.nsmiles.happybeingsdklib.mycoachfragment.adapter.CoachAudioAdapter;
import com.nsmiles.happybeingsdklib.mycoachfragment.adapter.CoachViewPagerAdapter;
import com.nsmiles.happybeingsdklib.mycoachfragment.presenter.CoachPresenter;
import com.nsmiles.happybeingsdklib.mycoachfragment.presenter.CommonPresenter;
import com.nsmiles.happybeingsdklib.mycoachfragment.view.CoachView;
import com.nsmiles.happybeingsdklib.network.NetworkError;
import com.nsmiles.happybeingsdklib.network.NetworkService;
import com.nsmiles.happybeingsdklib.network.Service;
import com.nsmiles.happybeingsdklib.playaudio.MindGymModel;
import com.nsmiles.happybeingsdklib.settings.activity.MySettingActivity;
import com.nsmiles.happybeingsdklib.wellbeingassessment.activity.AssessmentDetailsActivity;
import com.nsmiles.happybeingsdklib.wellbeingassessment.adapter.WellBeingAdapter;
import com.nsmiles.happybeingsdklib.wellbeingassessment.implementation.WellBeingCategoryImplementation;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.CorporateModel;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.assessmentcompleted.AssessmentCompletedStatus;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.categorymodel.WellBeingCategoryCategory;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.categorymodel.WellBeingCategoryStatusModel;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static android.content.Context.MODE_PRIVATE;


public class CoachImplementation implements CoachPresenter, ForceUpdateChecker.OnUpdateNeededListener, CommonPresenter.WellBeingCategoryPresenter {
    private String id = "";
    private String titleName = "";
    private String API_URL, CATEGORY_API_URL, COMPLETED_STATUS_API,report_API_URL;
    private String WELL_BEING_SKU_ID, WELL_BEING_SERVER_SKU_ID, PAYMENT_CATEGORY;
    private List<WellBeingCategoryCategory> wellBeingCategory;
    private boolean IS_ALL_ASSESSMENTS_COMPLETED = false;
    private ArrayList<String> sub_category;
    Map<String, ArrayList<String>> reportStatusMap = new HashMap<>();
    WellBeingAdapter wellBeingAdapter;
    private List<CorporateModel> finalModelList;
    private String[] area, image_url, title, status_title;
    String categoryString="", report_VERSION="";
    private final TextView tv_day_workout;
    private String wishmessage;
    private CoachView coachView;
    private Activity activity;
    private CommonUtils commonUtils;
    private MySql dbHelper;
    private SQLiteDatabase db;
    private String task_completed_date, total_completed_days, currentProfile, update_table;
    private String download_status, sd_card_path, base_url, sub_url, audio_title, day_number, audio_number, audio_id, duration;
    private CoachAudioDetails coachAdapterAudioDetails;
    private List<CoachAudioDetails> coachAdapterAudioList;
    Date coach_today_date, gratitude_today_date;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    private SimpleDateFormat today_dd;
    private SimpleDateFormat today_mm;
    private SimpleDateFormat default_date_format, currentDateFormat;
    String dd, mm, simple_yyy_mm_dd;
    Date dd_date, mm_month, simple_date;
    String token;
    private TextView[] dots;
    CoachAudioAdapter coachAudioAdapter;
    RecyclerView past_audio_recycle_view;
    Date today_date;
    List<MindGymModel> dummyMindGymModelList;
    List<MindGymModel> JsonMindGymModelList;
    MindGymModel mindGymModel;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    CoachModel coachModel = new CoachModel();
    int audioCount, lastAudio, play_status;
    String paymentStatus;
    private boolean is_Login = false;

    boolean feedback, offer_40, offer_20;

    Button f_not_really_button, f_yes_button, no_thanks, ok_sure;
    Button save_button;
    EditText et_feedback;
    String userMobileISDCode = "", user_mobil;
    private int dateDifference = 0;
    private FragmentManager fragmentManager;
    private String name;
    ViewPager viewPager;
    LinearLayout layoutdots;
    boolean includeEdges = true;
    private CompositeSubscription compositeSubscription;
    private Service service;
    private DataManager dataManager;
    PreferenceManager preferenceManager;


    public CoachImplementation(Service service,DataManager dataManager,boolean islogin, TextView tv_day_workout, CoachView coachView, Activity activity, RecyclerView past_audio_recycle_view,  ViewPager viewPager, LinearLayout layoutdots) {
        this.service=service;
        this.dataManager=dataManager;
        this.is_Login=islogin;
        this.coachView = coachView;
        this.tv_day_workout=tv_day_workout;
        this.activity = activity;
        this.past_audio_recycle_view = past_audio_recycle_view;
        this.viewPager=viewPager;
        this.layoutdots=layoutdots;
        compositeSubscription = new CompositeSubscription();
        commonUtils = new CommonUtils();
        coach_today_date = new Date();
        gratitude_today_date = new Date();
        today_dd = new SimpleDateFormat("dd", Locale.ENGLISH);
        today_mm = new SimpleDateFormat("MMM", Locale.ENGLISH);
        default_date_format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);  //// Tue Nov 21 17:15:27 GMT+05:30 2017
        default_date_format.setTimeZone(TimeZone.getTimeZone("GMT"));
        currentDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        TimeZone tz = TimeZone.getTimeZone("UTC");
        currentDateFormat.setTimeZone(tz);
        prefs = activity.getSharedPreferences("HAPPY_BEING", MODE_PRIVATE);
        coachView.hideUpgrade();
        ForceUpdateChecker.with(activity).onUpdateNeeded(this).check();
        titleName = "Corporate wellbeing";
        id = new SdkPreferenceManager(activity).get(AppConstants.SDK_EMAIL,"");
        Log.i("WellbeingCategory", "In set the category id is "+id);
        API_URL = NetworkService.corporateWellBeing.trim();
        CATEGORY_API_URL = NetworkService.corporateWellBeingCategoryStatus+"?id="+id.trim();
        COMPLETED_STATUS_API = NetworkService.corporateAllCompletedStatus.trim();
        PAYMENT_CATEGORY = AppConstants.CORPORATE_WELLBEING;
        report_API_URL = "CORPORATEWELLBEINGV4";
        preferenceManager=new PreferenceManager(activity);
    }

    private void trimSingleName() {
        String s = name;
        String[] words = s.split("\\s");
        if (words.length>1) {
            for (int i = 0; i < words.length; i++) {

                words[i] = words[i].replaceAll("[^\\w]", "");
                if (!(words[i].length() == 1 || words[i].contains("."))){
                    name=words[i];
                    break;
                }


            }
        }
        else{
            name=s;
        }
        if(name=="")
            name=words[0];
    }

    private void playAudios(List<MindGymModel> mindGymModelList) {

        int coach_position;
        int audio_day;
        Cursor cursor;
        int server_day;

        try {
            if (mindGymModelList != null) {
                coachAdapterAudioList = new ArrayList<>();    /// adapter list initilization
                if (currentProfile != null)
                    currentProfile = currentProfile.toUpperCase();
                mindGymModelList.get(0).setActual_server_day(1);
                /* Implementation for relax first 9 audio*/
                Log.d(getClass().getSimpleName(), String.valueOf(mindGymModelList.get(0).getActual_server_day()));
                addImagery(mindGymModelList.get(0).getDdDate(), mindGymModelList.get(0).getActual_server_day(),
                        mindGymModelList.get(0).getPaymentStatus(), mindGymModelList.get(0).getMmMonth());

                if (mindGymModelList.get(0).getActual_server_day() <= 33) {
                    // load relax audio
                    Log.i("Coach", "Date is "+mindGymModelList.get(0).getDdDate());
                    for (int i = 0; i < mindGymModelList.size(); i++) {
                        dbHelper = new MySql(activity, "mydb", null, MySql.version);
                        db = dbHelper.getWritableDatabase();

                        /*cursor = db.rawQuery("SELECT * FROM coach_audio_student_job WHERE DAY_NUMBER=?", new String[]{String.valueOf(audio_day)}, null);*/

                        cursor = db.rawQuery("SELECT * FROM relax_coach_others WHERE DAY_NUMBER=?",
                                    new String[]{String.valueOf(mindGymModelList.get(i).getActual_server_day() - 1)}, null);

                     //   CommonUtils.showLogInforamtion(getClass().getSimpleName(), "sssss aaaa", cursor.getCount(), true);

                        if (cursor.getCount() > 0) {
                            cursor.moveToFirst();
                            audio_id = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("_ID")));
                            audio_title = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_TITLE"));
                            base_url = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_BASE_URL"));
                            sub_url = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_SUB_URL"));
                            download_status = cursor.getString(cursor.getColumnIndexOrThrow("DOWNLOAD_STATUS"));
                            sd_card_path = cursor.getString(cursor.getColumnIndexOrThrow("SD_CARD_PATH"));
                            day_number = cursor.getString(cursor.getColumnIndexOrThrow("DAY_NUMBER"));
                            audio_number = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_NUMBER"));
                            duration = cursor.getString(cursor.getColumnIndexOrThrow("DURATION"));

                            update_table = "relax_coach_others";
                            coachAdapterAudioDetails = new CoachAudioDetails();
                            coachAdapterAudioDetails.setId(audio_id);
                            coachAdapterAudioDetails.setAudioOrGratitude("AUDIO");
                            coachAdapterAudioDetails.setDay_number(day_number);
                            coachAdapterAudioDetails.setAudio_title(audio_title);
                            coachAdapterAudioDetails.setCoach_duration(duration);
                            coachAdapterAudioDetails.setTable_name(update_table);
                            coachAdapterAudioDetails.setRelaxOrCoach("COACH");
                            coachAdapterAudioDetails.setAudio_number(audio_number);
                            //TODO:remove this after api imp
                            coachAdapterAudioDetails.setSynced_flag(1);
                            Log.i("CoachImplementation", "play status is "+mindGymModelList.get(0).getPlayStatus());

                            if (mindGymModelList.get(i).getPlayStatus() == 1) {
                                coachAdapterAudioDetails.setReply_audio(true);
                            } else {
                                coachAdapterAudioDetails.setReply_audio(false);
                            }

                            coachAdapterAudioDetails.setDd_date(mindGymModelList.get(i).getDdDate());
                            coachAdapterAudioDetails.setMm_month(mindGymModelList.get(i).getMmMonth());
                            coachAdapterAudioDetails.setSynced_flag(mindGymModelList.get(i).getDataSynced());
                            coachAdapterAudioDetails.setYyyyMmDdDate(mindGymModelList.get(i).getYyyyMmDdDate());
                            coachAdapterAudioDetails.setActual_server_day(mindGymModelList.get(i).getActual_server_day());
                            coachAdapterAudioDetails.setPlayStatus(mindGymModelList.get(i).getPlayStatus());
                            coachAdapterAudioDetails.setPaymentStatus(mindGymModelList.get(i).getPaymentStatus());
                            coachAdapterAudioList.add(coachAdapterAudioDetails);
                            loadGratitudeData(mindGymModelList.get(0).getActual_server_day(), mindGymModelList.get(0).getPaymentStatus(),
                                    day_number, mindGymModelList.get(0).getCurrentDate());

                            boolean available = prefs.getBoolean(AppConstants.AUDIO_SETTINGS, false);
                            if (!available) {
                                if (mindGymModelList.get(i).getPlayStatus() == 1 &&
                                        mindGymModelList.get(i).getDateDifference() == 1 && mindGymModelList.size() == 1) {
                                    editor = activity.getSharedPreferences("HAPPY_BEING", MODE_PRIVATE).edit();
                                    editor.putBoolean(AppConstants.AUDIO_SETTINGS, true);
                                    editor.apply();
                                    editor.commit();
                                    activity.startActivity(new Intent(activity, MySettingActivity.class)
                                            .putExtra("SETTINGS", "MORNING")
                                    );
                                }
                            }

                        }
                        CommonUtils.CloseCursor(cursor);
                    }
                    loadCoachAdapter();
                } else {
                    int date =  mindGymModelList.get(0).getActual_server_day();
                        date = date - 33;
                        if (date <= 33) {
                            mindGymModelList.get(0).setActual_server_day(date);
                            playAudios(mindGymModelList);
                        }
                        else {
                            date = date - 66;
                            mindGymModelList.get(0).setActual_server_day(date);
                            playAudios(mindGymModelList);
                        }
                }

                int gratitudeDayCheck;
                gratitudeDayCheck = Integer.parseInt(day_number);
                if (gratitudeDayCheck > 33) {
                    day_number = String.valueOf(gratitudeDayCheck % 33);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGratitudeData(int server_day, String paymentStatus, String gratitudeDay, String gratitudeDate) {
        Log.i("CoachImplementation", "In load couach impementation");
        loadAffirmationData(gratitudeDay, server_day, paymentStatus, gratitudeDate);
        coachAdapterAudioDetails = new CoachAudioDetails();
        coachAdapterAudioDetails.setAudioOrGratitude("GRATITUDE");
        coachAdapterAudioDetails.setGratitudeDay(dateDifference);
        coachAdapterAudioDetails.setActual_server_day(server_day);
        coachAdapterAudioDetails.setCoach_duration("5");
        coachAdapterAudioDetails.setReply_audio(false);
        coachAdapterAudioDetails.setPaymentStatus(paymentStatus);
        coachAdapterAudioList.add(coachAdapterAudioDetails);
        Log.i("Coach", "List size in load gratitude "+coachAdapterAudioList.size());

    }

    private void loadAffirmationData(String day_number, int server_day, String paymentStatus, String gratitudeDate) {
        Log.i("Coach", "*****In affirmations method *******"+dateDifference);
        coachAdapterAudioDetails = new CoachAudioDetails();
        coachAdapterAudioDetails.setAudioOrGratitude("AFFIRMATION");
        coachAdapterAudioDetails.setGratitudeDay(dateDifference);
        coachAdapterAudioDetails.setCoach_duration("5");
        coachAdapterAudioDetails.setActual_server_day(server_day);
        coachAdapterAudioDetails.setReply_audio(false);
        coachAdapterAudioDetails.setPaymentStatus(paymentStatus);
        coachAdapterAudioList.add(coachAdapterAudioDetails);
        Log.i("Coach", "List size load affirmation "+coachAdapterAudioList.size());

    }
    private void addImagery(String day_number, int server_day, String paymentStatus, String gratitudeDate) {
        Log.i("Coach", "*****In affirmations method *****"+dateDifference);
        coachAdapterAudioDetails = new CoachAudioDetails();
        coachAdapterAudioDetails.setDd_date(day_number);
        coachAdapterAudioDetails.setMm_month(gratitudeDate);
        coachAdapterAudioDetails.setSynced_flag(1);
        coachAdapterAudioDetails.setAudioOrGratitude("IMAGERY");
        coachAdapterAudioDetails.setGratitudeDay(dateDifference);
        coachAdapterAudioDetails.setCoach_duration("30");
        coachAdapterAudioDetails.setAudio_number("GUIDED_IMAGERY");
        coachAdapterAudioDetails.setTable_name("relax_coach_others");
        coachAdapterAudioDetails.setActual_server_day(server_day);
        coachAdapterAudioDetails.setReply_audio(false);
        coachAdapterAudioDetails.setPaymentStatus(paymentStatus);
        coachAdapterAudioList.add(coachAdapterAudioDetails);
        Log.i("Coach", "List size add imagery "+coachAdapterAudioList.size());

    }

    @Override
    public void hideProgress() {
        coachView.hideSyncTextView();
        coachView.hideProgressBar();
    }

    @Override
    public void defaultCoachData() {
        Log.i("Coach", "In defaultCoach");
        loadDefaultCoach();
    }

    public int getDateDifference(){
        return dateDifference;
    }

    @Override
    public void getMindGymData() {
        String parameters = "email=" + commonUtils.getUserEmail(activity);
        String version = "1";
        final String day = "7";
        final String unique = "&unique=true";
        token = commonUtils.getTokenId(activity);

        parameters = parameters.concat("&version=" + version).concat("&days=" + day).concat(unique);
       // CommonUtils.showLogInforamtion(getClass().getSimpleName(), "ggggggggg", parameters, true);

        if (CommonUtils.isNetworkAvailable(activity)) {
            coachView.hideSyncTextView();
            coachView.showProgressBar();
            new ApiProvider.GetMindGymData(parameters, token, 1, new API_Response_Listener<List<MindGymModel>>() {
                @Override
                public void onComplete(List<MindGymModel> data, long request_code, String failure_code) {

                    try {

                        JsonMindGymModelList = new ArrayList<>();
                        if (data != null) {

                            @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM coach_offline", null);
                            Log.i("Coach", "Cursor count is "+cursor.getCount());
                            if (cursor.getCount() > 0) {
                                db.delete("coach_offline", null, null);
                                cursor.close();
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

                                if (i == 0) {
                                    editor = activity.getSharedPreferences("HAPPY_BEING", MODE_PRIVATE).edit();
                                    editor.putInt("COACH_NUMBER_LAST_SYNC", data.get(0).getDateDifference());
                                    editor.putString("COACH_DATE_LAST_SYNC", data.get(0).getCurrentDate());
                                    editor.apply();
                                    editor.commit();
                                }
                                mindGymModel = new MindGymModel();
                                mindGymModel.setDateDifference(data.get(i).getDateDifference());
                                dateDifference = data.get(i).getDateDifference();
                                Log.i("Coach", "*****In affirmations method ************"+dateDifference);
                                editor = activity.getSharedPreferences(AppConstants.SHARED_HAPPY_BEING, MODE_PRIVATE).edit();
                                if (dateDifference!=0) {
                                    editor.putString(AppConstants.DATE_DIFFERENCE, String.valueOf(dateDifference));
                                    tv_day_workout.setText("Here is your day " + dateDifference + " workout.");
                                    name=new SdkPreferenceManager(activity).get(AppConstants.SDK_NAME,"");
                                    trimSingleName();
                                    editor.putString(AppConstants.DATE_DIFFERENCE, String.valueOf(dateDifference));
                                    //tv_user_message.setText(name+" here is your day " + dateDifference + " plan.");
                                    //subscription_month.setText(String.valueOf(dateDifference));
                                }
                                    editor.apply();
                                editor.commit();

                                mindGymModel.setActual_server_day(data.get(i).getDateDifference());
                                Log.i("Coach", "Setting date is "+dd);
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

                            if (JsonMindGymModelList != null && JsonMindGymModelList.size() > 0) {
                                editor = activity.getSharedPreferences(AppConstants.SHARED_HAPPY_BEING, MODE_PRIVATE).edit();
                           //     editor.putString(AppConstants.PAYMENT_API_STATUS, JsonMindGymModelList.get(0).getPaymentStatus());
                                editor.apply();
                                editor.commit();
                            }


                            if (JsonMindGymModelList != null) {
                                ContentValues contentValues = new ContentValues();
                                if (JsonMindGymModelList.get(0).getDateDifference() == 5 ||
                                        JsonMindGymModelList.get(0).getDateDifference() == 7 &&
                                                JsonMindGymModelList.get(0).getPaymentStatus().equalsIgnoreCase("TRIAL")) {

                                    // Call Feedback Api
                                    if (!feedback) {
                                        if (JsonMindGymModelList.get(0).getDateDifference() == 5) {
                                            getMindGymAudioCount(5);
                                            contentValues.put("FEEDBACK_POPUP", "5");
                                            db.update("coach_offline", contentValues, "DATE_DIFFERENCE" + "=?",
                                                    new String[]{"5"});
                                        } else {
                                            getMindGymAudioCount(7);
                                            contentValues.put("FEEDBACK_POPUP", "7");
                                            db.update("coach_offline", contentValues, "DATE_DIFFERENCE" + "=?",
                                                    new String[]{"7"});
                                        }
                                    }
                                    /////// feedback end

                                } else if (JsonMindGymModelList.get(0).getDateDifference() == 10 ||
                                        JsonMindGymModelList.get(0).getDateDifference() == 11 ||
                                        JsonMindGymModelList.get(0).getDateDifference() == 12 &&
                                                JsonMindGymModelList.get(0).getPaymentStatus().equalsIgnoreCase("TRIAL")) {
                                    if (!offer_40) {
                                        if (JsonMindGymModelList.get(0).getDateDifference() == 10) {
                                            getMindGymAudioCount(10);
                                            contentValues.put("OFFER_40_POPUP", "10");
                                            db.update("coach_offline", contentValues, "DATE_DIFFERENCE" + "=?",
                                                    new String[]{"10"});
                                        } else if (JsonMindGymModelList.get(0).getDateDifference() == 11) {
                                            getMindGymAudioCount(11);
                                            contentValues.put("OFFER_40_POPUP", "11");
                                            db.update("coach_offline", contentValues, "DATE_DIFFERENCE" + "=?",
                                                    new String[]{"11"});
                                        } else {
                                            getMindGymAudioCount(12);
                                            contentValues.put("OFFER_40_POPUP", "12");
                                            db.update("coach_offline", contentValues, "DATE_DIFFERENCE" + "=?",
                                                    new String[]{"12"});
                                        }
                                    }
                                    // Call 40 % Offer Api

                                }
                                /////   40 percent offer
                                else if (JsonMindGymModelList.get(0).getDateDifference() == 14 &&
                                        JsonMindGymModelList.get(0).getPaymentStatus().equalsIgnoreCase("TRIAL")) {
                                    if (!offer_20) {
                                        getMindGymAudioCount(14);
                                        contentValues.put("OFFER_20_POPUP", "14");
                                        db.update("coach_offline", contentValues, "DATE_DIFFERENCE" + "=?",
                                                new String[]{"14"});
                                    }
                                    // Call 20 % Api

                                }
                                /////   20 percent offer

                                else if (JsonMindGymModelList.get(0).getDateDifference() == 18 &&
                                        JsonMindGymModelList.get(0).getPaymentStatus().equalsIgnoreCase("TRIAL")) {
                                    // Call Refer Friend
                                }
                                ///
                                ///
                            }

                            if (JsonMindGymModelList != null) {
                                playAudios(JsonMindGymModelList);
                            }

                        }
                        else {
                            coachView.showSyncTextView();
                        }

                            /*else {
                                loadDefaultCoach();
                            }
                        } else {
                            loadDefaultCoach();
                        }*/

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    coachView.hideProgressBar();
                }
            }).call();
        } else {
            coachView.showNoInternet();
        }
    }

    @Override
    public void pushCoachData() {
        Log.i("Coach", "In push coach data");
        try {
            dbHelper = new MySql(activity, "mydb", null, MySql.version);
            db = dbHelper.getWritableDatabase();
            if (db.isOpen()) {
                // get data from local db
                @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM New_MyCoach_Audio_Table WHERE sync_flag=?", new String[]{"0"});
                Log.i("Coach", "New my coach audio table count is "+cursor.getCount());
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    coachModel.set_id(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
                    coachModel.setUser_id(cursor.getString(cursor.getColumnIndexOrThrow("user_id")));
                    coachModel.setDate_time(cursor.getString(cursor.getColumnIndexOrThrow("date_time")));
                    coachModel.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                    coachModel.setPrimary_profile(cursor.getString(cursor.getColumnIndexOrThrow("primary_profile")));
                    coachModel.setSecondary_profile(cursor.getString(cursor.getColumnIndexOrThrow("secondary_profile")));
                    coachModel.setMind_gym_type(cursor.getString(cursor.getColumnIndexOrThrow("mind_gym_type")));
                    coachModel.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                    coachModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                    coachModel.setUrl(cursor.getString(cursor.getColumnIndexOrThrow("url")));
                    coachModel.setRepeat(cursor.getString(cursor.getColumnIndexOrThrow("repeat")));
                    coachModel.setMycoach_completed_days(cursor.getString(cursor.getColumnIndexOrThrow("mycoach_completed_days")));
                    coachModel.setCompleted_date(cursor.getString(cursor.getColumnIndexOrThrow("mycoach_completed_date")));
                    coachModel.setSync_flag(cursor.getString(cursor.getColumnIndexOrThrow("sync_flag")));
                    Log.d(getClass().getSimpleName(), coachModel.getSync_flag());

                    new ApiProvider.SaveMyCoachData(coachModel, commonUtils.getTokenId(activity), 2, new API_Response_Listener<String>() {

                        @Override
                        public void onComplete(String data, long request_code, String failure_code) {

                            MySql dbHelper = new MySql(activity, "mydb", null, MySql.version);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();

                            if (data != null) {
                                if (data.equalsIgnoreCase("true")) {
                                    Log.d(getClass().getSimpleName(), "true");
                                    ContentValues cv = new ContentValues();
                                    cv.put("sync_flag", "1");
                                    db.update("New_MyCoach_Audio_Table", cv, "_id" + "=?", new String[]{String.valueOf(coachModel.get_id())});
                                }
                            }
                        }
                    }).call();
                }
                CommonUtils.CloseCursor(cursor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void loadOfflineCoachData() {
        Log.i("Coach", "In load offlineCoachData");
        String feed, off20,off40,today_match, today_check;

       today_match =  simpleDateFormat.format(coach_today_date);
        MySql dbHelper = new MySql(activity, "mydb", null, MySql.version);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        JsonMindGymModelList = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM coach_offline", null);
        Log.i("Coach", "Offline data is "+cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                mindGymModel = new MindGymModel();
                mindGymModel.setDateDifference(cursor.getInt(cursor.getColumnIndexOrThrow("DATE_DIFFERENCE")));
                mindGymModel.setActual_server_day(cursor.getInt(cursor.getColumnIndexOrThrow("ACTUAL_SERVER_DATE")));
                mindGymModel.setDdDate(cursor.getString(cursor.getColumnIndexOrThrow("DD_DATE")));
                mindGymModel.setMmMonth(cursor.getString(cursor.getColumnIndexOrThrow("MM_MONTH")));
                mindGymModel.setPlayStatus(cursor.getInt(cursor.getColumnIndexOrThrow("PLAY_STATUS")));
                mindGymModel.setDataSynced(cursor.getInt(cursor.getColumnIndexOrThrow("DATA_SYNCED")));
                mindGymModel.setYyyyMmDdDate(cursor.getString(cursor.getColumnIndexOrThrow("YYYY_MM_DD_DATE")));
                mindGymModel.setCurrentDate(cursor.getString(cursor.getColumnIndexOrThrow("CURRENT_DATEE")));
                mindGymModel.setPaymentStatus(cursor.getString(cursor.getColumnIndexOrThrow("PAYMENT_STATUS")));

                if(cursor.getPosition()==0){

                    Log.d(getClass().getSimpleName(), "ccc  "   + cursor.getCount());
                    Log.d(getClass().getSimpleName(), "cccdddd  "   + cursor.getColumnIndexOrThrow("CURRENT_DATEE"));
                    Log.d(getClass().getSimpleName(), "cccdddd tttt  "   + today_match);

                    today_check = cursor.getString(cursor.getColumnIndexOrThrow("YYYY_MM_DD_DATE"));
                    if(!today_match.equalsIgnoreCase(today_check)){

                        coachView.showSyncTextView();
                    }
                    else {
                        coachView.hideSyncTextView();
                    }

                    feed = cursor.getString(cursor.getColumnIndexOrThrow("FEEDBACK_POPUP"));
                    off20 = cursor.getString(cursor.getColumnIndexOrThrow("OFFER_20_POPUP"));
                    off40 = cursor.getString(cursor.getColumnIndexOrThrow("OFFER_40_POPUP"));

                    if(feed!=null && feed.length()>0){
                        getMindGymAudioCount(Integer.parseInt(feed));
                    }
                    else if(off20!=null && off20.length()>0){
                        getMindGymAudioCount(Integer.parseInt(off20));
                    }
                    else if(off40!=null && off40.length()>0){
                        getMindGymAudioCount(Integer.parseInt(off40));
                    }
                }

                JsonMindGymModelList.add(mindGymModel);
            }
            while (cursor.moveToNext());
            if (JsonMindGymModelList != null) {
                Log.i("Coach", "json mind gym model list is "+JsonMindGymModelList.size());
                playAudios(JsonMindGymModelList);
            } else {
                loadDefaultCoach();
            }
            CommonUtils.CloseCursor(cursor);
        }
        else {
            loadDefaultCoach();
        }

    }

    @Override
    public void getPaymentstatus() {
        String parameters = "email=" + commonUtils.getUserEmail(activity);
        String version = "1";
        final String day = "7";
        final String unique = "&unique=true";
        token = commonUtils.getTokenId(activity);

        parameters = parameters.concat("&version=" + version).concat("&days=" + day).concat(unique);
        // CommonUtils.showLogInforamtion(getClass().getSimpleName(), "ggggggggg", parameters, true);

        if (CommonUtils.isNetworkAvailable(activity)) {
            coachView.hideSyncTextView();
            coachView.showProgressBar();
            new ApiProvider.GetMindGymData(parameters, token, 1, new API_Response_Listener<List<MindGymModel>>() {
                @Override
                public void onComplete(List<MindGymModel> data, long request_code, String failure_code) {


                    if (data != null) {

                        for (int i = 0; i < data.size(); i++) {


                            preferenceManager.add(AppConstants.PAYMENT_STATUS,data.get(i).getPaymentStatus());


                        }



                    }
                    else {
                        coachView.showSyncTextView();
                    }


                    coachView.hideProgressBar();
                }
            }).call();
        } else {
            coachView.showNoInternet();
        }



    }

    @Override
    public void upgrade() {

       // CommonUtils.playStore(activity);
    }


    public void loadCoachAdapter() {
        if (coachAdapterAudioList != null && coachAdapterAudioList.size() > 0) {



            putIMAGERYtoBottom(coachAdapterAudioList);

            Calendar rightNow = Calendar.getInstance();
            int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);

            if (currentHourIn24Format < 12) {

                Collections.swap(coachAdapterAudioList, 0, 0);

            } else if (currentHourIn24Format < 18) {

                Collections.swap(coachAdapterAudioList, 1, 0);

            } else if (currentHourIn24Format < 24) {

                Collections.swap(coachAdapterAudioList, 0, 0);

            }



            CoachViewPagerAdapter coachViewPagerAdapter=new CoachViewPagerAdapter(activity, coachAdapterAudioList,viewPager);
            viewPager.setAdapter(coachViewPagerAdapter);
            viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
            addBottomDots(0);


            past_audio_recycle_view.setHasFixedSize(true);
            past_audio_recycle_view.setLayoutManager(new GridLayoutManager(activity, 2));
            int spanCount = 2;
            int spacing = 30;
            boolean includeEdge = false;

            if(includeEdges){
                includeEdges = false;
                past_audio_recycle_view.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
            }


            MySql dbHelper = new MySql(activity, "mydb", null, MySql.version);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            sub_category = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM Assessment_Tiles", null);
            String assmentTiles = "";
            Log.i("WellbeingCategory", "Cursor count "+cursor.getCount());
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                assmentTiles = cursor.getString(cursor.getColumnIndex("TILES_LIST"));
                Log.i("WellbeingCategory", "Assessmnet title is "+assmentTiles);
                categoryString = cursor.getString(cursor.getColumnIndex("REPORT_CATEGORY"));
                // report_API_URL = cursor.getString(cursor.getColumnIndex("REPORT_NAME"));
               // Log.i("WellbeingCategory", "Report url is "+report_API_URL);
                report_VERSION = cursor.getString(cursor.getColumnIndex("REPORT_VERSION"));
                cursor.close();
            }
            else {
                Log.i("WellbeingCategory", "detachFragment");

            /*FragmentTransaction fragmentTransaction = null;
            if (fragment.getFragmentManager() != null) {
                fragmentTransaction = fragment.getFragmentManager().beginTransaction();

            fragmentTransaction.detach(fragment);
            fragmentTransaction.attach(fragment);
            fragmentTransaction.commit();
            }*/
            }
            Type type = new TypeToken<ArrayList<CorporateModel>>() {}.getType();
            Type arrylist_type = new TypeToken<ArrayList<String>>() {}.getType();
            //Log.i("OfflineAssessment", "Assessment tiles are"+assmentTiles);
            Gson gson = new Gson();
            finalModelList = gson.fromJson(assmentTiles, type);
            sub_category = gson.fromJson(categoryString, arrylist_type);
            Log.i("WellBeingChanges", "Getting it from local db "+finalModelList);

            int index = finalModelList.size() - 1;
            finalModelList.remove(index);

            wellBeingAdapter = new WellBeingAdapter(activity, finalModelList);
            past_audio_recycle_view.setAdapter(wellBeingAdapter);

            wellBeingAdapter.setCardOnClickListener(new WellBeingAdapter.CardOnClickListener() {

                @Override
                public void onDetailsButtonClicked(List<CorporateModel> corporateModelList, int pos) {

                    activity.startActivity(new Intent(activity, AssessmentDetailsActivity.class)
                            .putExtra("TITLE_OF_ASSESSMENT", corporateModelList.get(pos).getTitle())
                            .putExtra("INSTRUCTIONS_OF_ASSESSMENT", corporateModelList.get(pos).getDescription())
                            .putExtra("LIST", (Serializable) finalModelList)
                            .putExtra("POS",pos)
                            .putExtra("isLOGIN",is_Login)
                            .putExtra(AppConstants.REPORT_SUB_TYPE, report_API_URL)
                            .putExtra(AppConstants.ASSESSMENT_VERSION, report_VERSION)
                            .putExtra(AppConstants.ASSESSMENT, corporateModelList.get(pos).getArea()));

                }

            });


            if (finalModelList != null) {
                Log.i("WellbeingCategory", "FInal model list "+finalModelList);
                title = new String[finalModelList.size()];
                status_title = new String[finalModelList.size()];
                for (int i = 0; i < finalModelList.size(); i++) {
                    Log.i("WellbeingCategory", "FInal model list "+finalModelList.get(i));
                    title[i] = finalModelList.get(i).getTitle();
                    status_title[i] = finalModelList.get(i).getStatus_title();
                    Log.i("WellBeingChanges", "Title from the database is "+finalModelList.get(i).getTitle());
                    reportStatusMap.put(title[i], sub_category);
                }
            }
            else {
                if (CommonUtils.isNetworkAvailable(activity)) {
                    //TODO again call the api for the details
                }
                else {
                    Toast.makeText(activity, "Please connect to internet!!!", Toast.LENGTH_SHORT).show();
                }
            }
            db.close();

            categoryReportStatuss();


             coachViewPagerAdapter.setCoachOnClickListenerInterface(new CoachViewPagerAdapter.CoachOnClickListenerInterface() {
                 @Override
                 public void reloadCoachOnClickListener() {
                     if(CommonUtils.isNetworkAvailable(activity)) {
                         getMindGymData();
                     }
                     else {
                         coachView.showNoInternet();
                     }

                 }
             });

        }
    }

    private void addBottomDots(int currentposition) {

        dots = new TextView[3];

        int[] colorsActive = activity.getResources().getIntArray(R.array.coach_array_dot_active);
        int[] colorsInactive = activity.getResources().getIntArray(R.array.coach_array_dot_inactive);

        layoutdots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(activity);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(45);
            dots[i].setTextColor(colorsInactive[currentposition]);
            layoutdots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentposition].setTextColor(colorsActive[currentposition]);


    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


    private void putIMAGERYtoBottom(List<CoachAudioDetails> coachAdapterAudioList) {
        //Log.d("coachAudioAdapterList1", String.valueOf(coachAdapterAudioList.size()));

        CoachAudioDetails IMAGERY = coachAdapterAudioList.get(0);
        coachAdapterAudioList.remove(0);  // remove item from the list
        coachAdapterAudioList.add(coachAdapterAudioList.size(),IMAGERY); //add at 0 index of your list
        //coachAudioAdapter.notifyDataSetChanged();
        //Log.d("coachAudioAdapterList11", String.valueOf(coachAdapterAudioList.size()));

    }

    private void loadDefaultCoach() {
        Log.i("Coach", "In load default coach method");
        try {
            mindGymModel = new MindGymModel();
            dummyMindGymModelList = new ArrayList<>();
            today_date = new Date();
            dd = today_dd.format(today_date);
            mm = new SimpleDateFormat("MMMM").format(today_date);
            mm_month = new SimpleDateFormat("MMMM").parse(mm);
            mm = today_mm.format(mm_month);

            mindGymModel.setDateDifference(1);
            mindGymModel.setActual_server_day(1);
            mindGymModel.setDdDate(dd);
            mindGymModel.setMmMonth(mm);
            mindGymModel.setPlayStatus(0);
            mindGymModel.setDataSynced(0);
            dummyMindGymModelList.add(mindGymModel);
            if(CommonUtils.isNetworkAvailable(activity)) {
            //    getMindGymData();
            }

            playAudios(dummyMindGymModelList);
            coachView.hideSyncTextView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getMindGymAudioCount(final int action) {

        String parameters = "email=" + commonUtils.getUserEmail(activity);
        String version = "1";
        final String day = "7";
        final String unique = "&unique=false";
        token = commonUtils.getTokenId(activity);


        parameters = parameters.concat("&version=" + version).concat("&days=" + day).concat(unique);

        if (CommonUtils.isNetworkAvailable(activity)) {

            new ApiProvider.TrailUserAudioDetails(parameters, token, 1, new API_Response_Listener<List<MindGymModel>>() {
                @Override
                public void onComplete(List<MindGymModel> data, long request_code, String failure_code) {


                    try {

                        if (data != null && data.size() > 0) {

                            paymentStatus = data.get(0).getPaymentStatus();
                            audioCount = data.get(0).getDateDifference();
                            lastAudio = data.get(0).getLast_audio();
                            play_status = data.get(0).getPlayStatus();
                            Log.i("CoachImplementation", "Play status is "+data.get(0).getPlayStatus());
                            if(paymentStatus.equalsIgnoreCase("TRAIL"))

                            {
                                if(action==5) {

                                    if(lastAudio>=4){

                                        if(play_status==1){
                                            trailUserDayFiveFeedbackSuggestion();
                                        }
                                    }
                                }
                                else if(action==7){

                                    if(lastAudio>=5){

                                        if(play_status==1) {
                                            trailUserDaySevenFeedbackSuggestion();
                                        }
                                    }
                                }
                                else if(action==10||action==11||action==12){

                                    if(lastAudio>=6) {

                                        if(play_status==1) {
                                            //trailUserFortyPercentOffer();
                                          //  trailUserModifiedTwentyPercentOffer();

                                        }
                                    }
                                }
                                else if(action==14){

                                    if(lastAudio>=7) {

                                        if(play_status==1) {
                                            //trailUserTwentyPercentOffer();
                                        }
                                    }
                                }


                            }


                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).call();
        }

    }

    /*

    editor = activity.getSharedPreferences("HAPPY_BEING", MODE_PRIVATE).edit();
                                    editor.putBoolean(AppConstants.TRAIL_FEEDBACK, true);
                                    editor.apply();
                                    editor.commit();

    * */

    @Override
    public void trailUserDayFiveFeedbackSuggestion() {

        editor = activity.getSharedPreferences("HAPPY_BEING", MODE_PRIVATE).edit();
        editor.putBoolean(AppConstants.TRAIL_FEEDBACK, true);
        editor.apply();
        editor.commit();
        yourFeedbackMatter();
        // call feedback
    }

    @Override
    public void trailUserDaySevenFeedbackSuggestion() {

        editor = activity.getSharedPreferences("HAPPY_BEING", MODE_PRIVATE).edit();
        editor.putBoolean(AppConstants.TRAIL_FEEDBACK, true);
        editor.apply();
        editor.commit();
        yourFeedbackMatter();
        // call feedback
    }

    @Override
    public void trailUserModifiedTwentyPercentOffer() {
    /*    editor = activity.getSharedPreferences("HAPPY_BEING", MODE_PRIVATE).edit();
        editor.putBoolean(AppConstants.TRAIL_20_OFFER, true);
        editor.apply();
        editor.commit();

        activity.startActivity(new Intent(activity, SubscriptionActivity.class));
*/
    }

    private void yourFeedbackMatter() {

        final Dialog feedback_dialog = new Dialog(activity);
        feedback_dialog.setContentView(R.layout.your_feedback_popup);
        feedback_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        feedback_dialog.setTitle("Happy Being");
        f_yes_button = (Button) feedback_dialog.findViewById(R.id.yes_button);
        f_not_really_button = (Button) feedback_dialog.findViewById(R.id.not_really);

        f_yes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Suggestion Feedback
                suggestionFeedback();
                feedback_dialog.dismiss();
            }
        });

        f_not_really_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Improve Suggestion
                improveFeedback();
                feedback_dialog.dismiss();
            }
        });

        feedback_dialog.show();

    }

    private void suggestionFeedback() {

        final Dialog suggestion_dialog = new Dialog(activity);
        suggestion_dialog.setContentView(R.layout.suggestion_feedback);
        suggestion_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        suggestion_dialog.setTitle("Happy Being");
        no_thanks = (Button) suggestion_dialog.findViewById(R.id.no_thanks);
        ok_sure = (Button) suggestion_dialog.findViewById(R.id.ok_sure);

        no_thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set Notification
                suggestion_dialog.dismiss();
            }
        });

        ok_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the play store
                CommonUtils.playStore(activity);
                ///suggestion_dialog.dismiss();
            }
        });

        suggestion_dialog.show();

    }

    private void improveFeedback() {

        final Dialog improve_dialog = new Dialog(activity);
        improve_dialog.setContentView(R.layout.improve_feedback);
        improve_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        improve_dialog.setTitle("Happy Being");
        no_thanks = (Button) improve_dialog.findViewById(R.id.no_thanks);
        ok_sure = (Button) improve_dialog.findViewById(R.id.ok_sure);

        no_thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish
                improve_dialog.dismiss();
            }
        });

        ok_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Feedback Popup Windows
                feedbackPopWindows();
                improve_dialog.dismiss();
            }
        });

        improve_dialog.show();

    }

    private void feedbackPopWindows() {

        final Dialog rate_dialog = new Dialog(activity);
        rate_dialog.setContentView(R.layout.rate_us_popup);
        rate_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rate_dialog.setTitle(activity.getResources().getString(R.string.report_an_issue));
        save_button = (Button) rate_dialog.findViewById(R.id.save_button);
        et_feedback = (EditText) rate_dialog.findViewById(R.id.et_feedback);
        et_feedback.setHint(activity.getResources().getString(R.string.help_us));

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkValidation()) {
                    // saveFeedback(); save to db
                    sendFeedbackInMailClient(); // send to email client
                    rate_dialog.dismiss();
                }

            }
        });


        rate_dialog.show();

    }

    private boolean checkValidation() {

        boolean returnValue = true;

        // feedbackText validation
        if (!CommonUtils.hasText(et_feedback, "Please fill the field"))
            returnValue = false;
        return returnValue;
    }

    private void sendFeedbackInMailClient() {
        SharedPreferences prefs;
        prefs = activity.getSharedPreferences("HAPPY_BEING", MODE_PRIVATE);
        String userEmail = prefs.getString("user_email", "");
        Intent email = new Intent(Intent.ACTION_SENDTO);

        try {
            email.setData(Uri.parse("mailto:"));
            email.setPackage("com.google.android.gm");
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"help@nsmiles.com"});
            email.putExtra(Intent.EXTRA_SUBJECT, "Feedback - " + userEmail);
            email.putExtra(Intent.EXTRA_TEXT, et_feedback.getText().toString());
            //need this to prompts email client only
            email.setType("message/rfc822");
            activity.startActivityForResult(Intent.createChooser(email, "Send email"), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdateNeeded(String updateUrl) {
        coachView.showUpgrade();
    }


    @Override
    public void categoryReportStatus() {


    }

    @Override
    public void categoryReportStatuss() {

        coachView.showProgressBar();


        final Subscription subscription = service.getWellBeingCategoryStatus(commonUtils.getTokenId(activity),
                CATEGORY_API_URL, new Service.WellBeingCategoryStatusCheckCallBack() {
                    @Override
                    public void onSuccess(WellBeingCategoryStatusModel wellBeingCategoryStatusModel) {
                        if (wellBeingCategoryStatusModel != null && wellBeingCategoryStatusModel.getResult().getSuccess() != null) {

                            coachView.hideProgressBar();


                            wellBeingCategory = wellBeingCategoryStatusModel.getResult().getSuccess().getCategory();
                            for (int i = 0; i < wellBeingCategory.size(); i++) {

                                for (int j = 0; j < status_title.length; j++) {
//
                                    if (wellBeingCategory.get(i).getName().equals(status_title[j])) {

                                        finalModelList.get(j).setAssessmentStatus(wellBeingCategory.get(i).getStatus());
                                        wellBeingAdapter.notifyItemChanged(j);
                                        break;
                                    }
                                }
                            }


                        }
                    }


                    @Override
                    public void onError(NetworkError networkError) {

                        coachView.hideProgressBar();
                    }
                });

        compositeSubscription.add(subscription);

    }

    @Override
    public void showCategoryReport(ArrayList<String> category) {

    }

    @Override
    public void checkAAssessmentCompletedStatus() {

//        coachView.showProgressBar();
//        Log.i("WellBeingChanges", "Completed status API"+COMPLETED_STATUS_API);
//        Subscription subscription = service.getWellBeingAllCompletedStatus(AppConstants.BEARER +  new PreferenceManager(activity).get(AppConstants.HB_USER_TOKEN,""),
//                COMPLETED_STATUS_API, new Service.WellBeingAllCompeltedCallBack() {
//                    @Override
//                    public void onSuccess(AssessmentCompletedStatus assessmentCompletedStatus) {
//
//                        if (assessmentCompletedStatus.getSuccess().getPregnancywellbeingExists() != null
//                                && assessmentCompletedStatus.getSuccess().getPregnancywellbeingExists() == 1) {
//                            IS_ALL_ASSESSMENTS_COMPLETED = true;
//
//                        } else if (assessmentCompletedStatus.getSuccess().getWellbeingExists() != null
//                                && assessmentCompletedStatus.getSuccess().getWellbeingExists() == 1) {
//                            IS_ALL_ASSESSMENTS_COMPLETED = true;
//                        }
//                        else if(assessmentCompletedStatus.getSuccess().getCorporatewellbeingExists() != null
//                                && assessmentCompletedStatus.getSuccess().getCorporatewellbeingExists() == 1){
//                            IS_ALL_ASSESSMENTS_COMPLETED = true;
//                        }
//                        else {
//                            //disable touch on Summary report Button
//                            //  view.disableCombainedReportButton();
//                        }
//                        Log.i("Wellbeing", "Is all completed is "+IS_ALL_ASSESSMENTS_COMPLETED);
//                        if (IS_ALL_ASSESSMENTS_COMPLETED) {
//                            //  view.showCombinedReportButton();
//                        }
//                        coachView.hideProgressBar();
//                    }
//
//                    @Override
//                    public void onError(NetworkError networkError) {
//
//                        coachView.hideProgressBar();
//                    }
//                });
//
//        compositeSubscription.add(subscription);

    }

    @Override
    public void viewCombinedReport() {

    }

    @Override
    public void viewDetailReport() {

    }

    @Override
    public void initCorporateData() {

    }

    @Override
    public void callAdapter() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void checkAssessmentPaymentStatus(WellBeingCategoryImplementation.WellBeingCallBack callBack) {

    }

}