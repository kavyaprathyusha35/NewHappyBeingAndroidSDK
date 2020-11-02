package com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Group implements Serializable
{

    @SerializedName("titlequestion")
    @Expose
    private String titlequestion;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("name")
    @Expose
    private String name;
    private final static long serialVersionUID = -1426675166514010824L;

    public String getTitlequestion() {
        return titlequestion;
    }

    public void setTitlequestion(String titlequestion) {
        this.titlequestion = titlequestion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}