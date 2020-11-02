package com.nsmiles.happybeingsdklib.wellbeingassessment.model.assessmentcompleted;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AssessmentCompletedStatus implements Serializable {

    @SerializedName("success")
    @Expose
    private AssessmentCompletedSuccess success;
    private final static long serialVersionUID = 8286459608097149813L;

    public AssessmentCompletedSuccess getSuccess() {
        return success;
    }

    public void setSuccess(AssessmentCompletedSuccess success) {
        this.success = success;
    }



}