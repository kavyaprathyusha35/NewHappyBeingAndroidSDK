package com.nsmiles.happybeingsdklib.Reports.DetailReport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.nsmiles.happybeingsdklib.Models.CorporateWellbeing.CorporateWellbeingReportModel;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Reports.RadarMarkerView;
import com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.PregnancyWellBeingPresenter;
import com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.PregnancyWellBeingView;
import com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.WellbeingReportAdapter;
import com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.pregnancywellbeingcategorymodel.TableDatum;
import com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.pregnancywellbeingmodel.GeneralWellBeingModel;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
import com.nsmiles.happybeingsdklib.mycoachfragment.presenter.CommonPresenter;
import com.nsmiles.happybeingsdklib.network.NetworkError;
import com.nsmiles.happybeingsdklib.network.NetworkService;
import com.nsmiles.happybeingsdklib.network.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Sukumar on 6/1/2018.
 */

public class DetailReportImplementation implements PregnancyWellBeingPresenter, CommonPresenter.ReportInterface, ExpandableListView.OnGroupExpandListener {


    private Activity activity;
    private Context context;
    private PregnancyWellBeingView view;
    private DataManager dataManager;
    private Service service;
    private RadarChart radarChart;
    private ArrayList<RadarEntry> entries1 = new ArrayList<>();
    private ArrayList<RadarEntry> entries2 = new ArrayList<>();
    private String[] mActivities;
    private RadarDataSet set1, set2;
    private MarkerView mv;
    private CompositeSubscription compositeSubscription;
    private String tag = "combined_report", PAYMENT_CATEGORY = "";
    private WellbeingReportAdapter generalWellBeingAdapter;
    private DetailReportAdapter corporateWellbeingReportAdapter;
    //private RecyclerView recyclerView;
    private ExpandableListView expandableListView;
    private ScrollView scrollView;
    List<com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.pregnancywellbeingmodel.TableDatum> tableDatumList;
    int availableCount = 0, actCount = 0;
    private Intent myIntent;
    private String area;
    private List<String> category = new ArrayList<>();
    String MYURL;
    private String report_name;
    private boolean hideRadar;
    private TextView happiness_index_text;

    List<TableDatum> tableDatumCategoryList;
    List<com.nsmiles.happybeingsdklib.Models.CorporateWellbeing.TableDatum> categoryTableData;

    public DetailReportImplementation(Activity activity,
                                      DataManager dataManager,
                                      Service service,
                                      PregnancyWellBeingView view,
                                      RadarChart radarChart,
                                      ExpandableListView expandableListView,
                                      ScrollView scrollView,
                                      Intent myIntent) {

        this.activity = activity;
        this.dataManager = dataManager;
        this.service = service;
        this.context = dataManager.getContext();
        this.radarChart = radarChart;
        this.view = view;
        this.expandableListView = expandableListView;
        this.scrollView = scrollView;
        expandableListView.setOnGroupExpandListener(this);
        this.myIntent = myIntent;
        compositeSubscription = new CompositeSubscription();

        if (myIntent != null && myIntent.hasExtra(AppConstants.ASSESSMENT) && myIntent.hasExtra(AppConstants.REPORT_SUB_TYPE)) {

            area = myIntent.getStringExtra(AppConstants.ASSESSMENT);
            report_name = myIntent.getStringExtra(AppConstants.REPORT_SUB_TYPE);

            if (myIntent.hasExtra(area)) {

                category = myIntent.getStringArrayListExtra(area);
            }
        } else if (myIntent != null && myIntent.hasExtra(AppConstants.SHOW_ALL_PREGNANCY_REPORT) && myIntent.hasExtra(AppConstants.REPORT_SUB_TYPE)) {
            category = myIntent.getStringArrayListExtra(AppConstants.SHOW_ALL_PREGNANCY_REPORT);
            report_name = myIntent.getStringExtra(AppConstants.REPORT_SUB_TYPE);

            if (myIntent.hasExtra(AppConstants.HIDE_RADAR)) {
                hideRadar = true;
            } else {
                view.showRadarChart();
            }
        } else {
            dataManager.toast(activity, "");
            activity.finish();
        }

        PAYMENT_CATEGORY = AppConstants.CORPORATE_WELLBEING;
    }

