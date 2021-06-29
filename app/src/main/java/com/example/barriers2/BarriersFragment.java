package com.example.barriers2;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barriers2.db.AppDatabase;
import com.example.barriers2.db.Barriers;
import com.example.barriers2.db.Location;
import com.example.barriers2.viewmodel.BarriersFragmentViewModel;
import com.example.barriers2.viewmodel.LocationFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class BarriersFragment extends Fragment implements BarriersListAdapter.HandleBarriersClick {

    private BarriersFragmentViewModel viewModel;
    private TextView noResultTextView;
    private RecyclerView recyclerView;
    private BarriersListAdapter barriersListAdapter;
    private Barriers barrierForEdit;
    private AppDatabase appDatabase;
    private List<Location> locationList;
    int locationId;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        //inflater - pretvara XML u java object
        View view = inflater.inflate(R.layout.fragment_barriers, container, false);
        appDatabase = AppDatabase.getDBinstance(view.getContext());
        //dohvatimo sve elemente koji su nam potrebni iz layouta
        noResultTextView = view.findViewById(R.id.noResultBarriers);
        recyclerView = view.findViewById(R.id.recycler_view_barrier);

        ImageView addNew = view.findViewById(R.id.addNewBarrierImageView);
        addNew.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                showAddBarrierDialog(false);
            }
        });

//        List<Location> locations = new ArrayList<>();
//        Location l = new Location();
//        l.locationIP = "dsad";
//        locations.add(l);
//
//        Location l1 = new Location();
//        locations.add(l1);
//
//        for(Location ll:locations){
//
//        }


        initViewModel();
        initRecyclerView();
        viewModel.getAllBarriersList();

        return view;
    }

    //pravljenje liste item-a
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        barriersListAdapter = new BarriersListAdapter(getActivity(), this);
        recyclerView.setAdapter(barriersListAdapter);
    }

    private  void initViewModel() {
        viewModel = new ViewModelProvider(this).get(BarriersFragmentViewModel.class);
        viewModel.getBarriersListObserver().observe(getViewLifecycleOwner(), new Observer<List<Barriers>>() {
            @Override
            public void onChanged(List<Barriers> barriers) {
                if(barriers == null) {
                    //OVO SE DESI ! ! !, IZ NEKOG RAZLOGA NE UPISE U BARRIERS
                    noResultTextView.setVisibility(View.VISIBLE); //ispise poruku da je prazna lista (nema parking lokacija)
                    recyclerView.setVisibility(View.GONE); //da nema liste itema
                } else {
                    //show in recyclerview
                    barriersListAdapter.setBarriersList(barriers);
                    recyclerView.setVisibility(View.VISIBLE); //da se prikaze lista itema
                    noResultTextView.setVisibility(View.GONE);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showAddBarrierDialog(boolean isForEdit) {
        AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
        View dialogView = getLayoutInflater().inflate(R.layout.layout_dailog_barrier, null);

        EditText editBarrierName = dialogView.findViewById(R.id.edit_barrier_name);
//        EditText editBarrierLocationName = dialogView.findViewById(R.id.edit_barrier_location_name);

        EditText editIpAddress = dialogView.findViewById(R.id.edit_barrier_ip_address);
        EditText editLocationDescription = dialogView.findViewById(R.id.barrier_description);
        AutoCompleteTextView autoCompleteTextView = dialogView.findViewById(R.id.autoCompleteText);

        locationList = appDatabase.parkingDao().getAllLocationList();

        String[] option = new String[locationList.size()];
        int[] optionIDs = new int[locationList.size()];


        for(int i=0; i<locationList.size(); i++){
            Location l = locationList.get(i);
            option[i] = l.locationName;
            optionIDs[i] = l.uid;
        }

        locationId = optionIDs[0];

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.option_item, option);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(), false);

        autoCompleteTextView.setAdapter(arrayAdapter);


        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                locationId = optionIDs[pos];
            }
        });

        TextView createButton = dialogView.findViewById(R.id.barrierCrateButton);
        TextView cancelButton = dialogView.findViewById(R.id.barrierCancelButton);

        if(isForEdit) {
            createButton.setText("Update");
            editBarrierName.setText(barrierForEdit.barriersName);
            editIpAddress.setText(barrierForEdit.barrierIP);
            editLocationDescription.setText(barrierForEdit.description);
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String barrierName = editBarrierName.getText().toString();
                if(TextUtils.isEmpty(barrierName)) {
                    //TREBA DA NE IZLAZI IZ DIALOGA A DA PRIKAZE PORUKU SREDITI ! ! ! !
                    Toast.makeText(getContext(), "Enter barrier name", Toast.LENGTH_SHORT).show();
                    return;
                }
//
//                String locNametext = editBarrierLocationName.getText().toString();

                int locationID = locationId;
//                int locationName = Integer.parseInt(locNametext);
//                if(TextUtils.isEmpty(locationName)) {
//                    //TREBA DA NE IZLAZI IZ DIALOGA A DA PRIKAZE PORUKU SREDITI ! ! ! !
//                    Toast.makeText(getContext(), "Enter location name", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                String ipAddress = editIpAddress.getText().toString();
                if(TextUtils.isEmpty(ipAddress)) {
                    //TREBA DA NE IZLAZI IZ DIALOGA A DA PRIKAZE PORUKU SREDITI ! ! ! !
                    Toast.makeText(getContext(), "Enter IP address", Toast.LENGTH_SHORT).show();
                    return;
                }

                String description = editLocationDescription.getText().toString();
                if(TextUtils.isEmpty(description)) {
                    //TREBA DA NE IZLAZI IZ DIALOGA A DA PRIKAZE PORUKU SREDITI ! ! ! !
                    Toast.makeText(getContext(), "Enter location description", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(isForEdit) {
                    barrierForEdit.barriersName = barrierName;
                    barrierForEdit.locationId = locationID; //treba id
                    barrierForEdit.barrierIP = ipAddress;
                    barrierForEdit.description = description;
                    viewModel.updateBarrier(barrierForEdit);
                } else {

                    viewModel.insertBarrier(barrierName, locationID, ipAddress, description );
                }
                //here we need to call view model

                dialogBuilder.show();
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    @Override
    public void itemClick(Barriers barrier) {

    }

    @Override
    public void removeItem(Barriers barrier) {
        viewModel.deleteBarrier(barrier);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void editItem(Barriers barrier) {
        this.barrierForEdit = barrier;
        showAddBarrierDialog(true);
    }
}