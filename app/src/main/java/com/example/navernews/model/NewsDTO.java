package com.example.navernews.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "News")
public class NewsDTO {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    private String title,description,time,imgURL,link;

    public NewsDTO(String title, String description, String time, String imgURL,String link) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.imgURL = imgURL;
        this.link = link;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
