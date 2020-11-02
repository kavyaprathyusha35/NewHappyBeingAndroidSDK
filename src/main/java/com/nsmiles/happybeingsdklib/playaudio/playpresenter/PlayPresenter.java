package com.nsmiles.happybeingsdklib.playaudio.playpresenter;

/**
 * Created by nSmiles on 11/24/2017.
 */

public interface PlayPresenter {


    void getAudioArgumentDetails();
    void hideAllFields();
    void playAudio();
    void pauseAudio();
    void downloadAudio();
    void backIconPressed();
    void onRestart();
    void onStop();
    void onPause();
    void onDestroy();
    void onResume();
    void doneaudio();
    void repeataudio();
}
