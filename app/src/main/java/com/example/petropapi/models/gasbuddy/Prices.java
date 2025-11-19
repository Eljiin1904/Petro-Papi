package com.example.petropapi.models.gasbuddy;

public class Prices {
    private PriceDetail cash;
    private PriceDetail credit;
    private String fuelProduct;
    private String longName;
    private String __typename;

    public PriceDetail getCash() {
        return cash;
    }

    public PriceDetail getCredit() {
        return credit;
    }

    public String getFuelProduct() {
        return fuelProduct;
    }

    public String getLongName() {
        return longName;
    }

    public String get__typename() {
        return __typename;
    }
}
