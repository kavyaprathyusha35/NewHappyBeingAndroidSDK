package com.nsmiles.happybeingsdklib.dagger.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.nsmiles.happybeingsdklib.Utils.AppConstants;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Admin on 19-03-2017.
 */
@SuppressWarnings("DefaultFileTemplate")
public class UserInformation {
    private String name;
    private String screenName;
    private String id;
    private String mobileNumber;
    private String emailId;
    private boolean isPaid;
    private String role;
    private SharedPreferences sharedPreferences;
    private String response_string;
    private String token_string;
    private String secondary_Profile;

    private String oldPassword;
    private String newPassword;
    private String gender;

    private String mind_gym_end_time;
    private String relax_start_time;
    private String relax_end_time;
    private String mind_gym_start_time;
    private String happy_moment_time;


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMind_gym_start_time() {
        return mind_gym_start_time;
    }

    public void setMind_gym_start_time(String mind_gym_start_time) {
        this.mind_gym_start_time = mind_gym_start_time;
    }



    public String getMind_gym_end_time() {
        return mind_gym_end_time;
    }

    public void setMind_gym_end_time(String mind_gym_end_time) {
        this.mind_gym_end_time = mind_gym_end_time;
    }

    public String getRelax_start_time() {
        return relax_start_time;
    }

    public void setRelax_start_time(String relax_start_time) {
        this.relax_start_time = relax_start_time;
    }

    public String getRelax_end_time() {
        return relax_end_time;
    }

    public void setRelax_end_time(String relax_end_time) {
        this.relax_end_time = relax_end_time;
    }


    private String response;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getSecondary_Profile() {
        return secondary_Profile;
    }

    public void setSecondary_Profile(String secondary_Profile) {
        this.secondary_Profile = secondary_Profile;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public String getId() {
        return id;
    }

    public String getToken_string() {
        return token_string;
    }

    public void setToken_string(String token_string) {
        this.token_string = token_string;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getResponse_string() {
        return response_string;
    }

    public void setResponse_string(String response_string) {
        this.response_string = response_string;
    }


    public String getHappy_moment_time() {
        return happy_moment_time;
    }

    public void setHappy_moment_time(String happy_moment_time) {
        this.happy_moment_time = happy_moment_time;
    }

    public boolean isLogedIn(Context context) {
        sharedPreferences = context.getSharedPreferences("HAPPY_BEING", MODE_PRIVATE);
        return sharedPreferences.getBoolean(AppConstants.IS_SIGNED_IN, false);
    }

    public void setLogedIn(Context context, boolean logedIn) {
        sharedPreferences = context.getSharedPreferences("HAPPY_BEING", MODE_PRIVATE);
        sharedPreferences.getBoolean(AppConstants.IS_SIGNED_IN, logedIn);
    }
}
