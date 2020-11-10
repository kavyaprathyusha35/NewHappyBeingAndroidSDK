package com.nsmiles.happybeingsdklib.ServerApiConnectors;

import android.util.Log;

import com.nsmiles.happybeingsdklib.Affimations.AffirmationModel;
import com.nsmiles.happybeingsdklib.MindGym.AddEmotionRequest;
import com.nsmiles.happybeingsdklib.Models.PackageInfo;
import com.nsmiles.happybeingsdklib.MyCoach.CoachModel;
import com.nsmiles.happybeingsdklib.UI.gratitude.SelfLoveData;
import com.nsmiles.happybeingsdklib.dagger.data.UserInformation;
import com.nsmiles.happybeingsdklib.playaudio.MindGymModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Kavya on 10/27/2020.
 */

public class JSONParser {
    static JSONObject getJsonForSaveAffirmation(AffirmationModel data) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", data.getEmail());
            jsonObject.put("primary_profile", data.getPrimary_profile());
            jsonObject.put("secondary_profile", data.getSecondary_profile());
            jsonObject.put("title", data.getTitle());
            jsonObject.put("mind_gym_type", data.getMind_gym_type());
            jsonObject.put("description", data.getDescription());
            jsonObject.put("url", data.getUrl());
            jsonObject.put("device_os", "Happy_being_ANDROID");
            jsonObject.put("mycoach_completed_days", data.getMycoach_completed_days());
            jsonObject.put("mycoach_completed_date", data.getCompleted_date());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    static JSONObject getJsonForSaveRelaxAudio(CoachModel data) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", data.getEmail());
            jsonObject.put("device_os", "Happy_being");
            jsonObject.put("description", data.getDescription());
            jsonObject.put("url", data.getUrl());
            jsonObject.put("title", data.getTitle());
            jsonObject.put("device_os", "Happy_being_ANDROID");
            jsonObject.put("completed_date", data.getCompleted_date());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    static JSONObject getJsonForEmotion(AddEmotionRequest data) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", data.getUser_id());
            jsonObject.put("date_time", data.getDate_time());
            jsonObject.put("device_id", data.getDevice_id());
            jsonObject.put("emotion1", data.getEmotion1());
            jsonObject.put("feature", data.getFeature());
            jsonObject.put("activity", data.getActivity());
            jsonObject.put("start_date_time", data.getStart_date_time());
            jsonObject.put("end_date_time", data.getEnd_date_time());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    static AddEmotionRequest getDataForEmotion(String response) {
        AddEmotionRequest addEmotionRequest = new AddEmotionRequest();
        try {
            MyJsonObject jsonObject = new MyJsonObject(response);
            String successString = jsonObject.getString("success");
            Log.e("successString ", successString);

            if (successString != null && !successString.equals("")) {
                addEmotionRequest.setResponse("true");
            } else {
                addEmotionRequest.setResponse("false");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return addEmotionRequest;
    }


    static CoachModel getDataForSaveMyCoach(String response) {
        CoachModel coachModel = new CoachModel();
        try {
            MyJsonObject jsonObject = new MyJsonObject(response);
            String successString = jsonObject.getString("success");
            Log.e("successString ", "value is "+successString.contains("awesome"));

            if (successString != null && successString.contains("awesome")) {
                coachModel.setResponse("true");
            } else {
                coachModel.setResponse("false");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return coachModel;
    }
    static String getErrorString(String response) {
        if (response.contains("mobile already exists")) {
            return ApiProvider.MOBILE_NUMBER_EXISTS;
        } else if (response.contains("email already exists")) {
            return ApiProvider.EMAIL_EXISTS;
        } else if (response.contains("screenname already taken")) {
            return response;
        } else {
            try {
                JSONObject jobj = new JSONObject(response);
                if (jobj.has("errors")) {
                    return jobj.getString("errors");
                }

                return null;
            } catch (JSONException e) {
                //e.printStackTrace();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobj = jsonArray.getJSONObject(i);
                        if (jobj.has("errors")) {
                            return jobj.getString("errors");
                        }
                    }
                    return null;
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    return response;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "unknown error";
            }
        }
    }
    static JSONObject getJsonForSaveMyCoach(CoachModel data) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", data.getEmail());
            jsonObject.put("primary_profile", data.getPrimary_profile());
            jsonObject.put("secondary_profile", data.getSecondary_profile());
            jsonObject.put("title", data.getTitle());
            jsonObject.put("mind_gym_type", data.getMind_gym_type());
            jsonObject.put("description", data.getDescription());
            jsonObject.put("repeat", data.getRepeat());
            jsonObject.put("url", data.getUrl());
            jsonObject.put("device_os", "Happy_being_ANDROID");
            jsonObject.put("mycoach_completed_days", data.getMycoach_completed_days());
            jsonObject.put("mycoach_completed_date", data.getCompleted_date());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    static List<MindGymModel> getAudioMindGymData(String response) {
        SimpleDateFormat ddmmyyyyFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TimeZone tz = TimeZone.getTimeZone("UTC");
        ddmmyyyyFormat.setTimeZone(tz);


        Date serverDate = null, simpleDate;
        String serverTxtDate = "", simpleTxtDate = "";
        List<MindGymModel> mindGymList = new ArrayList<>();
        MindGymModel mindGymModel;
        String usercreateddate, currentdate, timedifference, paymentStatus;
        int datedifference, storeDay, limit;
       // CommonUtils.showLogInforamtion("dddd", "eee", "data available json", true);
        Calendar calendar = Calendar.getInstance();
        String[] gymDate;
        int[] audioDay;
        List<Integer> audioDayList;
        String[] SCurrentDate;
        String[] SPlayStatus;
        int[] SDayDiff;
        List<Integer> daysList;
        String convertTxt;
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject successJson = jsonObject.getJSONObject("success");
            JSONObject dateDetailJson = successJson.getJSONObject("datedetails");
            //usercreateddate = dateDetailJson.getString("usercreateddate");
            currentdate = ddmmyyyyFormat.format(new Date());
            Log.i("JsonParser","******Get date is "+ddmmyyyyFormat.format(new Date()));
            //    timedifference = dateDetailJson.getString("timedifference");
            datedifference = dateDetailJson.getInt("datedifference");
            limit = 1;
            // limit = dateDetailJson.getInt("datedifference");
            storeDay = dateDetailJson.getInt("datedifference");
            // release changes.
            //limit = 20;
            // storeDay = 40;
            // datedifference = 40;
            // release changes.
            if (successJson.has("paymentstatus")) {

                JSONObject paymentJson = successJson.getJSONObject("paymentstatus");

                if (paymentJson.has("paymentStatus")) {
                    paymentStatus = paymentJson.getString("paymentStatus");
                } else {
                    //// data is empty assign expired user release changes  EXPIRED
                    paymentStatus = "EXPIRED";
                }

            } else {
                //// data is empty assign expired user release changes  EXPIRED
                paymentStatus = "EXPIRED";
            }
            serverDate = ddmmyyyyFormat.parse(currentdate);
            serverTxtDate = ddmmyyyyFormat.format(serverDate);
            simpleDate = simpleDateFormat.parse(currentdate);
            simpleTxtDate = simpleDateFormat.format(simpleDate);


            if (paymentStatus.equalsIgnoreCase("EXPIRED")) {
                //CommonUtils.showLogInforamtion("dddd", "eee", "empty json", true);
                mindGymModel = new MindGymModel();
                mindGymModel.setPaymentStatus(paymentStatus);
                mindGymModel.setDateDifference(datedifference);
                mindGymModel.setCurrentDate(currentdate);
                mindGymModel.setPlayStatus(0);
                mindGymModel.setDataSynced(1);
                mindGymList.add(mindGymModel);
            } else {
                if (successJson.has("mindgymdata")) {
                    JSONArray mindGymDataJson = successJson.getJSONArray("mindgymdata");
                    /*Server Date*/
                    calendar.setTime(simpleDateFormat.parse(simpleTxtDate));
                    if (mindGymDataJson.length() > 0) {
                        if (limit > 7) {
                            limit = 7;
                        }
                        gymDate = new String[limit];
                        audioDay = new int[limit];
                        SCurrentDate = new String[mindGymDataJson.length()];
                        SDayDiff = new int[mindGymDataJson.length()];
                        SPlayStatus = new String[mindGymDataJson.length()];
                        daysList = new ArrayList<>();
                        audioDayList = new ArrayList<>();

                        for (int i = 0; i < limit; i++) {
                            convertTxt = simpleDateFormat.format(calendar.getTime());
                            gymDate[i] = convertTxt;
                            audioDay[i] = storeDay;
                            audioDayList.add(storeDay);
                           // CommonUtils.showLogInforamtion("ggggym date -7", "date  ", limit, true);
                            calendar.add(Calendar.DATE, -1);
                            calendar.setTime(calendar.getTime());
                            storeDay--;
                        }
                        for (int j = 0; j < mindGymDataJson.length(); j++) {

                            JSONObject coachDataJson = mindGymDataJson.getJSONObject(j);
                            SCurrentDate[j] = coachDataJson.getString("mycoach_completed_date");
                            SPlayStatus[j] = coachDataJson.getString("repeat");
                            SDayDiff[j] = Integer.parseInt(coachDataJson.getString("mycoach_completed_days"));
                            daysList.add(Integer.valueOf(coachDataJson.getString("mycoach_completed_days")));
                        }
                        /*Order MultipleReportData Populate Own List*/
                        for (int k = 0; k < limit; k++) {

                            if (daysList.contains(audioDayList.get(k))) {
                                mindGymModel = new MindGymModel();
                                mindGymModel.setPaymentStatus(paymentStatus);
                                mindGymModel.setDateDifference(audioDay[k]);
                                mindGymModel.setCurrentDate(gymDate[k]);
                                mindGymModel.setPlayStatus(1);
                                mindGymModel.setDataSynced(1);
                                mindGymList.add(mindGymModel);
                            } else {
                                mindGymModel = new MindGymModel();
                                mindGymModel.setPaymentStatus(paymentStatus);
                                mindGymModel.setDateDifference(audioDay[k]);
                                mindGymModel.setCurrentDate(gymDate[k]);
                                mindGymModel.setPlayStatus(0);
                                mindGymModel.setDataSynced(1);
                                mindGymList.add(mindGymModel);
                            }
                        }
                        /*Order MultipleReportData Populate Own List End*/
                    } else {

                        // user not user one audio even, current day is more than one create list

                        if (datedifference == 1) {
                          //  CommonUtils.showLogInforamtion("dddd", "eee", "empty json", true);
                            mindGymModel = new MindGymModel();
                            mindGymModel.setPaymentStatus(paymentStatus);
                            mindGymModel.setDateDifference(datedifference);
                            //   mindGymModel.setDateDifference(32);
                            mindGymModel.setCurrentDate(currentdate);
                            mindGymModel.setPlayStatus(0);
                            mindGymModel.setDataSynced(1);
                            mindGymList.add(mindGymModel);
                        } else {

                            if (limit > 7) {
                                limit = 7;
                            }

                            gymDate = new String[limit];
                            audioDay = new int[limit];
                            SCurrentDate = new String[limit];
                            SDayDiff = new int[limit];
                            SPlayStatus = new String[limit];
                            daysList = new ArrayList<>();
                            audioDayList = new ArrayList<>();

                            for (int i = 0; i < limit; i++) {
                                convertTxt = simpleDateFormat.format(calendar.getTime());
                                gymDate[i] = convertTxt;
                                audioDay[i] = storeDay;
                                audioDayList.add(storeDay);
                                //CommonUtils.showLogInforamtion("ggggym date -7", "date  ", limit, true);
                                calendar.add(Calendar.DATE, -1);
                                calendar.setTime(calendar.getTime());
                                storeDay--;
                                mindGymModel = new MindGymModel();
                                mindGymModel.setPaymentStatus(paymentStatus);
                                mindGymModel.setDateDifference(audioDay[i]);
                                mindGymModel.setCurrentDate(gymDate[i]);
                                mindGymModel.setPlayStatus(0);
                                mindGymModel.setDataSynced(1);
                                mindGymList.add(mindGymModel);
                            }
                        }
                    }
                    /*Server Date*/
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mindGymList;
    }
    static SelfLoveData getDataForGratitude(String response) {
        SelfLoveData selfLoveData = new SelfLoveData();
        try {
            MyJsonObject jsonObject = new MyJsonObject(response);
            String successString = jsonObject.getString("success");
            Log.e("successString ", successString);

            if (successString != null && !successString.equals("")) {
                selfLoveData.setResponse("true");
                selfLoveData.setId(successString);
            } else {
                selfLoveData.setResponse("false");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return selfLoveData;
    }
    static JSONObject getJsonForGratitude(SelfLoveData data) {
        JSONObject jsonObject = new JSONObject();
        try {

            // jsonObject.put("_csrf", UserInformation.csrf_key);
            jsonObject.put("user_id", data.getUser_id());
            jsonObject.put("date_time", data.getDate_time());
            jsonObject.put("type_of_gratitude", data.getType_of_gratitude());
            jsonObject.put("description", data.getDescription());
            jsonObject.put("express_your_gratitude", data.getExpress_your_gratitude());
            jsonObject.put("link", data.getLink());
            jsonObject.put("pic", data.getPic());
            jsonObject.put("title", data.getTitle());
            jsonObject.put("email", data.getEmail());
            jsonObject.put("subject", data.getSubject());
            jsonObject.put("device_os", "Happy_being_ANDROID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    static JSONObject getJsonForDeleteGratitude(SelfLoveData data) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", data.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    static SelfLoveData getDataForDeleteGratitude(String response) {
        SelfLoveData selfLoveData = new SelfLoveData();
        try {
            MyJsonObject jsonObject = new MyJsonObject(response);
            String successString = jsonObject.getString("success");
            Log.e("successString ", successString);

            if (successString != null && !successString.equals("")) {
                selfLoveData.setResponse("true");
            } else {
                selfLoveData.setResponse("false");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return selfLoveData;
    }
    static JSONObject getUpdateJsonForGratitude(SelfLoveData data) {
        JSONObject jsonObject = new JSONObject();
        try {

            // jsonObject.put("_csrf", UserInformation.csrf_key);
            jsonObject.put("user_id", data.getUser_id());
            jsonObject.put("date_time", data.getDate_time());
            jsonObject.put("type_of_gratitude", data.getType_of_gratitude());
            jsonObject.put("description", data.getDescription());
            jsonObject.put("express_your_gratitude", data.getExpress_your_gratitude());
            jsonObject.put("link", data.getLink());
            jsonObject.put("pic", data.getPic());
            jsonObject.put("title", data.getTitle());
            jsonObject.put("email", data.getEmail());
            jsonObject.put("subject", data.getSubject());
            jsonObject.put("id", data.getId());
            jsonObject.put("device_os", "Happy_being_ANDROID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    static List<MindGymModel> getTrailUserData(String response) {

        List<MindGymModel> mindGymList = new ArrayList<>();
        MindGymModel mindGymModel;
        String paymentStatus;
        int today;
        List<Integer> lastAudioList;
        try {

            JSONObject jsonObject = new JSONObject(response);
            JSONObject successJson = jsonObject.getJSONObject("success");
            JSONObject dateDetailJson = successJson.getJSONObject("datedetails");

            if (successJson.has("paymentstatus")) {

                JSONObject paymentJson = successJson.getJSONObject("paymentstatus");

                if (paymentJson.has("paymentStatus")) {

                    paymentStatus = paymentJson.getString("paymentStatus");
                } else {
                    paymentStatus = "EXPIRED";
                }
            } else {

                paymentStatus = "EXPIRED";
            }
            today = dateDetailJson.getInt("datedifference");

            if (successJson.has("mindgymdata")) {
                JSONArray mindGymDataJson = successJson.getJSONArray("mindgymdata");
                if (mindGymDataJson.length() > 0) {

                    lastAudioList = new ArrayList<>();
                    for (int i = 0; i < mindGymDataJson.length(); i++) {
                        JSONObject coachDataJson = mindGymDataJson.getJSONObject(i);
                        if (coachDataJson.has("mind_gym_type")) {
                            if (coachDataJson.getString("mind_gym_type").equalsIgnoreCase("AUDIO")) {
                                if (!lastAudioList.contains(Integer.valueOf(coachDataJson.getString("mycoach_completed_days")))) {
                                    lastAudioList.add(Integer.valueOf(coachDataJson.getString("mycoach_completed_days")));
                                }
                            }
                        }
                    }

                    mindGymModel = new MindGymModel();
                    mindGymModel.setPaymentStatus(paymentStatus);
                    mindGymModel.setDateDifference(today);
                    if (lastAudioList.contains(today)) {
                        mindGymModel.setPlayStatus(1);
                    } else {
                        mindGymModel.setPlayStatus(0);
                    }
                    mindGymModel.setLast_audio(lastAudioList.size());
                    mindGymList.add(mindGymModel);

                } else {
                    mindGymModel = new MindGymModel();
                    mindGymModel.setPaymentStatus(paymentStatus);
                    mindGymModel.setDateDifference(today);
                    mindGymModel.setPlayStatus(0);
                    mindGymModel.setLast_audio(0);
                    mindGymList.add(mindGymModel);
                }

            } else {
                mindGymModel = new MindGymModel();
                mindGymModel.setPaymentStatus(paymentStatus);
                mindGymModel.setDateDifference(today);
                mindGymModel.setPlayStatus(0);
                mindGymModel.setLast_audio(0);
                mindGymList.add(mindGymModel);
            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("offer catch error", e.getMessage());
        }
        return mindGymList;
    }


    static JSONObject getJsonForPaymentPackageStatus(PackageInfo data) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", data.getEmail());
            jsonObject.put("packagename", data.getPackageName());
            jsonObject.put("assessment_name", data.getAssessment_name());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    static UserInformation getUserInfoFromJson(String response) {
        UserInformation userInformation = new UserInformation();
        try {
            MyJsonObject jsonObject = new MyJsonObject(response);
            String successString = jsonObject.getString("success");
            if (successString != null && !successString.equals("")) {
                userInformation.setIsPaid(true);
            } else {
                userInformation.setIsPaid(false);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userInformation;
    }
    static Integer getPackageDaysLeftFromJson(String response) {
        int daysLeft = 0;
        try {
            Log.e("response", response);

            MyJsonObject jsonObject = new MyJsonObject(response);
            String success = jsonObject.getString("success");

            if(success != null && success.equalsIgnoreCase("no payment information available for the package and assessment given")){
                daysLeft = -1;
            }
            else {
                MyJsonObject jsonObject1 = new MyJsonObject(success);
                if (jsonObject1.has("daysleft")) {

                    long daysLeftlong = jsonObject1.getLong("daysleft");
                    Log.e("daysLeftlong", String.valueOf(daysLeftlong));
                    daysLeft = (int) (daysLeftlong / (1000 * 60 * 60 * 24));
                }
                else if (jsonObject1.has("paymentstatus")) {
                    String paymentString = jsonObject1.getString("paymentstatus");
                    MyJsonObject paymentStatusJson = new MyJsonObject(paymentString);
                    if (paymentStatusJson.has("paymentStatus")) {
                        String paymentStatus = paymentStatusJson.getString("paymentStatus");
                        if (paymentStatus.equals("PAID")) {
                            daysLeft = 100000;
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return daysLeft;
    }

}
