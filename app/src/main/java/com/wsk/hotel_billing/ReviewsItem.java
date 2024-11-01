package com.wsk.hotel_billing;

public class ReviewsItem {
    String name, city, review;

    public ReviewsItem(String name, String city, String review) {
        this.name = name;
        this.city = city;
        this.review = review;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getReview() {
        return review;
    }
}
