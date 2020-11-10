package com.nsmiles.happybeingsdklib.wellbeingassessment.model.categorymodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WellBeingCategoryStatusModel implements Serializable
{

    @SerializedName("result")
    @Expose
    private WellBeingResultModel result;
    private final static long serialVersionUID = -4044962267434878092L;

    public WellBeingResultModel getResult() {
        return result;
    }

    public void setResult(WellBeingResultModel result) {
        this.result = result;
    }
}