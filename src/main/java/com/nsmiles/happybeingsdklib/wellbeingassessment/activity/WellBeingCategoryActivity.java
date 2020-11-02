package com.nsmiles.happybeingsdklib.wellbeingassessment.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.dagger.application.BaseApplication;
import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
import com.nsmiles.happybeingsdklib.dagger.data.PreferenceManager;
import com.nsmiles.happybeingsdklib.mycoachfragment.WellBeingCategoryView;
import com.nsmiles.happybeingsdklib.network.Service;
import com.nsmiles.happybeingsdklib.wellbeingassessment.implementation.WellBeingCategoryImplementation;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WellBeingCategoryActivity extends Fragment implements WellBeingCategoryView, View.OnClickListener {


    RecyclerView recycler_view;
    Context context;
    WellBeingCategoryView wellBeingCategoryView;
    WellBeingCategoryImplementation implementation;
    private View progressOverlay;
    private Button combined_report, detail_report_button;
    private TextView screen_name, summary_text;
    private LinearLayout retry_overlay, detail_report_layout;
    private TextView retry_tv;
    private CoordinatorLayout parent_layout;
    private boolean showReport = false;
    boolean isPaid = false;
    @Inject
    DataManager dataManager;

    @Inject
    Service service;
    private String PAYMENT_CATEGORY;
    private boolean is_Login = false;
    private Activity activity;
    private int positionIndex;
    private LinearLayoutManager llm;
    private int topView;
    private int currentVisiblePosition=0;
    private int scrollPosition = 0;
    private boolean shouldKeepScrollPosition = true;
    //AssessmentDetailsActivity assessmentDetailsActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_nsmiles_assessment_category, container, false);
        assert getActivity() != null;
        Log.d("inAssesment","oncreateCalled");

        ((BaseApplication) getActivity().getApplication()).getmApplicationApiComponent().inject(this);
        setPackageName();
        activity = getActivity();
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("IS_LOGIN")) {
            is_Login = bundle.getBoolean("IS_LOGIN");
        }
        renderView(view);
        saveRecycleViewState();
