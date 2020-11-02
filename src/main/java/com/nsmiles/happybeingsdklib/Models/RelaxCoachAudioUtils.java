package com.nsmiles.happybeingsdklib.Models;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.MySql;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nSmiles on 12/20/2017.
 */

public class RelaxCoachAudioUtils {

    String id;
    String sync_flag;
    String audio_base_url;
    String audio_title;
    String audio_sub_url;
    String created_at;
    String updated_at;
    String audio_description;
    String download_status;
    String sd_card_path;
    String audio_number;
    int audio_image;
    String duration;
    String favourite;
    String day_number;
    List<RelaxCoachAudioUtils> relaxCoachAudioUtilsList;
    List<RelaxCoachAudioUtils> othersRelaxCoachAudioUtilsList;

    private SQLiteDatabase db;
    MySql dbHelper;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSync_flag() {
        return sync_flag;
    }

    public void setSync_flag(String sync_flag) {
        this.sync_flag = sync_flag;
    }

    public String getAudio_base_url() {
        return audio_base_url;
    }

    public void setAudio_base_url(String audio_base_url) {
        this.audio_base_url = audio_base_url;
    }

    public String getAudio_title() {
        return audio_title;
    }

    public void setAudio_title(String audio_title) {
        this.audio_title = audio_title;
    }

    public String getAudio_sub_url() {
        return audio_sub_url;
    }

