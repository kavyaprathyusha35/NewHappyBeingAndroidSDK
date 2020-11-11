package com.nsmiles.happybeingsdklib.Models;

/**
 * Created by Sukumar on 4/28/2018.
 */

public class PackageInfo {

    String email;
    String amount;
    String currency;
    String packageName;
    String[] scope;
    String assessment_name;
    int dayLeft;
    String sku_id;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String[] getScope() {
        return scope;
    }

    public void setScope(String[] scope) {
        this.scope = scope;
    }

    public String getAssessment_name() {
        return assessment_name;
    }

    public void setAssessment_name(String assessment_name) {
        this.assessment_name = assessment_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDayLeft() {
        return dayLeft;
    }

    public void setDayLeft(int dayLeft) {
        this.dayLeft = dayLeft;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }
}
