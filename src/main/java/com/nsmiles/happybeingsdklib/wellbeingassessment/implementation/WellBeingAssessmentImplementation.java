package com.nsmiles.happybeingsdklib.wellbeingassessment.implementation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nsmiles.happybeingsdklib.Models.WellBeingAssessmentView;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.MySql;
import com.nsmiles.happybeingsdklib.Utils.SdkPreferenceManager;
import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
import com.nsmiles.happybeingsdklib.mycoachfragment.presenter.CommonPresenter;
import com.nsmiles.happybeingsdklib.network.NetworkError;
import com.nsmiles.happybeingsdklib.network.NetworkService;
import com.nsmiles.happybeingsdklib.network.Service;
import com.nsmiles.happybeingsdklib.wellbeingassessment.adapter.NSmilesAssessmentAdapter;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.CorporateSuccess;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.answermodel.Answer;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.answermodel.Answer_;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.answermodel.AssessmentData;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.answermodel.AssessmentJsonModel;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.answermodel.Value;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel.Option;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel.Question;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Sukumar on 5/10/2018.
 */

public class WellBeingAssessmentImplementation implements CommonPresenter.WellBeingAssessmentPresenter {

    private Activity activity;
    private Context context;
    private WellBeingAssessmentView view;
    private DataManager dataManager;
    private Intent myIntent;
    private Service service;
    private CompositeSubscription compositeSubscription;
    private String tag = getClass().getSimpleName();
    private NSmilesAssessmentAdapter nSmilesAssessmentAdapter;
    private RecyclerView recyclerView;
    private List<Question> questionList;
    private List<Option> optionList;
    private int adapterPositionP;
    private int adapterPositionN;
    private Subscription subscription;

    private Question question;
    private Option option;
    private int ques = 0, update_progress_count;
    private List<Question> userAnswerList;

    /*Spinner*/
    Question userAnswerAndValue;
    List<Question> spinnerTypeList;
    List<Question> spinnerValueList;
    /*Spinner*/

    String API_URL;
    String ASSESSMENT_CATEGORY;
    String ASSESSMENT_TYPE, ASSESSMENT_VERSION;
    ArrayList<String> assess_category;
    SdkPreferenceManager preferenceManager;

    public WellBeingAssessmentImplementation(Activity activity, RecyclerView recyclerView, Intent myIntent, DataManager dataManager, Service service, WellBeingAssessmentView view) {

        this.activity = activity;
        this.dataManager = dataManager;
        this.context = dataManager.getContext();
        this.myIntent = myIntent;
        this.service = service;
        this.view = view;
        this.recyclerView = recyclerView;
        compositeSubscription = new CompositeSubscription();
        view.hidePreviousButton();
        view.hideFooterLayout();
        preferenceManager = new SdkPreferenceManager(activity);


    /*Getting User Primary Profile and Secondary Profile
        * Staff and Senior Citizen !!!! General Well Being  !!!!
        * Employee and Homemaker  [Wanting to be mom and I am pregnant] !!!! Pregnancy Well Being  !!!!
        * Employee  [Male and Female] Other secondary profile   !!!! Corporate WellBeing   !!!!
        *
        *
        *
        *
        * */
      //  dataManager.infoLog("!!!  primary profile", dataManager.get(AppConstants.ROLE, ""));
       // dataManager.infoLog("!!!  secondary profile", dataManager.get(AppConstants.HB_SECONDARY_VALUE, ""));
        corporateWellBeing();



        /*Getting intent data
        * Get the category name
        *
        *
        * */
        if (myIntent != null
                && myIntent.hasExtra(AppConstants.ASSESSMENT)
                && myIntent.hasExtra(AppConstants.REPORT_SUB_TYPE)
                && myIntent.hasExtra(myIntent.getStringExtra(AppConstants.ASSESSMENT))
                && myIntent.hasExtra(AppConstants.ASSESSMENT_VERSION)) {

            ASSESSMENT_CATEGORY = myIntent.getStringExtra(AppConstants.ASSESSMENT);
            assess_category = myIntent.getStringArrayListExtra(ASSESSMENT_CATEGORY);
            ASSESSMENT_TYPE = myIntent.getStringExtra(AppConstants.REPORT_SUB_TYPE);
            Log.i("CheckDB", "Reoprt sub type is "+ASSESSMENT_CATEGORY);
            ASSESSMENT_VERSION = myIntent.getStringExtra(AppConstants.ASSESSMENT_VERSION);


            for (String s : assess_category) {

                Log.d("dd", s);
            }

        }

    }

