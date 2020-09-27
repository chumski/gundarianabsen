package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceView
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_main.*

class ScannerActivity : AppCompatActivity() {

    var scannedResult: String = ""

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    var result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

    if(result != null){

        if(result.contents != null){
            scannedResult = result.contents
            barcode_text.text = scannedResult
        } else {
            barcode_text.text = ""
        }
    } else {
        super.onActivityResult(requestCode, resultCode, data)
    }
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnTriggerScan.setOnClickListener {
            run{
                IntentIntegrator(this).initiateScan()
            }
        }


    }
}
