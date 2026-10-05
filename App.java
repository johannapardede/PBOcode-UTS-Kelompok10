import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.klinik.Kasir;
import com.klinik.Makeup;
import com.klinik.Pembeli;
import com.klinik.Produk;
import com.klinik.Skincare;
import com.klinik.Transaksi;

public class App {

    private static final String FILE_KATALOG = "katalog_produk.txt";
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws Exception {


        ArrayList<Produk> katalog = bacaKatalog();
        if (katalog.isEmpty()) {
            System.out.println("Katalog kosong, program dihentikan.");
            input.close();
            return;
        }

        System.out.println("Data katalog berhasil dibaca (" + katalog.size() + " produk).");

        System.out.println("\n=== DATA TRANSAKSI ===");
        String namaKasir = bacaTeks("Nama Kasir     : ");
        Kasir kasir = new Kasir(namaKasir, "");

        String namaPelanggan = bacaTeks("Nama Pelanggan : ");
        String kodePelanggan = bacaTeks("Kode Pelanggan : ");
        Pembeli pembeli = new Pembeli(namaPelanggan, "", kodePelanggan);

        System.out.println("\n=== TANGGAL TRANSAKSI ===");

        int tanggal;
        int bulan;
        int tahun;
        boolean valid;

        do {
            tanggal = bacaInt("Tanggal : ", 1, 31);
            bulan = bacaInt("Bulan : ", 1, 12);
            tahun = bacaInt("Tahun : ", 2000, 2100);

            valid = tanggal <= hariDalamBulan(bulan, tahun);

            if (!valid) {
                System.out.println("Tanggal tidak valid, ulangi lagi!");
            }

        } while (!valid);

        String hari = bacaHari();

        System.out.println("\n======================================");
        System.out.println("           KATALOG PRODUK");
        System.out.println("======================================");

        for (int i = 0; i < katalog.size(); i++) {
            Produk p = katalog.get(i);

            System.out.println((i + 1) + ". [" + p.getKategori() + " - "
                + p.getJenis() + "] "
                + p.getNamaProduk() + " - Rp "
                + Transaksi.formatAngka(p.getHargaProduk()));
        }

        System.out.println("======================================");

        ArrayList<Produk> keranjang = new ArrayList<Produk>();

        while (true) {

            int pilihan = bacaInt(
                "\nPilih nomor produk (0 = selesai): ",
                0,
                katalog.size()
            );

            if (pilihan == 0) {

                if (keranjang.isEmpty()) {
                    System.out.println("Keranjang masih kosong.");
                    continue;
                }

                break;
            }

            Produk dipilih = katalog.get(pilihan - 1);

            System.out.println("Produk : " + dipilih.getNamaProduk());

            int qty = bacaInt("Jumlah : ", 1, 999);

            // Polymorphism salin() dijalankan sesuai class asli
            keranjang.add(dipilih.salin(qty));

            System.out.println("Produk berhasil ditambahkan.");
        }

        String metodeBayar = bacaMetodeBayar();

        Transaksi trx = new Transaksi(tanggal, bulan, tahun, hari, pembeli, kasir, metodeBayar,keranjang);

        System.out.println("\n");

        trx.cetakBarisStruk();

        input.close();
    }

    private static ArrayList<Produk> bacaKatalog() {

        ArrayList<Produk> katalog = new ArrayList<Produk>();

        try {

            File file = new File(FILE_KATALOG);
            Scanner pembaca = new Scanner(file);

            while (pembaca.hasNextLine()) {

                String baris = pembaca.nextLine().trim();

                if (baris.isEmpty()) {
                    continue;
                }

                try {

                    String[] data = baris.split(";");

                    String kategori = data[0].trim();
                    String nama = data[1].trim();
                    double harga = Double.parseDouble(data[2].trim());
                    String jenis = data[3].trim();

                    if (kategori.equals("MAKEUP")) {

                        katalog.add(
                            new Makeup(nama, harga, jenis, 1)
                        );

                    } else if (kategori.equals("SKINCARE")) {

                        katalog.add(
                            new Skincare(nama, harga, jenis, 1)
                        );

                    } else {

                        System.out.println(
                            "Kategori tidak dikenal, dilewati: " + baris
                        );
                    }

                } catch (ArrayIndexOutOfBoundsException e) {

                    System.out.println(
                        "Data kurang lengkap, baris dilewati: " + baris
                    );

                } catch (NumberFormatException e) {

                    System.out.println(
                        "Harga bukan angka, baris dilewati: " + baris
                    );
                }
            }

            pembaca.close();

        } catch (FileNotFoundException e) {

            System.out.println(
                "File " + FILE_KATALOG + " tidak ditemukan."
            );

            System.out.println(
                "Letakkan file di folder project."
            );
        }

        return katalog;
    }

    // Method validasi input
    private static int bacaInt(String pesan, int min, int maks) {

        int angka = 0;
        boolean valid;

        do {

            valid = true;

            System.out.print(pesan);

            String cek = input.nextLine().trim();

            try {

                angka = Integer.parseInt(cek);

                if (angka < min || angka > maks) {

                    System.out.println(
                        "Input harus antara " + min + " dan " + maks
                        + ", ulangi lagi!"
                    );

                    valid = false;
                }

            } catch (NumberFormatException e) {

                System.out.println("Salah input, ulangi lagi!");

                valid = false;
            }

        } while (!valid);

        return angka;
    }

    private static String bacaTeks(String pesan) {

        String teks;

        do {

            System.out.print(pesan);

            teks = input.nextLine().trim();

            if (teks.isEmpty()) {

                System.out.println(
                    "Tidak boleh kosong, ulangi lagi!"
                );
            }

        } while (teks.isEmpty());

        return teks;
    }

    private static String bacaHari() {

        String[] namaHari = {"senin", "selasa", "rabu", "kamis", "jumat", "sabtu", "minggu"};

        String hasil = "";
        boolean valid;

        do {

            valid = false;

            System.out.print("Hari: ");

            String cek = input.nextLine().trim();

            for (int i = 0; i < namaHari.length; i++) {

                if (cek.equals(namaHari[i])) {

                    hasil = namaHari[i];
                    valid = true;
                }
            }

            if (!valid) {

                System.out.println(
                    "Nama hari tidak valid, ulangi lagi!"
                );
            }

        } while (!valid);

        return hasil;
    }

    //ini buat pilihan metode pembayaran
    private static String bacaMetodeBayar() {

        String[] metode = {"cash", "qris", "debit"};

        String hasil = "";
        boolean valid;

        do {

            valid = false;

            System.out.print(
                "Metode Pembayaran (CASH/QRIS/DEBIT): "
            );

            String cek = input.nextLine().trim();

            for (int i = 0; i < metode.length; i++) {

                if (cek.equals(metode[i])) {

                    hasil = metode[i];
                    valid = true;
                }
            }

            if (!valid) {

                System.out.println(
                    "Pilih salah satu: CASH, QRIS, atau DEBIT."
                );
            }

        } while (!valid);

        return hasil;
    }

    private static int hariDalamBulan(int bulan, int tahun) {
        int[] hari = {31, 28, 31, 30, 31, 30,  //ini jumlah hari dri bulan 
                        31, 31, 30, 31, 30, 31};

        boolean kabisat =
            (tahun % 4 == 0 && tahun % 100 != 0)
            || tahun % 400 == 0;

        if (bulan == 2 && kabisat) {
            return 29;
        }

        return hari[bulan - 1];
    }
}