//package com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing;
//
//import android.app.Activity;
//import android.content.Context;
//import android.text.Html;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.TextView;
//
//import com.nsmiles.happybeingsdklib.R;
////import com.nsmiles.happybeingsdklib.Reports.DetailReport.DetailReportImplementation;
//import com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.pregnancywellbeingmodel.TableDatum;
//import com.nsmiles.happybeingsdklib.Utils.RoundedImageView;
//import com.nsmiles.happybeingsdklib.mycoachfragment.presenter.CommonPresenter;
//
//import java.util.List;
//
///**
// * Created by Kavya on 10/31/2018.
// */
//
//public class WellbeingReportAdapter extends BaseExpandableListAdapter {
//
//
//    public Activity activity;
//    public List<TableDatum> tableDatumList;
//
//    private TextView category_tv, range_tv,message_tv ,exp_range_tv,sub_content_tv;
//    private RoundedImageView result_img;
//
//    private String description="", message="" , exp_range="", sub_content="";
//    private int score = 0;
//    private CommonPresenter.ReportInterface examPressureReportPresenter;
//
//    public WellbeingReportAdapter(Activity activity, List<TableDatum> tableDatumList, DetailReportImplementation examPressureReportPresenter) {
//        Log.i("WellbiengAdapter", "**********In wellbeing adapter*****");
//        this.activity = activity;
//        this.tableDatumList = tableDatumList;
//        this.examPressureReportPresenter = examPressureReportPresenter;
//    }
//
//
//    @Override
//    public int getGroupCount() {
//        return tableDatumList.size();
//    }
//
//    @Override
//    public int getChildrenCount(int groupPosition) {
//        return 1;
//    }
//
//    @Override
//    public Object getGroup(int groupPosition) {
//        return tableDatumList.get(groupPosition);
//    }
//
//    @Override
//    public Object getChild(int groupPosition, int childPosititon) {
//        return childPosititon;
//    }
//
//    @Override
//    public long getGroupId(int groupPosition) {
//        return groupPosition;
//    }
//
//    @Override
//    public long getChildId(int groupPosition, int childPosition) {
//        return childPosition;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return false;
//    }
//
//    @Override
//    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
//        Log.i("WellBeingAdapter", "Category text is "+description);
//        try {
//            if(tableDatumList.get(groupPosition).getDescription()!=null && tableDatumList.get(groupPosition).getDescription().length()>0) {
//                description = tableDatumList.get(groupPosition).getDescription();
//
//            }
//
///*
//            if(tableDatumList.get(groupPosition).getScore()!=null) {
//                score = tableDatumList.get(groupPosition).getScore();
//            }
//*/
//
//            if (convertView == null) {
//                LayoutInflater infalInflater = (LayoutInflater) activity
//                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                assert infalInflater != null;
//                convertView = infalInflater.inflate(R.layout.exam_pressure_list_group, null);
//            }
//
//            category_tv = (TextView) convertView.findViewById(R.id.category_tv);
//            range_tv = (TextView) convertView.findViewById(R.id.range_tv);
//
//
//            result_img = (RoundedImageView) convertView.findViewById(R.id.result_img);
//            // category_tv.setTypeface(null, Typeface.BOLD);
//            category_tv.setText(Html.fromHtml(description));
//            // >0 <3 Good
//            // >=3 <5 Ok
//            // >=5 bad
//            boolean lock = false;
//            Log.i("WellBeingAdapter", "Score in adapter is "+score);
//            if(score>=0 && score<3){
//                result_img.setBackgroundColor(activity.getResources().getColor(R.color.good));
//                range_tv.setText("GOOD");
//                lock = false;
//            }
//            else if(score>=3 && score<5){
//                result_img.setBackgroundColor(activity.getResources().getColor(R.color.ok));
//                range_tv.setText("OK");
//                lock = true;
//            }
//            else if(score>=5){
//                result_img.setBackgroundColor(activity.getResources().getColor(R.color.bad));
//                range_tv.setText("BAD");
//                lock = true;
//            }
//            examPressureReportPresenter.scoreInfomation(lock, groupPosition);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("error" , e.getMessage());
//        }
//
//        return convertView;
//    }
//
//    @Override
//    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {
//
//
//
//        if (convertView == null) {
//            LayoutInflater infalInflater = (LayoutInflater) activity
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            assert infalInflater != null;
//            convertView = infalInflater.inflate(R.layout.exam_pressure_list_item, null);
//        }
//
//        exp_range = tableDatumList.get(groupPosition).getRangePercentage().toString();
//        sub_content = tableDatumList.get(groupPosition).getSubcontent();
//        message = tableDatumList.get(groupPosition).getDescription();
//
//        message_tv = (TextView) convertView.findViewById(R.id.message_tv);
//        exp_range_tv = (TextView) convertView.findViewById(R.id.exp_range_tv);
//        sub_content_tv = (TextView) convertView.findViewById(R.id.sub_content_tv);
//
//
//        exp_range_tv.setText(Html.fromHtml(exp_range));
//        sub_content_tv.setText(Html.fromHtml(sub_content));
//        message_tv.setText(Html.fromHtml(message));
//
//        return convertView;
//    }
//
//
//    @Override
//    public boolean isChildSelectable(int i, int i1) {
//        return true;
//    }
//}