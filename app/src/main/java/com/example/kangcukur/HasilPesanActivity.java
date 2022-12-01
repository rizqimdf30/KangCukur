package com.example.kangcukur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HasilPesanActivity extends AppCompatActivity {
    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_pesan);
        back = findViewById(R.id.text_kembali);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kembali = new Intent(HasilPesanActivity.this, MainAdminActivity.class);
                startActivity(kembali);
            }
        });
    }
}