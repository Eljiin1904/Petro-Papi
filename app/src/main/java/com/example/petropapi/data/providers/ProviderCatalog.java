package com.example.petropapi.data.providers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ProviderCatalog {
    private ProviderCatalog() {
    }

    public static List<ProviderInfo> getEvProviders() {
        List<ProviderInfo> providers = new ArrayList<>();
        providers.add(new ProviderInfo(
                "NREL",
                "EV/Fuel (Government)",
                "US/Canada",
                "Public station details",
                "Government-grade alternative fuel data",
                "Open Government"
        ));
        providers.add(new ProviderInfo(
                "Open Charge Map",
                "EV (Open Registry)",
                "Global",
                "Station + connector details, community updates",
                "Open-source EV station registry",
                "Open Data"
        ));
        providers.add(new ProviderInfo(
                "TomTom",
                "EV (Dynamic availability)",
                "Global",
                "Availability + live status",
                "Real-time availability checks",
                "Commercial/Freemium"
        ));
        providers.add(new ProviderInfo(
                "Smartcar",
                "Vehicle telematics",
                "Global",
                "Battery state, location, vehicle data",
                "Vehicle-level live data",
                "Commercial/Freemium"
        ));
        providers.add(new ProviderInfo(
                "PlugShare",
                "EV (Community)",
                "Global",
                "Station reviews + photos",
                "Community-driven station details",
                "Commercial"
        ));
        providers.add(new ProviderInfo(
                "HERE",
                "EV routing",
                "Global",
                "Charge points along routes",
                "Route planning with station details",
                "Commercial/Freemium"
        ));
        providers.add(new ProviderInfo(
                "ChargeHub",
                "EV (North America)",
                "US/Canada/Mexico",
                "POI + dynamic status",
                "North American station coverage",
                "Commercial"
        ));
        providers.add(new ProviderInfo(
                "Mapbox ChargeFinder",
                "EV + POI",
                "Global",
                "Stations + nearby amenities",
                "Stop planning with amenities",
                "Commercial/Freemium"
        ));
        return Collections.unmodifiableList(providers);
    }

    public static List<ProviderInfo> getFuelProviders() {
        List<ProviderInfo> providers = new ArrayList<>();
        providers.add(new ProviderInfo(
                "EIA",
                "Fuel (Government)",
                "United States",
                "Regional average prices",
                "Market trend analysis",
                "Open Government"
        ));
        providers.add(new ProviderInfo(
                "NREL",
                "Fuel (Alternative)",
                "United States",
                "Alt-fuel station details",
                "Alternative fuel locations",
                "Open Government"
        ));
        providers.add(new ProviderInfo(
                "EU Weekly Oil Bulletin",
                "Fuel (Government)",
                "Europe",
                "Weekly price datasets",
                "EU price benchmarks",
                "Open Data"
        ));
        providers.add(new ProviderInfo(
                "CollectAPI",
                "Fuel (Commercial)",
                "Global",
                "Real-time city-level prices",
                "Quick price lookups",
                "Freemium"
        ));
        providers.add(new ProviderInfo(
                "TomTom Fuel Prices",
                "Fuel (Commercial)",
                "Global",
                "Station details + prices",
                "Navigation apps",
                "Freemium"
        ));
        providers.add(new ProviderInfo(
                "Apify GasBuddy Scraper",
                "Fuel (Scraped)",
                "United States",
                "Station metadata + prices",
                "Detailed station metadata",
                "Freemium"
        ));
        return Collections.unmodifiableList(providers);
    }
}
