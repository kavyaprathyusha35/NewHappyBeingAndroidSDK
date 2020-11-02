package com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.pregnancywellbeingmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GeneralWellBeingModel implements Serializable
{

    @SerializedName("success")
    @Expose
    private Success success;
    private final static long serialVersionUID = 6172138909927324301L;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    @SerializedName("errors")
    @Expose
    private String errors;


    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

}