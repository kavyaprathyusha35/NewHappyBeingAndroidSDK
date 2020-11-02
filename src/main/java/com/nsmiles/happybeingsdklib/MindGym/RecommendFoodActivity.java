package com.nsmiles.happybeingsdklib.MindGym;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class RecommendFoodActivity extends AppCompatActivity implements RecommendView, View.OnClickListener {

    RecommendView recommendView;
    RecommendImplementation recommendImplementation;
    Activity activity;
    TextView food_tv;
    Button done_btn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_food);


        food_tv = (TextView) findViewById(R.id.food_tv);
        done_btn = (Button) findViewById(R.id.done_btn);
        toolbar  = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Stress relief food");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        done_btn.setOnClickListener(this);
        activity = RecommendFoodActivity.this;
        recommendView = RecommendFoodActivity.this;
        recommendImplementation = new RecommendImplementation(recommendView, activity,getIntent());
        recommendImplementation.loadFoodData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(RecommendFoodActivity.this ,R.color.hb_circle_black_light));
        }
    }

    @Override
    public void displayFoodData(String food) {
        food_tv.setText(food);
    }

    @Override
    public void changeToolbar() {
        toolbar.setTitle("Relax activity");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.done_btn) {
            finish();
        }

    }
}
