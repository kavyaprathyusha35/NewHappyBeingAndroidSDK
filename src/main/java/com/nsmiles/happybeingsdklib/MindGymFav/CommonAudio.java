package com.nsmiles.happybeingsdklib.MindGymFav;

/**
 * Created by nSmiles on 12/10/2017.
 */

public class CommonAudio {

    String[] audio_title;
    String[] base_url;
    String[] sub_url;
    String[] audio_number;
    String[] day_number;
    String[] duration;
    CommonAudio commonAudio;
    int audio_size;

    String day_title;
    String day_base_url;
    String day_sub_url;
    String day_audio_number;
    String day_day_number;
    String day_duration;

    public String[] getAudio_title() {
        return audio_title;
    }

    public void setAudio_title(String[] audio_title) {
        this.audio_title = audio_title;
    }

    public String[] getBase_url() {
        return base_url;
    }

    public void setBase_url(String[] base_url) {
        this.base_url = base_url;
    }

    public String[] getSub_url() {
        return sub_url;
    }

    public void setSub_url(String[] sub_url) {
        this.sub_url = sub_url;
    }

    public String[] getAudio_number() {
        return audio_number;
    }

    public void setAudio_number(String[] audio_number) {
        this.audio_number = audio_number;
    }

    public String[] getDay_number() {
        return day_number;
    }

    public void setDay_number(String[] day_number) {
        this.day_number = day_number;
    }

    public String[] getDuration() {
        return duration;
    }

    public void setDuration(String[] duration) {
        this.duration = duration;
    }

    public int getAudio_size() {
        return audio_size;
    }

    public void setAudio_size(int audio_size) {
        this.audio_size = audio_size;
    }

    public String getDay_title() {
        return day_title;
    }

    public void setDay_title(String day_title) {
        this.day_title = day_title;
    }

    public String getDay_base_url() {
        return day_base_url;
    }

    public void setDay_base_url(String day_base_url) {
        this.day_base_url = day_base_url;
    }

    public String getDay_sub_url() {
        return day_sub_url;
    }

    public void setDay_sub_url(String day_sub_url) {
        this.day_sub_url = day_sub_url;
    }

    public String getDay_audio_number() {
        return day_audio_number;
    }

    public void setDay_audio_number(String day_audio_number) {
        this.day_audio_number = day_audio_number;
    }

    public String getDay_day_number() {
        return day_day_number;
    }

    public void setDay_day_number(String day_day_number) {
        this.day_day_number = day_day_number;
    }

    public String getDay_duration() {
        return day_duration;
    }

    public void setDay_duration(String day_duration) {
        this.day_duration = day_duration;
    }

    public CommonAudio getCommonAudio(int position) {

        commonAudio = new CommonAudio();

        base_url = new String[]{
                "https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/",
                "https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/",
                "https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/",
                "https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/",
                "https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/",
                "https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/",
                "https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/",
                "https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/",
                "https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/"

        };

        sub_url = new String[]{

                "BELLY-BREATHING.mp3",
                "4-7-8-BREATHING.mp3",
                "Counting-Numbers.mp3",
                "Tense-and-relax.mp3",
                "Calmness-meditation.mp3",
                "self-comapssion-meditation.mp3",
                "Mindful-Body-Scan.mp3",
                "Noisy-Breathing-exercise.mp3",
                "Following-the-Out-Breath.mp3",
        };

        audio_title = new String[]{

                "Relax from stress",
                "Calm down when angry or upset",
                "Overcome fear and anxiety",
                "Reduce Irritation or frustration",
                "Experience calmness after busy day",
                "Overcome sadness",
                "Relax from physical pain",
                "Drift to peaceful deep sleep",
                "Focus breathing to improve concentration",

        };

        audio_number = new String[]{
                "101",
                "102",
                "103",
                "104",
                "105",
                "106",
                "107",
                "108",
                "109",

        };


        day_number = new String[]{

                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
        };


        duration = new String[]{

                "5",
                "9",
                "9",
                "9",
                "9",
                "9",
                "9",
                "9",
                "9",
        };

        day_base_url = base_url[position];
        day_sub_url = sub_url[position];
        day_audio_number = audio_number[position];
        day_title = audio_title[position];
        day_day_number = day_number[position];
        day_duration = duration[position];

        commonAudio.setDay_base_url(day_base_url);
        commonAudio.setDay_sub_url(day_sub_url);
        commonAudio.setDay_audio_number(day_audio_number);
        commonAudio.setDay_title(day_title);
        commonAudio.setDay_day_number(day_day_number);
        commonAudio.setDay_duration(day_duration);
        return commonAudio;
    }

}
