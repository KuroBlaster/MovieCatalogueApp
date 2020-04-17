package com.bcit.moviecatalogueapp;

public class Movie {

    public String title;
    public String description;
    public String link;

    public Movie() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Movie(String Title, String Description, String link) {
        this.title = Title;
        this.description = Description;
        this.link = link;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
