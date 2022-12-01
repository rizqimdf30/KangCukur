package com.example.kangcukur.model;

public class Pesanan {
    private String namacust,tanggal,namakc,paket,id;

    public Pesanan(){

    }
    public Pesanan(String namacust, String tanggal,String namakc,String paket){
        this.paket = paket;
        this.namacust = namacust;
        this.tanggal= tanggal;
        this.namakc = namakc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamacust() {
        return namacust;
    }

    public void setNamacust(String namacust) {
        this.namacust = namacust;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNamakc() {
        return namakc;
    }

    public void setNamakc(String namakc) {
        this.namakc = namakc;
    }

    public String getPaket() {
        return paket;
    }

    public void setPaket(String paket) {
        this.paket = paket;
    }
}

