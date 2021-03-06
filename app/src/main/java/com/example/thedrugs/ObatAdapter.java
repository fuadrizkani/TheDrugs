package com.example.thedrugs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ObatAdapter extends RecyclerView.Adapter<ObatAdapter.ObatViewHolder> {
    //deklarasi Context dan ArrayList<Obat>
    private Context context;
    private ArrayList<Obat> dataObat;
    //deklarasi tanggal
    private SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    ObatAdapter(Context context, ArrayList<Obat> dataObat) {
        this.context = context;
        this.dataObat = dataObat;
    }

    //membuat createViewHolder
    @NonNull
    @Override
    public ObatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_obat, parent, false);
        return new ObatViewHolder (view);
    }

    //menghubungkan data dengan view holder
    @Override
    public void onBindViewHolder(@NonNull ObatViewHolder holder, int position) {
        Obat tempObat = dataObat.get(position);
        holder.idObat = tempObat.getIdObat();
        holder.tvNama.setText(tempObat.getNamaObat());
        holder.tvEfek.setText(tempObat.getEfekSamping());
        holder.tvHarga.setText(tempObat.getHarga());
        holder.tanggal = sdformat.format(tempObat.getTglKadaluarsa());
        holder.gambar = tempObat.getGambar();
        holder.komposisi = tempObat.getKomposisi();

        //mengambil gambar dari media penyimpanan
        try {
            File file = new File(holder.gambar);
            Bitmap bitmap = BitmapFactory.decodeStream((new FileInputStream(file)));
            holder.imgObat.setImageBitmap(bitmap);
            holder.imgObat.setContentDescription(holder.gambar);
        }catch (FileNotFoundException e) {
            e.printStackTrace();

            //menampilkan popup dengan pesan "Gagal mengambil gambar dari media penyimpanan"
            Toast.makeText(context, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }

    }

    //membuat getItemCount
    @Override
    public int getItemCount() {
        return dataObat.size();
    }

    public class ObatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        //Deklarasi variabel imageView,TextView,int dan String
        private ImageView imgObat;
        private TextView tvNama, tvHarga, tvEfek;
        private int idObat;
        private String tanggal, gambar, komposisi;

        ObatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama_obat);
            tvHarga = itemView.findViewById(R.id.tv_harga);
            tvEfek = itemView.findViewById(R.id.tv_efek);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        //menekan tombol untuk melihat data obat
        @Override
        public void onClick(View v) {
            Intent bukaObat = new Intent(context, TampilActivity.class);
            bukaObat.putExtra("ID", idObat);
            bukaObat.putExtra("NAMA",tvNama.getText().toString());
            bukaObat.putExtra("TANGGAL", tanggal);
            bukaObat.putExtra("GAMBAR", gambar);
            bukaObat.putExtra("HARGA", tvHarga.getText().toString());
            bukaObat.putExtra("EFEK", tvEfek.getText().toString());
            bukaObat.putExtra("KOMPOSISI", komposisi);
            context.startActivity(bukaObat);
        }

        //menekan tombol dengan lama untuk mengupdate data obat
        @Override
        public boolean onLongClick(View v) {
            Intent bukaInput = new Intent(context, InputActivity.class);
            bukaInput.putExtra("ID", idObat);
            bukaInput.putExtra("NAMA", tvNama.getText().toString());
            bukaInput.putExtra("TANGGAL", tanggal);
            bukaInput.putExtra("GAMBAR", gambar);
            bukaInput.putExtra("HARGA", tvHarga.getText().toString());
            bukaInput.putExtra("EFEK", tvEfek.getText().toString());
            bukaInput.putExtra("KOMPOSISI", komposisi);
            context.startActivity(bukaInput);
            return true;
        }
    }
}
