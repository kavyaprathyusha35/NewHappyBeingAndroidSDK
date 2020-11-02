package com.nsmiles.happybeingsdklib.mycoachfragment.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.Affimations.AffirmationScreen;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.UI.HomeScreenActivity;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.mycoachfragment.CoachAudioDetails;
import com.nsmiles.happybeingsdklib.mycoachfragment.DailyGratitude;
import com.nsmiles.happybeingsdklib.playaudio.fragment.PlayAudioActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;


public class CoachAudioAdapter extends RecyclerView.Adapter<CoachAudioAdapter.ViewHolder> implements View.OnClickListener {

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


    public CoachAudioAdapter(Activity activity, FragmentManager fragmentManager, List<CoachAudioDetails> coachAudioAdapterList) {
        this.activity = activity;
        this.coachAudioAdapterList = coachAudioAdapterList;
        commonUtils = new CommonUtils();
        START_DATE = df.format(Calendar.getInstance().getTime());
        dailyGratitude = new DailyGratitude();
        this.fragmentManager = fragmentManager;
        sharedPreferences = activity.getSharedPreferences("HAPPY_BEING_STORED", MODE_PRIVATE);
    }

    public void setCoachOnClickListenerInterface(CoachOnClickListenerInterface coachOnClickListenerInterface) {
        this.coachOnClickListenerInterface = coachOnClickListenerInterface;
    }

