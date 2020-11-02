package com.nsmiles.happybeingsdklib.playaudio.playview;

/**
 * Created by nSmiles on 11/24/2017.
 */

public interface PlayView {

    void displayDownload();
    void displayDownloadProgress();
    void displayPlay();
    void displayPause();
    void displayPlayProgress();
    void displayAllTimer();
    void disableDownload();
    void displayRepeatDOne();
    void hidedisplayRepeatDOne();

    void setAudioName(String audio_name);
    void setAudioDay(String audio_day);
    void setTotalDuration(String total_duration);
    void setRemainingDuration(String remaining_duration);
    void updateProgressWave(int progress);


    void hideAllField();
    void hideDownloadAll();
    void hideProgress();

    void hideDayAfter31();


}
