package com.nsmiles.happybeingsdklib.MindGym;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.view.View;

import com.nsmiles.happybeingsdklib.NatureCalm.NatureCalmActivity;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Affimations.AffirmationScreen;
import com.nsmiles.happybeingsdklib.MindGymFav.RelaxFavouritesActivity;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;


public class RelaxAffirmationImplementation implements RelaxAffirmationPresenter {

    private Activity activity;
    private RelaxView relaxView;
    private SimpleDateFormat today_dd;
    private SimpleDateFormat today_mm;
    private Date today_date;
    private String dd, mm;

    private CommonUtils commonUtils;
    private RelaxUtils relaxUtils;
    private String user_login_status, email_id, user_id;
    private Random audioRandomGenerator;
    private List<RelaxAudioModel> audioList;
    private SQLiteDatabase db;
    private MySql dbHelper;

    private List<RelaxAudioModel> relaxAudioModelList;
    private RelaxAudioModel relaxAudio;
    private RecyclerView audio_recycle_view;
    private RecyclerView relax_activity_recycle_view;
    private RelaxAffirmationAdapter homeAdapter;
    private RelaxActivityAdapter homeActivityAdapter;
    private String START_DATE = "", END_DATE = "";
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z", Locale.getDefault());
    private FragmentManager fragmentManager;
    //Activity
    private List<String> activity_list;

    private String[] activityData = new String[]{
            AppConstants.RELAX_AUDIO,
            AppConstants.ACTIVITIES,
            AppConstants.STRESS_RELIEF_FOOD,
            AppConstants.NATURE_CALM,
            AppConstants.VENT_RECORD
    };

    private String token;
    private int today, lastAudio;
    private String paymentStatus;
    private SharedPreferences prefs;
    private int audio_size = 0;
    private Random recommendRandomGenerator;
    private int index;
    private boolean is_Login = false;

    public RelaxAffirmationImplementation(Activity activity, FragmentManager fragmentManager, boolean isLogin, RelaxView relaxView, RecyclerView audio_recycle_view, RecyclerView relax_activity_recycle_view) {

        this.activity = activity;
        this.relaxView = relaxView;
        this.fragmentManager = fragmentManager;
        this.audio_recycle_view = audio_recycle_view;
        this.relax_activity_recycle_view = relax_activity_recycle_view;
        START_DATE = df.format(Calendar.getInstance().getTime());
        today_dd = new SimpleDateFormat("dd", Locale.ENGLISH);
        today_mm = new SimpleDateFormat("MMM", Locale.ENGLISH);
        today_date = new Date();
        dd = today_dd.format(today_date);
        mm = today_mm.format(today_date);
        commonUtils = new CommonUtils();
        dbHelper = new MySql(activity, "mydb", null, MySql.version);
        db = dbHelper.getWritableDatabase();
        //email_id = commonUtils.getUserEmail(activity);
        //user_id = commonUtils.getUserId(activity);
        relaxUtils = new RelaxUtils();
        this.is_Login = isLogin;
        activity_list = new ArrayList<>();
        activity_list.addAll(Arrays.asList(activityData));
        prefs = activity.getSharedPreferences("HAPPY_BEING", MODE_PRIVATE);
    }


