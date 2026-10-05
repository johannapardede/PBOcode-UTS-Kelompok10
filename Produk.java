package com.klinik;

public abstract class Produk {
    private String namaProduk;
    private double hargaProduk;
    private String kategori;
    private int qty;

    //ini constructornya
    public Produk(String namaProduk, double hargaProduk, String kategori, int qty) {
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.kategori = kategori;
        this.qty = qty;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setHargaProduk(double hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public double getHargaProduk() {
        return hargaProduk;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKategori() {
        return kategori;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getQty() {
        return qty;
    }

    public double hitungSubtotal() {
        return hargaProduk * qty;
    }

    //abstract method yang diimplementasikan di class turunan
    public abstract String getJenis();

    public abstract Produk salin(int qtyBaru);
}