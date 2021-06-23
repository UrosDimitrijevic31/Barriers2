package com.example.barriers2.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Location.class, Barriers.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ParkingDao parkingDao();
    public static AppDatabase INSTANCE;

    public static AppDatabase getDBinstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "Location")
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }
}
