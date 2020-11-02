package com.nsmiles.happybeingsdklib.wellbeingassessment.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.dagger.data.HappyUtils;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel.Option;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel.Question;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Sukumar on 5/10/2018.
 */

public class NSmilesAssessmentAdapter extends
        RecyclerView.Adapter<NSmilesAssessmentAdapter.ViewHolder>
        implements Animation.AnimationListener {

    Activity activity;
    Context context;
    List<Question> questionList;
    AssessmentOnClickListener assessmentOnClickListener;
    int pos = 0, qus_pos, opt_pos = 0;
    LinearLayout.LayoutParams layoutParams;
    Animation animBlink, from_right, from_left;
    int margin_eight = 8;
    int margin_four = 4;
    List<Integer> positionList = new ArrayList<>();
    boolean progress = false;
    List<Question> answerValueList;
    List<Question> answerOptionList;
    ArrayList<String> dropDownValueList;
    ArrayAdapter<String> spinnerArrayAdapter;
    Question userAnswerAndValue;

    /*CheckBox*/
    List<Question> nameList;
    List<Question> valueList;
    Question name;
    Question value;
    boolean firstTimeClicked = false;
    boolean isOptionContainsOther = false;

    /*CheckBox*/

    LinearLayout.LayoutParams subQuestionHorizontal;
    LinearLayout.LayoutParams dropDownHorizontal;
    LinearLayout.LayoutParams hintHorizontal;


    LinearLayout.LayoutParams imageLayout;
    LinearLayout.LayoutParams questionLayout;
    LinearLayout.LayoutParams buttonLayout;
    LinearLayout.LayoutParams typingLayout;
    RadioGroup.LayoutParams checkParams;
    LinearLayout.LayoutParams nextNButtonLayout;
    private List<Option> optionsArray;
    private View swipeButton;
    private View v;
    boolean[] isSeen;

    public void setAssessmentOnClickListener(AssessmentOnClickListener assessmentOnClickListener) {
        this.assessmentOnClickListener = assessmentOnClickListener;
    }

    public NSmilesAssessmentAdapter(Activity activity, Context context, List<Question> questionList) {

        this.activity = activity;
        this.context = context;
        this.questionList = questionList;
        swipeButton=activity.findViewById(R.id.generate_report);
        animBlink = AnimationUtils.loadAnimation(context,
                R.anim.blink);
        animBlink.setAnimationListener(this);
        setHasStableIds(true);
        isSeen = new boolean[questionList.size()];
        imageLayout = new LinearLayout.LayoutParams(
                90,
                90);
        imageLayout.setMargins(0, 0, 0, 20);
        imageLayout.gravity = Gravity.LEFT;

        questionLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        questionLayout.setMargins(8, 8, 8, 8);
        questionLayout.gravity = Gravity.RIGHT;

        buttonLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayout.setMargins(8, 8, 8, 8);
        buttonLayout.gravity = Gravity.RIGHT;


        typingLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        typingLayout.setMargins(8, 8, 8, 8);
        typingLayout.gravity = Gravity.CENTER;


        nextNButtonLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        nextNButtonLayout.setMargins(8, 8, 8, 8);
        nextNButtonLayout.gravity = Gravity.END| Gravity.BOTTOM;

        checkParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        checkParams.setMargins(16, 16, 16, 16);
        checkParams.gravity = Gravity.RIGHT;

        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(margin_four, margin_four, margin_four, margin_four);
        layoutParams.gravity = Gravity.TOP;

        subQuestionHorizontal = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        subQuestionHorizontal.setMargins(margin_four, margin_eight, margin_four, margin_eight);


        dropDownHorizontal = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        dropDownHorizontal.setMargins(margin_four, margin_eight, margin_four, margin_eight);

        hintHorizontal = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        hintHorizontal.setMargins(margin_four, margin_eight, margin_four, margin_eight);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_nsmiles_form, parent, false);
        v=view;
        return new NSmilesAssessmentAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (isSeen[position]){
            Log.d("**isSeen", String.valueOf(isSeen[position])+position);
            assessmentOnClickListener.showNext();
        }
        else {
            Log.d("**isSeen", String.valueOf(isSeen[position])+position);
            assessmentOnClickListener.hideNext();
        }
        try {

            if (!positionList.contains(position)) {


                from_left = AnimationUtils.loadAnimation(context, R.anim.from_left);
                from_left.setAnimationListener(this);

                from_right = AnimationUtils.loadAnimation(context,
                        R.anim.from_right);
                from_right.setAnimationListener(this);

                holder.subQuestionHorizontal = new LinearLayout(context);
                holder.subQuestionHorizontal.setGravity(Gravity.BOTTOM);
                holder.subQuestionHorizontal.setOrientation(LinearLayout.HORIZONTAL);

                holder.imageView = new ImageView(context);
                holder.imageView.setLayoutParams(imageLayout);
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.hb_db_tr));
                holder.subQuestionHorizontal.addView(holder.imageView);

                holder.typing = new TextView(context);
                holder.typing.setLayoutParams(typingLayout);
                holder.typing.setText("Typing...");
                holder.typing.setTextColor(context.getResources().getColor(R.color.curated));
                holder.typing.setTextSize(14);
                holder.typing.setGravity(Gravity.CENTER);
                holder.typing.setPadding(16,0,16,0);
                holder.subQuestionHorizontal.addView(holder.typing);
                holder.typing.setVisibility(View.VISIBLE);


                holder.adapterLayout.addView(holder.subQuestionHorizontal);


                holder.questionTextView = new TextView(context);
                holder.questionTextView.setLayoutParams(layoutParams);
                holder.questionTextView.setText(Html.fromHtml(questionList.get(position).getQuestion()));
                holder.questionTextView.setTextColor(context.getResources().getColor(R.color.black));
                holder.questionTextView.setTextSize(22f);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.questionTextView.setBreakStrategy(Layout.BREAK_STRATEGY_SIMPLE);
                }
                holder.questionTextView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4.0f, context.getResources().getDisplayMetrics()), 1.0f);
                holder.adapterLayout.addView(holder.questionTextView);
                holder.questionTextView.setTag(position);
                positionList.add(position);


                /*animation
                 *
                 * */

                holder.imageView.setAnimation(from_left);
                holder.questionTextView.setAnimation(from_right);


                if (questionList.get(position).getType().equalsIgnoreCase("multi")) {

                    holder.multi = new Spinner[questionList.get(position).getOptions().size()];
                    holder.multiSpinner = new Spinner[questionList.get(position).getOptions().size()];


                    for (int d = 0; d < questionList.get(position).getOptions().size(); d++) {

                        holder.subQuestionHorizontal = new LinearLayout(context);
                        holder.subQuestionHorizontal.setGravity(Gravity.CENTER_HORIZONTAL);
                        holder.subQuestionHorizontal.setOrientation(LinearLayout.HORIZONTAL);

                        /*DropDown Option
                         *
                         * */
                        if (questionList.get(position).getOptions().get(d).getType().equalsIgnoreCase("DROPDOWN")) {

                            /*Sub Question Name*/
                            if (questionList.get(position).getOptions().get(d).getName() != null) {
                                holder.subQuestionTextView = new TextView(context);
                                holder.subQuestionTextView.setText(questionList.get(position).getOptions().get(d).getValue());
                                holder.subQuestionTextView.setTextColor(context.getResources().getColor(R.color.curated));
                                holder.subQuestionTextView.setTextSize(18f);
                                holder.subQuestionTextView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4.0f, context.getResources().getDisplayMetrics()), 1.0f);
                                holder.subQuestionTextView.setLayoutParams(subQuestionHorizontal);
                                holder.subQuestionHorizontal.addView(holder.subQuestionTextView);


                            }

                            if (questionList.get(position).getOptions().get(d).getDropdownOptions().size() > 0) {

                                holder.multi[d] = new Spinner(context);
                                holder.multi[d].setPopupBackgroundDrawable(context.getResources().getDrawable(R.drawable.spinner_popup));
                                dropDownValueList = new ArrayList<>();
                                dropDownValueList.addAll(questionList.get(position).getOptions().get(d).getDropdownOptions());
                                spinnerArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_layout, dropDownValueList);
                                holder.multi[d].setAdapter(spinnerArrayAdapter);
                                //holder.multi[d].setSelection(dropDownValueList.size() / 2);
                                holder.multi[d].setLayoutParams(dropDownHorizontal);
                                holder.subQuestionHorizontal.addView(holder.multi[d]);
                                pos = position + 1;
                                holder.multi[d].setId(pos);
                                holder.multi[d].setTag(d);


                                holder.multi[d].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int po, long id) {

                                        int ddd = (int) parent.getTag(); /// option position
                                        int qqq = holder.multi[ddd].getId(); /// question position

                                        if (questionList.get(qqq - 1).getOptions().get(ddd).getDropdownOptions().get(po) != null) {
                                            Log.d("dropDown", String.valueOf(questionList.get(qqq - 1).getOptions().get(ddd).getDropdownOptions().get(po)));


                                            if(questionList.get(position).getUserAnswerValueList().get(ddd)!=null
                                                    && questionList.get(position).getUserAnswerValueList().get(0).getUserAnswerValue().equalsIgnoreCase("")){

                                                questionList.get(position).getUserAnswerValueList().get(ddd)
                                                        .setUserAnswerValue(String.valueOf(questionList.get(qqq - 1)
                                                                .getOptions().get(ddd).getDropdownOptions().get(po)));

                                                assessmentOnClickListener.saveAssessment(questionList, position, false, true, false);
                                                assessmentOnClickListener.hidePrevious();
                                                if (questionList.size() == position + 1) {
                                                    assessmentOnClickListener.showFooterLayout();
                                                    //view.showFooterLayout();
                                                }
                                            }

                                            else {
                                                questionList.get(position).getUserAnswerValueList().get(ddd)
                                                        .setUserAnswerValue(String.valueOf(questionList.get(qqq - 1)
                                                                .getOptions().get(ddd).getDropdownOptions().get(po)));

                                                int pro_count = 0;
                                                for (int p = 0; p < questionList.get(position).getUserAnswerValueList().size(); p++) {

                                                    if (questionList.get(position).getUserAnswerValueList().get(0).getUserAnswerValue() != null) {
                                                        pro_count++;
                                                    }
                                                }

                                                if (pro_count == questionList.get(position).getUserAnswerNameList().size()) {
                                                    progress = true;
                                                } else {
                                                    progress = false;
                                                }
                                                assessmentOnClickListener.saveAssessment(questionList, position, false, false, false);
                                                if (questionList.size() == position + 1) {
                                                    assessmentOnClickListener.showFooterLayout();
                                                    //view.showFooterLayout();
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }

                            if (questionList.get(position).getOptions().get(d).getHint() != null) {
                                holder.hintTextView = new TextView(context);
                                holder.hintTextView.setText(questionList.get(position).getOptions().get(d).getHint());
                                holder.hintTextView.setTextColor(context.getResources().getColor(R.color.curated));
                                holder.hintTextView.setTextSize(18f);
                                holder.hintTextView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4.0f, context.getResources().getDisplayMetrics()), 1.0f);
                                holder.hintTextView.setLayoutParams(hintHorizontal);
                                holder.subQuestionHorizontal.addView(holder.hintTextView);

                            }
                            holder.adapterLayout.addView(holder.subQuestionHorizontal);
                        }

                        /*DropDown Option
                         *
                         * */

                    }

                    holder.button = new Button(context);
                    holder.button.setText("Done");
                    holder.button.setLayoutParams(nextNButtonLayout);
                    holder.button.setTextColor(context.getResources().getColor(R.color.white));
                    holder.button.setBackground(context.getResources().getDrawable(R.drawable.button_radius));
                    holder.button.setGravity(Gravity.CENTER);
                    holder.adapterLayout.addView(holder.button);

                    holder.button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isSeen[position]=true;

                            if (questionList.get(position).getUserAnswerNameList()!= null ||
                                    questionList.get(position).getUserAnswerNameList().size()>1) {
                                assessmentOnClickListener.moveToNext(position);
                            }
                            else {
                                HappyUtils.ShowSnackBar(v,"Choose Height and Weight Both");

                            }
                        }
                    });



                    /*Radio Button Type Question
                     *
                     * */
                } else if (questionList.get(position).getType().equalsIgnoreCase("single")) {


                    RadioGroup.LayoutParams checkParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                    checkParams.setMargins(0, margin_eight, 0, margin_eight);
                    checkParams.gravity = Gravity.START;

                    holder.optionRadioGroup = new RadioGroup(context);
                    holder.optionRadioGroup.setOrientation(RadioGroup.VERTICAL);
                    holder.optionRadioGroup.setLayoutParams(checkParams);

                    if (questionList.get(position).getOptions().size() > 0) {

                        for (int i = 0; i < questionList.get(position).getOptions().size(); i++) {

                            holder.optionRadioButton = new RadioButton(context);
                            holder.optionRadioButton.setLayoutParams(checkParams);
                            holder.optionRadioButton.setMinimumWidth(300);
                            holder.optionRadioButton.setGravity(Gravity.LEFT);
                            holder.optionRadioButton.setBackground(context.getResources().getDrawable(R.drawable.rbtn_selector));
                            holder.optionRadioButton.setButtonDrawable(null);
                            holder.optionRadioButton.setText( questionList.get(position).getOptions().get(i).getValue());
                            //TODO TEMPORARY fix for table questions
                            if (questionList.get(position).getOptions().get(i).getValue().equals("A")) {
                                holder.optionRadioButton.setText("A lot");
                                String question = "In the past month, have you experienced this? Select that is applicable.";
                                question = question + questionList.get(position).getQuestion();
                                holder.questionTextView.setText(Html.fromHtml(question));
                                Log.i("NsmilesAssAdapter", "***** Question text is "+questionList.get(position).getQuestion());
                            }
                            if (questionList.get(position).getOptions().get(i).getValue().equals("B")) {
                                holder.optionRadioButton.setText("A little");
                            }
                            if (questionList.get(position).getOptions().get(i).getValue().equals("C")) {
                                holder.optionRadioButton.setText("Not at all");
                            }
                            holder.optionRadioButton.setTextColor(ContextCompat.getColorStateList(context, R.color.radiobutton_text));
                            holder.optionRadioButton.setTextSize(18f);

                            pos = questionList.get(position).getQNo();
                            pos = position + 1;

                            holder.optionRadioButton.setTag(pos);
                            holder.optionRadioButton.setId(i);
                            holder.optionRadioButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    isSeen[position]=true;
                                    v.getId();
                                    int i=(int)v.getTag();
                                    Log.i("RadioPosition", String.valueOf(i));
                                    try {
                                        // checkedId is the RadioButton selected
                                        RadioButton rb = (RadioButton) v.findViewById(v.getId());
                                        pos = (int) rb.getTag();
                                        Log.i("Radio pos", String.valueOf(pos));

                                        //   char d = rb.getText().toString().trim().charAt(0);


                                        progress = !(questionList.get(pos - 1).getUserAnswerValueList() != null &&
                                                questionList.get(pos - 1).getUserAnswerValueList().get(0).getUserAnswerValue() != null
                                                && !questionList.get(pos - 1).getUserAnswerValueList().get(0).getUserAnswerValue().equalsIgnoreCase(""));

                                        Question userAnswerAndValue;

                                        userAnswerAndValue = new Question();
                                        answerOptionList = new ArrayList<>();
                                        userAnswerAndValue.setUserAnswerName(questionList.get(position).getOptions().get(rb.getId()).getName());
                                        answerOptionList.add(userAnswerAndValue);

                                        userAnswerAndValue = new Question();
                                        answerValueList = new ArrayList<>();
                                        userAnswerAndValue.setUserAnswerValue(questionList.get(position).getOptions().get(rb.getId()).getValue());
                                        answerValueList.add(userAnswerAndValue);

                                        questionList.get(pos - 1).setUserAnswerNameList(answerOptionList);
                                        questionList.get(pos - 1).setUserAnswerValueList(answerValueList);

                                        rb.setVisibility(View.VISIBLE);
                                        rb.startAnimation(animBlink);
                                        assessmentOnClickListener.saveAssessment(questionList, position, true, progress, false);
                                        if (questionList.size() == position + 1) {
                                            assessmentOnClickListener.showFooterLayout();
                                            //view.showFooterLayout();
                                        }


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            holder.optionRadioGroup.addView(holder.optionRadioButton);
                        }

                        holder.adapterLayout.addView(holder.optionRadioGroup);
                        holder.optionRadioGroup.setAnimation(from_right);


                        holder.optionRadioGroup.setOnClickListener(new RadioGroup.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.i("Radio pos", "****In on click listener of radio group"+view.getId());
                            }
                        });
                        holder.optionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {



                            }
                        });
                    }
                }//// else
                /*Radio Button Type Question
                 *
                 * */


                /*CheckBox or Multi type question
                 *
                 * */
                else if (questionList.get(position).getType().equalsIgnoreCase("checkbox")) {


                    holder.optionCheckBox = new CheckBox[questionList.get(position).getOptions().size()];
                    optionsArray=questionList.get(position).getOptions();

                    for (int c = 0; c < optionsArray.size(); c++) {

                        holder.optionCheckBox[c] = new CheckBox(context);
                        layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(2, 2, 2, 2);
                        holder.optionCheckBox[c].setLayoutParams(layoutParams);
                        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            holder.optionCheckBox[c].setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                        }*/
                        Log.d("***optionName",questionList.get(position).getOptions().get(c).getValue());
                        holder.optionCheckBox[c].setText(questionList.get(position).getOptions().get(c).getValue());
                        holder.optionCheckBox[c].setSingleLine(false);
                        holder.optionCheckBox[c].setTextColor(context.getResources().getColor(R.color.curated));
                        holder.optionCheckBox[c].setTextSize(16f);
                        holder.optionCheckBox[c].setButtonTintList(ColorStateList.valueOf(Color.parseColor("#F49C1E")));

                        pos = position + 1;
                        holder.optionCheckBox[c].setId(pos);
                        //holder.optionCheckBox[c].setInputType(c);
                        holder.optionCheckBox[c].setMaxEms(c);
                        holder.adapterLayout.addView(holder.optionCheckBox[c]);
                        holder.optionCheckBox[c].setAnimation(from_right);
                        if (optionsArray.get(c).getValue().equalsIgnoreCase("other")){
                            isOptionContainsOther=true;
                        }

                    }

                    if (isOptionContainsOther){
                        holder.otherEdittext = new EditText(context);
                        holder.otherEdittext.setFocusable(true);
                        holder.otherEdittext.setLayoutParams(layoutParams);
                        holder.otherEdittext.setTextSize(16f);
                        holder.otherEdittext.setBackground(activity.getDrawable(R.drawable.button_button_outer));
                        holder.otherEdittext.setVisibility(View.GONE);
                        holder.otherEdittext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.i("NsmilesAdapter", String.valueOf(v.isFocusableInTouchMode()));
                                holder.otherEdittext.setFocusableInTouchMode(true);

                            }
                        });
                        holder.adapterLayout.addView(holder.otherEdittext);
                    }

                    holder.button = new Button(context);
                    holder.button.setText("Done");
                    holder.button.setLayoutParams(nextNButtonLayout);
                    holder.button.setTextColor(context.getResources().getColor(R.color.white));
                    holder.button.setBackground(context.getResources().getDrawable(R.drawable.button_radius));
                    holder.button.setGravity(Gravity.CENTER);
                    holder.adapterLayout.addView(holder.button);

                    holder.button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isSeen[position]=true;

                            try {
                                if (questionList.get(position).getUserAnswerNameList()!= null && questionList.get(position).getUserAnswerNameList().size()>0) {
                                    assessmentOnClickListener.moveToNext(position);
                                    //assessmentOnClickListener.showFooterLayout();
                                }
                                if ((questionList.size() == position + 1)){
                                    assessmentOnClickListener.showFooterLayout();
                                }
                                else if (isOptionContainsOther && holder.otherEdittext.isShown()){
                                    String userEnteredValue = holder.otherEdittext.getText().toString();

                                    if (holder.otherEdittext != null && !(userEnteredValue.equalsIgnoreCase(""))) {
                                        Log.i("NsmilesAdapter", "User entered value is "+userEnteredValue);
                                        holder.otherEdittext.setFocusable(false);
                                        assessmentOnClickListener.showFooterLayout();

                                    }
                                    else {
                                        HappyUtils.ShowSnackBar(v,"Enter Some value in text box");
                                    }
                                }
                                /*else {
                                    assessmentOnClickListener.showFooterLayout();
                                }*/

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });

                    for (int k = 0; k < questionList.get(position).getOptions().size(); k++) {

                        final int j = k;

                        holder.optionCheckBox[k].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                Log.d("***optionNameChk",holder.optionCheckBox[j].getText().toString());
                                assessmentOnClickListener.hideFooterLayout();

                                qus_pos = holder.optionCheckBox[j].getId();
                                qus_pos--;
                                opt_pos = holder.optionCheckBox[j].getMaxEms();
                                optionsArray=questionList.get(qus_pos).getOptions();

                                if (holder.optionCheckBox[j].isChecked()) {
                                    if (optionsArray.get(opt_pos).getValue().equalsIgnoreCase("None") ||
                                            optionsArray.get(opt_pos).getValue().equalsIgnoreCase("None of these")) {
                                        uncheckRemainingCheeckBoxes(holder.optionCheckBox);
                                    }
                                    else if (questionList.get(qus_pos).getOptions().get(opt_pos).getValue().equalsIgnoreCase("Other")) {
                                        uncheckOthers(holder.optionCheckBox);
                                        holder.otherEdittext.setVisibility(View.VISIBLE);

                                    }
                                    else {
                                        uncheckNoneOrOthersCheckBoxes(holder.optionCheckBox);
                                    }
                                    Log.d("checked   ", qus_pos + "    question Number  " + "  Option Position  " + opt_pos);
                                    Log.d("checked", questionList.get(qus_pos).getOptions().get(opt_pos).getName());
                                    Log.d("checked", questionList.get(qus_pos).getOptions().get(opt_pos).getValue());

                                    if (answerOptionList == null && answerValueList == null) {
                                        answerOptionList = new ArrayList<>();
                                        answerValueList = new ArrayList<>();

                                        userAnswerAndValue = new Question();
                                        userAnswerAndValue.setUserAnswerName(questionList.get(qus_pos).getOptions().get(opt_pos).getName());
                                        answerOptionList.add(userAnswerAndValue);

                                        userAnswerAndValue = new Question();
                                        userAnswerAndValue.setUserAnswerValue(questionList.get(qus_pos).getOptions().get(opt_pos).getValue());
                                        answerValueList.add(userAnswerAndValue);

                                        questionList.get(qus_pos).setUserAnswerNameList(answerOptionList);
                                        questionList.get(qus_pos).setUserAnswerValueList(answerValueList);
                                        assessmentOnClickListener.saveAssessment(questionList, position, false, true, false);
                                        // update progress inserting first value

                                    } else {
                                        // Local answer list not empty. Check the selected question list is empty
                                        // Not empty get value from old list. add newly selected value store in arraylist

                                        if (questionList.get(qus_pos).getUserAnswerNameList() != null
                                                && questionList.get(qus_pos).getUserAnswerNameList().size() > 0
                                                && questionList.get(qus_pos).getUserAnswerValueList() != null
                                                && questionList.get(qus_pos).getUserAnswerValueList().size() > 0) {

                                            valueList = new ArrayList<>();
                                            nameList = new ArrayList<>();
                                            for (int n = 0; n < questionList.get(qus_pos).getUserAnswerNameList().size(); n++) {
                                                name = new Question();
                                                name.setUserAnswerName(questionList.get(qus_pos).getUserAnswerNameList().get(n).getUserAnswerName());
                                                nameList.add(name);
                                            }
                                            name = new Question();
                                            name.setUserAnswerName(questionList.get(qus_pos).getOptions().get(opt_pos).getName());
                                            nameList.add(name);

                                            for (int v = 0; v < questionList.get(qus_pos).getUserAnswerValueList().size(); v++) {
                                                value = new Question();
                                                value.setUserAnswerValue(questionList.get(qus_pos).getUserAnswerValueList().get(v).getUserAnswerValue());
                                                valueList.add(value);
                                            }
                                            value = new Question();
                                            value.setUserAnswerValue(questionList.get(qus_pos).getOptions().get(opt_pos).getValue());
                                            valueList.add(value);


                                            questionList.get(qus_pos).setUserAnswerNameList(nameList);
                                            questionList.get(qus_pos).setUserAnswerValueList(valueList);
                                            assessmentOnClickListener.saveAssessment(questionList, position, false, false, false);
                                            // no need to update progress value already available
                                        }
                                        // Array list is not empty but 2 question array list is empty
                                        // repeat code
                                        else {
                                            answerOptionList = new ArrayList<>();
                                            answerValueList = new ArrayList<>();

                                            userAnswerAndValue = new Question();
                                            userAnswerAndValue.setUserAnswerName(questionList.get(qus_pos).getOptions().get(opt_pos).getName());
                                            answerOptionList.add(userAnswerAndValue);

                                            userAnswerAndValue = new Question();
                                            userAnswerAndValue.setUserAnswerValue(questionList.get(qus_pos).getOptions().get(opt_pos).getValue());
                                            answerValueList.add(userAnswerAndValue);

                                            questionList.get(qus_pos).setUserAnswerNameList(answerOptionList);
                                            questionList.get(qus_pos).setUserAnswerValueList(answerValueList);
                                            assessmentOnClickListener.saveAssessment(questionList, position, false, true, false);
                                            // update progress list is not empty , but value is empty
                                        }

                                    }


                                } else {
                                    ///// Unchecked state
                                    Log.d("un checked   ", qus_pos + "    question Number  " + "  Option Position  " + opt_pos);
                                    Log.d("un checked", questionList.get(qus_pos).getOptions().get(opt_pos).getName());
                                    Log.d("un checked", questionList.get(qus_pos).getOptions().get(opt_pos).getValue());

                                    if (questionList.get(qus_pos).getOptions().get(opt_pos).getValue().equalsIgnoreCase("Other")){
                                        holder.otherEdittext.setVisibility(View.GONE);
                                    }
                                    if (questionList.get(qus_pos).getUserAnswerNameList() != null &&
                                            questionList.get(qus_pos).getUserAnswerNameList().size() > 0
                                            && questionList.get(qus_pos).getUserAnswerValueList() != null &&
                                            questionList.get(qus_pos).getUserAnswerValueList().size() > 0) {
                                        for (int q = 0; q < questionList.get(qus_pos).getUserAnswerNameList().size(); q++) {


                                            if (questionList.get(qus_pos).getUserAnswerNameList().get(q).getUserAnswerName()
                                                    .equalsIgnoreCase(questionList.get(qus_pos).getOptions().get(opt_pos).getName())) {

                                                questionList.get(qus_pos).getUserAnswerNameList().remove(q);
                                                questionList.get(qus_pos).getUserAnswerValueList().remove(q);
                                                break;

                                            }
                                        }

                                        progress = questionList.get(qus_pos).getUserAnswerNameList() == null
                                                || questionList.get(qus_pos).getUserAnswerNameList().size() == 0
                                                || questionList.get(qus_pos).getUserAnswerNameList().get(0).getUserAnswerName().length() == 0;
                                        assessmentOnClickListener.saveAssessment(questionList, position, false, false, progress);
                                    }
                                }
                            }
                        });
                    }
                }

                /*CheckBox or Multi type question
                 *
                 * */
            }




            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    holder.typing.setVisibility(View.GONE);
                }
            },1000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uncheckRemainingCheeckBoxes(CheckBox[] checkBoxes) {
        for (int i = 0; i < checkBoxes.length; i++) {
            Log.i("NsmilesAdapter", "In uncheck class"+checkBoxes[i].getText().toString());
            //Replace R.id.checkbox with the id of CheckBox in your layout
            if (checkBoxes[i].getText().toString().equalsIgnoreCase("None") ||
                    checkBoxes[i].getText().toString().equalsIgnoreCase("None of these")){
                checkBoxes[i].setChecked(true);
            }
            else
                checkBoxes[i].setChecked(false);
        }
    }

    private void uncheckNoneOrOthersCheckBoxes(CheckBox[] checkBoxes) {
        for (int i = 0; i < checkBoxes.length; i++) {
            Log.i("NsmilesAdapter", "In uncheck class"+checkBoxes[i].getText().toString());
            //Replace R.id.checkbox with the id of CheckBox in your layout
            if (checkBoxes[i].getText().toString().equalsIgnoreCase("None") ||
                    checkBoxes[i].getText().toString().equalsIgnoreCase("None of these")){
                checkBoxes[i].setChecked(false);
            }
            /*if (checkBoxes[i].getText().toString().equalsIgnoreCase("Other")) {
                checkBoxes[i].setChecked(false);
            }*/
        }
    }
    private void uncheckOthers(CheckBox[] checkBoxes) {
        for (CheckBox checkBox : checkBoxes) {
            Log.i("NsmilesAdapter", "In uncheck class" + checkBox.getText().toString());
            //Replace R.id.checkbox with the id of CheckBox in your layout
            /*if (checkBoxes[i].getText().toString().equalsIgnoreCase("Other")){
               checkBoxes[i].setChecked(true);
            }
            else*/
            checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == from_right) {
            Log.i("NewAssessment", "In animation ended");
            assessmentOnClickListener.stopBackgroundAnimation();
        }
        else if (animation == animBlink) {
            Log.i("NewAssessment", "blink **( animation ended");
            assessmentOnClickListener.resumeBackgroundAnimation();
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout adapterLayout;
        LinearLayout subQuestionHorizontal;
        TextView questionTextView;
        TextView titleasses;
        RadioButton optionRadioButton;
        RadioGroup optionRadioGroup;
        TextView subQuestionTextView;
        TextView hintTextView;
        Spinner dropDownSpinner;
        CheckBox[] optionCheckBox;
        EditText otherEdittext;
        Spinner multiSpinner[];
        Spinner[] multi;
        Button button;

        private ImageView imageView;
        private TextView typing;

        public ViewHolder(View itemView) {
            super(itemView);
            adapterLayout = (LinearLayout) itemView.findViewById(R.id.adapter_linear_layout);
        }
    }

    public interface AssessmentOnClickListener {

        void saveAssessment(List<Question> questionList,
                            int position,
                            boolean scroll,
                            boolean progress,
                            boolean decrease_progress);

        void moveToNext(int position);

        void hidePrevious();

        void hideNext();

        void showNext();

        void showFooterLayout();
        void hideFooterLayout();
        void stopBackgroundAnimation();
        void resumeBackgroundAnimation();
    }
}
