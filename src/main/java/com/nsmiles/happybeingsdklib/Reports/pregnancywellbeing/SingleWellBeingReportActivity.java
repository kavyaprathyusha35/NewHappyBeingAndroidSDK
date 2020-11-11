package com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams;

import com.github.mikephil.charting.charts.RadarChart;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.dagger.application.BaseApplication;
import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
import com.nsmiles.happybeingsdklib.network.Service;


import java.util.List;

import javax.inject.Inject;

public class SingleWellBeingReportActivity extends AppCompatActivity implements PregnancyWellBeingView {

    private RadarChart radar_chart;
    private Activity activity;
   // private RecyclerView report_recycler_view;
    private ExpandableListView expandableListView;
    private ScrollView scrollView;
    private PregnancyWellBeingView view;
    private PregnancyWellBeingImplementation generalWellBeingImplementation;
    private Intent myIntent;
    LinearLayout appBar;
    private View progressOverlay;
    private LinearLayout introduction_layout, immediate_actions_layout;
    private TextView report_title_text, happiness_index_text, immediate_actions_layout_heading, detail_report_text, detail_report_explanation_text;

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsing_toolbar;
    @Inject
    DataManager dataManager;

    @Inject
    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_well_being_report);
        ((BaseApplication)getApplication()).getmApplicationApiComponent().inject(this);
        myIntent = getIntent();
        Log.i("PregnencyReport", "In On create of report activity*****");
        Log.i("DJJJDJJD",dataManager.get(AppConstants.ROLE, ""));
        renderView();
    }

    private void renderView(){


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        collapsing_toolbar =  (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        if (dataManager.get(AppConstants.ROLE, "").equalsIgnoreCase(AppConstants.PROFILE_HOME_MAKER)) {
//            if (dataManager.get(AppConstants.HB_SECONDARY_VALUE, "").equalsIgnoreCase("pregnant") ||
//                    dataManager.get(AppConstants.HB_SECONDARY_VALUE, "").equalsIgnoreCase("conceive")) {
//                collapsing_toolbar.setTitle("Pregnancy wellbeing");
//            }
//            else {
//                collapsing_toolbar.setTitle("Personal wellbeing");
//            }
//
//        }
//        else if (dataManager.get(AppConstants.HB_SECONDARY_VALUE, "").equalsIgnoreCase("pregnant") ||
//                dataManager.get(AppConstants.HB_SECONDARY_VALUE, "").equalsIgnoreCase("conceive")) {
//            collapsing_toolbar.setTitle("Pregnancy wellbeing");
//        }
//        else {
//            collapsing_toolbar.setTitle("Corporate wellbeing");
//        }
//        collapsing_toolbar.setContentScrimColor(getResources().getColor(R.color.h_b_color));

        appBar = (LinearLayout) findViewById(R.id.appBar);
        activity = SingleWellBeingReportActivity.this;
        view = SingleWellBeingReportActivity.this;
        radar_chart = (RadarChart) findViewById(R.id.radar_chart);
        expandableListView = (ExpandableListView) findViewById(R.id.wellbeing_expandableListView);
        expandableListView.setFocusable(false);
        introduction_layout = findViewById(R.id.introduction_layout);
        detail_report_explanation_text = findViewById(R.id.detail_report_textview_explanation);
        detail_report_text = findViewById(R.id.detail_report_text);

        Log.i("AAAAAAAAAAAAA",dataManager.get(AppConstants.ROLE, ""));

        report_title_text = findViewById(R.id.report_title_text);
        happiness_index_text = findViewById(R.id.happiness_index_text);
        immediate_actions_layout = findViewById(R.id.immediate_actions_layout);
        immediate_actions_layout_heading = findViewById(R.id.immediate_actions_layout_heading);
        //report_recycler_view = (RecyclerView) findViewById(R.id.report_recycler_view);
        expandableListView.setIndicatorBounds(expandableListView.getRight()- 100, expandableListView.getWidth());
        generalWellBeingImplementation = new PregnancyWellBeingImplementation(activity, dataManager, service,view,
                radar_chart, expandableListView,  myIntent);
        generalWellBeingImplementation.initilizeRadarChartComponent();

//        if (dataManager.get(AppConstants.ROLE, "").equalsIgnoreCase(AppConstants.PROFILE_STAFF)) {
//
//            generalWellBeingImplementation.getGeneralWellBeingCategoryReport();
//        }
//        else if (dataManager.get(AppConstants.ROLE, "").equalsIgnoreCase(AppConstants.PROFILE_SENIOR_CITIZEN)) {
//            generalWellBeingImplementation.getGeneralWellBeingCategoryReport();
//        }
//        else if (dataManager.get(AppConstants.ROLE, "").equalsIgnoreCase(AppConstants.PROFILE_EMPLOYED)) {
//            Log.i("WellbingCategory", "Role is "+dataManager.get(AppConstants.HB_SECONDARY_VALUE, "").equalsIgnoreCase("pregnant"));
//
//            if (dataManager.get(AppConstants.HB_SECONDARY_VALUE, "").equalsIgnoreCase("pregnant") ||
//                    dataManager.get(AppConstants.HB_SECONDARY_VALUE, "").equalsIgnoreCase("conceive")) {
//                generalWellBeingImplementation.getGeneralWellBeingCategoryReport();
//                //PREGNANCY_WELLBEING;
//            } else {
//                generalWellBeingImplementation.getCorporateWellbeingReport();            }
//
//        }
//        else if (dataManager.get(AppConstants.ROLE, "").equalsIgnoreCase(AppConstants.PROFILE_HOME_MAKER)) {
//
//            if (dataManager.get(AppConstants.HB_SECONDARY_VALUE, "").equalsIgnoreCase("pregnant") ||
//                    dataManager.get(AppConstants.HB_SECONDARY_VALUE, "").equalsIgnoreCase("conceive")) {
//                generalWellBeingImplementation.getGeneralWellBeingCategoryReport();
//                //PREGNANCY_WELLBEING;
//            } else {
//                generalWellBeingImplementation.getGeneralWellBeingCategoryReport();
//            }
//        }
        generalWellBeingImplementation.getCorporateWellbeingReport();
        /*if (dataManager.get(AppConstants.ROLE, "").equalsIgnoreCase(AppConstants.PROFILE_EMPLOYED) &&(!dataManager.get(AppConstants.HB_SECONDARY_VALUE, "").equalsIgnoreCase("pregnant") ||
                !dataManager.get(AppConstants.HB_SECONDARY_VALUE, "").equalsIgnoreCase("conceive"))) {
            // Call for corporate wellbeing report
            generalWellBeingImplementation.getCorporateWellbeingReport();
            }
        else {
            generalWellBeingImplementation.getGeneralWellBeingCategoryReport();
        }*/

        progressOverlay = findViewById(R.id.progress_overlay);
        Intent intent = getIntent();
        if (intent.hasExtra(AppConstants.HIDE_RADAR)) {
            introduction_layout.setVisibility(View.GONE);
            report_title_text.setVisibility(View.VISIBLE);
            expandableListView.setVisibility(View.VISIBLE);
        }
        else {
            introduction_layout.setVisibility(View.VISIBLE);
            report_title_text.setVisibility(View.GONE);
            expandableListView.setVisibility(View.GONE);
        }
//ADDING THIS AS WE HAVE TO SHOW SUMMARY REPORT WITHOUT DETAIL REPORt
        detail_report_text.setVisibility(View.GONE);
        detail_report_explanation_text.setVisibility(View.GONE);

        //expandableListView.setVisibility(View.GONE);
    }

    @Override
    public void showRadarChart() {

        appBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setHappinessIndexText(Double indexText) {
        Log.i("WellbingCategory", "IN happinessIndex method");
        int index = indexText.intValue();
        happiness_index_text.setText(String.valueOf(index)+"%");
    }

    @Override
    public void setActionsText(List<String> actionsList) {
        immediate_actions_layout.removeAllViews();
        if (actionsList.size() > 0) {
            immediate_actions_layout_heading.setVisibility(View.VISIBLE);
            TextView strengthHeading = new TextView(activity);
            strengthHeading.setText("IMMEDIATE ACTIONS");
            strengthHeading.setTextSize(22);
            strengthHeading.setPadding(10, 15, 10, 15);
            strengthHeading.setGravity(Gravity.CENTER);
            strengthHeading.setTextColor(Color.BLACK);
            immediate_actions_layout.addView(strengthHeading);

            for (int i = 0; i < actionsList.size(); i++) {
                TextView textView = new TextView(activity);
                textView.setText(actionsList.get(i));
                textView.setPadding(5, 10, 5, 10);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(18);

                immediate_actions_layout.addView(textView);
                View view = new View(activity);
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,2));
                view.setBackgroundColor(Color.parseColor("#B3B3B3"));
                immediate_actions_layout.addView(view);
            }
            Log.d("buttonAdded","pregnancy");

            TextView textView = new TextView(activity);
            textView.setText(getString(R.string.Get_my_complete));
            textView.setPadding(5, 10, 5, 10);
            textView.setTextColor(Color.GRAY);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);
            setMarginToText(textView);
            immediate_actions_layout.addView(textView);

            Button subscribeBtn = new Button(activity);
            subscribeBtn.setText(getString(R.string.get_now));
            subscribeBtn.setTextSize(16);
            subscribeBtn.setBackground(activity.getDrawable(R.drawable.button_radius));
            subscribeBtn.setPadding(5, 5, 5, 5);
            subscribeBtn.setGravity(Gravity.CENTER);
            subscribeBtn.setTextColor(Color.WHITE);
            subscribeBtn.setTypeface(Typeface.DEFAULT_BOLD);
            subscribeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (false) {
                        //implementation.viewDetailReport();
                    }
                    else {
//                        startActivityForResult(new Intent(activity, SubscriptionActivity.class)
//                                        .putExtra(AppConstants.SKU_ID, AppConstants.ITEM_SKU_OVERCOME_EXAM_PRESSURE)
//                                        .putExtra(AppConstants.SERVER_SKU_ID, AppConstants.ITEM_SKU_OVERCOME_EXAM_PRESSURE)
//                                , AppConstants.IN_APP_PAYMENT);

                    }
                }
            });

            immediate_actions_layout.addView(subscribeBtn);
        }
    }

    private void setMarginToText(TextView textView) {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        params.setMargins(5,20,5,5);
        textView.setLayoutParams(params);
    }

    @Override
    public void internetRequired() {
        dataManager.alert(this, getResources().getString(R.string.no_internet_connection),
                getResources().getString(R.string.no_network),
                "OK");

    }

    @Override
    public void showProgress() {
        dataManager.progress(progressOverlay, View.VISIBLE, 0.4f, 200);
    }

    @Override
    public void hideProgress() {
        dataManager.progress(progressOverlay, View.GONE, 0f, 0);
    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }

    @Override
    public void showSuccessMessage(String successMessage) {

    }
}
