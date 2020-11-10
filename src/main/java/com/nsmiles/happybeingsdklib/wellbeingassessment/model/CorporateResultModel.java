package com.nsmiles.happybeingsdklib.wellbeingassessment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CorporateResultModel implements Serializable {

    @SerializedName("result")
    @Expose
    private CorporateSuccess result;
    private final static long serialVersionUID = -3975161807641559582L;


    public CorporateSuccess getResult() {
        return result;
    }

    public void setResult(CorporateSuccess result) {
        this.result = result;
    }
}
