package com.nsmiles.happybeingsdklib.MindGym;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Services.DownloadService;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class RelaxUtils {

    private SQLiteDatabase db;
    MySql dbHelper;
    List<RelaxAudioModel> relaxAudioModelList;
    private SharedPreferences prefs;
    private String token_id = "";

    String[] recommend_food_list = new String[]{

            /*1*/    "Hydrate yourself with some pure coconut water - lower your stress, tension and anxiety.",
            /*2*/    "Drink hot or cold milk - lower your stress and get the energy boost.",
            /*3*/    "Sip green tea and relieve the stress.",
            /*4*/    "Fresh vegetable juice for stress and anxiety relief.",
            /*5*/    "Have a whole fruit and relieve from tension.",
            /*6*/    "Have a square of dark chocolate - boost your mood and reduce stress.",
            /*7*/    "Try a spoonful of honey - reduce stress and anxiety.",
            /*8*/    "Take a chewing gum - reduce anxiety and improve mood.",
            /*9*/    "Handful of nuts like organic pistachios, cashew nuts, walnuts, almonds, raisins.",
            /*10*/    "Munch a crunchy snack and ease your stress.",
            /*11*/    "Have  a vegetable sandwich with complex carbs like vegetables, fruits, beans etc and ease out from stress.",
            /*12*/    "Yogurt helps reduce stress and also will help fight the monthly, stressful symptoms of PMS.",
            /*13*/    "Smell some citrus fruits - oranges, lemons - reduce stress and feel energised.",
            /*14*/    "Smell some coffee beans and let the smell of coffee soothe you.",
            /*15*/    "Have a fresh fruit salad and let the sweetness of fruits energise you.",
            /*16*/    "Have an orange juice to reduce stress.",
            /*17*/    "Have a whole fruit and relieve from tension.",
            /*18*/    "Take a whole grain bread with jam or honey to reduce stress.",
            /*19*/    "Have an ice cream or any sugary foods. Feel the good effect.",
            /*20*/    "Have some broccoli. Relieve from stress, worry or anxiety.",
            /*21*/    "Munch a handful of peanuts to reduce stress.",
            /*22*/    "Have some hot soup to reduce stress.",


    };

    String[] type = new String[]{

            /*1*/   "IMAGE",
            /*2*/   "IMAGE",
            /*3*/   "IMAGE",
            /*7*/   "FOOD",
            /*8*/   "FOOD",
            /*9*/   "FOOD",
            /*10*/  "FOOD",
            /*11*/  "FOOD",
            /*12*/  "FOOD",
            /*13*/  "FOOD",
            /*14*/  "FOOD",
            /*15*/  "FOOD",
            /*16*/  "FOOD",
            /*17*/  "FOOD",
            /*18*/  "FOOD",
            /*19*/  "FOOD"


    };

    String[] title = new String[]{

            /*1*/   "Stretching to relax your muscles",
            /*2*/   "Have a alone time to collect your thoughts and clear your head",
            /*3*/   "Make or create a stress free zone and relax there for a while till you feel calm",
            /*4*/   "Write a gratitude journal",
            /*5*/   "Express your thoughts or journal",
            /*6*/   "Vent your burdens and disappointments",
            /*7*/   "Drop some cold water on your wrists and behind your earlobes feel the sense of calmness",
            /*8*/   "Pinch the length of earlobes and feel sense of relaxation",
            /*9*/   "Brush your hair and feel the sensations of relaxation",
            /*10*/  "Rub Your Feet over a golf or tennis ball - feel a nice self massage",
            /*11*/  "Squeeze a stress ball and beat the stress away",
            /*12*/  "Head outside and get some bright sun - feel better",
            /*13*/  "Take a five-minute break to do nothing but stare out the window to a natural scene like a park or a tree or kids playing or simply at sky and enjoy the feel of relaxation",
            /*14*/  "Take a time out to de-clutter your working desk - be more productive and focused in your work",
            /*15*/  "Dance to your favourite song - relax as you tap your legs and move your body in tune with the music",
            /*16*/  "Walk for 50 steps and grab some water while you clear your thoughts",
            /*17*/  "Smell some flowers and let the fragrance soothe, relieve stress & anxiety and promote rest",
            /*18*/  "Dab some essential oil on your palm and inhale for beating stress",
            /*19*/  "Cuddle a pet, reduce stress, loneliness and anxiety"
    };


    private String activity_type;
    private String activity_title;


    public String getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(String type) {
        this.activity_type = type;
    }


    public String getActivity_title() {
        return activity_title;
    }

    public void setActivity_title(String title) {
        this.activity_title = activity_title;
    }


    public void insertAllRelaxAudio(Context context) {
        String audio_base_url;
        String audio_sub_url;
        String audio_number;

        dbHelper = new MySql(context, "mydb", null, MySql.version);
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM relax_audio", null);
        if (cursor.getCount() == 0) {
            // Insert Audio File Details Into Database
            // Call the Creation method.
            relaxAudioDetails();

            // Checking the list size and inserting the value.
            if (relaxAudioModelList != null && relaxAudioModelList.size() > 0) {

                for (int i = 0; i < relaxAudioModelList.size(); i++) {

                    cv.put("SYNC_FLAG", relaxAudioModelList.get(i).getSync_flag());
                    cv.put("AUDIO_BASE_URL", relaxAudioModelList.get(i).getAudio_base_url());
                    cv.put("AUDIO_SUB_URL", relaxAudioModelList.get(i).getAudio_sub_url());
                    cv.put("CREATED_AT", new Date().toString());
                    cv.put("UPDATED_AT", new Date().toString());
                    cv.put("AUDIO_TITLE", relaxAudioModelList.get(i).getAudio_title());
                    cv.put("AUDIO_DESCRIPTION", relaxAudioModelList.get(i).getAudio_description());
                    cv.put("DOWNLOAD_STATUS", relaxAudioModelList.get(i).getDownload_status());
                    cv.put("SD_CARD_PATH", relaxAudioModelList.get(i).getSd_card_path());
                    cv.put("DRAWABLE_IMAGE", relaxAudioModelList.get(i).getAudio_image());
                    cv.put("AUDIO_NUMBER", relaxAudioModelList.get(i).getAudio_number());
                    cv.put("DURATION", relaxAudioModelList.get(i).getDuration());
                    cv.put("DAY_NUMBER", relaxAudioModelList.get(i).getDay_number());
                    cv.put("FILE_TYPE", "AUDIO");
                    db.insert("relax_audio", null, cv);
                }
            }
        }
        // Already data available check any new files added in the list or not
        else {
            cursor.moveToFirst();
            do {

                audio_number = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_NUMBER"));
                audio_base_url = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_BASE_URL"));
                audio_sub_url = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_SUB_URL"));
              /*  CommonUtils.showLogInforamtion(getClass().getSimpleName(), "AUDIO_NUMBER", audio_number, false);
                CommonUtils.showLogInforamtion(getClass().getSimpleName(), "URL", audio_base_url + audio_sub_url, false);
                CommonUtils.showLogInforamtion(getClass().getSimpleName(), "AUDIO_BASE_URL", audio_base_url, false);
                CommonUtils.showLogInforamtion(getClass().getSimpleName(), "AUDIO_SUB_URL", audio_sub_url, false);
*/            }
            while (cursor.moveToNext());
        }


        // @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM relax_audio WHERE DOWNLOAD_STATUS=?", new String[]{"0"});

        if (CommonUtils.isNetworkAvailable(context)) {
            downloadAllRelaxAudioFiles(context);
        }
    }

    private void downloadAllRelaxAudioFiles(Context context) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra("RELAX", "RELAX");
        context.startService(intent);

    }

    private void relaxAudioDetails() {
        relaxAudioModelList = new ArrayList<>();
        // 1. BELLY-BREATHING.mp3 cloud
        RelaxAudioModel r = new RelaxAudioModel();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/");
        r.setAudio_title("De-stress and relax instantly");
        r.setAudio_sub_url("BELLY-BREATHING.mp3");
        r.setAudio_description("Simple breathing exercise to help you instantly relax and de-stress.");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("101");
        r.setDuration("5");
        r.setDay_number(1);
        r.setAudio_image(R.drawable.de_stress_and_relax_instantly);
        relaxAudioModelList.add(r);

        // 2. 4-7-8-BREATHING.mp3  fire
        r = new RelaxAudioModel();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/");
        r.setAudio_title("Feeling angry or upset ? Listen to this audio");
        r.setAudio_sub_url("4-7-8-BREATHING.mp3");
        r.setAudio_description("Get any negative emotions out of your system with this simple meditation.");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("102");
        r.setDuration("4");
        r.setDay_number(2);
        r.setAudio_image(R.drawable.feeling_angry_or_upset);
        relaxAudioModelList.add(r);

        // 3. Counting-Numbers.mp3 mountains
        r = new RelaxAudioModel();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/");
        r.setAudio_title("Overcome fear and anxiety");
        r.setAudio_sub_url("Counting-Numbers.mp3");
        r.setAudio_description("Guided meditation for you to reduce anxiety before that big event or to manage that thing not in your control.");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("103");
        r.setDuration("3");
        r.setDay_number(3);
        r.setAudio_image(1);
        relaxAudioModelList.add(r);

        // 4. Tense-and-relax.mp3  garden with flowers
        r = new RelaxAudioModel();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/");
        r.setAudio_title("Relieve tension instantly");
        r.setAudio_sub_url("Tense-and-relax.mp3");
        r.setAudio_description("Feel rejuvenated with this guided meditation to relieve from pain and tension.");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("104");
        r.setDuration("5");
        r.setDay_number(4);
        r.setAudio_image(R.drawable.relieve_tension_instantly);
        relaxAudioModelList.add(r);

        // 5. Calmness-meditation.mp3  landscape
        r = new RelaxAudioModel();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/");
        r.setAudio_title("Feeling overwhelmed? Listen to this audio");
        r.setAudio_sub_url("Calmness-meditation.mp3");
        r.setAudio_description("For that few moments of peace and calm on an overwhelming day.");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("105");
        r.setDuration("5");
        r.setDay_number(5);
        r.setAudio_image(R.drawable.feeling_overwelmed);
        relaxAudioModelList.add(r);

        // 6. self-comapssion-meditation.mp3  flowers in a tree
        r = new RelaxAudioModel();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/");
        r.setAudio_title("Feeling sad or low? Listen to this audio");
        r.setAudio_sub_url("self-comapssion-meditation.mp3");
        r.setAudio_description("We sometimes get too tough on ourselves and need to realise you are awesome! Simple guided meditation to help you feel better.");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("106");
        r.setDuration("5");
        r.setDay_number(6);
        r.setAudio_image(R.drawable.feeling_sad_or_low);
        relaxAudioModelList.add(r);

        // 7. Mindful-Body-Scan.mp3  massage
        r = new RelaxAudioModel();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/");
        r.setAudio_title("Relieve from body pains and tension");
        r.setAudio_sub_url("Mindful-Body-Scan.mp3");
        r.setAudio_description("Release body tension with this simple guided meditation especially after a hectic day.");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("107");
        r.setDuration("5");
        r.setDay_number(7);
        r.setAudio_image(R.drawable.relieve_from_body_pains_and_tension);
        relaxAudioModelList.add(r);

        // 8. Noisy-Breathing-exercise.mp3  stars
        r = new RelaxAudioModel();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/");
        r.setAudio_title("Drift to peaceful deep sleep");
        r.setAudio_sub_url("Noisy-Breathing-exercise.mp3");
        r.setAudio_description("A must do if are finding difficult to fall asleep. Also, helps you improve the quality of your sleep to rest your mind well.");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("108");
        r.setDuration("8");
        r.setDay_number(8);
        r.setAudio_image(R.drawable.de_stress_and_relax_instantly);
        relaxAudioModelList.add(r);

        // 9. Following-the-Out-Breath.mp3  tree with fruits
        r = new RelaxAudioModel();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/");
        r.setAudio_title("Improve concentration and get focused");
        r.setAudio_sub_url("Following-the-Out-Breath.mp3");
        r.setAudio_description("Simple meditation to perform anywhere to reduce the noise in your mind and help concentrate better on the positive / task on hand.");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("109");
        r.setDuration("3");
        r.setDay_number(9);
        r.setAudio_image(R.drawable.improve_concentration_and_get_focused);
        relaxAudioModelList.add(r);

        // 10. lullaby-jasper.mp3  water
        r = new RelaxAudioModel();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/");
        r.setAudio_title("Relax through soothing music");
        r.setAudio_sub_url("lullaby-jasper.mp3");
        r.setAudio_description("If you are too tired or find hard to do a guided meditation, this soothing music will help you relieve from stress and relax.");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("110");
        r.setDuration("4");
        r.setDay_number(10);
        r.setAudio_image(R.drawable.relax_through_soothing_music);
        relaxAudioModelList.add(r);

        // 11. harmony-music.mp3  	musical notes
        r = new RelaxAudioModel();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/");
        r.setAudio_title("Relax through pleasant tune");
        r.setAudio_sub_url("harmony-music.mp3");
        r.setAudio_description("Rejuvenate through this soothing and pleasant music.");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("111");
        r.setDuration("1");
        r.setDay_number(11);
        r.setAudio_image(R.drawable.relax_through_pleasant_tune);
        relaxAudioModelList.add(r);

        // 12. symphony-music.mp3  musical instruments
        r = new RelaxAudioModel();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/");
        r.setAudio_title("Relax through gentle music");
        r.setAudio_sub_url("symphony-music.mp3");
        r.setAudio_description("Soothing music to help you relax easily and prepare you for a guided meditation that may like to do after this.");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("112");
        r.setDuration("2");
        r.setDay_number(12);
        r.setAudio_image(R.drawable.relax_through_gentle_music);
        relaxAudioModelList.add(r);

        // 13. Ocean-Waves-Loop.mp4  water waves
        r = new RelaxAudioModel();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/");
        r.setAudio_title("Relax with soft ocean waves");
        r.setAudio_sub_url("Ocean-Waves-Loop.mp4");
        r.setAudio_description("The sound on a calm beach does wonders to help you relax. Grab a headphone and close your eyes as this soft ocean waves play on.");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("113");
        r.setDuration("1");
        r.setDay_number(13);
        r.setAudio_image(R.drawable.relax_as_you_listen_to_soft_ocean_waves);
        relaxAudioModelList.add(r);

        // 14. nature_calms.mp3  trees, flowers and birds
        r = new RelaxAudioModel();
        r.setSync_flag("0");
        r.setAudio_base_url("https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/");
        r.setAudio_title("Relax with soothing birds tweet");
        r.setAudio_sub_url("nature_calms.mp3");
        r.setAudio_description("Listen to soothing birds sounds and relax: Nature helps the best to relax. Grab a headphone and close your eyes as this sound track helps you relax and relieve.");
        r.setSd_card_path("");
        r.setDownload_status("0");
        r.setAudio_number("114");
        r.setDuration("4");
        r.setDay_number(14);
        r.setAudio_image(R.drawable.relax_with_soothing_birds_tweet);
        relaxAudioModelList.add(r);

    }




    public int recommendFoodSize() {
        return recommend_food_list.length;
    }


    public String getRecommend_food_list(int random) {
        String randomFoodData = "";
        randomFoodData = recommend_food_list[random];
        return randomFoodData;
    }


    public int activitySize() {
        return type.length;
    }

    public String getActivityTitle(int position) {
        return title[position];
    }

    public String getActivityType(int position) {
        return type[position];
    }

    public String getTokenId(Activity activity) {

        prefs = activity.getSharedPreferences("HAPPY_BEING", MODE_PRIVATE);
        token_id = prefs.getString("user_token", "");
        return token_id;
    }


}