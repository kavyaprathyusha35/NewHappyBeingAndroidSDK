package com.nsmiles.happybeingsdklib.MindGym;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.UI.MyCoachDescription;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


public class RelaxAffirmationFragment extends Fragment implements View.OnClickListener, RelaxView {

    Activity activity;
    LinearLayout favouritesLayout;
    TextView favouritesTv, info_text;
    RecyclerView audio_recycle_view, relax_activity_recycle_view;
    RelaxView relaxView;
    RelaxAffirmationImplementation relaxAffirmationImplementation;
    private boolean is_login = false;
    TextView descriptionOfCoach;
    Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.relax_affirmation, container, false);
        favouritesTv = (TextView) view.findViewById(R.id.favouritesTv);
        favouritesLayout = (LinearLayout) view.findViewById(R.id.favouritesLayout);
        audio_recycle_view = (RecyclerView) view.findViewById(R.id.audio_recycle_view);
        relax_activity_recycle_view = (RecyclerView) view.findViewById(R.id.relax_activity_recycle_view);
        activity = getActivity();
        info_text = view.findViewById(R.id.info_text);
        descriptionOfCoach = view.findViewById(R.id.descriptionOfCoach);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("IS_LOGIN")) {
            is_login = bundle.getBoolean("IS_LOGIN", false);
        }
        favouritesLayout.setOnClickListener(this);
        descriptionOfCoach.setOnClickListener(this);
        relaxView = RelaxAffirmationFragment.this;
        FragmentManager fragmentManager = getFragmentManager();
        relaxAffirmationImplementation = new RelaxAffirmationImplementation(activity, fragmentManager, is_login, relaxView,audio_recycle_view,relax_activity_recycle_view);
        relaxAffirmationImplementation.displayKitkatIcon();
        relaxAffirmationImplementation.loadActivityAdapter();
        info_text.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        relaxAffirmationImplementation.getTodayDDAndMM();
        relaxAffirmationImplementation.getFavouritesCount();
        relaxAffirmationImplementation.getRandomRelaxAudio();
        //relaxAffirmationImplementation.pushAffirmationDataToServer();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        int id =v.getId();
        if (id == R.id.favouritesLayout) {
            relaxAffirmationImplementation.favouriteOnClickListener();
        }else if(id == R.id.descriptionOfCoach){
            startActivity(new Intent(getActivity(), MyCoachDescription.class).putExtra("DescriptionName", "MindSpa"));
        }
    }



    @Override
    public void displayKitkat() {

    }

    @Override
    public void hideKitkat() {

    }

    @Override
    public void setDdDate(String date) {
    }

    @Override
    public void setMmMonth(String month) {
    }

    @Override
    public void setFavourites(String favourites) {
        favouritesTv.setText("("+favourites+")");
    }


    @Override
    public void internetRequired() {
        CommonUtils.alertDialog(activity, getResources().getString(R.string.network_header)
                , getResources().getString(R.string.network_message), "Ok");
    }
}
