package com.nsmiles.happybeingsdklib.Models.CorporateWellbeing;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kavya on 6/20/2019.
 */

public class CorporateWellbeingReportModel {

    @SerializedName("result")
    private CorprateWellBeingModel result;


    public CorprateWellBeingModel getResult() {
        return result;
    }

    public void setResult(CorprateWellBeingModel result) {
        this.result = result;
    }
}
