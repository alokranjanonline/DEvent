package com.example.devent

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class StockDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_details)
        mAdView = findViewById(R.id.adView)
        val actionbar=supportActionBar
        val str = intent.getStringExtra("stockId")
        actionbar!!.title=intent.getStringExtra("stockName")
        val stocksDetails = findViewById<TextView>(R.id.scriptDetails)
        stocksDetails.text= intent.getStringExtra("stockDetails")
        show_banner_ads(mAdView,this)
    }
}