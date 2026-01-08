package com.example.petropapi.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class NearbySearchResponse {
    private List<Place> results;
    private String status;
    @SerializedName("next_page_token")
    private String nextPageToken;

    public List<Place> getResults() {
        return results;
    }

    public String getStatus() {
        return status;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }
}
