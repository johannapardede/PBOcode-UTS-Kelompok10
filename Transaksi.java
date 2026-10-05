package com.klinik;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Transaksi implements Struk {
    private static final int LEBAR = 50;
    private static final double DISKON_HARI = 0.05; //diskon untuk setiap hari senin dan rabu
    private static final double MIN_BELANJA_GIFT = 500000; //belanja lebih dari 500k
    private static final String FILE_STRUK = "struk_transaksi.txt";
    private static final String NL = System.lineSeparator(); 
    private static final String DOKTER = "dr. Dewi Lestari";
    private static final String KODE_KASIR = "[ E ]";
    private static final String COMMENT = "ROSE";

    private String noReceipt;
    private int tanggal;
    private int bulan;
    private int tahun;
    private String hari;
    private Pembeli pembeli;
    private Kasir kasir;
    private String metodeBayar;
    private ArrayList<Produk> daftarBeli;

    // Constructor
    public Transaksi(int tanggal, int bulan, int tahun, String hari, Pembeli pembeli,
                     Kasir kasir, String metodeBayar, ArrayList<Produk> daftarBeli) {
        this.tanggal = tanggal;
        this.bulan = bulan;
        this.tahun = tahun;
        this.hari = hari;
        this.pembeli = pembeli;
        this.kasir = kasir;
        this.metodeBayar = metodeBayar;
        this.daftarBeli = daftarBeli;

        int acak = (int) (Math.random() * 9000) + 1000; //ngebuat angka acak buat no receiptnya
        this.noReceipt = "NSC-" + tahun + dua(bulan) + dua(tanggal) + "-" + acak;
    }

    public void setNoReceipt(String noReceipt) {
        this.noReceipt = noReceipt;
    }
    public String getNoReceipt() {
        return noReceipt;
    }
    public void setTanggal(int tanggal) {
        this.tanggal = tanggal;
    }
    public int getTanggal() {
        return tanggal;
    }

    public void setBulan(int bulan) {
        this.bulan = bulan;
    }
    public int getBulan() {
        return bulan;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }
    public int getTahun() {
        return tahun;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }
    public String getHari() {
        return hari;
    }

    public void setPembeli(Pembeli pembeli) {
        this.pembeli = pembeli;
    }
    public Pembeli getPembeli() {
        return pembeli;
    }

    public void setKasir(Kasir kasir) {
        this.kasir = kasir;
    }
    public Kasir getKasir() {
        return kasir;
    }

    public void setMetodeBayar(String metodeBayar) {
        this.metodeBayar = metodeBayar;
    }

    public String getMetodeBayar() {
        return metodeBayar;
    }

    public ArrayList<Produk> getDaftarBeli() {
        return daftarBeli;
    }

    public String getTanggalLengkap() {
        return dua(tanggal) + "-" + dua(bulan) + "-" + tahun;
    }


    // Diskon 5% setiap hari Senin dan Rabu
    private double persenDiskon() {
        if (hari.equals("senin") || hari.equals("rabu")) {
            return DISKON_HARI;
        }
        return 0;
    }

    public double hitungTotalKotor() { //buat hitung semua sebelum diskon
        double total = 0;
        for (Produk p : daftarBeli) {
            total = total + p.hitungSubtotal();
        }
        return total;
    }

    public double hitungTotalBelanja() { //hitung total semua harga setelah diskon
        double kotor = hitungTotalKotor();
        return kotor - (kotor * persenDiskon());
    }

    public int getTotalHarga() { //ini untuk total harga dalam bntuk bilangan bulat
        return (int) Math.round(hitungTotalBelanja());
    }

    //ini buat bikin titik di angka ribuan kayak 500000 jadi 500.000
    public static String formatAngka(double angka) {
        long bulat = Math.round(angka);
        boolean negatif = bulat < 0; 
        if (negatif) {
            bulat = -bulat;
        }
        String digit = String.valueOf(bulat); //ini baca dari belakang buat ngasih titik
        String hasil = "";
        int hitung = 0;
        for (int i = digit.length() - 1; i >= 0; i--) {
            hasil = digit.charAt(i) + hasil;
            hitung++;
            if (hitung % 3 == 0 && i > 0) {
                hasil = "." + hasil;
            }
        }
        if (negatif) {
            hasil = "-" + hasil;
        }
        return hasil;
    }

    private static String dua(int angka) { //angka yang 1 digit dibikin jadi 2 kaya 2 jadi o2
        if (angka < 10) {
            return "0" + angka;
        }
        return String.valueOf(angka);
    }

    // Mengulang teks sebanyak jumlah kali
    private static String ulang(String teks, int jumlah) {
        String hasil = "";
        for (int i = 0; i < jumlah; i++) {
            hasil = hasil + teks;
        }
        return hasil;
    }

    private static String rataKiri(String teks, int lebar) {
        return teks + ulang(" ", lebar - teks.length());
    }

    private static String rataKanan(String teks, int lebar) {
        return ulang(" ", lebar - teks.length()) + teks;
    }

    private static String tengah(String teks) {
        int spasi = (LEBAR - teks.length()) / 2;
        if (spasi < 0) {
            spasi = 0;
        }
        return ulang(" ", spasi) + teks;
    }

    private static String baris(String kiri, String kanan) {
        return rataKiri(kiri, LEBAR - kanan.length()) + kanan;
    }

    private static String dataBaris(String label, String nilai) {
        return rataKiri(label, 15) + ": " + nilai;
    }

    // Memecah nama produk yang panjang menjadi beberapa baris
    private static ArrayList<String> bungkus(String teks, int lebar) {
        ArrayList<String> hasil = new ArrayList<String>();
        String barisIni = "";
        String[] kata = teks.split(" ");
        for (int i = 0; i < kata.length; i++) {
            if (barisIni.length() > 0 && barisIni.length() + 1 + kata[i].length() > lebar) {
                hasil.add(barisIni);
                barisIni = "";
            }
            if (barisIni.length() > 0) {
                barisIni = barisIni + " ";
            }
            barisIni = barisIni + kata[i];
        }
        if (barisIni.length() > 0) {
            hasil.add(barisIni);
        }
        return hasil;
    }

    public String buatStruk() {
        String garisDouble = ulang("=", LEBAR);
        String garisSingle = ulang("-", LEBAR);

        double totalKotor = hitungTotalKotor();
        double persen = persenDiskon();
        double totalBelanja = hitungTotalBelanja();

        String struk = garisDouble + NL;
        struk += tengah(NAMA_PT) + NL;
        struk += tengah(ALAMAT_1) + NL;
        struk += tengah(ALAMAT_2) + NL;
        struk += tengah(TELP_KLINIK) + NL;
        struk += NL;

        struk += dataBaris("No. Receipt", noReceipt) + NL;
        struk += dataBaris("Tanggal", hari + ", " + getTanggalLengkap()) + NL;
        struk += dataBaris("Customer Code", pembeli.getKodePelanggan()) + NL;
        struk += dataBaris("Customer Name", pembeli.getNamaPelanggan()) + NL;
        struk += dataBaris("Doctor", DOKTER) + NL;
        struk += dataBaris("Cashier", kasir.getNamaKasir() + " " + KODE_KASIR) + NL;
        struk += dataBaris("PA", "") + NL;
        struk += dataBaris("Comment", COMMENT) + NL;
        struk += garisDouble + NL;

        int nomor = 1;
        for (Produk p : daftarBeli) {
            double subtotal = p.hitungSubtotal();

            ArrayList<String> namaBaris = bungkus(p.getNamaProduk().toUpperCase(), 40);
            for (int i = 0; i < namaBaris.size(); i++) {
                String awalan = "    ";
                if (i == 0) {
                    awalan = rataKanan(String.valueOf(nomor), 2) + ". ";
                }
                struk += awalan + namaBaris.get(i) + NL;
            }
            struk += "    " + p.getJenis() + NL;
            struk += baris("    " + p.getQty() + ".00 Pcs", formatAngka(subtotal) + "*") + NL;

            if (persen > 0) {
                String label = "    Disc " + Math.round(persen * 100) + "%";
                struk += baris(label, "-" + formatAngka(subtotal * persen)) + NL;
            }
            nomor++;
        }

        if (totalKotor >= MIN_BELANJA_GIFT) {
            struk += rataKanan(String.valueOf(nomor), 2) + ". GIFT POUCH NATASHA" + NL;
            struk += "    FREE GIFT MIN BELANJA 500K" + NL;
            struk += baris("    1.00 Pcs", "0*") + NL;
            struk += baris("    Disc 100%", "-0") + NL;
        }

        struk += garisSingle + NL;
        struk += baris("TOTAL BAYAR", "Rp " + formatAngka(totalBelanja)) + NL;
        struk += baris("Payment Method", metodeBayar) + NL;
        struk += garisSingle + NL;

        struk += tengah(FOOTER_1) + NL;
        struk += tengah(FOOTER_2) + NL;
        struk += tengah(FOOTER_3) + NL;
        struk += NL;
        struk += rataKanan(getTanggalLengkap(), LEBAR) + NL;

        return struk;
    }

    @Override
    public void cetakBarisStruk() {
        String isiStruk = buatStruk();
        System.out.println(isiStruk);
        simpanKeFile(isiStruk);
    }

    // nyimpan struk ke file 
    private void simpanKeFile(String isiStruk) {
        try {
            FileWriter fw = new FileWriter(FILE_STRUK, true);
            fw.write(isiStruk);
            fw.write(NL);
            fw.write(ulang("=", LEBAR));
            fw.write(NL);
            fw.write(NL);
            fw.close();
            System.out.println("Struk berhasil disimpan ke " + FILE_STRUK);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan struk ke file.");
            e.printStackTrace();
        }
    }
}