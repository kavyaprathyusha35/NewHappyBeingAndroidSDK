package com.nsmiles.happybeingsdklib.wellbeingassessment.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.SingleWellBeingReportActivity;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.CustomToast;
import com.nsmiles.happybeingsdklib.Utils.MySql;
import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.CorporateModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AssessmentDetailsActivity extends AppCompatActivity {

    private TextView assessmentTitleTextView, instructionsTextView;
    private Button start_button;
    private List<CorporateModel> corporateModelList;
    private boolean assessment_completed_status = false;
    int pos;
    String report_API_URL,report_VERSION,assegment;
    private boolean isLogin;
    public final int WELL_BEING_ASSESSMENT_INTENT_REQUEST = 189;
    @Inject
    DataManager dataManager;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_details_activity);
        assessmentTitleTextView = findViewById(R.id.title_of_assessment);
        instructionsTextView = findViewById(R.id.instructions_text);
        start_button = findViewById(R.id.start_button);
        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE_OF_ASSESSMENT");
        String instructions = intent.getStringExtra("INSTRUCTIONS_OF_ASSESSMENT");
        pos = intent.getExtras().getInt("POS");
         corporateModelList= (ArrayList<CorporateModel>) intent.getSerializableExtra("LIST");
         report_API_URL = intent.getStringExtra(AppConstants.REPORT_SUB_TYPE);
         report_VERSION = intent.getStringExtra(AppConstants.ASSESSMENT_VERSION);
         assegment = intent.getStringExtra(AppConstants.ASSESSMENT);
         isLogin = getIntent().getExtras().getBoolean("isLOGIN");



        if(corporateModelList.get(pos).isAssessmentStatus()) {
            assessment_completed_status = true;
            start_button.setText("REPORT");
        }
        else {
            assessment_completed_status = false;
            start_button.setText("Start");

            //holder.completed_not_img.setImageDrawable(context.getResources().getDrawable(R.drawable.payment_gray));
        }

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!corporateModelList.get(pos).isAssessmentStatus()) {
                    if (!dbEntryExists("'" + corporateModelList.get(pos).getArea() + "'")) {
                        AssessmentDetailsActivity.this.startActivityForResult(new Intent(AssessmentDetailsActivity.this, WellBeingAssessmentActivity.class)
                                .putExtra(AppConstants.ASSESSMENT, corporateModelList.get(pos).getArea())
                                .putExtra(AppConstants.REPORT_SUB_TYPE, report_API_URL)
                                .putExtra(AppConstants.ASSESSMENT_VERSION, report_VERSION)
                                .putStringArrayListExtra(corporateModelList.get(pos).getArea(),
                                        corporateModelList.get(pos).getCategory().get(corporateModelList.get(pos).getArea())), WELL_BEING_ASSESSMENT_INTENT_REQUEST);
                        AssessmentDetailsActivity.this.finish();

                    } else {
                        CustomToast.makeText(AssessmentDetailsActivity.this, "Report will be generated once the network is available!!!", Toast.LENGTH_SHORT).show();
                        //activity.finish();
                    }
                } else {
                    if (CommonUtils.isNetworkAvailable(AssessmentDetailsActivity.this)) {
                        //   if (!(corporateModelList.size()-1 == pos))

                                AssessmentDetailsActivity.this.startActivity(new Intent(AssessmentDetailsActivity.this, SingleWellBeingReportActivity.class)
                                        .putExtra(AppConstants.REPORT_SUB_TYPE, report_API_URL)
                                        .putExtra(AppConstants.ASSESSMENT_VERSION, report_VERSION)
                                        .putExtra(AppConstants.HIDE_RADAR, true)
                                        .putStringArrayListExtra(AppConstants.SHOW_ALL_PREGNANCY_REPORT, corporateModelList.get(pos).getCategory().get(corporateModelList.get(pos).getArea())));
                                         AssessmentDetailsActivity.this.finish();

//                            if(corporateModelList.size()-1 == pos)
//                            {
//                                CustomToast.makeText(AssessmentDetailsActivity.this, "No report available for general information",Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                Log.i("OfflineAssessment", "IN else "+report_API_URL);
//
//                            }

                    } else {
                        CustomToast.makeText(AssessmentDetailsActivity.this, "Internet not available!!! Please check your data network", Toast.LENGTH_SHORT).show();
                    }
                }            }
        });




        assessmentTitleTextView.setText(title);
        if (instructions != null) {
            instructionsTextView.setText(Html.fromHtml(instructions));
        }
        else {
            instructions = "General details about yourself.";
            instructionsTextView.setText(instructions);
        }
    }

    private boolean dbEntryExists(String nameOfAssessment) {
        MySql dbHelper = new MySql(AssessmentDetailsActivity.this, "mydb", null, MySql.version);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Assessment_Answers_Table where ASSESSMENT_SUB_TYPE = "+nameOfAssessment, null);
        if (cursor.getCount() > 0) {
            db.close();
            return true;
        }
        else {
            db.close();
            return false;
        }
    }

}
