package com.example.login.ui.login

import android.R.layout.simple_list_item_1
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import com.example.login.R
import com.example.login.helper.DatabaseHandler
import kotlinx.android.synthetic.main.activity_list_absensi.*
import kotlinx.android.synthetic.main.list_absensi.*

class ListAbsensiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Daftar Kehadiran")
        setContentView(R.layout.activity_list_absensi)
        val dbHandler = DatabaseHandler(this)
        val listAbsen = dbHandler.absensi
        val arrayColumn = arrayOf("npm","name","create_date")
        val arrayResourcesId = IntArray(3)
        arrayResourcesId[0] = R.id.lblNpm
        arrayResourcesId[1] = R.id.lblNama
        arrayResourcesId[2] = R.id.lblDate
        lvAbsensi.adapter = SimpleAdapter(this,listAbsen,R.layout.list_absensi,arrayColumn,arrayResourcesId)
    }
}
