package com.nsmiles.happybeingsdklib.Affimations;

/**
 * Created by nSmiles on 10/11/2017.
 */

public class AffirmationModel {

    private String user_id;
    private String date_time;
    private String email;
    private String primary_profile;
    private String secondary_profile;
    private String title;
    private String description;
    private String url;
    private String mycoach_completed_days;
    private String completed_date;
    private String sync_flag;
    private String mind_gym_type;
    public String getMind_gym_type() {
        return mind_gym_type;
    }

    public void setMind_gym_type(String mind_gym_type) {
        this.mind_gym_type = mind_gym_type;
    }

    public String getServer_id() {
        return server_id;
    }

    public void setServer_id(String server_id) {
        this.server_id = server_id;
    }

    private String server_id;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    private String response;
    private int _id;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMycoach_completed_days() {
        return mycoach_completed_days;
    }

    public void setMycoach_completed_days(String mycoach_completed_days) {
        this.mycoach_completed_days = mycoach_completed_days;
    }

    public String getCompleted_date() {
        return completed_date;
    }

    public void setCompleted_date(String completed_date) {
        this.completed_date = completed_date;
    }

    public String getSync_flag() {
        return sync_flag;
    }

    public void setSync_flag(String sync_flag) {
        this.sync_flag = sync_flag;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }


}
