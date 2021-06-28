package com.example.barriers2.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Location {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo( name = "locationLabel")
    public String locationLabel = "Location name";

    @ColumnInfo( name = "locationName")
    public String locationName;

    @ColumnInfo( name = "locationIP")
    public String locationIP;

    @ColumnInfo( name = "description")
    public String description;
}
