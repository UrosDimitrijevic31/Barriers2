package com.example.barriers2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barriers2.databinding.FragmentFirstBinding;
import com.example.barriers2.db.Location;
import com.example.barriers2.viewmodel.LocationFragmentViewModel;

import java.util.List;

public class FirstFragment extends Fragment implements MainLocationListAdapter.HandleLocationClick {

    private FragmentFirstBinding binding;
    private LocationFragmentViewModel viewModel;
    private TextView noResultTextView;
    private RecyclerView recyclerView;
    private MainLocationListAdapter mainLocationListAdapter;
    private Location locationForEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

    //  ovo je samo bilo
//        binding = FragmentFirstBinding.inflate(inflater, container, false);
//        return binding.getRoot();

        //inflater - pretvara XML u java object
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        //dohvatimo sve elemente koji su nam potrebni zi layouta
        noResultTextView = view.findViewById(R.id.noResultMain);
        recyclerView = view.findViewById(R.id.recycler_view_manin_location);
//
//        ImageView addNew = view.findViewById(R.id.addNewLocationImageView);
//        addNew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showAddCategoryDialog(false);
//            }
//        });

        initViewModel();
        initRecyclerView();
        viewModel.getAllLocationList();

        return view;

    }
    //pravljenje liste item-a
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainLocationListAdapter = new MainLocationListAdapter(getActivity(), this);
        recyclerView.setAdapter(mainLocationListAdapter);
    }

    private  void initViewModel() {
        viewModel = new ViewModelProvider(this).get(LocationFragmentViewModel.class);
        viewModel.getLocationListObserver().observe(getViewLifecycleOwner(), new Observer<List<Location>>() {

            @Override
            public void onChanged(List<Location> locations) {
                if(locations == null) {
                    System.out.println(locations + "ovo su lokacije");
                    noResultTextView.setVisibility(View.VISIBLE); //ispise poruku da je prazna lista (nema parking lokacija)
                    recyclerView.setVisibility(View.GONE); //da nema liste itema
                } else {
                    //show in recyclerview
                    mainLocationListAdapter.setLocationList(locations);
                    recyclerView.setVisibility(View.VISIBLE); //da se prikaze lista itema
                    noResultTextView.setVisibility(View.GONE);
                }
            }
        });
    }


//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        binding.homeCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void itemClick(Location location) {
//        Intent intent = new Intent(getActivity(), SecondFragment.class);
//        intent.putExtra("location_id", location.uid);
//        intent.putExtra("category_name", location.locationName);
//
//        startActivity(intent);
    }
}