package com.nsmiles.happybeingsdklib.Models.CorporateWellbeing;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kavya on 6/20/2019.
 */

public class CorporateWellbeingReportModel {
    @SerializedName("err")
    private String err;
    @SerializedName("success")
    private Success success;

    @SerializedName("err")
    public String getErr() {
        return err;
    }

    @SerializedName("err")
    public void setErr(String err) {
        this.err = err;
    }

    @SerializedName("success")
    public Success getSuccess() {
        return success;
    }

    @SerializedName("success")
    public void setSuccess(Success success) {
        this.success = success;
    }
}
