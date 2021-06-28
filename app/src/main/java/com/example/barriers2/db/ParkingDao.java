package com.example.barriers2.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ParkingDao {
    @Query("Select * from Location")
    List<Location> getAllLocationList();

    @Query("Select uid from Location")
    List<Location> getAllLocationListId();


    @Insert
    void insertLocation(Location locations);

    @Update
    void updateLocation(Location location);

    @Delete
    void deleteLocation(Location location);

    @Query("Select * from Barriers")
    List<Barriers> getAllBarriersList();

    @Query("Select * from Barriers where locationId = :locId")
    List<Barriers> getAllBarriersList(int locId);

    @Insert
    void insertBarriers(Barriers barriers);

    @Update
    void updateBarriers(Barriers barriers);

    @Delete
    void deleteBarriers(Barriers barriers);

}
