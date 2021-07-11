package com.example.thedrugs;

import java.util.Date;

public class Obat {
    //Deklarasi variabel int, string, dan date
    private int idObat;
    private String namaObat;
    private Date tglKadaluarsa;
    private String gambar;
    private String efekSamping;
    private String harga;
    private String komposisi;

    //membuat class obat
    public Obat(int idObat, String namaObat, Date tglKadaluarsa, String gambar, String efekSamping, String harga, String komposisi) {
        this.idObat = idObat;
        this.namaObat = namaObat;
        this.tglKadaluarsa = tglKadaluarsa;
        this.gambar = gambar;
        this.efekSamping = efekSamping;
        this.harga = harga;
        this.komposisi = komposisi;
    }

    //method untuk set komposisi IdObat
    public void setIdObat(int idObat) {
        this.idObat = idObat;
    }
    //method untuk set komposisi NamaObat
    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }
    //method untuk set komposisi TgkKadaluarsa
    public void setTglKadaluarsa(Date tglKadaluarsa) {
        this.tglKadaluarsa = tglKadaluarsa;
    }
    //method untuk set komposisi Gambar
    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
    //method untuk set komposisi EfekSamping
    public void setEfekSamping(String efekSamping) {
        this.efekSamping = efekSamping;
    }
    //method untuk set komposisi Harga
    public void setHarga(String harga) {
        this.harga = harga;
    }
    //method untuk set komposisi Komposisi
    public void setKomposisi(String komposisi) {
        this.komposisi = komposisi;
    }

    //untuk mengambil dari database
    public int getIdObat() {
        return idObat;
    }

    public String getNamaObat() {
        return namaObat;
    }

    public Date getTglKadaluarsa() {
        return tglKadaluarsa;
    }

    public String getGambar() {
        return gambar;
    }

    public String getEfekSamping() {
        return efekSamping;
    }

    public String getHarga() {
        return harga;
    }

    public String getKomposisi() {
        return komposisi;
    }
}