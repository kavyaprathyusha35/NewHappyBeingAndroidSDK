package com.nsmiles.happybeingsdklib.ServerApiConnectors;

/**
 * Created by Bala on 6/30/2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public interface API_Response_Listener<T> {
    void onComplete(T data, long request_code, String failure_code);
}
