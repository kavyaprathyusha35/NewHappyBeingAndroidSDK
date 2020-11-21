package com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.dagger.data.PreferenceManager;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by nSmiles on 12/8/2017.
 */

public class GratitudeListAdapter extends RecyclerView.Adapter<GratitudeListAdapter.ViewHolder> {

   private List<String> gratitudeList;
    private Activity activity;
    private ExpressOnClickListener expressOnClickListener;
    private int row_index=0;
    PreferenceManager preferenceManager;
    String payment_status;

    public GratitudeListAdapter(Activity activity, List<String> gratitudeList) {
        this.activity = activity;
        this.gratitudeList = gratitudeList;
        preferenceManager=new PreferenceManager(activity);
        payment_status=preferenceManager.get(AppConstants.PAYMENT_STATUS,"");
    }

    public void setExpressOnClickListener(ExpressOnClickListener expressOnClickListener){
        this.expressOnClickListener = expressOnClickListener;
    }

    @Override
    public GratitudeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_gratitude,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GratitudeListAdapter.ViewHolder holder, int position) {

        if(position == 0){
            holder.card_view.measure(0,0);
            expressOnClickListener.getFirstCardSize(holder.card_view.getMeasuredHeight(), gratitudeList.size());
        }

        try {
            holder.name_tv.setText(gratitudeList.get(position));

            if(payment_status!=null && payment_status.equalsIgnoreCase("EXPIRED")){

                if(gratitudeList.get(position).equalsIgnoreCase(AppConstants.GRATITUDE_JOURNAL)){
                    holder.premium.setVisibility(View.VISIBLE);

                }
            } else{
                holder.premium.setVisibility(View.GONE);
            }

            if(expressOnClickListener!=null){
                expressOnClickListener.hideLastViewLine(holder.myView, position , gratitudeList.size(), gratitudeList, holder.gratitude_img);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return gratitudeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView card_view;
        LinearLayout complete_layout;
        TextView name_tv;
        ImageView gratitude_img,premium;
        View myView;

        public ViewHolder(View itemView) {
            super(itemView);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            complete_layout = (LinearLayout) itemView.findViewById(R.id.complete_layout);
            name_tv = (TextView) itemView.findViewById(R.id.name_tv);
            gratitude_img = (ImageView) itemView.findViewById(R.id.gratitude_img);
            myView = (View) itemView.findViewById(R.id.myView);
            card_view.setOnClickListener(this);
            premium = (ImageView) itemView.findViewById(R.id.premium);

        }

        @Override
        public void onClick(View v) {
            if(expressOnClickListener != null){
                row_index = getAdapterPosition();
                expressOnClickListener.actionExpressOnClickListener(gratitudeList,row_index);
            }
        }
    }

    public interface ExpressOnClickListener{
        void getFirstCardSize(int card_size, int list_size);
        void actionExpressOnClickListener(List<String> getGratitudeList, int position);
        void hideLastViewLine(View myView, int position, int totalCount, List<String> getGratitudeList, ImageView imageView);
    }
}
