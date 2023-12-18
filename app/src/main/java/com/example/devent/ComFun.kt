package com.example.devent

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import org.json.JSONObject
import java.util.Date


lateinit var  mAdView : AdView
private var mInterstitialAd: InterstitialAd? = null
var adRequest = AdRequest.Builder().build()
var number: Int = 0
fun loadInterestitialAd(context: Context, holder:CustomAdapter.ViewHolder,gIntAd:String){
    InterstitialAd.load(context,gIntAd, adRequest, object : InterstitialAdLoadCallback() {
        override fun onAdFailedToLoad(adError: LoadAdError) {
            mInterstitialAd = null
            //holder.textView.text="Not Loaded"
        }

        override fun onAdLoaded(interstitialAd: InterstitialAd) {
            mInterstitialAd = interstitialAd
            //holder.textView.text="Ad Loaded"
        }
    })
}
fun showInterestitialAd(
    context: Context, holder:CustomAdapter.ViewHolder, activity: Activity,
    stockId:Int, stockSymbol:String, stockName:String,
    stockExdate: String, stockRecordDate:String,
    stockPurpose:String){
    mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
        override fun onAdClicked() {
            // Called when a click is recorded for an ad.
            //holder.textView.text="Ad was clicked."
            //Log.d(ContentValues.TAG, "Ad was clicked.")
        }

        override fun onAdDismissedFullScreenContent() {
            // Called when ad is dismissed.
            //Log.d(ContentValues.TAG, "Ad dismissed fullscreen content.")
            //holder.textView.text="Ad dismissed fullscreen content."
            mInterstitialAd = null
            val intent = Intent(context, activity::class.java)
            intent.putExtra("message_key", "Hello Alok")
            intent.putExtra("stockId", stockId)
            intent.putExtra("stockSymbol", stockSymbol)
            intent.putExtra("stockName", stockName)
            intent.putExtra("stockExdate", stockExdate)
            intent.putExtra("stockRecordDate", stockRecordDate)
            intent.putExtra("stockPurpose", stockPurpose)
            context.startActivity(intent)
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            // Called when ad fails to show.
            //holder.textView.text="Ad failed to show fullscreen content."
            mInterstitialAd = null
        }

        override fun onAdImpression() {
            // Called when an impression is recorded for an ad.
            //Log.d(ContentValues.TAG, "Ad recorded an impression.")
            //holder.textView.text="Ad recorded an impression."
        }

        override fun onAdShowedFullScreenContent() {
            // Called when ad is shown.
            //holder.textView.text="Ad showed fullscreen content."
            //Log.d(ContentValues.TAG, "Ad showed fullscreen content.")
        }
    }
    if (mInterstitialAd != null) {
        mInterstitialAd?.show( context as Activity)

    } else {
        //holder.textView.text="The interstitial ad wasn't ready yet."
        val intent = Intent(context, activity::class.java)
        intent.putExtra("stockId", stockId)
        intent.putExtra("stockSymbol", stockSymbol)
        intent.putExtra("stockName", stockName)
        intent.putExtra("stockExdate", stockExdate)
        intent.putExtra("stockRecordDate", stockRecordDate)
        intent.putExtra("stockPurpose", stockPurpose)
        context.startActivity(intent)
    }
}
fun show_banner_ads(mAdView:AdView,context:Context ) {
    MobileAds.initialize(context) {}
    val adRequest = AdRequest.Builder().build()
    mAdView.loadAd(adRequest)
}
fun checkForInternet(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // if the android version is equal to M
    // or greater we need to use the
    // NetworkCapabilities to check what type of
    // network has the internet connection
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        // Returns a Network object corresponding to
        // the currently active default data network.
        val network = connectivityManager.activeNetwork ?: return false

        // Representation of the capabilities of an active network.
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            // Indicates this network uses a Wi-Fi transport,
            // or WiFi has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            // Indicates this network uses a Cellular transport. or
            // Cellular has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            // else return false
            else -> false
        }
    } else {
        // if the android version is below M
        @Suppress("DEPRECATION") val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }
}
