package com.example.barriers2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barriers2.db.Location;
import com.example.barriers2.viewmodel.LocationFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class LocationFragment extends Fragment implements LocationListAdapter.HandleLocationClick {

    private LocationFragmentViewModel viewModel;
    private TextView noResultTextView;
    private RecyclerView recyclerView;
    private LocationListAdapter locationListAdapter;
    private Location locationForEdit;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflater - pretvara XML u java object
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        //dohvatimo sve elemente koji su nam potrebni zi layouta
        noResultTextView = view.findViewById(R.id.noResult);
        recyclerView = view.findViewById(R.id.recycler_view);

        ImageView addNew = view.findViewById(R.id.addNewLocationImageView);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCategoryDialog(false);
            }
        });

        initViewModel();
        initRecyclerView();
        viewModel.getAllLocationList();

        return view;
    }

    //pravljenje liste item-a
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        locationListAdapter = new LocationListAdapter(getActivity(), this);
        recyclerView.setAdapter(locationListAdapter);
    }

    private  void initViewModel() {
        viewModel = new ViewModelProvider(this).get(LocationFragmentViewModel.class);
        viewModel.getLocationListObserver().observe(getViewLifecycleOwner(), new Observer<List<Location>>() {
            @Override
            public void onChanged(List<Location> locations) {
                if(locations == null) {
                    noResultTextView.setVisibility(View.VISIBLE); //ispise poruku da je prazna lista (nema parking lokacija)
                    recyclerView.setVisibility(View.GONE); //da nema liste itema
                } else {
                    //show in recyclerview
                    locationListAdapter.setLocationList(locations);
                    recyclerView.setVisibility(View.VISIBLE); //da se prikaze lista itema
                    noResultTextView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void showAddCategoryDialog(boolean isForEdit) {
        AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
        View dialogView = getLayoutInflater().inflate(R.layout.layout_dailog_location, null);

        EditText editName = dialogView.findViewById(R.id.edit_name);

        EditText editIpAddress = dialogView.findViewById(R.id.edit_ip_address);
        EditText editLocationDescription = dialogView.findViewById(R.id.location_description);
        TextView createButton = dialogView.findViewById(R.id.crateButton);
        TextView cancelButton = dialogView.findViewById(R.id.cancelButton);

        if(isForEdit) {
            createButton.setText("Update");
            editName.setText(locationForEdit.locationName);
            editIpAddress.setText(locationForEdit.locationIP);
            editLocationDescription.setText(locationForEdit.description);
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

                String name = editName.getText().toString();
                if(TextUtils.isEmpty(name)) {
                    //TREBA DA NE IZLAZI IZ DIALOGA A DA PRIKAZE PORUKU SREDITI ! ! ! !
                    Toast.makeText(getContext(), "Enter location name", Toast.LENGTH_SHORT).show();
                    return;
                }

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
                    locationForEdit.locationName = name;
                    locationForEdit.locationIP = ipAddress;
                    locationForEdit.description = description;
                    viewModel.updateLocation(locationForEdit);
                } else {
                    viewModel.insertLocation(name,ipAddress, description);
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
    public void itemClick(Location location) {

    }

    @Override
    public void removeItem(Location location) {
        viewModel.deleteLocation(location);
    }

    @Override
    public void editItem(Location location) {
        this.locationForEdit = location;
        showAddCategoryDialog(true);
    }

}