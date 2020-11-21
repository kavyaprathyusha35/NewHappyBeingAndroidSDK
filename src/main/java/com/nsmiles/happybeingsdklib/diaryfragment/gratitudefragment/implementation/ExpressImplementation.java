package com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.implementation;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nsmiles.happybeingsdklib.MindGym.VentOutJournal;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.UI.HomeScreenActivity;
import com.nsmiles.happybeingsdklib.UI.SubscriptionActivity;
import com.nsmiles.happybeingsdklib.UI.gratitude.ExpressGratitudeSelfLove;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.dagger.data.PreferenceManager;
import com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.adapter.GratitudeListAdapter;
import com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.fragment.GratitudeJournalListFragment;
import com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.presenter.ExpressPresenter;
import com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.view.ExpressView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ExpressImplementation implements ExpressPresenter {

    private ExpressView expressView;
    private Activity activity;
    private RecyclerView recyclerView;
    private GratitudeListAdapter gratitudeListAdapter;
    private List<String> gratitudeList;
    private CommonUtils commonUtils;
    private String userChoosenTask = "";
    private String START_DATE = "", END_DATE = "";
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z", Locale.getDefault());
    private final int REQUEST_JOURNAL = 55555;
    private FragmentManager fragmentManager;
    String payment_status;
    PreferenceManager preferenceManager;

    public ExpressImplementation(Activity activity, FragmentManager fragmentManager, ExpressView expressView, RecyclerView recyclerView) {
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.expressView = expressView;
        this.recyclerView = recyclerView;
        commonUtils = new CommonUtils();
        START_DATE = df.format(Calendar.getInstance().getTime());
        preferenceManager=new PreferenceManager(activity);
        payment_status=preferenceManager.get(AppConstants.PAYMENT_STATUS,"");
    }


    @Override
    public void displayKitkatIcon() {

    }

    @Override
    public void loadGratitudeList() {
        gratitudeList = new ArrayList<>();
        gratitudeList.add(AppConstants.GRATITUDE_JOURNAL);
        gratitudeList.add(AppConstants.ABUDANCE_JOURNAL);
        gratitudeList.add(AppConstants.MANIFEST_JOURNAL);
        gratitudeList.add(AppConstants.HAPPY_MOMENT);
        gratitudeList.add(AppConstants.DE_STRESS_JOURNAL);

        if (gratitudeList != null && gratitudeList.size() > 0) {
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
            recyclerView.setLayoutManager(layoutManager);
            gratitudeListAdapter = new GratitudeListAdapter(activity, gratitudeList);
            recyclerView.setAdapter(gratitudeListAdapter);
            gratitudeListAdapter.notifyDataSetChanged();// Notify the adapter


            gratitudeListAdapter.setExpressOnClickListener(new GratitudeListAdapter.ExpressOnClickListener() {
                @Override
                public void getFirstCardSize(int card_size, int list_size) {
                    recyclerView.setMinimumHeight(card_size*list_size);
                }

                @Override
                public void actionExpressOnClickListener(List<String> getGratitudeList, int position) {

                    if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.GRATITUDE_JOURNAL)) {

                        if(payment_status.equalsIgnoreCase("EXPIRED")){

                            activity.startActivity(new Intent(activity, SubscriptionActivity.class));
                            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);


                        }else{
                            HomeScreenActivity.viewJournalClicked = true;
                            Fragment fragment = new GratitudeJournalListFragment();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.new_screen_fragment_layout, fragment, fragment.getClass().getSimpleName());
                            fragmentTransaction.commit();
                        }




                    } else if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.ABUDANCE_JOURNAL)) {


                        activity.startActivityForResult(new Intent(activity, ExpressGratitudeSelfLove.class).putExtra("NAME", AppConstants.ABUDANCE_JOURNAL), REQUEST_JOURNAL);
                        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    } else if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.MANIFEST_JOURNAL)) {
                        activity.startActivityForResult(new Intent(activity, ExpressGratitudeSelfLove.class).putExtra("NAME", AppConstants.MANIFEST_JOURNAL), REQUEST_JOURNAL);
                        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);

                    } else if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.DE_STRESS_JOURNAL)) {
                        activity.startActivity(new Intent(activity, VentOutJournal.class));
                        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    }
                    else if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.HAPPY_MOMENT)) {

                        /*Analytics Activity Calling*/
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            storage_Version_check();
                        } else {
                            selectImage();
                        }
                    }
                }

                @Override
                public void hideLastViewLine(View myView, int position, int totalCount, List<String> getGratitudeList, ImageView imageView) {

                    if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.HAPPY_MOMENT)) {
                        imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.happy_moments));
                    } else if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.GRATITUDE_JOURNAL)) {
                        imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.gratitude_journal));
                    }else if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.ABUDANCE_JOURNAL)) {
                        imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.abundance_icon));
                    }else if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.MANIFEST_JOURNAL)) {
                        imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.manifest_icon));
                    }else if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.DE_STRESS_JOURNAL)) {
                        imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.de_stress_icon));
                    }
                    if (position == totalCount - 1) {
                        myView.setVisibility(View.GONE);
                    }
                }
            });

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
                    userChoosenTask = "Take Photo";
                    if (result)
                        expressView.cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        expressView.galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void storage_Version_check() {
        try {
            if (ContextCompat.checkSelfPermission(activity,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        111);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(getClass().getSimpleName(), e.toString());
        }
    }


}
