package com.shuttl.assignment.model;

import java.io.Serializable;

public class FeedItem implements Serializable{

    private String name,title, imageUrl, text, time, description;
    private boolean isLiked;
 
    public FeedItem() {
    }
 
    public FeedItem( String name, String title, String imageurl,
            String text, String time, String description) {
        super();

        this.name = name;
        this.title = title;
        this.imageUrl = imageurl;
        this.text = text;
        this.time = time;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}