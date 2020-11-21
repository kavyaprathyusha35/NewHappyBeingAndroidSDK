package com.nsmiles.happybeingsdklib.ServerApiConnectors;

/**
 * Created by Admin on 18-03-2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public class Urls {

     private static final String SERVER_ADDRESS_V2 = "https://api.nsmiles.com/v2";
     //private static final String SERVER_ADDRESS_V2 = "https://api.widget.nsmiles.com//v2";
   // private static final String SERVER_ADDRESS_V2 = "https://testapi.nsmiles.com/v2";
    private static final String SERVER_ADDRESS_V1 = "https://partner.nsmiles.com/v1";
    private static final String LIVE = "https://api.nsmiles.com";
    private static final String DYNAMIC_API = "https://api.nsmiles.com/questionnaires";


   //  private static final String SERVER_ADDRESS_V2 = "http://testapi.nsmiles.com/v2";
  //  private static final String LIVE = "http://testapi.nsmiles.com";
  //  private static final String DYNAMIC_API = "http://testapi.nsmiles.com/questionaires";


    public static String getSignUpUrl() {
        return SERVER_ADDRESS_V2 + "/user/signup";
    }

    public static String getSignInUrl() {
        return SERVER_ADDRESS_V2 + "/user/login";
    }

    public static String getCsrfKayUrl() {
        return SERVER_ADDRESS_V2 +"/csrfToken";
    }

    public static String getSetPaymentStatusUrl() {
        return SERVER_ADDRESS_V2 + "/user/payment";
    }

    public static String getPaymentStatusUrl() {
        return SERVER_ADDRESS_V2 + "/user/payment-expiry";
    }

    public static String getDownloadUrl() {
        return SERVER_ADDRESS_V2 + "/G_download/mindgym-for-adults/Day1-Loving-Kindness.mp3";
    }

    public static String getForgotPasswordUrl() {
        return SERVER_ADDRESS_V2 + "/user/forgot-password";
    }

    public static String getActivityUrl() {
        return SERVER_ADDRESS_V2 + "/activity";
    }
	
	public static String saveSelfLoveURL() {
        return SERVER_ADDRESS_V1 + "/questionnaires/gratitude";
    }

    public static String saveMyGoalURL() {
        return SERVER_ADDRESS_V2 + "/savegoals";

    }
    public static String savePaidAssessmentWellBeingURL() {
        return LIVE + "/appform/get";

    }

    public static String savePaidAssessmentStudentURL() {
        return LIVE + "/genericform/get";

    }


    public static String saveTrackingGoalURL() {
        return SERVER_ADDRESS_V2 + "/savegoalstrack";
    }

    public static String viewTrackingGoalURL() {
        return SERVER_ADDRESS_V2 + "/goalstrack";
    }

    public static String saveEmotionsURL() {
        return SERVER_ADDRESS_V1 + "/questionnaires/emotion";
    }

    public static String updateSecondaryProfileURL() {
        return SERVER_ADDRESS_V2 + "/user/update-details";
    }

    public static String updatePasswordURL() {
        return SERVER_ADDRESS_V2 + "/user/change-password";
    }

    public static String getViewMyDiaryUrl() {
        return SERVER_ADDRESS_V2 + "/gratitude";
    }


    public static String getMindGymUrl() {

        return SERVER_ADDRESS_V1 + "/get/getMindGym";
    }


    public static String getWellBeingAssessmentUrl() {

        return SERVER_ADDRESS_V2 + "/user/wellbeingreportbyid";
    }

    public static String getCareerAssessmentUrl() {

        return SERVER_ADDRESS_V2 + "/user/careerassessmentbyid";
    }

    public static String getCorporateCareerAssessmentUrl() {

        return SERVER_ADDRESS_V2 + "/user/corporatewellbeingreportbyid";
    }

    public static String getEmployabilityAssessmentUrl() {

        return SERVER_ADDRESS_V2 + "/user/employabilityReportbyid";
    }


    public static String getHappyExamAssessmentUrl() {

        return SERVER_ADDRESS_V2 + "/user/happyexamsReportbyid";
    }

    public static String getStudentAllAssessmentStatus(){
        return SERVER_ADDRESS_V2+ "/user/getStudentAssessmentStatus";
    }


    public static String getDynamicAssessmentUrl() {
               return DYNAMIC_API;   /// 14 days 9 audio

    }

    public static String getStudentCareerUrl() {

        return SERVER_ADDRESS_V2 + "/user/careerinterestbyid";
    }

    public static String getStudentLeaningUrl() {

        return SERVER_ADDRESS_V2 + "/user/learningstylebyid";
    }

    public static String getStudentStudySkillUrl() {

        return SERVER_ADDRESS_V2 + "/user/studentSkillsReport";
    }


    public static String getStudentConcentrationUrl() {

        return SERVER_ADDRESS_V2 + "/user/concentrationReport";
    }


    public static String getStudentWillPowerUrl() {

        return SERVER_ADDRESS_V2 + "/user/willpowerReport";
    }


    public static String getStudentAnxietyUrl() {

        return SERVER_ADDRESS_V2 + "/user/examAnxietyReport";
    }

    public static String getPackageUrl() {

        return SERVER_ADDRESS_V2 + "/getPaymentPackages";
    }

    public static String getPackageExpiryUrl() {

        return SERVER_ADDRESS_V1 + "/questionnaires/user/payment-expiry";
    }


    public static String getLifeReadynessAssessmentUrl() {

        return SERVER_ADDRESS_V2 + "/user/lifereadinessassessmentbyid";
    }

    public static String getWellBeingCompletedUrl() {

        return SERVER_ADDRESS_V2 + "/user/getWellbeingAssessmentStatus";
    }

    public static String getWellBeingCorporateCompletedUrl() {

        return SERVER_ADDRESS_V2 + "/user/getCorporateWellbeingAssessmentStatus";
    }

    public static String getCollegeAssessmentStatusUrl() {

        return SERVER_ADDRESS_V2 + "/user/getCollegeAssessmentStatus";
    }

    @SuppressWarnings("SameReturnValue")
    public static String getPayPalTokenURL() {
        return "https://api.sandbox.paypal.com/v1/oauth2/token";
    }

    public static String getViewGoalUrl() {
        return SERVER_ADDRESS_V2 + "/goals";
    }

    public static String getVersionsUrl() {
        return SERVER_ADDRESS_V2 + "/admin/versions";
    }

    public static String updateSettingsURL() {
        return SERVER_ADDRESS_V2 + "/user/updateSettings";
    }

    @SuppressWarnings("SameReturnValue")
    public static String getTypeFormURL() {
        return "https://api.typeform.com/v1/form/";
    }

    public static String getSaveAssessmentScoreUrl() {
        return SERVER_ADDRESS_V2 + "/assessment2";
    }

    public static String getAssessmentScoreURL() {
        return SERVER_ADDRESS_V2 + "/assessment2";
    }

    public static String getSaveAlertPreferenceUrl() {
        return SERVER_ADDRESS_V2 + "/user/settings";
    }

    public static String getSaveMyCoachUrl() {
        return SERVER_ADDRESS_V2 +"/mindgym";
    }

    public static String getSaveRelaxUrl() {
        return SERVER_ADDRESS_V2 +"/relieverelaxlist";
    }

    public static String getCompetitiveExam(){

        return "/competitive_exams";
    }

    public static String getStudySkills(){

        return "/study_skills";
    }

    public static String getPersonalityFinder(){

        return "/career_interests";
    }

    public static String getExamPerformanceAnxiety(){

        return "/exam_performances";
    }

    public static String getLearningStrategiesPlanner(){

        return "/learning_startegy";
    }

    public static String getWinningAttitudesAssessment(){

        return "/measure_your_attitude";
    }


    public static String getConcentrationAnalysis(){

        return "/measure_your_focus";
    }

    public static String getSplashScreenImageUrl() {
        return "https://api.nsmiles.com/v2/splashscreenlist";
    }

    public static String getPaymentPackagesUrl() {
        return "https://partner.nsmiles.com/v1/questionnaires/getPaymentPackages";
    }
}
