package com.nsmiles.happybeingsdklib.MindGym;

// import com.bluelinelabs.logansquare.annotation.JsonField;
// import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by nSmiles on 6/1/2017.
 */
// @JsonObject
@SuppressWarnings("DefaultFileTemplate")
public class AddEmotionRequest {

    private int id;
    private String user_id;
    private String date_time;
    private String device_id;
    private String email;


    private String feature;
    private String activity;
    private String emotion1;
    private String start_date_time;
    private String end_date_time;
    private String response;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
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

    public String getStart_date_time() {
        return start_date_time;
    }

    public void setStart_date_time(String start_date_time) {
        this.start_date_time = start_date_time;
    }

    public String getEnd_date_time() {
        return end_date_time;
    }

    public void setEnd_date_time(String end_date_time) {
        this.end_date_time = end_date_time;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getEmotion1() {
        return emotion1;
    }

    public void setEmotion1(String emotion1) {
        this.emotion1 = emotion1;
    }

}