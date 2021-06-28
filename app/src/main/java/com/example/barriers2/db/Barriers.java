package com.example.barriers2.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Barriers {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo( name = "barrierLabel")
    public String barrierLabel = "barrier name";

    @ColumnInfo( name = "barrierName")
    public String barriersName;

    @ColumnInfo( name = "locationLabel")
    public String barrierLocationLabel = "Location name";

    @ColumnInfo( name = "locationId")
    public int locationId;

    @ColumnInfo( name = "barrierIP" )
    public String barrierIP;

    @ColumnInfo( name = "description")
    public String description;


}
