package com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.implementation;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.diaryfragment.fragment.DiaryFragment;
import com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.presenter.GratitudePresenter;
import com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.view.GratitudeView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class GratitudeImplementation implements GratitudePresenter {


    private GratitudeView gratitudeView;
    private Activity activity;
    private CommonUtils commonUtils;
    private GratitudePagerAdapter gratitudePagerAdapter;
    private Context context;
    private FragmentManager fragmentManager;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Bundle bundle;
    private String go_dairy;


    public GratitudeImplementation(Bundle bundle, Activity activity, Context context, GratitudeView gratitudeView, FragmentManager fragmentManager, TabLayout tabLayout, ViewPager viewPager) {

        this.activity = activity;
        this.gratitudeView = gratitudeView;
        this.commonUtils = new CommonUtils();
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.tabLayout = tabLayout;
        this.viewPager = viewPager;
        this.bundle = bundle;
        gratitudePagerAdapter = new GratitudePagerAdapter(fragmentManager, tabLayout.getTabCount());
        viewPager.setAdapter(gratitudePagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        if (bundle != null) {
            go_dairy = bundle.getString("GO");
            if (go_dairy != null) {
                if (go_dairy.equalsIgnoreCase("DIARY")) {
                    viewPager.setCurrentItem(2);
                }

            }
        }
    }

    @Override
    public void setOnTabSelectedListener() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void displayKitkatIcon() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            gratitudeView.displayKitkat();
        } else {
            gratitudeView.hideKitkat();
        }
    }

    @Override
    public void goViewMyDiary() {

    }


    public class GratitudePagerAdapter extends FragmentStatePagerAdapter {

        int numOfTabs;

        public GratitudePagerAdapter(FragmentManager fm, int numOfTabs) {
            super(fm);
            this.numOfTabs = numOfTabs;
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    DiaryFragment diaryFragment = new DiaryFragment();
                    return diaryFragment;
                /*case 1:
                    DiaryTabFragment diaryTabFragment = new DiaryTabFragment();
                    return diaryTabFragment;*/
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numOfTabs;
        }
    }

}


