package com.klinik;

public class Kasir {

    // Atribut
    private String namaKasir;
    private String noTelepon;

    // Constructor
    public Kasir(String namaKasir, String noTelepon) {
        this.namaKasir = namaKasir;
        this.noTelepon = noTelepon;
    }

    // Setter dan getter
    public void setNamaKasir(String namaKasir) {
        this.namaKasir = namaKasir;
    }

    public String getNamaKasir() {
        return namaKasir;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getNoTelepon() {
        return noTelepon;
    }
}