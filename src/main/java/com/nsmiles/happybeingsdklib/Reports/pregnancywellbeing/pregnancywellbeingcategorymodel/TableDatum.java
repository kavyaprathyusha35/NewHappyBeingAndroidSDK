package com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.pregnancywellbeingcategorymodel;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sukumar on 6/23/2018.
 */

public class TableDatum {

    @SerializedName("score")
    private Integer score;
    @SerializedName("range")
    private String range;
    @SerializedName("range_pdf")
    private String rangePdf;
    @SerializedName("ip_title")
    private String ipTitle;
    @SerializedName("subtext")
    private String subtext;
    @SerializedName("subcontent")
    private String subcontent;
    @SerializedName("ip_message")
    private String ipMessage;
    @SerializedName("range_percentage")
    private Double rangePercentage;
    @SerializedName("range_percentage_max")
    private Double rangePercentageMax;
    @SerializedName("sort_range")
    private Integer sortRange;
    @SerializedName("strengths")
    private List<String> strengths = null;
    @SerializedName("goals")
    private List<String> goals = null;
    @SerializedName("firstStep")
    private String firstStep;
    @SerializedName("rewardPoint")
    private String rewardPoint;
    @SerializedName("finalText")
    private String finalText;
    @SerializedName("workOn")
    private String workOn;
    @SerializedName("positiveQ1_title")
    private String positiveQ1Title;
    @SerializedName("positiveQ1_desc")
    private String positiveQ1Desc;
    @SerializedName("positiveQ2_desc")
    private String positiveQ2Desc;
    @SerializedName("socialQ1_desc")
    private String socialQ1Desc;
    @SerializedName("ip_message1")
    private String ipMessage1;
    @SerializedName("financialQ1_title")
    private Object financialQ1Title;
    @SerializedName("financialQ1_desc")
    private Object financialQ1Desc;
    @SerializedName("health_addictions_table")
    private List<String> healthAddictionsTable = null;
    @SerializedName("no_health_addictions_table")
    private List<Object> noHealthAddictionsTable = null;
    @SerializedName("health_addictions_text")
    private String healthAddictionsText;
    @SerializedName("no_health_addictions_text")
    private String noHealthAddictionsText;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @SerializedName("score")
    public Integer getScore() {
        return score;
    }

    @SerializedName("score")
    public void setScore(Integer score) {
        this.score = score;
    }

    @SerializedName("range")
    public String getRange() {
        return range;
    }

    @SerializedName("range")
    public void setRange(String range) {
        this.range = range;
    }

    @SerializedName("range_pdf")
    public String getRangePdf() {
        return rangePdf;
    }

    @SerializedName("range_pdf")
    public void setRangePdf(String rangePdf) {
        this.rangePdf = rangePdf;
    }

    @SerializedName("ip_title")
    public String getIpTitle() {
        return ipTitle;
    }

    @SerializedName("ip_title")
    public void setIpTitle(String ipTitle) {
        this.ipTitle = ipTitle;
    }

    @SerializedName("subtext")
    public String getSubtext() {
        return subtext;
    }

