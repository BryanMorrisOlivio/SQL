package com.mariakurniatimandayu.sqllitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
    public static final String DB_NAME="project2.db";
    private static final int DB_version= 1;

    public DBHelper(Context context) {super(context, DB_NAME, null, DB_version); }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(username TEXT primary key, password TEXT)");
        sqLiteDatabase.execSQL("create table buku(kodebuku TEXT primary key, judulbuku TEXT, pengarang TEXT, penerbit TEXT, nomorisbn TEXT)");
        sqLiteDatabase.execSQL("create table biodata(nim TEXT primary key, nama TEXT, jk TEXT, alamat TEXT, email TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
        sqLiteDatabase.execSQL("drop table if exists buku");
        sqLiteDatabase.execSQL("drop table if exists biodata");
        onCreate(sqLiteDatabase);
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        long result = db.insert("users", null, values);
        if (result ==1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=?", new String[] {username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checusernamepassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", new String[] {username, password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean insertDatamhs (String nim,String nama, String jk, String alamat, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nim",nim);
        values.put("nama",nama);
        values.put("jk", jk);
        values.put("alamat", alamat);
        values.put("email", email);

        long result = db.insert("biodata", null,values);
        if (result==1) return false;
        else
            return true;
    }

    public Boolean checkkode(String kode){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from buku where kode=?", new String[] {kode});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Cursor tampilDatamhs (){
        SQLiteDatabase db= this.getWritableDatabase();
        return db.rawQuery("select * from biodata", null);
    }

    public Boolean checknim (String nim){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from biodata where nim=?", new String[] {nim});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean hapusDatamhs (String nim){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from biodata where nim=?", new String[]{nim});
        if (cursor.getCount()>0) {
            long result = db.delete("biodata", "nim=?", new String[]{nim});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }   else {
            return false;
        }
    }

    public Boolean editDatamhs (String nim,String nama, String jeniskelamin, String alamat, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama", nama);
        values.put("jeniskelamin", jeniskelamin);
        values.put("alamat", alamat);
        values.put("email", email);
        Cursor cursor = db.rawQuery("Select * from biodata where nim=?", new String[]{nim});
        if (cursor.getCount() > 0) {
            long result = db.update("biodata", values, "nim=?", new String[]{nim});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    public Boolean checkkodebuku (String kodebuku){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from buku where kodebuku=?", new String[] {kodebuku});
        if (cursor.getCount()>0)
            return true;
        else
            return false;


    }

    public Boolean editDatabuku (String kode,String judul, String pengarang, String penerbit, String nomorisbn){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("kodebuku",kode);
        values.put("judulbuku", judul);
        values.put("pengarang", pengarang);
        values.put("penerbit", penerbit);
        values.put("nomorisbn", nomorisbn);
        Cursor cursor = db.rawQuery("Select * from buku where kodebuku=?", new String[]{kode});
        if (cursor.getCount()>0){
            long result = db.update("buku",values, "kodebuku=?",new String[]{kode});
            if (result == -1){
                return false;
            }else {
                return true;
            }

        }else {
            return  false;
        }
    }

    public Boolean insertDatabuku (String kodebuku,String judulbuku, String pengarang, String penerbit, String nomorisbn){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("kodebuku",kodebuku);
        values.put("judulbuku",judulbuku);
        values.put("pengarang", pengarang);
        values.put("penerbit", penerbit);
        values.put("nomorisbn", nomorisbn);
        long result = db.insert("buku", null,values);
        if (result==1) return false;
        else
            return true;
    }

    public Cursor tampildatabuku(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from buku", null);
        return cursor;
    }

    public boolean hapusDatabuku (String nim){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from buku where kodebuku=?", new String[]{nim});
        if (cursor.getCount()>0){
            long result = db.delete("buku", "kodebuku=?", new String[]{nim});
            if (result == -1){
                return false;
            }else {
                return true;
            }
        }else {
            return false;
        }
    }
}
