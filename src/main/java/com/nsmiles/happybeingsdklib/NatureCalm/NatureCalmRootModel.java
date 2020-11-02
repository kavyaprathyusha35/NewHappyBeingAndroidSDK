package com.nsmiles.happybeingsdklib.NatureCalm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NatureCalmRootModel implements Serializable
{

    @SerializedName("success")
    @Expose
    private NatureCalmModel success;
    private final static long serialVersionUID = -4044962267434878092L;

    public NatureCalmRootModel(NatureCalmModel success) {
        this.success = success;

    }

    public NatureCalmModel getSuccess() {
        return success;
    }

    public void setSuccess(NatureCalmModel success) {
        this.success = success;
    }

}