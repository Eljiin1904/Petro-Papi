package com.example.petropapi;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petropapi.models.gasbuddy.Station;
import com.example.petropapi.data.StationRepository;
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

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MainActivity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private RecyclerView recyclerView;
    private GasStationAdapter gasStationAdapter;
    private StationRepository stationRepository;

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

        stationRepository = new StationRepository(this);
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
        stationRepository.fetchNearbyStations(lat, lng, new StationRepository.StationCallback() {
            @Override
            public void onSuccess(List<Station> stationList, boolean fromCache) {
                updateStationsOnMap(stationList);
                if (fromCache) {
                    Toast.makeText(MainActivity.this, "Showing cached gas station data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String message) {
                Log.e(TAG, "Station repository error: " + message);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateStationsOnMap(List<Station> stationList) {
        if (stationList == null || stationList.isEmpty()) {
            Toast.makeText(MainActivity.this, "No gas stations found", Toast.LENGTH_LONG).show();
            return;
        }
        gasStationAdapter.updateData(stationList);

        if (googleMap != null) {
            googleMap.clear();
            for (Station station : stationList) {
                LatLng latLng = new LatLng(station.getLatitude(), station.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(latLng).title(station.getName()));
            }
            Station firstStation = stationList.get(0);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(firstStation.getLatitude(), firstStation.getLongitude()), 12f));
        }
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
