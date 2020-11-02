package com.nsmiles.happybeingsdklib.Models;

// import com.bluelinelabs.logansquare.annotation.JsonField;
// import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by nSmiles on 6/1/2017.
 */
// @JsonObject
@SuppressWarnings("DefaultFileTemplate")
public class AssessmentScore {

    private String response;
    private int id;
    private String user_id;
    private String date_time;
    private String device_id;
    private String email;


    private String primary_profile;
    private String secondary_profile;
    private String assessment_name;
    private String assessment_date;
    private String score;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getPrimary_profile() {
        return primary_profile;
    }

    public void setPrimary_profile(String primary_profile) {
        this.primary_profile = primary_profile;
    }

    public String getSecondary_profile() {
        return secondary_profile;
    }

    public void setSecondary_profile(String secondary_profile) {
        this.secondary_profile = secondary_profile;
    }

    public String getAssessment_name() {
        return assessment_name;
    }

    public void setAssessment_name(String assessment_name) {
        this.assessment_name = assessment_name;
    }

    public String getAssessment_date() {
        return assessment_date;
    }

    public void setAssessment_date(String assessment_date) {
        this.assessment_date = assessment_date;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}