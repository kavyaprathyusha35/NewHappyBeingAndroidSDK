package com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.statusmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryStatusModel implements Serializable
{

    @SerializedName("success")
    @Expose
    private Success success;
    private final static long serialVersionUID = -1978459762136025970L;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

}