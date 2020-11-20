package com.nsmiles.happybeingsdklib.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.nsmiles.happybeingsdklib.R;

import androidx.annotation.Nullable;

/**
 * Created by Kavya on 11/18/2020.
 */

public class MyCoachDescription extends Activity {

    LinearLayout myCoachLayout, goalsLayout, journal_layout, mindspaLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_layout);
        Intent intent = getIntent();
        String descriptionName = "";
        if (intent.hasExtra("DescriptionName")) {
            descriptionName = intent.getStringExtra("DescriptionName");
        }
        myCoachLayout = findViewById(R.id.my_coach_desc_layout);
        goalsLayout = findViewById(R.id.my_goals_desc_layout);
        journal_layout = findViewById(R.id.journal_desc_layout);
        mindspaLayout = findViewById(R.id.mind_spa_desc_layout);
        assert descriptionName != null;
        setLayout(descriptionName);
    }

    private void setLayout(String descriptionName) {
        switch (descriptionName) {
            case "My Coach":
                myCoachLayout.setVisibility(View.VISIBLE);
                goalsLayout.setVisibility(View.VISIBLE);
                journal_layout.setVisibility(View.GONE);
                mindspaLayout.setVisibility(View.GONE);
                break;
            case "My Journal":
                myCoachLayout.setVisibility(View.GONE);
                goalsLayout.setVisibility(View.GONE);
                journal_layout.setVisibility(View.VISIBLE);
                mindspaLayout.setVisibility(View.GONE);
                break;
            case "MindSpa":
                myCoachLayout.setVisibility(View.GONE);
                goalsLayout.setVisibility(View.GONE);
                journal_layout.setVisibility(View.GONE);
                mindspaLayout.setVisibility(View.VISIBLE);
                break;
        }
    }
}
