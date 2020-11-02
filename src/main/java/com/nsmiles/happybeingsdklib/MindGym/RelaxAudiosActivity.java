package com.nsmiles.happybeingsdklib.MindGym;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.nsmiles.happybeingsdklib.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RelaxAudiosActivity extends AppCompatActivity implements RelaxView {

    Toolbar choose_toolbar;
    RecyclerView audio_recycle_view;
    RelaxView relaxView;
    RelaxImplementation relaxImplementation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax_audios);

        choose_toolbar = (Toolbar) findViewById(R.id.choose_toolbar);
        audio_recycle_view = (RecyclerView) findViewById(R.id.audio_recycle_view);
        choose_toolbar.setTitle("Relax audios");
        setSupportActionBar(choose_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(RelaxAudiosActivity.this ,R.color.hb_circle_black_light));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        relaxView = RelaxAudiosActivity.this;
        relaxImplementation = new RelaxImplementation(RelaxAudiosActivity.this, relaxView, audio_recycle_view);
        relaxImplementation.loadRelaxAudio();
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        super.onBackPressed();
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

    }

    @Override
    public void internetRequired() {

    }
}
