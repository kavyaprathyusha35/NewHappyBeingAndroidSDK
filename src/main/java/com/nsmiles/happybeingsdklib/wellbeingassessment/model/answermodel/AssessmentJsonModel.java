package com.nsmiles.happybeingsdklib.wellbeingassessment.model.answermodel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AssessmentJsonModel implements Serializable {

    @SerializedName("assessmentData")
    @Expose
    private AssessmentData assessmentData;
    private final static long serialVersionUID = -2119043918386350648L;

    public AssessmentData getAssessmentData() {
        return assessmentData;
    }

    public void setAssessmentData(AssessmentData assessmentData) {
        this.assessmentData = assessmentData;
    }


}