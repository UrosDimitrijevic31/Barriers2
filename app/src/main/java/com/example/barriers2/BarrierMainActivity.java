package com.example.barriers2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.barriers2.db.Barriers;
import com.example.barriers2.viewmodel.BarriersFragmentViewModel;

import java.util.List;

public class BarrierMainActivity extends AppCompatActivity implements MainBarrierListAdapter.HandleBarrierClick {
     private int location_id;
    private MainBarrierListAdapter mainBarrierListAdapter;
    private BarriersFragmentViewModel viewModel;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barrier_main);

        location_id = getIntent().getIntExtra("location_id", 0);
        String locationName = getIntent().getStringExtra("location_name");

        getSupportActionBar().setTitle(locationName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initRecyclerView();
        initViewModel();
        viewModel.getAllBarriersList(location_id);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(BarriersFragmentViewModel.class);
        viewModel.getBarriersListObserver().observe(this, new Observer<List<Barriers>>() {
            @Override
            public void onChanged(List<Barriers> barriers) {
                if(barriers == null) {
                    recyclerView.setVisibility(View.GONE);
                    findViewById(R.id.noResultSecondBarrier).setVisibility(View.VISIBLE);
                } else {
                    mainBarrierListAdapter.setbarrierList(barriers);
                    recyclerView.setVisibility(View.VISIBLE);
                    findViewById(R.id.noResultSecondBarrier).setVisibility(View.GONE);
                }
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_manin_barrier);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mainBarrierListAdapter = new MainBarrierListAdapter(this, this);
        recyclerView.setAdapter(mainBarrierListAdapter);
    }

    @Override
    public void itemClick(Barriers barrier) {

    }


}