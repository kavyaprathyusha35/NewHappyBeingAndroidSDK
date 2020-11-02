package com.nsmiles.happybeingsdklib.wellbeingassessment.model.categorymodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WellBeingCategoryStatusModel implements Serializable
{

    @SerializedName("success")
    @Expose
    private WellBeingCategorySuccess success;
    private final static long serialVersionUID = -4044962267434878092L;

    public WellBeingCategorySuccess getSuccess() {
        return success;
    }

    public void setSuccess(WellBeingCategorySuccess success) {
        this.success = success;
    }

}