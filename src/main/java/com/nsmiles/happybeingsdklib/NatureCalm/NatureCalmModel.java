package com.nsmiles.happybeingsdklib.NatureCalm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NatureCalmModel implements Serializable {

    @SerializedName("screen")
    @Expose
    private NatureCalmListModel screen;
    private final static long serialVersionUID = -4044962267434878092L;


    public NatureCalmListModel getScreen() {
        return screen;
    }

    public void setScreen(NatureCalmListModel screen) {
        this.screen = screen;
    }
}
