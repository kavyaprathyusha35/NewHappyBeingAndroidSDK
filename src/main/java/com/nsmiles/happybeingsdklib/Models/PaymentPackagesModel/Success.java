package com.nsmiles.happybeingsdklib.Models.PaymentPackagesModel;

import java.util.List;

/**
 * Created by Sukumar on 11/10/2020.
 */

public class Success {
    private String amount;
    private String currency;
    private String packagename;
    private List<String> scopes = null;
    private String validity;
    private String id;
    private String skuId;
    private String skuIdIos;
    private String type;
    private String additionaltext;
    private Boolean school;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Success withAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Success withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public Success withPackagename(String packagename) {
        this.packagename = packagename;
        return this;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public Success withScopes(List<String> scopes) {
        this.scopes = scopes;
        return this;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public Success withValidity(String validity) {
        this.validity = validity;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Success withId(String id) {
        this.id = id;
        return this;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Success withSkuId(String skuId) {
        this.skuId = skuId;
        return this;
    }

    public String getSkuIdIos() {
        return skuIdIos;
    }

    public void setSkuIdIos(String skuIdIos) {
        this.skuIdIos = skuIdIos;
    }

    public Success withSkuIdIos(String skuIdIos) {
        this.skuIdIos = skuIdIos;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Success withType(String type) {
        this.type = type;
        return this;
    }

    public String getAdditionaltext() {
        return additionaltext;
    }

    public void setAdditionaltext(String additionaltext) {
        this.additionaltext = additionaltext;
    }

    public Success withAdditionaltext(String additionaltext) {
        this.additionaltext = additionaltext;
        return this;
    }

    public Boolean getSchool() {
        return school;
    }

    public void setSchool(Boolean school) {
        this.school = school;
    }

    public Success withSchool(Boolean school) {
        this.school = school;
        return this;
    }

}
