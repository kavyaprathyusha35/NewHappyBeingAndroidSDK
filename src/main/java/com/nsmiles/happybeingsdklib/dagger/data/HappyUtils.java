package com.nsmiles.happybeingsdklib.dagger.data;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.nsmiles.happybeingsdklib.R;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import androidx.appcompat.app.AlertDialog;

/**
 * Created by Sukumar on 4/28/2018.
 */

public class HappyUtils {

    private Calendar mInstance;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat outputDateFormat;
    private Date date;
    private Context context;
    private Snackbar snackbar;

    @Inject
    public HappyUtils(Context context) {
        this.context = context;
        if (mInstance == null) {
            mInstance = Calendar.getInstance();
        }
    }

    @SuppressLint("SimpleDateFormat")
    public String DD_MM_YYYY() {

        if (mInstance == null) {
            mInstance = Calendar.getInstance();
        }
        dateFormat = new SimpleDateFormat("dd mm yyyy");
        date = new Date(mInstance.getTimeInMillis());
        return dateFormat.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public String DD_MM_YYYY_T() {

        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        date = new Date(mInstance.getTimeInMillis());
        return dateFormat.format(date);
    }

    public String getExpiryDate(int moths_to_be_added) {
        Calendar gc = Calendar.getInstance();
        gc.add(Calendar.MONTH, moths_to_be_added);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date tomorrow = gc.getTime();
        return dateFormat.format(tomorrow);
    }

    public String parseIOS_to_DDMMYYYY(String time) {
        String data="";
        dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        outputDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        try {
            date = dateFormat.parse(time);
            data = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    @SuppressLint("HardwareIds")
    public String getDeviceId() {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    @SuppressLint("SimpleDateFormat")
    public String startTime() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        date = new Date(mInstance.getTimeInMillis());
        return dateFormat.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public String endTime() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        date = new Date(mInstance.getTimeInMillis());
        return dateFormat.format(date);
    }

    public Long currentTimeMilliSecond() {
        return mInstance.getTimeInMillis();
    }

    public String convertMilliSecondToDateFormat(Long milliSecond) {

        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        date = new Date(milliSecond);
        return dateFormat.format(date);
    }


    public static String device_info() {

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE, version_edit;
        Double release;
        String codeName = "", all = "NONE";


        try {
            version_edit = versionRelease;
            Log.d("lllll", version_edit.length() + "");
            if (version_edit.length() > 3) {
                version_edit = version_edit.substring(0, 3);
            }
            release = Double.parseDouble(version_edit);

            Log.e("MyActivity", "manufacturer " + manufacturer
                    + " \n model " + model
                    + " \n version " + version
                    + " \n versionRelease " + versionRelease
            );

            if (release >= 4.1 && release < 4.4) codeName = "Jelly Bean";
            else if (release < 5) codeName = "Kit Kat";
            else if (release < 6) codeName = "Lollipop";
            else if (release < 7) codeName = "Marshmallow";
            else if (release < 8) codeName = "Nougat";
            else if (release < 9) codeName = "Oreo";


// Nougat_7.0_24  E/MyActivity:   manufacturer ManufacturerX
            //////   model SM-T310
            //   version 19
            //   versionRelease 4.4.2

            all = "ANDROID_" + codeName.concat("_").concat(versionRelease.concat("_").concat(String.valueOf(version)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return all;
    }

    public static void ShowSnackBar(View view, String message) {
        try {
            Snackbar mSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
            View mView = mSnackbar.getView();
            TextView mTextView = (TextView) mView.findViewById(com.google.android.material.R.id.snackbar_text);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            else
                mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            mSnackbar.show();




            /*Log.d("LayoutParamsView",view.toString());
            snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
            view = snackbar.getView();
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.BOTTOM;
            view.setLayoutParams(params);
            snackbar.show();;*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void toast(Context context, String message) {

        try {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            assert wm != null;
            Display display = wm.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            View toastView = toast.getView(); //This'll return the default View of the Toast.

        /* And now you can get the TextView of the default View of the Toast. */
            TextView toastMessage = toastView.findViewById(android.R.id.message);
            toastMessage.setTextSize(14);
            toastMessage.setTextColor(Color.WHITE);
            //  toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_launcher, 0, 0, 0);
            toastMessage.setGravity(Gravity.CENTER);
           // toastMessage.setPadding(8, 4, 8, 4);
           // toastMessage.setCompoundDrawablePadding(4);
            //toastMessage.setMinWidth(width);
            //toastMessage.setMinHeight(100);
           // toastMessage.setMaxHeight(height);
           // toastMessage.setMaxWidth(width);
            //noinspection deprecation
            toastView.setBackgroundColor(context.getResources().getColor(R.color.toast));
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*Show Alert dialog with message and button*/
    public  void alert(Activity activity, String title, String message, String button_name) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyAlertDialogMaterialStyle);
            //noinspection deprecation
            builder.setMessage(message)
                    .setCancelable(true)
                    .setTitle(Html.fromHtml("<font color='#F26722'>" + title + "</font>"))
                    .setPositiveButton(button_name, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    protected void animateView(final View view, final int toVisibility, float toAlpha, int duration) {
        try {
            boolean show = toVisibility == View.VISIBLE;
            if (show) {
                view.setAlpha(0);
            }
            view.setVisibility(View.VISIBLE);
            view.animate()
                    .setDuration(duration)
                    .alpha(show ? toAlpha : 0)
                    .setListener( new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            view.setVisibility(toVisibility);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void debugLog(String tag, String log) {
        Log.d(tag, log);
    }

    protected void infoLog(String tag, String log) {
        Log.d(tag, log);
    }

    protected void errorLog(String tag, String log) {
        Log.e(tag, log);
    }

    protected void errorLog(String tag, String log, Throwable t) {
        Log.e(tag, log, t);

    }

    protected void viewLog(String tag, String log) {
        Log.v(tag, log);
    }


    public boolean isNetworkAvailable(Activity activity) {
/*
        boolean isConnectedWifi = false;
        boolean isConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressWarnings("deprecation") NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equals(ni.getTypeName()))
                if (ni.isConnected())
                    isConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase(ni.getTypeName()))
                if (ni.isConnected())
                    isConnectedMobile = true;
        }
        return isConnectedWifi || isConnectedMobile;
*/
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) return false;
        NetworkInfo.State network = info.getState();
        return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);

    }

    public float round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public boolean hasText(EditText editText, String message) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                //noinspection deprecation
                editText.setError(Html.fromHtml("<font color='red'>" + message + "</font>"));
            } else {
                editText.setError(message);
            }
            return false;
        }
        return true;
    }

    public boolean isValid(EditText email, String message) {
        email.setError(null);
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email.getText().toString().trim();
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            return true;
        } else {
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                //noinspection deprecation
                email.setError(Html.fromHtml("<font color='red'>" + message + "</font>"));
            } else {
                email.setError(message);
            }
            return false;
        }
    }
    public boolean hasTextMobile(EditText editText, String message) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() < 6) {
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                //noinspection deprecation
                editText.setError(Html.fromHtml("<font color='red'>" + message + "</font>"));
            } else {
                editText.setError(message);
            }
            return false;
        }

        return true;
    }

    public boolean isValidPhoneNumber(EditText editText, String abrevation) {
        String phone = editText.getText().toString();
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

}