    public void setAudio_sub_url(String audio_sub_url) {
        this.audio_sub_url = audio_sub_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getAudio_description() {
        return audio_description;
    }

    public void setAudio_description(String audio_description) {
        this.audio_description = audio_description;
    }

    public String getDownload_status() {
        return download_status;
    }

    public void setDownload_status(String download_status) {
        this.download_status = download_status;
    }

    public String getSd_card_path() {
        return sd_card_path;
    }

    public void setSd_card_path(String sd_card_path) {
        this.sd_card_path = sd_card_path;
    }

    public String getAudio_number() {
        return audio_number;
    }

    public void setAudio_number(String audio_number) {
        this.audio_number = audio_number;
    }

    public int getAudio_image() {
        return audio_image;
    }

    public void setAudio_image(int audio_image) {
        this.audio_image = audio_image;
    }

    public String getCoach_duration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public String getDay_number() {
        return day_number;
    }

    public void setDay_number(String day_number) {
        this.day_number = day_number;
    }


    private void RelaxCoachOthersAudioDetails() {

        othersRelaxCoachAudioUtilsList = new ArrayList<>();
        // 1. BELLY-BREATHING.mp3
        RelaxCoachAudioUtils r = new RelaxCoachAudioUtils();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_title("Mindful of breathing");
        r.setAudio_sub_url("01-Mindfulness-of-breathing.mp3");
        r.setAudio_description("A simple mindfulness meditation to focus your attention on your breathing");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("101");
        r.setDuration("10");
        r.setDay_number("0");
        r.setAudio_image(R.drawable.cloud_audio);
        othersRelaxCoachAudioUtilsList.add(r);

        // 2. 4-7-8-BREATHING.mp3
        r = new RelaxCoachAudioUtils();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_title("Breath counting-In");
        r.setAudio_sub_url("02-Breath-counting-inhale.mp3");
        r.setAudio_description("A mindfulness meditation to be mindful about inhaling breath");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("102");
        r.setDuration("7");
        r.setDay_number("1");
        r.setAudio_image(R.drawable.green_audio);
        othersRelaxCoachAudioUtilsList.add(r);

        // 3. Counting-Numbers.mp3
        r = new RelaxCoachAudioUtils();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_title("Breath counting-Out");
        r.setAudio_sub_url("03-Breath-counting-exhale.mp3");
        r.setAudio_description("A mindfulness meditation to be mindful about exhaling breathe");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("103");
        r.setDuration("9");
        r.setDay_number("2");
        r.setAudio_image(R.drawable.moutain_audio);
        othersRelaxCoachAudioUtilsList.add(r);

        // 4. Tense-and-relax.mp3
        r = new RelaxCoachAudioUtils();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_title("Breath counting together");
        r.setAudio_sub_url("04-Breath-counting-together.mp3");
        r.setAudio_description("A mindfulness meditation to be mindful about Inhalation and exhalation");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("104");
        r.setDuration("8");
        r.setDay_number("3");
        r.setAudio_image(R.drawable.wave_audio);
        othersRelaxCoachAudioUtilsList.add(r);

        // 5. Calmness-meditation.mp3
        r = new RelaxCoachAudioUtils();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_title("Mindful long breath");
        r.setAudio_sub_url("05-Mindful-Long-Breath.mp3");
        r.setAudio_description("A mindfulness meditation to be mindful about long breath");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("105");
        r.setDuration("8");
        r.setDay_number("4");
        r.setAudio_image(R.drawable.cloud_audio);
        othersRelaxCoachAudioUtilsList.add(r);

        // 6. self-comapssion-meditation.mp3
        r = new RelaxCoachAudioUtils();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_title("Mindful short breath");
        r.setAudio_sub_url("06-Mindful-Short-breath.mp3");
        r.setAudio_description("A mindfulness meditation to be mindful about short breath");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("106");
        r.setDuration("8");
        r.setDay_number("5");
        r.setAudio_image(R.drawable.green_audio);
        othersRelaxCoachAudioUtilsList.add(r);

        // 7. Mindful-Body-Scan.mp3
        r = new RelaxCoachAudioUtils();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_title("Breath awareness");
        r.setAudio_sub_url("07-Breath-awareness.mp3");
        r.setAudio_description("A mindful meditation to become aware of breathe");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("107");
        r.setDuration("10");
        r.setDay_number("6");
        r.setAudio_image(R.drawable.moutain_audio);
        othersRelaxCoachAudioUtilsList.add(r);

        // 8. Noisy-Breathing-exercise.mp3
        r = new RelaxCoachAudioUtils();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_title("Mindful breath sensations");
        r.setAudio_sub_url("08-Mindful-breath-sensations.mp3");
        r.setAudio_description("A mindful meditation to become aware of sensations");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("108");
        r.setDuration("9");
        r.setDay_number("7");
        r.setAudio_image(R.drawable.wave_audio);
        othersRelaxCoachAudioUtilsList.add(r);

        // 9. Following-the-Out-Breath.mp3
        r = new RelaxCoachAudioUtils();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_title("Following breath at two points");
        r.setAudio_sub_url("09-Following-breath-at-two-points.mp3");
        r.setAudio_description("A mindful meditation to become of aware breathe at two points");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("109");
        r.setDuration("9");
        r.setDay_number("8");
        r.setAudio_image(R.drawable.cloud_audio);
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 10*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Following breath at three points");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_sub_url("10-Following-breath-three-point.mp3");
        r.setAudio_description("A mindful meditation to become of aware breathe at three points");
        r.setAudio_number("3001");
        r.setDay_number("9");
        r.setDuration("9");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 11*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Following the whole breath");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_sub_url("11-Following-whole-breath.mp3");
        r.setAudio_description("A mindful meditation to become of aware whole breathe in and out of body");
        r.setAudio_number("3002");
        r.setDay_number("10");
        r.setDuration("10");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 12*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Mindful movement of breath");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_sub_url("12-Mindful-movement-of-breath.mp3");
        r.setAudio_description("A mindful meditation to become of aware mindful movement of breath in and out of body");
        r.setAudio_number("3003");
        r.setDay_number("11");
        r.setDuration("12");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 13*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Mindful symphony");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_sub_url("13-Mindful-symphony.mp3");
        r.setAudio_description("A mindful meditation that allows you to be present of the symphony of breathe");
        r.setAudio_number("3004");
        r.setDay_number("12");
        r.setDuration("11");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 14*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Mindful following of breath");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_sub_url("14-Mindful-following-of-breath.mp3");
        r.setAudio_description("A mindful meditation to follow movement of breathe in and out of body");
        r.setAudio_number("3005");
        r.setDay_number("13");
        r.setDuration("10");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 15*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Mindfulness of body");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_sub_url("15-Mindfulness-of-Body.mp3");
        r.setAudio_description("A mindful meditation to become aware of body");
        r.setAudio_number("3006");
        r.setDay_number("14");
        r.setDuration("9");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 16*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Mindfulness of posture");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_sub_url("16-Mindfulness-of-posture.mp3");
        r.setAudio_description("A mindful meditation to become aware of posture");
        r.setAudio_number("3007");
        r.setDay_number("15");
        r.setDuration("6");
        othersRelaxCoachAudioUtilsList.add(r);
        /*Day 17*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Eating mindfulness");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_sub_url("17-Eating-Mindfulness.mp3");
        r.setAudio_description("A mindfulness meditation to be mindful about eating mindfully");
        r.setAudio_number("3008");
        r.setDay_number("16");
        r.setDuration("6");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 18*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Body scan");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_sub_url("18-Body-scan.mp3");
        r.setAudio_description("A mindfulness meditation to be mindful of body sensations");
        r.setAudio_number("3009");
        r.setDay_number("17");
        r.setDuration("15");
        othersRelaxCoachAudioUtilsList.add(r);
        /*Day 19*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Walking mindfulness");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_sub_url("19-Walking-Mindfulness.mp3");
        r.setAudio_description("A mindfulness meditation to be mindful about walking");
        r.setAudio_number("3010");
        r.setDay_number("18");
        r.setDuration("9");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 20*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Mindfulness of truth");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_sub_url("21-Mindfulness-of-truth.mp3");
        r.setAudio_description("A mindfulness meditation to encourage you to be truthful honest and integral.");
        r.setAudio_number("3011");
        r.setDay_number("19");
        r.setDuration("10");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 21*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Mindfulness of distractions");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_sub_url("22-Mindfulness-of-distraction.mp3");
        r.setAudio_description("A mindfulness meditation to be present to distraction and be with it");
        r.setAudio_number("3012");
        r.setDay_number("20");
        r.setDuration("9");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 22*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Mindfulness of thoughts, body and sensations");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_sub_url("23-Thoughts-Body-Sensations-and-Emotions.mp3");
        r.setAudio_description("A mindfulness meditation to be present to thoughts, body and sensations");
        r.setAudio_number("3013");
        r.setDay_number("21");
        r.setDuration("10");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 23*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Basic component of walking");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_sub_url("basic_components_of_walking.mp3");
        r.setAudio_description("A mindfulness meditation to be mindful about walking");
        r.setAudio_number("3014");
        r.setDay_number("22");
        r.setDuration("9");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 24*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Mindfulness of physical discomfort");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_description("A mindfulness meditation to be mindful about physical discomfort");
        r.setAudio_sub_url("26-Mindfulness-of-physical-Discomfort.mp3");
        r.setAudio_number("3015");
        r.setDay_number("23");
        r.setDuration("7");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 25*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("5 senses of mindfulness");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_description("A mindfulness meditation to be mindful of 5 senses Sight, sound, smell, taste and touch");
        r.setAudio_sub_url("five_senses_of_mindfulness.mp3");
        r.setAudio_number("3016");
        r.setDay_number("24");
        r.setDuration("11");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 26*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Mountain meditation");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_sub_url("mountain_meditation.mp3");
        r.setAudio_description("A mindfulness meditation to cultivate stillness and calm and to connect with our inner strength and stability");
        r.setAudio_number("3017");
        r.setDay_number("25");
        r.setDuration("10");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 27*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Mindfulness of touch");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_description("A mindfulness meditation to bring awareness to the sensations on your skin");
        r.setAudio_sub_url("mindfulness_of_touch.mp3");
        r.setAudio_number("3018");
        r.setDay_number("26");
        r.setDuration("11");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 28*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Mindfulness of thoughts");
        r.setAudio_description("A mindfulness meditation to be mindful about thoughts");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_sub_url("mindfulness_of_thoughts.mp3");
        r.setAudio_number("3019");
        r.setDay_number("27");
        r.setDuration("8");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 29*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Basic mindful walking");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_description("A mindfulness meditation to consciously cultivate awareness about moving and walking");
        r.setAudio_sub_url("basic_mindful_walking.mp3");
        r.setAudio_number("3020");
        r.setDay_number("28");
        r.setDuration("11");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 30*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Mindful body movement");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_description("A mindfulness meditation to be mindful about body movements");
        r.setAudio_sub_url("mindful_body_movements.mp3");
        r.setAudio_number("3021");
        r.setDay_number("29");
        r.setDuration("15");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 31*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Mindfulness of greed");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_sub_url("32-Mindfulness-of-greed.mp3");
        r.setAudio_description("A mindfulness meditation to be mindful about greed");
        r.setAudio_number("3022");
        r.setDay_number("30");
        r.setDuration("8");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 32*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Mindfulness of pain");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_description("A mindfulness meditation to be mindful about pain");
        r.setAudio_sub_url("33-Mindfulness-of-pain.mp3");
        r.setAudio_number("3023");
        r.setDay_number("31");
        r.setDuration("8");
        othersRelaxCoachAudioUtilsList.add(r);

        /*Day 33*/
        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Mindfulness of being grateful");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/MindfullnessStudents/");
        r.setAudio_description("A mindfulness meditation to be mindfully expressing gratitude");
        r.setAudio_sub_url("mindful_body_scan.mp3");
        r.setAudio_number("3024");
        r.setDay_number("32");
        r.setDuration("8");
        othersRelaxCoachAudioUtilsList.add(r);

        r = new RelaxCoachAudioUtils();
        r.setAudio_title("Meditation to boost immunity");
        r.setAudio_description("Special guided imagery to deeply relax, visualise strengthening your body's ability to repair itself and empower body cells of your immune system to effectively activate, attack, and destroy any viruses and bacteria that enters or already present in your body.");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/common/");
        r.setAudio_sub_url("guided-imagery.m4a");
        r.setAudio_number("GUIDED_IMAGERY");
        r.setDay_number("535");
        r.setDuration("30");
        othersRelaxCoachAudioUtilsList.add(r);

    }


