package com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.pregnancywellbeingcategorymodel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sukumar on 6/23/2018.
 */

public class GeneralWellBeingCategoryModel implements Serializable {

    @SerializedName("err")
    @Expose
    private String err;
    @SerializedName("success")
    @Expose
    private Success success;
    private final static long serialVersionUID = -5444537848715050563L;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    @SerializedName("errors")
    @Expose
    private String errors;


    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}
