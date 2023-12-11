package com.example.devent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class ListStock : AppCompatActivity() {
    private var list:ArrayList<ItemsViewModel> = ArrayList()
    private val adapter = CustomAdapter(list,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_stock)
        mAdView = findViewById(R.id.adView)
        fetch_data()
        //Coding for RecycleVIew
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter

        swipeToRefresh(recyclerview)
        //Show mobile ad
        show_banner_ads(mAdView,this)
    }


    fun swipeToRefresh(recyclerview:RecyclerView){
        val swipeRefreshLayout: SwipeRefreshLayout = findViewById(R.id.swipe)
        swipeRefreshLayout.setOnRefreshListener {
            val textShow_error_msg = findViewById<TextView>(R.id.textErrorDisplay)
            textShow_error_msg.text = number++.toString()
            recyclerview.setAdapter(adapter)
            swipeRefreshLayout.isRefreshing = false
        }
    }
    fun fetch_data(){
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
                        val user=ItemsViewModel(stockId,stockName,stockDesc,stockExdate)
                        list.add(user)
                    }
                    adapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(this, "There is some problem.", Toast.LENGTH_SHORT).show()
                }
            },
            { textShow_error_msg.text = "There is some problem. Please try again." })
        queue.add(stringRequest)
    }
}