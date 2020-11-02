package com.nsmiles.happybeingsdklib.MindGym;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by nSmiles on 12/13/2017.
 */

public class RelaxActivityAdapter extends RecyclerView.Adapter<RelaxActivityAdapter.ViewHolder> {

    Activity activity;
    List<String> activity_list;
    int index;
    RelaxActivityOnClickListener relaxActivityOnClickListener;


    public RelaxActivityAdapter(List<String> activity_list, Activity activity) {

        this.activity_list = activity_list;
        this.activity = activity;
    }

    public void setRelaxActivityOnClickListener(RelaxActivityOnClickListener relaxActivityOnClickListener) {
        this.relaxActivityOnClickListener = relaxActivityOnClickListener;
    }

    @Override
    public RelaxActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.relax_activity_card_layout, parent, false);
        return new RelaxActivityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RelaxActivityAdapter.ViewHolder holder, int position) {

        holder.activity_name.setText(activity_list.get(position));

        if(position==0){
            holder.card_view.measure(0,0);
            relaxActivityOnClickListener.getFirstCardSize(holder.card_view.getMeasuredHeight(), activity_list.size());
        }


         if(activity_list.get(position).contains(AppConstants.RELAX_AUDIO)){
             holder.roundedImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.relax_music));
        }
        else if(activity_list.get(position).contains(AppConstants.AFFIRMATION)){
             holder.roundedImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.relax_affirmation));
        }
         /*else if(activity_list.get(position).contains(AppConstants.BREATHING_TECHIQUES)){
             holder.roundedImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.breething));
         }*/
        else if(activity_list.get(position).contains(AppConstants.DE_STRESS_JOURNAL)){
             holder.roundedImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.relax_write));
        }
        else if(activity_list.get(position).contains(AppConstants.ACTIVITIES)){
             holder.roundedImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.relax_activity));
        }
        else if(activity_list.get(position).contains(AppConstants.NATURE_CALM)){
             holder.roundedImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.nature_calm));
         }
         else if(activity_list.get(position).contains(AppConstants.VENT_RECORD)){
             holder.roundedImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.express_yourself));
         }
         else if(activity_list.get(position).contains(AppConstants.GRATITUDE_JOURNAL)){
             holder.roundedImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.gratitude_journal));
         }
        else {
             holder.roundedImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.relax_food));
        }


        if (relaxActivityOnClickListener != null) {
            relaxActivityOnClickListener.hideLastView(holder.myView, position, activity_list.size());
        }

    }

    @Override
    public int getItemCount() {
        return activity_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView card_view;
        TextView activity_name;
        View myView;
        ImageView roundedImageView;


        public ViewHolder(View itemView) {
            super(itemView);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            roundedImageView = (ImageView) itemView.findViewById(R.id.roundedImageView);
            activity_name = (TextView) itemView.findViewById(R.id.activity_name);
            myView = (View) itemView.findViewById(R.id.myView);
            card_view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            index = getAdapterPosition();

            if (relaxActivityOnClickListener != null) {

                relaxActivityOnClickListener.relaxActivityOnClickListener(activity_list, index);
            }
        }
    }
}