    @Override
    public void initilizeRadarChartComponent() {
        radarChart.setBackgroundColor(Color.WHITE);
        Description description = new Description();
        /*description.setText("1. Physical \n2.Career \n3.Mental \n4.Social \n5.Financial \n" +
                "6.Spiritual \n7.Environmental \n8.Family ");*/
        description.setText("Click On the Graph to Get Corresponding Values");
        description.setTextColor(activity.getResources().getColor(R.color.red));
        radarChart.setDescription(description);
        radarChart.getDescription().setEnabled(true);
        radarChart.setWebLineWidth(1f);
        radarChart.setWebColor(activity.getResources().getColor(R.color.min_3));
        radarChart.setWebLineWidthInner(1f);
        radarChart.setWebColorInner(activity.getResources().getColor(R.color.min_3));
        radarChart.setWebAlpha(100);

        mv = new RadarMarkerView(activity, R.layout.radar_markerview);
        mv.setChartView(radarChart); // For bounds control
        radarChart.setMarker(mv); // Set the marker to the chart

    }


    @Override
    public void wellBeingRadarChart() {
        XAxis xAxis = radarChart.getXAxis();
        xAxis.setTextSize(5f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);

        final String[] mActivitiesNew = new String[]{"Physical", "Career", "Mental", "Social", "Financial","Spiritual","Environmental","Family"};
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivitiesNew[(int) value % mActivitiesNew.length];
            }
        });
        xAxis.setDrawLabels(true);
        xAxis.setTextColor(activity.getResources().getColor(R.color.welcome555));
        xAxis.setGranularityEnabled(true);

        Log.d("mActivities:", String.valueOf(mActivities.length));
        YAxis yAxis = radarChart.getYAxis();
        yAxis.setLabelCount(mActivities.length-2, true);
        yAxis.setTextSize(7f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(100f);
        yAxis.setDrawLabels(true);

        Legend l = radarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(activity.getResources().getColor(R.color.app_blue));
        l.setTextSize(0f);
        l.setEnabled(true);
        l.setWordWrapEnabled(true);


        if (entries1 != null && entries1.size() > 0) {
            set1 = new RadarDataSet(entries1, "Your current score");   //High score of well-being   Your current score
            set1.setColor(activity.getResources().getColor(R.color.app_second_color));
            //set1.setFillColor(activity.getResources().getColor(R.color.app_second_color));
            //set1.setDrawFilled(true);
            //set1.setFillAlpha(180);
            set1.setLineWidth(4f);
            set1.setDrawHighlightCircleEnabled(true);
            set1.setDrawHighlightIndicators(false);
            set1.setValueTextColor(Color.BLACK);
            set1.setValueTextSize(18f);
        }


        if (entries2 != null && entries2.size() > 0) {
            set2 = new RadarDataSet(entries2, "Minimum desired score");
            set2.setColor(activity.getResources().getColor(R.color.primary_txt));
            //set2.setFillColor(Color.rgb(255, 119, 119));
            //set2.setDrawFilled(true);
            //set2.setFillAlpha(180);
            set2.setLineWidth(2f);
            set2.setDrawHighlightCircleEnabled(true);
            set2.setDrawHighlightIndicators(false);
        }


        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();

        if (set2 != null) {
            sets.add(set2);
        }

        if (set1 != null) {
            sets.add(set1);
        }

        RadarData data = new RadarData(sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(activity.getResources().getColor(R.color.curated));
        radarChart.setData(data);
        radarChart.invalidate();

        radarChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);



    }

