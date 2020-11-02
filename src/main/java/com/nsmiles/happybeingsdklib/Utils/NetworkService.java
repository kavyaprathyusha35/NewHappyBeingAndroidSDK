package com.nsmiles.happybeingsdklib.Utils;

import com.nsmiles.happybeingsdklib.Registration.AuthModel;
import com.nsmiles.happybeingsdklib.Registration.AuthSuccessModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

//import nsmiles.career.assessment.mvp.Models.ReportModel.MultipleIntelligenceReportModel.MultipleIntelligenceReportModel;
//import nsmiles.career.assessment.mvp.Models.ReportModel.StudyPlannerReportModel.StudyPlannerReportModel;

/**
 * Created by ennur on 6/25/16.
 */
public interface NetworkService {

    String corporateWellBeing = "questionnaires/CorporateWellbeing";
    //String corporateWellBeingCategoryStatus = "v2/getCategoryStatus/CORPORATEWELLBEING";
    String corporateWellBeingCategoryStatus = "v2/getCategoryStatus/CORPORATEWELLBEINGV4";
    //String corporateAllCompletedStatus = "v2/user/getCorporateWellbeingAssessmentStatus";
    String corporateAllCompletedStatus = "v2/user/getCorporateWellbeingV4Status";

    @Headers({"Accept: application/json",
            "Content-type: application/json"})
    @POST("/v1/customers/register")
    Call<AuthSuccessModel> registerUser(@Header("Referer") String referrer, @Body AuthModel authModel);

}
