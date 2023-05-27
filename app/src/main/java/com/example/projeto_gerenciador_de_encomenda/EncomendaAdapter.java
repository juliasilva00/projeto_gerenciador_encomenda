package com.example.projeto_gerenciador_de_encomenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto_gerenciador_de_encomenda.Model.Encomenda;
import com.example.projeto_gerenciador_de_encomenda.R;

import java.util.ArrayList;

public class EncomendaAdapter extends RecyclerView.Adapter<EncomendaAdapter.ViewHolder> {
    Context context;
    ArrayList<Encomenda> arrayList;
    OnItemClickListener onItemClickListener;

    public EncomendaAdapter(Context context, ArrayList<Encomenda> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.encomenda_lista_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nomeMorador.setText(arrayList.get(position).getNomeMorador());
        holder.statusEncomenda.setText(arrayList.get(position).getStatus());
        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(arrayList.get(position)));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomeMorador, statusEncomenda;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeMorador = itemView.findViewById(R.id.list_item_title);
            statusEncomenda = itemView.findViewById(R.id.status);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(Encomenda encomenda);
    }
}