package com.nsmiles.happybeingsdklib.gratitude.view;

/**
 * Created by nSmiles on 11/25/2017.
 */

public interface GratitudeDailyView {

    void setGratitudeData(int loadImage, String content);
    void showDeleteIcon();
    void changeBtnName();

    void showEditHideTextView(String gratitude_data);
}
