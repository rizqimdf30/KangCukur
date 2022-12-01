package com.example.kangcukur.adapter;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kangcukur.R;
import com.example.kangcukur.model.Pegawai;

import java.io.IOException;
import java.util.List;

public class pegawaiadapter extends RecyclerView.Adapter<pegawaiadapter.MyViewHolder>{
    private Context context;
    private List<Pegawai> list;
    private Dialog dialog;


    public interface Dialog{
        void onCLick(int pos);
    }

    public void setDialog(Dialog dialog){
        this.dialog = dialog;
    }

    public pegawaiadapter(Context context, List<Pegawai> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pegawai,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nama.setText(list.get(position).getNama());
        holder.umur.setText(list.get(position).getUmur());
        holder.pengalaman.setText(list.get(position).getPengalaman());
        holder.nohp.setText(String.valueOf(list.get(position).getNohp()));
        Glide.with(context).load(list.get(position).getProfil()).into(holder.profil);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nama, umur, pengalaman, nohp;
        ImageView profil;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.namaKangcukur);
            umur = itemView.findViewById(R.id.umurKangcukur);
            pengalaman = itemView.findViewById(R.id.pengalamanKangcukur);
            profil = itemView.findViewById(R.id.foto_kangcukur);
            nohp = itemView.findViewById(R.id.handphoneKangcukur);


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
