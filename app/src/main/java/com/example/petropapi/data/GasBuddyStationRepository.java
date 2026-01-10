package com.example.petropapi.data;

import com.example.petropapi.data.model.ChargerDetail;
import com.example.petropapi.data.model.CustomerData;
import com.example.petropapi.data.model.FuelPrice;
import com.example.petropapi.data.model.LiveData;
import com.example.petropapi.data.model.StationDetails;
import com.example.petropapi.data.model.StationSummary;
import com.example.petropapi.models.gasbuddy.GasBuddyStationResponse;
import com.example.petropapi.models.gasbuddy.PriceReport;
import com.example.petropapi.models.gasbuddy.Station;
import com.example.petropapi.network.GasBuddyApiService;
import com.example.petropapi.network.GasBuddyRetrofitClient;
import com.example.petropapi.network.GraphQLRequest;
import com.example.petropapi.network.gasbuddy.GasBuddyQueries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GasBuddyStationRepository implements StationRepository {
    private static final String PROVIDER_NAME = "GasBuddy";
    private final GasBuddyApiService gasBuddyApiService;

    public GasBuddyStationRepository() {
        this.gasBuddyApiService = GasBuddyRetrofitClient.getService();
    }

    @Override
    public void fetchStations(double latitude, double longitude, StationRepositoryCallback<List<StationSummary>> callback) {
        String queryString = GasBuddyQueries.getStationsQuery();
        Map<String, Object> vars = new HashMap<>();
        vars.put("lat", latitude);
        vars.put("lng", longitude);
        GraphQLRequest request = new GraphQLRequest("GetStations", vars, queryString);

        gasBuddyApiService.getStationsData(request).enqueue(new Callback<GasBuddyStationResponse>() {
            @Override
            public void onResponse(Call<GasBuddyStationResponse> call, Response<GasBuddyStationResponse> response) {
                if (!response.isSuccessful() || response.body() == null ||
                        response.body().getData() == null ||
                        response.body().getData().getStations() == null ||
                        response.body().getData().getStations().getResults() == null) {
                    callback.onError(new IllegalStateException("GasBuddy response was empty"));
                    return;
                }

                List<Station> stationList = response.body().getData().getStations().getResults();
                List<StationSummary> summaries = new ArrayList<>();
                for (Station station : stationList) {
                    summaries.add(mapToSummary(station));
                }
                callback.onSuccess(summaries);
            }

            @Override
            public void onFailure(Call<GasBuddyStationResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void fetchStationDetails(String stationId, StationRepositoryCallback<StationDetails> callback) {
        callback.onError(new UnsupportedOperationException("GasBuddy station details require a detail endpoint."));
    }

    @Override
    public void fetchChargerDetails(String stationId, StationRepositoryCallback<List<ChargerDetail>> callback) {
        callback.onError(new UnsupportedOperationException("GasBuddy does not provide EV charger details."));
    }

    @Override
    public void fetchLiveData(String stationId, StationRepositoryCallback<LiveData> callback) {
        callback.onError(new UnsupportedOperationException("GasBuddy live data not available in current integration."));
    }

    @Override
    public void fetchCustomerData(String stationId, StationRepositoryCallback<CustomerData> callback) {
        callback.onError(new UnsupportedOperationException("GasBuddy customer data not available in current integration."));
    }

    private StationSummary mapToSummary(Station station) {
        String addressLine1 = station.getAddress() != null ? station.getAddress().getLine1() : null;
        String locality = station.getAddress() != null ? station.getAddress().getLocality() : null;
        String region = station.getAddress() != null ? station.getAddress().getRegion() : null;
        String postalCode = station.getAddress() != null ? station.getAddress().getPostalCode() : null;
        String imageUrl = null;
        if (station.getBrands() != null && !station.getBrands().isEmpty()) {
            imageUrl = station.getBrands().get(0).getImageUrl();
        }
        List<FuelPrice> fuelPrices = mapPrices(station.getPrices());
        String priceSummary = buildPriceSummary(fuelPrices);
        String hoursSummary = null;
        if (station.getHours() != null) {
            String openingHours = station.getHours().getOpeningHours();
            String status = station.getHours().getStatus();
            hoursSummary = "Hours: " + (openingHours != null ? openingHours : "N/A")
                    + " (" + (status != null ? status : "N/A") + ")";
        }

        return new StationSummary(
                station.getId(),
                station.getName(),
                addressLine1,
                locality,
                region,
                postalCode,
                station.getLatitude(),
                station.getLongitude(),
                priceSummary,
                hoursSummary,
                imageUrl,
                PROVIDER_NAME,
                fuelPrices
        );
    }

    private List<FuelPrice> mapPrices(List<PriceReport> prices) {
        if (prices == null || prices.isEmpty()) {
            return Collections.emptyList();
        }
        List<FuelPrice> results = new ArrayList<>();
        for (PriceReport price : prices) {
            String fuelType = price.getLongName();
            String cashPrice = price.getCash() != null ? String.valueOf(price.getCash().getPrice()) : null;
            String creditPrice = price.getCredit() != null ? String.valueOf(price.getCredit().getPrice()) : null;
            String formattedCash = price.getCash() != null ? price.getCash().getFormattedPrice() : null;
            String formattedCredit = price.getCredit() != null ? price.getCredit().getFormattedPrice() : null;
            results.add(new FuelPrice(fuelType, cashPrice, creditPrice, formattedCash, formattedCredit));
        }
        return results;
    }

    private String buildPriceSummary(List<FuelPrice> prices) {
        if (prices == null || prices.isEmpty()) {
            return "No prices available";
        }
        StringBuilder builder = new StringBuilder();
        for (FuelPrice price : prices) {
            if (price.getFormattedCreditPrice() != null) {
                builder.append(price.getFuelType())
                        .append(": ")
                        .append(price.getFormattedCreditPrice())
                        .append(", ");
            } else if (price.getFormattedCashPrice() != null) {
                builder.append(price.getFuelType())
                        .append(": ")
                        .append(price.getFormattedCashPrice())
                        .append(", ");
            }
        }
        if (builder.length() > 2) {
            builder.setLength(builder.length() - 2);
        }
        return builder.toString();
    }
}
