package com.nsmiles.happybeingsdklib.MindGym;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ImageView;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.MindGymFav.CommonAudio;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by nSmiles on 12/15/2017.
 */

public class RelaxImplementation implements RelaxAudioPresenter {

    Activity activity;
    CommonUtils commonUtils;
    RelaxView relaxView;
    CommonAudio commonAudio;
    RecyclerView audio_recycle_view;
    SQLiteDatabase db;
    MySql dbHelper;
    RelaxUtils relaxUtils;
    List<RelaxAudioModel> relaxAudioModelList;
    RelaxAudioModel relaxAudio;
    RelaxAudioAdapter relaxAudioAdapter;
    List<String> audio_number_list;

    public RelaxImplementation(Activity activity, RelaxView relaxView, RecyclerView audio_recycle_view) {

        this.activity = activity;
        this.relaxView = relaxView;
        this.audio_recycle_view = audio_recycle_view;
        commonAudio = new CommonAudio();
        commonUtils = new CommonUtils();
        relaxUtils = new RelaxUtils();
        dbHelper = new MySql(activity, "mydb", null, MySql.version);
        db = dbHelper.getWritableDatabase();
    }


    private void loadFavouriteData() {

        try {
            dbHelper = new MySql(activity, "mydb", null, MySql.version);
            db = dbHelper.getWritableDatabase();
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM relax_favourite WHERE EMAIL=?",
                    new String[]{commonUtils.getUserEmail(activity)});
            if (cursor.getCount() > 0) {
                audio_number_list = new ArrayList<>();
                cursor.moveToFirst();
                do{
                    audio_number_list.add(cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_NUMBER")));
                }
                while (cursor.moveToNext());

            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void loadRelaxAudio() {

        loadFavouriteData();

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM relax_audio", null);
        if (cursor.getCount() == 0) {
            //relaxUtils.insertAllRelaxAudio(activity);
        }
        if (cursor.getCount() > 0) {
            relaxAudioModelList = new ArrayList<>();
            cursor.moveToFirst();
            do {
                relaxAudio = new RelaxAudioModel();
                relaxAudio.setAudio_title(cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_TITLE")));
                relaxAudio.setAudio_image(cursor.getInt(cursor.getColumnIndexOrThrow("DRAWABLE_IMAGE")));
                relaxAudio.setAudio_base_url(cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_BASE_URL")));
                relaxAudio.setAudio_sub_url(cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_SUB_URL")));
                relaxAudio.setSd_card_path(cursor.getString(cursor.getColumnIndexOrThrow("SD_CARD_PATH")));
                relaxAudio.setDownload_status(cursor.getString(cursor.getColumnIndexOrThrow("DOWNLOAD_STATUS")));
                relaxAudio.setAudio_number(cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_NUMBER")));
                relaxAudio.setId(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("_ID"))));
                relaxAudio.setDuration(cursor.getString(cursor.getColumnIndexOrThrow("DURATION")));
                relaxAudio.setAudio_description(cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_DESCRIPTION")));

                if (audio_number_list!=null && audio_number_list.size() > 0) {
                    if (audio_number_list.contains(cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_NUMBER")))) {
                        relaxAudio.setFavourite("true");
                    } else {
                        relaxAudio.setFavourite("false");
                    }
                } else {
                    relaxAudio.setFavourite("false");
                }
                //CommonUtils.showLogInforamtion(getClass().getSimpleName(), "DRAWABLE_IMAGE", cursor.getString(cursor.getColumnIndexOrThrow("DRAWABLE_IMAGE")), false);
                relaxAudioModelList.add(relaxAudio);


            }
            while (cursor.moveToNext());
        }

        if (relaxAudioModelList.size() > 0) {
            audio_recycle_view.setHasFixedSize(false);
            //CommonUtils.showLogInforamtion(getClass().getSimpleName(), "choose relax audio size", relaxAudioModelList.size(), false);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
            audio_recycle_view.setLayoutManager(layoutManager);
            relaxAudioAdapter = new RelaxAudioAdapter(activity, relaxAudioModelList);
            audio_recycle_view.setAdapter(relaxAudioAdapter);
            relaxAudioAdapter.setRelaxInterfaceListener(new RelaxInterfaceListener() {
                @Override
                public void relaxOnItemClickListener(List<RelaxAudioModel> relaxAudioModels, int position) {
                    activity.startActivity(new Intent(activity, PlayRelaxAudioActivity.class)
                            .putExtra("AUDIO_ID", relaxAudioModelList.get(position).getId())
                            .putExtra("AUDIO_NUMBER", relaxAudioModelList.get(position).getAudio_number())
                            .putExtra("FAVOURITE", relaxAudioModelList.get(position).getFavourite())
                    );
                }

                @Override
                public void placeAudioData(CardView audio_card, ImageView fav_img, TextView audio_name, TextView audio_desc, TextView duration) {

                }
            });

        }

    }
}
