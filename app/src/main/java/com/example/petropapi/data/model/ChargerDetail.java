package com.example.petropapi.data.model;

public class ChargerDetail {
    private final String connectorType;
    private final String powerKw;
    private final String status;
    private final String pricing;

    public ChargerDetail(String connectorType, String powerKw, String status, String pricing) {
        this.connectorType = connectorType;
        this.powerKw = powerKw;
        this.status = status;
        this.pricing = pricing;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public String getPowerKw() {
        return powerKw;
    }

    public String getStatus() {
        return status;
    }

    public String getPricing() {
        return pricing;
    }
}
