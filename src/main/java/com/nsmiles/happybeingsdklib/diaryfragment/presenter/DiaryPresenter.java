package com.nsmiles.happybeingsdklib.diaryfragment.presenter;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by Sukumar on 1/19/2018.
 */

public interface DiaryPresenter {

    void loadViewPagerData();
    void displayKitkatIcon();
    void onPause();
    void setupViewPager(ViewPager viewPager);
    void addGratitudeData();
    void calenderEntry();
    void editDiaryData();
    void deleteDiaryData();

}
