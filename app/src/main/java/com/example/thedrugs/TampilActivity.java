package com.example.thedrugs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TampilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        //Menghubungkan variabel imgObat dengan componen iv_obat pada Layout
        ImageView imgObat = findViewById(R.id.iv_obat);
        //Menghubungkan variabel tvNama dengan componen tv_nama_obat pada Layout
        TextView tvNama = findViewById(R.id.tv_nama_obat);
        //Menghubungkan variabel tvTanggal dengan componen tv_tanggal_kadaluarsa pada Layout
        TextView tvTanggal = findViewById(R.id.tv_tanggal_kadaluarsa);
        //Menghubungkan variabel tvEfek dengan componen tv_efek pada Layout
        TextView tvEfek = findViewById(R.id.tv_efek);
        //Menghubungkan variabel tvHarga dengan componen tv_harga pada Layout
        TextView tvHarga = findViewById(R.id.tv_harga);
        //Menghubungkan variabel tvKomposisi dengan componen tv_komposisi pada Layout
        TextView tvKomposisi = findViewById(R.id.tv_komposisi);
        Intent terimaData = getIntent();

        //mengSet tvNama,tvTanggal,tvEfek,tvHarga,dan tvKomposisi sesuai data yang kita inginkan
        tvNama.setText(terimaData.getStringExtra("NAMA"));
        tvTanggal.setText(terimaData.getStringExtra("TANGGAL"));
        tvEfek.setText(terimaData.getStringExtra("EFEK"));
        tvHarga.setText(terimaData.getStringExtra("HARGA"));
        tvKomposisi.setText(terimaData.getStringExtra("KOMPOSISI"));
        String imgLocation = terimaData.getStringExtra("GAMBAR");

        //Mengambil gambar
        try {
            assert imgLocation != null;
            File file = new File(imgLocation);
            Bitmap bitmap =  BitmapFactory.decodeStream((new FileInputStream(file)));
            imgObat.setImageBitmap(bitmap);
            imgObat.setContentDescription(imgLocation);
        } catch (FileNotFoundException er) {
            er.printStackTrace();

            //menampilkan popup dengan pesan "Gagal mengambil gambar dari media penyimpanan"
            Toast.makeText(this, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }
    }
}