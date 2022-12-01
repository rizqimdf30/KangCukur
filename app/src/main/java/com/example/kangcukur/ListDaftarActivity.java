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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kangcukur.adapter.paketadapter;
import com.example.kangcukur.adapter.pegawaiadapter;
import com.example.kangcukur.model.Paket;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListDaftarActivity extends AppCompatActivity {
    private ImageView back;
    private MaterialButton addPaket;
    RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Paket> list=new ArrayList<>();
    private com.example.kangcukur.adapter.paketadapter paketadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_daftar);
        back = findViewById(R.id.kembali);
        addPaket = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recylerview_paket);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kembali = new Intent(ListDaftarActivity.this, MainAdminActivity.class);
                startActivity(kembali);
            }
        });

        paketadapter = new paketadapter(getApplicationContext(), list);
        paketadapter.setDialog(new paketadapter.Dialog() {
            @Override
            public void onCLick(int pos) {
                final CharSequence[] dialogitem = {"Edit","Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(ListDaftarActivity.this);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), EditPaketActivity.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("paket", list.get(pos).getPaket());
                                intent.putExtra("lay1", list.get(pos).getLay1());
                                intent.putExtra("lay2", list.get(pos).getLay2());
                                intent.putExtra("lay3", list.get(pos).getLay3());
                                intent.putExtra("lay4",list.get(pos).getLay4());
                                intent.putExtra("harga",list.get(pos).getHarga());
                                startActivity(intent);
                                break;
                            case 1:
                                deleteData(list.get(pos).getId());
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(paketadapter);

        addPaket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tambah = new Intent(ListDaftarActivity.this, AddPaketActivity.class);
                startActivity(tambah);
            }
        });
        getData();
    }

    private void getData() {
        db.collection("Paket")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Paket paket = new Paket(document.getString("paket"), document.getString("lay1"), document.getString("lay2"), document.getString("lay3"), document.getString("lay4"), document.getString("harga"));
                                paket.setId(document.getId());
                                list.add(paket);
                            }
                            paketadapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), "Data gagal dimuat!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void deleteData(String id){
        db.collection("Paket").document(id)
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