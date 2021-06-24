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

public class MainBarrierListAdapter extends RecyclerView.Adapter<MainBarrierListAdapter.LocationViewHolder> {
    private List<Location> locationList;
    private Context context;
    private HandleLocationClick clickListener;

    public MainBarrierListAdapter(Context context, HandleLocationClick clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainBarrierListAdapter.LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent,false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) { //postion - je pozicija elementa u listi
        holder.textViewLocationLabel.setText(this.locationList.get(position).locationLabel);
        holder.textViewLocation.setText(this.locationList.get(position).locationName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClick(locationList.get(position));
            }
        });

        holder.button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.editItem(locationList.get(position));
            }
        });

        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.removeItem(locationList.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        if(locationList == null || locationList.size() == 0) {
            return 0;
        }else {
            return locationList.size();
        }
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView textViewLocationLabel;
        TextView textViewLocation;
        Button button_edit;
        Button button_delete;

        public LocationViewHolder(View view) {
            super(view);
            textViewLocationLabel = itemView.findViewById(R.id.textViewLocationLabel);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            button_edit = itemView.findViewById(R.id.button_edit);
            button_delete = itemView.findViewById(R.id.button_delete);
        }
    }

    public interface HandleLocationClick {
        void itemClick(Location location);
        void removeItem(Location location);
        void editItem(Location location);
    }

}
