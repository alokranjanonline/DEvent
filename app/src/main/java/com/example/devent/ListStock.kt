package com.example.devent

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ListStock : AppCompatActivity() {

    private var list:ArrayList<ItemsViewModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_stock)
        mAdView = findViewById(R.id.adView)

        //val progressCircular = findViewById<ProgressBar>(R.id.progress_circular)
        //progressCircular.visibility=VISIBLE


        val mViewPager: ViewPager2 =findViewById(R.id.my_view_pager)
        val mTabLayout: TabLayout =findViewById(R.id.my_tab_layout)

        mViewPager.adapter= MyVPAdapter(this)
        TabLayoutMediator(mTabLayout,mViewPager){
                tab,position->
            when(position){
                0->tab.text="Dividend"
                1->tab.text="Split"
                2->tab.text="News"
            }
        }.attach()

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
        //Show mobile ad
        show_banner_ads(mAdView,this)
    }
}