    private void corporateWellBeing() {
        API_URL = NetworkService.corporateWellBeing.trim();
    }


    /*Fetch Question
    *
    *
    *
    * */
    @Override
    public void initAssessmentData() {

  //      view.showProgress();

/*
        subscription = service.getNSmilesAssessmentQuestion(API_URL, new Service.GetNSmilesAssessmentQuestionCallBack() {
            @Override
            public void onSuccess(CorporateAssessModel corporateAssessmentModel) {
                view.hideProgress();

                //TODO: changing here for offline data
                if (corporateAssessmentModel.getResult().get(0).getQuestions() != null && corporateAssessmentModel.getResult().get(0).getQuestions().size() > 0) {
                    ques = 0;
                    questionList = new ArrayList<>();



                    for (int i = 0; i < corporateAssessmentModel.getResult().get(0).getQuestions().size(); i++) {



                        */
/*Filter the question based on selected category
                        *
                        *
                        *
                        *
                        * *//*

                        if (corporateAssessmentModel.getResult().get(0).getQuestions().get(i).getCategory().equalsIgnoreCase(ASSESSMENT_CATEGORY)) {
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
                    */
/*Load the question recycler view adapter
                    *
                    *
                    *
                    * *//*

                    if (questionList != null) {
                        view.assignProgressBar(questionList.size());
                        callAssessmentAdapter(questionList);
                    }
                }

            }

            @Override
            public void onSuccessError(String errorMessage) {

                dataManager.toast(context, errorMessage);
                view.hideProgress();
                view.finishActivity();
            }

            @Override
            public void onError(NetworkError networkError) {
                dataManager.toast(context, "error  " + networkError.getMessage());
                dataManager.errorLog(tag, " Api Calling Error " + networkError.errorCode(), networkError.getError());
                view.hideProgress();
                view.finishActivity();
            }

        });
*/
//        compositeSubscription.add(subscription);
        String assessment_name = "", instruction = "";
        MySql dbHelper = new MySql(activity, "mydb", null, MySql.version);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Assessments_Table", null);
        @SuppressLint("Recycle") Cursor cursor1 = db.rawQuery("SELECT * FROM Assessments_Table where ASSESSMENT_TYPE = 'General Information'", null);
        String assessment_Questions = "";
        Log.i("CheckDB", "Cursor  count is "+cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            assessment_name = cursor.getString(cursor.getColumnIndex("ASSESSMENT_TYPE"));
            instruction = cursor.getString(cursor.getColumnIndex("ASSESSMENT_INSTRUCTION"));
            assessment_Questions = cursor.getString(cursor.getColumnIndex("QUESTION_LIST"));
            ASSESSMENT_VERSION = cursor.getString(cursor.getColumnIndex("ASSESSMENT_VERSION"));
        }
        Log.i("CheckDB", "Assessment typr in assesssments is"+assessment_name);
        ASSESSMENT_TYPE = assessment_name;

        Type type = new TypeToken<ArrayList<Question>>() {}.getType();
        Gson gson = new Gson();
        List<Question> finalOutputString = gson.fromJson(assessment_Questions, type);
        if (finalOutputString == null) {
            //TODO: download the assessment again and store it in db
            if (dataManager.isNetworkAvailable(activity)) {
            //    assesmentDownload(API_URL, service, activity, dataManager.get(AppConstants.ROLE, ""));
                initAssessmentData();
            }
            else {
                dataManager.alert(activity, "Network not available!!!", "Please check your intenet connection.", "OK");
            }
        }
        else {

            questionList = new ArrayList<>();
            for (int i = 0; i < finalOutputString.size(); i++) {
                Log.i("CheckDB", "Question is "+finalOutputString.get(i).getQuestion()+" Assessment caregory "+finalOutputString.get(i).getCategory());
                if (finalOutputString.get(i).getCategory().equals(ASSESSMENT_CATEGORY)) {
                    Log.i("CheckDB", "In final Assessment category "+finalOutputString.get(i).getQuestion());
                    questionList.add(finalOutputString.get(i));
                }
            }
            Log.i("ForUpdatingCode", "Questions list size is " + questionList.size());


            view.assignProgressBar(questionList.size());
            //instruction_tv = instruction;
            //title_tv = assessment_name;

            callAssessmentAdapter(questionList);
        }
        db.close();

    }


