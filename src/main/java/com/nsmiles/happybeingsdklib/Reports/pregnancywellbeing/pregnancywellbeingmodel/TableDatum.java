package com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.pregnancywellbeingmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TableDatum implements Serializable
{

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("subcontent")
    @Expose
    private String subcontent;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("range")
    @Expose
    private String range;
    @SerializedName("ip_title")
    @Expose
    private String ipTitle;
    @SerializedName("ip_message")
    @Expose
    private String ipMessage;
    @SerializedName("range_percentage")
    @Expose
    private Double rangePercentage;
    @SerializedName("range_percentage_max")
    @Expose
    private Double rangePercentageMax;


    @SerializedName("sort_range")
    @Expose
    private Integer sortRange;
    private final static long serialVersionUID = -7250341922871839636L;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubcontent() {
        return subcontent;
    }

    public void setSubcontent(String subcontent) {
        this.subcontent = subcontent;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getIpTitle() {
        return ipTitle;
    }

    public void setIpTitle(String ipTitle) {
        this.ipTitle = ipTitle;
    }

    public String getIpMessage() {
        return ipMessage;
    }

    public void setIpMessage(String ipMessage) {
        this.ipMessage = ipMessage;
    }

    public Double getRangePercentage() {
        return rangePercentage;
    }

    public void setRangePercentage(Double rangePercentage) {
        this.rangePercentage = rangePercentage;
    }

    public Double getRangePercentageMax() {
        return rangePercentageMax;
    }

    public void setRangePercentageMax(Double rangePercentageMax) {
        this.rangePercentageMax = rangePercentageMax;
    }

    public Integer getSortRange() {
        return sortRange;
    }

    public void setSortRange(Integer sortRange) {
        this.sortRange = sortRange;
    }

}