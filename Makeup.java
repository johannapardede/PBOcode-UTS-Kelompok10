package com.klinik;

public class Makeup extends Produk {

    // Atribut
    private String jenisMakeup;

    // Constructor
    public Makeup(String namaProduk, double hargaProduk, String jenisMakeup, int qty) {
        super(namaProduk, hargaProduk, "Makeup", qty);
        this.jenisMakeup = jenisMakeup;
    }

    // Setter dan getter
    public void setJenisMakeup(String jenisMakeup) {
        this.jenisMakeup = jenisMakeup;
    }

    public String getJenisMakeup() {
        return jenisMakeup;
    }

    @Override //ini implementasi abstrack methodnya dari produk
    public String getJenis() {
        return jenisMakeup;
    }

    @Override
    public Produk salin(int qtyBaru) {
        return new Makeup(getNamaProduk(), getHargaProduk(), jenisMakeup, qtyBaru);
    }
}