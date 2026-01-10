package com.example.petropapi.data;

import com.example.petropapi.data.model.ChargerDetail;
import com.example.petropapi.data.model.CustomerData;
import com.example.petropapi.data.model.LiveData;
import com.example.petropapi.data.model.StationDetails;
import com.example.petropapi.data.model.StationSummary;

import java.util.List;

public interface StationRepository {
    void fetchStations(double latitude, double longitude, StationRepositoryCallback<List<StationSummary>> callback);

    void fetchStationDetails(String stationId, StationRepositoryCallback<StationDetails> callback);

    void fetchChargerDetails(String stationId, StationRepositoryCallback<List<ChargerDetail>> callback);

    void fetchLiveData(String stationId, StationRepositoryCallback<LiveData> callback);

    void fetchCustomerData(String stationId, StationRepositoryCallback<CustomerData> callback);
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.petropapi.data.local.AppDatabase;
import com.example.petropapi.data.local.StationDao;
import com.example.petropapi.data.local.StationEntity;
import com.example.petropapi.models.gasbuddy.Address;
import com.example.petropapi.models.gasbuddy.GasBuddyStationResponse;
import com.example.petropapi.models.gasbuddy.Station;
import com.example.petropapi.network.GasBuddyApiService;
import com.example.petropapi.network.GraphQLRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StationRepository {
    private static final String TAG = "StationRepository";
    private static final int CACHE_LIMIT = 100;
    private static final long CACHE_RETENTION_MS = 7L * 24L * 60L * 60L * 1000L;

    private final GasBuddyApiService gasBuddyApi;
    private final StationDao stationDao;
    private final ExecutorService ioExecutor;
    private final Handler mainHandler;

    public StationRepository(Context context) {
        this.stationDao = AppDatabase.getInstance(context).stationDao();
        this.ioExecutor = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.gasBuddyApi = createGasBuddyApi();
    }

    public interface StationCallback {
        void onSuccess(List<Station> stations, boolean fromCache);

        void onError(String message);
    }

    public void fetchNearbyStations(double lat, double lng, StationCallback callback) {
        Log.d(TAG, String.format(Locale.US, "fetchNearbyStations lat=%.6f lng=%.6f", lat, lng));
        GraphQLRequest request = new GraphQLRequest("GetStations", buildVars(lat, lng), buildQuery());
        gasBuddyApi.getStationsData(request).enqueue(new Callback<GasBuddyStationResponse>() {
            @Override
            public void onResponse(Call<GasBuddyStationResponse> call, Response<GasBuddyStationResponse> response) {
                if (!response.isSuccessful() || response.body() == null
                        || response.body().getData() == null
                        || response.body().getData().getStations() == null
                        || response.body().getData().getStations().getResults() == null
                        || response.body().getData().getStations().getResults().isEmpty()) {
                    String errorBody = readErrorBody(response);
                    Log.e(TAG, "GasBuddy call failed: code=" + response.code() + " body=" + errorBody);
                    loadCachedStations(lat, lng, callback, "Error fetching gas station data");
                    return;
                }

                List<Station> stations = response.body().getData().getStations().getResults();
                cacheStations(stations, lat, lng);
                callback.onSuccess(stations, false);
            }

            @Override
            public void onFailure(Call<GasBuddyStationResponse> call, Throwable t) {
                Log.e(TAG, "GasBuddy API Error", t);
                loadCachedStations(lat, lng, callback, "Failed to fetch gas station data");
            }
        });
    }

    private void loadCachedStations(double lat, double lng, StationCallback callback, String fallbackMessage) {
        ioExecutor.execute(() -> {
            List<StationEntity> cached = stationDao.getStationsForQuery(lat, lng);
            if (cached.isEmpty()) {
                cached = stationDao.getRecentStations(CACHE_LIMIT);
            }
            List<Station> stations = mapEntitiesToStations(cached);
            mainHandler.post(() -> {
                if (!stations.isEmpty()) {
                    callback.onSuccess(stations, true);
                } else {
                    callback.onError(fallbackMessage);
                }
            });
        });
    }

    private void cacheStations(List<Station> stations, double queryLat, double queryLng) {
        ioExecutor.execute(() -> {
            long now = System.currentTimeMillis();
            List<StationEntity> entities = new ArrayList<>();
            for (Station station : stations) {
                String id = station.getId();
                if (id == null || id.trim().isEmpty()) {
                    id = station.getName() + "-" + station.getLatitude() + "-" + station.getLongitude();
                }
                String addressLine1 = station.getAddress() != null ? station.getAddress().getLine1() : null;
                entities.add(new StationEntity(
                        id,
                        station.getName(),
                        addressLine1,
                        station.getLatitude(),
                        station.getLongitude(),
                        queryLat,
                        queryLng,
                        now
                ));
            }
            stationDao.insertStations(entities);
            stationDao.deleteOlderThan(now - CACHE_RETENTION_MS);
        });
    }

    private List<Station> mapEntitiesToStations(List<StationEntity> entities) {
        List<Station> stations = new ArrayList<>();
        for (StationEntity entity : entities) {
            Address address = entity.getAddressLine1() != null ? new Address(entity.getAddressLine1()) : null;
            stations.add(new Station(entity.getId(), entity.getName(), entity.getLatitude(), entity.getLongitude(), address));
        }
        return stations;
    }

    private GasBuddyApiService createGasBuddyApi() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request newRequest = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .header("accept", "*/*")
                            .header("accept-language", "en-US,en;q=0.9")
                            .header("apollo-require-preflight", "true")
                            .header("gbcsrf", "1.meTUIoHxkwpNhiis")
                            .header("origin", "https://www.gasbuddy.com")
                            .header("referer", "https://www.gasbuddy.com/station/45933")
                            .header("sec-ch-ua", "\"Not A(Brand\";v=\"8\", \"Chromium\";v=\"132\", \"Android WebView\";v=\"132\"")
                            .header("sec-ch-ua-mobile", "?1")
                            .header("sec-ch-ua-platform", "\"Android\"")
                            .header("sec-fetch-dest", "empty")
                            .header("sec-fetch-mode", "cors")
                            .header("sec-fetch-site", "same-origin")
                            .header("user-agent", "Mozilla/5.0 (Linux; Android 11; Pixel 4 XL Build/RQ2A.210305.006) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Mobile Safari/537.36")
                            .build();
                    return chain.proceed(newRequest);
                })
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.gasbuddy.com/graphql/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(GasBuddyApiService.class);
    }

    private Map<String, Object> buildVars(double lat, double lng) {
        Map<String, Object> vars = new HashMap<>();
        vars.put("lat", lat);
        vars.put("lng", lng);
        return vars;
    }

    private String buildQuery() {
        return "query GetStations($lat: Float!, $lng: Float!) { "
                + "stations(lat: $lat, lng: $lng) { "
                + "results { "
                + "address { country line1 line2 locality postalCode region __typename } "
                + "amenities { amenityId imageUrl name __typename } "
                + "badges { badgeId callToAction campaignId clickTrackingUrl description detailsImageUrl detailsImpressionTrackingUrls imageUrl impressionTrackingUrls internalName targetUrl title __typename } "
                + "brands { brandId brandingType imageUrl name __typename } "
                + "currency "
                + "emergencyStatus { hasGas { nickname reportStatus stamp updateDate __typename } hasPower { nickname reportStatus stamp updateDate __typename } hasDiesel { nickname reportStatus stamp updateDate __typename } __typename } "
                + "enterprise "
                + "fuels "
                + "hasActiveOutage "
                + "hours { nextIntervals { close open __typename } openingHours status __typename } "
                + "id "
                + "latitude "
                + "longitude "
                + "name "
                + "prices { cash { nickname postedTime price formattedPrice __typename } "
                + "credit { nickname postedTime price formattedPrice __typename } "
                + "fuelProduct longName __typename } "
                + "__typename "
                + "} "
                + "__typename "
                + "} "
                + "}";
    }

    private String readErrorBody(Response<?> response) {
        if (response == null || response.errorBody() == null) {
            return "";
        }
        try {
            return response.errorBody().string();
        } catch (IOException e) {
            return "";
        }
    }
}
