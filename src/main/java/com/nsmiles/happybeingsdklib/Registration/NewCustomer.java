package com.nsmiles.happybeingsdklib.Registration;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewCustomer implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("clientId")
    @Expose
    private String clientId;
    @SerializedName("clientSecret")
    @Expose
    private String clientSecret;
    @SerializedName("userProfile")
    @Expose
    private UserProfile userProfile;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    private final static long serialVersionUID = -1309668875352192502L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

