package com.example.kangcukur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddPaketActivity extends AppCompatActivity {
    private TextInputEditText etPaket, etLay1, etLay2, etLay3, etLay4, etharga;
    private MaterialButton addPaket;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_paket);
        etPaket = findViewById(R.id.etNamapaket);
        etLay1 = findViewById(R.id.etLayanan1);
        etLay2 = findViewById(R.id.etLayanan2);
        etLay3 = findViewById(R.id.etLayanan3);
        etLay4 = findViewById(R.id.etLayanan4);
        etharga = findViewById(R.id.etHarga);
        addPaket = findViewById(R.id.btnAdd);
        addPaket.setOnClickListener(v-> {
            if (etPaket.getText().length()>0 && etLay1.getText().length()>0 && etharga.getText().length()>0){
                saveData(etPaket.getText().toString(), etLay1.getText().toString(),etLay2.getText().toString(), etLay3.getText().toString(),etLay4.getText().toString(), etharga.getText().toString());
            }else{
                Toast.makeText(getApplicationContext(), "Silahkan isi data yang diperlukan!", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(AddPaketActivity.this, MainAdminActivity.class);
            startActivity(intent);
        });
        Intent intent = getIntent();
        if (intent!=null){
            id = intent.getStringExtra("id");
            etPaket.setText(intent.getStringExtra("paket"));
            etLay1.setText(intent.getStringExtra("lay1"));
            etLay2.setText(intent.getStringExtra("lay2"));
            etLay3.setText(intent.getStringExtra("lay3"));
            etLay4.setText(intent.getStringExtra("lay4"));
            etharga.setText(intent.getStringExtra("harga"));
        }
    }

    private void saveData(String paket,String lay1, String lay2,String lay3,String lay4,String harga){
        Map<String, Object> Paket = new HashMap<>();
        Paket.put("paket", paket);
        Paket.put("lay1", lay1);
        Paket.put("lay2", lay2);
        Paket.put("lay3", lay3);
        Paket.put("lay4", lay4);
        Paket.put("harga", harga);

        if(id!=null){
            db.collection("Paket").document(id)
                    .set(Paket)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Berhasil!", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Gagal!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            db.collection("Paket")
                    .add(Paket)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Berhasil!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}

