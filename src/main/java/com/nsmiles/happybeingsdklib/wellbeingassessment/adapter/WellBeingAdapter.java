package com.nsmiles.happybeingsdklib.wellbeingassessment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.CorporateModel;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Sukumar on 5/9/2018.
 */

public class WellBeingAdapter extends RecyclerView.Adapter<WellBeingAdapter.ViewHolder> {

    private Context context;
    private List<CorporateModel> corporateModelList;
    private CardOnClickListener cardOnClickListener;

    public WellBeingAdapter(Context context, List<CorporateModel> corporateAdapterList) {

        this.context = context;
        this.corporateModelList = corporateAdapterList;

    }

    public void setCardOnClickListener(CardOnClickListener cardOnClickListener) {
        this.cardOnClickListener = cardOnClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_general,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        if(position==0){
//            holder.parent_layout.measure(0,0);
//            cardOnClickListener.getFirstCardSize(holder.parent_layout.getMeasuredHeight(), corporateModelList.size()+1);
//        }
        if (position==0){
            holder.coach_gratitude_img.setImageResource(R.drawable.family_c);
        }
        else if(position==1){
            holder.coach_gratitude_img.setImageResource(R.drawable.money_c);

        }
        else if(position==2){
            holder.coach_gratitude_img.setImageResource(R.drawable.work_c);

        }
        else if(position==3){
            holder.coach_gratitude_img.setImageResource(R.drawable.romantic_c);

        }
        else if(position==4){
            holder.coach_gratitude_img.setImageResource(R.drawable.health_c);

        }
        else if(position==5){
            holder.coach_gratitude_img.setImageResource(R.drawable.emotation_c);

        }
        else if(position==6){
            holder.coach_gratitude_img.setImageResource(R.drawable.relation_c);

        }
        else if(position==7){
            holder.coach_gratitude_img.setImageResource(R.drawable.spitual_c);

        }
        else if(position==8){
            holder.coach_gratitude_img.setImageResource(R.drawable.relation_c);
        }
        else if(position==9){
            holder.coach_gratitude_img.setImageResource(R.drawable.relation_c);
        }

        Log.i("WellbeingCategory", "IN bind view Time is "+corporateModelList.get(position).getAssessmentTime());
        holder.nameOfAssessement.setText(corporateModelList.get(position).getTitle());
//        if (corporateModelList.get(position).getAssessmentTime() != null)
//            holder.timeOfAssessment.setText(corporateModelList.get(position).getAssessmentTime());
//        holder.timeOfAssessment.setVisibility(View.VISIBLE);
//        if(corporateModelList.get(position).isAssessmentStatus()) {
//            holder.assessment_completed_status = true;
//            holder.startOrReportButton.setText("REPORT");
//        }
//        else {
//            holder.assessment_completed_status = false;
//            holder.startOrReportButton.setText("Start");
//
//            //holder.completed_not_img.setImageDrawable(context.getResources().getDrawable(R.drawable.payment_gray));
//        }
    }

    @Override
    public int getItemCount() {
        if (corporateModelList != null) {
            return corporateModelList.size();
        }else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RelativeLayout parent_layout;
        private TextView nameOfAssessement, timeOfAssessment;
        private ImageView coach_gratitude_img;
        private Button details_button, startOrReportButton;
        //private CardView card_view;
        private boolean assessment_completed_status = false;
        public ViewHolder(View itemView) {
            super(itemView);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            nameOfAssessement = itemView.findViewById(R.id.nameOfAssessement);
           // timeOfAssessment = itemView.findViewById(R.id.time_taken_text);
            coach_gratitude_img=itemView.findViewById(R.id.coach_gratitude_img);
          //  details_button = itemView.findViewById(R.id.details_button);
           // startOrReportButton = itemView.findViewById(R.id.start_button);
            //card_view = (CardView) itemView.findViewById(R.id.card_view);
            //card_view.setOnClickListener(this);
//            details_button.setOnClickListener(this);
//            startOrReportButton.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            cardOnClickListener.onDetailsButtonClicked(corporateModelList, getAdapterPosition());

        }

//        @Override
//        public void onClick(View v) {
//            int id = v.getId();
//
//            switch (id){
//               /*case R.id.card_view:
//                   cardOnClickListener.onClickListener(corporateModelList,getAdapterPosition());
//                   break;*/
//                case R.id.start_button:
//                    cardOnClickListener.onStartOrReportButtonClicked(corporateModelList, getAdapterPosition(), assessment_completed_status);
//                    break;
//                case R.id.details_button:
 //                   cardOnClickListener.onDetailsButtonClicked(corporateModelList, getAdapterPosition());
//                    break;
//            }
//        }
    }

    public interface CardOnClickListener {

        void onDetailsButtonClicked(List<CorporateModel> corporateModelList, int pos);

    }
}
