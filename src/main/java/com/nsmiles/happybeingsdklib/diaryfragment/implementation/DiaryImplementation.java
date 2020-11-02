package com.nsmiles.happybeingsdklib.diaryfragment.implementation;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.UI.HomeScreenActivity;
import com.nsmiles.happybeingsdklib.UI.gratitude.ExpressGratitudeSelfLove;
import com.nsmiles.happybeingsdklib.UI.gratitude.ExpressGratitudeToday;
import com.nsmiles.happybeingsdklib.UI.gratitude.HappyMomentView;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;
import com.nsmiles.happybeingsdklib.diaryfragment.DiaryData;
import com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.fragment.DiaryDynamicFragment;
import com.nsmiles.happybeingsdklib.diaryfragment.presenter.DiaryPresenter;
import com.nsmiles.happybeingsdklib.diaryfragment.utils.CalenderView;
import com.nsmiles.happybeingsdklib.diaryfragment.view.DiaryView;
import com.nsmiles.happybeingsdklib.gratitude.activity.GratitudeDailyActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by Sukumar on 1/19/2018.
 */

public class DiaryImplementation implements DiaryPresenter {

    private Activity activity;
    private CommonUtils commonUtils;
    private SQLiteDatabase db;
    private MySql dbHelper;
    private String user_email;
    private DiaryView diaryView;
    private List<DiaryData> viewMyDiaryList;
    private HashSet<Date> events;

    private List<String> dateList;
    private ViewPager mineViewPager;
    private FragmentManager fm;
    private List<Fragment> fragments;
    private TabLayout myTabLayout;
    private DiaryData d;
    private ViewPagerAdapter viewPagerAdapter;
    private Cursor diary_cursor;
    private String SelectedDate = "";
    private int move_position;
    private final int REQUEST_JOURNAL = 55555;
    private int gratitude_date = 0;

    public DiaryImplementation(Activity activity, int move_position, FragmentManager fm, DiaryView diaryView, ViewPager viewPager, TabLayout myTabLayout, int gratitude_date) {

        this.activity = activity;
        this.diaryView = diaryView;
        this.myTabLayout = myTabLayout;
        dbHelper = new MySql(activity, "mydb", null, MySql.version);
        db = dbHelper.getWritableDatabase();
        commonUtils = new CommonUtils();
        this.mineViewPager = viewPager;
        this.move_position = move_position;
        this.fm = fm;
        this.gratitude_date = gratitude_date;
        user_email = commonUtils.getUserEmail(activity);
        events = new HashSet<>();
        dateList = new ArrayList<>();
    }

