package com.nsmiles.happybeingsdklib.Models;

// import com.bluelinelabs.logansquare.annotation.JsonField;
// import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by nSmiles on 6/1/2017.
 */
// @JsonObject
@SuppressWarnings("DefaultFileTemplate")
public class AlertPreference {

    private int id;
    private String response;
    private String email;
    private String mind_gym_start_time;
    private String mind_gym_end_time;
    private String relax_start_time;
    private String relax_end_time;
    private String gender;



    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}