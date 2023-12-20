package com.example.devent

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class NewsFragment : Fragment() {
    private var list:ArrayList<ItemsViewModel> = ArrayList()
    private var recyclerview: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View=inflater.inflate(R.layout.fragment_news, container, false)
        super.onViewCreated(view,savedInstanceState)

        val adapter = CustomAdapter(list, view.context)
        //Fetch Data from server
        fetch_datea(view.context,adapter,view)
        //Fetch Data from server

        recyclerview = view.findViewById<View>(R.id.recyclerview) as RecyclerView
        recyclerview!!.layoutManager = LinearLayoutManager(activity)
        recyclerview!!.adapter = adapter

        val swipeRefreshLayout: SwipeRefreshLayout = view.findViewById(R.id.swipe)
        swipeRefreshLayout.setOnRefreshListener {
            list.clear()
            //recyclerview!!.setAdapter(adapter)
            fetch_datea(view.context,adapter,view)
            val progressCircular = view.findViewById<ProgressBar>(R.id.progress_circular)
            progressCircular.visibility= View.VISIBLE
            swipeRefreshLayout.isRefreshing = false
        }

        return view//inflater.inflate(R.layout.fragment_profile, container, false)
    }
    fun fetch_datea(view: Context, adapter:CustomAdapter,viewProgressbar: View){
        val queue = Volley.newRequestQueue(view)
        val url = "http://springtown.in/test/fetch_stock.php?stockEntryType=3"
        val textShow_error_msg = viewProgressbar.findViewById<TextView>(R.id.textErrorDisplay)
        val stringRequest = StringRequest( Request.Method.GET, url,
            { response ->
                //textShow_error_msg.text = "Response is: ${response}"
                val jsonObject= JSONObject(response)
                if(jsonObject.get("response").equals("sucess")){
                    val progressCircular = viewProgressbar.findViewById<ProgressBar>(R.id.progress_circular)
                    progressCircular.visibility= View.INVISIBLE
                    val jsonArray=jsonObject.getJSONArray("data")
                    for(i in 0.. jsonArray.length()-1){
                        val jo=jsonArray.getJSONObject(i)
                        val stockId=jo.get("stockId").toString()
                        val stockSymbol=jo.get("stockSymbol").toString()
                        val stockName=jo.get("stockName").toString()
                        val stockExdate=jo.get("stockExdate").toString()
                        val stockRecordDate=jo.get("stockRecordDate").toString()
                        val stockPurpose=jo.get("stockPurpose").toString()
                        val stockEntryType=jo.get("stockEntryType").toString()
                        val user=ItemsViewModel(stockId,stockSymbol,stockName,stockExdate,stockRecordDate,stockPurpose,stockEntryType)
                        list.add(user)
                    }
                    adapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(view, "There is some problem.", Toast.LENGTH_SHORT).show()
                }
            },
            { textShow_error_msg.text = "There is some problem. Please try again." })
        queue.add(stringRequest)
    }

}