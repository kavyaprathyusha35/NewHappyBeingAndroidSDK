package com.nsmiles.happybeingsdklib.wellbeingassessment.implementation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nsmiles.happybeingsdklib.Reports.DetailReport.DetailReportActivity;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;
import com.nsmiles.happybeingsdklib.Utils.NetworkService;
import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
import com.nsmiles.happybeingsdklib.mycoachfragment.WellBeingCategoryView;
import com.nsmiles.happybeingsdklib.mycoachfragment.presenter.CommonPresenter;
import com.nsmiles.happybeingsdklib.network.Service;
import com.nsmiles.happybeingsdklib.wellbeingassessment.activity.WellBeingCategoryActivity;
import com.nsmiles.happybeingsdklib.wellbeingassessment.adapter.WellBeingAdapter;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.CorporateModel;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.categorymodel.WellBeingCategoryCategory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
/**
 * Created by Sukumar on 6/7/2018.
 */

public class WellBeingCategoryImplementation extends RecyclerView implements CommonPresenter.WellBeingCategoryPresenter {

    private Activity activity;
    private WellBeingCategoryView view;
    private RecyclerView recyclerView;
    private List<CorporateModel> corporateModelList;
    private List<CorporateModel> finalModelList;
    private String[] area, image_url, title, status_title;
    private WellBeingAdapter generalAdapter;
 //   private AssessmentDetailsActivity assessmentDetailsActivity;
    private Context context;
    private CorporateModel corporateModel;
    private String API_URL, CATEGORY_API_URL, COMPLETED_STATUS_API;
    private String report_API_URL, report_VERSION;
    private int category_size;
    private ArrayList<String> category_name_list;
    private Map<String, ArrayList<String>> category_map;
    private Set<String> assessment_category_group;
    private ArrayList<String> sub_category;
    private boolean checkAllAssessmentCompleted = false;
    public final int WELL_BEING_ASSESSMENT_INTENT_REQUEST = 189;

    Map<String, ArrayList<String>> reportStatusMap = new HashMap<>();
    ArrayList<String> title_list;


    private String WELL_BEING_SKU_ID, WELL_BEING_SERVER_SKU_ID, PAYMENT_CATEGORY;
    private List<WellBeingCategoryCategory> wellBeingCategory;
    private boolean IS_ALL_ASSESSMENTS_COMPLETED = false;
    private String id = "";
    private String titleName = "";
    private boolean isLogin;
    private int mScrollPosition;

    public WellBeingCategoryImplementation(Activity activity, boolean isLogin, DataManager dataManager, Service service, WellBeingCategoryView view, RecyclerView recyclerView, WellBeingCategoryActivity fragment) {
        super(activity);

        Log.i("Assessment", "**In implementation*******");
        this.activity = activity;
        this.view = view;
        this.recyclerView = recyclerView;
        //this.assessmentDetailsActivity=assessmentDetailsActivity;
        this.isLogin = isLogin;
        corporateWellBeing();
    }

