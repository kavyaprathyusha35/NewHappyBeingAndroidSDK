package com.nsmiles.happybeingsdklib.diaryfragment;

import java.io.Serializable;

/**
 * Created by Gopal on 29-06-2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public class DiaryData implements Serializable {

    private int audio_id;
    private String id;
    private String user_id;
    private String email;
    private String createdAt;
    private String updatedAt;
    private String date_time;
    private String type_of_gratitude;
    private String title;
    private String description;
    private String subject;
    private String link;
    private String express_your_gratitude;
    private String custom_date;
    private String pic;
    private byte[] pic_byte;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    private String operation;


    public byte[] getPic_byte() {
        return pic_byte;
    }

    public void setPic_byte(byte[] pic_byte) {
        this.pic_byte = pic_byte;
    }




    public int getAudio_id() {
        return audio_id;
    }

    public void setAudio_id(int audio_id) {
        this.audio_id = audio_id;
    }
    public String getCustom_date() {
        return custom_date;
    }

    public void setCustom_date(String custom_date) {
        this.custom_date = custom_date;
    }



    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }



    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }




    public String getExpress_your_gratitude() {
        return express_your_gratitude;
    }

    public void setExpress_your_gratitude(String express_your_gratitude) {
        this.express_your_gratitude = express_your_gratitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getType_of_gratitude() {
        return type_of_gratitude;
    }

    public void setType_of_gratitude(String type_of_gratitude) {
        this.type_of_gratitude = type_of_gratitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}