    public void insertAllOthersAudio(Context context) {
        dbHelper = new MySql(context, "mydb", null, MySql.version);
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM relax_coach_others", null);
        if(cursor.getCount()==0){

            RelaxCoachOthersAudioDetails();
            if(othersRelaxCoachAudioUtilsList!=null && othersRelaxCoachAudioUtilsList.size()>0) {
                for(int i=0;i<othersRelaxCoachAudioUtilsList.size();i++) {
                    cv.put("SYNC_FLAG", othersRelaxCoachAudioUtilsList.get(i).getSync_flag());
                    cv.put("AUDIO_BASE_URL", othersRelaxCoachAudioUtilsList.get(i).getAudio_base_url());
                    cv.put("AUDIO_SUB_URL", othersRelaxCoachAudioUtilsList.get(i).getAudio_sub_url());
                    cv.put("CREATED_AT", new Date().toString());
                    cv.put("UPDATED_AT", new Date().toString());
                    cv.put("AUDIO_TITLE", othersRelaxCoachAudioUtilsList.get(i).getAudio_title());
                    cv.put("AUDIO_DESCRIPTION", othersRelaxCoachAudioUtilsList.get(i).getAudio_description());
                    cv.put("DOWNLOAD_STATUS", "0");
                    cv.put("SD_CARD_PATH", "");
                    cv.put("DAY_NUMBER", othersRelaxCoachAudioUtilsList.get(i).getDay_number());
                    cv.put("AUDIO_NUMBER", othersRelaxCoachAudioUtilsList.get(i).getAudio_number());
                    cv.put("FILE_TYPE", "AUDIO");
                    cv.put("DURATION", othersRelaxCoachAudioUtilsList.get(i).getCoach_duration());
                    db.insert("relax_coach_others", null, cv);
                }
            }
        }

    }

}
