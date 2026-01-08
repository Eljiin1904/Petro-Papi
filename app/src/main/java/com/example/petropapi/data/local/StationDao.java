package com.example.petropapi.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStations(List<StationEntity> stations);

    @Query("SELECT * FROM stations WHERE queryLatitude = :queryLat AND queryLongitude = :queryLng ORDER BY lastUpdated DESC")
    List<StationEntity> getStationsForQuery(double queryLat, double queryLng);

    @Query("SELECT * FROM stations ORDER BY lastUpdated DESC LIMIT :limit")
    List<StationEntity> getRecentStations(int limit);

    @Query("DELETE FROM stations WHERE lastUpdated < :cutoff")
    void deleteOlderThan(long cutoff);
}
