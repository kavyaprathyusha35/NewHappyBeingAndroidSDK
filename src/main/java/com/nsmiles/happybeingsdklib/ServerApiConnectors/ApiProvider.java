package com.nsmiles.happybeingsdklib.ServerApiConnectors;

import android.app.Activity;
import android.util.Log;

import com.nsmiles.happybeingsdklib.Affimations.AffirmationModel;
import com.nsmiles.happybeingsdklib.MindGym.AddEmotionRequest;
import com.nsmiles.happybeingsdklib.Models.PackageInfo;
import com.nsmiles.happybeingsdklib.MyCoach.CoachModel;
import com.nsmiles.happybeingsdklib.Registration.AuthModel;
import com.nsmiles.happybeingsdklib.Registration.AuthSuccessModel;
import com.nsmiles.happybeingsdklib.UI.gratitude.SelfLoveData;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.NetworkModule;
import com.nsmiles.happybeingsdklib.Utils.SdkPreferenceManager;
import com.nsmiles.happybeingsdklib.Utils.StatusInteface;
import com.nsmiles.happybeingsdklib.playaudio.MindGymModel;

import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Kavya on 9/21/2020.
 */

public class ApiProvider {
    public static String MOBILE_NUMBER_EXISTS = "MOBILE_NUMBER_EXISTS";
    public static String EMAIL_EXISTS = "EMAIL_EXISTS";

