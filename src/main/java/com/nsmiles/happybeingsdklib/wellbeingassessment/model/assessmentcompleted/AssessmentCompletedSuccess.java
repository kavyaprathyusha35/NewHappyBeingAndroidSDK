package com.nsmiles.happybeingsdklib.wellbeingassessment.model.assessmentcompleted;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AssessmentCompletedSuccess implements Serializable
{

    @SerializedName("wellbeingExists")
    @Expose
    private Integer wellbeingExists;
    private final static long serialVersionUID = 8136115153581207478L;

    public Integer getWellbeingExists() {
        return wellbeingExists;
    }

    public void setWellbeingExists(Integer wellbeingExists) {
        this.wellbeingExists = wellbeingExists;
    }

    @SerializedName("pregnancywellbeingExists")
    @Expose
    private Integer pregnancywellbeingExists;


    public Integer getPregnancywellbeingExists() {
        return pregnancywellbeingExists;
    }

    public void setPregnancywellbeingExists(Integer pregnancywellbeingExists) {
        this.pregnancywellbeingExists = pregnancywellbeingExists;
    }


    @SerializedName("corporatewellbeingv4Exists")
    @Expose
    private Integer corporatewellbeingExists;


    public Integer getCorporatewellbeingExists() {
        return corporatewellbeingExists;
    }

    public void setCorporatewellbeingExists(Integer corporatewellbeingExists) {
        this.corporatewellbeingExists = corporatewellbeingExists;
    }
}