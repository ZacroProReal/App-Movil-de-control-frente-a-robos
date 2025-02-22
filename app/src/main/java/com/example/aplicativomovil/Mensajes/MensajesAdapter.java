package com.example.aplicativomovil.Mensajes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicativomovil.R;

import java.util.List;

public class MensajesAdapter extends RecyclerView.Adapter<MensajesAdapter.ViewHolder> {
    private List<Mensaje> mensajesList;

    public MensajesAdapter(List<Mensaje> mensajesList) {
        this.mensajesList = mensajesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensaje, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Mensaje mensaje = mensajesList.get(position);
        holder.textoMensaje.setText(mensaje.getMensaje());
        holder.textoRemitente.setText(mensaje.getDe());
        holder.textoTimestamp.setText(String.valueOf(mensaje.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return mensajesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textoMensaje, textoRemitente, textoTimestamp;

        public ViewHolder(View itemView) {
            super(itemView);
            textoMensaje = itemView.findViewById(R.id.textoMensaje);
            textoRemitente = itemView.findViewById(R.id.textoRemitente);
            textoTimestamp = itemView.findViewById(R.id.textoTimestamp);
        }
    }
}
