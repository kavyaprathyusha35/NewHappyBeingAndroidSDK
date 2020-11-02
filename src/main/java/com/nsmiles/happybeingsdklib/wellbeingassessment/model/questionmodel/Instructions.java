package com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Instructions implements Serializable
{
@SerializedName("title")
@Expose
private String title;
@SerializedName("paras")
@Expose
private List<String> paras = null;
private final static long serialVersionUID = -2713372562068072474L;

public String getTitle() {
        return title;
        }

public void setTitle(String title) {
        this.title = title;
        }

public List<String> getParas() {
        return paras;
        }

public void setParas(List<String> paras) {
        this.paras = paras;
        }

        }