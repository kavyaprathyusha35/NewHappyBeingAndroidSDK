package com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.pregnancywellbeingmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Success implements Serializable
{

    @SerializedName("data")
    @Expose
    private Data data;
    private final static long serialVersionUID = 1809499977463483959L;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}