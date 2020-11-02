package com.nsmiles.happybeingsdklib.Models.CorporateWellbeing;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sukumar on 6/20/2019.
 */

public class TableDatum {

    @SerializedName("SelectedOption1_headder")
    private String SelectedOption1_headder;
    @SerializedName("SelectedOption2_headder")
    private String SelectedOption2_headder;
    @SerializedName("SelectedOption2_1_headder")
    private String SelectedOption2_1_headder;
    @SerializedName("SelectedOption3_headder")
    private String SelectedOption3_headder;
    @SerializedName("SelectedOption4_headder")
    private String SelectedOption4_headder;
    @SerializedName("SelectedOption4_1_headder")
    private String SelectedOption4_1_headder;
    @SerializedName("SelectedOption5_headder")
    private String SelectedOption5_headder;
    @SerializedName("SelectedOption6_headder")
    private String SelectedOption6_headder;
    @SerializedName("SelectedOption7_headder")
    private String SelectedOption7_headder;
    @SerializedName("SelectedOption8_headder")
    private String SelectedOption8_headder;

    @SerializedName("FamilyQ15_1_title1")
    private String FamilyQ15_1_title1;

    @SerializedName("FamilyQ15_1_Desc1")
    private String FamilyQ15_1_Desc1;
    @SerializedName("FamilyQ15_1_title2")
    private String FamilyQ15_1_title2;
    @SerializedName("FamilyQ15_1_Desc2")
    private String FamilyQ15_1_Desc2;
    @SerializedName("FamilyQ15_1_title3")
    private String FamilyQ15_1_title3;

    @SerializedName("FamilyQ15_1_Desc3")
    private String FamilyQ15_1_Desc3;
    @SerializedName("FamilyQ15_1_title4")
    private String FamilyQ15_1_title4;
    @SerializedName("FamilyQ15_1_Desc4")
    private String FamilyQ15_1_Desc4;
    @SerializedName("FamilyQ15_1_title5")
    private String FamilyQ15_1_title5;
    @SerializedName("FamilyQ15_1_Desc5")
    private String FamilyQ15_1_Desc5;
    @SerializedName("FamilyoptionTitle2")
    private String FamilyoptionTitle2;


    @SerializedName("FamilyQ15_2_title1")
    private String FamilyQ15_2_title1;

    @SerializedName("self_care")
    private String self_care;

    @SerializedName("FamilyQ15_2_Desc1")
    private String FamilyQ15_2_Desc1;
    @SerializedName("FamilyQ15_2_title2")
    private String FamilyQ15_2_title2;
    @SerializedName("FamilyQ15_2_Desc2")
    private String FamilyQ15_2_Desc2;
    @SerializedName("FamilyQ15_2_title3")
    private String FamilyQ15_2_title3;

    @SerializedName("FamilyQ15_2_Desc3")
    private String FamilyQ15_2_Desc3;
    @SerializedName("FamilyQ15_2_title4")
    private String FamilyQ15_2_title4;
    @SerializedName("FamilyQ15_2_Desc4")
    private String FamilyQ15_2_Desc4;
    @SerializedName("FamilyQ15_2_title5")
    private String FamilyQ15_2_title5;
    @SerializedName("FamilyQ15_2_Desc5")
    private String FamilyQ15_2_Desc5;







    @SerializedName("goals_headder")
    private String goals_headder;

    @SerializedName("strengths_headder")
    private String strengths_headder;


    @SerializedName("score")
    private Integer score;
    @SerializedName("range")
    private String range;
    @SerializedName("range_pdf")
    private String rangePdf;
    @SerializedName("ip_title")
    private String ipTitle;
    @SerializedName("title")
    private String title;
    @SerializedName("subcontent")
    private String subcontent;
    @SerializedName("ip_message")
    private String ip_message;
    @SerializedName("range_percentage")
    private Double rangePercentage;
    @SerializedName("range_percentage_max")
    private Double rangePercentageMax;
    @SerializedName("sort_range")
    private Integer sortRange;

    @SerializedName("PhysicalHealthOption71")
    private String physicalHealthOption71;

    @SerializedName("emotional_triggers")
    private String emotional_triggers;


    @SerializedName("recomendation_tips")
    private String recomendation_tips;

    @SerializedName("strengths")
    private List<String> strengths = null;
    @SerializedName("SelectedOption1")
    private List<String> SelectedOption1 = null;

    @SerializedName("SelectedOption2")
    private List<String> SelectedOption2 = null;

    @SerializedName("SelectedOption3")
    private List<String> SelectedOption3 = null;

