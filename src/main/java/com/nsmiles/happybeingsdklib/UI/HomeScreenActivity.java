package com.nsmiles.happybeingsdklib.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.MindGym.RelaxAffirmationFragment;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.UI.gratitude.HappyMomentView;
import com.nsmiles.happybeingsdklib.Utils.ImageFilePath;
import com.nsmiles.happybeingsdklib.Utils.SdkPreferenceManager;
import com.nsmiles.happybeingsdklib.diaryfragment.fragment.DiaryFragment;
import com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.fragment.GratitudeJournalListFragment;
import com.nsmiles.happybeingsdklib.mycoachfragment.fragment.CoachGratitudeFragment;
import com.nsmiles.happybeingsdklib.network.Service;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by Kavya on 4/14/2020.
 */
public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String MESSAGE_PROGRESS = "message_progress";
    public static boolean viewJournalClicked;
    private Toolbar toolbar;
    private TextView header_name,header_email,toolbar_tv;
    private ImageView reminder_icon;
    private DrawerLayout mDrawerLayout;
    private ExpandableListView expandableList;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private SdkPreferenceManager preferenceManager;
    private LinearLayout my_coach_layout, journal_layout, mindsapa_layout;
    private boolean coachSelected = true, journalSelected = false, mindSpaSelected = false;
    private ImageView home_image, journal_image, mindSpa_image;
    private FragmentManager fragmentManager;
    private int randomIntValue;
    private FragmentTransaction fragmentTransaction;
    private Bundle bundle;
    private String value;
    private static final int REQUEST_CAMERA = 1001;
    int IMAGE_CODE = 2002;

    @Inject
    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new_home_screen);



        value= null;
        if (getIntent().hasExtra("key")) {
            value = getIntent().getStringExtra("key");
        }
        if (value != null) {
            Log.i("gogo",value);
        }

        setStatusBarColor(this);
        bundle = new Bundle();
        toolbar = findViewById(R.id.toolbar);
        toolbar_tv = toolbar.findViewById(R.id.toolbar_tv);
        reminder_icon = findViewById(R.id.reminder_icon);
        toolbar.bringToFront();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        my_coach_layout = findViewById(R.id.home_layout);
        journal_layout = findViewById(R.id.my_journal_layout);
        mindsapa_layout = findViewById(R.id.mind_spa_layout);

        home_image = findViewById(R.id.home_image);
        journal_image = findViewById(R.id.journal_image);
        mindSpa_image = findViewById(R.id.mind_spa_image);

        preferenceManager = new SdkPreferenceManager(this);
        fragmentManager = getSupportFragmentManager();
        Intent intent = getIntent();
        if (intent.hasExtra("FROM_SCREEN") && intent.getStringExtra("FROM_SCREEN") != null) {
            String fromFragment = intent.getStringExtra("FROM_SCREEN");
            assert fromFragment != null;
            loadBeforeFragment(fromFragment);
        } else{
            coachSelected = true;
            journalSelected = false;
            mindSpaSelected = false;
            fragmentManager.beginTransaction().replace(R.id.new_screen_fragment_layout, new CoachGratitudeFragment()).commit();
            fragmentManager.popBackStack("CoachFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        int min = 1, max = 5;
        randomIntValue = new Random().nextInt((max - min) + 1) + min;
        updateBottomMenuSelected();


        my_coach_layout.setOnClickListener(this);
        mindsapa_layout.setOnClickListener(this);
        journal_layout.setOnClickListener(this);


        if (value != null && "value".contains(value)) {
            Log.i("gogo1", "testingVisible");
            goToGratitudeJournalListFragment();
        }

        reminder_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, SettingsLayout.class);
                if (coachSelected) {
                    intent.putExtra("FROM_SCREEN", "CoachReminders");
                } else if (mindSpaSelected) {
                    intent.putExtra("FROM_SCREEN", "MindSpaRminders");
                }
                startActivity(intent);
            }
        });

    }

    public static void setStatusBarColor(Activity activity) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(activity.getResources().getColor(R.color.app_blue));

    }

    private void loadBeforeFragment(String fragmentString) {
        Log.i("HomeScreen","IN load before fragment "+fragmentString);
        Fragment fragment = null;
        bundle = new Bundle();
        bundle.putBoolean("IS_LOGIN", true);
        if (fragmentString.equals("JOURNAL")) {
            coachSelected = false;
            journalSelected = true;
            mindSpaSelected = false;
            fragment = new DiaryFragment();
        }
        if (fragment != null) {
            updateBottomMenuSelected();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.new_screen_fragment_layout, fragment).commit();
            fragmentManager.popBackStack("fakeFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onPauseStarted","dbSyncCheck");
    }

    private void updateBottomMenuSelected() {
        if (mindSpaSelected) {
            toolbar_tv.setText("MindSpa");
            home_image.setBackground(getDrawable(R.drawable.home_icon));
            journal_image.setBackground(getDrawable(R.drawable.journal_icon));
            mindSpa_image.setBackground(getDrawable(R.drawable.mind_spa_selected));
            mindsapa_layout.setBackground(getDrawable(R.drawable.coach_back_new));
            my_coach_layout.setBackgroundColor(getResources().getColor(R.color.transparent));
            journal_layout.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
        else if (coachSelected) {
            toolbar_tv.setText("My Coach");
            home_image.setBackground(getDrawable(R.drawable.home_selected));
            journal_image.setBackground(getDrawable(R.drawable.journal_icon));
            mindSpa_image.setBackground(getDrawable(R.drawable.mind_spa_icon));
            mindsapa_layout.setBackgroundColor(getResources().getColor(R.color.transparent));
            my_coach_layout.setBackground(getDrawable(R.drawable.coach_back_new));
            journal_layout.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
        else if (journalSelected) {
            toolbar_tv.setText("My Journal");
            home_image.setBackground(getDrawable(R.drawable.home_icon));
            journal_image.setBackground(getDrawable(R.drawable.journal_selected));
            mindSpa_image.setBackground(getDrawable(R.drawable.mind_spa_icon));
            mindsapa_layout.setBackgroundColor(getResources().getColor(R.color.transparent));
            my_coach_layout.setBackgroundColor(getResources().getColor(R.color.transparent));
            journal_layout.setBackground(getDrawable(R.drawable.coach_back_new));
        }
        setBackgroundImageBasedOnRandom();
    }

    private void setBackgroundImageBasedOnRandom() {
        Log.i("HomeScreen", "Random int is "+randomIntValue);
        switch (randomIntValue) {
            case 1:
                if (mindSpaSelected)
                    mDrawerLayout.setBackground(getDrawable(R.drawable.mind_spa_background_1));
                else if (journalSelected)
                    mDrawerLayout.setBackground(getDrawable(R.drawable.my_journal_background_1));
                else if (coachSelected)
                    mDrawerLayout.setBackground(getDrawable(R.drawable.home_background_1));
                break;
            case 2:
                if (mindSpaSelected)
                    mDrawerLayout.setBackground(getDrawable(R.drawable.mind_spa_background_2));
                else if (journalSelected)
                    mDrawerLayout.setBackground(getDrawable(R.drawable.my_journal_background_2));
                else if (coachSelected)
                    mDrawerLayout.setBackground(getDrawable(R.drawable.home_background_2));
                break;
            case 3:
                if (mindSpaSelected)
                    mDrawerLayout.setBackground(getDrawable(R.drawable.mind_spa_background_3));
                else if (journalSelected)
                    mDrawerLayout.setBackground(getDrawable(R.drawable.my_journal_background_3));
                else if (coachSelected)
                    mDrawerLayout.setBackground(getDrawable(R.drawable.home_background_3));
                break;
            case 4:
                if (mindSpaSelected)
                    mDrawerLayout.setBackground(getDrawable(R.drawable.mind_spa_background_4));
                else if (journalSelected)
                    mDrawerLayout.setBackground(getDrawable(R.drawable.my_journal_background_4));
                else if (coachSelected)
                    mDrawerLayout.setBackground(getDrawable(R.drawable.home_background_4));
                break;
            case 5:
                if (mindSpaSelected)
                    mDrawerLayout.setBackground(getDrawable(R.drawable.mind_spa_background_5));
                else if (journalSelected)
                    mDrawerLayout.setBackground(getDrawable(R.drawable.my_journal_background_5));
                else if (coachSelected)
                    mDrawerLayout.setBackground(getDrawable(R.drawable.home_background_5));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Fragment fragment = new Fragment();
        if (id == R.id.mind_spa_layout) {
            recordfirebaseAnalatics("Relax clicked");
            mindSpaSelected = true;
            coachSelected = false;
            journalSelected = false;
            fragment = new RelaxAffirmationFragment();
            reminder_icon.setVisibility(View.VISIBLE);
        } else if (id == R.id.home_layout) {
            recordfirebaseAnalatics("My coach clicked");
            //appEventsLogger.logEvent("My coach clicked");
            mindSpaSelected = false;
            coachSelected = true;
            journalSelected = false;
            fragment = new CoachGratitudeFragment();
            reminder_icon.setVisibility(View.VISIBLE);
        } else if (id == R.id.my_journal_layout) {
            recordfirebaseAnalatics("Journal clicked");
            mindSpaSelected = false;
            coachSelected = false;
            journalSelected = true;
            fragment = new DiaryFragment();
            reminder_icon.setVisibility(View.INVISIBLE);
        }
        updateBottomMenuSelected();
        loadSelectedFragment(fragment, bundle, true);
    }

    private void recordfirebaseAnalatics(String event_name) {
    }

    @SuppressLint("LongLogTag")
    public void loadSelectedFragment(Fragment fragment, Bundle bundle, boolean animate) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {

            fragmentTransaction = fragmentManager.beginTransaction();
            if (animate) {
                fragmentTransaction.setCustomAnimations(R.anim.right_in, R.anim.left_out);
            }
            if (bundle != null)
                fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.new_screen_fragment_layout, fragment, fragment.getClass().getSimpleName());
            fragmentTransaction.commitAllowingStateLoss();

        } else{

            fragmentTransaction = fragmentManager.beginTransaction();
            if (animate) {
                fragmentTransaction.setCustomAnimations(R.anim.right_in, R.anim.left_out);
            }
            if (bundle != null)
                fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.new_screen_fragment_layout, fragment, fragment.getClass().getSimpleName());
            fragmentTransaction.commit();


        }
    }

    public void goToGratitudeJournalListFragment() {
        try {
            HomeScreenActivity.viewJournalClicked = true;
            Fragment fragment = new GratitudeJournalListFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.new_screen_fragment_layout, fragment, fragment.getClass().getSimpleName());
            //fragmentTransaction.addToBackStack("FLAG_JOURNAL");
            fragmentTransaction.commit();

            coachSelected = false;
            mindSpaSelected = false;
            journalSelected = true;
            updateBottomMenuSelected();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK && data != null) {
            try {
                if (data.getExtras() != null) {
                    Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                    assert imageBitmap != null;
                    Uri uri = getImageUri(imageBitmap);
                    String realPath = ImageFilePath.getPath(this, uri);
                    startActivity(new Intent(this, HappyMomentView.class).putExtra("IMAGE_PATH", realPath));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("HomeScreen","Camera Error "+e.toString());
                Log.e(getClass().getSimpleName(), e.toString());
            }
        }
        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK && null != data) {
            Log.i("GratitudeFragment", "In image result ok ");
            try {
                Uri uri = data.getData();
                String realPath = ImageFilePath.getPath(HomeScreenActivity.this, uri);
                startActivity(new Intent(HomeScreenActivity.this, HappyMomentView.class)
                        .putExtra("IMAGE_PATH", realPath));
            } catch (Exception e) {
                Log.i("GratitudeFragment", "@@@@@@@@ Exception occured @@@@@@@@@@@@@@@@@@@@@@");
                e.printStackTrace();
                Log.e(getClass().getSimpleName(), e.toString());
            }
        }

    }
    public Uri getImageUri( Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


}