    @Override
    public void displayKitkatIcon() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            diaryView.displayKitkat();
        } else {
            diaryView.hideKitkat();
        }
    }

    @Override
    public void loadViewPagerData() {

        viewPagerAdapter = new ViewPagerAdapter(fm);
        fragments = new ArrayList<>();
        try {

            diary_cursor = db.rawQuery("SELECT * FROM New_Gratitude_Table where email_id=? AND type_of_gratitude!=? AND syncFlag NOT LIKE '2%' ORDER BY datetime(date_time) DESC",
                    new String[]{user_email, "CRUSH"}, null);

            viewMyDiaryList = new ArrayList<>();
            if (viewMyDiaryList.size() > 0) {
                viewMyDiaryList.clear();
            }
            Log.i("Gratitude", "Count in table is "+diary_cursor.getCount());
            if (diary_cursor.getCount() > 0) {
                diary_cursor.moveToLast();
                do {
                    d = new DiaryData();
                    d.setAudio_id(diary_cursor.getInt(diary_cursor.getColumnIndexOrThrow("_id")));
                    d.setId(diary_cursor.getString(diary_cursor.getColumnIndexOrThrow("id")));
                    d.setUser_id(diary_cursor.getString(diary_cursor.getColumnIndexOrThrow("user_id")));
                    d.setEmail(diary_cursor.getString(diary_cursor.getColumnIndexOrThrow("email_id")));
                    d.setCreatedAt(diary_cursor.getString(diary_cursor.getColumnIndexOrThrow("createdAt")));
                    d.setUpdatedAt(diary_cursor.getString(diary_cursor.getColumnIndexOrThrow("updatedAt")));
                    d.setDate_time(diary_cursor.getString(diary_cursor.getColumnIndexOrThrow("date_time")));
                    d.setType_of_gratitude(diary_cursor.getString(diary_cursor.getColumnIndexOrThrow("type_of_gratitude")));
                    d.setTitle(diary_cursor.getString(diary_cursor.getColumnIndexOrThrow("title")));
                    d.setDescription(diary_cursor.getString(diary_cursor.getColumnIndexOrThrow("description")));
                    d.setSubject(diary_cursor.getString(diary_cursor.getColumnIndexOrThrow("subject")));
                    d.setLink(diary_cursor.getString(diary_cursor.getColumnIndexOrThrow("link")));
                    d.setPic_byte(diary_cursor.getBlob(diary_cursor.getColumnIndexOrThrow("pic")));
                    d.setExpress_your_gratitude(diary_cursor.getString(diary_cursor.getColumnIndexOrThrow("express_your_gratitude")));
                    d.setCustom_date(d.getDate_time());
                    viewPagerAdapter.addDynamicFragment(new DiaryDynamicFragment().newInstance(d.getType_of_gratitude(), d));
                    dateList.add(CommonUtils.DDMMYYYYString(d.getDate_time()));
                    Log.d("dddd", String.valueOf(CommonUtils.DDMMYYYY(d.getDate_time())));
                    events.add(CommonUtils.DDMMYYYY(d.getDate_time()));
                    viewMyDiaryList.add(d);

                }
                while (diary_cursor.moveToPrevious());


            }
            CommonUtils.CloseCursor(diary_cursor);
            if (viewMyDiaryList != null && viewMyDiaryList.size() > 0) {
                setupViewPager(mineViewPager);
            }

            mineViewPager.setCurrentItem(move_position, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPause() {
        try {
            if (fragments != null && fragments.size() > 0) {
                FragmentTransaction ft = fm.beginTransaction();
                for (Fragment f : fragments) {
                    //You can perform additional check to remove some (not all) fragments:
                    if (f instanceof DiaryDynamicFragment) {
                        ft.remove(f);
                    }
                }
                ft.commitAllowingStateLoss();
                viewPagerAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setupViewPager(final ViewPager viewPager) {

        try {
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(myTabLayout));
            myTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addDynamicFragment(Fragment fragment) {
            fragments.add(fragment);
        }


    }

    @Override
    public void addGratitudeData() {
        //gardenHomeImplementation.loadSelectedFragment(new ExpressTabFragment(), null, delayTime, true);
    }

    @Override
    public void editDiaryData() {

        // Fragment page = fm.findFragmentByTag("android:switcher:" + R.id.myViewPager + ":" + mineViewPager.getCurrentItem());
        try {
            Log.d("type of gratitude", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getType_of_gratitude());
            switch (viewMyDiaryList.get(mineViewPager.getCurrentItem()).getType_of_gratitude()) {

                case "EXPRESS_GRATITUDE":

                    activity.startActivityForResult(new Intent(activity, ExpressGratitudeToday.class)
                            .putExtra("NAME", AppConstants.EXPRESS_GRATITUDE)
                            .putExtra(AppConstants.MOVE_POSITION, mineViewPager.getCurrentItem())
                            .putExtra("HIDE", "RECOMMEND")
                            .putExtra("GRATITUDE_DATE", gratitude_date)
                            .putExtra("SELF", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getDescription())
                            .putExtra("ID", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getAudio_id())
                            .putExtra("DATE_TIME", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getDate_time()), REQUEST_JOURNAL);
                    break;

                case "SELF_LOVE":
                    activity.startActivityForResult(new Intent(activity, ExpressGratitudeSelfLove.class)
                            .putExtra("NAME", AppConstants.TO_OTHERS)
                            .putExtra(AppConstants.MOVE_POSITION, mineViewPager.getCurrentItem())
                            .putExtra("ID", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getAudio_id())
                            .putExtra("GRATITUDE_DATE", gratitude_date)
                            .putExtra("SELF", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getDescription())
                            .putExtra("DATE_TIME", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getDate_time()), REQUEST_JOURNAL);
                    break;

                case "HAPPY_MOMENT":

                    activity.startActivityForResult(new Intent(activity, HappyMomentView.class)
                            .putExtra(AppConstants.MOVE_POSITION, mineViewPager.getCurrentItem())
                            .putExtra("ID", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getAudio_id())
                            .putExtra("GRATITUDE_DATE", gratitude_date)
                            .putExtra("AUDIO_ID", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getAudio_id())
                            .putExtra("MOMENT", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getDescription())
                            .putExtra("FEELINGS", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getTitle())
                            .putExtra("DATE_TIME", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getDate_time()), REQUEST_JOURNAL);
                    break;

                case "DAILY_GRATITUDE":
                    if (viewMyDiaryList.get(mineViewPager.getCurrentItem()).getExpress_your_gratitude().length() == 0) {
                        viewMyDiaryList.get(mineViewPager.getCurrentItem()).setExpress_your_gratitude(viewMyDiaryList.get(mineViewPager.getCurrentItem()).getTitle());
                    }
                    if (viewMyDiaryList.get(mineViewPager.getCurrentItem()).getSubject().length() == 0) {
                        viewMyDiaryList.get(mineViewPager.getCurrentItem()).setSubject("1");
                    }
                    activity.startActivity(new Intent(activity, GratitudeDailyActivity.class)
                            .putExtra("DATE_TIME", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getDate_time())
                            .putExtra("ID", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getAudio_id())
                            .putExtra("DAY", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getSubject())
                            .putExtra("GRATITUDE_DATE", gratitude_date)
                            .putExtra("GENDER", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getLink())
                            .putExtra("TITLE", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getDescription())
                            .putExtra("DATA", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getExpress_your_gratitude())

                    );
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }





    @Override
    public void deleteDiaryData() {

        try {
            int current = mineViewPager.getCurrentItem()-1;

            Log.d("type of gratitude", viewMyDiaryList.get(mineViewPager.getCurrentItem()).getType_of_gratitude());
            ContentValues cv = new ContentValues();
            cv.put("syncFlag", "2");
            db.update("New_Gratitude_Table", cv, "date_time=?", new String[]{viewMyDiaryList.get(mineViewPager.getCurrentItem()).getDate_time()});
            MediaPlayer mediaPlayer = MediaPlayer.create(activity, R.raw.trash);
            mediaPlayer.start();

            activity.startActivity(new Intent(activity, HomeScreenActivity.class)
            .putExtra("FROM_SCREEN", "JOURNAL"));
            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            activity.finish();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void calenderEntry() {

        try {
            CalenderView cv;

            final Dialog d = new Dialog(activity);
            d.setContentView(R.layout.calendar_view);
            cv = ((CalenderView) d.findViewById(R.id.calendar_view));
            d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            d.setCancelable(true);
            d.setCanceledOnTouchOutside(true);
            cv.updateCalendar(events);
            cv.setEventHandler(new CalenderView.EventHandler() {
                @Override
                public void onDayLongPress(Date date, int position) {
                    // show returned day
                    DateFormat df = SimpleDateFormat.getDateInstance();
                    try {
                        SelectedDate = CommonUtils.DDMMYYYYString(date.toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < dateList.size(); i++) {

                        if (dateList.get(i).equalsIgnoreCase(SelectedDate)) {
                            mineViewPager.setCurrentItem(i, true);
                            Log.d("database date", dateList.get(i));
                            Log.d("database selected date", df.format(date));

                            break;
                        }
                    }

                    d.dismiss();
                }

            });

            d.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