    private void corporateWellBeing() {
        titleName = "Corporate wellbeing";
        Log.i("WellbeingCategory", "In set the category id is "+id);
        API_URL = NetworkService.corporateWellBeing.trim();
        CATEGORY_API_URL = NetworkService.corporateWellBeingCategoryStatus+"?id="+id.trim();
        COMPLETED_STATUS_API = NetworkService.corporateAllCompletedStatus.trim();
        PAYMENT_CATEGORY = AppConstants.CORPORATE_WELLBEING;
        report_API_URL = "CORPORATEWELLBEINGV4";
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        LayoutManager layoutManager = getLayoutManager();
        if(layoutManager instanceof LinearLayoutManager){
            mScrollPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        SavedState newState = new SavedState(superState);
        newState.mScrollPosition = mScrollPosition;
        return newState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        if(state instanceof SavedState){
            mScrollPosition = ((SavedState) state).mScrollPosition;
            LayoutManager layoutManager = getLayoutManager();
            if(layoutManager != null){
                int count = layoutManager.getItemCount();
                if(mScrollPosition != RecyclerView.NO_POSITION && mScrollPosition < count){
                    layoutManager.scrollToPosition(mScrollPosition);
                }
            }
        }
    }

    static class SavedState extends android.view.View.BaseSavedState {
        public int mScrollPosition;
        SavedState(Parcel in) {
            super(in);
            mScrollPosition = in.readInt();
        }
        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(mScrollPosition);
        }
        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    public void initCorporateData() {
        callAdapter();
    }

    @Override
    public void callAdapter() {
        Log.i("WellbeingCategory", "****In call adapter****");
        String categoryString = "";
        //TODO: Get the data from DAta base.
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Log.i("OfflineAssessment", "In call adapter");
        MySql dbHelper = new MySql(activity, "mydb", null, MySql.version);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        sub_category = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Assessment_Tiles", null);
        String assmentTiles = "";
        Log.i("WellbeingCategory", "Cursor count "+cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            assmentTiles = cursor.getString(cursor.getColumnIndex("TILES_LIST"));
            Log.i("WellbeingCategory", "Assessmnet title is "+assmentTiles);
            categoryString = cursor.getString(cursor.getColumnIndex("REPORT_CATEGORY"));
           // report_API_URL = cursor.getString(cursor.getColumnIndex("REPORT_NAME"));
            Log.i("WellbeingCategory", "Report url is "+report_API_URL);
            report_VERSION = cursor.getString(cursor.getColumnIndex("REPORT_VERSION"));
            cursor.close();
        }
        else {
            Log.i("WellbeingCategory", "detachFragment");

            /*FragmentTransaction fragmentTransaction = null;
            if (fragment.getFragmentManager() != null) {
                fragmentTransaction = fragment.getFragmentManager().beginTransaction();

            fragmentTransaction.detach(fragment);
            fragmentTransaction.attach(fragment);
            fragmentTransaction.commit();
            }*/
        }
        Type type = new TypeToken<ArrayList<CorporateModel>>() {}.getType();
        Type arrylist_type = new TypeToken<ArrayList<String>>() {}.getType();
        //Log.i("OfflineAssessment", "Assessment tiles are"+assmentTiles);
        Gson gson = new Gson();
        finalModelList = gson.fromJson(assmentTiles, type);
        sub_category = gson.fromJson(categoryString, arrylist_type);
        Log.i("WellBeingChanges", "Getting it from local db "+finalModelList);
        generalAdapter = new WellBeingAdapter(context, finalModelList);
        recyclerView.setAdapter(generalAdapter);
        if (finalModelList != null) {
            Log.i("WellbeingCategory", "FInal model list "+finalModelList);
            title = new String[finalModelList.size()];
            status_title = new String[finalModelList.size()];
            for (int i = 0; i < finalModelList.size(); i++) {
                Log.i("WellbeingCategory", "FInal model list "+finalModelList.get(i));
                title[i] = finalModelList.get(i).getTitle();
                status_title[i] = finalModelList.get(i).getStatus_title();
                Log.i("WellBeingChanges", "Title from the database is "+finalModelList.get(i).getTitle());
                reportStatusMap.put(title[i], sub_category);
            }
        }
        else {
            if (CommonUtils.isNetworkAvailable(activity)) {
                //TODO again call the api for the details
            }
            else {
                Toast.makeText(activity, "Please connect to internet!!!", Toast.LENGTH_SHORT).show();
            }
        }
        db.close();

        categoryReportStatus();
        checkAAssessmentCompletedStatus();


        /*OnClick Callback interface
        * Intent pass the selected category name
        *
        *
        * */
        view.printTitle(titleName);
        generalAdapter.setCardOnClickListener(new WellBeingAdapter.CardOnClickListener() {
//            @Override
//            public void onClickListener(final List<CorporateModel> corporateModelList, final int pos) {
//
//                //Log.i("CheckDB", "Name o assessment"+corporateModelList.get(pos).getArea());
//
//            }



//            @Override
//            public void onStartOrReportButtonClicked(final List<CorporateModel> corporateModelList, final int pos, boolean assessmentCompletedStatus) {
//                Log.i("Wellbeing", "(((((((CLicked here))))))))))))))))))))"+corporateModelList.get(pos).getTitle());
//
//                if (isLogin) {
//                    if (!corporateModelList.get(pos).isAssessmentStatus()) {
//                        if (!dbEntryExists("'"+corporateModelList.get(pos).getArea()+"'")) {
//
//
//                            Bundle bundle = new Bundle();
//                            bundle.putString(AppConstants.FIRE_BASE_DEVICE_ID, AppConstants.DEVICE_ID);
//                            bundle.putString(AppConstants.WELLBEING_START_BUTTON_CLICKD, AppConstants.WELLBEING_START_BUTTON_CLICKD);
//                            firebaseAnalytics.logEvent(AppConstants.WELLBEING_QUESTION_CLICKED + corporateModelList.get(pos).getTitle(), bundle);
//                            firebaseAnalytics.setAnalyticsCollectionEnabled(true);
//                            firebaseAnalytics.setMinimumSessionDuration(20000);
//                            firebaseAnalytics.setSessionTimeoutDuration(500);
//
//
//                            activity.startActivityForResult(new Intent(context, WellBeingAssessmentActivity.class)
//                                    .putExtra(AppConstants.ASSESSMENT, corporateModelList.get(pos).getArea())
//                                    .putExtra(AppConstants.REPORT_SUB_TYPE, report_API_URL)
//                                    .putExtra(AppConstants.ASSESSMENT_VERSION, report_VERSION)
//                                    .putStringArrayListExtra(corporateModelList.get(pos).getArea(),
//                                            corporateModelList.get(pos).getCategory().get(corporateModelList.get(pos).getArea())), WELL_BEING_ASSESSMENT_INTENT_REQUEST);
//                        }
//                        else {
//                            CustomToast.makeText(activity, "Report will be generated once the network is available!!!", Toast.LENGTH_SHORT).show();
//                            //activity.finish();
//                        }
//                    } else {
//                        /*Show Report
//                         *
//                         *
//                         * */
//                        if (CommonUtils.isNetworkAvailable(activity)) {
//                            if (!(corporateModelList.size()-1 == pos))
//
//
//                                activity.startActivity(new Intent(context, SingleWellBeingReportActivity.class)
//                                        .putExtra(AppConstants.REPORT_SUB_TYPE, report_API_URL)
//                                        .putExtra(AppConstants.ASSESSMENT_VERSION, report_VERSION)
//                                        .putExtra(AppConstants.HIDE_RADAR, true)
//                                        .putStringArrayListExtra(AppConstants.SHOW_ALL_PREGNANCY_REPORT, corporateModelList.get(pos).getCategory().get(corporateModelList.get(pos).getArea()))
//
//
//
//
//                                );
//
//                            if(corporateModelList.size()-1 == pos)
//                            {
//                                dataManager.toast(activity, "No report available for general information");
//                            }
//                            else {
//                                Log.i("OfflineAssessment", "IN else "+report_API_URL);
//
//                            }
//
//                        }
//                        else {
//                            CustomToast.makeText(activity, "Internet not available!!! Please check your data network", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//                else {
//                    //activity.startActivity(new Intent(activity, RegisterGardenFragment.class).putExtra("FROM_SCREEN", "ASSESS"));
//                    activity.startActivity(new Intent(activity, RegisterGardenFragment.class));
//                }
//            }

            @Override
            public void onDetailsButtonClicked(List<CorporateModel> corporateModelList, int pos) {

/*
                activity.startActivity(new Intent(activity, AssessmentDetailsActivity.class)
                        .putExtra("TITLE_OF_ASSESSMENT", corporateModelList.get(pos).getTitle())
                        .putExtra("INSTRUCTIONS_OF_ASSESSMENT", corporateModelList.get(pos).getDescription())
                        .putExtra("LIST", (Serializable) finalModelList)
                        .putExtra("POS",pos)
                        .putExtra("isLOGIN",isLogin)
                        .putExtra(AppConstants.REPORT_SUB_TYPE, report_API_URL)
                        .putExtra(AppConstants.ASSESSMENT_VERSION, report_VERSION)
                        .putExtra(AppConstants.ASSESSMENT, corporateModelList.get(pos).getArea()));
*/


            }

//            @Override
//            public void getFirstCardSize(int card_size, int list_size) {
//                recyclerView.setMinimumHeight((card_size*list_size)+80);
//
//            }
        });


    }





    private boolean dbEntryExists(String nameOfAssessment) {
        MySql dbHelper = new MySql(activity, "mydb", null, MySql.version);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Assessment_Answers_Table where ASSESSMENT_SUB_TYPE = "+nameOfAssessment, null);
        if (cursor.getCount() > 0) {
            db.close();
            return true;
        }
        else {
            db.close();
            return false;
        }
    }

    @Override
    public void showCategoryReport(ArrayList<String> category) {
        for (int i = 0; i < finalModelList.size(); i++) {
            finalModelList.get(i).setAssessmentStatus(true);
            corporateModelList.get(i).setAssessmentStatus(true);

            generalAdapter.notifyItemChanged(i);
        }
/*
        activity.startActivity(new Intent(context, SingleWellBeingReportActivity.class)
                */
/*Report Sub URL *
                * *//*

                .putExtra(AppConstants.REPORT_SUB_TYPE, report_API_URL)

                */
/*Report Version Number
                *//*

                .putExtra(AppConstants.ASSESSMENT_VERSION, report_VERSION)

                */
/*Report WellBeingCategoryCategory List *
                * *//*

                .putExtra(AppConstants.HIDE_RADAR, true)
                .putStringArrayListExtra(AppConstants.SHOW_ALL_PREGNANCY_REPORT, category));
*/
    }


    @Override
    public void viewCombinedReport() {

        /*Get Combined Report
        * Combined report mean, it fetch all category report
        * Pass all the category name in intent
        *
        *
        *
        * */

        //Log.i("WellbingCategory","Sub category is "+sub_category.size());
        for (int i =  0; i < sub_category.size(); i++) {
            Log.i("WellbingCategory", "Sub category name is "+sub_category.get(i));
        }
/*
        if (sub_category != null && sub_category.size() > 0) {
            activity.startActivity(new Intent(context, SingleWellBeingReportActivity.class)
                    .putExtra(AppConstants.REPORT_SUB_TYPE, report_API_URL)
                    .putExtra(AppConstants.ASSESSMENT_VERSION, report_VERSION)
                    .putStringArrayListExtra(AppConstants.SHOW_ALL_PREGNANCY_REPORT, sub_category));

        }
*/
    }

    @Override
    public void viewDetailReport() {
        for (int i =  0; i < sub_category.size(); i++) {
            Log.i("WellbingCategory", "Sub category name is "+sub_category.get(i));
        }
        if (sub_category != null && sub_category.size() > 0) {
            activity.startActivity(new Intent(context, DetailReportActivity.class)
                    .putExtra(AppConstants.REPORT_SUB_TYPE, report_API_URL)
                    .putExtra(AppConstants.ASSESSMENT_VERSION, report_VERSION)
                    .putStringArrayListExtra(AppConstants.SHOW_ALL_PREGNANCY_REPORT, sub_category));

        }

    }


    @Override
    public void onDestroy() {
    }


    @Override
    public void categoryReportStatus() {

        view.showProgress();
        Log.i("WellBeingChanges", "Category  API"+CATEGORY_API_URL);

/*
        new ApiProvider.getWellBeingCategoryStatus(AppConstants.BEARER + dataManager.get(AppConstants.HB_USER_TOKEN, ""),
                CATEGORY_API_URL, new Service.WellBeingCategoryStatusCheckCallBack() {
                    @Override
                    public void onSuccess(WellBeingCategoryStatusModel wellBeingCategoryStatusModel) {
                        if (wellBeingCategoryStatusModel != null && wellBeingCategoryStatusModel.getSuccess() != null) {
                            Log.i("WellBeingChanges", "In On success of status api");
                            view.hideProgress();
                            // reportStatusMap
*/
/*
                            for (int i = 0; i < wellBeingCategoryStatusModel.getSuccess().getCategory().size(); i++) {
                                //Log.i("WellBeingChanges", "name is "+wellBeingCategoryStatusModel.getSuccess().getCategory().get(i).getName());
                                Log.i("WellBeingChanges", "value is "+wellBeingCategoryStatusModel.getSuccess().getCategory().get(i).getStatus());
                            }
*//*


                            wellBeingCategory = wellBeingCategoryStatusModel.getSuccess().getCategory();
                            for (int i = 0; i < wellBeingCategory.size(); i++) {
                                Log.i("WellBeingChanges", "In first for loop "+wellBeingCategory.size());
                                for (int j = 0; j < status_title.length; j++) {
                                    Log.i("WellBeingChanges", "category name is "+wellBeingCategory.get(i).getName());
                                    Log.i("WellBeingChanges", "title is "+title[j]);
                                    Log.i("WellBeingChanges", "****In Wellbeing category name is **** "+wellBeingCategory.get(i).getName());
                                    Log.i("WellBeingChanges", "****In Wellbeing category Title  is **** "+title[j]);
                                    Log.i("WellBeingChanges", "****In Wellbeing category status is **** "+wellBeingCategory.get(i).getStatus());
                                    if (wellBeingCategory.get(i).getName().equals(status_title[j])) {
                                        Log.i("WellBeingChanges", "****In condition success**** "+wellBeingCategory.get(i).getStatus());
                                        finalModelList.get(j).setAssessmentStatus(wellBeingCategory.get(i).getStatus());
                                        generalAdapter.notifyItemChanged(j);
                                        break;
                                    }
                                }
                            }
                            */
/*
                            for (Map.Entry<String, ArrayList<String>> entry : reportStatusMap.entrySet()) {
                                Log.i("WellBeingChanges","In category for loop 1"+entry.getKey() + " = " + entry.getValue());


                                for (int i = 0; i < wellBeingCategoryStatusModel.getSuccess().getCategory().size(); i++) {
                                    Log.i("WellBeingChanges", "In category for loop 2"+entry.getValue().get(0));
                                    if (entry.getValue().get(0).equalsIgnoreCase(wellBeingCategoryStatusModel.getSuccess().getCategory().get(i).getName())) {

                                        Log.i("WellBeingChanges", "in if true");
                                        for (int j = 0; j < title.length; j++) {
                                            Log.i("WellBeingChanges", "in for loop 3 true"+entry.getKey()+" and title is "+title[j]);
                                            if (entry.getKey().equalsIgnoreCase(title[j])) {
                                                //corporateModelList.get(j).setAssessmentStatus(true);
                                                finalModelList.get(j).setAssessmentStatus(true);
                                                Log.i("WellBeingChanges", "In changing status of tiles"+finalModelList.get(j).isAssessmentStatus());
                                                generalAdapter.notifyItemChanged(j);
                                                break;
                                            }
                                        }

                                        break;
                                    }

                                }


                            }
*//*



                        }
                    }


                    @Override
                    public void onError(NetworkError networkError) {

                       // dataManager.toast(activity, networkError.getMessage());
                        view.hideProgress();
                    }
                });
*/

    }

    @Override
    public void categoryReportStatuss() {

    }

    @Override
    public void checkAAssessmentCompletedStatus() {

        view.showProgress();
        Log.i("WellBeingChanges", "Completed status API"+COMPLETED_STATUS_API);
/*
        Subscription subscription = service.getWellBeingAllCompletedStatus(AppConstants.BEARER + dataManager.get(AppConstants.HB_USER_TOKEN, ""),
                COMPLETED_STATUS_API, new Service.WellBeingAllCompeltedCallBack() {
                    @Override
                    public void onSuccess(AssessmentCompletedStatus assessmentCompletedStatus) {

                        if (assessmentCompletedStatus.getSuccess().getPregnancywellbeingExists() != null
                                && assessmentCompletedStatus.getSuccess().getPregnancywellbeingExists() == 1) {
                            IS_ALL_ASSESSMENTS_COMPLETED = true;

                        } else if (assessmentCompletedStatus.getSuccess().getWellbeingExists() != null
                                && assessmentCompletedStatus.getSuccess().getWellbeingExists() == 1) {
                            IS_ALL_ASSESSMENTS_COMPLETED = true;
                        }
                        else if(assessmentCompletedStatus.getSuccess().getCorporatewellbeingExists() != null
                            && assessmentCompletedStatus.getSuccess().getCorporatewellbeingExists() == 1){
                            IS_ALL_ASSESSMENTS_COMPLETED = true;
                        }
                        else {
                            //disable touch on Summary report Button
                            view.disableCombainedReportButton();
                        }
                        Log.i("Wellbeing", "Is all completed is "+IS_ALL_ASSESSMENTS_COMPLETED);
                        if (IS_ALL_ASSESSMENTS_COMPLETED) {
                            view.showCombinedReportButton();
                        }
                        view.hideProgress();
                    }

                    @Override
                    public void onError(NetworkError networkError) {

                        view.hideProgress();
                    }
                });
*/

    }

    @Override
    public void checkAssessmentPaymentStatus(final WellBeingCategoryImplementation.WellBeingCallBack callBack) {


/*
        if (dataManager.isNetworkAvailable(activity)) {
            view.showProgress();
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

        }
*/
/*
        else {
            view.internetRequired();
        }
*/
    }


    public interface WellBeingCallBack {

        void onPaymentCompleted();

        void onPaymentNotCompleted();
    }
}
