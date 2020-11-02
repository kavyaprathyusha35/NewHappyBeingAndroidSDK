package com.nsmiles.happybeingsdklib.wellbeingassessment.model.answermodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Answer implements Serializable
{

    @SerializedName("qNo")
    @Expose
    private Integer qNo;
    @SerializedName("answer")
    @Expose
    private List<Answer_> answer = null;
    private final static long serialVersionUID = 5683288683924634990L;

    public Integer getQNo() {
        return qNo;
    }

    public void setQNo(Integer qNo) {
        this.qNo = qNo;
    }

    public List<Answer_> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Answer_> answer) {
        this.answer = answer;
    }

}