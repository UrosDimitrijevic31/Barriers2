package com.example.barriers2.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.barriers2.db.AppDatabase;
import com.example.barriers2.db.Location;

import java.util.List;

public class LocationFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<List<Location>> listOfLocation;
    private AppDatabase appDatabase;

    public LocationFragmentViewModel(Application application) {
        super(application);
        listOfLocation = new MutableLiveData<>();

        appDatabase = AppDatabase.getDBinstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Location>> getLocationListObserver() {
        return listOfLocation;
    }

    public void getAllLocationList() {
        List<Location> locationsList = appDatabase.parkingDao().getAllLocationList();
        if(locationsList.size() > 0) {
            listOfLocation.postValue(locationsList);
        } else {
            listOfLocation.postValue(null);
        }
    }

    public void insertLocation(String locName, String locationIP, String description) {
        Location location = new Location();
        location.locationName = locName;
        location.locationIP = locationIP;
        location.description = description;

        appDatabase.parkingDao().insertLocation(location);
        getAllLocationList();
    }

    public void updateLocation(Location location) {
        appDatabase.parkingDao().updateLocation(location);
        getAllLocationList();
    }
    public void deleteLocation(Location location) {
        appDatabase.parkingDao().deleteLocation(location);
        getAllLocationList();
    }

}
