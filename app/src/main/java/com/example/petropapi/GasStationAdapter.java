package com.example.petropapi;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petropapi.data.model.StationSummary;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GasStationAdapter extends RecyclerView.Adapter<GasStationAdapter.GasStationViewHolder> {

    private final List<StationSummary> stationList;

    public GasStationAdapter(List<StationSummary> initialData) {
        if (initialData == null) {
            initialData = new ArrayList<>();
        }
        this.stationList = new ArrayList<>(initialData);
    }

    public void updateData(List<StationSummary> newStations) {
        stationList.clear();
        if (newStations != null) {
            stationList.addAll(newStations);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GasStationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_gas_station, parent, false);
        return new GasStationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GasStationViewHolder holder, int position) {
        StationSummary station = stationList.get(position);
        holder.bind(station);
    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }

    static class GasStationViewHolder extends RecyclerView.ViewHolder {
        TextView stationName;
        TextView stationAddress;
        TextView stationPrices;
        TextView stationHours;
        ImageView ivStationImage; // <-- ADDED

        public GasStationViewHolder(@NonNull View itemView) {
            super(itemView);
            stationName = itemView.findViewById(R.id.stationName);
            stationAddress = itemView.findViewById(R.id.stationAddress);
            stationPrices = itemView.findViewById(R.id.stationPrices);
            stationHours = itemView.findViewById(R.id.stationHours);
            ivStationImage = itemView.findViewById(R.id.ivStationImage); // <-- ADDED
        }

        public void bind(StationSummary station) {
            // ---- Station Name ----
            stationName.setText(
                    station.getName() != null ? station.getName() : "Unknown Station"
            );

            // ---- Station Address ----
            String addressLine = station.getAddressLine1();
            stationAddress.setText(addressLine != null ? addressLine : "No address available");

            // ---- Station Prices ----
            String priceSummary = station.getPriceSummary();
            stationPrices.setText(!TextUtils.isEmpty(priceSummary) ? priceSummary : "No prices available");

            // ---- Station Hours ----
            String hoursSummary = station.getHoursSummary();
            stationHours.setText(!TextUtils.isEmpty(hoursSummary) ? hoursSummary : "No hours available");

            // ---- Station Image ----
            // For example, if the brand list has the station's main image:
            String imageUrl = station.getImageUrl();

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.drawable.petropapi) // shown while loading
                        .error(R.drawable.petropapi)       // shown on failure
                        .into(ivStationImage);
            } else {
                // If no image URL, use fallback
                ivStationImage.setImageResource(R.drawable.petropapi);
            }
        }
    }
}
