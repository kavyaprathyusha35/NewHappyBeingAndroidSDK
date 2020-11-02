package com.nsmiles.happybeingsdklib.NatureCalm;

import android.app.Activity;
import android.os.Handler;

import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.apis.retrofit.RetrofitGenrater;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sukumar on 6/5/2018.
 */

public class NatureImplementation implements NaturePresenter {

    private Activity activity;
    private ViewPager myViewPager;
    private NatureAdapter natureAdapter;
    private String[] url;
    private List<Fragment> fragments;
    private NatureViewPageAdapter natureViewPageAdapter;
    private FragmentManager fm;
    int currentPage = 0;
    Timer timer;
    Handler handler;
    Runnable update;
    ArrayList<String> nature_calmimages;

    public NatureImplementation(Activity activity, ViewPager myViewPager, FragmentManager fm) {

        this.activity = activity;
        this.myViewPager = myViewPager;
        this.fm = fm;
        nature_calmimages=new ArrayList<String>();
    }

    @Override
    public void initNatureCalm() {


        getNatureCalmImages();

    }

    private void getNatureCalmImages() {

        RetrofitGenrater.getApi().getNatureCalmImage().enqueue(new Callback<NatureCalmRootModel>() {
            @Override
            public void onResponse(@NonNull Call<NatureCalmRootModel> call, @NonNull Response<NatureCalmRootModel> response) {

                if (response.body() != null) {

                    NatureCalmRootModel natureCalmRootModel = response.body();

                    nature_calmimages = natureCalmRootModel.getSuccess().getScreen().getNaturecalm_images();

                    nature_calmimages.add(0, "nature_calms_1");
                    nature_calmimages.add(1, "nature_calms_2");
                    nature_calmimages.add(2, "nature_calms_3");
                    callAdapter();

                }

            }

            @Override
            public void onFailure(Call<NatureCalmRootModel> call, Throwable t) {

                CommonUtils.showToast(activity,"something went wrong");

            }
        });

    }

    @Override
    public void onStop() {
        handler.removeCallbacks(update);
    }


    @Override
    public void callAdapter() {

        if (nature_calmimages.size() > 0) {

            natureViewPageAdapter = new NatureViewPageAdapter(fm);
            fragments = new ArrayList<>();

            for(int i=0; i<nature_calmimages.size();i++) {

                natureViewPageAdapter.addNatureCalmImage(NatureFragment.newInstance(nature_calmimages.get(i)));

            }

            setupViewPager(myViewPager);


        }

    }


    private void setupViewPager(final ViewPager viewPager){

        viewPager.setAdapter(natureViewPageAdapter);

        handler = new Handler();
        update = new Runnable() {
            @Override
            public void run() {

                if(currentPage == nature_calmimages.size()){

                    activity.finish();

                }

                myViewPager.setCurrentItem(currentPage++,true);
            }
        };

        timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },3000,7000);


    }




    private class NatureViewPageAdapter extends FragmentPagerAdapter {


        public NatureViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addNatureCalmImage(Fragment fragment){
            fragments.add(fragment);
        }
    }




}
