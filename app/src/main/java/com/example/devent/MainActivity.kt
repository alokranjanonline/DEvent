package com.thestockeventsnsebse.devent

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.Timer
import kotlin.concurrent.timerTask
import android.Manifest

class MainActivity : ComponentActivity() {
    companion object {
        var adCounter:Int = 0
        var adCounterFinalValue:Int=5
        var adError:String=""
        var gBannerAd:String=""
        var gIntAd:String="ca-app-pub-8103272807637922/2707500296"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAdView = findViewById(R.id.adView)
        mAdView.setVisibility(View.INVISIBLE)
        getAdId(this)
        val buttonCSActivity: Button = findViewById(R.id.homestart)
        buttonCSActivity.setOnClickListener {
            val intent = Intent(this, ListStock::class.java)
            startActivity(intent)
        }
        Timer().schedule(timerTask {
            val intent = Intent(this@MainActivity, ListStock::class.java)
            startActivity(intent)
            finish()
        }, 1000)


    }
    fun getAdId(view: MainActivity){
        val queue = Volley.newRequestQueue(this)
        val url = "https://www.cflick.com/androidStock/fetch_ad_setting.php"
        val textShow_error_msg = findViewById<TextView>(R.id.textErrorDisplay)
        val textAdCounter = view.findViewById<TextView>(R.id.textAdCounter)
        val stringRequest = StringRequest(Request.Method.POST, url,
            { response ->
                //textShow_error_msg.text = "Response is: ${response}"
                val jsonObject= JSONObject(response)
                val jsonArray=jsonObject.getJSONArray("data")
                val jo=jsonArray.getJSONObject(0)
                val to=jsonArray.getJSONObject(1)
                val interestitalAdCounter=jsonArray.getJSONObject(2)
                gBannerAd=jo.get("setting_value").toString()
                gIntAd=to.get("setting_value").toString()
                adCounterFinalValue= interestitalAdCounter.get("setting_value").toString().toInt()
                textAdCounter.text= "Adcounter: "+adCounter.toString()+"adCounterFinalValue "+adCounterFinalValue
            },
            { textShow_error_msg.text = "Couldnot connect to database." })
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}