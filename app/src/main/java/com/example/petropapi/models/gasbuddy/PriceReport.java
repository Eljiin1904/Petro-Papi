package com.example.petropapi.models.gasbuddy;

import com.google.gson.annotations.SerializedName;

public class PriceReport {

    // e.g. "regular_gas", "diesel", etc.
    @SerializedName("fuelProduct")
    private String fuelProduct;

    @SerializedName("longName")
    private String longName;

    // Each PriceReport can have "cash" and "credit"
    // which are FuelPrice objects:
    @SerializedName("cash")
    private FuelPrice cash;

    @SerializedName("credit")
    private FuelPrice credit;

    // NEW: Include __typename for additional metadata
    @SerializedName("__typename")
    private String __typename;

    public String getFuelProduct() {
        return fuelProduct;
    }

    public String getLongName() {
        return longName;
    }

    public FuelPrice getCash() {
        return cash;
    }

    public FuelPrice getCredit() {
        return credit;
    }

    public String get__typename() {
        return __typename;
    }
}
