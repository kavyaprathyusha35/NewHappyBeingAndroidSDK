/*
package com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.happybeing.nsmiles.R;
import com.happybeing.nsmiles.Utils.CommonUtils;
import com.happybeing.nsmiles.Utils.RoundedImageView;
import com.happybeing.nsmiles.mvp.assessmentreport.pregnancywellbeing.pregnancywellbeingcategorymodel.TableDatum;
import com.happybeing.nsmiles.mvp.commonmvp.CommonPresenter;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by Kavya on 10/31/2018.
 *//*


public class WellbeingCategoryReportAdapter extends BaseExpandableListAdapter {


    public Activity activity;
    public List<TableDatum> tableDatumList;

    private TextView category_tv, range_tv,message_tv ,exp_range_tv,sub_content_tv, first_step, reward_point, workOn;
    private LinearLayout strengthsLayout, goalsLayout;
    private RoundedImageView result_img;

    private String description="", message="" , exp_range="", sub_content="", firstStepString = "", rewardPointString = "", workOnString = "";
    private List<String> strengths = new ArrayList<>();
    private List<String> goals = new ArrayList<>();
    private int score;
    private String rangeString = "";
    private CommonPresenter.ReportInterface examPressureReportPresenter;

    public WellbeingCategoryReportAdapter(Activity activity, List<TableDatum> tableDatumList, CommonPresenter.ReportInterface examPressureReportPresenter) {
        Log.i("WellbingCategory","in on wellbeing report adapter");

        this.activity = activity;
        this.tableDatumList = tableDatumList;
        this.examPressureReportPresenter = examPressureReportPresenter;
    }


    @Override
    public int getGroupCount() {
        return tableDatumList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return tableDatumList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return childPosititon;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        try {
            if(tableDatumList.get(groupPosition).getIpTitle()!=null && tableDatumList.get(groupPosition).getIpTitle().length()>0) {
                description = tableDatumList.get(groupPosition).getIpTitle();
            }

            if(tableDatumList.get(groupPosition).getScore()!=null) {
                score = tableDatumList.get(groupPosition).getScore();
            }
            if (tableDatumList.get(groupPosition).getRange() != null) {
                rangeString = tableDatumList.get(groupPosition).getRange();
            }
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert infalInflater != null;
                convertView = infalInflater.inflate(R.layout.exam_pressure_list_group, null);
            }

            category_tv = (TextView) convertView.findViewById(R.id.category_tv);
            range_tv = (TextView) convertView.findViewById(R.id.range_tv);
            result_img = (RoundedImageView) convertView.findViewById(R.id.result_img);
            // category_tv.setTypeface(null, Typeface.BOLD);
            if (rangeString.equals("Low") || rangeString.equals("")) {
                result_img.setBackgroundColor(activity.getResources().getColor(R.color.low));
            }
            else if (rangeString.equals("Moderate") || rangeString.equals("Room for improvement")) {
                result_img.setBackgroundColor(activity.getResources().getColor(R.color.ok));
            }
            else if (rangeString.equals("High")) {
                result_img.setBackgroundColor(activity.getResources().getColor(R.color.high));
            }
            else if (rangeString.equals("Very Low")) {
                result_img.setBackgroundColor(activity.getResources().getColor(R.color.bad));
            }
            else if (rangeString.equals("Very High") || rangeString.equals("Excellent health")) {
                result_img.setBackgroundColor(activity.getResources().getColor(R.color.good));
            }
            range_tv.setText(rangeString);
            category_tv.setText(Html.fromHtml(description));
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("error" , e.getMessage());
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert infalInflater != null;
            convertView = infalInflater.inflate(R.layout.exam_pressure_list_item, null);
        }

       // exp_range = tableDatumList.get(groupPosition).getRangePercentage().toString();
        sub_content = tableDatumList.get(groupPosition).getSubcontent();
        message = tableDatumList.get(groupPosition).getIpMessage();
        firstStepString = tableDatumList.get(groupPosition).getFirstStep();
        rewardPointString = tableDatumList.get(groupPosition).getRewardPoint();
        strengths = new ArrayList<>();
        goals = new ArrayList<>();
        strengths = tableDatumList.get(groupPosition).getStrengths();
        goals = tableDatumList.get(groupPosition).getGoals();
        workOnString = tableDatumList.get(groupPosition).getWorkOn();

        checkForPayment();
        Log.i("WellbingCategory", "***MESSAGE IS ***"+message);
        message_tv = (TextView) convertView.findViewById(R.id.message_tv);
        exp_range_tv = (TextView) convertView.findViewById(R.id.exp_range_tv);
        sub_content_tv = (TextView) convertView.findViewById(R.id.sub_content_tv);
        first_step = convertView.findViewById(R.id.first_step);
        reward_point = convertView.findViewById(R.id.reward_point);
        strengthsLayout = convertView.findViewById(R.id.strengths_layout);
        goalsLayout = convertView.findViewById(R.id.goals_layout);
        workOn = convertView.findViewById(R.id.work_on);
        exp_range_tv.setText(Html.fromHtml(exp_range));
        sub_content_tv.setText(Html.fromHtml(sub_content));
        message_tv.setText(Html.fromHtml(message));
        reward_point.setText(rewardPointString);
        first_step.setText(firstStepString);
        workOn.setText(workOnString);
        strengthsLayout.removeAllViews();
        goalsLayout.removeAllViews();
        TextView strengthHeading = new TextView(activity);
        strengthHeading.setText("STRENGTHS");
        strengthHeading.setTextSize(22);
        strengthHeading.setBackground(activity.getResources().getDrawable(R.color.button_color));
        strengthHeading.setPadding(10, 15, 10, 15);
        strengthHeading.setGravity(Gravity.CENTER);
        strengthHeading.setTextColor(Color.WHITE);
        strengthHeading.setTypeface(Typeface.DEFAULT_BOLD);
        strengthsLayout.addView(strengthHeading);

        TextView goalsHeading = new TextView(activity);
        goalsHeading.setText("RECOMMENDATIONS");
        goalsHeading.setTextSize(22);
        goalsHeading.setBackground(activity.getResources().getDrawable(R.color.button_color));
        goalsHeading.setPadding(10, 15, 10, 15);
        goalsHeading.setGravity(Gravity.CENTER);
        goalsHeading.setTextColor(Color.WHITE);
        goalsHeading.setTypeface(Typeface.DEFAULT_BOLD);
        goalsLayout.addView(goalsHeading);
        if (strengths != null) {
            for (int i = 0; i < strengths.size(); i++) {
                TextView textView = new TextView(activity);
                textView.setText("\u2713 " + strengths.get(i));
                textView.setPadding(5, 10, 5, 10);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(18);

                strengthsLayout.addView(textView);
                View view = new View(activity);
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                view.setBackgroundColor(Color.parseColor("#B3B3B3"));
                strengthsLayout.addView(view);
            }
        }
        if (goals != null) {
            for (int i = 0; i < goals.size(); i++) {
                TextView textView = new TextView(activity);
                textView.setText("\u2713 " + goals.get(i));
                textView.setPadding(5, 10, 5, 10);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(18);

                goalsLayout.addView(textView);
                View view = new View(activity);
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                view.setBackgroundColor(Color.parseColor("#B3B3B3"));
                goalsLayout.addView(view);
            }
        }
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private void checkForPayment() {
        if (CommonUtils.isNetworkAvailable(activity)) {

        }
        else {
            first_step.setVisibility(View.GONE);
            reward_point.setVisibility(View.GONE);
            strengthsLayout.setVisibility(View.GONE);
            goalsLayout.setVisibility(View.GONE);
            workOn.setVisibility(View.GONE);

        }
    }

}*/
