package com.nsmiles.happybeingsdklib.mycoachfragment.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.Affimations.AffirmationScreen;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.UI.HomeScreenActivity;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.dagger.data.PreferenceManager;
import com.nsmiles.happybeingsdklib.mycoachfragment.CoachAudioDetails;
import com.nsmiles.happybeingsdklib.mycoachfragment.DailyGratitude;
import com.nsmiles.happybeingsdklib.playaudio.fragment.PlayAudioActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import static android.content.Context.MODE_PRIVATE;

public class CoachViewPagerAdapter extends PagerAdapter {

    List<CoachAudioDetails> coachAudioAdapterList;
    Activity activity;
    CommonUtils commonUtils;
    private String user_mobile, userMobileISDCode;
    CoachAudioDetails coachAudioDetails;
    CoachOnClickListenerInterface coachOnClickListenerInterface;
    boolean isplay = false;
    String START_DATE = "", END_DATE = "";
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z", Locale.getDefault());
    DailyGratitude dailyGratitude;
    FragmentManager fragmentManager;
    private SharedPreferences sharedPreferences;
    private boolean overlayDialogButtonClicked = false;
    private Dialog dialog;
    private String wishMessage;
    LayoutInflater layoutInflater;
    ViewPager viewPager;
    private boolean isLoggedIn=false;
    private PreferenceManager preferenceManager;



    public CoachViewPagerAdapter(Activity activity, List<CoachAudioDetails> coachAudioDetails, ViewPager viewPager) {
        this.activity = activity;
        this.coachAudioAdapterList = coachAudioDetails;
        commonUtils = new CommonUtils();
        this.viewPager=viewPager;
        START_DATE = df.format(Calendar.getInstance().getTime());
        dailyGratitude = new DailyGratitude();
        sharedPreferences = activity.getSharedPreferences("HAPPY_BEING_STORED", MODE_PRIVATE);
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        preferenceManager = new PreferenceManager(activity);
        isLoggedIn = preferenceManager.get(AppConstants.IS_SIGNED_IN, "").equals("true");
    }

    public void setCoachOnClickListenerInterface(CoachOnClickListenerInterface coachOnClickListenerInterface) {
        this.coachOnClickListenerInterface = coachOnClickListenerInterface;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.coach_viewpager, container, false);

        CardView coach_card_view = (CardView) itemView.findViewById(R.id.coach_card_view);
        TextView coach_duration_tv = (TextView) itemView.findViewById(R.id.coach_duration_tv);
        ImageView coach_gratitude_img = (ImageView) itemView.findViewById(R.id.coach_gratitude_img);
        TextView audio_desc_text = itemView.findViewById(R.id.audio_desc_text);
        LinearLayout coach_gratitude_linear = itemView.findViewById(R.id.coach_gratitude_linear);
        ImageView play_button = (ImageView) itemView.findViewById(R.id.play_button);



