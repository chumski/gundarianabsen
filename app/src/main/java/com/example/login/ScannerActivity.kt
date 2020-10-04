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
import com.example.login.data.model.Mahasiswa
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
    var dbHandler = DatabaseHandler(this)
    if(result != null){

        //abis scan, cek ke database npm nya valid ga, kalo valid input ke table absen
        if(result.contents != null){
            scannedResult = result.contents
            barcode_text.text = scannedResult
            val mahasiswa = dbHandler.getMahasiswa(scannedResult.toInt())

            //check dulu udh absen belum, biar ga dobel absennya
            if (isAlreadyAbsen(mahasiswa.npm))
            {
                Toast.makeText(this,mahasiswa.name + " sudah dicatat kehadirannya!",Toast.LENGTH_LONG).show()
            }
            else
            {
                barcode_text.text = mahasiswa.npm.toString() + " - " + mahasiswa.name
                dbHandler.addAbsen(Absensi(mahasiswa.npm,mahasiswa.name,mahasiswa.classYear,LocalDateTime.now().toString() ))
                Toast.makeText(this,"Berhasil mencatat " + mahasiswa.name + " pada daftar kehadiran!",Toast.LENGTH_LONG).show()
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
        InitiateData()
        btnTriggerScan.setOnClickListener {
            run{
                IntentIntegrator(this).initiateScan()
            }
        }

        btnNPM.setOnClickListener {
            //abis scan, cek ke database npm nya valid ga, kalo valid input ke table absen )
            val mahasiswa = dbHandler.getMahasiswa(txtNpm.text.toString().toInt())
            if (mahasiswa == null){
                Toast.makeText(this,"Nomor Mahasiswa tidak valid, data tidak ditemukan!",Toast.LENGTH_LONG).show()
            }
            else{
                //check dulu udh absen belum, biar ga dobel absennya
                if (isAlreadyAbsen(mahasiswa.npm))
                {
                    Toast.makeText(this,mahasiswa.name + " sudah dicatat kehadirannya!",Toast.LENGTH_LONG).show()
                }
                else
                {
                    dbHandler.addAbsen(Absensi(mahasiswa.npm,mahasiswa.name,mahasiswa.classYear,LocalDateTime.now().toString() ))
                    Toast.makeText(this,"Berhasil mencatat " + mahasiswa.name + " pada daftar kehadiran!",Toast.LENGTH_LONG).show()
                }
            }
        }


    }

    private fun isAlreadyAbsen(npm: Int): Boolean {
        val absensi = dbHandler.getAbsenMahasiswa(npm)

        //kalo absensi ga null, berarti belom absen. jadi return true
        return absensi != null
    }

    private fun InitiateData() {
        //method ini buat ngisi ke table mahasiswa, biar nanti kalo absen ngeceknya dari table ini
        //kalo mau ganti2 data mahasiswanya di sini ya

        //sebelum di add, di delete dulu datanya jaga2 supaya ga keinput 2x
        dbHandler.deleteMahasiswa("1234567")
        dbHandler.deleteMahasiswa("1234566")
        dbHandler.deleteMahasiswa("1234568")

        dbHandler.addMahasiswa(Mahasiswa(1234567,"Andika Mufid","Fasilkom","2016"))
        dbHandler.addMahasiswa(Mahasiswa(1234566,"Shani Indira Natio","Fasilkom","2016"))
        dbHandler.addMahasiswa(Mahasiswa(1234568,"Desy Purnamasari","Fasilkom","2016"))
    }
}
