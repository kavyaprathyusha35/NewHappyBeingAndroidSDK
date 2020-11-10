package com.nsmiles.happybeingsdklib.mycoachfragment.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.UI.HomeScreenActivity;
import com.nsmiles.happybeingsdklib.UI.WebViewActivity;

import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.SdkPreferenceManager;
import com.nsmiles.happybeingsdklib.dagger.application.BaseApplication;
import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
import com.nsmiles.happybeingsdklib.mycoachfragment.implementation.CoachImplementation;
import com.nsmiles.happybeingsdklib.mycoachfragment.view.CoachView;
import com.nsmiles.happybeingsdklib.network.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import javax.inject.Inject;


public class CoachGratitudeFragment extends Fragment implements CoachView, View.OnClickListener {

    TextView info_text,tv_day_workout;
    TextView descriptionOfCoach,getpremium,validtill;
    Activity activity;
    CommonUtils commonUtils;
    CoachView coachView;
    CoachImplementation coachImplementation;
    RecyclerView past_audio_recycle_view;
    private String dateDifference = "";
    //TextView nav_title_tv;
    TextView sync_tv;
    Button upgrade,get_started;
    TextView upgrade_tv;
    ScrollView scroll_view;
    LinearLayout coach_layout;
    Button not_now_btn;
    LinearLayout ll_first_time,ll_main,ll_tv_3,ll_tv_2,ll_btn,ll_tv_4,personal_text_layout;
    SdkPreferenceManager preferenceManager;
    private boolean is_Login = false;
    boolean isPaid = false;
    public static final String PREFS_NAME = "MyPrefsFile";

    LinearLayout layoutDots;

    private TextView coach_date_tv;
    private TextView coach_month_tv;
    private String wishMessage;
    private SdkPreferenceManager sharedPreferences;
    private boolean isNewDayToday;
    private String isNewDayId;
    private Animation animationFadeIn;
    private TextView tv_user_message;
    private String name;
    ImageView wishicon;
    SharedPreferences settings;
    ViewPager view_pager;
    private String PAYMENT_CATEGORY;
    @Inject
    Service service;
    @Inject
    DataManager dataManager;
    String expiry_date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coach_gratitude, container, false);
        activity = getActivity();

        ((BaseApplication)getActivity().getApplication()).getmApplicationApiComponent().inject(this);
        animationFadeIn = AnimationUtils.loadAnimation(activity, R.anim.goal_fade_in);
        tv_user_message=view.findViewById(R.id.tv_user_message);
        scroll_view = (ScrollView) view.findViewById(R.id.scroll_view);
        coach_layout = (LinearLayout) view.findViewById(R.id.coach_layout);
        tv_day_workout = view.findViewById(R.id.tv_day_workout);
        info_text = view.findViewById(R.id.info_text);
        wishicon = view.findViewById(R.id.wish_icon);
        descriptionOfCoach = view.findViewById(R.id.descriptionOfCoach);
        getpremium = view.findViewById(R.id.getpremium);
        validtill = view.findViewById(R.id.validtill);
        coach_date_tv = view.findViewById(R.id.coach_date_tv);
        coach_month_tv = view.findViewById(R.id.coach_month_tv);
        ll_first_time=view.findViewById(R.id.ll_first_time);
        ll_first_time.startAnimation(animationFadeIn);
        ll_main=view.findViewById(R.id.ll_main);
        ll_tv_2=view.findViewById(R.id.ll_tv_2);
        ll_tv_3=view.findViewById(R.id.ll_tv_3);
        ll_btn=view.findViewById(R.id.ll_btn);
        ll_tv_4=view.findViewById(R.id.ll_tv_4);
        layoutDots=view.findViewById(R.id.layoutDots);
        personal_text_layout=view.findViewById(R.id.personal_text_layout);
        preferenceManager = new SdkPreferenceManager(getActivity());


        past_audio_recycle_view = (RecyclerView) view.findViewById(R.id.past_audio_recycle_view);
        view_pager = (ViewPager) view.findViewById(R.id.view_pager);
        descriptionOfCoach.setOnClickListener(this);
        getpremium.setOnClickListener(this);
        commonUtils = new CommonUtils();
        assert activity != null;
        coachView = CoachGratitudeFragment.this;
        coachImplementation = new CoachImplementation(service,dataManager,is_Login,tv_day_workout,coachView, activity, past_audio_recycle_view,view_pager,layoutDots);
        sharedPreferences = new SdkPreferenceManager(getActivity());
        Calendar rightNow = Calendar.getInstance();
        int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
        Date date1 = Calendar.getInstance().getTime();
        String datedd = (String) android.text.format.DateFormat.format("dd", date1);
        String monthString  = (String) DateFormat.format("MMM",  date1); // Jun
        name = new SdkPreferenceManager(activity).get(AppConstants.SDK_NAME,"");
        expiry_date=commonUtils.getExpiryDate(activity);
        coach_date_tv.setText(datedd);
        coach_month_tv.setText(monthString);
        validtill.setText("valid till <"+expiry_date+">");





        coachImplementation.categoryReportStatuss();

        if (currentHourIn24Format < 12) {
            tv_user_message.setText("Good morning \n"+ name);
            wishicon.setBackground(getResources().getDrawable(R.drawable.good_morning));
        } else if (currentHourIn24Format < 18) {
            tv_user_message.setText("Good afternoon \n" + name);
            wishicon.setBackground(getResources().getDrawable(R.drawable.goods_afternoon));
        } else if (currentHourIn24Format < 24) {
            tv_user_message.setText("Good evening \n"+ name);
            wishicon.setBackground(getResources().getDrawable(R.drawable.good_evening));
        }

        name = new SdkPreferenceManager(activity).get(AppConstants.SDK_NAME,"");
        trimSingleName();
        info_text.setText(wishMessage+" "+name+"!");


         settings = getActivity().getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean(AppConstants.FIRSTTIME_ANIMATION, true)) {
            personal_text_layout.setVisibility(View.VISIBLE);
            past_audio_recycle_view.setVisibility(View.VISIBLE);
            descriptionOfCoach.setVisibility(View.VISIBLE);

        }  else {

           // dd_mm_layout.setVisibility(View.VISIBLE);
            personal_text_layout.setVisibility(View.VISIBLE);
            past_audio_recycle_view.setVisibility(View.VISIBLE);
            descriptionOfCoach.setVisibility(View.VISIBLE);


        }

        setDateTexts();
        isNewDay();
        return view;
    }



