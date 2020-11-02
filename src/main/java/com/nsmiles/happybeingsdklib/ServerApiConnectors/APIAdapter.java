package com.nsmiles.happybeingsdklib.ServerApiConnectors;

import android.util.Log;

import com.nsmiles.happybeingsdklib.Utils.AppConstants;

import org.json.JSONObject;

public abstract class APIAdapter<T, L> {

    public static int CHILD_MAIL = 10;
    public static int PARENT_MAIL = 11;

    public static final String CONNECTION_FAILED = "1000";
    public static int INVALID_RESPONSE = 1001;
    private static final String NO_FAILURE = "-1";


    public static int NO_FILE_TO_LOAD = 1002;

    private final T input_data;
    private final long requestCode;
    private final String token;
    private String filepath;
    private String fileKey;
    private String url;
    private final API_Response_Listener<L> listener;

    APIAdapter(T input_data, String token, long requestCode, final API_Response_Listener<L> listener) {
        this.input_data = input_data;
        this.requestCode = requestCode;
        this.listener = listener;
        this.token = token;
    }

    APIAdapter(T input_data, String url, String token, long requestCode, final API_Response_Listener<L> listener) {
        this.input_data = input_data;
        this.requestCode = requestCode;
        this.listener = listener;
        this.url = url;
        this.token = token;
    }

    APIAdapter(T input_data, String token, long requestCode, String filename, String fileKey, final API_Response_Listener<L> listener) {
        this.input_data = input_data;
        this.filepath = filename;
        this.requestCode = requestCode;
        this.listener = listener;
        this.fileKey = fileKey;
        this.token = token;
    }

    public void call() {

        String url = getURL();
        String http_method = getHttpMethod();
        JSONObject params = null;
        String getToken = getToken();

        Log.e("input_data", input_data.toString());
        Log.e("url", url);

        if (http_method.equalsIgnoreCase("get") ) {
            if (url.contains("api.typeform.com")) {
                url = url + input_data.toString();
            } else {
                url = url + "?" + input_data.toString();
                params = null;
            }
        }else {
            params = convertDataToJSON(input_data);
        }

        Log.e("updated url", url);
        Log.e("http_method", http_method);

        //Log.d("APIAdapter_input" , input_data.toString());
        ServerHandlerListener serverListener = new ServerHandlerListener() {
            @Override
            public void onSuccess(long requestCode, String result) {
                postProcess();
                L result_data = convertJSONToData(result);
                listener.onComplete(result_data, requestCode, NO_FAILURE);
            }

            @Override
            public void onError(long requestCode, String error) {
                postProcess();
                String failure_code = intrepret_error(error);
                listener.onComplete(null, requestCode, failure_code);
            }

            @Override
            public void onFailure(long requestCode, String errorCode) {
                postProcess();
                listener.onComplete(null, requestCode, CONNECTION_FAILED);
            }
        };

        try {
            ServerHandler server;

            if (filepath != null) {
                if(getToken.length()<1){
                    getToken= AppConstants.DEFAULT_TOKEN;
                }
                server = new ServerHandler(url, getToken, params, filepath, fileKey, serverListener);
            } else {
                if(getToken.length()<1){
                    getToken= AppConstants.DEFAULT_TOKEN;
                }
                server = new ServerHandler(url, getToken, http_method, params, serverListener);
            }

            preProcess();
            server.call(requestCode);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract String getURL();

    public String getToken()
    {
        return AppConstants.DEFAULT_TOKEN;
    }

    public String getHttpMethod() {
        return "GET";
    }

    public JSONObject convertDataToJSON(T data) {
        return null;
    }

    public L convertJSONToData(String response) {
        return null;
    }

    public String intrepret_error(String error) {
        return NO_FAILURE;
    }

    public void preProcess() {
        /* DO nothing */
    }

    public void postProcess() {
        /* DO Nothing */
    }
}
