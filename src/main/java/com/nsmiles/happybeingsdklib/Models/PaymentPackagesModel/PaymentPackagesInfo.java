package com.nsmiles.happybeingsdklib.Models.PaymentPackagesModel;

/**
 * Created by Kavya on 11/10/2020.
 */

public class PaymentPackagesInfo {
    private Integer statusCode;
    private String message;
    private Boolean success;
    private Result result;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public PaymentPackagesInfo withStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PaymentPackagesInfo withMessage(String message) {
        this.message = message;
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public PaymentPackagesInfo withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public PaymentPackagesInfo withResult(Result result) {
        this.result = result;
        return this;
    }

    public String getErrors() {
        return getErrors();
    }
}
