package com.nsmiles.happybeingsdklib.NatureCalm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.nsmiles.happybeingsdklib.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class NatureCalmActivity extends AppCompatActivity {

    private ViewPager myViewPager;
    private NatureImplementation natureImplementation;
    private Activity activity;
    RelativeLayout relativeLayout;
    FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nature_calm);
        renderView();
    }

    private void renderView(){
        activity = NatureCalmActivity.this;
        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        relativeLayout=(RelativeLayout)findViewById(R.id.relative);
        fm = getSupportFragmentManager();

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        natureImplementation = new NatureImplementation(activity,myViewPager,fm);
        natureImplementation.initNatureCalm();
    }

    @Override
    protected void onStop() {
        super.onStop();
        natureImplementation.onStop();
    }

}
