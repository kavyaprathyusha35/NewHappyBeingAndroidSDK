package com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel;

/**
 * Created by Sukumar on 5/15/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OptionTextType implements Serializable
{

    @SerializedName("a")
    @Expose
    private A a;
    private final static long serialVersionUID = -7190611633886070124L;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

}
