package com.nsmiles.happybeingsdklib.Models;

public interface WellBeingAssessmentView {

        void showProgress();
        void hideProgress();

        void showNextButton();

        void hideNextButton();

        void showPreviousButton();
        void hidePreviousButton();
        void showFooterLayout();
        void hideFooterLayout();
        void assignProgressBar(int max);
        void updateProgressBar(int percentage);
        void networkRequired();
        void finishActivity();
        void stopBackgroundAnimation();
        void resumeBackgroundAnimation();
    }
