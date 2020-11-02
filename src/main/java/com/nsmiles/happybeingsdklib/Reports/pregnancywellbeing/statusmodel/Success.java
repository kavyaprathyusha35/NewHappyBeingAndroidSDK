package com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.statusmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Success implements Serializable
{

    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    private final static long serialVersionUID = 1263797139100478912L;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

}