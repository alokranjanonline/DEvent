package com.example.devent

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SplitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SplitFragment : Fragment() {
    private var list:ArrayList<ItemsViewModel> = ArrayList()
    private var recyclerview: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View=inflater.inflate(R.layout.fragment_split, container, false)
        super.onViewCreated(view,savedInstanceState)

        val adapter = CustomAdapter(list, view.context)
        //Fetch Data from server
        fetch_datea(view.context,adapter)
        //Fetch Data from server

        recyclerview = view.findViewById<View>(R.id.recyclerview) as RecyclerView
        recyclerview!!.layoutManager = LinearLayoutManager(activity)
        recyclerview!!.adapter = adapter

        val progressCircular = view.findViewById<View>(R.id.progress_circular)
        progressCircular.visibility=View.INVISIBLE
        val swipeRefreshLayout: SwipeRefreshLayout = view.findViewById(R.id.swipe)
        swipeRefreshLayout.setOnRefreshListener {
            recyclerview!!.setAdapter(adapter)
            swipeRefreshLayout.isRefreshing = false
        }
        return view//inflater.inflate(R.layout.fragment_profile, container, false)
    }
    fun fetch_datea(view: Context, adapter:CustomAdapter){
        val queue = Volley.newRequestQueue(view)
        val url = "http://springtown.in/test/fetch_stock.php?stockEntryType=2"
        //val textShow_error_msg = view.findViewById<TextView>(R.id.textErrorDisplay)
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
                    //Toast.makeText(this, "There is some problem.", Toast.LENGTH_SHORT).show()
                }
            },
            { /*textShow_error_msg.text = "There is some problem. Please try again."*/ })
        queue.add(stringRequest)
    }

}