    public static void authorize(final Activity activity, AuthModel authModel, final StatusInteface statusInteface) {

        final SdkPreferenceManager sdkPreferenceManager = new SdkPreferenceManager(activity);
        Call<AuthSuccessModel> registerUser = NetworkModule.apiInterface.registerUser("", authModel);
        registerUser.enqueue(new Callback<AuthSuccessModel>() {
            @Override
            public void onResponse(Call<AuthSuccessModel> call, Response<AuthSuccessModel> authSuccessModel) {
                if (activity != null) {

                    if (authSuccessModel.isSuccessful() && authSuccessModel.body() != null && authSuccessModel.body().getResult() != null) {
                        sdkPreferenceManager.save(AppConstants.SDK_NAME, authSuccessModel.body().getResult().getName());
                        sdkPreferenceManager.save(AppConstants.SDK_EMAIL, authSuccessModel.body().getResult().getEmailId());
                        sdkPreferenceManager.save(AppConstants.SDK_ACCESS_TOKEN, authSuccessModel.body().getResult().getAccessToken());
                        sdkPreferenceManager.save(AppConstants.SDK_REFRESH_TOKEN, authSuccessModel.body().getResult().getRefreshToken());
                        sdkPreferenceManager.save(AppConstants.SDK_PROFILE_ID, authSuccessModel.body().getResult().getUserProfileId());
                        sdkPreferenceManager.save(AppConstants.SDK_CUSTOMER_ID, authSuccessModel.body().getResult().getCustomerId());
                        String expiry_date = CommonUtils.getDateFormat(authSuccessModel.body().getResult().getExpiresAt());
                        sdkPreferenceManager.save(AppConstants.SDK_EXPIRY_AT, expiry_date);
                        sdkPreferenceManager.save(AppConstants.SDK_LOGIN_STATUS, authSuccessModel.body().getSuccess());

                        statusInteface.onAuthorizationComplete(activity);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<AuthSuccessModel> authSuccessModel, Throwable t) {
                Log.i(TAG, "onFailure: auth failed: " + t.getMessage());
            }
        });

    }
    public static class SaveAffirmationData extends WaitingAPIAdapter<AffirmationModel, String> {
        public final AffirmationModel data;
        private final String token;

        public SaveAffirmationData(AffirmationModel input_data, String token, long requestCode, API_Response_Listener<String> listener) {
            super(input_data, token, requestCode, null, null, listener);
            this.data = input_data;
            this.token = token;
        }

        @Override
        public String getURL() {
            return Urls.getSaveMyCoachUrl();
        }

        @Override
        public String getHttpMethod() {
            return "POST";
        }

        @Override
        public JSONObject convertDataToJSON(AffirmationModel data) {
            return JSONParser.getJsonForSaveAffirmation(data);
        }

        @Override
        public String getToken() {
            return token;
        }

        @Override
        public String convertJSONToData(String response) {
            return JSONParser.getDataForSaveMyCoach(response).getResponse();
        }

        @Override
        public String intrepret_error(String error) {
            //Log.i("ApiProvider","Error is "+error);
            return error;
        }
    }

    public static class SaveRelaxAudioData extends WaitingAPIAdapter<CoachModel, String> {
        public final CoachModel data;
        private final String token;

        public SaveRelaxAudioData(CoachModel input_data, String token, long requestCode, API_Response_Listener<String> listener) {
            super(input_data, token, requestCode, null, null, listener);
            this.data = input_data;
            this.token = token;
        }

        @Override
        public String getURL() {
            return Urls.getSaveRelaxUrl();
        }

        @Override
        public String getHttpMethod() {
            return "POST";
        }

        @Override
        public JSONObject convertDataToJSON(CoachModel data) {
            Log.i("APiProvider","Json object for save relax is  "+JSONParser.getJsonForSaveRelaxAudio(data));
            return JSONParser.getJsonForSaveRelaxAudio(data);
        }

        @Override
        public String getToken() {
            return token;
        }

        @Override
        public String convertJSONToData(String response) {
            Log.i("APiProvider","Response for save relax is  "+response);
            return JSONParser.getDataForSaveMyCoach(response).getResponse();
        }

        @Override
        public String intrepret_error(String error) {
            //Log.i("ApiProvider","Error is "+error);
            return error;
        }
    }

    public static class SaveEmotions extends WaitingAPIAdapter<AddEmotionRequest, String> {
        public final AddEmotionRequest data;
        private final String token;

        public SaveEmotions(AddEmotionRequest input_data, String token, long requestCode, Activity activity, String message, API_Response_Listener<String> listener) {
            super(input_data, token, requestCode, activity, message, listener);
            this.data = input_data;
            this.token = token;
        }
        @Override
        public String getURL() {
            return Urls.saveEmotionsURL();
        }

        @Override
        public String getHttpMethod() {
            return "POST";
        }

        @Override
        public String getToken() {
            return token;
        }

        @Override
        public JSONObject convertDataToJSON(AddEmotionRequest data) {
            Log.i("APiProvider","Json object is "+JSONParser.getJsonForEmotion(data));
            return JSONParser.getJsonForEmotion(data);
        }

        @Override
        public String convertJSONToData(String response) {
            //Log.i("APiProvider","Response is "+response);
            return JSONParser.getDataForEmotion(response).getResponse();
        }

        @Override
        public String intrepret_error(String error) {
            //Log.i("ApiProvider","Error is "+error);
            return error;
        }
    }
    public static class SaveMyCoachData extends WaitingAPIAdapter<CoachModel, String> {
        public final CoachModel data;
        private final String token;


        public SaveMyCoachData(CoachModel input_data, String token, long requestCode, Activity activity, String message, API_Response_Listener<String> listener) {
            super(input_data, token, requestCode, activity, message, listener);
            this.data = input_data;
            this.token = token;
        }
        public SaveMyCoachData(CoachModel input_data, String token, long requestCode, API_Response_Listener<String> listener) {
            super(input_data, token, requestCode, null, null, listener);
            this.data = input_data;
            this.token = token;
        }

        @Override
        public String getURL() {
            return Urls.getSaveMyCoachUrl();
        }

        @Override
        public String getHttpMethod() {
            return "POST";
        }

        @Override
        public JSONObject convertDataToJSON(CoachModel data) {
            Log.i("APiProvider","Json object is "+JSONParser.getJsonForSaveMyCoach(data));
            return JSONParser.getJsonForSaveMyCoach(data);
        }

        @Override
        public String getToken() {
            return token;
        }

        @Override
        public String convertJSONToData(String response) {
            Log.i("APiProvider","Response is "+response);
            return JSONParser.getDataForSaveMyCoach(response).getResponse();
        }

        @Override
        public String intrepret_error(String error) {
            //Log.i("ApiProvider","Error is "+error);
            return error;
        }
    }
    public static class GetMindGymData extends WaitingAPIAdapter<String, List<MindGymModel>> {
        public final String data;
        private final String token;

        public GetMindGymData(String input_data, String token, long requestCode, Activity activity, String message, API_Response_Listener<List<MindGymModel>> listener) {
            super(input_data, token, requestCode, activity, message, listener);
            this.data = input_data;
            this.token = token;
        }

        public GetMindGymData(String input_data, String token, long requestCode, API_Response_Listener<List<MindGymModel>> listener) {
            super(input_data, token, requestCode, null, null, listener);
            this.data = input_data;
            this.token = token;
        }

        @Override
        public String getURL() {
            return Urls.getMindGymUrl();
        }

        @Override
        public String getHttpMethod() {
            return "GET";
        }

        @Override
        public String getToken() {
            return token;
        }


        @Override
        public List<MindGymModel> convertJSONToData(String response) {
            return JSONParser.getAudioMindGymData(response);
        }

    }
    public static class SaveSelfLove extends WaitingAPIAdapter<SelfLoveData, String> {
        public final SelfLoveData data;
        private final String token;

        public SaveSelfLove(SelfLoveData input_data, String token, long requestCode, Activity activity, String message, API_Response_Listener<String> listener) {
            super(input_data, token, requestCode, activity, message, listener);
            this.data = input_data;
            this.token = token;
        }

        public SaveSelfLove(SelfLoveData input_data, String token, long requestCode, API_Response_Listener<String> listener) {
            super(input_data, token, requestCode, null, null, listener);
            this.data = input_data;
            this.token = token;
        }

        @Override
        public String getURL() {
            return Urls.saveSelfLoveURL();
        }

        @Override
        public String getToken() {
            return token;
        }

        @Override
        public String getHttpMethod() {
            return "POST";
        }

        @Override
        public JSONObject convertDataToJSON(SelfLoveData data) {
            Log.i("APiProvider","Json object for gratitude is "+JSONParser.getJsonForGratitude(data));
            return JSONParser.getJsonForGratitude(data);
        }

        @Override
        public String convertJSONToData(String response) {
            //Log.i("APiProvider","Response is "+response);
            return JSONParser.getDataForGratitude(response).getResponse();
        }

        @Override
        public String intrepret_error(String error) {
            //Log.i("ApiProvider","Error is "+error);
            return error;
        }
    }

    public static class DeleteSelfLove extends WaitingAPIAdapter<SelfLoveData, String> {
        public final SelfLoveData data;
        private final String token;

        public DeleteSelfLove(SelfLoveData input_data, String token, long requestCode, Activity activity, String message, API_Response_Listener<String> listener) {
            super(input_data, token, requestCode, activity, message, listener);
            this.data = input_data;
            this.token = token;
        }


        @Override
        public String getURL() {
            return Urls.saveSelfLoveURL();
        }

        @Override
        public String getToken() {
            return token;
        }

        @Override
        public String getHttpMethod() {
            return "DELETE";
        }

        @Override
        public JSONObject convertDataToJSON(SelfLoveData data) {
            // Log.i("APiProvider","Json object is "+JSONParser.getJsonForLogin(data));
            return JSONParser.getJsonForDeleteGratitude(data);
        }

        @Override
        public String convertJSONToData(String response) {
            Log.i("DeleteEntry", "Response for delete api is " + response);
            return JSONParser.getDataForDeleteGratitude(response).getResponse();
        }

        @Override
        public String intrepret_error(String error) {
            //Log.i("ApiProvider","Error is "+error);
            return error;
        }
    }

    public static class UpdateSelfLove extends WaitingAPIAdapter<SelfLoveData, String> {
        public final SelfLoveData data;
        private final String token;

        public UpdateSelfLove(SelfLoveData input_data, String token, long requestCode, Activity activity, String message, API_Response_Listener<String> listener) {
            super(input_data, token, requestCode, activity, message, listener);
            this.data = input_data;
            this.token = token;
        }

        public UpdateSelfLove(SelfLoveData input_data, String token, long requestCode, API_Response_Listener<String> listener) {
            super(input_data, token, requestCode, null, null, listener);
            this.data = input_data;
            this.token = token;
        }

        @Override
        public String getURL() {
            return Urls.saveSelfLoveURL();
        }

        @Override
        public String getToken() {
            return token;
        }

        @Override
        public String getHttpMethod() {
            return "PUT";
        }

        @Override
        public JSONObject convertDataToJSON(SelfLoveData data) {
            // Log.i("APiProvider","Json object is "+JSONParser.getJsonForLogin(data));
            return JSONParser.getUpdateJsonForGratitude(data);
        }

        @Override
        public String convertJSONToData(String response) {
            //Log.i("APiProvider","Response is "+response);
            return JSONParser.getDataForGratitude(response).getResponse();
        }

        @Override
        public String intrepret_error(String error) {
            //Log.i("ApiProvider","Error is "+error);
            return error;
        }
    }
    public static class TrailUserAudioDetails extends WaitingAPIAdapter<String, List<MindGymModel>> {
        public final String data;
        private final String token;

        public TrailUserAudioDetails(String input_data, String token, long requestCode, Activity activity, String message, API_Response_Listener<List<MindGymModel>> listener) {
            super(input_data, token, requestCode, activity, message, listener);
            this.data = input_data;
            this.token = token;
        }

        public TrailUserAudioDetails(String input_data, String token, long requestCode, API_Response_Listener<List<MindGymModel>> listener) {
            super(input_data, token, requestCode, null, null, listener);
            this.data = input_data;
            this.token = token;
        }

        @Override
        public String getURL() {
            return Urls.getMindGymUrl();
        }

        @Override
        public String getHttpMethod() {
            return "GET";
        }

        @Override
        public String getToken() {
            return token;
        }


        @Override
        public List<MindGymModel> convertJSONToData(String response) {
            return JSONParser.getTrailUserData(response);
        }

    }

    public static class PaymentPackageExpiryDetails extends WaitingAPIAdapter<PackageInfo, Integer> {
        public final PackageInfo data;
        private final String token;

        public PaymentPackageExpiryDetails(PackageInfo input_data, String token, long requestCode, Activity activity, String message, API_Response_Listener<Integer> listener) {
            super(input_data, token, requestCode, activity, message, listener);
            this.data = input_data;
            this.token = token;
        }

        public PaymentPackageExpiryDetails(PackageInfo input_data, String token, long requestCode, API_Response_Listener<Integer> listener) {
            super(input_data, token, requestCode, null, null, listener);
            this.data = input_data;
            this.token = token;
        }

        @Override
        public String getURL() {
            //TODO: for testing
//            return "https://dev.api.nsmiles.com/v2/user/payment-expiry";
            return Urls.getPackageExpiryUrl();
        }

        @Override
        public String getHttpMethod() {
            return "POST";
        }

        @Override
        public String getToken() {
            return token;
        }

        @Override
        public JSONObject convertDataToJSON(PackageInfo data) {
            Log.i("ApiProvider","Json for payment is "+JSONParser.getJsonForPaymentPackageStatus(data));
            return JSONParser.getJsonForPaymentPackageStatus(data);
        }

        @Override
        public Integer convertJSONToData(String response) {
            Log.i("ApiProvider","Response for payment is "+JSONParser.getUserInfoFromJson(response));
            return JSONParser.getPackageDaysLeftFromJson(response);
        }

        @Override
        public String intrepret_error(String error) {
            return error;
        }
    }



}
