package com.example.petropapi.network.gasbuddy;

public final class GasBuddyQueries {
    private GasBuddyQueries() {
    }

    public static String getStationsQuery() {
        return "query GetStations($lat: Float!, $lng: Float!) { " +
                "stations(lat: $lat, lng: $lng) { " +
                "results { " +
                "address { country line1 line2 locality postalCode region __typename } " +
                "amenities { amenityId imageUrl name __typename } " +
                "badges { badgeId callToAction campaignId clickTrackingUrl description detailsImageUrl detailsImpressionTrackingUrls imageUrl impressionTrackingUrls internalName targetUrl title __typename } " +
                "brands { brandId brandingType imageUrl name __typename } " +
                "currency " +
                "emergencyStatus { hasGas { nickname reportStatus stamp updateDate __typename } hasPower { nickname reportStatus stamp updateDate __typename } hasDiesel { nickname reportStatus stamp updateDate __typename } __typename } " +
                "enterprise " +
                "fuels " +
                "hasActiveOutage " +
                "hours { nextIntervals { close open __typename } openingHours status __typename } " +
                "id " +
                "latitude " +
                "longitude " +
                "name " +
                "prices { cash { nickname postedTime price formattedPrice __typename } " +
                "credit { nickname postedTime price formattedPrice __typename } " +
                "fuelProduct longName __typename } " +
                "__typename " +
                "} " +
                "__typename " +
                "} " +
                "}";
    }
}
