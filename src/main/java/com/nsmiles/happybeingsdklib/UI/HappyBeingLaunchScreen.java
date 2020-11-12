package com.nsmiles.happybeingsdklib.UI;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.nsmiles.happybeingsdklib.MindGym.RelaxUtils;
import com.nsmiles.happybeingsdklib.Models.RelaxCoachAudioUtils;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;
import com.nsmiles.happybeingsdklib.Utils.SdkPreferenceManager;
import com.nsmiles.happybeingsdklib.dagger.application.BaseApplication;
import com.nsmiles.happybeingsdklib.mycoachfragment.presenter.CommonPresenter;
import com.nsmiles.happybeingsdklib.network.NetworkError;
import com.nsmiles.happybeingsdklib.network.NetworkService;
import com.nsmiles.happybeingsdklib.network.Service;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.CorporateModel;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel.CorporateAssessModel;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel.Option;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel.Question;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import rx.Subscription;

/**
 * Created by Kavya on 9/21/2020.
 */

public class HappyBeingLaunchScreen extends AppCompatActivity {
    private static MySql dbHelper;
    CommonUtils commonUtils;
    private static SQLiteDatabase db;

    @Inject
    Service service;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity);
        commonUtils=new CommonUtils();

        RelativeLayout splash_screen_layout = findViewById(R.id.splash_activity_layout);
        ImageView splash_img = findViewById(R.id.splash_img);
        Animation a = AnimationUtils.loadAnimation(this, R.anim.center_in);
        splash_img.startAnimation(a);
        Calendar rightNow = Calendar.getInstance();
        int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
        if (currentHourIn24Format < 12) {
            splash_screen_layout.setBackground(getResources().getDrawable(R.drawable.good_morning_background));
        } else if (currentHourIn24Format < 18) {
            splash_screen_layout.setBackground(getResources().getDrawable(R.drawable.good_afternoon_background));
        } else if (currentHourIn24Format < 24) {
            splash_screen_layout.setBackground(getResources().getDrawable(R.drawable.good_evening_background));
        }
        SdkPreferenceManager sdkPreferenceManager = new SdkPreferenceManager(this);
        if (sdkPreferenceManager.get(AppConstants.IS_FIRST_TIME, true)) {
            sdkPreferenceManager.save(AppConstants.IS_FIRST_TIME, false);
            RelaxUtils relaxUtils = new RelaxUtils();
            relaxUtils.insertAllRelaxAudio(this);
            RelaxCoachAudioUtils relaxCoachAudioUtils = new RelaxCoachAudioUtils();
            relaxCoachAudioUtils.insertAllOthersAudio(this);
        }

        boolean isDownloaded = sdkPreferenceManager.get(AppConstants.ASSESSMENT_DOWNLOADED, false);
        if (!isDownloaded) {
            ((BaseApplication) getApplication()).getmApplicationApiComponent().inject(this);
            sdkPreferenceManager.save(AppConstants.ASSESSMENT_DOWNLOADED, true);
            assesmentDownload(AppConstants.ASSESS_TOKEN,NetworkService.corporateWellBeing.trim(), service, this, AppConstants.PROFILE_EMPLOYED);
        }

        loadHandler();
    }

    private void loadHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                SdkPreferenceManager sdkPreferenceManager = new SdkPreferenceManager(HappyBeingLaunchScreen.this);
                String userStatus = sdkPreferenceManager.get(AppConstants.NEW_OR_OLD, "");
                Log.i("Launch", "User status is "+userStatus);
                if (userStatus.equals("New User")) {
                    intent = new Intent(HappyBeingLaunchScreen.this, IntroSliderActivity.class);
                    sdkPreferenceManager.save(AppConstants.IS_FIRST_TIME_LAUNCH, false);
                }
                else {
                    intent = new Intent(HappyBeingLaunchScreen.this, HomeScreenActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 3000);

    }

    public static void assesmentDownload(String token,String API_URL, Service service, final Activity activity, final String profile) {
        Log.i("ForUpdatingCode", "In assessment Download api url is " + API_URL);

        Subscription subscription = service.getNSmilesAssessmentQuestion(token,API_URL, new Service.GetNSmilesAssessmentQuestionCallBack() {
            @Override
            public void onSuccess(CorporateAssessModel corporateAssessmentModel) {

                Log.i("ForUpdatingCode", "In on success of assessments " + corporateAssessmentModel.getResult().get(0).getQuestions().size());
                dbHelper = new MySql(activity, "mydb", null, MySql.version);
                db = dbHelper.getWritableDatabase();
                db.delete("Assessment_Tiles", null, null);
                db.delete("Assessments_Table", null, null);
                getTheQuestionList(activity, corporateAssessmentModel, null);
                Log.i("ForUpdatingCode", "In on success Profile is "+profile);
                getCatogoriesTile(activity, corporateAssessmentModel, profile);

            }

            @Override
            public void onError(NetworkError networkError) {
                Log.i("ForUpdatingCode", "***On network error ****" + networkError);
            }

            @Override
            public void onSuccessError(String errorMessage) {
                Log.i("ForUpdatingCode", "***On error message ****" + errorMessage);

            }
        });
    }
    private static void getCatogoriesTile(Activity activity, CorporateAssessModel corporateAssessmentModel, String profile) {
        String[] area, image_url, title, description, time, status_title;
        ArrayList<String> title_list = null, sub_category, category_name_list;
        List<CorporateModel> corporateModelList;
        Map<String, ArrayList<String>> reportStatusMap = new HashMap<>();
        CorporateModel corporateModel;
        Map<String, ArrayList<String>> category_map;
        int category_size;
        String report_API_URL = "", report_VERSION = "";
        report_API_URL = corporateAssessmentModel.getResult().get(0).getReport().getType();
        report_VERSION = corporateAssessmentModel.getResult().get(0).getReport().getVersion();
        area = new String[corporateAssessmentModel.getResult().get(0).getCategories().size()];
        Log.i("ForUpdatingCode", "In catogories title size "+area);
        title = new String[corporateAssessmentModel.getResult().get(0).getCategories().size()];
        status_title = new String[corporateAssessmentModel.getResult().get(0).getCategories().size()];
        description = new String[corporateAssessmentModel.getResult().get(0).getCategories().size()];
        image_url = new String[corporateAssessmentModel.getResult().get(0).getCategories().size()];
        time = new String[corporateAssessmentModel.getResult().get(0).getCategories().size()];
        db = dbHelper.getWritableDatabase();
        title_list = new ArrayList<>();

        for (int i = 0; i < title.length; i++) {
            area[i] = corporateAssessmentModel.getResult().get(0).getCategories().get(i).getName();
            title[i] = corporateAssessmentModel.getResult().get(0).getCategories().get(i).getTitle();
            status_title[i] = corporateAssessmentModel.getResult().get(0).getCategories().get(i).getReportTitles().get(0);
            description[i] = corporateAssessmentModel.getResult().get(0).getCategories().get(i).getDescription();
            Log.i("ForUpdatingCode", "In catogories title "+title[i]);
            image_url[i] = AppConstants.IMAGE_URL.concat(corporateAssessmentModel.getResult().get(0).getCategories().get(i).getImageUrl());
            time[i] = corporateAssessmentModel.getResult().get(0).getCategories().get(i).getAssessmentTime();
            title_list.addAll(corporateAssessmentModel.getResult().get(0).getCategories().get(i).getReportTitles());
            reportStatusMap.put(title[i], title_list);
        }
        corporateModelList = new ArrayList<>();
        sub_category = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            corporateModel = new CorporateModel();
            corporateModel.setTitle(title[i]);
            corporateModel.setDescription(description[i]);
            corporateModel.setArea(area[i]);
            corporateModel.setAssessmentTime(time[i]);
            corporateModel.setStatus_title(status_title[i]);
            corporateModel.setImage_url(image_url[i]);
            corporateModelList.add(corporateModel);
            category_size = corporateAssessmentModel.getResult().get(0).getCategories().get(i).getReportTitles().size();
            if (category_size > 0) {
                category_name_list = new ArrayList<>();
                category_map = new HashMap<>();
                for (int c = 0; c < category_size; c++) {
                    category_name_list.add(corporateAssessmentModel.getResult().get(0).getCategories().get(i).getReportTitles().get(c));
                    sub_category.add(corporateAssessmentModel.getResult().get(0).getCategories().get(i).getReportTitles().get(c));
                }
                category_map.put(area[i], category_name_list);
                corporateModel.setCategory(category_map);
            }
        }
        if (title.length == corporateModelList.size()) {
            //TODO: Save it to db, save sub category list also

            Gson gson = new Gson();
            String tilesListString = gson.toJson(corporateModelList);
            System.out.println("inputString= " + tilesListString);
            String category_list_string = gson.toJson(sub_category);
            Log.i("WellBeingChanges", "Array list size is "+title_list.size());
            for (int i= 0; i < title_list.size(); i++) {
                Log.i("WellBeingChanges", "Title string is "+title_list.get(i));
            }
            if (db.isOpen()) {
                ContentValues cv = new ContentValues();
                cv.put("TILES_LIST", tilesListString);
                cv.put("REPORT_CATEGORY", category_list_string);
                cv.put("REPORT_NAME", report_API_URL);
                cv.put("REPORT_VERSION", report_VERSION);
                long insert = db.insert("Assessment_Tiles", null, cv);

                Log.i("ForUpdatingCode", "Inserrted successfully in tiles table...." +report_API_URL);
                db.close();
            }
        }

    }

    public static void getTheQuestionList(Activity activity, CorporateAssessModel corporateAssessmentModel, CommonPresenter.APICallBack apiCallBack) {
        String ASSESSMENT_TYPE = "", ASSESSMENT_VERSION = "";
        String title_tv = "", instruction_tv = "";
        Question question;
        Question userAnswerAndValue;
        Option option;
        List<Question> spinnerTypeList;
        List<Question> spinnerValueList;
        dbHelper = new MySql(activity, "mydb", null, MySql.version);

        db = dbHelper.getWritableDatabase();

        int ques = 0;
        List<Question> questionList = null;
        List<Option> optionList;
        if (corporateAssessmentModel.getResult().get(0).getQuestions() != null && corporateAssessmentModel.getResult().get(0).getQuestions().size() > 0) {
            ASSESSMENT_TYPE = corporateAssessmentModel.getResult().get(0).getReport().getType();
            ASSESSMENT_VERSION = corporateAssessmentModel.getResult().get(0).getReport().getVersion();
            if (corporateAssessmentModel.getResult().get(0).getInstructions() != null) {
                title_tv = corporateAssessmentModel.getResult().get(0).getInstructions().getTitle();
                for (int i = 0; i < corporateAssessmentModel.getResult().get(0).getInstructions().getParas().size(); i++) {
                    instruction_tv = instruction_tv.concat(corporateAssessmentModel.getResult().get(0).getInstructions().getParas().get(i).concat("\n"));
                }
            }
            ques = 0;
            questionList = new ArrayList<>();

            Collections.sort(corporateAssessmentModel.getResult().get(0).getQuestions(), new Comparator<Question>() {
                @Override
                public int compare(Question o1, Question o2) {
                    return o1.getQNo() - o2.getQNo();
                }
            });

            for (int i = 0; i < corporateAssessmentModel.getResult().get(0).getQuestions().size(); i++) {
                ques++;
                question = new Question();
                question.setQNo(corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getQNo());
                question.setQuestion(corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getQuestion());
                question.setCategory(corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getCategory());

                if (corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getType() != null) {
                    question.setType(corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getType());

                    if (corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getType().equalsIgnoreCase("multi")) {
                        optionList = new ArrayList<>();

                        spinnerTypeList = new ArrayList<>(); // Initilize spinner array value
                        spinnerValueList = new ArrayList<>();// Initilize spinner array value


                        for (int j = 0; j < corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().size(); j++) {

                            option = new Option();
                            if (corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().get(j).getName() != null) {
                                option.setName(corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().get(j).getName());
                            }

                            if (corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().get(j).getValue() != null) {
                                option.setValue(corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().get(j).getValue());
                            }
                            if (corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().get(j).getHint() != null) {
                                option.setHint(corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().get(j).getHint());
                            }
                            if (corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().get(j).getType() != null) {
                                option.setType(corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().get(j).getType());
                            }
                            if (corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().get(j).getDropdownOptions() != null) {
                                option.setDropdownOptions(corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().get(j).getDropdownOptions());
                            }

                            optionList.add(option);

                            userAnswerAndValue = new Question();
                            userAnswerAndValue.setUserAnswerName("@TEXT");
                            spinnerTypeList.add(userAnswerAndValue);

                            userAnswerAndValue = new Question();
                            userAnswerAndValue.setUserAnswerValue("");
                            spinnerValueList.add(userAnswerAndValue);

                        }

                        question.setOptions(optionList);
                        question.setUserAnswerNameList(spinnerTypeList);
                        question.setUserAnswerValueList(spinnerValueList);
                        questionList.add(question);

                    } // Multi Choice End ... DropDown Option

                    else if (corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getType().equalsIgnoreCase("checkbox")) {
                        question.setType(corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getType());
                        optionList = new ArrayList<>();

                        for (int k = 0; k < corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().size(); k++) {
                            option = new Option();
                            option.setName(corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().get(k).getName());
                            option.setValue(corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().get(k).getValue());
                            optionList.add(option);
                        }
                        question.setOptions(optionList);
                        questionList.add(question);
                    } // Checkbox Type. User can select multiple options
                    else  if (corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getType().equalsIgnoreCase("radio")) {
                        question.setType("single");
                        optionList = new ArrayList<>();

                        for (int k = 0; k < corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().size(); k++) {
                            option = new Option();
                            option.setName(corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().get(k).getName());
                            option.setValue(corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().get(k).getValue());
                            optionList.add(option);
                        }
                        question.setOptions(optionList);
                        questionList.add(question);

                    }
                }
                // No type Defined Take it as single selection
                else {
                    question.setType("single");
                    optionList = new ArrayList<>();

                    for (int k = 0; k < corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().size(); k++) {
                        option = new Option();
                        option.setName(corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().get(k).getName());
                        option.setValue(corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getOptions().get(k).getValue());
                        optionList.add(option);
                    }
                    question.setOptions(optionList);
                    questionList.add(question);
                }


            }

        }

        Log.i("ForUpdatingCode", "Question list is " + questionList.size());
        for (int i = 0; i < questionList.size(); i++) {
            Log.i("ForUpdatingCode", "Ouestion is "+questionList.get(i).getQuestion()+" Category is "+questionList.get(i).getCategory());
        }
        //TODO: store it to the db
        Gson gson = new Gson();
        String questionsListString = gson.toJson(questionList);
        System.out.println("inputString= " + questionsListString);
        if (db.isOpen()) {
            ContentValues cv = new ContentValues();
            cv.put("ASSESSMENT_INSTRUCTION", instruction_tv);
            cv.put("ASSESSMENT_TYPE", ASSESSMENT_TYPE);
            cv.put("ASSESSMENT_VERSION", ASSESSMENT_VERSION);
            cv.put("QUESTION_LIST", questionsListString);
            cv.put("ASSESSMENT_COMPLETED", false);
            long insert = db.insert("Assessments_Table", null, cv);
            Log.i("ForUpdatingCode", "Inserted successfully" + insert + " Entry created for " + ASSESSMENT_TYPE);
            db.close();
        }
        if (apiCallBack != null) {
            apiCallBack.apiCallingCompleted();
        }

    }

}
