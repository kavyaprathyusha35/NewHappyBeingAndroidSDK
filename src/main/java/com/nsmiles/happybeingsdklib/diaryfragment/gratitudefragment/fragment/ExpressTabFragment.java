package com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.implementation.ExpressImplementation;
import com.nsmiles.happybeingsdklib.diaryfragment.gratitudefragment.view.ExpressView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class ExpressTabFragment extends Fragment implements ExpressView {

    Activity activity;
    ExpressView expressView;
    ExpressImplementation expressImplementation;
    RecyclerView recycle_view;
    int IMAGE_CODE = 2002;
    private static final int REQUEST_CAMERA = 1001;
    private TextView info_text;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.express_tab_layout, container, false);
        activity = getActivity();
        recycle_view = (RecyclerView) view.findViewById(R.id.recycle_view);
        info_text = view.findViewById(R.id.info_text);
        expressView = ExpressTabFragment.this;
        FragmentManager fragmentManager = getFragmentManager();
        expressImplementation = new ExpressImplementation(activity, fragmentManager, expressView, recycle_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        expressImplementation.displayKitkatIcon();
        expressImplementation.loadGratitudeList();
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
}
