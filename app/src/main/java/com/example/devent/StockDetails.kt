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
        val stockSymbol = findViewById<TextView>(R.id.scriptSymbol)
        val stocksName = findViewById<TextView>(R.id.scriptName)
        val stockExdate = findViewById<TextView>(R.id.scriptExDate)
        val scriptRecordDate = findViewById<TextView>(R.id.scriptRecordDate)
        val stockPurpose = findViewById<TextView>(R.id.scriptPurpose)
        stockSymbol.text= "Stock Sybol: "+intent.getStringExtra("stockSymbol")
        stocksName.text= "Stock Name: "+intent.getStringExtra("stockName")
        stockPurpose.text="Purpose: "+intent.getStringExtra("stockPurpose")
        stockExdate.text= "Stock Ex Date: "+intent.getStringExtra("stockExdate")
        scriptRecordDate.text= "Stock Record Date: "+intent.getStringExtra("stockRecordDate")


        /*Show Testing Messages*/
        var textAdCounter = findViewById<TextView>(R.id.textAdCounter)
        textAdCounter.text= "Adcounter: "+MainActivity.adCounter.toString()
        /*End Testing Messages*/

        show_banner_ads(mAdView,this)
    }
}