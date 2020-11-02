package com.nsmiles.happybeingsdklib.MindGymFav;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.nsmiles.happybeingsdklib.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RelaxFavouritesActivity extends AppCompatActivity implements FavouriteView, View.OnClickListener {

    Toolbar choose_toolbar;
    RecyclerView audio_recycle_view;
    FavouriteView favouriteView;
    FavouriteImplementation favouriteImplementation;
    LinearLayout empty_list_layout;
    Button add_fav_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax_favourites);


        choose_toolbar = (Toolbar) findViewById(R.id.choose_toolbar);
        audio_recycle_view = (RecyclerView) findViewById(R.id.audio_recycle_view);
        empty_list_layout = (LinearLayout) findViewById(R.id.empty_list_layout);
        add_fav_btn = (Button) findViewById(R.id.add_fav_btn);
        add_fav_btn.setOnClickListener(this);
        choose_toolbar.setTitle("My favourites");
        setSupportActionBar(choose_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(RelaxFavouritesActivity.this ,R.color.hb_circle_black_light));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        favouriteView = RelaxFavouritesActivity.this;
        favouriteImplementation = new FavouriteImplementation(RelaxFavouritesActivity.this, favouriteView, audio_recycle_view);
        favouriteImplementation.loadFavouriteAudio();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.add_fav_btn) {
            favouriteImplementation.goRelaxListActivity();
        }
    }

    @Override
    public void showEmptyFavouriteLayout() {

        audio_recycle_view.setVisibility(View.GONE);
        empty_list_layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        super.onBackPressed();
    }
}
