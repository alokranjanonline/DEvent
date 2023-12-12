package com.example.devent

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.devent.ui.theme.DEventTheme
import java.util.Timer
import kotlin.concurrent.timerTask

class MainActivity : ComponentActivity() {
    companion object {
        var adCounter:Int = 0
        var adError:String=""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAdView = findViewById(R.id.adView)
        mAdView.setVisibility(View.INVISIBLE)
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
}