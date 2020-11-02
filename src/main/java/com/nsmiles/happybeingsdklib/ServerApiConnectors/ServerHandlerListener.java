package com.nsmiles.happybeingsdklib.ServerApiConnectors;

/**
 * Created by Admin on 23-06-2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public interface ServerHandlerListener {

    void onSuccess(long requestCode, String result);
    void onError(long requestCode, String error); /* API returns an error */
    void onFailure(long requestCode, String errorCode);  /* HTTP response failure */
}
