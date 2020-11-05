package com.nsmiles.happybeingsdklib.dagger.application;

import com.nsmiles.happybeingsdklib.Reports.DetailReport.DetailReportActivity;
import com.nsmiles.happybeingsdklib.UI.HappyBeingLaunchScreen;
import com.nsmiles.happybeingsdklib.UI.HomeScreenActivity;
import com.nsmiles.happybeingsdklib.UI.gratitude.ExpressGratitudeOthers;
import com.nsmiles.happybeingsdklib.broadcast.NetworkChangeReceiver;
import com.nsmiles.happybeingsdklib.broadcastnotifications.ShowRelaxBigNotification;
import com.nsmiles.happybeingsdklib.mycoachfragment.fragment.CoachGratitudeFragment;
import com.nsmiles.happybeingsdklib.network.NetworkModule;
import com.nsmiles.happybeingsdklib.wellbeingassessment.activity.WellBeingAssessmentActivity;
import com.nsmiles.happybeingsdklib.wellbeingassessment.activity.WellBeingCategoryActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Sukumar on 4/11/2018.
 */

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class,})

public interface Deps {

    void inject(WellBeingAssessmentActivity nSmilesAssessmentActivity);
    void inject(HappyBeingLaunchScreen happyBeingLaunchScreen);
    //void inject(NatureCalmActivity natureCalmActivity);
    void inject(WellBeingCategoryActivity nSmilesAssessmentCategoryActivity);
    void inject(DetailReportActivity detailReportActivity);
    void inject(NetworkChangeReceiver networkChangeReceiver);
    void inject(ShowRelaxBigNotification showRelaxBigNotification);
    void inject(ExpressGratitudeOthers showRelaxBigNotification);
    void inject(HomeScreenActivity showRelaxBigNotification);
    void inject(CoachGratitudeFragment coachGratitudeFragment);
}
