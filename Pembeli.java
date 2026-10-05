package com.klinik;

public class Pembeli {

    // Atribut
    private String namaPelanggan;
    private String noTelepon; // String agar angka 0 di depan tidak hilang
    private String kodePelanggan;

    // Constructor
    public Pembeli(String namaPelanggan, String noTelepon, String kodePelanggan) {
        this.namaPelanggan = namaPelanggan;
        this.noTelepon = noTelepon;
        this.kodePelanggan = kodePelanggan;
    }

    // Setter dan getter
    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setKodePelanggan(String kodePelanggan) {
        this.kodePelanggan = kodePelanggan;
    }

    public String getKodePelanggan() {
        return kodePelanggan;
    }
}