package com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable
{
    @SerializedName("assessment_time")
    private String assessmentTime;
    @SerializedName("groups")
    @Expose
    private List<Group> groups = null;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("report_titles")
    @Expose
    private List<String> reportTitles = null;


    private final static long serialVersionUID = -3261686221312808030L;


    public List<String> getReportTitles() {
        return reportTitles;
    }

    public void setReportTitles(List<String> reportTitles) {
        this.reportTitles = reportTitles;
    }
    public String getAssessmentTime() {
        return assessmentTime;
    }

    @SerializedName("assessment_time")
    public void setAssessmentTime(String assessmentTime) {
        this.assessmentTime = assessmentTime;
    }


    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}