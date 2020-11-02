package com.nsmiles.happybeingsdklib.network;


import com.nsmiles.happybeingsdklib.Models.CorporateWellbeing.CorporateWellbeingReportModel;
import com.nsmiles.happybeingsdklib.Models.SendEmailModel;
import com.nsmiles.happybeingsdklib.Models.SendGratitudeModel;
import com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.pregnancywellbeingcategorymodel.GeneralWellBeingCategoryModel;
import com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.pregnancywellbeingmodel.GeneralWellBeingModel;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.CorporateSuccess;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.answermodel.AssessmentJsonModel;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel.CorporateAssessModel;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ennur on 6/25/16.
 */
public interface NetworkService {


    String generalWellBeing = "questionnaires/general_wellbeing_homemaker";
    String getGeneralWellBeingCategory = "questionnaires/report/";
    String generalWellBeingCategoryStatus = "v2/getCategoryStatus/WELLBEING";
    String generalAllCompletedStatus = "v2/user/getWellbeingAssessmentStatus";
    String getCorporateWellbeingUrl = "questionnaires/report/CORPORATEWELLBEINGV4";
     //String getCorporateWellbeingUrls = "v2/user/corporatewellbeingv4report";

    String pregnancyWellBeing = "questionnaires/pregnancy_wellbeing";
    String pregnancyWellBeingCategoryStatus = "v2/getCategoryStatus/PREGNANCYWELLBEING";
    String pregnancyAllCompletedStatus = "v2/user/getPregnancyWellbeingAssessmentStatus";


    //String corporateWellBeing = "questionnaires/corporate_wellness";
    String corporateWellBeing = "questionnaires/CorporateWellbeing";
    //String corporateWellBeingCategoryStatus = "v2/getCategoryStatus/CORPORATEWELLBEING";
    String corporateWellBeingCategoryStatus = "v2/getCategoryStatus/CORPORATEWELLBEINGV4";
    //String corporateAllCompletedStatus = "v2/user/getCorporateWellbeingAssessmentStatus";
    String corporateAllCompletedStatus = "v2/user/getCorporateWellbeingV4Status";


    String job_employability_skills = "questionnaires/employability_skills";
    String job_career_interest = "questionnaires/strength_finder";
    String job_multiple_intelligences = "questionnaires/multiple_intelligences";

    String overcome_exam_pressure = "questionnaires/competitive_exams";

    String achieve_multiple_intelligence = "questionnaires/multiple_intelligences";
    String achieve_study_planner = "questionnaires/study_skills";
    String achieve_boost_concentration = "questionnaires/measure_your_focus";
    String achieve_will_power = "questionnaires/measure_your_attitude";

    String studentWellBeing = "questionnaires/student_wellbeing";


    //@GET("questionaires/well_being")
    @GET("questionnaires/corporate_wellbeing")
    Observable<CorporateAssessModel> getCorporateWellBeingAssessment();


    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
     @POST("genericform/get")
     Observable<CorporateSuccess> generateCorporateWellBeingAssessmentReport(@Header("Authorization") String authorization, @Body AssessmentJsonModel assessmentData);

    @GET
    Observable<CorporateAssessModel> getQuestionnaires(@Url String url);


    @GET("v2/user/wellbeingreportbyid")
    Observable<GeneralWellBeingModel> getGeneralWellBeingReport(@Header("authorization") String authorization, @Query("id") String userId);

    @Headers({
            "Content-type: application/json"
    })

    /*
    https://api.nsmiles.com/questionnaires/report/WELLBEING?reportname=WELLBEING&reportstable=["Physical well-being","Physical Well-being : Physical health","Physical wellbeing - Positive personal health behaviour:"]&language=en
https://api.nsmiles.com/questionnaires/report/WELLBEING?reportname=WELLBEING&reportstable=["Home and Self"]&language=en
    * */


    @GET
    Observable<GeneralWellBeingCategoryModel> getGeneralWellBeingCategoryReport(@Header("authorization") String authorization,
                                                                                @Url String url);
/*
    @GET
    Observable<CorporateWellbeingReportModel> getCorporateWellBeingCategoryReport(@Header("authorization") String authorization,
                                                                                  @Url String url);
*/


/*
    @PUT("v2/user/settings")
    Observable<AlertNotificationSuccess> pushAlertNotificationGenderData(@Header("Authorization") String authorization, @Body AlertNotification alertNotification);

    @GET("v2/user/employabilityReportbyid")
    Observable<EmployabilityReportModel> getEmployabilityReportData(@Header("authorization") String authorization, @Query("id") String userId);

    @GET("v2/user/lifereadinessassessmentbyid")
    Observable<LifeReadyCareerInterestModel> getLifeReadyOrPersonalAbilityReport(@Header("Authorization") String authorization, @Query("id") String userId);

*/

    @GET
    Observable<CorporateWellbeingReportModel> getCorporateWellBeingCategoryReport(@Header("authorization") String authorization,
                                                                                  @Url String url);
    @POST("/gratitude/others")
    Observable<SendEmailModel> gratitudeToOthers
            (@Body SendGratitudeModel sendGratitudeModel);

}
