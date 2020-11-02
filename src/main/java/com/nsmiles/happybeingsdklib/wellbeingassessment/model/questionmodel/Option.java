package com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class Option implements Serializable
{

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("hint")
    @Expose
    private String hint;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("dropdownOptions")
    @Expose
    private List<String> dropdownOptions = null;
    private final static long serialVersionUID = -6762906768097489737L;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getDropdownOptions() {
        return dropdownOptions;
    }

    public void setDropdownOptions(List<String> dropdownOptions) {
        this.dropdownOptions = dropdownOptions;
    }

}


