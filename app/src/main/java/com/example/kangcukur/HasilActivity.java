package com.example.kangcukur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class HasilActivity extends AppCompatActivity {
    private TextView pemesan, tglpesan, namapesan,paketpesan;
    private MaterialButton btnKonfir;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);
        pemesan = findViewById(R.id.tv_konfirnamacust);
        tglpesan = findViewById(R.id.tv_konfirtanggal);
        namapesan = findViewById(R.id.tv_konfirnama);
        paketpesan = findViewById(R.id.tv_konfirpaket);
        btnKonfir = findViewById(R.id.btnKonfirpesan);
        back = findViewById(R.id.kembali);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(HasilActivity.this, PesanActivity.class);
                startActivity(back);
            }
        });

        String s_pemesan = getIntent().getExtras().getString("namacust");
        String s_tglpesan = getIntent().getExtras().getString("tanggal");
        String s_namakc = getIntent().getExtras().getString("namakc");
        String s_paket = getIntent().getExtras().getString("paket");

        pemesan.setText(s_pemesan);
        tglpesan.setText(s_tglpesan);
        namapesan.setText(s_namakc);
        paketpesan.setText(s_paket);


        btnKonfir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent konfirmasi = new Intent(HasilActivity.this, HasilPesanActivity.class);
                startActivity(konfirmasi);
            }
        });
    }
}