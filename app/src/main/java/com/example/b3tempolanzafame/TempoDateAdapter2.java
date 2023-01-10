package com.example.b3tempoLanzafame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b3tempoLanzafame.databinding.TempoDateItem2Binding;

import java.util.ArrayList;
import java.util.List;

public class TempoDateAdapter2 extends RecyclerView.Adapter<TempoDateAdapter2.TempoDateViewHolder2> {

    private List<TempoDate> tempoDates = new ArrayList<>();
    private Context context;

    public TempoDateAdapter2( List<TempoDate> tempoDates, Context context) {
        this.tempoDates = tempoDates;
        this.context = context;
    }

    @NonNull
    @Override
    public TempoDateViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tempo_date_item2, parent, false);
        TempoDateItem2Binding binding = TempoDateItem2Binding.bind(view);
        return new TempoDateViewHolder2(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TempoDateViewHolder2 holder, int position) {
        holder.binding.dateTv.setText(tempoDates.get(position).getDate());
        holder.binding.colorFl.setBackgroundColor(ContextCompat.getColor(context, tempoDates.get(position).getCouleur().getColorResId()));
        holder.binding.colorText.setText(context.getString(tempoDates.get(position).getCouleur().getStringResId()));
    }

    @Override
    public int getItemCount() {
        return tempoDates.size();
    }

    public class TempoDateViewHolder2 extends RecyclerView.ViewHolder {
        TempoDateItem2Binding binding;

        public TempoDateViewHolder2(@NonNull TempoDateItem2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}