        if (coachAudioAdapterList.size() > 0) {
            Log.i("Coach", "Size is "+coachAudioAdapterList.size());
            if (coachAudioAdapterList.get(position).getAudioOrGratitude().equalsIgnoreCase("GRATITUDE")) {

                coach_duration_tv.setText(coachAudioAdapterList.get(position).getCoach_duration()  + " Min");
                audio_desc_text.setVisibility(View.VISIBLE);
                audio_desc_text.setText("Today's journaling practice");
                play_button.setVisibility(View.VISIBLE);

                /**/
/*
                dailyGratitude.getDailyData(commonUtils.getPrimaryProfile(activity), coachAudioAdapterList.get(position).getGratitudeDay() ,
                        commonUtils.getSecondaryProfile(activity), commonUtils.getGender(activity));
*/
                coach_gratitude_img.setImageResource(R.drawable.journal_image);


                /**/
                Log.i("CoachAudioAdapter","gratitude day is "+coachAudioAdapterList.get(position).getGratitudeDay());
                boolean completed = commonUtils.getGratitudeData(activity, coachAudioAdapterList.get(position).getGratitudeDay() - 1);


            } else if (coachAudioAdapterList.get(position).getAudioOrGratitude().equalsIgnoreCase("AFFIRMATION")) {

                coach_duration_tv.setText(coachAudioAdapterList.get(position).getCoach_duration() + " Min");
                audio_desc_text.setVisibility(View.VISIBLE);
                audio_desc_text.setText("Today's positive affirmations list");
                play_button.setVisibility(View.VISIBLE);

                /**/
/*
                dailyGratitude.getDailyData(commonUtils.getPrimaryProfile(activity), coachAudioAdapterList.get(position).getGratitudeDay(),
                        commonUtils.getSecondaryProfile(activity), commonUtils.getGender(activity));
*/
                coach_gratitude_img.setImageResource(R.drawable.affirmation_icon_new);

                /**/

            }
//        else if (coachAudioAdapterList.get(position).getAudioOrGratitude().equalsIgnoreCase("IMAGERY")){
//            //holder.tv_good_mrng.setText(" Morning workout");
//
//            audio_desc_text.setText("Best time to practice - Morning or after the mindfulness practice to boost immunity and strengthen the body's ability to heal");
//            audio_desc_text.setVisibility(View.VISIBLE);
//            coach_duration_tv.setText(coachAudioAdapterList.get(position).getCoach_duration() + " Min");
//
//            play_button.setVisibility(View.VISIBLE);
//            Log.i("Coach", "In imagery");
//            /**/
//            dailyGratitude.getDailyData(commonUtils.getPrimaryProfile(activity), coachAudioAdapterList.get(position).getGratitudeDay() ,
//                    commonUtils.getSecondaryProfile(activity), commonUtils.getGender(activity));
//            coach_gratitude_img.setImageResource(R.drawable.imagery_icon);
//
//
//        }
            else {

                coach_duration_tv.setText(coachAudioAdapterList.get(position).getCoach_duration() + " Min");
                coach_gratitude_img.setImageResource(R.drawable.relex_images);
                audio_desc_text.setVisibility(View.VISIBLE);
                play_button.setVisibility(View.VISIBLE);
                audio_desc_text.setText(coachAudioAdapterList.get(position).getAudio_title());


//            if (coachAudioAdapterList.size() == position + 1) {
//                Log.d("ssssss", coachAudioAdapterList.get(position).getRelaxOrCoach());
//            }
            }

        }
        coach_gratitude_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CoachAudioAdapter","In onclick of card");
                overlayDialogButtonClicked = sharedPreferences.getBoolean(AppConstants.OVERLAY_DIALOG_BUTTON_CLICKED, false);
                //Log.i("Coach", "****Over lay clicked "+overlayDialogButtonClicked);
                if (coachAudioAdapterList.get(position) != null && coachAudioAdapterList.get(position).getAudioOrGratitude() != null) {
                    if (coachAudioAdapterList.get(position).getAudioOrGratitude().equalsIgnoreCase("AUDIO")
                            || coachAudioAdapterList.get(position).getAudioOrGratitude().equalsIgnoreCase("PLAY")) {
                        Log.i("CoachAudioAdapter","In onclick of card"+coachAudioAdapterList.get(position).getSynced_flag());
                        coachAudioAdapterList.get(position).setSynced_flag(1);
                        if (coachAudioAdapterList.get(position).getSynced_flag() == 1) {

                            coachAudioDetails = new CoachAudioDetails();
                            coachAudioDetails.setTable_name(coachAudioAdapterList.get(position).getTable_name());
                            coachAudioDetails.setDay_number(coachAudioAdapterList.get(position).getDay_number());
                            coachAudioDetails.setSynced_flag(coachAudioAdapterList.get(position).getSynced_flag());
                            coachAudioDetails.setRelaxOrCoach(coachAudioAdapterList.get(position).getRelaxOrCoach());
                            coachAudioDetails.setAudio_title(coachAudioAdapterList.get(position).getAudio_title());
                            coachAudioDetails.setYyyyMmDdDate(coachAudioAdapterList.get(position).getYyyyMmDdDate());
                            coachAudioDetails.setActual_server_day(coachAudioAdapterList.get(position).getActual_server_day());
                            coachAudioDetails.setPlayStatus(coachAudioAdapterList.get(position).getPlayStatus());
                            coachAudioDetails.setAudio_number(coachAudioAdapterList.get(position).getAudio_number());

                            if (coachAudioAdapterList.get(position).isReply_audio()) {
                                coachAudioDetails.setReply_audio(true);
                            } else {
                                coachAudioDetails.setReply_audio(false);
                            }

                            if (coachAudioDetails.isReply_audio()) {
                                isplay = true;
                            } else {
                                isplay = false;
                            }
                            Log.i("CoachAudioAdapter", "****Table name is " + coachAudioDetails.getTable_name());

                            activity.startActivity(new Intent(activity, PlayAudioActivity.class)
                                    .putExtra("TABLE", coachAudioDetails.getTable_name())
                                    .putExtra("ID", coachAudioDetails.getId())
                                    .putExtra("DAY_NUMBER", coachAudioDetails.getDay_number())
                                    .putExtra("RELAX_COACH", coachAudioDetails.getRelaxOrCoach())
                                    .putExtra("AUDIO_NAME", coachAudioDetails.getAudio_title())
                                    .putExtra("SIMPLE_DATE", coachAudioDetails.getYyyyMmDdDate())
                                    .putExtra("ACTUAL_DAY", String.valueOf(coachAudioDetails.getActual_server_day()))
                                    .putExtra("PLAY_STATUS", String.valueOf(coachAudioDetails.getPlayStatus()))
                                    .putExtra("AUDIO_NUMBER", String.valueOf(coachAudioDetails.getAudio_number()))
                                    .putExtra("REPLAY", isplay));
                            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);


                        } else {
                            // load again use is offline
                            coachOnClickListenerInterface.reloadCoachOnClickListener();
                        }
                        /*} else {
                            coachOnClickListenerInterface.showNetworkOnClickListener();
                        }*/

                    }   // audio logic

