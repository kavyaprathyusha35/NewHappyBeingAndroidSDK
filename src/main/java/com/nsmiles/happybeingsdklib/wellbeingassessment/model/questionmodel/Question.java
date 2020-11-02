package com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel;

/**
 * Created by Sukumar on 5/10/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable
{

    @SerializedName("qNo")
    @Expose
    private Integer qNo;
    @SerializedName("titleassess")
    @Expose
    private String titleassess;

    @SerializedName("question")
    @Expose
    private String question;

    @SerializedName("optionTextType")
    @Expose
    private OptionTextType optionTextType;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("group")
    @Expose
    private String group;

    private String userAnswerName;
    private String userAnswerValue;

    private List<Question> userAnswerNameList;
    private List<Question> userAnswerValueList;

    private String text_option;
    private String text_value;

    private String assessment_type, assessment_version;
    private final static long serialVersionUID = -6464926649227855529L;

    public Integer getQNo() {
        return qNo;
    }

    public void setQNo(Integer qNo) {
        this.qNo = qNo;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public OptionTextType getOptionTextType() {
        return optionTextType;
    }

    public void setOptionTextType(OptionTextType optionTextType) {
        this.optionTextType = optionTextType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUserAnswerName() {
        return userAnswerName;
    }

    public void setUserAnswerName(String userAnswerName) {
        this.userAnswerName = userAnswerName;
    }

    public String getUserAnswerValue() {
        return userAnswerValue;
    }

    public void setUserAnswerValue(String userAnswerValue) {
        this.userAnswerValue = userAnswerValue;
    }


    public List<Question> getUserAnswerNameList() {
        return userAnswerNameList;
    }

    public void setUserAnswerNameList(List<Question> userAnswerNameList) {
        this.userAnswerNameList = userAnswerNameList;
    }

    public List<Question> getUserAnswerValueList() {
        return userAnswerValueList;
    }

    public void setUserAnswerValueList(List<Question> userAnswerValueList) {
        this.userAnswerValueList = userAnswerValueList;
    }

    public String getText_option() {
        return text_option;
    }

    public void setText_option(String text_option) {
        this.text_option = text_option;
    }

    public String getText_value() {
        return text_value;
    }

    public void setText_value(String text_value) {
        this.text_value = text_value;
    }

    public String getAssessment_type() {
        return assessment_type;
    }

    public void setAssessment_type(String assessment_type) {
        this.assessment_type = assessment_type;
    }

    public String getAssessment_version() {
        return assessment_version;
    }

    public void setAssessment_version(String assessment_version) {
        this.assessment_version = assessment_version;
    }


    public String getTitleassess() {
        return titleassess;
    }

    public void setTitleassess(String titleassess) {
        this.titleassess = titleassess;
    }
}
