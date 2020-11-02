package com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel;

/**
 * Created by Sukumar on 5/15/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class A implements Serializable {

    @SerializedName("values")
    @Expose
    private List<String> values = null;
    private final static long serialVersionUID = 4619848714853564700L;

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
