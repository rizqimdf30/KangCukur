package com.example.kangcukur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HasilGantiPassActivity extends AppCompatActivity {
    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_ganti_pass);
        back = findViewById(R.id.text_kembali);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kembali = new Intent(HasilGantiPassActivity.this, LoginActivity.class);
                startActivity(kembali);
            }
        });
    }
}