package com.example.navernews.model;

public class NewsDTO {

    private String title,description,time,imgURL;

    public NewsDTO(String title, String description, String time, String imgURL) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.imgURL = imgURL;
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
}