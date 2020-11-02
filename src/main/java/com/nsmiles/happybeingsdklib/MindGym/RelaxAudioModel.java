package com.nsmiles.happybeingsdklib.MindGym;

/**
 * Created by nSmiles on 10/25/2017.
 */

public class RelaxAudioModel {

    String id;
    String sync_flag;
    String audio_base_url;
    String audio_title;
    String audio_sub_url;
    String created_at;
    String updated_at;
    String audio_description;
    String download_status;
    String sd_card_path;
    String audio_number;
    int audio_image;
    String duration;
    String favourite;
    int day_number;

    public int getAudio_image() {
        return audio_image;
    }

    public void setAudio_image(int audio_image) {
        this.audio_image = audio_image;
    }

    public String getId() {
        return id;
    }

    public String getSync_flag() {
        return sync_flag;
    }

    public void setSync_flag(String sync_flag) {
        this.sync_flag = sync_flag;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAudio_base_url() {
        return audio_base_url;
    }

    public void setAudio_base_url(String audio_base_url) {
        this.audio_base_url = audio_base_url;
    }

    public String getAudio_title() {
        return audio_title;
    }

    public void setAudio_title(String audio_name) {
        this.audio_title = audio_name;
    }

    public String getAudio_sub_url() {
        return audio_sub_url;
    }

    public void setAudio_sub_url(String audio_sub_url) {
        this.audio_sub_url = audio_sub_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getAudio_description() {
        return audio_description;
    }

    public void setAudio_description(String audio_description) {
        this.audio_description = audio_description;
    }

    public String getDownload_status() {
        return download_status;
    }

    public void setDownload_status(String download_status) {
        this.download_status = download_status;
    }

    public String getSd_card_path() {
        return sd_card_path;
    }

    public void setSd_card_path(String sd_card_path) {
        this.sd_card_path = sd_card_path;
    }

    public String getAudio_number() {
        return audio_number;
    }

    public void setAudio_number(String audio_number) {
        this.audio_number = audio_number;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public int getDay_number() {
        return day_number;
    }

    public void setDay_number(int day_number) {
        this.day_number = day_number;
    }
}
