package com.example.kangcukur.model;

public class Paket {
    private String id, paket,lay1,lay2,lay3,lay4,harga;

    public Paket(){

    }

    public Paket(String paket, String lay1,String lay2,String lay3, String lay4, String harga){
        this.paket = paket;
        this.lay1 = lay1;
        this.lay2 = lay2;
        this.lay3 = lay3;
        this.lay4 = lay4;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Paket(String nama, String umur, String pengalaman, String nohp) {
    }

    public String getPaket() {
        return paket;
    }

    public void setPaket(String paket) {
        this.paket = paket;
    }

    public String getLay1() {
        return lay1;
    }

    public void setLay1(String lay1) {
        this.lay1 = lay1;
    }

    public String getLay2() {
        return lay2;
    }

    public void setLay2(String lay2) {
        this.lay2 = lay2;
    }

    public String getLay3() {
        return lay3;
    }

    public void setLay3(String lay3) {
        this.lay3 = lay3;
    }

    public String getLay4() {
        return lay4;
    }

    public void setLay4(String lay4) {
        this.lay4 = lay4;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}

