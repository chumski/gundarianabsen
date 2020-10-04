package com.example.login.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.login.data.model.Absensi;
import com.example.login.data.model.Mahasiswa;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    // static variable
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "Absensi";

    // table name
    private static final String TABLE_MAHASISWA = "Mahasiswa";
    private static final String TABLE_ABSEN = "Absen";

    // column tables
    private static final String KEY_ID_MAHASISWA = "npm";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public DatabaseHandler(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MAHASISWA = "CREATE TABLE IF NOT EXISTS " + TABLE_MAHASISWA + "("
                + "npm INTEGER PRIMARY KEY,"
                + "name TEXT,"
                + "faculty TEXT,"
                + "class_year TEXT)";
        db.execSQL(CREATE_MAHASISWA);

        String CREATE_ABSEN = "CREATE TABLE IF NOT EXISTS " + TABLE_ABSEN + "("
                + "npm INTEGER PRIMARY KEY,"
                + "name TEXT,"
                + "class_year TEXT,"
                + "create_date TEXT)";
        db.execSQL(CREATE_ABSEN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAHASISWA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAHASISWA);
        onCreate(db);
    }

    public void addAbsen(Absensi absensi){
        SQLiteDatabase db  = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("npm", absensi.getNpm());
        values.put("name", absensi.getName());
        values.put("class_year", absensi.getClass_year());
        values.put("create_date", absensi.getCreate_date());

        db.insert(TABLE_ABSEN, null, values);
        db.close();
    }

    public void addMahasiswa(Mahasiswa mahasiswa){
        SQLiteDatabase db  = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("npm", mahasiswa.getNpm());
        values.put("name", mahasiswa.getName());
        values.put("faculty", mahasiswa.getFaculty());
        values.put("class_yeaer", mahasiswa.getClassYear());

        db.insert(TABLE_ABSEN, null, values);
        db.close();
    }

    public Mahasiswa getMahasiswa(int npm){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MAHASISWA, new String[] { KEY_ID_MAHASISWA,
                        "name", "faculty", "class_year" }, KEY_ID_MAHASISWA + "=?",
                new String[] { String.valueOf(npm) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Mahasiswa mahasiswa = new Mahasiswa(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3));
        // return contact
        return mahasiswa;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<HashMap<String, String>> getAbsensi() {
        ArrayList<HashMap<String,String>> listAbsen = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ABSEN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String,String> absensi = new HashMap<>();
                absensi.put("npm",cursor.getString(0));
                absensi.put("name",cursor.getString(1));
                absensi.put("class_year",cursor.getString(2));
                absensi.put("create_date", cursor.getString(3).replace("T"," "));

                listAbsen.add(absensi);
            } while (cursor.moveToNext());
        }

        // return list
        return listAbsen;
    }
}
