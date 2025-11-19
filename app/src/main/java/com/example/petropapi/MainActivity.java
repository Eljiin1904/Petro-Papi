package com.example.petropapi;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petropapi.models.gasbuddy.GasBuddyStationResponse;
import com.example.petropapi.models.gasbuddy.Station;
import com.example.petropapi.network.GasBuddyApiService;
import com.example.petropapi.network.GraphQLRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MainActivity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private RecyclerView recyclerView;
    private GasStationAdapter gasStationAdapter;
    private GasBuddyApiService gasBuddyApi;

    // NEW: "Search this area" button reference
    private Button btnSearchThisArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the top app bar.
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);
        topAppBar.setTitle("Petro Papi");

        Log.d(TAG, "onCreate called");

        // Check Google Play Services availability
        int playServicesStatus = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (playServicesStatus != ConnectionResult.SUCCESS) {
            Toast.makeText(this, "Google Play Services not available", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Google Play Services error: " + playServicesStatus);
        }



        // Initialize location provider.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Find the map fragment.
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Setup RecyclerView.
        recyclerView = findViewById(R.id.recyclerViewStations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gasStationAdapter = new GasStationAdapter(Collections.emptyList());
        recyclerView.setAdapter(gasStationAdapter);

        // Find the "Search this area" button and set its click listener.
        btnSearchThisArea = findViewById(R.id.btnSearchThisArea);
        btnSearchThisArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchThisAreaClicked();
            }
        });

        // Check or request location permission.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        } else {
            getUserLocation();
        }

        // Build OkHttpClient with headers.
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

        // Build Retrofit.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.gasbuddy.com/graphql/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gasBuddyApi = retrofit.create(GasBuddyApiService.class);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.googleMap = map;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }
        // Initially set camera to Los Angeles.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(34.0522, -118.2437), 12f));

        // When the user starts moving the map, show the "Search this area" button.
        googleMap.setOnCameraMoveStartedListener(reason -> {
            if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                btnSearchThisArea.setVisibility(View.VISIBLE);
            }
        });

        // Optional: Add additional listener on camera idle if desired.
        googleMap.setOnMarkerClickListener(marker -> {
            Log.d(TAG, "Marker clicked: " + marker.getTitle());
            return false; // allow default behavior (info window)
        });
    }

    private void getUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getCurrentLocation(
                    com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                    null
            ).addOnSuccessListener(location -> {
                if (location != null) {
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();
                    Log.d(TAG, "Location found: " + lat + ", " + lng);
                    fetchNearbyStations(lat, lng);
                } else {
                    Log.d(TAG, "Location is null, using fallback coordinates (Punta Gorda, FL)");
                    fetchNearbyStations(26.8946, -82.0456);
                }
            });
        }
    }

    /**
     * Called when the user taps the "Search this area" button.
     * Gets the current map center and triggers a new API call.
     */
    private void onSearchThisAreaClicked() {
        if (googleMap != null) {
            LatLng center = googleMap.getCameraPosition().target;
            double newLat = center.latitude;
            double newLng = center.longitude;
            Log.d(TAG, "Search this area clicked. New center: " + newLat + ", " + newLng);
            fetchNearbyStations(newLat, newLng);
            // Hide the button after search is triggered.
            btnSearchThisArea.setVisibility(View.GONE);
        }
    }

    /**
     * Fetch nearby stations using the GasBuddy API.
     */
    private void fetchNearbyStations(double lat, double lng) {
        Log.d(TAG, "fetchNearbyStations: lat=" + lat + ", lng=" + lng);
        String queryString = "query GetStations($lat: Float!, $lng: Float!) { " +
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
        Map<String, Object> vars = new HashMap<>();
        vars.put("lat", lat);
        vars.put("lng", lng);
        GraphQLRequest request = new GraphQLRequest("GetStations", vars, queryString);

        gasBuddyApi.getStationsData(request).enqueue(new Callback<GasBuddyStationResponse>() {
            @Override
            public void onResponse(Call<GasBuddyStationResponse> call, Response<GasBuddyStationResponse> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.e(TAG, "GasBuddy call failed: code = " + response.code());
                    try {
                        Log.e(TAG, "errorBody: " + (response.errorBody() != null ? response.errorBody().string() : "none"));
                    } catch (IOException e) {
                        Log.e(TAG, "Error reading errorBody", e);
                    }
                    Toast.makeText(MainActivity.this, "Error fetching gas station data", Toast.LENGTH_LONG).show();
                    return;
                }
                if (response.body().getData().getStations() == null ||
                        response.body().getData().getStations().getResults() == null ||
                        response.body().getData().getStations().getResults().isEmpty()) {
                    Log.e(TAG, "No stations found in response");
                    Toast.makeText(MainActivity.this, "No gas stations found", Toast.LENGTH_LONG).show();
                    return;
                }
                // Update RecyclerView with station list.
                List<Station> stationList = response.body().getData().getStations().getResults();
                gasStationAdapter.updateData(stationList);

                // Update map markers.
                if (googleMap != null) {
                    googleMap.clear();
                    for (Station s : stationList) {
                        LatLng latLng = new LatLng(s.getLatitude(), s.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(latLng).title(s.getName()));
                    }
                    // Optionally move camera to the first station.
                    Station firstStation = stationList.get(0);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(firstStation.getLatitude(), firstStation.getLongitude()), 12f));
                }
            }

            @Override
            public void onFailure(Call<GasBuddyStationResponse> call, Throwable t) {
                Log.e(TAG, "GasBuddy API Error: " + t.getMessage());
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "Failed to fetch gas station data", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
