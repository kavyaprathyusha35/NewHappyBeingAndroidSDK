package com.nsmiles.happybeingsdklib.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AlertNotification implements Serializable
{

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("morning_remainder_time_stamp1")
    @Expose
    private String coachMorningAudio;


    @SerializedName("evening_remainder_time_stamp2")
    @Expose
    private String coachEveningGratitude;


    @SerializedName("start_time_for_emotion_check")
    @Expose
    private String relaxStartTime;


    @SerializedName("end_time_for_emotion_check")
    @Expose
    private String relaxEndTime;


    @SerializedName("gender")
    @Expose
    private String gender;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCoachMorningAudio() {
        return coachMorningAudio;
    }

    public void setCoachMorningAudio(String coachMorningAudio) {
        this.coachMorningAudio = coachMorningAudio;
    }

    public String getCoachEveningGratitude() {
        return coachEveningGratitude;
    }

    public void setCoachEveningGratitude(String coachEveningGratitude) {
        this.coachEveningGratitude = coachEveningGratitude;
    }

    public String getRelaxStartTime() {
        return relaxStartTime;
    }

    public void setRelaxStartTime(String relaxStartTime) {
        this.relaxStartTime = relaxStartTime;
    }

    public String getRelaxEndTime() {
        return relaxEndTime;
    }

    public void setRelaxEndTime(String relaxEndTime) {
        this.relaxEndTime = relaxEndTime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}