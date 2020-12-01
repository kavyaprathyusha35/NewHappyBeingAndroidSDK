package com.nsmiles.happybeingsdklib.Registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserProfile implements Serializable
{

    @SerializedName("emailId")
    @Expose
    private String emailId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("registerorloggedin")
    @Expose
    private String registerorloggedin;
    private final static long serialVersionUID = 2904442197457205497L;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getRegisterorloggedin() {
        return registerorloggedin;
    }

    public void setRegisterorloggedin(String registerorloggedin) {
        this.registerorloggedin = registerorloggedin;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}