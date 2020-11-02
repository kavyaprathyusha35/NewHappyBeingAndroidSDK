package com.nsmiles.happybeingsdklib.mycoachfragment.presenter;

/**
 * Created by nSmiles on 11/22/2017.
 */

public interface CoachPresenter {

    void hideProgress();
    void defaultCoachData();
    void getMindGymData();
    void pushCoachData();
    void loadOfflineCoachData();

    void upgrade();

    void trailUserDayFiveFeedbackSuggestion();   /// Minimum 4 audio  day 5
    void trailUserDaySevenFeedbackSuggestion();  /// Minimum 5 audio  day 7
    void trailUserModifiedTwentyPercentOffer();
    //void trailUserFortyPercentOffer();           /// Minimum 6 audio  day 10, 11, 12
    //void trailUserTwentyPercentOffer();          /// Minimum 8 audio  day  14
   // void trailUserUnPaidEighteenDayReferFriends();  // Mimimum 10 audio
}
