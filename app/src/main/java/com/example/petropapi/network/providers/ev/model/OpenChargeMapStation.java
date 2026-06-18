package com.example.petropapi.network.providers.ev.model;

import java.util.List;

public class OpenChargeMapStation {
    private int id;
    private OpenChargeMapAddressInfo addressInfo;
    private List<OpenChargeMapConnection> connections;
    private OpenChargeMapStatusType statusType;
    private List<OpenChargeMapUserComment> userComments;
    private List<OpenChargeMapMediaItem> mediaItems;

    public int getId() {
        return id;
    }

    public OpenChargeMapAddressInfo getAddressInfo() {
        return addressInfo;
    }

    public List<OpenChargeMapConnection> getConnections() {
        return connections;
    }

    public OpenChargeMapStatusType getStatusType() {
        return statusType;
    }

    public List<OpenChargeMapUserComment> getUserComments() {
        return userComments;
    }

    public List<OpenChargeMapMediaItem> getMediaItems() {
        return mediaItems;
    }
}