    @SerializedName("subtext")
    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }

    @SerializedName("subcontent")
    public String getSubcontent() {
        return subcontent;
    }

    @SerializedName("subcontent")
    public void setSubcontent(String subcontent) {
        this.subcontent = subcontent;
    }

    @SerializedName("ip_message")
    public String getIpMessage() {
        return ipMessage;
    }

    @SerializedName("ip_message")
    public void setIpMessage(String ipMessage) {
        this.ipMessage = ipMessage;
    }

    @SerializedName("range_percentage")
    public Double getRangePercentage() {
        return rangePercentage;
    }

    @SerializedName("range_percentage")
    public void setRangePercentage(Double rangePercentage) {
        this.rangePercentage = rangePercentage;
    }

    @SerializedName("range_percentage_max")
    public Double getRangePercentageMax() {
        return rangePercentageMax;
    }

    @SerializedName("range_percentage_max")
    public void setRangePercentageMax(Double rangePercentageMax) {
        this.rangePercentageMax = rangePercentageMax;
    }

    @SerializedName("sort_range")
    public Integer getSortRange() {
        return sortRange;
    }

    @SerializedName("sort_range")
    public void setSortRange(Integer sortRange) {
        this.sortRange = sortRange;
    }

    @SerializedName("strengths")
    public List<String> getStrengths() {
        return strengths;
    }

    @SerializedName("strengths")
    public void setStrengths(List<String> strengths) {
        this.strengths = strengths;
    }

    @SerializedName("goals")
    public List<String> getGoals() {
        return goals;
    }

    @SerializedName("goals")
    public void setGoals(List<String> goals) {
        this.goals = goals;
    }

    @SerializedName("firstStep")
    public String getFirstStep() {
        return firstStep;
    }

    @SerializedName("firstStep")
    public void setFirstStep(String firstStep) {
        this.firstStep = firstStep;
    }

    @SerializedName("rewardPoint")
    public String getRewardPoint() {
        return rewardPoint;
    }

    @SerializedName("rewardPoint")
    public void setRewardPoint(String rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    @SerializedName("finalText")
    public String getFinalText() {
        return finalText;
    }

    @SerializedName("finalText")
    public void setFinalText(String finalText) {
        this.finalText = finalText;
    }

    @SerializedName("workOn")
    public String getWorkOn() {
        return workOn;
    }

    @SerializedName("workOn")
    public void setWorkOn(String workOn) {
        this.workOn = workOn;
    }

    @SerializedName("positiveQ1_title")
    public String getPositiveQ1Title() {
        return positiveQ1Title;
    }

    @SerializedName("positiveQ1_title")
    public void setPositiveQ1Title(String positiveQ1Title) {
        this.positiveQ1Title = positiveQ1Title;
    }

    @SerializedName("positiveQ1_desc")
    public String getPositiveQ1Desc() {
        return positiveQ1Desc;
    }

    @SerializedName("positiveQ1_desc")
    public void setPositiveQ1Desc(String positiveQ1Desc) {
        this.positiveQ1Desc = positiveQ1Desc;
    }

    @SerializedName("positiveQ2_desc")
    public String getPositiveQ2Desc() {
        return positiveQ2Desc;
    }

    @SerializedName("positiveQ2_desc")
    public void setPositiveQ2Desc(String positiveQ2Desc) {
        this.positiveQ2Desc = positiveQ2Desc;
    }

    @SerializedName("socialQ1_desc")
    public String getSocialQ1Desc() {
        return socialQ1Desc;
    }

    @SerializedName("socialQ1_desc")
    public void setSocialQ1Desc(String socialQ1Desc) {
        this.socialQ1Desc = socialQ1Desc;
    }

    @SerializedName("ip_message1")
    public String getIpMessage1() {
        return ipMessage1;
    }

    @SerializedName("ip_message1")
    public void setIpMessage1(String ipMessage1) {
        this.ipMessage1 = ipMessage1;
    }

    @SerializedName("financialQ1_title")
    public Object getFinancialQ1Title() {
        return financialQ1Title;
    }

    @SerializedName("financialQ1_title")
    public void setFinancialQ1Title(Object financialQ1Title) {
        this.financialQ1Title = financialQ1Title;
    }

    @SerializedName("financialQ1_desc")
    public Object getFinancialQ1Desc() {
        return financialQ1Desc;
    }

    @SerializedName("financialQ1_desc")
    public void setFinancialQ1Desc(Object financialQ1Desc) {
        this.financialQ1Desc = financialQ1Desc;
    }

    @SerializedName("health_addictions_table")
    public List<String> getHealthAddictionsTable() {
        return healthAddictionsTable;
    }

    @SerializedName("health_addictions_table")
    public void setHealthAddictionsTable(List<String> healthAddictionsTable) {
        this.healthAddictionsTable = healthAddictionsTable;
    }

    @SerializedName("no_health_addictions_table")
    public List<Object> getNoHealthAddictionsTable() {
        return noHealthAddictionsTable;
    }

    @SerializedName("no_health_addictions_table")
    public void setNoHealthAddictionsTable(List<Object> noHealthAddictionsTable) {
        this.noHealthAddictionsTable = noHealthAddictionsTable;
    }

    @SerializedName("health_addictions_text")
    public String getHealthAddictionsText() {
        return healthAddictionsText;
    }

    @SerializedName("health_addictions_text")
    public void setHealthAddictionsText(String healthAddictionsText) {
        this.healthAddictionsText = healthAddictionsText;
    }

    @SerializedName("no_health_addictions_text")
    public String getNoHealthAddictionsText() {
        return noHealthAddictionsText;
    }

    @SerializedName("no_health_addictions_text")
    public void setNoHealthAddictionsText(String noHealthAddictionsText) {
        this.noHealthAddictionsText = noHealthAddictionsText;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}