//    @Override
//    public void getGeneralWellBeingReport() {
//
//
//        Subscription subscription = service.getGeneralWellBeingReport(AppConstants.BEARER + dataManager.get(AppConstants.HB_USER_TOKEN, ""),
//                dataManager.get(AppConstants.USER_ID, ""), new Service.GeneralWellBeingReportCallBack() {
//                    @Override
//                    public void onSuccess(GeneralWellBeingModel generalWellBeingModel) {
//
//                        //      dataManager.toast(activity, generalWellBeingModel.getSuccess().getData().getUserAssessmentData().getWellbeing().getReportContent());
//
//                        Log.i("PregnencyWell", "*** data is ********"+generalWellBeingModel.getSuccess().getData().getUserAssessmentData());
//                        tableDatumList = generalWellBeingModel.getSuccess().getData().getUserAssessmentData().getWellbeing().getTableData().get(0);
//
//
//
//                        if(!hideRadar) {
//                            tableDatumList.remove(tableDatumList.size()-1);
//                            if (mActivities != null && mActivities.length > 0) {
//                                mActivities = null;
//                            }
//                            if (entries1 != null && entries2 != null && entries1.size() > 0 && entries2.size() > 0) {
//                                entries1 = new ArrayList<>();
//                                entries2 = new ArrayList<>();
//                            }
//
//                            for (int g = 0; g < tableDatumList.size(); g++) {
//
//                                if (tableDatumList.get(g).getRangePercentage() != null
//                                        && tableDatumList.get(g).getRangePercentage() != 0
//                                        && tableDatumList.get(g).getScore() != null
//                                        ) {
//
//                                    availableCount++;
//                                }
//                            }
//
//                            mActivities = new String[tableDatumList.size()];
//                            mActivities = new String[availableCount];
//
//
//                        for (int i = 0; i < tableDatumList.size(); i++) {
//
//                            //    mActivities[i] = tableDatumList.get(i).getDescription();
//
//                            if (tableDatumList.get(i).getRangePercentage() != null && tableDatumList.get(i).getRangePercentage() != 0
//                                    && tableDatumList.get(i).getScore() != null && tableDatumList.get(i).getScore() != 0) {
//                                mActivities[actCount] = tableDatumList.get(i).getIpTitle();
//                                actCount++;
//                                double val1 = tableDatumList.get(i).getRangePercentage();
//                                entries1.add(new RadarEntry(dataManager.roundNumber(val1, 1)));
//
//                                double val2 = tableDatumList.get(i).getRangePercentageMax();
//                                entries2.add(new RadarEntry(dataManager.roundNumber(val2, 1)));
//
//                                //          dataManager.infoLog(tag, tableDatumList.get(i).getDescription());
//                            }
//                        }
//                        wellBeingRadarChart();
//                        }
//
//                        generalWellBeingAdapter = new WellbeingReportAdapter(activity, tableDatumList, DetailReportImplementation.this);
//                        expandableListView.setAdapter(generalWellBeingAdapter);
//                        if (hideRadar)
//                            expandableListView.expandGroup(0);
//                        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//                            @Override
//                            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
//                                Log.i("PregnencyWellBeing", "***** Group position is ***** "+i);
//                                setListViewHeight(expandableListView, i);
//                                return false;
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onError(NetworkError networkError) {
//
//                        //dataManager.toast(activity, networkError.getMessage());
//                    }
//
//                    @Override
//                    public void onSuccessError(String errorMessage) {
//
////                        dataManager.toast(activity, errorMessage);
//                    }
//                });
//
//        compositeSubscription.add(subscription);
//    }


    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }
