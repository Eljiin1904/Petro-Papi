package com.example.petropapi.network.providers.ev.model;

public class OpenChargeMapConnection {
    private String connectionType;
    private double powerKW;
    private int quantity;

    public String getConnectionType() {
        return connectionType;
    }

    public double getPowerKW() {
        return powerKW;
    }

    public int getQuantity() {
        return quantity;
    }
}
