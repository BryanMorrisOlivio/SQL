package com.mariakurniatimandayu.sqllitedb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BukuActivity extends AppCompatActivity {

    EditText kode,judul,pengarang,penerbit,isbn;
    Button simpan,tampil,edit,hapus;
    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku);
        kode = findViewById(R.id.kodebuku);
        judul = findViewById(R.id.judulbuku);
        pengarang = findViewById(R.id.pengarang);
        penerbit = findViewById(R.id.penerbit);
        isbn = findViewById(R.id.isbn);
        simpan = findViewById(R.id.btnsimpan);
        tampil = findViewById(R.id.btntampil);
        edit = findViewById(R.id.btnedit);
        hapus = findViewById(R.id.btnhapus);
        db = new DBHelper(this);


        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  isikode = kode.getText().toString();
                String isijudul = judul.getText().toString();
                String isipengarang = pengarang.getText().toString();
                String isipenerbit = penerbit.getText().toString();
                String isiisbn = isbn.getText().toString();

                if (TextUtils.isEmpty(isikode) || TextUtils.isEmpty(isijudul) || TextUtils.isEmpty(isipengarang)
                        || TextUtils.isEmpty(isipenerbit) || TextUtils.isEmpty(isiisbn)){
                    Toast.makeText(BukuActivity.this,"Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                }else {
                    Boolean checkkode = db.checkkodebuku(isikode);
                    if (checkkode == false){
                        Boolean insert = db.insertDatabuku(isikode,isijudul,isipengarang,isipenerbit,isiisbn);
                        if (insert == true){
                            Toast.makeText(BukuActivity.this, "Buku berhasil disimpan", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),BukuActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(BukuActivity.this,"Buku gagal disimpan", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(BukuActivity.this,"Kode Buku Sudah Ada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.tampildatabuku();
                if(res.getCount() == 0){
                    Toast.makeText(BukuActivity.this,"Tidak ada Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Kode Buku : " + res.getString(0) + "\n");
                    buffer.append("Judul Buku : " + res.getString(1) + "\n");
                    buffer.append("Pengarang : " + res.getString(2) + "\n");
                    buffer.append("Penerbit : " + res.getString(3) + "\n");
                    buffer.append("Nomor ISBN : " + res.getString(4) + "\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(BukuActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Data Buku");
                builder.setMessage(buffer.toString());
                builder.show();

            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kb = kode.getText().toString();
                Boolean cekHapusData = db.hapusDatabuku(kb);
                if (cekHapusData == true)
                    Toast.makeText(BukuActivity.this,"Data Terhapus", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(BukuActivity.this,"Data Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isikode = kode.getText().toString();
                String isijudul = judul.getText().toString();
                String isipengarang = pengarang.getText().toString();
                String isipenerbit = penerbit.getText().toString();
                String isiisbn = isbn.getText().toString();

                if (TextUtils.isEmpty(isikode) || TextUtils.isEmpty(isijudul) || TextUtils.isEmpty(isipengarang)
                        || TextUtils.isEmpty(isipenerbit) || TextUtils.isEmpty(isiisbn)){
                    Toast.makeText(BukuActivity.this,"Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                }else {
                    Boolean edit = db.editDatabuku(isikode, isijudul, isipengarang, isipenerbit, isiisbn);
                    if (edit == true) {
                        Toast.makeText(BukuActivity.this, "Data Berhasil di Edit", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BukuActivity.this, "Data Gagal di Edit", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

}