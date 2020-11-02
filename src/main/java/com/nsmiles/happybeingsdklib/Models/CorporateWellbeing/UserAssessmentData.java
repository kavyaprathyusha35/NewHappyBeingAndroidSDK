package com.nsmiles.happybeingsdklib.Models.CorporateWellbeing;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sukumar on 6/20/2019.
 */

public class UserAssessmentData {
    @SerializedName("corporatewellbeingv4")
    private Corporatewellbeing corporatewellbeing;

    @SerializedName("corporatewellbeingv4")
    public Corporatewellbeing getCorporatewellbeing() {
        return corporatewellbeing;
    }

    @SerializedName("corporatewellbeingv4")
    public void setCorporatewellbeing(Corporatewellbeing corporatewellbeing) {
        this.corporatewellbeing = corporatewellbeing;
    }

}
