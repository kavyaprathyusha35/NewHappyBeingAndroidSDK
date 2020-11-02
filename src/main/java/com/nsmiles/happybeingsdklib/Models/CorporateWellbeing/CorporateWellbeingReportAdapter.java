/*
package com.nsmiles.happybeingsdklib.Models.CorporateWellbeing;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.happybeing.nsmiles.R;
import com.happybeing.nsmiles.ServerAPIConnectors.APIProvider;
import com.happybeing.nsmiles.ServerAPIConnectors.API_Response_Listener;
import com.happybeing.nsmiles.Utils.AppConstants;
import com.happybeing.nsmiles.Utils.CommonUtils;
import com.happybeing.nsmiles.Utils.RoundedImageView;
import com.happybeing.nsmiles.dagger.data.DataManager;
import com.happybeing.nsmiles.model.PackageInfo;
import com.happybeing.nsmiles.mvp.assessments.wellbeingassessment.implementation.WellBeingCategoryImplementation;
import com.happybeing.nsmiles.mvp.commonmvp.CommonPresenter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by Kavya on 10/31/2018.
 *//*


public class CorporateWellbeingReportAdapter extends BaseExpandableListAdapter {

    public Activity activity;
    public List<TableDatum> tableDatumList;
    TextView familytitle2,q15t1,q15t2,q15t3,q15t4,q15t5,q15d1,q15d2,q15d3,q15d4,q15d5,q152t1,q152t2,q152t3,q152t4,q152t5,q152d1,q152d2,q152d3,q152d4,q152d5;
    LinearLayout familylin1,familylin2;
    private TextView physicaloptions1,emotiontrigger,category_tv, range_tv,message_tv ,exp_range_tv,sub_content_tv,self_plan,things_workon,recommend_tips, first_step, reward_point, workOn,self_care,family1title;
    private LinearLayout strengthsLayout, goalsLayout,selected1_layout,selected2_layout,selected21_layout,selected3_layout,selected4_layout,selected4_1layout,selected5layout,selected6layout,selected7layout,selected8layout;
    private RoundedImageView result_img;
    private String selectheader1="",selectheader2="",selectheader21="",selectheader3="",selectheader4="",selectheader41="",selectheader5="",selectheader6="",selectheader7="",selectheader8="",strengthsheadder="",goalheader="";

    private String description="",str_physicalselected="",str_emotiontrigger="",str_recommend_tips="", message="" , exp_range="",faimlytitle1="",faimlytitle2="",faimlydescription1="", sub_content="",work_on="", firstStepString = "", rewardPointString = "", workOnString = "",str_selfcare="";
    private List<String> strengths = new ArrayList<>();
    private List<String> goals = new ArrayList<>();
    private List<String> selected1 = new ArrayList<>();
    private List<String> selected2 = new ArrayList<>();
    private List<String> selected21 = new ArrayList<>();
    private List<String> selected3 = new ArrayList<>();
    private List<String> selected4 = new ArrayList<>();
    private List<String> selected41 = new ArrayList<>();
    private List<String> selected5 = new ArrayList<>();
    private List<String> selected6 = new ArrayList<>();
    private List<String> selected7 = new ArrayList<>();
    private List<String> selected8 = new ArrayList<>();

    private int score;
    private String rangeString = "";
    private CommonPresenter.ReportInterface examPressureReportPresenter;
    private DataManager dataManager;
    private boolean isPaid;

    public CorporateWellbeingReportAdapter(Activity activity, DataManager dataManager, List<TableDatum> tableDatumList, boolean isPaid, CommonPresenter.ReportInterface examPressureReportPresenter) {
        Log.i("WellbingCategory","in on wellbeing report adapter");
        this.activity = activity;
        this.dataManager = dataManager;
        this.tableDatumList = tableDatumList;
        this.isPaid = isPaid;
        this.examPressureReportPresenter = examPressureReportPresenter;
        checkForPaymentStatus();

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
            if(tableDatumList.get(groupPosition).getTitle()!=null && tableDatumList.get(groupPosition).getTitle().length()>0) {
                description = tableDatumList.get(groupPosition).getTitle();

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
//            result_img = (RoundedImageView) convertView.findViewById(R.id.result_img);
//            // category_tv.setTypeface(null, Typeface.BOLD);
//            if (rangeString.equals("Low")) {
//                result_img.setBackgroundColor(activity.getResources().getColor(R.color.low));
//            }
//            else if (rangeString.equals("Moderate")) {
//                result_img.setBackgroundColor(activity.getResources().getColor(R.color.ok));
//            }
//            else if (rangeString.equals("High")) {
//                result_img.setBackgroundColor(activity.getResources().getColor(R.color.high));
//            }
//            else if (rangeString.equals("Very Low")) {
//                result_img.setBackgroundColor(activity.getResources().getColor(R.color.bad));
//            }
//            else if (rangeString.equals("Very High")) {
//                result_img.setBackgroundColor(activity.getResources().getColor(R.color.good));
//            }
            range_tv.setText(Html.fromHtml(rangeString));
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
        message = tableDatumList.get(groupPosition).getIp_message();
        firstStepString = tableDatumList.get(groupPosition).getFirstStep();
        rewardPointString = tableDatumList.get(groupPosition).getRewardPoint();
        faimlytitle1 = tableDatumList.get(groupPosition).getFamilyoptionTitle1();
        faimlydescription1 = tableDatumList.get(groupPosition).getFamilyoptionDes1();
        work_on = tableDatumList.get(groupPosition).getBelief_workon();
        str_recommend_tips = tableDatumList.get(groupPosition).getRecomendation_tips();
        str_physicalselected = tableDatumList.get(groupPosition).getPhysicalHealthOption71();
        str_emotiontrigger = tableDatumList.get(groupPosition).getEmotional_triggers();
        strengths = new ArrayList<>();
        goals = new ArrayList<>();
        selected1 = new ArrayList<>();
        selected2 = new ArrayList<>();
        selected21 = new ArrayList<>();
        selected3 = new ArrayList<>();
        selected4 = new ArrayList<>();
        selected41 = new ArrayList<>();
        selected5 = new ArrayList<>();
        selected6 = new ArrayList<>();
        selected7 = new ArrayList<>();
        selected8 = new ArrayList<>();
        strengths = tableDatumList.get(groupPosition).getStrengths();
        goals = tableDatumList.get(groupPosition).getGoals();
        selected1 = tableDatumList.get(groupPosition).getSelectedOption1();
        selected2 = tableDatumList.get(groupPosition).getSelectedOption2();
        selected21 = tableDatumList.get(groupPosition).getSelectedOption2_1();
        selected3 = tableDatumList.get(groupPosition).getSelectedOption3();
        selected4 = tableDatumList.get(groupPosition).getSelectedOption4();
        selected41 = tableDatumList.get(groupPosition).getSelectedOption4_1();
        selected5 = tableDatumList.get(groupPosition).getSelectedOption5();
        selected6 = tableDatumList.get(groupPosition).getSelectedOption6();
        selected7 = tableDatumList.get(groupPosition).getSelectedOption7();
        selected8 = tableDatumList.get(groupPosition).getSelectedOption8();
        workOnString = tableDatumList.get(groupPosition).getWorkOn();

        str_selfcare = tableDatumList.get(groupPosition).getSelf_care();

        selectheader1 = tableDatumList.get(groupPosition).getSelectedOption1_headder();
        selectheader2 = tableDatumList.get(groupPosition).getSelectedOption2_headder();
        selectheader21 = tableDatumList.get(groupPosition).getSelectedOption2_1_headder();
        selectheader3 = tableDatumList.get(groupPosition).getSelectedOption3_headder();
        selectheader4 = tableDatumList.get(groupPosition).getSelectedOption4_headder();
        selectheader41 = tableDatumList.get(groupPosition).getSelectedOption4_1_headder();
        selectheader5 = tableDatumList.get(groupPosition).getSelectedOption5_headder();
        selectheader6 = tableDatumList.get(groupPosition).getSelectedOption6_headder();
        selectheader7 = tableDatumList.get(groupPosition).getSelectedOption7_headder();
        selectheader8 = tableDatumList.get(groupPosition).getSelectedOption8_headder();
        strengthsheadder = tableDatumList.get(groupPosition).getStrengths_headder();
        goalheader = tableDatumList.get(groupPosition).getGoals_headder();

        Log.i("Wellbeing", "Strengths size is " + strengths.size() + "Goals size is " + goals);

        Log.i("WellbingCategory", "***MESSAGE IS ***" + message);
        message_tv = (TextView) convertView.findViewById(R.id.message_tv);
        exp_range_tv = (TextView) convertView.findViewById(R.id.exp_range_tv);
        sub_content_tv = (TextView) convertView.findViewById(R.id.sub_content_tv);
        first_step = convertView.findViewById(R.id.first_step);
        reward_point = convertView.findViewById(R.id.reward_point);
        strengthsLayout = convertView.findViewById(R.id.strengths_layout);
        goalsLayout = convertView.findViewById(R.id.goals_layout);
        selected1_layout = convertView.findViewById(R.id.selected1_layout);
        selected2_layout = convertView.findViewById(R.id.selected2_layout);
        selected21_layout = convertView.findViewById(R.id.selected21_layout);
        workOn = convertView.findViewById(R.id.work_on);
        self_care = convertView.findViewById(R.id.self_care);
        things_workon = convertView.findViewById(R.id.things_workon);
        family1title = convertView.findViewById(R.id.family1title);
        recommend_tips = convertView.findViewById(R.id.recommend_tips);
        physicaloptions1 = convertView.findViewById(R.id.physicaloptions1);
        emotiontrigger = convertView.findViewById(R.id.emotiontrigger);
        selected3_layout = convertView.findViewById(R.id.selected3_layout);
        selected4_layout = convertView.findViewById(R.id.selected4_layout);
        selected4_1layout = convertView.findViewById(R.id.selected4_1layout);
        selected5layout = convertView.findViewById(R.id.selected5layout);
        selected6layout = convertView.findViewById(R.id.selected6layout);
        selected7layout = convertView.findViewById(R.id.selected7layout);
        selected8layout = convertView.findViewById(R.id.selected8layout);

        familytitle2 = convertView.findViewById(R.id.family2title);
        familylin1 = convertView.findViewById(R.id.familylin1);
        familylin2 = convertView.findViewById(R.id.familylin2);
        q15t1 = convertView.findViewById(R.id.q15t1);
        q15t2 = convertView.findViewById(R.id.q15t2);
        q15t3 = convertView.findViewById(R.id.q15t3);
        q15t4 = convertView.findViewById(R.id.q15t4);
        q15t5 = convertView.findViewById(R.id.q15t5);
        q15d1 = convertView.findViewById(R.id.q15d1);
        q15d2 = convertView.findViewById(R.id.q15d2);
        q15d3 = convertView.findViewById(R.id.q15d3);
        q15d4 = convertView.findViewById(R.id.q15d4);
        q15d5 = convertView.findViewById(R.id.q15d5);

        q152t1 = convertView.findViewById(R.id.q152t1);
        q152t2 = convertView.findViewById(R.id.q152t2);
        q152t3 = convertView.findViewById(R.id.q152t3);
        q152t4 = convertView.findViewById(R.id.q152t4);
        q152t5 = convertView.findViewById(R.id.q152t5);
        q152d1 = convertView.findViewById(R.id.q152d1);
        q152d2 = convertView.findViewById(R.id.q152d2);
        q152d3 = convertView.findViewById(R.id.q152d3);
        q152d4 = convertView.findViewById(R.id.q152d4);
        q152d5 = convertView.findViewById(R.id.q152d5);



        if (isPaid) {
            first_step.setVisibility(View.VISIBLE);
            reward_point.setVisibility(View.VISIBLE);
            strengthsLayout.setVisibility(View.VISIBLE);
            goalsLayout.setVisibility(View.VISIBLE);
            workOn.setVisibility(View.VISIBLE);
            selected1_layout.setVisibility(View.VISIBLE);
            selected2_layout.setVisibility(View.VISIBLE);
            selected21_layout.setVisibility(View.VISIBLE);
            familylin1.setVisibility(View.VISIBLE);
            familylin2.setVisibility(View.VISIBLE);
            self_care.setVisibility(View.VISIBLE);
            things_workon.setVisibility(View.VISIBLE);
            recommend_tips.setVisibility(View.VISIBLE);
            selected3_layout.setVisibility(View.VISIBLE);
            selected4_layout.setVisibility(View.VISIBLE);
            selected4_1layout.setVisibility(View.VISIBLE);
            selected5layout.setVisibility(View.VISIBLE);
            selected6layout.setVisibility(View.VISIBLE);
            selected7layout.setVisibility(View.VISIBLE);
            selected8layout.setVisibility(View.VISIBLE);
            physicaloptions1.setVisibility(View.VISIBLE);
            emotiontrigger.setVisibility(View.VISIBLE);

            familytitle2.setVisibility(View.VISIBLE);
            family1title.setVisibility(View.VISIBLE);
            q15t1.setVisibility(View.VISIBLE);
            q15t2.setVisibility(View.VISIBLE);
            q15t3 .setVisibility(View.VISIBLE);
            q15t4.setVisibility(View.VISIBLE);
            q15t5.setVisibility(View.VISIBLE);
            q15d1.setVisibility(View.VISIBLE);
            q15d2.setVisibility(View.VISIBLE);
            q15d3.setVisibility(View.VISIBLE);
            q15d4.setVisibility(View.VISIBLE);
            q15d5.setVisibility(View.VISIBLE);

            q152t1.setVisibility(View.VISIBLE);
            q152t2.setVisibility(View.VISIBLE);
            q152t3.setVisibility(View.VISIBLE);
            q152t4.setVisibility(View.VISIBLE);
            q152t5.setVisibility(View.VISIBLE);
            q152d1.setVisibility(View.VISIBLE);
            q152d2.setVisibility(View.VISIBLE);
            q152d3.setVisibility(View.VISIBLE);
            q152d4.setVisibility(View.VISIBLE);
            q152d5.setVisibility(View.VISIBLE);

        } else {
            first_step.setVisibility(View.GONE);
            reward_point.setVisibility(View.GONE);
            strengthsLayout.setVisibility(View.GONE);
            goalsLayout.setVisibility(View.GONE);
            workOn.setVisibility(View.GONE);
            familylin1.setVisibility(View.GONE);
            familylin2.setVisibility(View.GONE);
            selected1_layout.setVisibility(View.GONE);
            self_care.setVisibility(View.GONE);
            things_workon.setVisibility(View.GONE);
            selected2_layout.setVisibility(View.GONE);
            selected21_layout.setVisibility(View.GONE);
            recommend_tips.setVisibility(View.GONE);
            selected3_layout.setVisibility(View.GONE);
            selected4_layout.setVisibility(View.GONE);
            selected4_1layout.setVisibility(View.GONE);
            selected5layout.setVisibility(View.GONE);
            selected6layout.setVisibility(View.GONE);
            selected7layout.setVisibility(View.GONE);
            selected8layout.setVisibility(View.GONE);
            physicaloptions1.setVisibility(View.GONE);
            emotiontrigger.setVisibility(View.GONE);

            familytitle2.setVisibility(View.GONE);
            family1title.setVisibility(View.GONE);

            q15t1.setVisibility(View.GONE);
            q15t2.setVisibility(View.GONE);
            q15t3 .setVisibility(View.GONE);
            q15t4.setVisibility(View.GONE);
            q15t5.setVisibility(View.GONE);
            q15d1.setVisibility(View.GONE);
            q15d2.setVisibility(View.GONE);
            q15d3.setVisibility(View.GONE);
            q15d4.setVisibility(View.GONE);
            q15d5.setVisibility(View.GONE);

            q152t1.setVisibility(View.GONE);
            q152t2.setVisibility(View.GONE);
            q152t3.setVisibility(View.GONE);
            q152t4.setVisibility(View.GONE);
            q152t5.setVisibility(View.GONE);
            q152d1.setVisibility(View.GONE);
            q152d2.setVisibility(View.GONE);
            q152d3.setVisibility(View.GONE);
            q152d4.setVisibility(View.GONE);
            q152d5.setVisibility(View.GONE);

        }

        if(exp_range!=null && !exp_range.equals("")){

            exp_range_tv.setText(Html.fromHtml(exp_range));

        }else {

            exp_range_tv.setVisibility(View.GONE);
        }

        if(sub_content!=null && !sub_content.equals("")) {
            sub_content_tv.setText(Html.fromHtml(sub_content));
        }else {

            sub_content_tv.setVisibility(View.GONE);
        }

        if(message!=null && !message.equals("")) {
            message_tv.setText(Html.fromHtml(message));
        }else {

            message_tv.setVisibility(View.GONE);
        }
        //reward_point.setText(rewardPointString);
        if(firstStepString!=null && !firstStepString.equals("")) {
            first_step.setText(Html.fromHtml(firstStepString));
        }else {

            first_step.setVisibility(View.GONE);
        }
        if(workOnString!=null && !workOnString.equals("")) {
            workOn.setText(Html.fromHtml(workOnString));
        }else {

            workOn.setVisibility(View.GONE);
        }


        if(str_selfcare!=null && !str_selfcare.equals("")) {
            self_care.setText(Html.fromHtml(str_selfcare));
        }else {

            self_care.setVisibility(View.GONE);
        }

        if(work_on!=null && !work_on.equals("")) {
            things_workon.setText(Html.fromHtml(work_on));
        }else {

            things_workon.setVisibility(View.GONE);
        }

        if(str_recommend_tips!=null && !str_recommend_tips.equals("")) {

            recommend_tips.setText(Html.fromHtml(str_recommend_tips));
        }else {

            recommend_tips.setVisibility(View.GONE);
        }

        if(tableDatumList.get(groupPosition).getPhysicalHealthOption71()!=null && !tableDatumList.get(groupPosition).getPhysicalHealthOption71().equals("")){

            physicaloptions1.setText(Html.fromHtml(str_physicalselected));
        } else {
            physicaloptions1.setVisibility(View.GONE);
        }

        if(tableDatumList.get(groupPosition).getEmotional_triggers()!=null && !tableDatumList.get(groupPosition).getEmotional_triggers().equals("")){

            emotiontrigger.setText(Html.fromHtml(str_emotiontrigger));
        } else {
            emotiontrigger.setVisibility(View.GONE);
        }


        if (tableDatumList.get(groupPosition).getFamilyoptionTitle1()==null || tableDatumList.get(groupPosition).getFamilyQ15_1_title1()==null){
            familylin1.setVisibility(View.GONE);

        }else{

            familylin1.setVisibility(View.VISIBLE);
            family1title.setText(Html.fromHtml(faimlytitle1));
            q15t1.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_1_title1()));
            q15t2.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_1_title2()));
            q15t3.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_1_title3()));
            q15t4.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_1_title4()));
            q15t5.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_1_title5()));

            PicassoImageGetter imageGetter = new PicassoImageGetter(q15d1);
            Spannable html = (Spannable) Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_1_Desc1(), imageGetter, null);
            q15d1.setText(html);
           // q15d1.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_1_Desc1(),new ImageGetter(),null));
            q15d2.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_1_Desc2()));
            q15d3.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_1_Desc3()));
            q15d4.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_1_Desc4()));
            q15d5.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_1_Desc5()));
        }

        if (tableDatumList.get(groupPosition).getFamilyoptionTitle2()==null || tableDatumList.get(groupPosition).getFamilyQ15_2_title1()==null){
            familylin2.setVisibility(View.GONE);

        }else{

            familylin2.setVisibility(View.VISIBLE);
            familytitle2.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyoptionTitle2()));
            q152t1.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_2_title1()));
            q152t2.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_2_title2()));
            q152t3.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_2_title3()));
            q152t4.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_2_title4()));
            q152t5.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_2_title5()));

            PicassoImageGetter imageGetter = new PicassoImageGetter(q152d1);
            Spannable html = (Spannable) Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_2_Desc1(), imageGetter, null);
            q152d1.setText(html);

            //q152d1.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_2_Desc1(),new ImageGetter(),null));
            q152d2.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_2_Desc2()));
            q152d3.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_2_Desc3()));
            q152d4.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_2_Desc4()));
            q152d5.setText(Html.fromHtml(tableDatumList.get(groupPosition).getFamilyQ15_2_Desc5()));
        }


        selected1_layout.removeAllViews();
        selected2_layout.removeAllViews();
        selected21_layout.removeAllViews();
        strengthsLayout.removeAllViews();
        goalsLayout.removeAllViews();
        selected3_layout.removeAllViews();
        selected4_layout.removeAllViews();
        selected4_1layout.removeAllViews();
        selected5layout.removeAllViews();
        selected6layout.removeAllViews();
        selected7layout.removeAllViews();
        selected8layout.removeAllViews();

        if (strengths != null && !strengths.isEmpty()) {

            if(strengthsheadder!=null && !strengthsheadder.equals("")) {

                TextView strengthHeading = new TextView(activity);
                strengthHeading.setText(Html.fromHtml(strengthsheadder));
                strengthHeading.setTextSize(20);
               // strengthHeading.setBackground(activity.getResources().getDrawable(R.color.button_color));
                strengthHeading.setPadding(10, 15, 10, 15);
                strengthHeading.setGravity(Gravity.CENTER);
                strengthHeading.setTextColor(Color.BLACK);

                if (strengths.size() > 0) {
                    strengthsLayout.addView(strengthHeading);
                }
            }

        }else {
            strengthsLayout.setVisibility(View.GONE);

        }


        if (selected1 != null && !selected1.isEmpty()) {

            if(selectheader1!=null && !selectheader1.equals("")) {

                 TextView selectedoption1z = new TextView(activity);
                selectedoption1z.setText(Html.fromHtml(selectheader1));
                selectedoption1z.setTextSize(20);
               // selectedoption1z.setBackground(activity.getResources().getDrawable(R.color.button_color));
                selectedoption1z.setPadding(10, 15, 10, 15);
                selectedoption1z.setGravity(Gravity.CENTER);
                selectedoption1z.setTextColor(Color.BLACK);

                if (selected1.size() > 0) {
                    selected1_layout.addView(selectedoption1z);
                }

            }

        } else {

            selected1_layout.setVisibility(View.GONE);
        }


        if (selected2 != null && !selected2.isEmpty()) {

            if(selectheader2!=null && !selectheader2.equals("")) {

                TextView selectedoption2z = new TextView(activity);
                selectedoption2z.setText(Html.fromHtml(selectheader2));
                selectedoption2z.setTextSize(20);
                //selectedoption2z.setBackground(activity.getResources().getDrawable(R.color.button_color));
                selectedoption2z.setPadding(10, 15, 10, 15);
                selectedoption2z.setGravity(Gravity.CENTER);
                selectedoption2z.setTextColor(Color.BLACK);

                if (selected2.size() > 0) {
                    selected2_layout.addView(selectedoption2z);
                }

            }

        }else {
            selected2_layout.setVisibility(View.GONE);

        }

        if (selected21 != null && !selected21.isEmpty()) {

            if(selectheader21!=null && !selectheader21.equals("")) {

                TextView selectedoption21z = new TextView(activity);
                selectedoption21z.setText(Html.fromHtml(selectheader21));
                selectedoption21z.setTextSize(20);
              //  selectedoption21z.setBackground(activity.getResources().getDrawable(R.color.button_color));
                selectedoption21z.setPadding(10, 15, 10, 15);
                selectedoption21z.setGravity(Gravity.CENTER);
                selectedoption21z.setTextColor(Color.BLACK);

                if (selected21.size() > 0) {
                    selected21_layout.addView(selectedoption21z);
                }
            }

        }else {
            selected21_layout.setVisibility(View.GONE);

        }

        if (selected3 != null && !selected3.isEmpty()) {

            if(selectheader3!=null && !selectheader3.equals("")) {

                TextView selectedoption21z = new TextView(activity);
                selectedoption21z.setText(Html.fromHtml(selectheader3));
                selectedoption21z.setTextSize(20);
             //   selectedoption21z.setBackground(activity.getResources().getDrawable(R.color.button_color));
                selectedoption21z.setPadding(10, 15, 10, 15);
                selectedoption21z.setGravity(Gravity.CENTER);
                selectedoption21z.setTextColor(Color.BLACK);

                if (selected3.size() > 0) {
                    selected3_layout.addView(selectedoption21z);
                }

            }

        }else {
            selected3_layout.setVisibility(View.GONE);

        }


        if (selected4 != null && !selected4.isEmpty()) {

            if(selectheader4!=null && !selectheader4.equals("")) {

                TextView selectedoption21z = new TextView(activity);
                selectedoption21z.setText(Html.fromHtml(selectheader4));
                selectedoption21z.setTextSize(20);
            //    selectedoption21z.setBackground(activity.getResources().getDrawable(R.color.button_color));
                selectedoption21z.setPadding(10, 15, 10, 15);
                selectedoption21z.setGravity(Gravity.CENTER);
                selectedoption21z.setTextColor(Color.BLACK);

                if (selected4.size() > 0) {
                    selected4_layout.addView(selectedoption21z);
                }
            }

        }else {
            selected4_layout.setVisibility(View.GONE);

        }

        if (selected41 != null && !selected41.isEmpty()) {

            if(selectheader41!=null && !selectheader41.equals("")) {

                TextView selectedoption21z = new TextView(activity);
                selectedoption21z.setText(Html.fromHtml(selectheader41));
                selectedoption21z.setTextSize(20);
              //  selectedoption21z.setBackground(activity.getResources().getDrawable(R.color.button_color));
                selectedoption21z.setPadding(10, 15, 10, 15);
                selectedoption21z.setGravity(Gravity.CENTER);
                selectedoption21z.setTextColor(Color.BLACK);

                if (selected41.size() > 0) {
                    selected4_1layout.addView(selectedoption21z);
                }

            }

        }else {
            selected4_1layout.setVisibility(View.GONE);

        }


        if (selected5 != null && !selected5.isEmpty()) {

            if(selectheader5!=null && !selectheader5.equals("")) {

                TextView selectedoption21z = new TextView(activity);
                selectedoption21z.setText(Html.fromHtml(selectheader5));
                selectedoption21z.setTextSize(20);
             //   selectedoption21z.setBackground(activity.getResources().getDrawable(R.color.button_color));
                selectedoption21z.setPadding(10, 15, 10, 15);
                selectedoption21z.setGravity(Gravity.CENTER);
                selectedoption21z.setTextColor(Color.BLACK);

                if (selected5.size() > 0) {
                    selected5layout.addView(selectedoption21z);
                }

            }

        }else {
            selected5layout.setVisibility(View.GONE);

        }


        if (selected6 != null && !selected6.isEmpty()) {

            if(selectheader6!=null && !selectheader6.equals("")) {

                TextView selectedoption21z = new TextView(activity);
                selectedoption21z.setText(Html.fromHtml(selectheader6));
                selectedoption21z.setTextSize(20);
               // selectedoption21z.setBackground(activity.getResources().getDrawable(R.color.button_color));
                selectedoption21z.setPadding(10, 15, 10, 15);
                selectedoption21z.setGravity(Gravity.CENTER);
                selectedoption21z.setTextColor(Color.BLACK);

                if (selected6.size() > 0) {
                    selected6layout.addView(selectedoption21z);
                }

            }

        }else {
            selected6layout.setVisibility(View.GONE);

        }


        if (selected7 != null && !selected7.isEmpty()) {
            if(selectheader7!=null && !selectheader7.equals("")) {
                TextView selectedoption21z = new TextView(activity);
                selectedoption21z.setText(Html.fromHtml(selectheader7));
                selectedoption21z.setTextSize(20);
                //selectedoption21z.setBackground(activity.getResources().getDrawable(R.color.button_color));
                selectedoption21z.setPadding(10, 15, 10, 15);
                selectedoption21z.setGravity(Gravity.CENTER);
                selectedoption21z.setTextColor(Color.BLACK);

                if (selected7.size() > 0) {
                    selected7layout.addView(selectedoption21z);
                }
            }

        }else {
            selected7layout.setVisibility(View.GONE);

        }


        if (selected8 != null && !selected8.isEmpty()) {
            if(selectheader8!=null && !selectheader8.equals("")) {
                TextView selectedoption21z = new TextView(activity);
                selectedoption21z.setText(Html.fromHtml(selectheader8));
                selectedoption21z.setTextSize(20);
              //  selectedoption21z.setBackground(activity.getResources().getDrawable(R.color.button_color));
                selectedoption21z.setPadding(10, 15, 10, 15);
                selectedoption21z.setGravity(Gravity.CENTER);
                selectedoption21z.setTextColor(Color.BLACK);

                if (selected8.size() > 0) {
                    selected8layout.addView(selectedoption21z);
                }

            }

        }else {
            selected8layout.setVisibility(View.GONE);

        }

        if (goals != null && !goals.isEmpty()) {

            if(goalheader!=null && !goalheader.equals("")) {

                TextView goalsHeading = new TextView(activity);
                goalsHeading.setText(Html.fromHtml(goalheader));
                goalsHeading.setTextSize(22);
            //    goalsHeading.setBackground(activity.getResources().getDrawable(R.color.button_color));
                goalsHeading.setPadding(10, 15, 10, 15);
                goalsHeading.setGravity(Gravity.CENTER);
                goalsHeading.setTextColor(Color.BLACK);

                if (goals.size() > 0) {
                    goalsLayout.addView(goalsHeading);
                }
            }

        } else{
            goalsLayout.setVisibility(View.GONE);

        }


        if (strengths != null && !strengths.isEmpty()) {
            for (int i = 0; i < strengths.size(); i++) {
                TextView textView = new TextView(activity);
                textView.setText(Html.fromHtml(strengths.get(i)));
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

        if (selected3 != null && !selected3.isEmpty()) {
            for (int i = 0; i < selected3.size(); i++) {
                TextView textView = new TextView(activity);
                textView.setText(Html.fromHtml(selected3.get(i)));
                textView.setPadding(5, 10, 5, 10);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(18);

                selected3_layout.addView(textView);
                View view = new View(activity);
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                view.setBackgroundColor(Color.parseColor("#B3B3B3"));
                selected3_layout.addView(view);
            }
        }


        if (selected4!= null && !selected4.isEmpty()) {
            for (int i = 0; i < selected4.size(); i++) {
                TextView textView = new TextView(activity);
                textView.setText(Html.fromHtml(selected4.get(i)));
                textView.setPadding(5, 10, 5, 10);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(18);

                selected4_layout.addView(textView);
                View view = new View(activity);
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                view.setBackgroundColor(Color.parseColor("#B3B3B3"));
                selected4_layout.addView(view);
            }
        }

        if (selected41!= null && !selected41.isEmpty()) {
            for (int i = 0; i < selected41.size(); i++) {
                TextView textView = new TextView(activity);
                textView.setText( Html.fromHtml(selected41.get(i)));
                textView.setPadding(5, 10, 5, 10);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(18);

                selected4_1layout.addView(textView);
                View view = new View(activity);
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                view.setBackgroundColor(Color.parseColor("#B3B3B3"));
                selected4_1layout.addView(view);
            }
        }

        if (selected5!= null && !selected5.isEmpty()) {
            for (int i = 0; i < selected5.size(); i++) {
                TextView textView = new TextView(activity);
                textView.setText(Html.fromHtml(selected5.get(i)));
                textView.setPadding(5, 10, 5, 10);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(18);

                selected5layout.addView(textView);
                View view = new View(activity);
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                view.setBackgroundColor(Color.parseColor("#B3B3B3"));
                selected5layout.addView(view);
            }
        }

        if (selected6!= null && !selected6.isEmpty()) {
            for (int i = 0; i < selected6.size(); i++) {
                TextView textView = new TextView(activity);
                textView.setText( Html.fromHtml(selected6.get(i)));
                textView.setPadding(5, 10, 5, 10);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(18);

                selected6layout.addView(textView);
                View view = new View(activity);
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                view.setBackgroundColor(Color.parseColor("#B3B3B3"));
                selected6layout.addView(view);
            }
        }

        if (selected7!= null && !selected7.isEmpty()) {
            for (int i = 0; i < selected7.size(); i++) {
                TextView textView = new TextView(activity);
                textView.setText( Html.fromHtml(selected7.get(i)));
                textView.setPadding(5, 10, 5, 10);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(18);

                selected7layout.addView(textView);
                View view = new View(activity);
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                view.setBackgroundColor(Color.parseColor("#B3B3B3"));
                selected7layout.addView(view);
            }
        }

        if (selected8!= null && !selected8.isEmpty()) {
            for (int i = 0; i < selected8.size(); i++) {
                TextView textView = new TextView(activity);
                textView.setText(Html.fromHtml(selected8.get(i)));
                textView.setPadding(5, 10, 5, 10);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(18);

                selected8layout.addView(textView);
                View view = new View(activity);
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                view.setBackgroundColor(Color.parseColor("#B3B3B3"));
                selected8layout.addView(view);
            }
        }


        if (selected1 != null && !selected1.isEmpty()) {

            for (int i = 0; i < selected1.size(); i++) {
                TextView textView = new TextView(activity);
                textView.setText(Html.fromHtml(selected1.get(i)));
                textView.setPadding(5, 10, 5, 10);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(18);

                selected1_layout.addView(textView);
                View view = new View(activity);
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                view.setBackgroundColor(Color.parseColor("#B3B3B3"));
                selected1_layout.addView(view);
            }

        }


        if (selected2 != null && !selected2.isEmpty()) {

            for (int i = 0; i < selected2.size(); i++) {
                TextView textView = new TextView(activity);
                textView.setText(Html.fromHtml(selected2.get(i)));
                textView.setPadding(5, 10, 5, 10);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(18);

                selected2_layout.addView(textView);
                View view = new View(activity);
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                view.setBackgroundColor(Color.parseColor("#B3B3B3"));
                selected2_layout.addView(view);
            }

        }


        if (selected21 != null && !selected21.isEmpty()) {

            for (int i = 0; i < selected21.size(); i++) {
                TextView textView = new TextView(activity);
                textView.setText( Html.fromHtml(selected21.get(i)));
                textView.setPadding(5, 10, 5, 10);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(18);

                selected21_layout.addView(textView);
                View view = new View(activity);
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                view.setBackgroundColor(Color.parseColor("#B3B3B3"));
                selected21_layout.addView(view);
            }

        }


        if (goals != null && !goals.isEmpty()) {

            for (int i = 0; i < goals.size(); i++) {
                TextView textView = new TextView(activity);
                textView.setText( Html.fromHtml(goals.get(i)));
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

    private void checkForPaymentStatus() {
        if (CommonUtils.isNetworkAvailable(activity)) {
            checkAssessmentPaymentStatus(new WellBeingCategoryImplementation.WellBeingCallBack() {
                @Override
                public void onPaymentCompleted() {
                    first_step.setVisibility(View.VISIBLE);
                    reward_point.setVisibility(View.VISIBLE);
                    strengthsLayout.setVisibility(View.VISIBLE);
                    goalsLayout.setVisibility(View.VISIBLE);
                    workOn.setVisibility(View.VISIBLE);
                    selected1_layout.setVisibility(View.VISIBLE);
                    familylin1.setVisibility(View.VISIBLE);
                    familylin2.setVisibility(View.VISIBLE);
                    self_care.setVisibility(View.VISIBLE);
                    things_workon.setVisibility(View.VISIBLE);
                    selected2_layout.setVisibility(View.VISIBLE);
                    selected21_layout.setVisibility(View.VISIBLE);
                    recommend_tips.setVisibility(View.VISIBLE);
                    selected3_layout.setVisibility(View.VISIBLE);
                    selected4_layout.setVisibility(View.VISIBLE);
                    selected4_1layout.setVisibility(View.VISIBLE);
                    selected5layout.setVisibility(View.VISIBLE);
                    selected6layout.setVisibility(View.VISIBLE);
                    selected7layout.setVisibility(View.VISIBLE);
                    selected8layout.setVisibility(View.VISIBLE);
                    physicaloptions1.setVisibility(View.VISIBLE);
                    emotiontrigger.setVisibility(View.VISIBLE);

                    familytitle2.setVisibility(View.VISIBLE);
                    family1title.setVisibility(View.VISIBLE);

                    q15t1.setVisibility(View.VISIBLE);
                    q15t2.setVisibility(View.VISIBLE);
                    q15t3 .setVisibility(View.VISIBLE);
                    q15t4.setVisibility(View.VISIBLE);
                    q15t5.setVisibility(View.VISIBLE);
                    q15d1.setVisibility(View.VISIBLE);
                    q15d2.setVisibility(View.VISIBLE);
                    q15d3.setVisibility(View.VISIBLE);
                    q15d4.setVisibility(View.VISIBLE);
                    q15d5.setVisibility(View.VISIBLE);

                    q152t1.setVisibility(View.VISIBLE);
                    q152t2.setVisibility(View.VISIBLE);
                    q152t3.setVisibility(View.VISIBLE);
                    q152t4.setVisibility(View.VISIBLE);
                    q152t5.setVisibility(View.VISIBLE);
                    q152d1.setVisibility(View.VISIBLE);
                    q152d2.setVisibility(View.VISIBLE);
                    q152d3.setVisibility(View.VISIBLE);
                    q152d4.setVisibility(View.VISIBLE);
                    q152d5.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                }

                @Override
                public void onPaymentNotCompleted() {
                    first_step.setVisibility(View.GONE);
                    reward_point.setVisibility(View.GONE);
                    strengthsLayout.setVisibility(View.GONE);
                    goalsLayout.setVisibility(View.GONE);
                    workOn.setVisibility(View.GONE);
                    familylin1.setVisibility(View.GONE);
                    familylin2.setVisibility(View.GONE);
                    selected1_layout.setVisibility(View.GONE);
                    self_care.setVisibility(View.GONE);
                    things_workon.setVisibility(View.GONE);
                    selected2_layout.setVisibility(View.GONE);
                    selected21_layout.setVisibility(View.GONE);
                    recommend_tips.setVisibility(View.GONE);
                    selected3_layout.setVisibility(View.GONE);
                    selected4_layout.setVisibility(View.GONE);
                    selected4_1layout.setVisibility(View.GONE);
                    selected5layout.setVisibility(View.GONE);
                    selected6layout.setVisibility(View.GONE);
                    selected7layout.setVisibility(View.GONE);
                    selected8layout.setVisibility(View.GONE);
                    physicaloptions1.setVisibility(View.GONE);
                    emotiontrigger.setVisibility(View.GONE);

                    familytitle2.setVisibility(View.GONE);
                    family1title.setVisibility(View.GONE);

                    q15t1.setVisibility(View.GONE);
                    q15t2.setVisibility(View.GONE);
                    q15t3 .setVisibility(View.GONE);
                    q15t4.setVisibility(View.GONE);
                    q15t5.setVisibility(View.GONE);
                    q15d1.setVisibility(View.GONE);
                    q15d2.setVisibility(View.GONE);
                    q15d3.setVisibility(View.GONE);
                    q15d4.setVisibility(View.GONE);
                    q15d5.setVisibility(View.GONE);

                    q152t1.setVisibility(View.GONE);
                    q152t2.setVisibility(View.GONE);
                    q152t3.setVisibility(View.GONE);
                    q152t4.setVisibility(View.GONE);
                    q152t5.setVisibility(View.GONE);
                    q152d1.setVisibility(View.GONE);
                    q152d2.setVisibility(View.GONE);
                    q152d3.setVisibility(View.GONE);
                    q152d4.setVisibility(View.GONE);
                    q152d5.setVisibility(View.GONE);

                }
            });
        }
        else {
            first_step.setVisibility(View.GONE);
            reward_point.setVisibility(View.GONE);
            strengthsLayout.setVisibility(View.GONE);
            goalsLayout.setVisibility(View.GONE);
            workOn.setVisibility(View.GONE);
            familylin1.setVisibility(View.GONE);
            familylin2.setVisibility(View.GONE);
            selected1_layout.setVisibility(View.GONE);
            self_care.setVisibility(View.GONE);
            things_workon.setVisibility(View.GONE);
            selected2_layout.setVisibility(View.GONE);
            selected21_layout.setVisibility(View.GONE);
            recommend_tips.setVisibility(View.GONE);
            selected3_layout.setVisibility(View.GONE);
            selected4_layout.setVisibility(View.GONE);
            selected4_1layout.setVisibility(View.GONE);
            selected5layout.setVisibility(View.GONE);
            selected6layout.setVisibility(View.GONE);
            selected7layout.setVisibility(View.GONE);
            selected8layout.setVisibility(View.GONE);
            physicaloptions1.setVisibility(View.GONE);
            emotiontrigger.setVisibility(View.GONE);

            familytitle2.setVisibility(View.GONE);
            family1title.setVisibility(View.GONE);

            q15t1.setVisibility(View.GONE);
            q15t2.setVisibility(View.GONE);
            q15t3 .setVisibility(View.GONE);
            q15t4.setVisibility(View.GONE);
            q15t5.setVisibility(View.GONE);
            q15d1.setVisibility(View.GONE);
            q15d2.setVisibility(View.GONE);
            q15d3.setVisibility(View.GONE);
            q15d4.setVisibility(View.GONE);
            q15d5.setVisibility(View.GONE);

            q152t1.setVisibility(View.GONE);
            q152t2.setVisibility(View.GONE);
            q152t3.setVisibility(View.GONE);
            q152t4.setVisibility(View.GONE);
            q152t5.setVisibility(View.GONE);
            q152d1.setVisibility(View.GONE);
            q152d2.setVisibility(View.GONE);
            q152d3.setVisibility(View.GONE);
            q152d4.setVisibility(View.GONE);
            q152d5.setVisibility(View.GONE);

        }
    }

    public void checkAssessmentPaymentStatus(final WellBeingCategoryImplementation.WellBeingCallBack callBack) {


        PackageInfo packageInfo = new PackageInfo();
        packageInfo.setEmail(dataManager.get(AppConstants.HB_USER_EMAIL, ""));
        packageInfo.setPackageName(AppConstants.CORPORATE_WELLBEING);
        packageInfo.setAssessment_name(AppConstants.CORPORATE_WELLBEING);


        new APIProvider.PaymentPackageExpiryDetails(packageInfo, AppConstants.BEARER + dataManager.get(AppConstants.HB_USER_TOKEN, ""),
                1, null, null, new API_Response_Listener<Integer>() {
            @Override
            public void onComplete(Integer data, long request_code, String failure_code) {
                if (data != null && data > 0) {

                    callBack.onPaymentCompleted();


                } else if (data != null && data < 0) {

                    callBack.onPaymentNotCompleted();

                }
            }

        }).call();


    }

    public class PicassoImageGetter implements Html.ImageGetter {

        private TextView textView = null;

        public PicassoImageGetter() {

        }

        public PicassoImageGetter(TextView target) {
            textView = target;
        }

        @Override
        public Drawable getDrawable(String source) {
            BitmapDrawablePlaceHolder drawable = new BitmapDrawablePlaceHolder();
            Picasso.get().load(source).into(drawable);
            return drawable;
        }

        private class BitmapDrawablePlaceHolder extends BitmapDrawable implements Target {

            protected Drawable drawable;

            @Override
            public void draw(final Canvas canvas) {
                if (drawable != null) {
                    drawable.draw(canvas);
                }
            }

            public void setDrawable(Drawable drawable) {
                this.drawable = drawable;
                int width = drawable.getIntrinsicWidth();
                int height = drawable.getIntrinsicHeight();
                drawable.setBounds(0, 0, width, height);
                setBounds(0, 0, width, height);
                if (textView != null) {
                    textView.setText(textView.getText());
                }
            }

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                setDrawable(new BitmapDrawable(activity.getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }

        }
    }

}*/
