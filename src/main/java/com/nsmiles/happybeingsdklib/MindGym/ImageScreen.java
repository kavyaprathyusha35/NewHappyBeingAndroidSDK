package com.nsmiles.happybeingsdklib.MindGym;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;

import java.io.IOException;
import java.util.Date;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

/**
 * Created by vinay on 24-05-2017.
 */

public class ImageScreen extends Activity {

    ImageView image_view;
    String text_title="", imageName="", imageText="", image_path="";
    CommonUtils commonUtils;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.new_image_screen);
        image_view = (ImageView) findViewById(R.id.image_view);

        commonUtils = new CommonUtils();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(ImageScreen.this ,R.color.hb_circle_black_light));
        }

        try {
            Intent intent = getIntent();
            if (intent.hasExtra("IMAGE_TITLE")) {
                text_title = intent.getStringExtra("IMAGE_TITLE");
                System.out.println("ImageScreen.text_title "+text_title);
            }

            if (intent.hasExtra("IMAGE_ID")) {
                int id = intent.getIntExtra("IMAGE_ID", 0);
                if (id != 0) {

                    image_view.setBackground(getResources().getDrawable(id));
                }
            }

            if (intent.hasExtra("IMAGE_TEXT")) {
                imageText= intent.getStringExtra("IMAGE_TEXT");
                System.out.println("ImageScreen.imageText "+imageText);
            }

            if(intent.hasExtra("FROM_HAPPY")) {

                image_path = intent.getStringExtra("FROM_HAPPY");
                Bitmap bm = CommonUtils.decodeSampledBitmapFromUri(image_path, 220, 220);
                try {
                    bm = CommonUtils.modifyOrientation(bm,image_path);
                    image_view.setImageBitmap(bm);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            if(image_path.length()==0) {
                AddActivityEntry();
            }
            else {
                finish();
            }
        } else {
            getFragmentManager().popBackStack();
        }
    }

    private  void AddActivityEntry() {
       // Save Locally
        MySql dbHelper = new MySql(this, "mydb", null, MySql.version);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("user_id", "");
        cv.put("date_time", new Date().toString());
        cv.put("feature_name","RELAX_RELEIVE");
        cv.put("activity_name",imageText);
        cv.put("activity_detail",imageText);
        cv.put("sync_flag","0");
        db.insert("New_Activity_Table", null, cv);

        db.close();
        finish();
    }
}