    @SerializedName("SelectedOption4")
    private List<String> SelectedOption4 = null;

    @SerializedName("SelectedOption4_1")
    private List<String> SelectedOption4_1 = null;

    @SerializedName("SelectedOption5")
    private List<String> SelectedOption5 = null;

    @SerializedName("SelectedOption6")
    private List<String> SelectedOption6 = null;

    @SerializedName("SelectedOption7")
    private List<String> SelectedOption7 = null;

    @SerializedName("SelectedOption8")
    private List<String> SelectedOption8 = null;

    @SerializedName("SelectedOption2_1")
    private List<String> SelectedOption2_1 = null;

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
    @SerializedName("description")
    private String description;
    @SerializedName("Belief_workon")
    private String Belief_workon;

    @SerializedName("FamilyoptionTitle1")
    private String FamilyoptionTitle1;
    @SerializedName("FamilyoptionDes1")
    private String FamilyoptionDes1;


    public String getSelf_care() {
        return self_care;
    }

    public void setSelf_care(String self_care) {
        this.self_care = self_care;
    }

    @SerializedName("FamilyoptionTitle1")
    public String getFamilyoptionTitle1() {
        return FamilyoptionTitle1;
    }
    @SerializedName("FamilyoptionTitle1")
    public void setFamilyoptionTitle1(String familyoptionTitle1) {
        FamilyoptionTitle1 = familyoptionTitle1;
    }


    public String getEmotional_triggers() {
        return emotional_triggers;
    }

    public void setEmotional_triggers(String emotional_triggers) {
        this.emotional_triggers = emotional_triggers;
    }

    public String getFamilyoptionTitle2() {
        return FamilyoptionTitle2;
    }

    public void setFamilyoptionTitle2(String familyoptionTitle2) {
        FamilyoptionTitle2 = familyoptionTitle2;
    }

    @SerializedName("FamilyoptionDes1")
    public String getFamilyoptionDes1() {
        return FamilyoptionDes1;
    }
    @SerializedName("FamilyoptionDes1")
    public void setFamilyoptionDes1(String familyoptionDes1) {
        FamilyoptionDes1 = familyoptionDes1;
    }

    @SerializedName("SelectedOption1")
    public List<String> getSelectedOption1() {
        return SelectedOption1;
    }
    @SerializedName("SelectedOption1")
    public void setSelectedOption1(List<String> selectedOption1) {
        SelectedOption1 = selectedOption1;
    }


    public String getSelectedOption1_headder() {
        return SelectedOption1_headder;
    }

    public void setSelectedOption1_headder(String selectedOption1_headder) {
        SelectedOption1_headder = selectedOption1_headder;
    }

    public String getSelectedOption2_headder() {
        return SelectedOption2_headder;
    }

    public void setSelectedOption2_headder(String selectedOption2_headder) {
        SelectedOption2_headder = selectedOption2_headder;
    }

    public String getSelectedOption2_1_headder() {
        return SelectedOption2_1_headder;
    }

    public void setSelectedOption2_1_headder(String selectedOption2_1_headder) {
        SelectedOption2_1_headder = selectedOption2_1_headder;
    }

    public String getSelectedOption3_headder() {
        return SelectedOption3_headder;
    }

    public void setSelectedOption3_headder(String selectedOption3_headder) {
        SelectedOption3_headder = selectedOption3_headder;
    }

    public String getSelectedOption4_headder() {
        return SelectedOption4_headder;
    }

    public void setSelectedOption4_headder(String selectedOption4_headder) {
        SelectedOption4_headder = selectedOption4_headder;
    }

    public String getSelectedOption4_1_headder() {
        return SelectedOption4_1_headder;
    }

    public void setSelectedOption4_1_headder(String selectedOption4_1_headder) {
        SelectedOption4_1_headder = selectedOption4_1_headder;
    }

    public String getSelectedOption5_headder() {
        return SelectedOption5_headder;
    }

    public void setSelectedOption5_headder(String selectedOption5_headder) {
        SelectedOption5_headder = selectedOption5_headder;
    }

    public String getSelectedOption6_headder() {
        return SelectedOption6_headder;
    }

    public void setSelectedOption6_headder(String selectedOption6_headder) {
        SelectedOption6_headder = selectedOption6_headder;
    }

    public String getSelectedOption7_headder() {
        return SelectedOption7_headder;
    }

    public void setSelectedOption7_headder(String selectedOption7_headder) {
        SelectedOption7_headder = selectedOption7_headder;
    }

