package com.example.barriers2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barriers2.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {
    private TextView noResultTextView;
    private RecyclerView recyclerView;
    private MainBarrierListAdapter mainBarrierListAdapter;

    private FragmentSecondBinding binding;
    private int location_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //ovo je bilo
//        binding = FragmentSecondBinding.inflate(inflater, container, false);
//        return binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_second, container, false);

        //dohvatimo sve elemente koji su nam potrebni zi layouta
        noResultTextView = view.findViewById(R.id.noResultSecondBarrier);
        recyclerView = view.findViewById(R.id.recycler_view_manin_barrier);



        return view;
    }

    private void initRecyclerView() {
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mainBarrierListAdapter = new MainBarrierListAdapter(getActivity(), this);
//        recyclerView.setAdapter(mainBarrierListAdapter);
    }


//
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//
//
//        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(SecondFragment.this)
//                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
//            }
//        });
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}