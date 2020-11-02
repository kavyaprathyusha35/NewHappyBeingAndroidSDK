package com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.UI.gratitude.ExpressGratitudeOthers;
import com.nsmiles.happybeingsdklib.UI.gratitude.ExpressGratitudeSelfLove;
import com.nsmiles.happybeingsdklib.UI.gratitude.ExpressGratitudeToday;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.adapter.GratitudeListAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Kavya on 4/7/2020.
 */
public class GratitudeJournalListFragment extends Fragment implements GratitudeListAdapter.ExpressOnClickListener, View.OnClickListener {
    private Activity activity;
    private RecyclerView recycle_view;
    private ArrayList<String> gratitudeList;
    private GratitudeListAdapter gratitudeListAdapter;
    private final int REQUEST_JOURNAL = 55555;
    private TextView info_text;
    CommonUtils commonUtils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.express_tab_layout, container, false);
        activity = getActivity();
        recycle_view = view.findViewById(R.id.recycle_view);
        info_text = view.findViewById(R.id.info_text);
        info_text.setVisibility(View.GONE);
        Log.i("Express", "In on create view");
        commonUtils=new CommonUtils();
        //TextView descriptionOfJournal = view.findViewById(R.id.descriptionOfJournal);
        //descriptionOfJournal.setOnClickListener(this);
        loadGratitudeJournalList();
        return view;
    }

    private void loadGratitudeJournalList() {
        gratitudeList = new ArrayList<>();
        gratitudeList.add(AppConstants.ABOUT_WORK);
        gratitudeList.add(AppConstants.ABOUT_TODAY);
        gratitudeList.add(AppConstants.TO_SELF);
        gratitudeList.add(AppConstants.GUIDE_ME);
        recycle_view.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        recycle_view.setLayoutManager(layoutManager);
        gratitudeListAdapter = new GratitudeListAdapter(activity, gratitudeList);
        recycle_view.setAdapter(gratitudeListAdapter);
        gratitudeListAdapter.notifyDataSetChanged();// Notify the adapter
        gratitudeListAdapter.setExpressOnClickListener(this);

    }

    @Override
    public void getFirstCardSize(int card_size, int list_size) {

    }

    @Override
    public void actionExpressOnClickListener(List<String> getGratitudeList, int position) {

        if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.ABOUT_WORK))
        {
            if (getFragmentManager() != null) {
                Log.d("onBackPressed", String.valueOf(getFragmentManager().getBackStackEntryCount()));
            }

            Log.d("gotogetGratitudeList2","onBackPressedClickedGratitude");

            activity.startActivityForResult(new Intent(activity, ExpressGratitudeToday.class)
                    .putExtra("HIDE", "RECOMMEND").putExtra("TITLE", "About my work"), REQUEST_JOURNAL);
            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
        else if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.ABOUT_TODAY)) {
            activity.startActivityForResult(new Intent(activity, ExpressGratitudeToday.class)
                    .putExtra("HIDE", "RECOMMEND").putExtra("TITLE", "About today"), REQUEST_JOURNAL);
            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);

        } else if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.TO_SELF)) {


            activity.startActivityForResult(new Intent(activity, ExpressGratitudeSelfLove.class).putExtra("NAME", AppConstants.TO_SELF),REQUEST_JOURNAL);
            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);

        } else if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.TO_OTHERS)) {
            activity.startActivityForResult(new Intent(activity, ExpressGratitudeOthers.class), REQUEST_JOURNAL);
            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);

        }
        else if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.GUIDE_ME)) {
            activity.startActivityForResult(new Intent(activity, ExpressGratitudeToday.class).putExtra("HIDE", "IMAGE"), REQUEST_JOURNAL);
            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
    }

    @Override
    public void hideLastViewLine(View myView, int position, int totalCount, List<String> getGratitudeList, ImageView imageView) {
       if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.ABOUT_WORK)) {
            imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.about_studying));
        } else if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.ABOUT_TODAY)) {
            imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.about_today));
        } else if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.TO_SELF)) {
            imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.to_self));
        } else if (getGratitudeList.get(position).equalsIgnoreCase(AppConstants.GUIDE_ME)) {
            imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.guide_me));
        }
    }

    @Override
    public void onClick(View v) {
/*
        if (v.getId() == R.id.descriptionOfJournal) {
            startActivity(new Intent(activity, JournalDescriptionActivity.class));
        }
*/

    }
}
