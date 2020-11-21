package com.nsmiles.happybeingsdklib.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nsmiles.happybeingsdklib.Models.PaymentInfo;
import com.nsmiles.happybeingsdklib.Models.PaymentPackagesModel.PaymentPackagesInfo;
import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.ServerApiConnectors.Urls;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;
import com.nsmiles.happybeingsdklib.Utils.SdkPreferenceManager;
import com.nsmiles.happybeingsdklib.dagger.application.BaseApplication;
import com.nsmiles.happybeingsdklib.network.NetworkError;
import com.nsmiles.happybeingsdklib.network.Service;
import com.payumoney.core.PayUmoneySdkInitializer.PaymentParam;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import rx.Subscription;

public class SubscriptionActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout one_year_layout, life_time_layout, one_month_layout;
    private boolean isOne_year_value, isOne_months_value, isLifetime_months_value;
    private Button continue_button;
    private TextView no_thanks_text, oneMonthsAmount, oneYearAmount, sixMonthsAmount, onemonthtext1,
            oneYearText1, life_time_text1, onemonthtext2, oneYearText2, life_time_amount,life_time_amount1,
            three_months_one_month_text, year_one_month_text,day_one_month_text, six_months_one_month_text;
    private String one_months_amount, lifetime_months_amount, one_year_amount;
    private String amount;
    SdkPreferenceManager sdkPreferenceManager;
    String key = "UBzYP4", txnid = "1223", productinfo = "HappyBeing", salt = "w4ziPuC3",
            sUrl = "https://staging.vidalhealth.com:8443/patient/consultations/pay-success",
            fUrl = "https://staging.vidalhealth.com:8443/patient/consultations/pay-failure";
    private String serverCalculatedHash;

    @Inject
    Service service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_subscription);
        one_year_layout = findViewById(R.id.one_year_layout);
        life_time_layout = findViewById(R.id.life_time_layout);
        one_month_layout = findViewById(R.id.one_month_layout);
        continue_button = findViewById(R.id.continue_button);
        no_thanks_text = findViewById(R.id.no_thanks_text);
        oneMonthsAmount = findViewById(R.id.one_month_amount_text);
        oneYearAmount = findViewById(R.id.one_year_amount_text);
        sixMonthsAmount = findViewById(R.id.six_months_amount_text);
        onemonthtext1 = findViewById(R.id.one_month_text);
        onemonthtext2 = findViewById(R.id.one_month_text2);
        oneYearText1 = findViewById(R.id.one_year_text);
        oneYearText2 = findViewById(R.id.one_year_text2);
        life_time_text1 = findViewById(R.id.life_time_text1);
        life_time_amount = findViewById(R.id.life_time_amount);
        life_time_amount1 = findViewById(R.id.life_time_amount1);
        three_months_one_month_text = findViewById(R.id.three_months_one_month_text);
        year_one_month_text = findViewById(R.id.year_one_month_text);
        day_one_month_text = findViewById(R.id.day_one_month_text);
        six_months_one_month_text = findViewById(R.id.six_months_one_month_text);
        one_months_amount = "270";
        lifetime_months_amount = "9900";
        one_year_amount = "2900";
        sdkPreferenceManager = new SdkPreferenceManager(this);
        one_year_layout.setOnClickListener(this);
        life_time_layout.setOnClickListener(this);
        one_month_layout.setOnClickListener(this);
        continue_button.setOnClickListener(this);
        no_thanks_text.setOnClickListener(this);
        isOne_year_value = true;
        isOne_months_value = false;
        isLifetime_months_value = false;
        ((BaseApplication) getApplication()).getmApplicationApiComponent().inject(this);
        getPaymentPackages();
        payUMoneySetUp();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.one_month_layout) {
            one_year_layout.setBackground(null);
            one_month_layout.setBackground(getDrawable(R.drawable.button_orange_border));
            life_time_layout.setBackground(null);
            isOne_year_value = false;
            isOne_months_value = true;
            isLifetime_months_value = false;
            setTextColours();

        } else if (id == R.id.one_year_layout) {
            one_month_layout.setBackground(null);
            one_year_layout.setBackground(getDrawable(R.drawable.button_orange_border));
            life_time_layout.setBackground(null);
            isOne_year_value = true;
            isOne_months_value = false;
            isLifetime_months_value = false;

            setTextColours();
        } else if (id == R.id.life_time_layout) {
            one_month_layout.setBackground(null);
            life_time_layout.setBackground(getDrawable(R.drawable.button_orange_border));
            one_year_layout.setBackground(null);
            isOne_year_value = false;
            isOne_months_value = false;
            isLifetime_months_value = true;

            setTextColours();
        } else if (id == R.id.continue_button) {
            if (CommonUtils.isNetworkAvailable(SubscriptionActivity.this)) {

                if (isOne_year_value) {
                    askForPayment(AppConstants.ITEM_SKU_ONE_YEAR);
                } else if (isLifetime_months_value) {
                    askForPayment(AppConstants.ITEM_SKU_LIFETIME);
                } else if (isOne_months_value) {
                    askForPayment(AppConstants.ITEM_SKU_ONE_MONTH);
                }
            } else {
                CommonUtils.showToast(SubscriptionActivity.this, getResources().getString(R.string.no_network));
            }
        } else if (id == R.id.no_thanks_text) {
            startActivity(new Intent(this, HomeScreenActivity.class).putExtra("Animate", true).putExtra("MODE", "Trail_mode"));
            finishAffinity();
        }
    }

    private void setTextColours() {

        if (isOne_year_value) {
            oneYearText1.setTextColor(getResources().getColor(R.color.black));
            oneYearText2.setTextColor(getResources().getColor(R.color.black));
            onemonthtext1.setTextColor(getResources().getColor(R.color.bot_gray));
            onemonthtext2.setTextColor(getResources().getColor(R.color.bot_gray));
            life_time_text1.setTextColor(getResources().getColor(R.color.bot_gray));
            life_time_amount.setTextColor(getResources().getColor(R.color.bot_gray));
        }
        else if (isLifetime_months_value) {
            oneYearText1.setTextColor(getResources().getColor(R.color.bot_gray));
            oneYearText2.setTextColor(getResources().getColor(R.color.bot_gray));
            onemonthtext1.setTextColor(getResources().getColor(R.color.bot_gray));
            onemonthtext2.setTextColor(getResources().getColor(R.color.bot_gray));
            life_time_text1.setTextColor(getResources().getColor(R.color.black));
            life_time_amount.setTextColor(getResources().getColor(R.color.black));

        }
        else if (isOne_months_value) {
            oneYearText1.setTextColor(getResources().getColor(R.color.bot_gray));
            oneYearText2.setTextColor(getResources().getColor(R.color.bot_gray));
            onemonthtext1.setTextColor(getResources().getColor(R.color.black));
            onemonthtext2.setTextColor(getResources().getColor(R.color.black));
            life_time_text1.setTextColor(getResources().getColor(R.color.bot_gray));
            life_time_amount.setTextColor(getResources().getColor(R.color.bot_gray));

        }
    }

    private void askForPayment(final String skuId) {
        Log.i("HomeScreen", skuId);
        if (CommonUtils.isNetworkAvailable(this)) {
            try {
                payULaunch();
            } catch (Exception e) {
                Log.i("Subscribe", "Error occured");
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(this, "Network not available!!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getPaymentPackages() {
        Subscription subscription = service.getPaymentPackagesInfo(sdkPreferenceManager.get(AppConstants.SDK_ACCESS_TOKEN, ""), Urls.getPaymentPackagesUrl(), new Service.PaymentPackagesCallBack() {
            @Override
            public void onSuccess(PaymentPackagesInfo paymentPackagesInfo) {
                for(int i = 0; i < paymentPackagesInfo.getResult().getSuccess().size(); i++) {
                    String lifeTimeAmount, oneMonthAmount, oneyearAmount,onedaypermonth,onemonthperyear;
                    if (paymentPackagesInfo.getResult().getSuccess().get(i).getPackagename().equals("AndroidMonthly")) {
                        oneMonthAmount =  paymentPackagesInfo.getResult().getSuccess().get(i).getAmount();
                        oneMonthsAmount.setText(oneMonthAmount);
                    } else if (paymentPackagesInfo.getResult().getSuccess().get(i).getPackagename().equals("AndroidYearly")) {
                        oneyearAmount = paymentPackagesInfo.getResult().getSuccess().get(i).getAmount();
                        oneYearAmount.setText(oneyearAmount);
                    } else if (paymentPackagesInfo.getResult().getSuccess().get(i).getPackagename().equals("AndroidLifeTime")) {
                        lifeTimeAmount = paymentPackagesInfo.getResult().getSuccess().get(i).getAmount();
                        life_time_amount.setText(lifeTimeAmount);
                        life_time_amount1.setText(lifeTimeAmount);
                    }else if (paymentPackagesInfo.getResult().getSuccess().get(i).getPackagename().equals("AndroidPerDay")) {
                        onedaypermonth = paymentPackagesInfo.getResult().getSuccess().get(i).getAmount();
                        day_one_month_text.setText(onedaypermonth+" / day");

                    }else if (paymentPackagesInfo.getResult().getSuccess().get(i).getPackagename().equals("AndroidPerMonth")) {
                        onemonthperyear = paymentPackagesInfo.getResult().getSuccess().get(i).getAmount();
                        year_one_month_text.setText(onemonthperyear+ " / mo");

                    }
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

    private void pushToServer(String id) {
        Log.i("Subscribe", "In push to server");
        CommonUtils commonUtils = new CommonUtils();
        String packageName = "corporateWellbeing";
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setEmail(commonUtils.getUserEmail(this));
        paymentInfo.setTxId(id);
        paymentInfo.setTxMsg("Payment Successful");
        paymentInfo.setPaymentMode("FROM App");
        paymentInfo.setPackageName(packageName);

        if (isOne_months_value) {
            paymentInfo.setAmount(one_months_amount);
            amount = one_months_amount;
            paymentInfo.setExnExpiryDateTime(commonUtils.getExpiryDate(1));
        } else if (isOne_year_value) {
            amount = one_year_amount;
            paymentInfo.setAmount(one_year_amount);
            paymentInfo.setExnExpiryDateTime(commonUtils.getExpiryDate(12));

        } else if (isLifetime_months_value) {
            amount = lifetime_months_amount;
            paymentInfo.setExnExpiryDateTime(commonUtils.getExpiryDate(60));
            paymentInfo.setAmount(lifetime_months_amount);
        }

        paymentInfo.setTxnDateTime(commonUtils.DD_MM_YYYY_T());

/*
        new ApiProvider.SetPayment(paymentInfo, commonUtils.getTokenId(SubscriptionActivity.this), 4, this, "Updating payment details", new API_Response_Listener<UserInformation>() {
            @Override
            public void onComplete(UserInformation data, long request_code, String failure_code) {
                if (data != null) {
                   // HomeScreenActivity.AppFlyerEvent(SubscriptionActivity.this, "Payment successful..Updated to our server","Payment Canceled");
                    CommonUtils.showToast(SubscriptionActivity.this, "Payment successful!!!");
                    startActivity(new Intent(SubscriptionActivity.this, HomeScreenActivity.class).putExtra("Animate", true).putExtra("MODE", "Paid_mode"));
                    finishAffinity();
                }
//                else {
//                    HomeScreenActivity.AppFlyerEvent(SubscriptionActivity.this, "Updated to our server failed","Payment Canceled");
//                }
            }
        }).call();
*/

    }

/*
    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {

        if (responseCode == BillingClient.BillingResponse.OK) {
            if (purchases != null && purchases.size() > 0)
                pushToServer(purchases.get(0).getOrderId());
            else
                pushToServer("1234567");
        }
        else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {

            Log.i("AcheiveAcademicSuccess", "onPurchasesUpdated() - user cancelled the purchase flow - skipping");
        } else {
            Log.w("AcheiveAcademicSuccess", "onPurchasesUpdated() got unknown resultCode: " + responseCode);
        }
    }
*/


    private void payUMoneySetUp() {

        String hashSequence = key + txnid + amount + productinfo + sdkPreferenceManager.get(AppConstants.SDK_NAME, "") + sdkPreferenceManager.get(AppConstants.SDK_EMAIL, "") + salt;
        serverCalculatedHash= hashCal("SHA-512", hashSequence);
    }

    public static String hashCal(String type, String hashString) {
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
    }

    private void payULaunch() throws Exception {
        PaymentParam.Builder builder = new
                PaymentParam.Builder();
        builder.setAmount("300")                          // Payment amount
                .setTxnId("1223")                                             // Transaction ID
                .setPhone("6363132597")                                           // User Phone number
                .setProductName("HappyBeing")                   // Product Name or description
                .setFirstName(sdkPreferenceManager.get(AppConstants.SDK_NAME, ""))                              // User First name
                .setEmail(sdkPreferenceManager.get(AppConstants.SDK_EMAIL, ""))                                            // User Email ID
                .setsUrl(sUrl)                    // Success URL (surl)
                .setfUrl(fUrl)                     //Failure URL (furl)
                .setIsDebug(false)                              // Integration environment - true (Debug)/ false(Production)
                .setKey(key)
                .setMerchantId("150789");                        // Merchant key
        PaymentParam paymentParam;
        paymentParam = builder.build();
        paymentParam.setMerchantHash(serverCalculatedHash);


        PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam,
                this, R.style.AppTheme_default, false);
//set the hash
    }
}

