package com.example.kangcukur.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kangcukur.R;
import com.example.kangcukur.model.Pesanan;

import java.util.List;

public class pesananadapter extends RecyclerView.Adapter<pesananadapter.MyViewHolder> {
    private Context context;
    private List<Pesanan> list;
    private pesananadapter.Dialog dialog;

    public interface Dialog{
        void onCLick(int pos);
    }

    public void setDialog(pesananadapter.Dialog dialog){
        this.dialog = dialog;
    }

    public pesananadapter(Context context, List<Pesanan> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public pesananadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pesanan,parent,false);
        return new pesananadapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull pesananadapter.MyViewHolder holder, int position) {
        holder.namacust.setText(list.get(position).getNamacust());
        holder.tanggal.setText(list.get(position).getTanggal());
        holder.namakc.setText(list.get(position).getNamakc());
        holder.paket.setText(list.get(position).getPaket());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView namacust, tanggal, namakc, paket;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            namacust = itemView.findViewById(R.id.custhistory);
            tanggal = itemView.findViewById(R.id.tglhistory);
            namakc = itemView.findViewById(R.id.namahistory);
            paket = itemView.findViewById(R.id.pakethistory);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dialog!=null){
                        dialog.onCLick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
