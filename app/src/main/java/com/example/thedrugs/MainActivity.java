package com.example.thedrugs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Deklrasi RecyclerView dan Database secara private
    private RecyclerView rvObat;
    private Database dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Menghubungkan variabel rvObat dengan componen rv_tampil_obat pada Layout
        rvObat = findViewById(R.id.rv_tampil_obat);

        dbHandler = new Database(this);
        tampilDataObat();
    }

    //untuk menampilkan data obat
    private void tampilDataObat() {

        //menambahkan arraylist
        ArrayList<Obat> dataObat = dbHandler.getAllBerita();
        ObatAdapter obatAdapter = new ObatAdapter(this, dataObat);
        RecyclerView.LayoutManager layManager = new LinearLayoutManager(MainActivity.this);
        rvObat.setLayoutManager(layManager);
        rvObat.setAdapter(obatAdapter);
    }

    //membuat menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //membuat menu tambah data
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.item_menu_tambah) {

            //digunakan untuk pindah dari mainactivity ke kelas inputactivity
            Intent bukaInput = new Intent(getApplicationContext(), InputActivity.class);
            bukaInput.putExtra("OPERASI", "insert");

            //Untuk menjalankan activity
            startActivity(bukaInput);
        }
        return super.onOptionsItemSelected(item);
    }

    //menampilkan tampilan data obat
    @Override
    protected void onResume() {
        super.onResume();
        tampilDataObat();
    }
}