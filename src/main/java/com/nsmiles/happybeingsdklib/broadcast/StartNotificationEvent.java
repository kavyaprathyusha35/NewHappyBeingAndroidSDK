package com.nsmiles.happybeingsdklib.broadcast;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.nsmiles.happybeingsdklib.UI.HappyBeingLaunchScreen;
import com.nsmiles.happybeingsdklib.UI.HomeScreenActivity;
import com.nsmiles.happybeingsdklib.Utils.CommonUtils;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by nSmiles on 11/20/2017.
 */

public class StartNotificationEvent extends BroadcastReceiver {

    CommonUtils commonUtils;
    int notificationId = 0;
    Uri uri;
    Intent i;
    PendingIntent pIntent;
    String from;
    private SharedPreferences prefs;
    private boolean isPaid = false;
    private String userMobileISDCode;
    private String login;

    @Override
    public void onReceive(Context context, Intent intent) {

        try {

            if (intent.hasExtra("notificationId")) {
                notificationId = intent.getIntExtra("notificationId", 0);
            }
            if (intent.hasExtra("FROM")) {
                from = intent.getStringExtra("FROM");
            }

            commonUtils = new CommonUtils();
            i = new Intent(context, HappyBeingLaunchScreen.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            pIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);

            if (from != null && from.length() > 0) {

                if (from.equalsIgnoreCase("UPGRADE")) {
                    uri = Uri.parse("market://details?id=" + context.getPackageName());
                    i = new Intent(Intent.ACTION_VIEW, uri);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pIntent = PendingIntent.getActivity(context, 0, i,
                            PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);
                }

                else if (from.equalsIgnoreCase("GRATITUDE")) {
                    i = new Intent(context, HomeScreenActivity.class);
                    i.putExtra("GO_GRATITUDE", "GRATITUDE");
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pIntent = PendingIntent.getActivity(context, 0, i,
                            PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);


                }

                else if (from.equalsIgnoreCase("HAPPY_MOMENT")) {
                    i = new Intent(context, HomeScreenActivity.class);
                    i.putExtra("GO_HAPPY_MOMENT", "HAPPY_MOMENT");
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pIntent = PendingIntent.getActivity(context, 0, i,
                            PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);
                }


                else if (from.equalsIgnoreCase("RELAX")) {
                    i = new Intent(context, HomeScreenActivity.class);
                    i.putExtra("GO_RELAX", "RELAX");
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pIntent = PendingIntent.getActivity(context, 0, i,
                            PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);

                } else if (from.equalsIgnoreCase("COACH")) {
                    i = new Intent(context, HomeScreenActivity.class);
                    i.putExtra("GO_COACH", "COACH");
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pIntent = PendingIntent.getActivity(context, 0, i,
                            PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);

                } else if (from.equalsIgnoreCase("SUBSCRIBE")) {
                    prefs = context.getSharedPreferences("HAPPY_BEING", MODE_PRIVATE);
                    login = prefs.getString("user_login", "");
                        loadDefault(context);

                }

                else if (from.equalsIgnoreCase("DAILY")) {
                    i = new Intent(context, HomeScreenActivity.class);
                    i.putExtra("GO_GOAL", "GOAL");
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pIntent = PendingIntent.getActivity(context, 0, i,
                            PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);

                }

                else if (from.equalsIgnoreCase("WEEKLY")) {
                    i = new Intent(context, HomeScreenActivity.class);
                    i.putExtra("GO_GOAL", "GOAL");
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pIntent = PendingIntent.getActivity(context, 0, i,
                            PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);

                }
                else {
                    loadSplashScreen(context);
                }

            }

            pIntent.send(context, 0, i);
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(notificationId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDefault(Context context) {
        i = new Intent(context, HomeScreenActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pIntent = PendingIntent.getActivity(context, 0, i,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void loadSplashScreen(Context context){
        i = new Intent(context, HappyBeingLaunchScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pIntent = PendingIntent.getActivity(context, 0, i,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
