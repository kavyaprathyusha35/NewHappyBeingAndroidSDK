package com.nsmiles.happybeingsdklib.Models.CorporateWellbeing;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kavya on 6/20/2019.
 */

public class Data {
    @SerializedName("userAssessmentData")
    private UserAssessmentData userAssessmentData;

    @SerializedName("userAssessmentData")
    public UserAssessmentData getUserAssessmentData() {
        return userAssessmentData;
    }

    @SerializedName("userAssessmentData")
    public void setUserAssessmentData(UserAssessmentData userAssessmentData) {
        this.userAssessmentData = userAssessmentData;
    }

}
