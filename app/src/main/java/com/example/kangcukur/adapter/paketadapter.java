package com.example.kangcukur.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kangcukur.R;
import com.example.kangcukur.model.Paket;

import java.util.List;

public class paketadapter extends RecyclerView.Adapter<paketadapter.MyViewHolder>{
    private Context context;
    private List<Paket> list;
    private Dialog dialog;

    public interface Dialog{
        void onCLick(int pos);
    }

    public void setDialog(paketadapter.Dialog dialog){
        this.dialog = dialog;
    }

    public paketadapter(Context context, List<Paket> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.paket,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.paket.setText(list.get(position).getPaket());
        holder.lay1.setText(list.get(position).getLay1());
        holder.lay2.setText(list.get(position).getLay2());
        holder.lay3.setText(list.get(position).getLay3());
        holder.lay4.setText(list.get(position).getLay4());
        holder.harga.setText(String.valueOf(list.get(position).getHarga()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView paket, lay1, lay2, lay3, lay4, harga;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            paket = itemView.findViewById(R.id.namapaket);
            lay1 = itemView.findViewById(R.id.laypaket1);
            lay2 = itemView.findViewById(R.id.laypaket2);
            lay3 = itemView.findViewById(R.id.laypaket3);
            lay4 = itemView.findViewById(R.id.laypaket4);
            harga = itemView.findViewById(R.id.hargapaket);

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
