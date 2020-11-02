package com.nsmiles.happybeingsdklib.ServerApiConnectors;

import android.app.Activity;
import android.app.ProgressDialog;


/**
 * Created by Kavya on 19/3/2017.
 */
@SuppressWarnings("DefaultFileTemplate")
public abstract class WaitingAPIAdapter<T, L> extends APIAdapter<T, L> {

    private ProgressDialog waitDialog;
    private Activity activity;

    public WaitingAPIAdapter(T input_data, String token, long requestCode, Activity activity, String message, API_Response_Listener<L> listener) {
        super(input_data,token, requestCode, listener);
        if(activity != null) {
            waitDialog = new ProgressDialog(activity);
            waitDialog.setMessage(message);
            waitDialog.setCancelable(true);
            waitDialog.setCanceledOnTouchOutside(false);
            this.activity = activity;
            waitDialog.setOwnerActivity(activity);
        }
    }

    public WaitingAPIAdapter(T input_data, String url, String token, long requestCode, Activity activity, String message, API_Response_Listener<L> listener) {
        super(input_data,url,token, requestCode, listener);
        if(activity != null) {
            waitDialog = new ProgressDialog(activity);
            waitDialog.setMessage(message);
            waitDialog.setCancelable(true);
            waitDialog.setCanceledOnTouchOutside(false);
            this.activity = activity;
            waitDialog.setOwnerActivity(activity);
        }
    }



    @Override
    public void preProcess() {
        super.preProcess();
        if(waitDialog != null) {
            waitDialog.show();
        }
    }

    @Override
    public void postProcess() {
        super.postProcess();
        if (activity == null) {
            return;
        }
        if (activity.isFinishing()) {
            return;
        }
        if (waitDialog != null && waitDialog.isShowing()) {
            waitDialog.dismiss();
        }
    }
}
