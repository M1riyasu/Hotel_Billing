package com.wsk.hotel_billing;

import java.util.List;

public class DetailsItem {
    private String id, title;
    private List<String> rating;
    private List<ReviewsItem> review;
    private List<RoomsItem> room;

    public DetailsItem(String id, String title, List<String> rating, List<ReviewsItem> review, List<RoomsItem> room) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.review = review;
        this.room = room;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getRating() {
        return rating;
    }

    public List<ReviewsItem> getReview() {
        return review;
    }

    public List<RoomsItem> getRoom() {
        return room;
    }
}