//    @Override
//    public void getGeneralWellBeingCategoryReport() {
//
///*
//
//        Set<String> categoryGroup;
//
//        */
///*Save Pregnancy Category Group Name*//*
//
//        if (area != null) {
//            categoryGroup = dataManager.get(AppConstants.GENERAL_CATEGORY_GROUP);
//            if (categoryGroup == null) {
//                categoryGroup = new HashSet<>();
//            }
//            categoryGroup.add(area);
//            dataManager.save(AppConstants.GENERAL_CATEGORY_GROUP, categoryGroup);
//        */
///*Save Pregnancy Category Group Name*//*
//
//
//        */
///*Save Pregnancy Category Group List Name*//*
//
//            categoryGroup = new HashSet<>();
//            categoryGroup.addAll(category);
//            dataManager.save(area, categoryGroup);
//        */
///*Save Pregnancy Category Group List Name*//*
//
//        }
//
//        StringBuffer url = new StringBuffer(NetworkService.getGeneralWellBeingCategory);
//        url.append(report_name).append("?").append("reportname").append("=").append(report_name).append("&").append("reportstable").append("=").append("[");
//        Log.i("ReportImplementation", "Url is "+url+"report_name is "+report_name);
//        for (int i = 0; i < category.size(); i++) {
//
//            url.append('"');
//            url.append(category.get(i)).append('"');
//            if (i != category.size() - 1) {
//                url.append(",");
//            }
//
//        }
//        url.append("]");
//
//
//        try {
//            MYURL = java.net.URLDecoder.decode(String.valueOf(url), "UTF-8");
//
//            Log.d("eee", MYURL);
//
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        Log.i("WellbingCategory", "############Url is "+MYURL);
//        Log.i("WellbingCategory", "Category is "+category);
//
//        Subscription subscription = service.getGeneralWellBeingCategoryReport(AppConstants.BEARER + dataManager.get(AppConstants.HB_USER_TOKEN, ""),
//                MYURL,
//                report_name,
//                category,
//                new Service.GeneralWellBeingCategoryReportCallBack() {
//                    @Override
//                    public void onSuccess(GeneralWellBeingCategoryModel generalWellBeingModel) {
//
//                        //  dataManager.toast(activity, generalWellBeingModel.getSuccess().getTableData().get(0).get(0).getIpTitle());
//                        tableDatumCategoryList = generalWellBeingModel.getSuccess().getTableData().get(0);
//                        Log.i("WellbeingReport","in on success"+tableDatumCategoryList.size());
//
//                        if(!hideRadar)
//                        {
//                            tableDatumCategoryList.remove(tableDatumCategoryList.size()-1);
//                        if (mActivities != null && mActivities.length > 0) {
//                            mActivities = null;
//                        }
//                        if (entries1 != null && entries2 != null && entries1.size() > 0 && entries2.size() > 0) {
//                            entries1 = new ArrayList<>();
//                            entries2 = new ArrayList<>();
//                        }
//
//                        for (int g = 0; g < tableDatumCategoryList.size(); g++) {
//
//                            if (tableDatumCategoryList.get(g).getRangePercentage() != 0
//                                    && tableDatumCategoryList.get(g).getRangePercentage() != 0
//                                    && tableDatumCategoryList.get(g).getScore() != null
//                                    ) {
//
//                                availableCount++;
//                            }
//                        }
//
//                        mActivities = new String[tableDatumCategoryList.size()];
//                        mActivities = new String[availableCount];
//
//
//                        for (int i = 0; i < tableDatumCategoryList.size(); i++) {
//
//                            //    mActivities[i] = tableDatumList.get(i).getDescription();
//
//                            if (tableDatumCategoryList.get(i).getRangePercentage() != 0 && tableDatumCategoryList.get(i).getRangePercentage() != 0
//                                    && tableDatumCategoryList.get(i).getScore() != null && tableDatumCategoryList.get(i).getScore() != 0) {
//                                mActivities[actCount] = tableDatumCategoryList.get(i).getIpTitle();
//                                mActivities[actCount] = String.valueOf(i+1);
//                                actCount++;
//                                double val1 = tableDatumCategoryList.get(i).getRangePercentage();
//                                entries1.add(new RadarEntry(dataManager.roundNumber(val1, 1)));
//
//                                double val2 = tableDatumCategoryList.get(i).getRangePercentageMax();
//                                entries2.add(new RadarEntry(dataManager.roundNumber(val2, 1)));
//                            }
//                        }
//                        wellBeingRadarChart();
//
//                    }
//
//                        generalWellBeingCategoryAdapter = new WellbeingCategoryReportAdapter(activity, tableDatumCategoryList, DetailReportImplementation.this);
//                        expandableListView.setAdapter(generalWellBeingCategoryAdapter);
//                        setListViewHeight(expandableListView, 0);
//                        if (hideRadar)
//                            expandableListView.expandGroup(0);
//
//
//                    }
//
//                    @Override
//                    public void onError(NetworkError networkError) {
//                        Log.i("WellbeingReport","IN network error "+networkError);
//
//                        dataManager.toast(activity, networkError.getMessage());
//                    }
//
//                    @Override
//                    public void onSuccessError(String errorMessage) {
//                        Log.i("WellbeingReport", "In success error "+errorMessage);
//                        dataManager.toast(activity, errorMessage);
//                    }
//                });
//
//        compositeSubscription.add(subscription);
//*/
//    }
    @Override
    public void getCorporateWellbeingReport() {
//TODO : Call the different api for the corpporate wellbeing/
        Set<String> categoryGroup;

        if (area != null) {
            categoryGroup = dataManager.get(AppConstants.GENERAL_CATEGORY_GROUP);
            if (categoryGroup == null) {
                categoryGroup = new HashSet<>();
            }
            categoryGroup.add(area);
            dataManager.save(AppConstants.GENERAL_CATEGORY_GROUP, categoryGroup);
            categoryGroup = new HashSet<>();
            categoryGroup.addAll(category);
            dataManager.save(area, categoryGroup);
        }

        StringBuffer url = new StringBuffer(NetworkService.getCorporateWellbeingUrl);

        url.append("?").append("reportname").append("=").append(report_name).append("&").append("reportstable").append("=").append("[");

        for (int i = 0; i < category.size(); i++) {

            url.append('"');
            url.append(category.get(i)).append('"');
            if (i != category.size() - 1) {
                url.append(",");
            }

        }
        url.append("]");


        try {
            MYURL = java.net.URLDecoder.decode(String.valueOf(url), "UTF-8");

            Log.d("eee", MYURL);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("WellbingCategory", "############Url is "+MYURL);
        Log.i("WellbingCategory", "Category is "+category);
        Log.i("WellbingCategory","Report name is "+report_name);

        Subscription subscription = service.getCorporateWellBeingCategoryReport(AppConstants.BEARER + dataManager.get(AppConstants.HB_USER_TOKEN, ""),
                MYURL,
                report_name,
                category,
                new Service.CorporateWellbeingReportCallBack() {
                    @Override
                    public void onSuccess(CorporateWellbeingReportModel corporateWellBeingModel) {
                        Log.i("WellbingCategory","in on succes1 range is "+corporateWellBeingModel.getResult().getSuccess().getCorporatewellbeingv4Percentage());
                        Double happinessIndex = corporateWellBeingModel.getResult().getSuccess().getCorporatewellbeingv4Percentage();
                        List<String> actionsList = corporateWellBeingModel.getResult().getSuccess().getOverallGoals();
                        view.setHappinessIndexText(happinessIndex);
                        view.setActionsText(actionsList);
                        //  dataManager.toast(activity, generalWellBeingModel.getSuccess().getTableData().get(0).get(0).getIpTitle());
                        categoryTableData = corporateWellBeingModel.getResult().getSuccess().getTableData().get(0);

                        if(!hideRadar)
                        {
                            Log.i("WellbingCategory", "In not hide radar");
                            categoryTableData.remove(categoryTableData.size()-1);
                        if (mActivities != null && mActivities.length > 0) {
                            mActivities = null;
                        }
                        if (entries1 != null && entries2 != null && entries1.size() > 0 && entries2.size() > 0) {
                            entries1 = new ArrayList<>();
                            entries2 = new ArrayList<>();
                        }

                        for (int g = 0; g < categoryTableData.size(); g++) {

                            if (categoryTableData.get(g).getRangePercentage() != 0
                                    && categoryTableData.get(g).getRangePercentage() != 0
                                    && categoryTableData.get(g).getScore() != null
                                    ) {

                                availableCount++;
                            }
                        }

                        mActivities = new String[categoryTableData.size()];
                        mActivities = new String[availableCount];


                        for (int i = 0; i < categoryTableData.size(); i++) {

                            //    mActivities[i] = tableDatumList.get(i).getDescription();

                            if (categoryTableData.get(i).getRangePercentage() != 0 && categoryTableData.get(i).getRangePercentage() != 0
                                    && categoryTableData.get(i).getScore() != null && categoryTableData.get(i).getScore() != 0) {
                                mActivities[actCount] = categoryTableData.get(i).getIpTitle();
                                mActivities[actCount] = String.valueOf(i+1);
                                actCount++;
                                double val1 = categoryTableData.get(i).getRangePercentage();
                                entries1.add(new RadarEntry(dataManager.roundNumber(val1, 1)));

                                double val2 = categoryTableData.get(i).getRangePercentageMax();
                                entries2.add(new RadarEntry(dataManager.roundNumber(val2, 1)));
                            }
                        }
                        wellBeingRadarChart();

                    }
                        Log.i("WellbingCategory", "Setting adapter");
                        corporateWellbeingReportAdapter = new DetailReportAdapter(activity, categoryTableData, DetailReportImplementation.this);
                        expandableListView.setAdapter(corporateWellbeingReportAdapter);
                        setListViewHeight(expandableListView, 0);
                        if (hideRadar)
                            expandableListView.expandGroup(0);


                    }

                    @Override
                    public void onError(NetworkError networkError) {
                        Log.i("WellbingCategory","in on error"+networkError.getMessage());

                        dataManager.toast(activity, networkError.getMessage());
                    }

                    @Override
                    public void onSuccessError(String errorMessage) {

                        dataManager.toast(activity, errorMessage);
                    }
                });

        compositeSubscription.add(subscription);
    }

    @Override
    public void scoreInfomation(boolean lock, int position) {
        Log.i("WellBeingChanges", "Score information method ***********");

    }

    @Override
    public void onGroupExpand(final int i) {
        //TODO: Check for Payment
        Log.i("WellBeingChanges", "In on group expand listener");
/*
        if (dataManager.isNetworkAvailable(activity)) {
            checkAssessmentPaymentStatus(new WellBeingCategoryImplementation.WellBeingCallBack() {
                @Override
                public void onPaymentCompleted() {
                    Log.i("WellBeingChanges", "In on payment completed");
                }

                @Override
                public void onPaymentNotCompleted() {
                    activity.startActivityForResult(new Intent(activity, SubscriptionActivity.class)
                                    .putExtra(AppConstants.SKU_ID, AppConstants.ITEM_SKU_OVERCOME_EXAM_PRESSURE)
                                    .putExtra(AppConstants.SERVER_SKU_ID, AppConstants.ITEM_SKU_OVERCOME_EXAM_PRESSURE)
                            , AppConstants.IN_APP_PAYMENT);

                    expandableListView.collapseGroup(i);
                }
            });
        }
        else {
            expandableListView.collapseGroup(i);
            view.internetRequired();
        }
*/
    }

/*
    public void checkAssessmentPaymentStatus(final WellBeingCategoryImplementation.WellBeingCallBack callBack) {


        if (dataManager.isNetworkAvailable(activity)) {
           // view.showProgress();
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.setEmail(dataManager.get(AppConstants.HB_USER_EMAIL, ""));
            packageInfo.setPackageName(PAYMENT_CATEGORY);
            packageInfo.setAssessment_name(PAYMENT_CATEGORY);


            new APIProvider.PaymentPackageExpiryDetails(packageInfo, AppConstants.BEARER + dataManager.get(AppConstants.HB_USER_TOKEN, ""),
                    1, null, null, new API_Response_Listener<Integer>() {
                @Override
                public void onComplete(Integer data, long request_code, String failure_code) {
                    if (data != null && data > 0) {

                        callBack.onPaymentCompleted();


                    } else if (data != null && data < 0) {

                        callBack.onPaymentNotCompleted();

                    } else {
                        view.showErrorMessage(activity.getResources().getString(R.string.went_wrong_try_again));
                    }

                    view.hideProgress();
                }

            }).call();


        } else {
            view.internetRequired();
        }
    }
*/
}
