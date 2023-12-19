package com.example.devent

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


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
        stockSymbol.text= intent.getStringExtra("stockSymbol")
        stocksName.text= intent.getStringExtra("stockName")
        stockPurpose.text=intent.getStringExtra("stockPurpose")
        stockExdate.text= intent.getStringExtra("stockExdate")
        scriptRecordDate.text= intent.getStringExtra("stockRecordDate")


        /*Show Testing Messages*/
        var textAdCounter = findViewById<TextView>(R.id.textAdCounter)
        textAdCounter.text= "Adcounter: "+MainActivity.adCounter.toString()
        val textShow_error_msg = findViewById<TextView>(R.id.textErrorDisplay)
        val textRefresh = findViewById<TextView>(R.id.textRefresh)
        if(checkForInternet(this) == false){
            textRefresh.visibility= View.VISIBLE
            textShow_error_msg.visibility= View.VISIBLE
            textShow_error_msg.text="Failed to load data."
            textRefresh.text="RETRY"
        }
        textShow_error_msg.setOnClickListener {
            val mIntent = intent
            finish()
            startActivity(mIntent)
        }
        /*End Testing Messages*/

        show_banner_ads(mAdView,this)
    }
    /*override fun onOptionsItemSelected(item: MenuItem,): Boolean {
        val adapter= MyVPAdapter(this)
        when (item.itemId) {
            android.R.id.home ->
                Toast.makeText(applicationContext,item.itemId,Toast.LENGTH_SHORT).show()
        }
        return true
    }*/
}