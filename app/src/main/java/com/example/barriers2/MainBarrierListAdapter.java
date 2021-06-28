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

public class MainBarrierListAdapter extends RecyclerView.Adapter<MainBarrierListAdapter.BarriersViewHolder> {
    private List<Barriers> barrierList;
    private Context context;
    private HandleBarrierClick clickListener;

    public MainBarrierListAdapter(Context context, HandleBarrierClick clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setbarrierList(List<Barriers> barrierList) {
        this.barrierList = barrierList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainBarrierListAdapter.BarriersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.barrier_item_main, parent,false);
        return new BarriersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarriersViewHolder holder, int position) { //postion - je pozicija elementa u listi
        holder.textViewBarrier.setText(this.barrierList.get(position).barriersName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClick(barrierList.get(position));
            }
        });

        holder.open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                clickListener.editItem(barrierList.get(position));
            }
        });

        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                clickListener.removeItem(barrierList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(barrierList == null || barrierList.size() == 0) {
            return 0;
        }else {
            return barrierList.size();
        }
    }

    public class BarriersViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBarrier;
        Button open;
        Button close;

        public BarriersViewHolder(View view) {
            super(view);
            textViewBarrier = itemView.findViewById(R.id.barrier_name_main);
            open = itemView.findViewById(R.id.open);
            close = itemView.findViewById(R.id.close);
        }
    }

    public interface HandleBarrierClick {
        void itemClick(Barriers barrier);
//        void removeItem(Barriers barrier);
//        void editItem(Barriers barrier);
    }

}
