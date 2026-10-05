package com.klinik;

public class Skincare extends Produk {
    private String jenisSkincare;

    public Skincare(String namaProduk, double hargaProduk, String jenisSkincare, int qty) {
        super(namaProduk, hargaProduk, "Skincare", qty);
        this.jenisSkincare = jenisSkincare;
    }

    public void setJenisSkincare(String jenisSkincare) {
        this.jenisSkincare = jenisSkincare;
    }

    public String getJenisSkincare() {
        return jenisSkincare;
    }

    @Override
    public String getJenis() {
        return jenisSkincare;
    }

    @Override
    public Produk salin(int qtyBaru) {
        return new Skincare(getNamaProduk(), getHargaProduk(), jenisSkincare, qtyBaru);
    }
}