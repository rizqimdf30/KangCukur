package com.example.kangcukur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

public class PesanActivity extends AppCompatActivity {
    private MaterialButton btnPesan;
    private TextInputEditText namaPemesan,tglPesan, namaPesan, paketPesan;
    private ImageView back;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);
        btnPesan = findViewById(R.id.btnPesan);
        tglPesan = findViewById(R.id.et_tanggalorder);
        namaPemesan = findViewById(R.id.et_namapemesan);
        namaPesan = findViewById(R.id.et_ordernama);
        paketPesan = findViewById(R.id.et_orderpaket);
        back = findViewById(R.id.backimg);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(PesanActivity.this, MainAdminActivity.class);
                startActivity(back);
            }
        });
        btnPesan.setOnClickListener(v-> {
            String namacust = namaPemesan.getText().toString();
            String tanggal = tglPesan.getText().toString();
            String namakc = namaPesan.getText().toString();
            String paket = paketPesan.getText().toString();

            if (namaPemesan.getText().length()>0 && tglPesan.getText().length()>0 && namaPesan.getText().length()>0 && paketPesan.getText().length()>0){
                saveData(namaPemesan.getText().toString(), tglPesan.getText().toString(),namaPesan.getText().toString(), paketPesan.getText().toString());
            }else{
                Toast.makeText(getApplicationContext(), "Silahkan isi data yang diperlukan!", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(PesanActivity.this, HasilActivity.class);
            intent.putExtra("namacust", namacust);
            intent.putExtra("tanggal", tanggal);
            intent.putExtra("namakc", namakc);
            intent.putExtra("paket", paket);
            startActivity(intent);
        });
        Intent intent = getIntent();
        if (intent!=null){
            id = intent.getStringExtra("id");
            namaPemesan.setText(intent.getStringExtra("namacust"));
            tglPesan.setText(intent.getStringExtra("tanggal"));
            namaPesan.setText(intent.getStringExtra("namakc"));
            paketPesan.setText(intent.getStringExtra("paket"));
        }
    }
    private void saveData(String namacust,String tanggal, String namakc,String paket){
        Map<String, Object> Pesanan = new HashMap<>();
        Pesanan.put("namacust", namacust);
        Pesanan.put("tanggal", tanggal);
        Pesanan.put("namakc", namakc);
        Pesanan.put("paket", paket);

        if(id!=null){
            db.collection("Pesanan").document(id)
                    .set(Pesanan)
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
            db.collection("Pesanan")
                    .add(Pesanan)
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