package com.wsk.hotel_billing;

public class HomeItem {
    private String id, photo, title, rating, description;

    public HomeItem(String id, String photo, String title, String rating, String description) {
        this.id = id;
        this.photo = photo;
        this.title = title;
        this.rating = rating;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getPhoto() {
        return photo;
    }

    public String getTitle() {
        return title;
    }

    public String getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }
}
