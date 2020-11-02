package com.nsmiles.happybeingsdklib.wellbeingassessment.model.answermodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Answer_ implements Serializable
{

    @SerializedName("option")
    @Expose
    private String option;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("values")
    @Expose
    private List<Value> values = null;
    private final static long serialVersionUID = -9110247220865063336L;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

}