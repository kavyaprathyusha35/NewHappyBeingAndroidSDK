package com.nsmiles.happybeingsdklib.MindGym;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Sukumar on 8/30/2017.
 */

public class RelaxAudioAdapter extends RecyclerView.Adapter<RelaxAudioAdapter.RelaxAudioViewHolder> {

    private Activity activity;
    private List<RelaxAudioModel> relaxAudioModelList;
    RelaxInterfaceListener relaxInterfaceListener;
    int index;

    public RelaxAudioAdapter(Activity activity, List<RelaxAudioModel> relaxAudioModelList) {
        this.activity = activity;
        this.relaxAudioModelList = relaxAudioModelList;

    }

    public void setRelaxInterfaceListener(RelaxInterfaceListener relaxInterfaceListener) {
        this.relaxInterfaceListener = relaxInterfaceListener;
    }

    @Override
    public RelaxAudioAdapter.RelaxAudioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_relax_audio, parent, false);
        return new RelaxAudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RelaxAudioAdapter.RelaxAudioViewHolder holder, final int position) {

        try {

            holder.duration_tv.setText(relaxAudioModelList.get(position).getDuration()+" Min");
            holder.audio_name_tv.setText(relaxAudioModelList.get(position).getAudio_title());
            holder.audio_desc_tv.setText(relaxAudioModelList.get(position).getAudio_description());
            setDrawableImage(holder.roundedImageView, relaxAudioModelList.get(position).getAudio_title());
            if (relaxAudioModelList.get(position).getFavourite().equalsIgnoreCase("true")) {
                holder.fav_img.setVisibility(View.VISIBLE);
                holder.fav_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_fav_filled));
            } else {
                //holder.fav_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_fav));
                holder.fav_img.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void setDrawableImage(ImageView roundedImageView, String audio_title) {
        switch (audio_title) {
            case "De-stress and relax instantly":
                roundedImageView.setImageDrawable(activity.getDrawable(R.drawable.de_stress_and_relax_instantly));
                break;
            case "Feeling angry or upset ? Listen to this audio":
                roundedImageView.setImageDrawable(activity.getDrawable(R.drawable.feeling_angry_or_upset));
                break;
            case "Relieve tension instantly":
                roundedImageView.setImageDrawable(activity.getDrawable(R.drawable.relieve_tension_instantly));
                break;
            case "Relax through sounds of gong":
                roundedImageView.setImageDrawable(activity.getDrawable(R.drawable.feeling_overwelmed));
                break;
            case "Relieve from body pains and tension":
                roundedImageView.setImageDrawable(activity.getDrawable(R.drawable.relieve_from_body_pains_and_tension));
                break;
            case "Improve concentration and get focused":
                roundedImageView.setImageDrawable(activity.getDrawable(R.drawable.improve_concentration_and_get_focused));
                break;
            case "Relax through soothing music":
                roundedImageView.setImageDrawable(activity.getDrawable(R.drawable.relax_through_soothing_music));
                break;
            case "Relax through pleasant tune":
                roundedImageView.setImageDrawable(activity.getDrawable(R.drawable.relax_through_pleasant_tune));
                break;
            case "Relax through gentle music":
                roundedImageView.setImageDrawable(activity.getDrawable(R.drawable.relax_through_gentle_music));
                break;
            case "Relax with soft ocean waves":
                roundedImageView.setImageDrawable(activity.getDrawable(R.drawable.relax_as_you_listen_to_soft_ocean_waves));
                break;
            case "Relax with soothing birds tweet":
                roundedImageView.setImageDrawable(activity.getDrawable(R.drawable.relax_with_soothing_birds_tweet));
                break;
        }
    }


    @Override
    public int getItemCount() {
        return relaxAudioModelList.size();
    }

    public class RelaxAudioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView audio_card_view;
        ImageView fav_img,roundedImageView;
        TextView audio_name_tv, audio_desc_tv, duration_tv;

        public RelaxAudioViewHolder(View itemView) {
            super(itemView);

            audio_card_view = (CardView) itemView.findViewById(R.id.audio_card_view);
            fav_img = (ImageView) itemView.findViewById(R.id.fav_img);
            roundedImageView = (ImageView) itemView.findViewById(R.id.roundedImageView);
            audio_name_tv = (TextView) itemView.findViewById(R.id.audio_name_tv);
            audio_desc_tv = (TextView) itemView.findViewById(R.id.audio_desc_tv);
            duration_tv = (TextView) itemView.findViewById(R.id.duration_tv);
            audio_card_view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (relaxInterfaceListener != null) {
                index = getAdapterPosition();
                relaxInterfaceListener.relaxOnItemClickListener(relaxAudioModelList, index);
            }
        }
    }
}
