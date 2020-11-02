package com.nsmiles.happybeingsdklib.diaryfragment;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.mycoachfragment.DailyGratitude;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyDiaryAdapter extends RecyclerView.Adapter<MyDiaryAdapter.DiaryHolder> {

    private Activity activity;
    private DiaryData diaryDataList;
    CommonUtils commonUtils;
    DailyGratitude dailyGratitude;
    DiaryData diaryData;

    public MyDiaryAdapter(Activity activity, DiaryData diaryDataList) {
        this.activity = activity;
        this.diaryDataList = diaryDataList;
        commonUtils = new CommonUtils();
    }


    @Override
    public MyDiaryAdapter.DiaryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_dairy_layout, parent, false);
        return new DiaryHolder(view);
    }


    @Override
    public void onViewRecycled(DiaryHolder holder) {
        Log.d("action", "onViewRecycled");
        super.onViewRecycled(holder);
    }


    @Override
    public void onBindViewHolder(final MyDiaryAdapter.DiaryHolder holder, final int position) {


        try {
            holder.setIsRecyclable(false);
            Log.d(getClass().getSimpleName(), diaryDataList.getType_of_gratitude());
            Log.d("action", "onBindViewHolder");

            if (diaryDataList.getType_of_gratitude().contains("SELF_LOVE")) {
                CommonUtils.assignProfilePic(activity, "SELF", "", "", holder.gratitude_img);
                diaryData = new DiaryData();
                diaryData.setAudio_id(diaryDataList.getAudio_id());
                diaryData.setEmail(diaryDataList.getEmail());
                diaryData.setDate_time(diaryDataList.getDate_time());
                diaryData.setType_of_gratitude("SELF_LOVE");
                diaryData.setDescription(diaryDataList.getDescription());


            } else if (diaryDataList.getType_of_gratitude().contains("EXPRESS_GRATITUDE")) {
                CommonUtils.assignProfilePic(activity, "ABOUT", "", "", holder.gratitude_img);
                diaryData = new DiaryData();
                diaryData.setAudio_id(diaryDataList.getAudio_id());
                diaryData.setEmail(diaryDataList.getEmail());
                diaryData.setDate_time(diaryDataList.getDate_time());
                diaryData.setDescription(diaryDataList.getDescription());
                diaryData.setType_of_gratitude("EXPRESS_GRATITUDE");


            } else if (diaryDataList.getType_of_gratitude().contains("HAPPY_MOMENT")) {
                String base = new String(diaryDataList.getPic_byte());
                CommonUtils.convertBase64toBitmap(activity, base, holder.gratitude_img);
                diaryData = new DiaryData();
                diaryData.setAudio_id(diaryDataList.getAudio_id());
                diaryData.setEmail(diaryDataList.getEmail());
                diaryData.setDate_time(diaryDataList.getDate_time());
                diaryData.setAudio_id(diaryDataList.getAudio_id());
                diaryData.setDescription(diaryDataList.getDescription());
                diaryData.setTitle(diaryDataList.getTitle());
                diaryData.setPic_byte(diaryDataList.getPic_byte());
                diaryData.setType_of_gratitude("HAPPY_MOMENT");

            } else if (diaryDataList.getType_of_gratitude().contains("DAILY_GRATITUDE")) {
                dailyGratitude = new DailyGratitude();
                String day = diaryDataList.getSubject();
                if (day.length() == 0) {
                    day = "1";
                }
                dailyGratitude.getDailyData("", Integer.parseInt(day), "", "");
                holder.gratitude_img.setImageResource(dailyGratitude.getGratitude_image());
                diaryData = new DiaryData();
                diaryData.setAudio_id(diaryDataList.getAudio_id());
                diaryData.setEmail(diaryDataList.getEmail());
                diaryData.setDate_time(diaryDataList.getDate_time());
                if (diaryDataList.getExpress_your_gratitude().length() == 0) {
                    diaryData.setExpress_your_gratitude(diaryDataList.getTitle());
                } else {
                    diaryData.setExpress_your_gratitude(diaryDataList.getExpress_your_gratitude());
                }
                diaryData.setTitle(diaryDataList.getDescription());
                diaryData.setPic(diaryDataList.getPic());
                diaryData.setLink(diaryDataList.getLink());
                diaryData.setType_of_gratitude("DAILY_GRATITUDE");

            }


            if (diaryDataList.getType_of_gratitude().contains("DAILY_GRATITUDE")) {
                if (diaryDataList.getExpress_your_gratitude().length() == 0) {
                    holder.user_desc_tv.setText(diaryDataList.getTitle());
                } else {
                    holder.user_desc_tv.setText(diaryDataList.getExpress_your_gratitude());
                }

            } else {


                if (diaryDataList.getType_of_gratitude().contains("HAPPY_MOMENT")) {

                    holder.user_desc_tv.setText("Because: "+diaryDataList.getDescription());
                    holder.user_title_tv.setVisibility(View.VISIBLE);
                    holder.user_title_tv.setText("Feeling: "+diaryDataList.getTitle());

                } else{

                    holder.user_desc_tv.setText(diaryDataList.getDescription());
                }


            }

            CommonUtils.DDDateFormat(diaryDataList.getCustom_date(), holder.day_number_tv);
            CommonUtils.MMDateFormat(diaryDataList.getCustom_date(), holder.day_month_tv);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class DiaryHolder extends RecyclerView.ViewHolder {

        CardView diary_card_view;
        LinearLayout complete_layout;
        TextView day_number_tv, day_month_tv, user_desc_tv,user_title_tv;
        ImageView gratitude_img;

        public DiaryHolder(View itemView) {
            super(itemView);

            diary_card_view = (CardView) itemView.findViewById(R.id.diary_card_view);
            complete_layout = (LinearLayout) itemView.findViewById(R.id.complete_layout);
            day_number_tv = (TextView) itemView.findViewById(R.id.day_number_tv);
            day_month_tv = (TextView) itemView.findViewById(R.id.day_month_tv);
            user_desc_tv = (TextView) itemView.findViewById(R.id.user_desc_tv);
            gratitude_img = (ImageView) itemView.findViewById(R.id.gratitude_img);
            user_title_tv = (TextView) itemView.findViewById(R.id.user_title_tv);

        }
    }

}

