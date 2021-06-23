package com.example.barriers2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barriers2.db.Location;

import java.util.List;

public class MainLocationListAdapter extends RecyclerView.Adapter<MainLocationListAdapter.MainViewHolder> {
    private List<Location> locationList;
    private Context context;
    private HandleLocationClick clickListener;

    public MainLocationListAdapter(Context context, HandleLocationClick clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainLocationListAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item_main, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.mainLocationCardText.setText(this.locationList.get(position).locationName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClick(locationList.get(position));
            }
        });

//        holder.button_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clickListener.editItem(locationList.get(position));
//            }
//        });

    }

    @Override
    public int getItemCount() {
        if(locationList == null || locationList.size() == 0) {
            return 0;
        }else {
            return locationList.size();
        }
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView mainLocationCardText;

        public MainViewHolder(View view) {
            super(view);
            mainLocationCardText = itemView.findViewById(R.id.mainLocationCardText);
        }
    }

    public interface HandleLocationClick {
        void itemClick(Location location);
    }
}
