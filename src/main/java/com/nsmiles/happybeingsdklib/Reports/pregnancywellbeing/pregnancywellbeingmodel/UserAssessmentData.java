package com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.pregnancywellbeingmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserAssessmentData implements Serializable
{

    @SerializedName("wellbeing")
    @Expose
    private Wellbeing wellbeing;
    private final static long serialVersionUID = -831900475188692054L;

    public Wellbeing getWellbeing() {
        return wellbeing;
    }

    public void setWellbeing(Wellbeing wellbeing) {
        this.wellbeing = wellbeing;
    }

}