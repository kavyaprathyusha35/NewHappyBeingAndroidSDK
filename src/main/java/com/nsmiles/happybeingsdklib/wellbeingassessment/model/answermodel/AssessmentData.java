package com.nsmiles.happybeingsdklib.wellbeingassessment.model.answermodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AssessmentData implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("orgname")
    @Expose
    private String orgname;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("mobile")
    @Expose
    private Long mobile;
    @SerializedName("primary_profile")
    @Expose
    private List<String> primaryProfile = null;
    @SerializedName("secondary_profile")
    @Expose
    private String secondaryProfile;
    @SerializedName("assessment_type")
    @Expose
    private String assessmentType;
    @SerializedName("assessment_sub_type")
    @Expose
    private String assessmentSubType;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("assessment_taken_date")
    @Expose
    private String assessmentTakenDate;
    @SerializedName("assessment_start_time")
    @Expose
    private AssessmentStartTime assessmentStartTime;
    @SerializedName("assessment_end_time")
    @Expose
    private String assessmentEndTime;
    @SerializedName("assessment_duration")
    @Expose
    private Object assessmentDuration;
    @SerializedName("assessment_output_type")
    @Expose
    private String assessmentOutputType;
    @SerializedName("answers")
    @Expose
    private List<Answer> answers = null;
    private final static long serialVersionUID = 7276454896060131491L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public List<String> getPrimaryProfile() {
        return primaryProfile;
    }

    public void setPrimaryProfile(List<String> primaryProfile) {
        this.primaryProfile = primaryProfile;
    }

    public String getSecondaryProfile() {
        return secondaryProfile;
    }

    public void setSecondaryProfile(String secondaryProfile) {
        this.secondaryProfile = secondaryProfile;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentSubType() {
        return assessmentSubType;
    }

    public void setAssessmentSubType(String assessmentSubType) {
        this.assessmentSubType = assessmentSubType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAssessmentTakenDate() {
        return assessmentTakenDate;
    }

    public void setAssessmentTakenDate(String assessmentTakenDate) {
        this.assessmentTakenDate = assessmentTakenDate;
    }

    public AssessmentStartTime getAssessmentStartTime() {
        return assessmentStartTime;
    }

    public void setAssessmentStartTime(AssessmentStartTime assessmentStartTime) {
        this.assessmentStartTime = assessmentStartTime;
    }

    public String getAssessmentEndTime() {
        return assessmentEndTime;
    }

    public void setAssessmentEndTime(String assessmentEndTime) {
        this.assessmentEndTime = assessmentEndTime;
    }

    public Object getAssessmentDuration() {
        return assessmentDuration;
    }

    public void setAssessmentDuration(Object assessmentDuration) {
        this.assessmentDuration = assessmentDuration;
    }

    public String getAssessmentOutputType() {
        return assessmentOutputType;
    }

    public void setAssessmentOutputType(String assessmentOutputType) {
        this.assessmentOutputType = assessmentOutputType;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

}