////    private void showLayoutfirst() {
////
////        dd_mm_layout.postDelayed(new Runnable() {
////            public void run() {
////                dd_mm_layout.setVisibility(View.VISIBLE);
////                dd_mm_layout.startAnimation(animationFadeIn);
////                ll_tv_4.setAnimation(null);
////                personal_text_layout.setVisibility(View.VISIBLE);
////                personal_text_layout.startAnimation(animationFadeIn);
////                showLayoutthird();
////            }
////        }, 2500);
////
////
////
////    }
//
//    private void showLayoutthird() {
//
//        past_audio_recycle_view.postDelayed(new Runnable() {
//            public void run() {
//                past_audio_recycle_view.setVisibility(View.VISIBLE);
//                past_audio_recycle_view.startAnimation(animationFadeIn);
//                personal_text_layout.setAnimation(null);
//           //     dd_mm_layout.setAnimation(null);
//                showLayoutfourth();
//            }
//        }, 2500);
//
//
//    }
//
//    private void showLayoutfourth() {
//
//        descriptionOfCoach.postDelayed(new Runnable() {
//            public void run() {
//                descriptionOfCoach.setVisibility(View.VISIBLE);
//                descriptionOfCoach.startAnimation(animationFadeIn);
//                past_audio_recycle_view.setAnimation(null);
//
//
//            }
//        }, 2500);
//
//        settings.edit().putBoolean(AppConstants.FIRSTTIME_ANIMATION, false).commit();
//    }

    private void trimSingleName() {
        String[] arr = name.split("\\s");

        String firstName = "";
        firstName=arr[0];
        for (int i = 0; i < arr.length; i++) {
            String tempStr = arr[i];
            if (!tempStr.contains(".")) {
                if (tempStr.length() < 3 && firstName.equals("")) {
                    firstName = tempStr;
                } else if (tempStr.length() >= 3 && firstName.length() < 3) {
                    firstName = tempStr;
                }
            }
        };

        name= firstName;
    }

    private boolean isNewDay() {
        String IS_NEW_DAY = sharedPreferences.get(AppConstants.IS_NEW_DAY,"");

        if (IS_NEW_DAY != null) {
            isNewDayToday=IS_NEW_DAY.equals(todayDate());
        }
        return !isNewDayToday;
    }

    private String setDateTexts() {
        //Log.d("setDateTexts", String.valueOf(coachImplementation.getDateDifference()));
        //subscription_month.setText(coachImplementation.getDateDifference());
        dateDifference = sharedPreferences.get(AppConstants.DATE_DIFFERENCE, "1");
        Log.d("setDateTexts", dateDifference);
        //subscription_month.setText(dateDifference);
        //tv_user_message.setText(name+" here is your day " + dateDifference + " plan.");
        //tv_day_workout.setText("Here is your day "+dateDifference+" workout.");
        isNewDayId="-day"+dateDifference+"-";
        return dateDifference;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        coachImplementation.hideProgress();
        setDateTexts();
/*        if (dataManager.get(AppConstants.ROLE, "").equalsIgnoreCase(AppConstants.PROFILE_LOOKING_JOB)) {


            if(dataManager.get(AppConstants.CAREER_START, "").length()>0){
                coachImplementation.defaultCoachData();
                //coachImplementation.getMindGymData();
                coachImplementation.loadOfflineCoachData();
                coachImplementation.pushCoachData();
                scroll_view.setVisibility(View.GONE);
                coach_layout.setVisibility(View.VISIBLE);
            }
            else {
                scroll_view.setVisibility(View.VISIBLE);
                coach_layout.setVisibility(View.GONE);
            }



        } else {
            Log.i("CoachFragment", "In else loop of profile");

        }*/

        coachImplementation.defaultCoachData();
        //coachImplementation.getMindGymData();
        coachImplementation.loadOfflineCoachData();
        coachImplementation.pushCoachData();
        //scroll_view.setVisibility(View.GONE);
        coach_layout.setVisibility(View.VISIBLE);


    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void showProgressBar() {

      //  progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
       // progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSyncTextView() {

    }

    @Override
    public void hideSyncTextView() {

    }

    @Override
    public void showUpgrade() {

    }

    @Override
    public void hideUpgrade() {

    }


    @Override
    public void showNoInternet() {
        CommonUtils.alertDialog(getActivity(), getResources().getString(R.string.network_header)
                , getResources().getString(R.string.network_message), "Ok");
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.descriptionOfCoach) {
            startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("PAGE_URL", "https://myhappybeing.com/mycoach"));
        } else if(id==R.id.getpremium){

//            Intent intent = new Intent(getActivity(), SubscriptionActivity.class);
//            startActivity(intent);

        }
    }
    private String todayDate(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        return df.format(c);
    }


}