    public String getSelectedOption8_headder() {
        return SelectedOption8_headder;
    }

    public void setSelectedOption8_headder(String selectedOption8_headder) {
        SelectedOption8_headder = selectedOption8_headder;
    }


    public String getGoals_headder() {
        return goals_headder;
    }

    public void setGoals_headder(String goals_headder) {
        this.goals_headder = goals_headder;
    }

    public String getStrengths_headder() {
        return strengths_headder;
    }

    public void setStrengths_headder(String strengths_headder) {
        this.strengths_headder = strengths_headder;
    }

    @SerializedName("SelectedOption2_1")
    public List<String> getSelectedOption2_1() {
        return SelectedOption2_1;
    }

    @SerializedName("SelectedOption2_1")
    public void setSelectedOption2_1(List<String> selectedOption2_1) {
        SelectedOption2_1 = selectedOption2_1;
    }

    @SerializedName("recomendation_tips")
    public String getRecomendation_tips() {
        return recomendation_tips;
    }
    @SerializedName("recomendation_tips")
    public void setRecomendation_tips(String recomendation_tips) {
        this.recomendation_tips = recomendation_tips;
    }

    @SerializedName("belief_workon")
    public String getBelief_workon() {
        return Belief_workon;
    }
    @SerializedName("belief_workon")
    public void setBelief_workon(String belief_workon) {
        Belief_workon = belief_workon;
    }


    public List<String> getSelectedOption2() {
        return SelectedOption2;
    }

    public void setSelectedOption2(List<String> selectedOption2) {
        SelectedOption2 = selectedOption2;
    }


    public String getPhysicalHealthOption71() {
        return physicalHealthOption71;
    }

    public void setPhysicalHealthOption71(String physicalHealthOption71) {
        this.physicalHealthOption71 = physicalHealthOption71;
    }

    public List<String> getSelectedOption3() {
        return SelectedOption3;
    }

    public void setSelectedOption3(List<String> selectedOption3) {
        SelectedOption3 = selectedOption3;
    }

    public List<String> getSelectedOption4() {
        return SelectedOption4;
    }

    public void setSelectedOption4(List<String> selectedOption4) {
        SelectedOption4 = selectedOption4;
    }

    public List<String> getSelectedOption4_1() {
        return SelectedOption4_1;
    }

    public void setSelectedOption4_1(List<String> selectedOption4_1) {
        SelectedOption4_1 = selectedOption4_1;
    }

    public List<String> getSelectedOption5() {
        return SelectedOption5;
    }

    public void setSelectedOption5(List<String> selectedOption5) {
        SelectedOption5 = selectedOption5;
    }

    public List<String> getSelectedOption6() {
        return SelectedOption6;
    }

    public void setSelectedOption6(List<String> selectedOption6) {
        SelectedOption6 = selectedOption6;
    }

    public List<String> getSelectedOption7() {
        return SelectedOption7;
    }

    public void setSelectedOption7(List<String> selectedOption7) {
        SelectedOption7 = selectedOption7;
    }

    public List<String> getSelectedOption8() {
        return SelectedOption8;
    }

    public void setSelectedOption8(List<String> selectedOption8) {
        SelectedOption8 = selectedOption8;
    }

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

