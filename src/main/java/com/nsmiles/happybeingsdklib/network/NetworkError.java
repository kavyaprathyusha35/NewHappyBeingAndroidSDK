package com.nsmiles.happybeingsdklib.network;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit2.HttpException;

import static java.net.HttpURLConnection.HTTP_BAD_GATEWAY;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_GATEWAY_TIMEOUT;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_SERVER_ERROR;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static java.net.HttpURLConnection.HTTP_UNAVAILABLE;

public class NetworkError extends Throwable {
    public static final String DEFAULT_ERROR_MESSAGE = "Something went wrong! Please try again.";
    public static final String NETWORK_ERROR_MESSAGE = "No Internet Connection!";
    private static final String ERROR_MESSAGE_HEADER = "Error-Message";
    private final Throwable error;

    public NetworkError(Throwable e) {
        super(e);
        this.error = e;
    }

    public String getMessage() {
        return error.getMessage();
    }

    public String errorCode() {

        String err;


        if (error instanceof HttpException && ((HttpException) error).code() == HTTP_BAD_REQUEST) {

            err = "  Bad Request " + error.getMessage();
        } else if (error instanceof HttpException && ((HttpException) error).code() == HTTP_UNAUTHORIZED) {

            err = "  Unauthorized  " + error.getMessage();
        } else if (error instanceof HttpException && ((HttpException) error).code() == HTTP_FORBIDDEN) {

            err = "  Forbidden " + error.getMessage();
        } else if (error instanceof HttpException && ((HttpException) error).code() == HTTP_NOT_FOUND) {

            err = "  Not Found  " + error.getMessage();
        } else if (error instanceof HttpException && ((HttpException) error).code() == HTTP_SERVER_ERROR) {

            err = "  Internal Server Error  " + error.getMessage();
        } else if (error instanceof HttpException && ((HttpException) error).code() == HTTP_BAD_GATEWAY) {

            err = "  Bad Gateway  " + error.getMessage();
        } else if (error instanceof HttpException && ((HttpException) error).code() == HTTP_UNAVAILABLE) {

            err = "  Service Unavailable  " + error.getMessage();
        } else if (error instanceof HttpException && ((HttpException) error).code() == HTTP_GATEWAY_TIMEOUT) {

            err = "  Gateway Timeout  " + error.getMessage();
        }
        else {
            err = error.getMessage();
        }

        return err;
    }


    public boolean isAuthFailure() {
        return error instanceof HttpException &&
                ((HttpException) error).code() == HTTP_UNAUTHORIZED;
    }

    public boolean isResponseNull() {
        return error instanceof HttpException && ((HttpException) error).response() == null;
    }

    public String getAppErrorMessage() {
        if (this.error instanceof IOException) return NETWORK_ERROR_MESSAGE;
        if (!(this.error instanceof HttpException)) return DEFAULT_ERROR_MESSAGE;
        retrofit2.Response<?> response = ((HttpException) this.error).response();
        if (response != null) {
            String status = getJsonStringFromResponse(response);
            if (!TextUtils.isEmpty(status)) return status;

            Map<String, List<String>> headers = response.headers().toMultimap();
            if (headers.containsKey(ERROR_MESSAGE_HEADER))
                return headers.get(ERROR_MESSAGE_HEADER).get(0);
        }

        return DEFAULT_ERROR_MESSAGE;
    }

    protected String getJsonStringFromResponse(final retrofit2.Response<?> response) {
        try {
            String jsonString = response.errorBody().string();
            Response errorResponse = new Gson().fromJson(jsonString, Response.class);
            return errorResponse.status;
        } catch (Exception e) {
            return null;
        }
    }

    public Throwable getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NetworkError that = (NetworkError) o;

        return error != null ? error.equals(that.error) : that.error == null;

    }

    @Override
    public int hashCode() {
        return error != null ? error.hashCode() : 0;
    }
}
