package com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel;

/**
 * Created by Sukumar on 5/10/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable
{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;

    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;

    @SerializedName("report")
    @Expose
    private Report report;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }


    @SerializedName("instructions")
    @Expose
    private Instructions instructions;
    private final static long serialVersionUID = -2372215686399688924L;

    public Instructions getInstructions() {
        return instructions;
    }

    public void setInstructions(Instructions instructions) {
        this.instructions = instructions;
    }

}