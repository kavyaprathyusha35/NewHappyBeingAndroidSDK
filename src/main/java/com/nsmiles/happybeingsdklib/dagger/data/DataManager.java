package com.nsmiles.happybeingsdklib.dagger.data;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.nsmiles.happybeingsdklib.Utils.MySql;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Sukumar on 4/28/2018.
 */


public class DataManager {

    /*Singleton instance creation*/
    @Singleton
    private Context context;
    @Singleton
    private PreferenceManager preferenceManager;
    @Singleton
    private MySql dbHelper;
    @Singleton
    private HappyUtils happyUtils;


    /*Injecting Context, Database, Shared Preference and Happy Utils*/
    @Inject
    public DataManager(Context context,
                       MySql dbHelper,
                       PreferenceManager preferenceManager,
                       HappyUtils happyUtils) {

        this.context = context;
        this.preferenceManager = preferenceManager;
        this.dbHelper = dbHelper;
        this.happyUtils = happyUtils;
    }

    /*Provide Context*/
    public Context getContext(){
        return context;
    }

    /*Toast Message*/
    public void toast(Context context, String message){
        happyUtils.toast(context,message);
    }

    public void showSnackBarError(View view, String message){
        happyUtils.ShowSnackBar(view, message);
    }

    /*Alert Dialog Message*/
    public void alert(Activity activity, String title, String message, String button_name){
        happyUtils.alert(activity,title,message,button_name);
    }


    /*Information Log*/
     public void infoLog(String tag, String log){
         happyUtils.infoLog(tag, log);
     }

    /*Progress Bar*/
    public void progress(View view, int visibility, float alpha, int duration){
        if (view != null)
            happyUtils.animateView(view,visibility,alpha,duration);
    }

    /*Save String Shared Preference MultipleReportData*/
    public void save(String key, String value) {
        preferenceManager.add(key, value);
    }

    public void save(String key, Set<String> value){
        preferenceManager.add(key,value);
    }

    /*Save Integer Shared Preference MultipleReportData*/
    public void save(String key, int value) {
        preferenceManager.add(key, value);
    }

    /*Save Boolean Shared Preference MultipleReportData*/
    public void save(String key, boolean value) {
        preferenceManager.add(key, value);
    }

    /*Get String Shared Preference MultipleReportData*/
    public String get(String key, String value) {
        return preferenceManager.get(key, value);
    }

    public Set<String> get(String key){
        return preferenceManager.get(key);
    }

    /*Get Integer Shared Preference MultipleReportData*/
    public Integer get(String key, Integer value) {
        return preferenceManager.get(key, value);
    }

    /*Get Boolean Shared Preference MultipleReportData*/
    public Boolean get(String key, boolean value) {
        return preferenceManager.get(key, value);
    }

   /*Clean Shared Preference MultipleReportData*/
    public void clearPreference() {
        preferenceManager.clear();
    }



    /*Get 01 12 2018 Date Format */
    public String getDD_MM_YYY_Format(){
        return happyUtils.DD_MM_YYYY();
    }

    /*Get 01 12 2018 T 09:00:00.000 5:30 Format */
    public String getDD_MM_YYYY_T_Format(){
        return happyUtils.DD_MM_YYYY_T();
    }

    public String parseIOS_to_DDMMYYYY(String time){
        return happyUtils.parseIOS_to_DDMMYYYY(time);
    }

    /*Get Mobile Device Id */
    public String getDeviceId(){
        return happyUtils.getDeviceId();
    }

    /*Get Device OS*/
    public String getDeviceOs(){
        return happyUtils.device_info();
    }

    /*Get Current Time*/
    public String getStartDate(){
        return happyUtils.startTime();
    }

    /*Get Current Time*/
    public String getEndDate(){
        return happyUtils.endTime();
    }

    /*Get Current Time in Milli Seconds*/
    public Long getCurrentTimeMilliSeconds(){
        return happyUtils.currentTimeMilliSecond();
    }

    /*Convert Milli Seconds into Date Format 2018-JAN-01 T 09:00:00:000 5:30 GMT*/
    public String convertMilliSecondToDateFormat(Long milli){
        return happyUtils.convertMilliSecondToDateFormat(milli);
    }

    /*Is Network Available Or Not*/
    public boolean isNetworkAvailable(Activity activity){
        return happyUtils.isNetworkAvailable(activity);
    }


    /*Database Manipulation*/

/*
    */
/*Insert Alarm Notification MultipleReportData to SQLITE Database*/

    public Long insertNotificationData(UserInformation userInformation) throws Exception {
        return dbHelper.insertNotificationTimings(userInformation);
    }

/*Insert Activity Analytic MultipleReportData to SQLITE Database*//*

    public Long insertActivityData(ActivityDataModel activityDataModel) throws Exception {
        return dbHelper.insertActivityData(activityDataModel);
    }

    public AlertNotification getAlertPreferenceData() throws Exception {
        return dbHelper.getAlertPreferenceData();
    }

*/
    /*Database Manipulation*/


    public boolean hasText(EditText editText, String message) {
        return happyUtils.hasText(editText, message);
    }

    public boolean isValidEmail(EditText email, String message) {
        return happyUtils.isValid(email, message);
    }

    public boolean hasTextMobile(EditText editText, String message) {

        return happyUtils.hasTextMobile(editText, message);
    }

    public boolean isValidPhoneNumber(EditText editText, String abrevation) {
        return happyUtils.isValidPhoneNumber(editText, abrevation);
    }

    public float roundNumber(double d, int decimalPlace){

        return happyUtils.round(d,decimalPlace);
    }

    public boolean checkGratitudeDataAvailable(String email) throws Exception {
        return dbHelper.getViewMyDiaryData(email);
    }

    public boolean checkRelaxAudioAvailable() throws Exception {
        return dbHelper.getRelaxAudioStatus();
    }

    public boolean checkRelaxCoachAudioStudent() throws Exception {
        return dbHelper.getRelaxCoachAudioStudentStatus();
    }

    public boolean checkCoachAllAudio() throws Exception {
        return dbHelper.getCoachAllAudioStatus();
    }
}
