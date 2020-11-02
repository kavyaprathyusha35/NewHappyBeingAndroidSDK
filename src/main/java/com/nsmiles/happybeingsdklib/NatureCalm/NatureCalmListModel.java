package com.nsmiles.happybeingsdklib.NatureCalm;

import java.io.Serializable;
import java.util.ArrayList;

public class NatureCalmListModel implements Serializable {

    ArrayList<String> naturecalm_images;


    public ArrayList<String> getNaturecalm_images() {
        return naturecalm_images;
    }

    public void setNaturecalm_images(ArrayList<String> naturecalm_images) {
        this.naturecalm_images = naturecalm_images;
    }
}
