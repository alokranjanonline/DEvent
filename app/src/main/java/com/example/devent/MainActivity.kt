package com.example.devent

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.devent.ui.theme.DEventTheme
import org.json.JSONObject
import java.util.Timer
import kotlin.concurrent.timerTask

class MainActivity : ComponentActivity() {
    companion object {
        var adCounter:Int = 0
        var adError:String=""
        var gBannerAd:String=""
        var gIntAd:String="ca-app-pub-3940256099942544/1033173712"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAdView = findViewById(R.id.adView)
        mAdView.setVisibility(View.INVISIBLE)
        getAdId()
        val buttonCSActivity: Button = findViewById(R.id.homestart)
        buttonCSActivity.setOnClickListener {
            val intent = Intent(this, ListStock::class.java)
            startActivity(intent)
        }
        Timer().schedule(timerTask {
            val intent = Intent(this@MainActivity, ListStock::class.java)
            startActivity(intent)
        }, 1000)


    }
    fun getAdId(){
        val queue = Volley.newRequestQueue(this)
        val url = "http://springtown.in/test/fetch_ad_setting.php"
        val textShow_error_msg = findViewById<TextView>(R.id.textErrorDisplay)
        val textAdcounter = findViewById<TextView>(R.id.textAdCounter)
        val stringRequest = StringRequest(Request.Method.POST, url,
            { response ->
                //textShow_error_msg.text = "Response is: ${response}"
                val jsonObject= JSONObject(response)
                val jsonArray=jsonObject.getJSONArray("data")
                val jo=jsonArray.getJSONObject(0)
                val to=jsonArray.getJSONObject(1)
                gBannerAd=jo.get("setting_value").toString()
                gIntAd=to.get("setting_value").toString()
            },
            { textShow_error_msg.text = "Couldnot connect to database." })
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}