    @Override
    public CoachAudioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_seven_day_coach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CoachAudioAdapter.ViewHolder holder, final int position) {
        Log.i("Coach", "Size is "+coachAudioAdapterList.size());
        if(position == 0){
            holder.coach_card_view.measure(0,0);
            coachOnClickListenerInterface.getFirstCardSize(holder.coach_card_view.getMeasuredHeight(), coachAudioAdapterList.size()+1);
        }

        if (coachAudioAdapterList != null && coachAudioAdapterList.size() > 0) {
            Log.d("coachAudioAdapterList", String.valueOf(coachAudioAdapterList.size())+coachAudioAdapterList.get(position).getAudioOrGratitude());
            Drawable img=activity.getResources().getDrawable(R.drawable.feed_back);

            wishMessage = "";
            Calendar c = Calendar.getInstance();
            int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
            Log.d("timeOfDay", String.valueOf(timeOfDay));

            if(timeOfDay >= 0 && timeOfDay < 12){
                holder.icon_morn.setImageDrawable(activity.getResources().getDrawable(R.drawable.good_mornings));
                holder.tv_good_mrng.setText(" Morning workout");

                wishMessage="Good Morning";
            }else if(timeOfDay >= 12 && timeOfDay < 16){
                wishMessage="Good Afternoon";
                holder.icon_morn.setImageDrawable(activity.getResources().getDrawable(R.drawable.any_times));
                holder.tv_good_mrng.setText(" Anytime workout");
            }else if(timeOfDay >= 16){
                wishMessage="Good Evening";
                holder.icon_morn.setImageDrawable(activity.getResources().getDrawable(R.drawable.good_evenings));
                holder.tv_good_mrng.setText(" Evening workout");
            }
            if (coachAudioAdapterList.get(position).getAudioOrGratitude().equalsIgnoreCase("GRATITUDE")) {
                holder.tv_good_mrng.setText(" Evening workout");
                holder.icon_morn.setImageDrawable(activity.getResources().getDrawable(R.drawable.good_evenings));


                holder.dd_mm_layout.setVisibility(View.VISIBLE);
                holder.coach_today_tv.setText(String.valueOf(coachAudioAdapterList.get(position).getGratitudeDay()));
                holder.coach_duration_tv.setText(coachAudioAdapterList.get(position).getCoach_duration() + " Min");
                holder.audio_name_tv.setText("Today's journaling practice");
                holder.card_image_layout.setVisibility(View.VISIBLE);
                holder.days_layout.setVisibility(View.GONE);
                holder.audio_desc_text.setVisibility(View.VISIBLE);
                holder.audio_desc_text.setText(R.string.gratitude_text);
                holder.play_button.setVisibility(View.GONE);

                /**/
                dailyGratitude.getDailyData("", coachAudioAdapterList.get(position).getGratitudeDay() ,
                        "", "");
                holder.coach_gratitude_img.setImageResource(R.drawable.journal_image);


                /**/
                Log.i("CoachAudioAdapter","gratitude day is "+coachAudioAdapterList.get(position).getGratitudeDay());
                boolean completed = commonUtils.getGratitudeData(activity, coachAudioAdapterList.get(position).getGratitudeDay() - 1);

                if (coachAudioAdapterList.get(position).isReply_audio() || completed) {

                    holder.coach_begin_btn.setTextColor(activity.getResources().getColor(R.color.white));
                    holder.coach_begin_btn.setBackground(activity.getResources().getDrawable(R.drawable.button_gray));
                    holder.coach_begin_btn.setText("VIEW JOURNAL");
                    holder.completed_tv.setVisibility(View.VISIBLE);
                    holder.coach_today_tv.setTextColor(activity.getResources().getColor(R.color.hb_circle_black_light));
                    holder.day_txt_tv.setTextColor(activity.getResources().getColor(R.color.hb_circle_black_light));

                } else {
                    holder.coach_begin_btn.setText("BEGIN");
                    holder.coach_begin_btn.setTextColor(activity.getResources().getColor(R.color.white));
                    holder.coach_begin_btn.setBackground(activity.getResources().getDrawable(R.drawable.button_radius));
                    holder.completed_tv.setVisibility(View.INVISIBLE);
                    holder.coach_today_tv.setTextColor(activity.getResources().getColor(R.color.day31dark));
                    holder.day_txt_tv.setTextColor(activity.getResources().getColor(R.color.day31dark));
                }
                holder.description_text.setText("Best time to practice - Before retiring to bed and just before going through affirmations.");

            } else if (coachAudioAdapterList.get(position).getAudioOrGratitude().equalsIgnoreCase("AFFIRMATION")) {
                holder.dd_mm_layout.setVisibility(View.VISIBLE);
                holder.tv_good_mrng.setText(" Anytime workout");
                holder.icon_morn.setImageDrawable(activity.getResources().getDrawable(R.drawable.any_times));

                //   holder.coach_today_tv.setText(String.valueOf(coachAudioAdapterList.get(position).getGratitudeDay()));
                holder.coach_duration_tv.setText(coachAudioAdapterList.get(position).getCoach_duration() + " Min");
                holder.audio_name_tv.setText("Today's positive affirmations list");
                holder.card_image_layout.setVisibility(View.VISIBLE);
                holder.days_layout.setVisibility(View.GONE);
                Log.i("Coach", "In affirmation");
                holder.description_text.setText("Best time to practice - Any time of the day you feel the need for` a positivity boost or just before going to bed.");
                holder.audio_desc_text.setVisibility(View.VISIBLE);
                holder.audio_desc_text.setText(R.string.affirmation_text);
                holder.play_button.setVisibility(View.VISIBLE);

                /**/
/*
                dailyGratitude.getDailyData(commonUtils.getPrimaryProfile(activity), coachAudioAdapterList.get(position).getGratitudeDay(),
                        commonUtils.getSecondaryProfile(activity), commonUtils.getGender(activity));
*/
                holder.coach_gratitude_img.setImageResource(R.drawable.affirmation_icon_new);

                /**/

                if (coachAudioAdapterList.get(position).isReply_audio()) {

                    holder.coach_begin_btn.setTextColor(activity.getResources().getColor(R.color.white));
                    holder.coach_begin_btn.setBackground(activity.getResources().getDrawable(R.drawable.button_gray));
                    holder.coach_begin_btn.setText("START AGAIN");
                    holder.completed_tv.setVisibility(View.VISIBLE);
                    holder.coach_today_tv.setTextColor(activity.getResources().getColor(R.color.hb_circle_black_light));
                    holder.day_txt_tv.setTextColor(activity.getResources().getColor(R.color.hb_circle_black_light));

                } else {
                    holder.coach_begin_btn.setText("BEGIN");
                    holder.coach_begin_btn.setTextColor(activity.getResources().getColor(R.color.white));
                    holder.coach_begin_btn.setBackground(activity.getResources().getDrawable(R.drawable.button_radius));
                    holder.completed_tv.setVisibility(View.INVISIBLE);
                    holder.coach_today_tv.setTextColor(activity.getResources().getColor(R.color.day31dark));
                    holder.day_txt_tv.setTextColor(activity.getResources().getColor(R.color.day31dark));
                }

            }else if (coachAudioAdapterList.get(position).getAudioOrGratitude().equalsIgnoreCase("IMAGERY")){
                //holder.tv_good_mrng.setText(" Morning workout");
                holder.icon_morn.setImageDrawable(activity.getResources().getDrawable(R.drawable.boosters));

                holder.dd_mm_layout.setVisibility(View.VISIBLE);
                holder.tv_good_mrng.setText(" Immunity booster");
                holder.audio_desc_text.setText("Best time to practice - Morning or after the mindfulness practice to boost immunity and strengthen the body's ability to heal");
                holder.audio_desc_text.setVisibility(View.VISIBLE);
                holder.coach_today_tv.setText(String.valueOf(coachAudioAdapterList.get(position).getGratitudeDay()));
                holder.coach_duration_tv.setText(coachAudioAdapterList.get(position).getCoach_duration() + " Min");
                holder.audio_name_tv.setText("Meditation to boost immunity");
                holder.card_image_layout.setVisibility(View.VISIBLE);
                holder.days_layout.setVisibility(View.GONE);
                holder.play_button.setVisibility(View.VISIBLE);
                Log.i("Coach", "In imagery");
                /**/
/*
                dailyGratitude.getDailyData(commonUtils.getPrimaryProfile(activity), coachAudioAdapterList.get(position).getGratitudeDay() ,
                        commonUtils.getSecondaryProfile(activity), commonUtils.getGender(activity));
*/
                holder.coach_gratitude_img.setImageResource(R.drawable.imagery_icon);

                /**/

                if (coachAudioAdapterList.get(position).isReply_audio()) {

                    holder.coach_begin_btn.setTextColor(activity.getResources().getColor(R.color.white));
                    holder.coach_begin_btn.setBackground(activity.getResources().getDrawable(R.drawable.button_gray));
                    holder.coach_begin_btn.setText("START AGAIN");
                    holder.completed_tv.setVisibility(View.VISIBLE);
                    holder.coach_today_tv.setTextColor(activity.getResources().getColor(R.color.hb_circle_black_light));
                    holder.day_txt_tv.setTextColor(activity.getResources().getColor(R.color.hb_circle_black_light));

                } else {
                    holder.coach_begin_btn.setText("BEGIN");
                    holder.coach_begin_btn.setTextColor(activity.getResources().getColor(R.color.white));
                    holder.coach_begin_btn.setBackground(activity.getResources().getDrawable(R.drawable.button_radius));
                    holder.completed_tv.setVisibility(View.INVISIBLE);
                    holder.coach_today_tv.setTextColor(activity.getResources().getColor(R.color.day31dark));
                    holder.day_txt_tv.setTextColor(activity.getResources().getColor(R.color.day31dark));
                }

            } else {
                holder.tv_good_mrng.setText(" Morning workout");
                holder.icon_morn.setImageDrawable(activity.getResources().getDrawable(R.drawable.good_mornings));
                holder.coach_today_tv.setText(coachAudioAdapterList.get(position).getDay_number());
                holder.coach_duration_tv.setText(coachAudioAdapterList.get(position).getCoach_duration() + " Min");
                holder.audio_name_tv.setText(coachAudioAdapterList.get(position).getAudio_title());
                holder.card_image_layout.setVisibility(View.VISIBLE);
                holder.days_layout.setVisibility(View.GONE);
                holder.coach_gratitude_img.setImageResource(R.drawable.relex_images);
                holder.description_text.setText("Best time to practice - First thing in the morning to build the attitude of being mindful all day.");
                holder.audio_desc_text.setVisibility(View.VISIBLE);
                holder.play_button.setVisibility(View.VISIBLE);
                holder.audio_desc_text.setText(R.string.audio_text);
                if (coachAudioAdapterList.get(position).getDd_date() != null && coachAudioAdapterList.get(position).getMm_month() != null) {
                    holder.dd_mm_layout.setVisibility(View.VISIBLE);
                    holder.coach_date_tv.setText(coachAudioAdapterList.get(position).getDd_date());
                    holder.coach_month_tv.setText(coachAudioAdapterList.get(position).getMm_month());
                }
                Log.i("CoauchAudioAdapter", "Status of playing is "+coachAudioAdapterList.get(position).getPlayStatus());

                if (coachAudioAdapterList.get(position).getPlayStatus() == 1) {

                    if (coachAudioAdapterList.get(position).getAudioOrGratitude().equalsIgnoreCase("AUDIO")) {
                        holder.coach_begin_btn.setText("PLAY AGAIN");
                        holder.coach_begin_btn.setTextColor(activity.getResources().getColor(R.color.white));
                        holder.coach_begin_btn.setBackground(activity.getResources().getDrawable(R.drawable.button_gray));
                        holder.completed_tv.setVisibility(View.VISIBLE);
                       // holder.coach_today_tv.setTextColor(activity.getResources().getColor(R.color.hb_circle_black_light));
                        //holder.day_txt_tv.setTextColor(activity.getResources().getColor(R.color.hb_circle_black_light));
                    } else {

                        holder.coach_begin_btn.setText("BEGIN");
                        holder.coach_begin_btn.setTextColor(activity.getResources().getColor(R.color.white));
                        holder.coach_begin_btn.setBackground(activity.getResources().getDrawable(R.drawable.button_radius));
                        holder.completed_tv.setVisibility(View.INVISIBLE);
                        //holder.coach_today_tv.setTextColor(activity.getResources().getColor(R.color.day31dark));
                        //holder.day_txt_tv.setTextColor(activity.getResources().getColor(R.color.day31dark));
                    }

                } else {
                    holder.coach_begin_btn.setText("BEGIN");
                    holder.coach_begin_btn.setTextColor(activity.getResources().getColor(R.color.white));
                    holder.coach_begin_btn.setBackground(activity.getResources().getDrawable(R.drawable.button_radius));
                    holder.completed_tv.setVisibility(View.INVISIBLE);
                    //holder.coach_today_tv.setTextColor(activity.getResources().getColor(R.color.day31dark));
                    //holder.day_txt_tv.setTextColor(activity.getResources().getColor(R.color.day31dark));
                }

                if (coachAudioAdapterList.size() == position + 1) {
                    Log.d("ssssss", coachAudioAdapterList.get(position).getRelaxOrCoach());
                }

                if (coachAudioAdapterList.get(position).getActual_server_day() > 31) {
                    holder.card_image_layout.setVisibility(View.VISIBLE);
                    holder.days_layout.setVisibility(View.GONE);


                    if (coachAudioAdapterList.get(position).getActual_server_day() > 40) {
                        dailyGratitude.getCoachImage("",
                                Integer.parseInt(coachAudioAdapterList.get(position).getDay_number()) - 1);
                    }
                    //// calculation less than 40 days relax audio will come...
                    else {
                        dailyGratitude.getCoachImage("",
                                Integer.parseInt(coachAudioAdapterList.get(position).getDay_number()) - 10);
                    }
                    holder.coach_gratitude_img.setImageResource(R.drawable.relex_images);

                } else {
                    holder.card_image_layout.setVisibility(View.VISIBLE);
                    holder.days_layout.setVisibility(View.GONE);
                }
            }

            holder.coach_gratitude_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    overlayDialogButtonClicked = sharedPreferences.getBoolean(AppConstants.OVERLAY_DIALOG_BUTTON_CLICKED, false);
                    //Log.i("Coach", "****Over lay clicked "+overlayDialogButtonClicked);
                    if (coachAudioAdapterList.get(position) != null && coachAudioAdapterList.get(position).getAudioOrGratitude() != null) {
                        if (coachAudioAdapterList.get(position).getAudioOrGratitude().equalsIgnoreCase("AUDIO")
                                || coachAudioAdapterList.get(position).getAudioOrGratitude().equalsIgnoreCase("PLAY")) {

                            /*Analytics Activity Calling*/
                            END_DATE = df.format(Calendar.getInstance().getTime());
                            /*Analytics Activity Calling*/


                            //    if (CommonUtils.isNetworkAvailable(activity)) {

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

                        else if(coachAudioAdapterList.get(position).getAudioOrGratitude().equalsIgnoreCase("AFFIRMATION")) {
                            activity.startActivity(new Intent(activity, AffirmationScreen.class));
//                                                .putExtra("Affirmation_day", coachAudioAdapterList.get(position).getGratitudeDay() - 1));
                            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);

                        }
                        else if (coachAudioAdapterList.get(position).getAudioOrGratitude().equalsIgnoreCase("IMAGERY")) {
                            Log.i("Coach", "In imagery click");
                            /*Analytics Activity Calling*/
                            END_DATE = df.format(Calendar.getInstance().getTime());
                            if (coachAudioAdapterList.get(position).getSynced_flag() == 1) {


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

                        }    /// gratitude logic

                    }
                }
            });


        }

    }


    @Override
    public int getItemCount() {
        return coachAudioAdapterList.size();
    }

    @Override
    public void onClick(View v) {


    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView coach_card_view, card_image_layout;
        TextView coach_today_tv, coach_duration_tv, audio_name_tv, coach_begin_btn, coach_date_tv, coach_month_tv, audio_desc_text
                    , description_text,getTv_good_mrng;
        LinearLayout dd_mm_layout, days_layout,coach_gratitude_linear;
        TextView completed_tv, day_txt_tv;
        TextView tv_good_mrng;
        ImageView coach_gratitude_img,icon_morn,play_button;

        public ViewHolder(View itemView) {
            super(itemView);
            coach_card_view = (CardView) itemView.findViewById(R.id.coach_card_view);
            card_image_layout = (CardView) itemView.findViewById(R.id.card_image_layout);
            day_txt_tv = (TextView) itemView.findViewById(R.id.day_txt_tv);
            tv_good_mrng = (TextView) itemView.findViewById(R.id.tv_good_mrng);
            icon_morn = (ImageView) itemView.findViewById(R.id.icon_morn);

            coach_today_tv = (TextView) itemView.findViewById(R.id.coach_today_tv);
            coach_duration_tv = (TextView) itemView.findViewById(R.id.coach_duration_tv);
            audio_name_tv = (TextView) itemView.findViewById(R.id.audio_name_tv);
            coach_date_tv = (TextView) itemView.findViewById(R.id.coach_date_tv);
            coach_month_tv = (TextView) itemView.findViewById(R.id.coach_month_tv);
            completed_tv = (TextView) itemView.findViewById(R.id.completed_tv);
            coach_begin_btn = (Button) itemView.findViewById(R.id.coach_begin_btn);
            dd_mm_layout = (LinearLayout) itemView.findViewById(R.id.dd_mm_layout);
            days_layout = (LinearLayout) itemView.findViewById(R.id.days_layout);
            coach_gratitude_img = (ImageView) itemView.findViewById(R.id.coach_gratitude_img);
            audio_desc_text = itemView.findViewById(R.id.audio_desc_text);
            description_text = itemView.findViewById(R.id.description_text);
            coach_gratitude_linear = itemView.findViewById(R.id.coach_gratitude_linear);
            play_button = (ImageView) itemView.findViewById(R.id.play_button);
        }
    }

    public interface CoachOnClickListenerInterface {

        void reloadCoachOnClickListener();

        void showNetworkOnClickListener();

        void getFirstCardSize(int card_size, int list_size);
    }

}
