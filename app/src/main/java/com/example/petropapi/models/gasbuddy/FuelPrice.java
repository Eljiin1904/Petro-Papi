package com.example.petropapi.models.gasbuddy;

import com.google.gson.annotations.SerializedName;

public class FuelPrice {

    @SerializedName("price")
    private double price;

    @SerializedName("postedTime")
    private String postedTime;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("formattedPrice")
    private String formattedPrice;

    public double getPrice() {
        return price;
    }

    public String getPostedTime() {
        return postedTime;
    }

    public String getNickname() {
        return nickname;
    }

    public String getFormattedPrice() {
        return formattedPrice;
    }
}
