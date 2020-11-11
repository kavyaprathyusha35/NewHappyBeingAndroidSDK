package com.nsmiles.happybeingsdklib.Models.PaymentPackagesModel;

import java.util.List;

/**
 * Created by Kavya on 11/10/2020.
 */

public class Result {
    private List<Success> success = null;

    public List<Success> getSuccess() {
        return success;
    }

    public void setSuccess(List<Success> success) {
        this.success = success;
    }

    public Result withSuccess(List<Success> success) {
        this.success = success;
        return this;
    }
}
