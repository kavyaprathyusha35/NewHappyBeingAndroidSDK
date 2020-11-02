package com.nsmiles.happybeingsdklib.Reports.DetailReport;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.RadarChart;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.PregnancyWellBeingView;
import com.nsmiles.happybeingsdklib.dagger.application.BaseApplication;
import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
import com.nsmiles.happybeingsdklib.network.Service;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailReportActivity extends AppCompatActivity implements PregnancyWellBeingView {

    private RadarChart radar_chart;
    private Activity activity;
   // private RecyclerView report_recycler_view;
    private ExpandableListView expandableListView;
    private ScrollView scrollView;
    private PregnancyWellBeingView view;
    private DetailReportImplementation detailReportImplementation;
    private Intent myIntent;
    LinearLayout appBar;
    private View progressOverlay;
    private LinearLayout introduction_layout, immediate_actions_layout;
    private TextView report_title_text, happiness_index_text, immediate_actions_layout_heading;

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
        Log.i("DetailReportActivity", "In On create of report activity*****");
        renderView();
    }

    private void renderView() {


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
        activity = DetailReportActivity.this;
        view = DetailReportActivity.this;
        radar_chart = (RadarChart) findViewById(R.id.radar_chart);
        expandableListView = (ExpandableListView) findViewById(R.id.wellbeing_expandableListView);
        expandableListView.setFocusable(false);
        scrollView = findViewById(R.id.scroll_view);
        introduction_layout = findViewById(R.id.introduction_layout);
        report_title_text = findViewById(R.id.report_title_text);
        happiness_index_text = findViewById(R.id.happiness_index_text);
        immediate_actions_layout = findViewById(R.id.immediate_actions_layout);
        immediate_actions_layout_heading = findViewById(R.id.immediate_actions_layout_heading);
        //report_recycler_view = (RecyclerView) findViewById(R.id.report_recycler_view);
        expandableListView.setIndicatorBounds(expandableListView.getRight()- 100, expandableListView.getWidth());
        detailReportImplementation = new DetailReportImplementation(activity, dataManager, service,view,
                radar_chart, expandableListView, scrollView,  myIntent);
        detailReportImplementation.initilizeRadarChartComponent();
        detailReportImplementation.getCorporateWellbeingReport();            }

        Intent intent = getIntent();
        /*if (intent.hasExtra(AppConstants.HIDE_RADAR)) {
            introduction_layout.setVisibility(View.GONE);
            report_title_text.setVisibility(View.VISIBLE);
        }
        else {
            introduction_layout.setVisibility(View.VISIBLE);
            report_title_text.setVisibility(View.GONE);
        }
        */

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
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                view.setBackgroundColor(Color.parseColor("#B3B3B3"));
                immediate_actions_layout.addView(view);
            }


        }
    }


    public void internetRequired() {
        dataManager.alert(this, getResources().getString(R.string.no_internet_connection),
                getResources().getString(R.string.no_network),
                "OK");

    }
    public void showProgress() {
        dataManager.progress(progressOverlay, View.VISIBLE, 0.4f, 200);
    }

    public void hideProgress() {
        dataManager.progress(progressOverlay, View.GONE, 0f, 0);
    }

    public void showErrorMessage(String errorMessage) {

    }

    public void showSuccessMessage(String successMessage) {

    }

}
