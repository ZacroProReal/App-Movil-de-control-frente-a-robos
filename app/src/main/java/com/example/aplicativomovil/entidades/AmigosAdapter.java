package com.example.aplicativomovil.entidades;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicativomovil.R;

import java.util.List;

public class AmigosAdapter extends RecyclerView.Adapter<AmigosAdapter.AmigoViewHolder> {

    private List<String> amigosList;

    public AmigosAdapter(List<String> amigosList) {
        this.amigosList = amigosList;
    }

    @Override
    public AmigoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_amigo, parent, false);
        return new AmigoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AmigoViewHolder holder, int position) {
        holder.nombreAmigo.setText(amigosList.get(position));
    }

    @Override
    public int getItemCount() {
        return amigosList.size();
    }

    public class AmigoViewHolder extends RecyclerView.ViewHolder {
        TextView nombreAmigo;

        public AmigoViewHolder(View itemView) {
            super(itemView);
            nombreAmigo = itemView.findViewById(R.id.nombreAmigo);
        }
    }
}
