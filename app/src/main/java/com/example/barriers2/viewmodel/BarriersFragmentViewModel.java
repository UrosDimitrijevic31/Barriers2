package com.example.barriers2.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.barriers2.db.AppDatabase;
import com.example.barriers2.db.Barriers;
import com.example.barriers2.db.Location;

import java.util.ArrayList;
import java.util.List;

public class BarriersFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<List<Barriers>> listOfBarriers;
    private AppDatabase appDatabase;
    private Location location;
    public BarriersFragmentViewModel(Application application) {
        super(application);
        listOfBarriers = new MutableLiveData<List<Barriers>>();

        appDatabase = AppDatabase.getDBinstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Barriers>> getBarriersListObserver() {
        return listOfBarriers;
    }

    public void getAllBarriersList() {
        List<Barriers> barriersList = appDatabase.parkingDao().getAllBarriersList();
        if(barriersList.size() > 0) {
            listOfBarriers.postValue(barriersList);
        } else {
            listOfBarriers.postValue(null);
        }
    }

    public void getAllBarriersList(int locationId) {
        List<Barriers> barriersList = appDatabase.parkingDao().getAllBarriersList(locationId);
        if(barriersList.size() > 0) {
            listOfBarriers.postValue(barriersList);
        } else {
            listOfBarriers.postValue(null);
        }
    }


    public void insertBarrier(String barName, int locationId, String ip, String description) {
        Barriers barrier = new Barriers();
        barrier.barriersName = barName;

        List<Location> id = appDatabase.parkingDao().getAllLocationListId();
        barrier.locationId = id.get(0).uid;

        barrier.barrierIP = ip;
        barrier.description = description;
        appDatabase.parkingDao().insertBarriers(barrier);
        getAllBarriersList();
    }

    public void updateBarrier(Barriers barrier) {
        appDatabase.parkingDao().updateBarriers(barrier);
        getAllBarriersList();
    }
    public void deleteBarrier(Barriers barrier) {
        appDatabase.parkingDao().deleteBarriers(barrier);
        getAllBarriersList();
    }

}
