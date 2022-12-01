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
import android.widget.Toast;

import com.example.kangcukur.adapter.pegawaiadapter;
import com.example.kangcukur.model.Pegawai;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class ListPegawaiActivity extends AppCompatActivity {
    private ImageView back;
    private MaterialButton addPegawai;
    RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Pegawai> list=new ArrayList<>();
    private com.example.kangcukur.adapter.pegawaiadapter pegawaiadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pegawai);
        back = findViewById(R.id.kembali);
        addPegawai = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recylerview_pegawai);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(ListPegawaiActivity.this, MainAdminActivity.class);
                startActivity(back);
            }
        });

        pegawaiadapter = new pegawaiadapter(getApplicationContext(),list);
        pegawaiadapter.setDialog(new pegawaiadapter.Dialog() {
            @Override
            public void onCLick(int pos) {
                final CharSequence[] dialogitem = {"Edit","Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(ListPegawaiActivity.this);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), EditPegawaiActivity.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("nama", list.get(pos).getNama());
                                intent.putExtra("umur", list.get(pos).getUmur());
                                intent.putExtra("pengalaman", list.get(pos).getPengalaman());
                                intent.putExtra("nohp", list.get(pos).getNohp());
                                intent.putExtra("profil",list.get(pos).getProfil());
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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(pegawaiadapter);

        addPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tambah = new Intent(ListPegawaiActivity.this, AddPegawaiActivity.class);
                startActivity(tambah);
            }
        });

        getData();
    }

    private void getData(){
        db.collection("Pegawai")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Pegawai pegawai = new Pegawai(document.getString("nama"),document.getString("umur"),document.getString("pengalaman"),document.getString("nohp"),document.getString("profil"));
                                pegawai.setId(document.getId());
                                list.add(pegawai);
                            }
                            pegawaiadapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(),"Data gagal dimuat!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void deleteData(String id){
        db.collection("Pegawai").document(id)
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