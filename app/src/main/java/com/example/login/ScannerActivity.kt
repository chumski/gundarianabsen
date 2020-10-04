package com.example.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.login.data.model.Absensi
import com.example.login.helper.DatabaseHandler
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class ScannerActivity : AppCompatActivity() {

    var scannedResult: String = ""
    var dbHandler = DatabaseHandler(this)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    var result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
    if(result != null){

        if(result.contents != null){
            scannedResult = result.contents
            barcode_text.text = scannedResult
            val mahasiswa = dbHandler.getMahasiswa(scannedResult.toInt())
            if (mahasiswa == null){
                Toast.makeText(this,"Nomor Mahasiswa tidak valid, data tidak ditemukan!",Toast.LENGTH_LONG)
            }
            else{
                dbHandler.addAbsen(Absensi(mahasiswa.npm,mahasiswa.name,mahasiswa.classYear,LocalDateTime.now().toString() ))
                Toast.makeText(this,"Berhasil mencatat " + mahasiswa.name + " pada daftar kehadiran!",Toast.LENGTH_LONG)
            }
        } else {
            barcode_text.text = ""
        }
    } else {
        super.onActivityResult(requestCode, resultCode, data)
    }
}

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnTriggerScan.setOnClickListener {
            run{
                IntentIntegrator(this).initiateScan()
            }
        }

        btnNPM.setOnClickListener {
            val mahasiswa = dbHandler.getMahasiswa(txtNpm.text.toString().toInt())
            if (mahasiswa == null){
                Toast.makeText(this,"Nomor Mahasiswa tidak valid, data tidak ditemukan!",Toast.LENGTH_LONG)
            }
            else{
                dbHandler.addAbsen(Absensi(mahasiswa.npm,mahasiswa.name,mahasiswa.classYear,LocalDateTime.now().toString() ))
                Toast.makeText(this,"Berhasil mencatat " + mahasiswa.name + " pada daftar kehadiran!",Toast.LENGTH_LONG)
            }
        }


    }
}
