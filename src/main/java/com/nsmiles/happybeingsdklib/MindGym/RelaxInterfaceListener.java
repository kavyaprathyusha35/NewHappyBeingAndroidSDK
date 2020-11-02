package com.nsmiles.happybeingsdklib.MindGym;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.cardview.widget.CardView;

/**
 * Created by nSmiles on 12/15/2017.
 */

public interface RelaxInterfaceListener {

    void relaxOnItemClickListener(List<RelaxAudioModel> relaxAudioModels, int position);
    void placeAudioData(CardView audio_card, ImageView fav_img, TextView audio_name, TextView audio_desc, TextView duration);
}
