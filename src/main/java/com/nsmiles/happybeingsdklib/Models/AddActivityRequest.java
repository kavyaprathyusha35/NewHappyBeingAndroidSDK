package com.nsmiles.happybeingsdklib.Models;

// import com.bluelinelabs.logansquare.annotation.JsonField;
// import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by nSmiles on 6/1/2017.
 */
// @JsonObject
@SuppressWarnings("DefaultFileTemplate")
public class AddActivityRequest {

    private int id;
    private String user_id;
    private String date_time;
    private String device_id;
    private String feature_name;
    private String activity_name;
    private String activity_detail;
    private String start_date_time;
    private String end_date_time;
    private String response;
    private String device_os;

    public String getSync_flag() {
        return sync_flag;
    }

    public void setSync_flag(String sync_flag) {
        this.sync_flag = sync_flag;
    }

    private String sync_flag;


    public String getDevice_os() {
        return device_os;
    }

    public void setDevice_os(String device_os) {
        this.device_os = device_os;
    }



    public String getFeature_name() {
        return feature_name;
    }

    public void setFeature_name(String feature_name) {
        this.feature_name = feature_name;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getActivity_detail() {
        return activity_detail;
    }

    public void setActivity_detail(String activity_detail) {
        this.activity_detail = activity_detail;
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



}