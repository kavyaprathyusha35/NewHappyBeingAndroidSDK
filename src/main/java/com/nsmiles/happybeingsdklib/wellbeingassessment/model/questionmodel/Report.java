package com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Report implements Serializable
{

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("subtype")
    @Expose
    private String subtype;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("paymentcheck")
    @Expose
    private String paymentcheck;
    @SerializedName("reportcheck")
    @Expose
    private String reportcheck;
    @SerializedName("websitereporturl")
    @Expose
    private String websitereporturl;
    private final static long serialVersionUID = -6924221611631211942L;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPaymentcheck() {
        return paymentcheck;
    }

    public void setPaymentcheck(String paymentcheck) {
        this.paymentcheck = paymentcheck;
    }

    public String getReportcheck() {
        return reportcheck;
    }

    public void setReportcheck(String reportcheck) {
        this.reportcheck = reportcheck;
    }

    public String getWebsitereporturl() {
        return websitereporturl;
    }

    public void setWebsitereporturl(String websitereporturl) {
        this.websitereporturl = websitereporturl;
    }

}