                    else if (coachAudioAdapterList.get(position).getAudioOrGratitude().equalsIgnoreCase("AFFIRMATION")) {
                        activity.startActivity(new Intent(activity, AffirmationScreen.class));
//                                                .putExtra("Affirmation_day", coachAudioAdapterList.get(position).getGratitudeDay() - 1));
                        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);


                    } else if (coachAudioAdapterList.get(position).getAudioOrGratitude().equalsIgnoreCase("IMAGERY")) {
                        Log.i("Coach", "In imagery click");

                        if (coachAudioAdapterList.get(position).getSynced_flag() == 1) {


                            coachAudioDetails = new CoachAudioDetails();
                            coachAudioDetails.setTable_name(coachAudioAdapterList.get(position).getTable_name());
                            coachAudioDetails.setDay_number(coachAudioAdapterList.get(position).getDay_number());
                            coachAudioDetails.setSynced_flag(coachAudioAdapterList.get(position).getSynced_flag());
                            coachAudioDetails.setRelaxOrCoach("COACH");
                            coachAudioDetails.setAudio_title("Meditation to boost immunity");
                            coachAudioDetails.setYyyyMmDdDate(coachAudioAdapterList.get(position).getYyyyMmDdDate());
                            coachAudioDetails.setActual_server_day(coachAudioAdapterList.get(position).getActual_server_day());
                            coachAudioDetails.setPlayStatus(coachAudioAdapterList.get(position).getPlayStatus());
                            coachAudioDetails.setAudio_number(coachAudioAdapterList.get(position).getAudio_number());

                            if (coachAudioAdapterList.get(position).isReply_audio()) {
                                coachAudioDetails.setReply_audio(true);
                            } else {
                                coachAudioDetails.setReply_audio(false);
                            }

                            if (coachAudioDetails.isReply_audio()) {
                                isplay = true;
                            } else {
                                isplay = false;
                            }

                            activity.startActivity(new Intent(activity, PlayAudioActivity.class)
                                    .putExtra("TABLE", coachAudioDetails.getTable_name())
                                    .putExtra("ID", coachAudioDetails.getId())
                                    .putExtra("DAY_NUMBER", coachAudioDetails.getDay_number())
                                    .putExtra("RELAX_COACH", coachAudioDetails.getRelaxOrCoach())
                                    .putExtra("AUDIO_NAME", coachAudioDetails.getAudio_title())
                                    .putExtra("SIMPLE_DATE", coachAudioDetails.getYyyyMmDdDate())
                                    .putExtra("ACTUAL_DAY", String.valueOf(coachAudioDetails.getActual_server_day()))
                                    .putExtra("PLAY_STATUS", String.valueOf(coachAudioDetails.getPlayStatus()))
                                    .putExtra("AUDIO_NUMBER", String.valueOf(coachAudioDetails.getAudio_number()))
                                    .putExtra("REPLAY", isplay));
                            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);


                        } else {
                            // load again use is offline
                            coachOnClickListenerInterface.reloadCoachOnClickListener();
                        }
                        /*} else {
                            coachOnClickListenerInterface.showNetworkOnClickListener();
                        }*/

                    }   // audio logic

                    else {

                        activity.startActivity(new Intent(activity, HomeScreenActivity.class)
                                .putExtra("FROM_SCREEN", "JOURNAL"));
                        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        activity.finish();
                    }

                }
            }
        });




        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View) object);
    }


    public interface CoachOnClickListenerInterface {

        void reloadCoachOnClickListener();


    }

}
