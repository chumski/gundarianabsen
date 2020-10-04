package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceView
import com.example.login.ui.login.ListAbsensiActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu.*

class MainActivity : AppCompatActivity() {


    var scannedResult: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)
        btnScanner.setOnClickListener {showScanner() }
        btnAbout.setOnClickListener { showAbout() }
        btnContact.setOnClickListener { showContact() }
        btnDatabase.setOnClickListener { showAbsensi() }
    }

    private fun showAbsensi() {
        val intent = Intent(this,ListAbsensiActivity::class.java)
        startActivity(intent);
    }

    private fun showContact() {
        val intent = Intent(this,ContactActivity::class.java)
        startActivity(intent);
    }

    private fun showAbout() {
        val intent = Intent( this, AboutActivity::class.java)
        startActivity(intent);
    }

    private fun showScanner() {
        val intent  = Intent(this, ScannerActivity::class.java)
        startActivity(intent);
    }


}
