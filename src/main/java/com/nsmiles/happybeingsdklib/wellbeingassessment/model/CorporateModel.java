package com.nsmiles.happybeingsdklib.wellbeingassessment.model;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sukumar on 5/9/2018.
 */

public class CorporateModel implements Serializable {

    @SerializedName("report_titles")
    private List<String> reportTitles = null;
    private String title, status_title;
    private String area;
    private String image_url;
    private Drawable drawable_area;
    private boolean assessmentStatus;
    private Set<String> category_item;
    @SerializedName("assessment_time")
    private String assessmentTime;
    private String description;

    private Map<String, ArrayList<String>> category;

    public List<String> getReportTitles() {
        return reportTitles;
    }

    public void setReportTitles(List<String> reportTitles) {
        this.reportTitles = reportTitles;
    }
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Drawable getDrawable_area() {
        return drawable_area;
    }

    public void setDrawable_area(Drawable drawable_area) {
        this.drawable_area = drawable_area;
    }


    String assessment_title;
    String version;
    String question_number;
    String question;
    String question_type;
    String mandatory;
    String validation;
    String answer;
    String user_answer;
    String option;
    int number;


    String name;
    String email;
    String mobile;
    String primary_profile;
    String secondary_profile;
    String org_name;
    String response;

    String header;
    String sub_header;

    public boolean isAssessmentStatus() {
        return assessmentStatus;
    }

    public void setAssessmentStatus(boolean assessmentStatus) {
        this.assessmentStatus = assessmentStatus;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPrimary_profile() {
        return primary_profile;
    }

    public void setPrimary_profile(String primary_profile) {
        this.primary_profile = primary_profile;
    }
    public String getAssessmentTime() {
        return assessmentTime;
    }

    public void setAssessmentTime(String assessmentTime) {
        this.assessmentTime = assessmentTime;
    }

    public String getSecondary_profile() {
        return secondary_profile;
    }

    public void setSecondary_profile(String secondary_profile) {
        this.secondary_profile = secondary_profile;
    }


    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }





    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }



    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    List<CorporateModel> assessOptionList;
    List<CorporateModel> answer_option;
    List<CorporateModel> subQuestionList;


    public List<CorporateModel> getSubQuestionList() {
        return subQuestionList;
    }

    public void setSubQuestionList(List<CorporateModel> subQuestionList) {
        this.subQuestionList = subQuestionList;
    }

    public List<CorporateModel> getAnswer_option() {
        return answer_option;
    }

    public void setAnswer_option(List<CorporateModel> answer_option) {
        this.answer_option = answer_option;
    }

    public List<CorporateModel> getAssessOptionList() {
        return assessOptionList;
    }

    public void setAssessOptionList(List<CorporateModel> assessOptionList) {
        this.assessOptionList = assessOptionList;
    }



    public String getAssessment_title() {
        return assessment_title;
    }

    public void setAssessment_title(String assessment_title) {
        this.assessment_title = assessment_title;
    }


    public String getVersion_number() {
        return version;
    }

    public void setVersion_number(String version_number) {
        this.version = version_number;
    }

    public String getQuestion_number() {
        return question_number;
    }

    public void setQuestion_number(String question_number) {
        this.question_number = question_number;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSub_header() {
        return sub_header;
    }

    public void setSub_header(String sub_header) {
        this.sub_header = sub_header;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Map<String, ArrayList<String>> getCategory() {
        return category;
    }

    public void setCategory(Map<String, ArrayList<String>> category) {
        this.category = category;
    }

    public Set<String> getCategory_item() {
        return category_item;
    }

    public void setCategory_item(Set<String> category_item) {
        this.category_item = category_item;
    }

    public String getStatus_title() {
        return status_title;
    }

    public void setStatus_title(String status_title) {
        this.status_title = status_title;
    }
}
