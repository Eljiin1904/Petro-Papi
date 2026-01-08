package com.example.petropapi.data.model;

import java.util.List;

public class StationDetails {
    private final StationSummary summary;
    private final List<ChargerDetail> chargers;
    private final LiveData liveData;
    private final CustomerData customerData;
    private final List<String> amenities;

    public StationDetails(StationSummary summary,
                          List<ChargerDetail> chargers,
                          LiveData liveData,
                          CustomerData customerData,
                          List<String> amenities) {
        this.summary = summary;
        this.chargers = chargers;
        this.liveData = liveData;
        this.customerData = customerData;
        this.amenities = amenities;
    }

    public StationSummary getSummary() {
        return summary;
    }

    public List<ChargerDetail> getChargers() {
        return chargers;
    }

    public LiveData getLiveData() {
        return liveData;
    }

    public CustomerData getCustomerData() {
        return customerData;
    }

    public List<String> getAmenities() {
        return amenities;
    }
}
