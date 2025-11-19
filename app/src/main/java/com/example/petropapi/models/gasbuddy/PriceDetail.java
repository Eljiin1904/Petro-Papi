package com.example.petropapi.models.gasbuddy;

public class PriceDetail {
    private String nickname;
    private String postedTime;
    private double price;
    private String formattedPrice;
    private String __typename;

    public String getNickname() {
        return nickname;
    }

    public String getPostedTime() {
        return postedTime;
    }

    public double getPrice() {
        return price;
    }

    public String getFormattedPrice() {
        return formattedPrice;
    }

    public String get__typename() {
        return __typename;
    }
}
