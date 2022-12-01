package com.example.kangcukur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainAdminActivity extends AppCompatActivity {
    private CardView kangcukur, paket,pesanan;
    private ImageView riwayat_pesanan;
    private Button logout;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth nAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        kangcukur = findViewById(R.id.daftar_kangcukur);
        paket = findViewById(R.id.daftar_paket);
        logout = findViewById(R.id.btn_logout);
        pesanan = findViewById(R.id.pesan_kangcukur);
        riwayat_pesanan = findViewById(R.id.btn_pesanan);

        nAuth = FirebaseAuth.getInstance();

        riwayat_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent riwayat = new Intent(MainAdminActivity.this, PesananActivity.class);
                startActivity(riwayat);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent(MainAdminActivity.this, LoginActivity.class);
                startActivity(logout);
            }
        });
        pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pesanan = new Intent(MainAdminActivity.this, PesanActivity.class);
                startActivity(pesanan);
            }
        });
        kangcukur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kangcukur = new Intent(MainAdminActivity.this, ListPegawaiActivity.class);
                startActivity(kangcukur);
            }
        });
        paket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paket = new Intent(MainAdminActivity.this, ListDaftarActivity.class);
                startActivity(paket);
            }
        });
    }
}