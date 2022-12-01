package com.example.kangcukur.model;

import android.widget.ImageView;

public class Pegawai {
    private String id, nama, umur, pengalaman, nohp, profil;

    public Pegawai(String nama, String umur, String pengalaman, String nohp, String profil){
        this.nama = nama;
        this.umur = umur;
        this.pengalaman = pengalaman;
        this.nohp = nohp;
        this.profil = profil;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getPengalaman() {
        return pengalaman;
    }

    public void setPengalaman(String pengalaman) {
        this.pengalaman = pengalaman;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }
}
