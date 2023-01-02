package com.example.b3tempoLanzafame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TempoDateAdapter extends RecyclerView.Adapter<TempoDateAdapter.TempoDateViewHolder> {

    private List<TempoDate> tempoDates = new ArrayList<>();
    private Context context;

    public TempoDateAdapter( List<TempoDate> tempoDates, Context context) {
        this.tempoDates = tempoDates;
        this.context = context;
    }

    @NonNull
    @Override
    public TempoDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tempo_date_item, parent, false);

        return new TempoDateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TempoDateViewHolder holder, int position) {
        holder.dateTv.setText(tempoDates.get(position).getDate());
        holder.colorFl.setBackgroundColor(ContextCompat.getColor(context, tempoDates.get(position).getCouleur().getResId()));
    }

    @Override
    public int getItemCount() {
        return tempoDates.size();
    }

    public class TempoDateViewHolder extends RecyclerView.ViewHolder {
        TextView dateTv;
        FrameLayout colorFl;

        public TempoDateViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTv = itemView.findViewById(R.id.date_tv);
            colorFl = itemView.findViewById(R.id.color_fl);
        }
    }
}
