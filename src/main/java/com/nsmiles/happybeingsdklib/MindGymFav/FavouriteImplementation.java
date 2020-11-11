package com.nsmiles.happybeingsdklib.MindGymFav;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ImageView;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.MindGym.PlayRelaxAudioActivity;
import com.nsmiles.happybeingsdklib.MindGym.RelaxAudioAdapter;
import com.nsmiles.happybeingsdklib.MindGym.RelaxAudioModel;
import com.nsmiles.happybeingsdklib.MindGym.RelaxAudiosActivity;
import com.nsmiles.happybeingsdklib.MindGym.RelaxInterfaceListener;
import com.nsmiles.happybeingsdklib.MindGym.RelaxUtils;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by nSmiles on 12/16/2017.
 */

public class FavouriteImplementation implements FavouritePresenter {

    Activity activity;
    CommonUtils commonUtils;
    FavouriteView favouriteView;
    CommonAudio commonAudio;
    RecyclerView audio_recycle_view;
    SQLiteDatabase db;
    MySql dbHelper;
    RelaxUtils relaxUtils;
    List<RelaxAudioModel> relaxAudioModelList;
    RelaxAudioModel relaxAudio;
    RelaxAudioAdapter relaxAudioAdapter;
    List<String> audio_number_list;


    public FavouriteImplementation(Activity activity, FavouriteView favouriteView, RecyclerView audio_recycle_view) {

        this.activity = activity;
        this.favouriteView = favouriteView;
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
    public void loadFavouriteAudio() {

        loadFavouriteData();

        if (audio_number_list != null && audio_number_list.size() > 0) {
            relaxAudioModelList = new ArrayList<>();
            for (int i = 0; i < audio_number_list.size(); i++) {

                @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM relax_audio WHERE AUDIO_NUMBER=?",
                        new String[]{audio_number_list.get(i)});

                if (cursor.getCount() == 0) {
                    //relaxUtils.insertAllRelaxAudio(activity);
                }

                cursor = db.rawQuery("SELECT * FROM relax_audio WHERE AUDIO_NUMBER=?",
                        new String[]{audio_number_list.get(i)});

                if (cursor.getCount() > 0) {

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
                        relaxAudio.setFavourite("true");
                        relaxAudioModelList.add(relaxAudio);
                    }
                    while (cursor.moveToNext());
                }
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
        else {
            favouriteView.showEmptyFavouriteLayout();
        }
    }

    @Override
    public void goRelaxListActivity() {
        activity.startActivity(new Intent(activity, RelaxAudiosActivity.class));
        activity.finish();
    }
}