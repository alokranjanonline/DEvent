package com.example.devent

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DividendFragment : Fragment() {
    private var list:ArrayList<ItemsViewModel> = ArrayList()
    private var recyclerview: RecyclerView? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View=inflater.inflate(R.layout.fragment_dividend, container, false)
        super.onViewCreated(view,savedInstanceState)

        val adapter = CustomAdapter(list, view.context)
        //Fetch Data from server
        fetchDatea(view.context,adapter,view)
        //Fetch Data from server

        recyclerview = view.findViewById<View>(R.id.recyclerview) as RecyclerView
        recyclerview!!.layoutManager = LinearLayoutManager(activity)
        recyclerview!!.adapter = adapter


        val swipeRefreshLayout: SwipeRefreshLayout = view.findViewById(R.id.swipe)
        swipeRefreshLayout.setOnRefreshListener {
            list.clear()
            //recyclerview!!.setAdapter(adapter)
            fetchDatea(view.context,adapter,view)
            val progressCircular = view.findViewById<ProgressBar>(R.id.progress_circular)
            progressCircular.visibility= View.VISIBLE
            swipeRefreshLayout.isRefreshing = false
        }

        return view//inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchDatea(view:Context, adapter:CustomAdapter, viewProgressbar: View){
        val queue = Volley.newRequestQueue(view)
        val url = "http://springtown.in/test/fetch_stock.php?stockEntryType=1"
        //val textShow_error_msg = viewProgressbar.findViewById<TextView>(R.id.textErrorDisplay)
        val stringRequest = StringRequest( Request.Method.GET, url,
            { response ->
                //textShow_error_msg.text = "Response is: ${response}"
                val jsonObject= JSONObject(response)
                if(jsonObject.get("response").equals("sucess")){
                    val progressCircular = viewProgressbar.findViewById<ProgressBar>(R.id.progress_circular)
                    progressCircular.visibility=INVISIBLE
                    val jsonArray=jsonObject.getJSONArray("data")
                    for(i in 0.. jsonArray.length()-1){
                        val jo=jsonArray.getJSONObject(i)
                        val stockId=jo.get("stockId").toString()
                        val stockSymbol=jo.get("stockSymbol").toString()
                        val stockName=jo.get("stockName").toString()

                        val f = DateTimeFormatter.ofPattern("dd-MMM-uuuu")
                        val stockExdateP = jo.get("stockExdate").toString()
                        val ed = LocalDate.parse(stockExdateP)
                        val stockExdate = ed.format(f)
                        val stockRecordDateP = jo.get("stockRecordDate").toString()
                        val rd = LocalDate.parse(stockRecordDateP)
                        val stockRecordDate=rd.format(f)


                        //val stockRecordDate=jo.get("stockRecordDate").toString()
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
            { /*textShow_error_msg.text = "There is some problem. Please try again."*/ })
        queue.add(stringRequest)
    }

}