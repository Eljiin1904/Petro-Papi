package com.example.petropapi.data.model;

public class FuelPrice {
    private final String fuelType;
    private final String cashPrice;
    private final String creditPrice;
    private final String formattedCashPrice;
    private final String formattedCreditPrice;

    public FuelPrice(String fuelType,
                     String cashPrice,
                     String creditPrice,
                     String formattedCashPrice,
                     String formattedCreditPrice) {
        this.fuelType = fuelType;
        this.cashPrice = cashPrice;
        this.creditPrice = creditPrice;
        this.formattedCashPrice = formattedCashPrice;
        this.formattedCreditPrice = formattedCreditPrice;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getCashPrice() {
        return cashPrice;
    }

    public String getCreditPrice() {
        return creditPrice;
    }

    public String getFormattedCashPrice() {
        return formattedCashPrice;
    }

    public String getFormattedCreditPrice() {
        return formattedCreditPrice;
    }
}
