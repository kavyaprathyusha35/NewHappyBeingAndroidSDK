package com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.pregnancywellbeingcategorymodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sukumar on 6/23/2018.
 */

public class Success implements Serializable {

    @SerializedName("table_data")
    @Expose
    private List<List<TableDatum>> tableData = null;
    @SerializedName("report_title")
    @Expose
    private String reportTitle;
    @SerializedName("report_content")
    @Expose
    private String reportContent;
    @SerializedName("report_ip_title")
    @Expose
    private String reportIpTitle;
    @SerializedName("report_ip_content")
    @Expose
    private String reportIpContent;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("version_sub")
    @Expose
    private Object versionSub;
    public List<List<TableDatum>> getTableData() {
        return tableData;
    }

    public void setTableData(List<List<TableDatum>> tableData) {
        this.tableData = tableData;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public String getReportIpTitle() {
        return reportIpTitle;
    }

    public void setReportIpTitle(String reportIpTitle) {
        this.reportIpTitle = reportIpTitle;
    }

    public String getReportIpContent() {
        return reportIpContent;
    }

    public void setReportIpContent(String reportIpContent) {
        this.reportIpContent = reportIpContent;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Object getVersionSub() {
        return versionSub;
    }

    public void setVersionSub(Object versionSub) {
        this.versionSub = versionSub;
    }


}