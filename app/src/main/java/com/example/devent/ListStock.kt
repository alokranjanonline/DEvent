package com.example.devent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.json.JSONObject

class ListStock : AppCompatActivity() {
    private var list:ArrayList<ItemsViewModel> = ArrayList()
    private val adapter = CustomAdapter(list,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_stock)
        mAdView = findViewById(R.id.adView)

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
        if(checkForInternet(this) == false){
            textShow_error_msg.text="Please check your Internet connection."
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


        /*super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_stock)
        mAdView = findViewById(R.id.adView)
        fetch_data()
        //Coding for RecycleVIew
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter



        //Show mobile ad
        show_banner_ads(mAdView,this)*/
    }


    /*fun fetch_data(){
        val queue = Volley.newRequestQueue(this)
        val url = "http://springtown.in/test/fetch_stock.php"
        val textShow_error_msg = findViewById<TextView>(R.id.textErrorDisplay)
        val stringRequest = StringRequest( Request.Method.GET, url,
            { response ->
                //textShow_error_msg.text = "Response is: ${response}"
                val jsonObject= JSONObject(response)
                if(jsonObject.get("response").equals("sucess")){
                    val jsonArray=jsonObject.getJSONArray("data")
                    for(i in 0.. jsonArray.length()-1){
                        val jo=jsonArray.getJSONObject(i)
                        val stockId=jo.get("stockId").toString()
                        val stockName=jo.get("stockName").toString()
                        val stockDesc=jo.get("stockDesc").toString()
                        val stockExdate=jo.get("stockExdate").toString()
                        val stockRecordDate=jo.get("stockRecordDate").toString()
                        val user=ItemsViewModel(stockId,stockName,stockDesc,stockExdate,stockRecordDate)
                        list.add(user)
                    }
                    adapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(this, "There is some problem.", Toast.LENGTH_SHORT).show()
                }
            },
            { textShow_error_msg.text = "There is some problem. Please try again." })
        queue.add(stringRequest)
    }*/
