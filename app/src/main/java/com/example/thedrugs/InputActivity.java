package com.example.thedrugs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class InputActivity extends AppCompatActivity implements View.OnClickListener {
    //Deklarasi variabel untuk EditText
    private EditText editNama, editTanggal, editEfek, editHarga, editKomposisi;

    //Deklarasi variabel untuk ImageView
    private ImageView ivObat;

    //Deklarasi variabel untuk Database
    private Database dbHandler;

    //Deklarasi variabel untuk tanggal
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private boolean updateData = false;
    private int idObat = 0;

    //Deklarasi variabel untuk Menyimpan tanggalBerita
    private String tanggalBerita;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        //Menghubungkan variabel editNama dengan componen edit_Nama pada Layout
        editNama = findViewById(R.id.edit_Nama);

        //Menghubungkan variabel editTanggal dengan componen edit_tanggal pada Layout
        editTanggal = findViewById(R.id.edit_tanggal);

        //Menghubungkan variabel editEfek dengan componen edit_efek_samping pada Layout
        editEfek = findViewById(R.id.edit_efek_samping);

        //Menghubungkan variabel editHarga dengan componen edit_Harga pada Layout
        editHarga = findViewById(R.id.edit_Harga);

        //Menghubungkan variabel editKomposisi dengan componen edit_Komposisi pada Layout
        editKomposisi = findViewById(R.id.edit_komposisi);

        //Menghubungkan variabel ivObat dengan componen iv_obat pada Layout
        ivObat = findViewById(R.id.iv_obat);

        //Menghubungkan variabel btnSimpan dengan componen button pada Layout
        Button btnSimpan = findViewById(R.id.btn_simpan);

        //Menghubungkan variabel btnPilihTanggal dengan componen button pada Layout
        Button btnPilihTanggal = findViewById(R.id.btn_pilih_tanggal);

        dbHandler = new Database(this);

        Intent terimaIntent = getIntent();
        Bundle data = terimaIntent.getExtras();
        assert data != null;
        if (Objects.equals(data.getString("OPERASI"), "insert")) {
            updateData = false;
        } else {

            //MengSet editNama,editTanggal,editEfek,editHarga,editKomposisi sesuai data yang kita inginkan
            updateData = true;
            idObat = data.getInt("ID");
            editNama.setText(data.getString("NAMA"));
            editTanggal.setText(data.getString("TANGGAL"));
            editEfek.setText(data.getString("EFEK"));
            editHarga.setText(data.getString("HARGA"));
            editKomposisi.setText(data.getString("KOMPOSISI"));
            loadImageFromInternalStorage(data.getString("GAMBAR"));
        }
        //mengaktifkan button ivObat, btnSimpan dan btn PilihTanggal
        ivObat.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);
        btnPilihTanggal.setOnClickListener(this);
    }

    //untuk memilih gambar
    private void pickImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(6,6)
                .start(this);
    }

    private void simpanData() {

        //Deklarasi String dan Date
        String nama, gambar, efekSamping, harga, komposisi;
        Date tanggal = new Date();

        //menyimpan input user di edittext editNama kedalam variabel nama
        nama = editNama.getText().toString();

        //menyimpan input user di ContentDescription ivObat kedalam variabel gambar
        gambar = ivObat.getContentDescription().toString();

        //menyimpan input user di edittext editEfek kedalam variabel efekSamping
        efekSamping = editEfek.getText().toString();

        //menyimpan input user di edittext editHarga kedalam variabel harga
        harga = editHarga.getText().toString();

        //menyimpan input user di edittext editKomposisi kedalam variabel komposisi
        komposisi = editKomposisi.getText().toString();

        //memilih tanggal
        try {
            tanggal = sdFormat.parse(editTanggal.getText().toString());
        } catch (ParseException er) {
            er.printStackTrace();
        }

        //
        Obat tempObat = new Obat(
                idObat, nama, tanggal, gambar, efekSamping, harga, komposisi
        );

        if (updateData) {
            dbHandler.editObat(tempObat);

            //membuat variabel toast dan menampilkan pesan "Data obat diperbaharui"
            Toast.makeText(this, "Data obat diperbaharui", Toast.LENGTH_SHORT).show();
        } else {
            dbHandler.tambahObat(tempObat);

            //membuat variabel toast dan menampilkan pesan "Data obat Ditambahkan"
            Toast.makeText(this, "Data obat Ditambahkan", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    //menghapus data
    private void hapusData() {
        dbHandler.hapusObat(idObat);

        //membuat variabel toast dan menampilkan pesan "Data obat dihapus"
        Toast.makeText(this, "Data obat dihapus", Toast.LENGTH_SHORT).show();
    }

    //untuk memilih tanggal
    private void pilihTanggal() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override

            //mengSet hari,bulan dan tahun
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tanggalBerita = dayOfMonth + "/" + month + "/" + year;

                pilihWaktu();
            }
        },
                //untuk memuncul kan kalender
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        pickerDialog.show();
    }

    //untuk memilih waktu
    private void pilihWaktu() {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog pickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override

            //menSet jam dan menit
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tanggalBerita = tanggalBerita + " " + hourOfDay + ":" + minute;
                editTanggal.setText(tanggalBerita);
            }
        },
                //menampilkan tampilan jam
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

        pickerDialog.show();
    }

    //memilih gambar
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
                try {
                    assert result != null;
                    Uri imageUri = result.getUri();
                    InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    String location = saveImageToInternalStorage(selectedImage, getApplicationContext());
                    loadImageFromInternalStorage(location);
                } catch (FileNotFoundException er) {
                    er.printStackTrace();

                    //menampilkan popup dengan pesan "Ada kesalahan dalam pengambilan gambar"
                    Toast.makeText(this, "Ada kesalahan dalam pengambilan gambar", Toast.LENGTH_SHORT).show();
                }
        } else {

            //menampilkan popup dengan pesan "Anda belum memilih gambar"
            Toast.makeText(this, "Anda belum memilih gambar", Toast.LENGTH_SHORT).show();
        }
    }

    //untuk menyimpan gambar yang di input ke database
    public static String saveImageToInternalStorage(Bitmap bitmap, Context ctx ) {
        ContextWrapper ctxWrapper = new ContextWrapper(ctx);
        File file = ctxWrapper.getDir("images", MODE_PRIVATE);
        String uniqueID = UUID.randomUUID().toString();
        file = new File(file, "obat-"+ uniqueID + ".jpg");
        try {
            OutputStream stream;
            stream = new FileOutputStream(file);

            //mengcompress gambar
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            stream.flush();
            stream.close();
        } catch (IOException er) {
            er.printStackTrace();
        }

        Uri savedImage = Uri.parse(file.getAbsolutePath());
        return savedImage.toString();
    }

    //mengambil gambar dari media penyimpanan
    private void loadImageFromInternalStorage(String imageLocation) {
        try {
            File file = new File(imageLocation);
            Bitmap bitmap = BitmapFactory.decodeStream((new FileInputStream(file)));
            ivObat.setImageBitmap(bitmap);
            ivObat.setContentDescription(imageLocation);
        } catch (FileNotFoundException er) {
            er.printStackTrace();

            //menampilkan popup dengan pesan "Gagal mengambil gambar dari media penyimpanan"
            Toast.makeText(this, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }
    }

    //untuk membuat menu hapus jika ada update data
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.item_menu_hapus);

        if (updateData) {
            item.setEnabled(true);
            item.getIcon().setAlpha(255);
        } else {
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    //untuk membuat menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.input_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //untuk menghapus data
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.item_menu_hapus) {
            hapusData();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    //fungsi klik
    @Override
    public void onClick(View v) {
        int idView = v.getId();

        //menekan button simpan maka akan menyimpan data
        if (idView == R.id.btn_simpan) {
            simpanData();
        }
        //menekan button pilih tanggal makan akan muncul kalender
        else if (idView == R.id.btn_pilih_tanggal) {
            pilihTanggal();
        }
    }
}