    @Override
    public void callAssessmentAdapter(List<Question> questionList) {
        recyclerView.setHasFixedSize(true);

        /*Disabling scroll control
        *
        * */
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);
        int option_size = questionList.get(1).getOptions().size();
        Log.i("WellBeingAssess", "options size is "+option_size);
        nSmilesAssessmentAdapter = new NSmilesAssessmentAdapter(activity,context, questionList);
        recyclerView.setAdapter(nSmilesAssessmentAdapter);


        /*Adapter onclick callback
        *
        *
        * */
        nSmilesAssessmentAdapter.setAssessmentOnClickListener(new NSmilesAssessmentAdapter.AssessmentOnClickListener() {
            @Override
            public void saveAssessment(List<Question> questionList,
                                       final int position,
                                       boolean scroll,
                                       boolean progress,
                                       boolean decrease_progress) {
                Log.d("gotToPreviousPosition", String.valueOf(position));
                adapterPositionP = position;
                adapterPositionN = position;

                saveAnswer(questionList);

                if (position > 0) {
                    view.showPreviousButton();
                }

                if (questionList.size() == position + 1) {
                    //view.showFooterLayout();
                    view.hideNextButton();
                }

                if (progress) {
                    update_progress_count++;
                    view.updateProgressBar(update_progress_count);
                }
                if (decrease_progress) {
                    update_progress_count--;
                    view.updateProgressBar(update_progress_count);
                }

                if (questionList.size() > position + 1) {

                    if (scroll) {

                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.scrollToPosition(position + 1);
                            }
                        }, 800);
                    }
                }


            }

            @Override
            public void moveToNext(int position) {

                recyclerView.scrollToPosition(position + 1);
                if (position == 0) {
                    view.showPreviousButton();
                }
            }

            @Override
            public void hidePrevious() {
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.hidePreviousButton();
                    }
                },2);

            }

            @Override
            public void hideNext() {
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.hideNextButton();
                    }
                },2);

            }

            @Override
            public void showNext() {
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.showNextButton();
                    }
                },2);

            }

            @Override
            public void showFooterLayout() {
                view.showFooterLayout();
            }

            @Override
            public void hideFooterLayout() {
                view.hideFooterLayout();
            }


            @Override
            public void stopBackgroundAnimation() {
                view.stopBackgroundAnimation();
            }

            @Override
            public void resumeBackgroundAnimation() {
                view.resumeBackgroundAnimation();
            }
        });
    }


    /*Update the array list
    *
    *
    * */
    @Override
    public void saveAnswer(List<Question> saveAnswer) {
        userAnswerList = saveAnswer;
    }


    /**/
    @Override
    public void validateAssessmentAnswer() {

        try {
            int count = 0;
            if (userAnswerList != null) {

                for (int i = 0; i < userAnswerList.size(); i++) {

                    if (userAnswerList.get(i).getUserAnswerNameList() == null ||
                            userAnswerList.get(i).getUserAnswerNameList().size() == 0 ||
                            userAnswerList.get(i).getUserAnswerNameList().get(0).getUserAnswerName() == null ||
                            userAnswerList.get(i).getUserAnswerNameList().get(0).getUserAnswerName().length() == 0 ||
                            userAnswerList.get(i).getUserAnswerValueList() == null ||
                            userAnswerList.get(i).getUserAnswerValueList().size() == 0 ||
                            userAnswerList.get(i).getUserAnswerValueList().get(0).getUserAnswerValue() == null ||
                            userAnswerList.get(i).getUserAnswerValueList().get(0).getUserAnswerValue().length() == 0) {
                        dataManager.toast(context, "Please fill all fields");
                        recyclerView.scrollToPosition(i);
                        break;
                    } else {
                        count++;
                    }
                }

                if (userAnswerList.size() == count) {
                    String mob = dataManager.get(AppConstants.SDK_NAME, "");
                    List<String> userRole = new ArrayList<>();
                    userRole.add(0, "user");
                    userRole.add(1, dataManager.get(AppConstants.ROLE, ""));

                    String combinedValue = "";
                    AssessmentJsonModel assessmentJsonModel = new AssessmentJsonModel();
                    AssessmentData assessmentData = new AssessmentData();
                    List<Answer> completedAnswerList = new ArrayList<>();

                    List<Answer_> answerList;
                    List<Value> valueList;
                    Value value;
                    Answer_ ans;
                    Answer answer;

                    for (int i = 0; i < userAnswerList.size(); i++) {

                        if (userAnswerList.get(i).getType().equalsIgnoreCase("multi")) {
                            ans = new Answer_();
                            valueList = new ArrayList<>();
                            answerList = new ArrayList<>();
                            for (int j = 0; j < userAnswerList.get(i).getUserAnswerValueList().size(); j++) {
                            //TODO: changed to fix the error in bmi
                                ans = new Answer_();
                                ans.setValue(userAnswerList.get(i).getUserAnswerValueList().get(j).getUserAnswerValue());
                                ans.setOption(userAnswerList.get(i).getUserAnswerNameList().get(j).getUserAnswerName());
                                answerList.add(ans);

/*
                                value = new Value();
                                value.setValue(userAnswerList.get(i).getUserAnswerValueList().get(j).getUserAnswerValue());
                                value.setType(userAnswerList.get(i).getUserAnswerNameList().get(j).getUserAnswerName());
                                valueList.add(value);
                                if (userAnswerList.get(i).getOptions().get(j).getHint() != null) {

                                    combinedValue = combinedValue.concat(userAnswerList.get(i).getOptions().get(j).getValue() + "  "
                                            + userAnswerList.get(i).getUserAnswerValueList().get(j).getUserAnswerValue() + "  "
                                            + userAnswerList.get(i).getOptions().get(j).getHint() + "  ");

                                }
*/
                            }
                           /* ans.setOption("a");
                            ans.setValue(combinedValue);
                            ans.setValues(valueList);
                            answerList.add(ans);
*/
                            answer = new Answer();

                            answer.setQNo(userAnswerList.get(i).getQNo());
                            answer.setAnswer(answerList);
                            completedAnswerList.add(answer);


                        } else {
                            answerList = new ArrayList<>();
                            for (int v = 0; v < userAnswerList.get(i).getUserAnswerValueList().size(); v++) {

                                ans = new Answer_();
                                ans.setValue(userAnswerList.get(i).getUserAnswerValueList().get(v).getUserAnswerValue());
                                ans.setOption(userAnswerList.get(i).getUserAnswerNameList().get(v).getUserAnswerName());
                                answerList.add(ans);

                            }
                            answer = new Answer();
                            answer.setQNo(userAnswerList.get(i).getQNo());
                            answer.setAnswer(answerList);
                            completedAnswerList.add(answer);

                        }
                        assessmentData.setName(preferenceManager.get(AppConstants.SDK_NAME, ""));
                        assessmentData.setEmail(preferenceManager.get(AppConstants.SDK_EMAIL, ""));
                        assessmentData.setOrgname("WELLBEING");
                        assessmentData.setUserId(dataManager.get(AppConstants.SDK_PROFILE_ID, ""));
                        assessmentData.setMobile(0L);
                        assessmentData.setPrimaryProfile(userRole);
                        assessmentData.setSecondaryProfile(dataManager.get(AppConstants.ROLE, ""));
                        assessmentData.setAssessmentType(ASSESSMENT_TYPE);
                        assessmentData.setAssessmentSubType(ASSESSMENT_CATEGORY);
                        assessmentData.setVersion(ASSESSMENT_VERSION);
                        assessmentData.setAssessmentTakenDate(dataManager.getDD_MM_YYYY_T_Format());
                        assessmentData.setAssessmentStartTime(null);
                        assessmentData.setAssessmentEndTime(dataManager.getDD_MM_YYYY_T_Format());
                        assessmentData.setAssessmentDuration(null);
                        assessmentData.setAssessmentOutputType("none");


                        Collections.sort(completedAnswerList, new Comparator<Answer>() {
                            @Override
                            public int compare(Answer o1, Answer o2) {

                                return o1.getQNo().compareTo(o2.getQNo());
                            }
                        });


                        assessmentData.setAnswers(completedAnswerList);
                        assessmentJsonModel.setAssessmentData(assessmentData);

                        String pri = new Gson().toJson(assessmentJsonModel);
                        dataManager.infoLog(tag, pri);

                    }
                    callReportGenerateApi(assessmentJsonModel);

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void previousButtonClickListener() {
        view.hideFooterLayout();
        view.showNextButton();
        Log.d("adapterPositionP", String.valueOf(adapterPositionP));
        if (adapterPositionP >= 0) {
            recyclerView.scrollToPosition(adapterPositionP);
            if (adapterPositionP == 0) {
                view.hidePreviousButton();
            }
            //adapterPositionN=adapterPositionP;
            adapterPositionP--;
        } else {
            view.hidePreviousButton();
        }
    }


    @Override
    public void nextButtonClickListener() {

        view.hideFooterLayout();
        view.showPreviousButton();
        try {
            if (questionList.size() != adapterPositionP) {
                Log.d("adapterPositionN", String.valueOf(adapterPositionN));
                adapterPositionP = adapterPositionP+2;
                recyclerView.scrollToPosition(adapterPositionP);
                adapterPositionP--;
                if (adapterPositionP == adapterPositionN ) {
                    view.hideNextButton();
                    view.showPreviousButton();
                }

            } else {
                view.hideNextButton();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void callReportGenerateApi(AssessmentJsonModel assessmentJsonModel) {
        view.showProgress();
        Subscription subscription = service.generateAssessmentReportApi(AppConstants.BEARER + dataManager.get(AppConstants.HB_USER_TOKEN, ""),
                assessmentJsonModel, new Service.GenerateReportCallBack() {
                    @Override
                    public void onSuccess(CorporateSuccess corporateSuccess) {

                        if (corporateSuccess != null) {


                            if (corporateSuccess.getErrors() != null) {
                                dataManager.toast(context, corporateSuccess.getErrors());
                            }
                           // dataManager.toast(context, "Report generated");
                            view.hideProgress();

                            activity.setResult(Activity.RESULT_OK, new Intent()
                                    .putExtra(AppConstants.ASSESSMENT, ASSESSMENT_CATEGORY)
                                    .putStringArrayListExtra(AppConstants.ASSESSMENT_CATEGORY, assess_category)

                            );
                            activity.finish();

                        }
                    }

                    @Override
                    public void onSuccessError(String errorMessage) {
                        view.hideProgress();
                        dataManager.toast(context, errorMessage);
                    }

                    @Override
                    public void onError(NetworkError networkError) {

                        view.hideProgress();
                        dataManager.toast(context, networkError.getMessage());
                    }
                });
        compositeSubscription.add(subscription);
    }


}
