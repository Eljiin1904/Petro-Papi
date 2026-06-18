package com.example.petropapi.data.model;

import java.util.List;

public class CustomerData {
    private final double rating;
    private final int reviewCount;
    private final List<String> photoUrls;

    public CustomerData(double rating, int reviewCount, List<String> photoUrls) {
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.photoUrls = photoUrls;
    }

    public double getRating() {
        return rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }
}