//        assessmentDetailsActivity=new AssessmentDetailsActivity();

        return view;
    }

    private void saveRecycleViewState() {
        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(shouldKeepScrollPosition) {
                    WellBeingCategoryActivity.this.scrollPosition += dy;
                }
            }
        });
    }

    private void renderView(View view) {
        screen_name = view.findViewById(R.id.screen_name);
        combined_report = view.findViewById(R.id.combined_report);
        detail_report_layout = view.findViewById(R.id.detail_report_layout);
        detail_report_button = view.findViewById(R.id.detail_report_button);
        progressOverlay = view.findViewById(R.id.progress_overlay);
        recycler_view =  view.findViewById(R.id.recycler_view);
        summary_text = view.findViewById(R.id.summary_report_not_completed_text);
        parent_layout = view.findViewById(R.id.parent_layout);
        retry_overlay = view.findViewById(R.id.retry_overlay);
        retry_tv = view.findViewById(R.id.retry_tv);


        context = getActivity();
        wellBeingCategoryView = WellBeingCategoryActivity.this;
        combined_report.setOnClickListener(this);
        detail_report_layout.setOnClickListener(this);
       // detail_report_button.setOnClickListener(this);
        retry_tv.setOnClickListener(this);
        implementation = new WellBeingCategoryImplementation(getActivity(), is_Login, dataManager, service, wellBeingCategoryView, recycler_view,WellBeingCategoryActivity.this);
        implementation.initCorporateData();

        if (is_Login) {
/*
            checkAssessmentPaymentStatus(new WellBeingCategoryImplementation.WellBeingCallBack() {
                @Override
                public void onPaymentCompleted() {
                    detail_report_button.setVisibility(View.GONE);
                    combined_report.setText(getString(R.string.view_report));
                    isPaid = true;
                }

                @Override
                public void onPaymentNotCompleted() {
                    detail_report_button.setText(getString(R.string.subscribe_to_get_detail_report));
                    combined_report.setText(getString(R.string.view_plan));
                    isPaid = false;
                }
            });
*/
        }
        PreferenceManager preferenceManager = null;
        try {
            preferenceManager = new PreferenceManager(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean onOverlayClicked = preferenceManager.get(AppConstants.IS_SIGNED_IN, "").equals("true");
        if (onOverlayClicked) {
            screen_name.setVisibility(View.GONE);
        }

    }

    @Override
    public void internetRequired() {
        dataManager.alert(getActivity(), context.getResources().getString(R.string.no_internet_connection),
                context.getResources().getString(R.string.no_network),
                "OK");
    }

    @Override
    public void showProgress() {
        dataManager.progress(progressOverlay, View.VISIBLE, 0.4f, 200);
    }

    @Override
    public void hideProgress() {
        dataManager.progress(progressOverlay, View.GONE, 0f, 0);
    }

    @Override
    public void showErrorMessage(String errorMessage) {

        dataManager.showSnackBarError(parent_layout, errorMessage);
    }

    @Override
    public void showSuccessMessage(String successMessage) {
        dataManager.showSnackBarError(parent_layout, successMessage);
    }

    @Override
    public void showCombinedReportButton() {
        showReport = true;
        combined_report.setClickable(true);
        combined_report.setBackground(getActivity().getDrawable(R.drawable.button_gratitude_style));
        summary_text.setVisibility(View.GONE);
        detail_report_layout.setVisibility(View.VISIBLE);
        //combined_report.setVisibility(View.VISIBLE);
        Log.i("WellbeingCategory", "In show combained report");
    }

    @Override
    public void printTitle(String title) {
       // screen_name.setText(title);
    }

    @Override
    public void showRetry() {
        retry_overlay.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        retry_overlay.setVisibility(View.GONE);
    }

    @Override
    public void disableCombainedReportButton() {
        Log.i("Wellbeing", "In disable touch");
        showReport = false;
        combined_report.setClickable(false);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.combined_report) {
            if (CommonUtils.isNetworkAvailable(activity)) {
                if (showReport) {

                    if (isPaid) {

                        implementation.viewDetailReport();

                    } else {

                        implementation.viewCombinedReport();


                    }


                }
            } else {
                internetRequired();
            }
        } else if (id == R.id.retry_tv) {
            implementation.initCorporateData();

//            case R.id.detail_report_button:
//                HomeScreenActivity.AppFlyerEvent(activity, "Assessment: Detailed report Clicked", "Clicked for detailed report");
//                if (isPaid) {
//                    implementation.viewDetailReport();
//                }
//                else {
//                    startActivityForResult(new Intent(getActivity(), SubscriptionActivity.class)
//                                    .putExtra(AppConstants.SKU_ID, AppConstants.ITEM_SKU_OVERCOME_EXAM_PRESSURE)
//                                    .putExtra(AppConstants.SERVER_SKU_ID, AppConstants.ITEM_SKU_OVERCOME_EXAM_PRESSURE)
//                            , AppConstants.IN_APP_PAYMENT);
//
//                }
/*
                checkAssessmentPaymentStatus(new WellBeingCategoryImplementation.WellBeingCallBack() {
                    @Override
                    public void onPaymentCompleted() {
                        Log.i("WellBeingChanges", "In on payment completed");
                    }

                    @Override
                    public void onPaymentNotCompleted() {


                    }
                });

*/
//                break;
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d("inAssesment","onPauseCalled");
        //currentVisiblePosition = llm.findFirstCompletelyVisibleItemPosition();
        Log.d("inAssesment","onPauseCalled"+currentVisiblePosition);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (is_Login) {
            Log.d("inAssesment","onResumeCalled"+currentVisiblePosition);
            implementation.categoryReportStatus();
            implementation.checkAAssessmentCompletedStatus();
        }
        try {
            //llm.scrollToPosition(9);
            Log.d("inAssesment","onResumeCalled"+currentVisiblePosition);
        } catch (Exception e) {
            e.printStackTrace();
        }

        currentVisiblePosition = 0;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);super.onActivityResult(requestCode, resultCode, data);

        int WELL_BEING_ASSESSMENT_INTENT_REQUEST = 189;
        if (requestCode == WELL_BEING_ASSESSMENT_INTENT_REQUEST && resultCode == Activity.RESULT_OK && data != null) {

            data.getStringExtra(AppConstants.ASSESSMENT);
            data.getStringArrayListExtra(AppConstants.ASSESSMENT_CATEGORY);

            if (data.getStringArrayListExtra(AppConstants.ASSESSMENT_CATEGORY).size() > 0) {

                implementation.categoryReportStatus();
                implementation.checkAAssessmentCompletedStatus();

         //       implementation.showCategoryReport(data.getStringArrayListExtra(AppConstants.ASSESSMENT_CATEGORY));
            }

        }


    }

    @Override
    public void onDestroy() {
        implementation.onDestroy();
        super.onDestroy();

    }

/*
    private void checkAssessmentPaymentStatus(final WellBeingCategoryImplementation.WellBeingCallBack callBack) {
        if (dataManager.isNetworkAvailable(getActivity())) {
            wellBeingCategoryView.showProgress();
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.setEmail(dataManager.get(AppConstants.HB_USER_EMAIL, ""));
            Log.i("WellbeingCategory", "Package name is "+PAYMENT_CATEGORY);
            packageInfo.setPackageName(PAYMENT_CATEGORY);
            packageInfo.setAssessment_name(PAYMENT_CATEGORY);

            new APIProvider.PaymentPackageExpiryDetails(packageInfo, AppConstants.BEARER + dataManager.get(AppConstants.HB_USER_TOKEN, ""),
                    1, null, null, new API_Response_Listener<Integer>() {
                @Override
                public void onComplete(Integer data, long request_code, String failure_code) {
                    Log.i("WellbeingCategory", "Data is "+data);
                    try {
                        if (data != null && data > 0) {
                            callBack.onPaymentCompleted();


                        } else if (data != null && data < 0) {

                            callBack.onPaymentNotCompleted();

                        } else {
                            wellBeingCategoryView.showErrorMessage(getResources().getString(R.string.went_wrong_try_again));
                        }


                    }
                    catch (IllegalStateException ie) {
                        Log.e("Wellbeing", "error");
                    }
                    wellBeingCategoryView.hideProgress();
                }

            }).call();

        }
        else {
            wellBeingCategoryView.internetRequired();
        }
    }
*/


    private void setPackageName() {
/*
        if (dataManager.get(AppConstants.ROLE, "").equalsIgnoreCase(AppConstants.PROFILE_EMPLOYED)) {
            Log.i("WellbingCategory", "Role is "+dataManager.get(AppConstants.HB_SECONDARY_VALUE, "").equalsIgnoreCase("pregnant"));

            if (dataManager.get(AppConstants.HB_SECONDARY_VALUE, "").equalsIgnoreCase("pregnant") ||
                    dataManager.get(AppConstants.HB_SECONDARY_VALUE, "").equalsIgnoreCase("conceive")) {

                PAYMENT_CATEGORY = AppConstants.PREGNANCY_WELLBEING;
            } else {
                PAYMENT_CATEGORY = AppConstants.CORPORATE_WELLBEING;
            }

        }
        else if (dataManager.get(AppConstants.ROLE, "").equalsIgnoreCase(AppConstants.PROFILE_HOME_MAKER)) {

            if (dataManager.get(AppConstants.HB_SECONDARY_VALUE, "").equalsIgnoreCase("pregnant") ||
                    dataManager.get(AppConstants.HB_SECONDARY_VALUE, "").equalsIgnoreCase("conceive")) {

                PAYMENT_CATEGORY = AppConstants.PREGNANCY_WELLBEING;
            } else {
                PAYMENT_CATEGORY = AppConstants.GENERAL_WELLBEING;
            }
        }
*/

    }
}
