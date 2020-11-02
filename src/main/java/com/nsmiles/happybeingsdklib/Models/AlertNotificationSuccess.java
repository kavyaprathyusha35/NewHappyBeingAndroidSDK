package com.nsmiles.happybeingsdklib.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AlertNotificationSuccess implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;
    private final static long serialVersionUID = -4723402419873962554L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}