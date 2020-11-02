package com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.pregnancywellbeingmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable
{

    @SerializedName("userAssessmentData")
    @Expose
    private UserAssessmentData userAssessmentData;
    private final static long serialVersionUID = -3797157817616299644L;

    public UserAssessmentData getUserAssessmentData() {
        return userAssessmentData;
    }

    public void setUserAssessmentData(UserAssessmentData userAssessmentData) {
        this.userAssessmentData = userAssessmentData;
    }

}