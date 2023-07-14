package com.mariakurniatimandayu.sqllitedb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MahasiswaActivity extends AppCompatActivity {

    EditText nim,nama,jeniskelamin,alamat,email;
    Button simpan,tampil,hapus,edit;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);
        nim = findViewById(R.id.edtnim);
        nama = findViewById(R.id.edtnama);
        jeniskelamin = findViewById(R.id.edtjk);
        alamat = findViewById(R.id.edtalamat);
        email = findViewById(R.id.edtemail);
        simpan = findViewById(R.id.btnsimpan);
        tampil = findViewById(R.id.btntampil);
        hapus = findViewById(R.id.btnhapus);
        edit = findViewById(R.id.btnedit);
        db = new DBHelper(this);


        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isinim = nim.getText().toString();
                String isinama = nama.getText().toString();
                String isijk = jeniskelamin.getText().toString();
                String isialamat = alamat.getText().toString();
                String isiemail = email.getText().toString();

                if (TextUtils.isEmpty(isinim) || TextUtils.isEmpty(isinama) || TextUtils.isEmpty(isijk) || TextUtils.isEmpty(isialamat) || TextUtils.isEmpty(isiemail)) {
                    Toast.makeText(MahasiswaActivity.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                } else {
                    Boolean checkkode = db.checknim(isinim);
                    if (checkkode == false) {
                        Boolean insert = db.insertDatamhs(isinim, isinama, isijk, isialamat, isiemail);
                        if (insert == true) {
                            Toast.makeText(MahasiswaActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                          //  Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            // startActivity(intent);
                        } else {
                            Toast.makeText(MahasiswaActivity.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(MahasiswaActivity.this, "Data Mahasiswa Sudah Ada", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kb = nim.getText().toString();
                Boolean cekHapusData = db.hapusDatamhs(kb);
                if (cekHapusData == true)
                    Toast.makeText(MahasiswaActivity.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MahasiswaActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        });

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.tampilDatamhs();
                if (res.getCount()==0){
                    Toast.makeText(MahasiswaActivity.this, "Tidak ada Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("NIM Mahasiswa : "+res.getString(0)+"\n");
                    buffer.append("Nama Mahasiswa : "+res.getString(1)+"\n");
                    buffer.append("Jenis Kelamin : "+res.getString(2)+"\n");
                    buffer.append("Alamat : "+res.getString(3)+"\n");
                    buffer.append("Email : "+res.getString(4)+"\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MahasiswaActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Biodata Mahasiswa");
                builder.setMessage(buffer.toString());
                builder.show();
            };

        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isikode = nim.getText().toString();
                String isijudul = nama.getText().toString();
                String isipengarang = jeniskelamin.getText().toString();
                String isipenerbit = alamat.getText().toString();
                String isiisbn = email.getText().toString();

                if (TextUtils.isEmpty(isikode) || TextUtils.isEmpty(isijudul) || TextUtils.isEmpty(isipengarang)
                        || TextUtils.isEmpty(isipenerbit) || TextUtils.isEmpty(isiisbn)) {
                    Toast.makeText(MahasiswaActivity.this, "Semua Field Wajib diisi", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean edit = db.editDatamhs(isikode, isijudul, isipengarang, isipenerbit, isiisbn);
                    if (edit == true) {
                        Toast.makeText(MahasiswaActivity.this, "Data Berhasil Di Edit", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MahasiswaActivity.this, "Data Gagal Di Edit", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

}