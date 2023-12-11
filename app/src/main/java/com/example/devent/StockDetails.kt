package com.example.devent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class StockDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_details)
        val actionbar=supportActionBar
        val str = intent.getStringExtra("stockId")
        actionbar!!.title=intent.getStringExtra("stockName")
    }
}