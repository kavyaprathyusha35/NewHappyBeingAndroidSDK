package com.nsmiles.happybeingsdklib.MindGym;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nSmiles on 10/23/2017.
 */

public class AudioDownload implements Parcelable {


    public AudioDownload(){

    }


    private int progress;
    private int currentFileSize;
    private int totalFileSize;
    private String audio_number;



    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getCurrentFileSize() {
        return currentFileSize;
    }

    public void setCurrentFileSize(int currentFileSize) {
        this.currentFileSize = currentFileSize;
    }

    public int getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(int totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getAudio_number() {
        return audio_number;
    }

    public void setAudio_number(String audio_number) {
        this.audio_number = audio_number;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(progress);
        dest.writeInt(currentFileSize);
        dest.writeInt(totalFileSize);
        dest.writeString(audio_number);
    }

    private AudioDownload(Parcel in) {

        progress = in.readInt();
        currentFileSize = in.readInt();
        totalFileSize = in.readInt();
        audio_number = in.readString();
    }

    public static final Parcelable.Creator<AudioDownload> CREATOR = new Parcelable.Creator<AudioDownload>() {
        public AudioDownload createFromParcel(Parcel in) {
            return new AudioDownload(in);
        }

        public AudioDownload[] newArray(int size) {
            return new AudioDownload[size];
        }
    };
}

