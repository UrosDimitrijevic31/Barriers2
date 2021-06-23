package com.example.barriers2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barriers2.db.Barriers;
import com.example.barriers2.db.Location;

import java.util.List;

public class BarriersListAdapter extends RecyclerView.Adapter<BarriersListAdapter.BarrierViewHolder> {
    private List<Barriers> barriersList;
    final private Context context;
    final private HandleBarriersClick clickListener;

    public BarriersListAdapter(Context context, HandleBarriersClick clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setBarriersList(List<Barriers> barriersList) {
        this.barriersList = barriersList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BarriersListAdapter.BarrierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.barrier_item, parent,false);
        return new BarrierViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarrierViewHolder holder, int position) { //postion - je pozicija elementa u listi
        holder.textViewLocationLabel.setText(this.barriersList.get(position).barrierLabel);
        holder.textViewLocation.setText(this.barriersList.get(position).barriersName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClick(barriersList.get(position));
            }
        });

        holder.button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.editItem(barriersList.get(position));
            }
        });

        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.removeItem(barriersList.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        if(barriersList == null || barriersList.size() == 0) {
            return 0;
        }else {
            return barriersList.size();
        }
    }

    public class BarrierViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBarrierLabel;
        TextView textViewBarrier;
        TextView textViewLocationLabel;
        TextView textViewLocation;
        Button button_edit;
        Button button_delete;

        public BarrierViewHolder(View view) {
            super(view);
            textViewBarrierLabel = itemView.findViewById(R.id.barrierLabel);
            textViewBarrier = itemView.findViewById(R.id.barrierName);
            textViewLocationLabel = itemView.findViewById(R.id.barrierLocationLabel);
            textViewLocation = itemView.findViewById(R.id.barrierLocationName);
            button_edit = itemView.findViewById(R.id.barrier_edit);
            button_delete = itemView.findViewById(R.id.barrier_delete);
        }
    }

    public interface HandleBarriersClick {
        void itemClick(Barriers barrier);
        void removeItem(Barriers barrier);
        void editItem(Barriers barrier);
    }

}
