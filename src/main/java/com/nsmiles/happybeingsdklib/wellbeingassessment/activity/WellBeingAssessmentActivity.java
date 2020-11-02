package com.nsmiles.happybeingsdklib.wellbeingassessment.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.nsmiles.happybeingsdklib.Models.WellBeingAssessmentView;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.dagger.application.BaseApplication;
import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
import com.nsmiles.happybeingsdklib.network.Service;
import com.nsmiles.happybeingsdklib.swipebutton.OnStateChangeListener;
import com.nsmiles.happybeingsdklib.swipebutton.SwipeButton;
import com.nsmiles.happybeingsdklib.wellbeingassessment.implementation.WellBeingAssessmentImplementation;
import com.q42.android.scrollingimageview.ScrollingImageView;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class WellBeingAssessmentActivity
        extends AppCompatActivity
        implements WellBeingAssessmentView,
        View.OnClickListener {


    private Activity activity;
    private WellBeingAssessmentView view;
    private WellBeingAssessmentImplementation corporateAssessmentImplementation;
    private View progressOverlay;
    private RecyclerView recycle_view;
    LinearLayout footer_layout;
    SwipeButton generate_report;
    Intent myIntent;
    ProgressBar progress;
    private Button previous_btn;
    private ScrollingImageView scrollingImageView;
    @Inject
    DataManager dataManager;

    @Inject
    Service service;
    private Button next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nsmiles_assessment);
        activity = WellBeingAssessmentActivity.this;
        renderView();


    }

    private void renderView() {
        progressOverlay = findViewById(R.id.progress_overlay);
        progress = (ProgressBar) findViewById(R.id.progress);
        recycle_view = (RecyclerView) findViewById(R.id.recycle_view);
        footer_layout = (LinearLayout) findViewById(R.id.footer_layout);
        generate_report = (SwipeButton) findViewById(R.id.generate_report);
        previous_btn = (Button) findViewById(R.id.previous_btn);
        next_btn = (Button) findViewById(R.id.next_btn);
        scrollingImageView = findViewById(R.id.scrolling_background);


        previous_btn.setOnClickListener(this);
        next_btn.setOnClickListener(this);

        //  generate_report.setOnClickListener(this);
        myIntent = getIntent();

        ((BaseApplication) getApplication()).getmApplicationApiComponent().inject(this);

        view = WellBeingAssessmentActivity.this;
        corporateAssessmentImplementation = new WellBeingAssessmentImplementation(activity, recycle_view, myIntent, dataManager, service, view);
        corporateAssessmentImplementation.initAssessmentData();


        generate_report.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {

                if(active){

                    corporateAssessmentImplementation.validateAssessmentAnswer();
                }
            }
        });
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
    public void showNextButton() {
        next_btn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNextButton() {
        next_btn.setVisibility(View.GONE);
    }

    @Override
    public void showPreviousButton() {
        previous_btn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePreviousButton() {
        previous_btn.setVisibility(View.GONE);
    }

    @Override
    public void showFooterLayout() {
        generate_report.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFooterLayout() {
        generate_report.setVisibility(View.GONE);
    }



    @Override
    public void assignProgressBar(int max) {
        progress.setMax(max);
    }

    @Override
    public void updateProgressBar(int percentage) {
        progress.setProgress(percentage);
    }

    @Override
    public void networkRequired() {

        dataManager.alert(activity, getResources().getString(R.string.network_message), getResources().getString(R.string.network_message), "Ok");

    }
    @Override
    public void stopBackgroundAnimation() {
        scrollingImageView.stop();
    }

    @Override
    public void resumeBackgroundAnimation() {
        scrollingImageView.start();
    }

    @Override
    public void finishActivity() {
        WellBeingAssessmentActivity.this.finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.generate_report) {// corporateAssessmentImplementation.validateAssessmentAnswer();
        } else if (id == R.id.previous_btn) {
            corporateAssessmentImplementation.previousButtonClickListener();
        } else if (id == R.id.next_btn) {
            corporateAssessmentImplementation.nextButtonClickListener();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppConstants.POST_ANSWER_REQUEST && resultCode==RESULT_OK){
            finish();
        }
    }
}
