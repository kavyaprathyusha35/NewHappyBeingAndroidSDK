package com.nsmiles.happybeingsdklib.wellbeingassessment.model.categorymodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class WellBeingCategorySuccess implements Serializable
{

    @SerializedName("category")
    @Expose
    private List<WellBeingCategoryCategory> category = null;
    private final static long serialVersionUID = 7221659529061596133L;

    public List<WellBeingCategoryCategory> getCategory() {
        return category;
    }

    public void setCategory(List<WellBeingCategoryCategory> category) {
        this.category = category;
    }

}