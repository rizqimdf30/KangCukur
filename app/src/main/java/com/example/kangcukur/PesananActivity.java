package com.example.kangcukur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kangcukur.adapter.paketadapter;
import com.example.kangcukur.adapter.pegawaiadapter;
import com.example.kangcukur.adapter.pesananadapter;
import com.example.kangcukur.model.Paket;
import com.example.kangcukur.model.Pegawai;
import com.example.kangcukur.model.Pesanan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PesananActivity extends AppCompatActivity {
    private ImageView home;
    RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Pesanan> list = new ArrayList<>();
    private pesananadapter pesananadapter;
    private static final String TAG="MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);
        home = findViewById(R.id.btn_home);
        recyclerView = findViewById(R.id.recylerview_pesanan);

        pesananadapter = new pesananadapter(getApplicationContext(),list);
        pesananadapter.setDialog(new pesananadapter.Dialog() {
            @Override
            public void onCLick(int pos) {
                final CharSequence[] dialogitem = {"Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(PesananActivity.this);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                deleteData(list.get(pos).getId());
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(pesananadapter);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(PesananActivity.this, MainAdminActivity.class);
                startActivity(home);
            }
        });

        getData();
    }
    private void getData(){
        db.collection("Pesanan")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Pesanan pesanan = new Pesanan(document.getString("namacust"),document.getString("tanggal"),document.getString("namakc"),document.getString("paket"));
                                pesanan.setId(document.getId());
                                list.add(pesanan);
                            }
                            pesananadapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(),"Data gagal dimuat!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void deleteData(String id){
        db.collection("Pesanan").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Data gagal dihapus!", Toast.LENGTH_SHORT).show();
                        }
                        getData();
                    }
                });
    }
}