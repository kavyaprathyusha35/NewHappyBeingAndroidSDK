package com.nsmiles.happybeingsdklib.mycoachfragment.presenter;

import com.nsmiles.happybeingsdklib.wellbeingassessment.implementation.WellBeingCategoryImplementation;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.answermodel.AssessmentData;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.answermodel.AssessmentJsonModel;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kavya on 10/28/2020.
 */

public class CommonPresenter {
    public interface WellBeingCategoryPresenter {

        void categoryReportStatus();
        void categoryReportStatuss();
        void showCategoryReport(ArrayList<String> category);
        void checkAAssessmentCompletedStatus();
        void viewCombinedReport();
        void viewDetailReport();
        void initCorporateData();
        void callAdapter();
        void onDestroy();
        void checkAssessmentPaymentStatus(WellBeingCategoryImplementation.WellBeingCallBack callBack);
    }
    public interface ReportInterface {
        void scoreInfomation(boolean lock, int position);
    }
    public interface WellBeingAssessmentPresenter {


        void initAssessmentData();
        void callAssessmentAdapter(List<Question> questionList);
        void saveAnswer(List<Question> saveAnswer);
        void validateAssessmentAnswer();
        void previousButtonClickListener();

        void nextButtonClickListener();

        void callReportGenerateApi(AssessmentData assessmentJsonModel);
    }
    public interface APICallBack {
        void apiCallingCompleted();
    }

}
