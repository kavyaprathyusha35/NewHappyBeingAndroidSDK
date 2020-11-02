package com.nsmiles.happybeingsdklib.apis.retrofit;

import android.util.Log;

import com.nsmiles.happybeingsdklib.Utils.AppConstants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitGenrater {


    private static Retrofit retrofit(){
        return new Retrofit.Builder()
                .baseUrl("https://api.nsmiles.com/")
                .client(okHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }




    private static OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder()
                .addInterceptor(headerAuthorizationInterceptor())
                .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
                .build();
    }




    private static Interceptor headerAuthorizationInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();



                Request.Builder requestBuilder = request.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-type", "application/json;charset=utf-8")
                        .addHeader("Authorization","Bearer "+ AppConstants.DEFAULT_TOKEN);
                Request orginal = requestBuilder.build();

                return chain.proceed(orginal);

            }
        };
    }

    private static HttpLoggingInterceptor httpLoggingInterceptor ()
    {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor( new HttpLoggingInterceptor.Logger()
                {
                    @Override
                    public void log (String message)
                    {
                        Log.d("DDD", "log: http log: " + message);
                    }
                } );
        httpLoggingInterceptor.setLevel( HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }


    public static RetrofitInterface getApi(){
        return retrofit().create(RetrofitInterface.class);
    }

}
