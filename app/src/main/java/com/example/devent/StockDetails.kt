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
        val stockExdate = findViewById<TextView>(R.id.scriptExDate)
        val scriptRecordDate = findViewById<TextView>(R.id.scriptRecordDate)
        stocksDetails.text= "Stock Name: "+intent.getStringExtra("stockDetails")
        stockExdate.text= "Stock Ex Date: "+intent.getStringExtra("stockExdate")
        scriptRecordDate.text= "Stock Record Date: "+intent.getStringExtra("stockRecordDate")


        /*Show Testing Messages*/
        var textAdCounter = findViewById<TextView>(R.id.textAdCounter)
        textAdCounter.text= "Adcounter: "+MainActivity.adCounter.toString()
        /*End Testing Messages*/

        show_banner_ads(mAdView,this)
    }
}