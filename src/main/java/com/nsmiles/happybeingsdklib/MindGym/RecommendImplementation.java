package com.nsmiles.happybeingsdklib.MindGym;

import android.app.Activity;
import android.content.Intent;

import com.nsmiles.happybeingsdklib.Utils.CommonUtils;

import java.util.Random;

/**
 * Created by nSmiles on 12/16/2017.
 */

public class RecommendImplementation implements RecommendPresenter {

    private Activity activity;
    private RecommendView recommendView;
    private int index;
    private Random recommendRandomGenerator;
    private RelaxUtils relaxUtils;
    private String food;
    private Intent intent;
    private CommonUtils commonUtils;

    public RecommendImplementation(RecommendView recommendView, Activity activity, Intent intent) {

        this.activity = activity;
        this.recommendView = recommendView;
        this.intent = intent;
        relaxUtils = new RelaxUtils();
        recommendRandomGenerator = new Random();
        index = recommendRandomGenerator.nextInt(relaxUtils.recommendFoodSize());
        food = relaxUtils.getRecommend_food_list(index);
        commonUtils = new CommonUtils();
    }

    @Override
    public void loadFoodData() {

        if (intent.hasExtra("DATA")) {
            recommendView.changeToolbar();
            recommendView.displayFoodData(intent.getStringExtra("DATA"));
        } else {

            recommendView.displayFoodData(food);
        }


    }

    @Override
    public void getHowAreYouFeeling() {
        if(activity!=null)
        CommonUtils.showHowAreYouFeeling(activity, "", "RELAX_RELIEVE_ANDROID", food, "Recommend food");
    }
}
