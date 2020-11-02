package com.nsmiles.happybeingsdklib.broadcast;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.MySql;
import com.nsmiles.happybeingsdklib.dagger.application.BaseApplication;
import com.nsmiles.happybeingsdklib.network.NetworkError;
import com.nsmiles.happybeingsdklib.network.Service;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.CorporateSuccess;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.answermodel.AssessmentJsonModel;

import java.lang.reflect.Type;

import javax.inject.Inject;

import rx.Subscription;
/**
 * Created by Sukumar on 11/2/2018.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    private Context context;
    private String assessment_name;
    private String id;
    @Inject
    Service service;
    private ConnectivityReceiverListener mConnectivityReceiverListener;
    private boolean isInBackground;

    public NetworkChangeReceiver( ConnectivityReceiverListener listener) {
        mConnectivityReceiverListener = listener;

    }
    public NetworkChangeReceiver( ) {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("NetworkConnectivity", "******Reconnected to receiver*****" +intent.getAction());
      //  Toast.makeText(context, "On reconnected  to network", Toast.LENGTH_LONG).show();
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            this.context = context;
            ((BaseApplication) context.getApplicationContext()).getmApplicationApiComponent().inject(this);
            //TODO: add dbsync here
            if (CommonUtils.isNetworkAvailable(context)) {
                if(!ifAppIsInBackGround()) {
                    Log.d("networkAvailable", "dbSyncCheck" + isInBackground);
                    Intent i = new Intent(context, DBSync.class);
                    context.sendBroadcast(i);
                    checklocalDbForUploadingAnswers();
                }
            }
        }
    }


    public static boolean ifAppIsInBackGround() {
        ActivityManager.RunningAppProcessInfo myProcess = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(myProcess);
        return myProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
        //kdkffs
    }

    private void checklocalDbForUploadingAnswers() {

        MySql dbHelper = new MySql(context, "mydb", null, MySql.version);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Assessment_Answers_Table", null);
        String assessment_Questions = "";
        Log.i("NetworkConnectivity", "Cursor count is "+cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                id = cursor.getString(cursor.getColumnIndex("_ID"));
                assessment_name = cursor.getString(cursor.getColumnIndex("ASSESSMENT_NAME"));
                String assessment_answers = cursor.getString(cursor.getColumnIndex("ANSWER_LIST"));
                String service_string = cursor.getString(cursor.getColumnIndex("SERVICE_OBJECT"));
                String hb_token = cursor.getString(cursor.getColumnIndex("TOKEN"));
                Type type = new TypeToken<AssessmentJsonModel>() {}.getType();
                Gson gson = new Gson();
                AssessmentJsonModel assessmentJsonModel = gson.fromJson(assessment_answers, type);
                  uploadToServer(service, assessmentJsonModel, id, hb_token);
                cursor.moveToNext();
            }

        }
        cursor.close();
        db.close();
    }

    private void uploadToServer(Service service, AssessmentJsonModel assessmentJsonModel, final String id, String token) {

            Subscription subscription = service.generateAssessmentReportApi(AppConstants.BEARER + token,
                    assessmentJsonModel, new Service.GenerateReportCallBack() {
                        @Override
                        public void onSuccess(CorporateSuccess corporateSuccess) {

                            if (corporateSuccess != null) {
                                MySql dbHelper = new MySql(context, "mydb", null, MySql.version);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                db.delete("Assessment_Answers_Table", "_ID=?", new String[]{id});
                                Log.i("NetworkConnectivity", "deleted the uploaded entry");
                            }

                        }

                        @Override
                        public void onError(NetworkError networkError) {

                        }

                        @Override
                        public void onSuccessError(String errorMessage) {
                        }

                    });

            }


    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }

}