    @SerializedName("title")
    public String getTitle() {
        return title;
    }
    @SerializedName("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @SerializedName("")
    public String getSubcontent() {
        return subcontent;
    }

    @SerializedName("subcontent")
    public void setSubcontent(String subcontent) {
        this.subcontent = subcontent;
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

    @SerializedName("description")
    public String getDescription() {
        return description;
    }

    @SerializedName("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @SerializedName("ip_message")
    public String getIp_message() {
        return ip_message;
    }
    @SerializedName("ip_message")
    public void setIp_message(String ip_message) {
        this.ip_message = ip_message;
    }


    public String getFamilyQ15_1_title1() {
        return FamilyQ15_1_title1;
    }

    public void setFamilyQ15_1_title1(String familyQ15_1_title1) {
        FamilyQ15_1_title1 = familyQ15_1_title1;
    }

    public String getFamilyQ15_1_Desc1() {
        return FamilyQ15_1_Desc1;
    }

    public void setFamilyQ15_1_Desc1(String familyQ15_1_Desc1) {
        FamilyQ15_1_Desc1 = familyQ15_1_Desc1;
    }

    public String getFamilyQ15_1_title2() {
        return FamilyQ15_1_title2;
    }

    public void setFamilyQ15_1_title2(String familyQ15_1_title2) {
        FamilyQ15_1_title2 = familyQ15_1_title2;
    }

    public String getFamilyQ15_1_Desc2() {
        return FamilyQ15_1_Desc2;
    }

    public void setFamilyQ15_1_Desc2(String familyQ15_1_Desc2) {
        FamilyQ15_1_Desc2 = familyQ15_1_Desc2;
    }

    public String getFamilyQ15_1_title3() {
        return FamilyQ15_1_title3;
    }

    public void setFamilyQ15_1_title3(String familyQ15_1_title3) {
        FamilyQ15_1_title3 = familyQ15_1_title3;
    }

    public String getFamilyQ15_1_Desc3() {
        return FamilyQ15_1_Desc3;
    }

    public void setFamilyQ15_1_Desc3(String familyQ15_1_Desc3) {
        FamilyQ15_1_Desc3 = familyQ15_1_Desc3;
    }

    public String getFamilyQ15_1_title4() {
        return FamilyQ15_1_title4;
    }

    public void setFamilyQ15_1_title4(String familyQ15_1_title4) {
        FamilyQ15_1_title4 = familyQ15_1_title4;
    }

    public String getFamilyQ15_1_Desc4() {
        return FamilyQ15_1_Desc4;
    }

    public void setFamilyQ15_1_Desc4(String familyQ15_1_Desc4) {
        FamilyQ15_1_Desc4 = familyQ15_1_Desc4;
    }

    public String getFamilyQ15_1_title5() {
        return FamilyQ15_1_title5;
    }

    public void setFamilyQ15_1_title5(String familyQ15_1_title5) {
        FamilyQ15_1_title5 = familyQ15_1_title5;
    }

    public String getFamilyQ15_1_Desc5() {
        return FamilyQ15_1_Desc5;
    }

    public void setFamilyQ15_1_Desc5(String familyQ15_1_Desc5) {
        FamilyQ15_1_Desc5 = familyQ15_1_Desc5;
    }

    public String getFamilyQ15_2_title1() {
        return FamilyQ15_2_title1;
    }

    public void setFamilyQ15_2_title1(String familyQ15_2_title1) {
        FamilyQ15_2_title1 = familyQ15_2_title1;
    }

    public String getFamilyQ15_2_Desc1() {
        return FamilyQ15_2_Desc1;
    }

    public void setFamilyQ15_2_Desc1(String familyQ15_2_Desc1) {
        FamilyQ15_2_Desc1 = familyQ15_2_Desc1;
    }

    public String getFamilyQ15_2_title2() {
        return FamilyQ15_2_title2;
    }

    public void setFamilyQ15_2_title2(String familyQ15_2_title2) {
        FamilyQ15_2_title2 = familyQ15_2_title2;
    }

    public String getFamilyQ15_2_Desc2() {
        return FamilyQ15_2_Desc2;
    }

    public void setFamilyQ15_2_Desc2(String familyQ15_2_Desc2) {
        FamilyQ15_2_Desc2 = familyQ15_2_Desc2;
    }

    public String getFamilyQ15_2_title3() {
        return FamilyQ15_2_title3;
    }

    public void setFamilyQ15_2_title3(String familyQ15_2_title3) {
        FamilyQ15_2_title3 = familyQ15_2_title3;
    }

    public String getFamilyQ15_2_Desc3() {
        return FamilyQ15_2_Desc3;
    }

    public void setFamilyQ15_2_Desc3(String familyQ15_2_Desc3) {
        FamilyQ15_2_Desc3 = familyQ15_2_Desc3;
    }

    public String getFamilyQ15_2_title4() {
        return FamilyQ15_2_title4;
    }

    public void setFamilyQ15_2_title4(String familyQ15_2_title4) {
        FamilyQ15_2_title4 = familyQ15_2_title4;
    }

    public String getFamilyQ15_2_Desc4() {
        return FamilyQ15_2_Desc4;
    }

    public void setFamilyQ15_2_Desc4(String familyQ15_2_Desc4) {
        FamilyQ15_2_Desc4 = familyQ15_2_Desc4;
    }

    public String getFamilyQ15_2_title5() {
        return FamilyQ15_2_title5;
    }

    public void setFamilyQ15_2_title5(String familyQ15_2_title5) {
        FamilyQ15_2_title5 = familyQ15_2_title5;
    }

    public String getFamilyQ15_2_Desc5() {
        return FamilyQ15_2_Desc5;
    }

    public void setFamilyQ15_2_Desc5(String familyQ15_2_Desc5) {
        FamilyQ15_2_Desc5 = familyQ15_2_Desc5;
    }
}
