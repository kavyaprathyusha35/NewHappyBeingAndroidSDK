package com.nsmiles.happybeingsdklib.NatureCalm;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nsmiles.happybeingsdklib.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by Sukumar on 6/5/2018.
 */

public class NatureFragment extends Fragment {

    private Animation animMove;
    ImageView nature_img;
    public static final String iMAGE_URL = "image_url";
    private String url;
    int image_drawable;
    HorizontalScrollView hr;

    public static NatureFragment newInstance(String url){

        NatureFragment natureFragment = new NatureFragment();
        Bundle args = new Bundle();
        args.putString(iMAGE_URL, url);
        natureFragment.setArguments(args);
        return natureFragment;
    }

    public static NatureFragment newInstance(int drawable){

        NatureFragment natureFragment = new NatureFragment();
        Bundle args  = new Bundle();
        args.putInt(iMAGE_URL, drawable);
        natureFragment.setArguments(args);
        return natureFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=null;

         url = getArguments().getString(iMAGE_URL);

         view = LayoutInflater.from(getContext()).inflate(R.layout.nature_adapter, container, false);

        nature_img = (ImageView) view.findViewById(R.id.nature_img);
       // animation = AnimationUtils.loadAnimation(getActivity(), R.anim.center_in);

        hr = (HorizontalScrollView) view.findViewById(R.id.hr);


        if(url!=null && url.length()>0) {

            if (url.startsWith("https")) {


                Glide.with(getActivity())
                        .load(url)
                        .into(nature_img);

                // nature_img.startAnimation(animation);


            } else if (url.equals("nature_calms_1")) {

                int width = Resources.getSystem().getDisplayMetrics().widthPixels;
                int height = Resources.getSystem().getDisplayMetrics().heightPixels;
                HorizontalScrollView.LayoutParams parms = new HorizontalScrollView.LayoutParams(width, height);
                nature_img.setLayoutParams(parms);

                nature_img.setImageDrawable(getResources().getDrawable(R.drawable.nature_calm1));
                //nature_img.startAnimation(animation);


            } else if (url.equals("nature_calms_2")) {

                int width = Resources.getSystem().getDisplayMetrics().widthPixels;
                int height = Resources.getSystem().getDisplayMetrics().heightPixels;
                HorizontalScrollView.LayoutParams parms = new HorizontalScrollView.LayoutParams(width, height);
                nature_img.setLayoutParams(parms);

                nature_img.setImageDrawable(getResources().getDrawable(R.drawable.nature_calm2));
                //nature_img.startAnimation(animation);


            } else if (url.equals("nature_calms_3")) {

                int width = Resources.getSystem().getDisplayMetrics().widthPixels;
                int height = Resources.getSystem().getDisplayMetrics().heightPixels;
                HorizontalScrollView.LayoutParams parms = new HorizontalScrollView.LayoutParams(width, height);
                nature_img.setLayoutParams(parms);

                nature_img.setImageDrawable(getResources().getDrawable(R.drawable.nature_calm3));
                //nature_img.startAnimation(animation);


            }

        }
        animMove = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, -900, TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f);
        animMove.setDuration(17000);
        animMove.setInterpolator(new LinearInterpolator());
        nature_img.startAnimation(animMove);


        return view;
    }

}
