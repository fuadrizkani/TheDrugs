package com.example.thedrugs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Database extends SQLiteOpenHelper {

    //membuat versi database
    private final static int DATABASE_VERSION = 1;
    //membuat nama database
    private final static String DATABASE_NAME = "db_obatku";
    //membuat tabel obat
    private final static String TABLE_OBAT = "t_obat";
    //membuat key id obat
    private final static String KEY_ID_OBAT = "ID_obat";
    //membuat key nama obat
    private final static String KEY_NAMA_OBAT = "Nama";
    //membuat key tanggal kadaluarsa
    private final static String KEY_TGL_KADALUARSA = "Kadaluarsa";
    //membuat key gambar
    private final static String KEY_GAMBAR = "Gambar";
    //membuat key efek
    private final static String KEY_EFEK = "Efek";
    //membuat key harga
    private final static String KEY_HARGA = "Harga";
    //membuat key komposisi
    private final static String KEY_KOMPOSISI = "Komposisi";
    //menentukan tanggal
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    Database(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //membuat tabel obat
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_BERITA = "CREATE TABLE " + TABLE_OBAT
                + "(" + KEY_ID_OBAT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAMA_OBAT + " TEXT, " + KEY_TGL_KADALUARSA + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_EFEK + " TEXT, "
                + KEY_HARGA + " VARCHAR, " + KEY_KOMPOSISI +" TEXT " + ")";

        db.execSQL(CREATE_TABLE_BERITA);
    }

    //untuk mengupdate tabel obat
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_OBAT;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    //untuk menginput data obat ke setiap key
    void tambahObat(Obat dataObat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NAMA_OBAT, dataObat.getNamaObat());
        cv.put(KEY_TGL_KADALUARSA, sdFormat.format(dataObat.getTglKadaluarsa()));
        cv.put(KEY_GAMBAR, dataObat.getGambar());
        cv.put(KEY_EFEK, dataObat.getEfekSamping());
        cv.put(KEY_HARGA, dataObat.getHarga());
        cv.put(KEY_KOMPOSISI, dataObat.getKomposisi());
        db.insert(TABLE_OBAT, null, cv);
        db.close();
    }

    //untuk mengedit data obat yang tersedia
    void editObat(Obat dataObat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NAMA_OBAT, dataObat.getNamaObat());
        cv.put(KEY_TGL_KADALUARSA, sdFormat.format(dataObat.getTglKadaluarsa()));
        cv.put(KEY_GAMBAR, dataObat.getGambar());
        cv.put(KEY_EFEK, dataObat.getEfekSamping());
        cv.put(KEY_HARGA, dataObat.getHarga());
        cv.put(KEY_KOMPOSISI, dataObat.getKomposisi());
        db.update(TABLE_OBAT, cv,KEY_ID_OBAT + "=?", new String[]{String.valueOf(dataObat.getIdObat())});
        db.close();
    }

    //untuk menghapus data obat yang tersedia
    void hapusObat(int idObat) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_OBAT, KEY_ID_OBAT + "=?", new String[]{String.valueOf(idObat)});
        db.close();
    }

    //menambahkan arraylist data obat
    ArrayList<Obat> getAllBerita() {
        ArrayList<Obat> dataObat = new ArrayList<>();

        //mengambil database dari tabel obat
        String query = "SELECT * FROM " + TABLE_OBAT;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if (csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                } catch (ParseException er){
                    er.printStackTrace();
                }

                Obat tempObat = new Obat(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6)

                );

                dataObat.add(tempObat);
            } while (csr.moveToNext());
        }
        return  dataObat;
    }
}