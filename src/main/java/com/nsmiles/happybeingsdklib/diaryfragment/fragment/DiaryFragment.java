package com.nsmiles.happybeingsdklib.diaryfragment.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.UI.MyCoachDescription;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.implementation.ExpressImplementation;
import com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.view.ExpressView;
import com.nsmiles.happybeingsdklib.diaryfragment.implementation.DiaryImplementation;
import com.nsmiles.happybeingsdklib.diaryfragment.view.DiaryView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class DiaryFragment extends Fragment implements DiaryView, ExpressView, View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    Activity activity;
    Button addGratitudeBtn;
    DiaryView diaryView;
    DiaryImplementation diaryImplementation;
    private ViewPager mineViewPager;
    FragmentManager fm;

    TabLayout myTabLayout;
    ImageView calender,editDelete;
    PopupMenu popup;
    int move_position=0;
    private int gratitudeDate = 0;
    ExpressView expressView;
    ExpressImplementation expressImplementation;
    RecyclerView recycle_view;
    int IMAGE_CODE = 2002;
    private static final int REQUEST_CAMERA = 1001;
    private TextView info_text,descriptionOfCoach;
    private RelativeLayout add_image_layout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_fragment_layout,container,false);
        addGratitudeBtn = (Button) view.findViewById(R.id.addGratitudeBtn);
        addGratitudeBtn.setOnClickListener(this);

        add_image_layout = view.findViewById(R.id.add_image_layout);
        add_image_layout.setOnClickListener(this);

        calender = (ImageView) view.findViewById(R.id.calender);
        editDelete = (ImageView) view.findViewById(R.id.editDelete);
        mineViewPager = (ViewPager) view.findViewById(R.id.myViewPager);
        fm = getChildFragmentManager();
        myTabLayout = (TabLayout) view.findViewById(R.id.myTabLayout);
        descriptionOfCoach=view.findViewById(R.id.descriptionOfCoach);
        myTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        activity = getActivity();
        diaryView = DiaryFragment.this;

        calender.setOnClickListener(this);
        editDelete.setOnClickListener(this);
        descriptionOfCoach.setOnClickListener(this);

        initExpressTabRecyclerView(view);


        return view;

    }

    private void initExpressTabRecyclerView(View view) {
        recycle_view = (RecyclerView) view.findViewById(R.id.recycle_view);
        expressView = DiaryFragment.this;
        FragmentManager fragmentManager = getFragmentManager();
        expressImplementation = new ExpressImplementation(activity, fragmentManager, expressView, recycle_view);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }




    @Override
    public void onResume() {
        super.onResume();
        setRetainInstance(true);

        if (getArguments() != null) {
            if (getArguments().containsKey(AppConstants.MOVE_POSITION))
                move_position = getArguments().getInt(AppConstants.MOVE_POSITION);
            else if (getArguments().containsKey("DAY_NUM")) {
                gratitudeDate = getArguments().getInt("DAY_NUM");
            }
        }


        diaryImplementation = new DiaryImplementation(activity, move_position, fm, diaryView, mineViewPager, myTabLayout, gratitudeDate);
        diaryImplementation.displayKitkatIcon();
        diaryImplementation.loadViewPagerData();

        expressImplementation.displayKitkatIcon();
        expressImplementation.loadGratitudeList();



        if (mineViewPager.getAdapter() != null) {
            if (mineViewPager.getAdapter().getCount() > 0) {
                add_image_layout.setVisibility(View.GONE);
                mineViewPager.setVisibility(View.VISIBLE);
            } else {
                add_image_layout.setVisibility(View.VISIBLE);
                mineViewPager.setVisibility(View.GONE);
            }
        } else {
            add_image_layout.setVisibility(View.VISIBLE);
            mineViewPager.setVisibility(View.GONE);
        }

    }


    @Override
    public void onPause() {
        super.onPause();

        try {
            Log.d("on","onPause");
            diaryImplementation.onPause();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("on","onStop");
    }


    @Override
    public void onDetach() {

        super.onDetach();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("on","onDestroy");
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.addGratitudeBtn) {
            diaryImplementation.addGratitudeData();
        } else if (id == R.id.calender) {
            diaryImplementation.calenderEntry();
        } else if (id == R.id.editDelete) {
            popup = new PopupMenu(getActivity(), v);
            popup.setOnMenuItemClickListener(this);
            popup.inflate(R.menu.calender);
            popup.show();
        } else if (id == R.id.add_image_layout) {
            Log.i("DairyFrament", "In add image click "+Build.VERSION.SDK_INT);
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.i("Dairy", "In if loop");
                    storage_Version_check();
                } else {
                    selectImage();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(getClass().getSimpleName(), e.toString());
            }
        } else if (id == R.id.descriptionOfCoach) {
          //  startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("PAGE_URL", "https://myhappybeing.com/myjournal"));
            startActivity(new Intent(getActivity(), MyCoachDescription.class).putExtra("DescriptionName", "My Journal"));
        }
    }



    @Override
    public void displayKitkat() {

    }

    @Override
    public void hideKitkat() {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        CommonUtils commonUtils = new CommonUtils();
        int itemId = item.getItemId();
        if (itemId == R.id.calender) {
            diaryImplementation.calenderEntry();
            return true;
        } else if (itemId == R.id.edit) {
            commonUtils.setGratitudeData(activity, gratitudeDate, true);
            diaryImplementation.editDiaryData();
            return true;
        } else if (itemId == R.id.delete) {
            commonUtils.setGratitudeData(activity, gratitudeDate, false);
            diaryImplementation.deleteDiaryData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void cameraIntent() {
        try {

            // Check permission for CAMERA
            if (ActivityCompat.checkSelfPermission(getActivity().getBaseContext(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                // Check Permissions Now
                // Callback onRequestPermissionsResult interceptado na Activity MainActivity
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        this.REQUEST_CAMERA);
            } else {
                // permission has been granted, continue as usual


                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //     getActivity().startActivityForResult(intent, REQUEST_CAMERA);
                ((Activity)getActivity()).startActivityForResult(intent, REQUEST_CAMERA);
            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(getClass().getSimpleName(), e.toString());
        }
    }

    @Override
    public void galleryIntent() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            //  getActivity().startActivityForResult(Intent.createChooser(intent, "Select File"), IMAGE_CODE);
            ((Activity)getActivity()).startActivityForResult(Intent.createChooser(intent, "Select File"), IMAGE_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(getClass().getSimpleName(), e.toString());
        }
    }

    private void storage_Version_check() {
        try {
            if (ContextCompat.checkSelfPermission(activity,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Log.i("DairyFragment", "In on request permissions");
                ActivityCompat.requestPermissions(activity,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        111);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(getClass().getSimpleName(), e.toString());
        }
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library"};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Add Photo!");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = CommonUtils.checkPermission(activity);


                if (items[item].equals("Take Photo")) {
                    //userChoosenTask = "Take Photo";
                    //permissionItem = String.valueOf(items[item]);
                    //Utilities.checkDynamicPermission(activity);
                    if (result)
                        expressView.cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    //userChoosenTask = "Choose from Library";
                    //permissionItem = String.valueOf(items[item]);
                    //Utilities.checkDynamicPermission(activity);
                    if (result)
                        expressView.galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

}
