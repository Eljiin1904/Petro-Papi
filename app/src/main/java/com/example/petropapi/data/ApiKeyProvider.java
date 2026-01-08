package com.example.petropapi.data;

import com.example.petropapi.BuildConfig;

public final class ApiKeyProvider {
    private ApiKeyProvider() {
    }

    public static String getNrelApiKey() {
        return BuildConfig.NREL_API_KEY;
    }

    public static String getOpenChargeMapApiKey() {
        return BuildConfig.OPEN_CHARGE_MAP_API_KEY;
    }

    public static String getTomTomApiKey() {
        return BuildConfig.TOMTOM_API_KEY;
    }

    public static String getSmartcarClientId() {
        return BuildConfig.SMARTCAR_CLIENT_ID;
    }

    public static String getSmartcarClientSecret() {
        return BuildConfig.SMARTCAR_CLIENT_SECRET;
    }

    public static String getHereApiKey() {
        return BuildConfig.HERE_API_KEY;
    }

    public static String getChargeHubApiKey() {
        return BuildConfig.CHARGEHUB_API_KEY;
    }

    public static String getMapboxApiKey() {
        return BuildConfig.MAPBOX_API_KEY;
    }

    public static String getEiaApiKey() {
        return BuildConfig.EIA_API_KEY;
    }

    public static String getCollectApiKey() {
        return BuildConfig.COLLECT_API_KEY;
    }

    public static String getApifyApiKey() {
        return BuildConfig.APIFY_API_KEY;
    }
}
