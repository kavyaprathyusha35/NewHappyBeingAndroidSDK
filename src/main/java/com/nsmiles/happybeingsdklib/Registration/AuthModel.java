package com.nsmiles.happybeingsdklib.Registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Kavya on 9/21/2020.
 */

public class AuthModel implements Serializable
{

    @SerializedName("newCustomer")
    @Expose
    private NewCustomer newCustomer;
    private final static long serialVersionUID = 8840968809248060789L;

    public NewCustomer getNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(NewCustomer newCustomer) {
        this.newCustomer = newCustomer;
    }

}