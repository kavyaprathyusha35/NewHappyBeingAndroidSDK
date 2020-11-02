package com.nsmiles.happybeingsdklib.wellbeingassessment.model.categorymodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WellBeingCategoryCategory implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private Boolean status;
    private final static long serialVersionUID = 8897773001174537955L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}