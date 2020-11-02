package com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.UI.HomeScreenActivity;
import com.nsmiles.happybeingsdklib.diaryfragment.DiaryData;
import com.nsmiles.happybeingsdklib.diaryfragment.MyDiaryAdapter;

import java.io.Serializable;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Created by Sukumar on 1/20/2018.
 */

public class DiaryDynamicFragment extends Fragment {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    RecyclerView recycle_view;
    MyDiaryAdapter myDiaryAdapter;

    public static DiaryDynamicFragment newInstance(String tab, DiaryData viewMyDiaryList) {

        DiaryDynamicFragment fragment = new DiaryDynamicFragment();
        Bundle args = new Bundle();
        args.putString("category" , tab);
        args.putSerializable(EXTRA_MESSAGE, (Serializable) viewMyDiaryList);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = null;
        try {
            DiaryData viewMyDiaryList = (DiaryData) getArguments().getSerializable(EXTRA_MESSAGE);
            view = inflater.inflate(R.layout.my_dairy_fragment_layout, container,false);
            recycle_view = (RecyclerView) view.findViewById(R.id.recycle_view);

            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,1);
            recycle_view.setHasFixedSize(true);
            staggeredGridLayoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
            myDiaryAdapter = new MyDiaryAdapter(getActivity(), viewMyDiaryList);
            recycle_view.setLayoutManager(staggeredGridLayoutManager);
            recycle_view.setAdapter(myDiaryAdapter);
        } catch (Exception e) {
            e.printStackTrace();
            startActivity(new Intent(getActivity(), HomeScreenActivity.class));
            getActivity().finishAffinity();
        }
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("on","onPause");


    }



    @Override
    public void onStop() {
        super.onStop();
        Log.d("on","onStop");
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("on","onDestroy");
    }
}
