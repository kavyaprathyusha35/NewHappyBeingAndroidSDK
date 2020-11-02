package com.nsmiles.happybeingsdklib.NatureCalm;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nsmiles.happybeingsdklib.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Sukumar on 6/5/2018.
 */

public class NatureAdapter extends RecyclerView.Adapter<NatureAdapter.ViewHolder> {

    private String[] url;
    private Activity activity;
    private Animation animation;

    public NatureAdapter(Activity activity, String[] url){
        this.activity = activity;
        this.url = url;
        animation = AnimationUtils.loadAnimation(activity, R.anim.center_in);
    }

    @Override
    public NatureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.nature_adapter,parent,false);
        return new NatureAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NatureAdapter.ViewHolder holder, int position) {

        Glide.with(activity)
                .load(url[position])
                .into(holder.nature_img);


        holder.nature_img.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return url.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView nature_img;


        public ViewHolder(View itemView) {
            super(itemView);

            nature_img = (ImageView) itemView.findViewById(R.id.nature_img);
        }
    }
}
