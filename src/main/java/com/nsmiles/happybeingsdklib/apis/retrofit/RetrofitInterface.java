package com.nsmiles.happybeingsdklib.apis.retrofit;


import com.nsmiles.happybeingsdklib.NatureCalm.NatureCalmRootModel;

import androidx.annotation.NonNull;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by nSmiles on 10/23/2017.
 */

public interface RetrofitInterface {

    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjU5MjUyZTZjMTIzNTRiZWQ4ZTliNzUwZSIsImlhdCI6MTQ5NTY5OTY4MH0.Z7IIk8ck1xbNvtiH42Z992kBkqOVvi7UZ0s3rzEG1u8"
    })
    @GET
   // @GET("files/Node-Android-Chat.zip")
    @Streaming
    Call<ResponseBody> downloadFile(@Url String sub_url);


    @NonNull
    @GET("v2/naturecalmlist")
    Call<NatureCalmRootModel> getNatureCalmImage(

    );
}
