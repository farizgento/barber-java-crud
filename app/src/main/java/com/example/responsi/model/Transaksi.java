package com.example.responsi.model;

public class Transaksi {
    private String id, jenis, harga, durasi, kategori, nama;

    public Transaksi(){

    }

    public Transaksi(String jenis, String harga, String durasi, String kategori, String nama){
        this.jenis = jenis;
        this.harga = harga;
        this.durasi = durasi;
        this.kategori = kategori;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

}