    @Override
    public void
    displayKitkatIcon() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            relaxView.displayKitkat();
        } else {
            relaxView.hideKitkat();
        }
    }

    @Override
    public void getFavouritesCount() {
        try {
            dbHelper = new MySql(activity, "mydb", null, MySql.version);
            db = dbHelper.getWritableDatabase();
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM relax_favourite ", null);
            if (cursor.getCount() > 0) {
                audio_size = cursor.getCount();
                relaxView.setFavourites(String.valueOf(cursor.getCount()));
               // CommonUtils.CloseCursor(cursor);
            } else {
                relaxView.setFavourites("0");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void favouriteOnClickListener() {
        activity.startActivity(new Intent(activity, RelaxFavouritesActivity.class));
        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    @Override
    public void getTodayDDAndMM() {

        if (dd != null && mm != null) {

            relaxView.setDdDate(dd);
            relaxView.setMmMonth(mm);
        }
    }

    @Override
    public void getRandomRelaxAudio() {
        loadAudioList();
    }

    @Override
    public void loadActivityAdapter() {

        if (activity_list != null && activity_list.size() > 0) {
            relax_activity_recycle_view.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
            relax_activity_recycle_view.setLayoutManager(layoutManager);
            homeActivityAdapter = new RelaxActivityAdapter(activity_list, activity);
            relax_activity_recycle_view.setAdapter(homeActivityAdapter);
            homeActivityAdapter.setRelaxActivityOnClickListener(new RelaxActivityOnClickListener() {
                @Override
                public void getFirstCardSize(int card_size, int list_size) {
                    relax_activity_recycle_view.setMinimumHeight(card_size*(list_size + 1));
                }

                @Override
                public void hideLastView(View view, int position, int list_count) {

                    if (position == list_count - 1) {
                        view.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void relaxActivityOnClickListener(List<String> activity_list, int position) {
                    if (activity_list.get(position).equalsIgnoreCase(AppConstants.AFFIRMATION)) {
                        activity.startActivity(new Intent(activity, AffirmationScreen.class));
                        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);

                    } else if (activity_list.get(position).equalsIgnoreCase(AppConstants.RELAX_AUDIO)) {
                        activity.startActivity(new Intent(activity, RelaxAudiosActivity.class));
                        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    } else if (activity_list.get(position).equalsIgnoreCase(AppConstants.STRESS_RELIEF_FOOD)) {
                        activity.startActivity(new Intent(activity, RecommendFoodActivity.class));
                        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    } else if (activity_list.get(position).equalsIgnoreCase(AppConstants.ACTIVITIES)) {
                        getActivityData();
                    }
                    else if(activity_list.get(position).equalsIgnoreCase(AppConstants.NATURE_CALM)) {
                        activity.startActivity(new Intent(activity, NatureCalmActivity.class));
                        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    }
                    else if(activity_list.get(position).equalsIgnoreCase(AppConstants.VENT_RECORD)) {
                        activity.startActivity(new Intent(activity, VentoutRecording.class));
                        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    }
                }
            });

            relax_activity_recycle_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });

            relax_activity_recycle_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
        }
    }


    private void loadAudioList() {

        try {
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM relax_audio", null);
            if (cursor.getCount() == 0) {
                relaxUtils.insertAllRelaxAudio(activity);
            }
            cursor = db.rawQuery("SELECT * FROM relax_audio", null);
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
                    //CommonUtils.showLogInforamtion(getClass().getSimpleName(), "AUDIO_TITLE", cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_TITLE")), false);
                    relaxAudioModelList.add(relaxAudio);
                }
                while (cursor.moveToNext());
                CommonUtils.CloseCursor(cursor);
            }


            if (relaxAudioModelList != null && relaxAudioModelList.size() > 0) {

                audioRandomGenerator = new Random();
                relaxAudio = new RelaxAudioModel();
                relaxAudio = getRandomAudioFile();
                audioList = new ArrayList<>();
                audioList.add(relaxAudio);

                audio_recycle_view.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
                audio_recycle_view.setLayoutManager(layoutManager);
                homeAdapter = new RelaxAffirmationAdapter(checkFavourite(relaxAudio.getAudio_number()), is_Login, audioList, activity);
                audio_recycle_view.setAdapter(homeAdapter);
                homeAdapter.notifyDataSetChanged();// Notify the adapter
                homeAdapter.setRelaxPlayAudioOnClickListener(new RelaxPlayAudioOnClickListener() {
                    @Override
                    public void PlayAudioOnClickListener(String fav, List<RelaxAudioModel> relaxAudioModelList, int position) {
                        activity.startActivity(new Intent(activity, PlayRelaxAudioActivity.class)
                                .putExtra("AUDIO_ID", relaxAudioModelList.get(position).getId())
                                .putExtra("AUDIO_NUMBER", relaxAudioModelList.get(position).getAudio_number())
                                .putExtra("FAVOURITE", fav)


                        );
                        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String checkFavourite(String audio_number) {

        String favorite = "false";
        try {
            dbHelper = new MySql(activity, "mydb", null, MySql.version);
            db = dbHelper.getWritableDatabase();
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM relax_favourite ", null);
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                favorite = cursor.getString(cursor.getColumnIndexOrThrow("FAVOURITE"));
                CommonUtils.CloseCursor(cursor);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return favorite;
    }

    private RelaxAudioModel getRandomAudioFile() {
        int index = audioRandomGenerator.nextInt(relaxAudioModelList.size());
        RelaxAudioModel item = relaxAudioModelList.get(index);
        System.out.println("Managers choice this week" + item + "our recommendation to you");
        return item;
    }

    @Override
    public void getActivityData() {
        String type, title;
        int image_id;
        relaxUtils = new RelaxUtils();
        recommendRandomGenerator = new Random();
        index = recommendRandomGenerator.nextInt(relaxUtils.activitySize());

        type = relaxUtils.getActivityType(index);
        title = relaxUtils.getActivityTitle(index);

        if (type.equalsIgnoreCase("IMAGE")) {

            if (title.equalsIgnoreCase("Stretching to relax your muscles")) {
                image_id = R.drawable.streach_background;
            } else if (title.equalsIgnoreCase("Have a alone time to collect your thoughts and clear your head")) {
                image_id = R.drawable.have_a_alone_time;
            } else {
                image_id = R.drawable.make_a_free_zone_backgorund;
            }

            activity.startActivity(new Intent(activity, ImageScreen.class)
                    .putExtra("IMAGE_ID", image_id)

            );
            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
        else if (type.equalsIgnoreCase("FOOD")) {

            activity.startActivity(new Intent(activity, RecommendFoodActivity.class)
                    .putExtra("FROM_ACTIVITY", "ACTIVITY")
                    .putExtra("DATA", title)
            );
            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }

    }
}
