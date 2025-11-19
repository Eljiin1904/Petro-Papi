package com.example.petropapi;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petropapi.models.gasbuddy.PriceReport;
import com.example.petropapi.models.gasbuddy.Station;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GasStationAdapter extends RecyclerView.Adapter<GasStationAdapter.GasStationViewHolder> {

    private final List<Station> stationList;

    public GasStationAdapter(List<Station> initialData) {
        if (initialData == null) {
            initialData = new ArrayList<>();
        }
        this.stationList = new ArrayList<>(initialData);
    }

    public void updateData(List<Station> newStations) {
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
        Station station = stationList.get(position);
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

        public void bind(Station station) {
            // ---- Station Name ----
            stationName.setText(
                    station.getName() != null ? station.getName() : "Unknown Station"
            );

            // ---- Station Address ----
            if (station.getAddress() != null) {
                stationAddress.setText(station.getAddress().getLine1());
            } else {
                stationAddress.setText("No address available");
            }

            // ---- Station Prices ----
            if (station.getPrices() != null && !station.getPrices().isEmpty()) {
                StringBuilder pricesStr = new StringBuilder();
                for (PriceReport pr : station.getPrices()) {
                    if (pr.getCredit() != null && !TextUtils.isEmpty(pr.getCredit().getFormattedPrice())) {
                        pricesStr.append(pr.getLongName())
                                .append(": ")
                                .append(pr.getCredit().getFormattedPrice())
                                .append(", ");
                    }
                }
                if (pricesStr.length() > 2) {
                    pricesStr.setLength(pricesStr.length() - 2); // remove trailing ", "
                }
                stationPrices.setText(pricesStr.toString());
            } else {
                stationPrices.setText("No prices available");
            }

            // ---- Station Hours ----
            if (station.getHours() != null) {
                String openingHours = station.getHours().getOpeningHours();
                String status = station.getHours().getStatus();
                String hoursText = "Hours: " + (openingHours != null ? openingHours : "N/A")
                        + " (" + (status != null ? status : "N/A") + ")";
                stationHours.setText(hoursText);
            } else {
                stationHours.setText("No hours available");
            }

            // ---- Station Image ----
            // For example, if the brand list has the station's main image:
            String imageUrl = null;
            if (station.getBrands() != null && !station.getBrands().isEmpty()) {
                // This assumes each Brand has an `imageUrl` field
                imageUrl = station.getBrands().get(0).getImageUrl();
            }

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
