package com.example.actc.myapplication.Model;

import java.io.Serializable;

public class Steps implements Serializable {

int idSteps;
String shortDescription;
String description;
String videoURL;
String thumbnailURL ;

    public Steps(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.idSteps = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public int getIdSteps() {
        return idSteps;
    }

    public void setIdSteps(int idSteps) {
        this.idSteps = idSteps;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
