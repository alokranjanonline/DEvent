package com.example.devent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class DividendFragment : Fragment() {
    private var list:ArrayList<ItemsViewModel> = ArrayList()
    //private val adapter = CustomAdapter(list,this)
    private var recyclerview: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View=inflater.inflate(R.layout.fragment_dividend, container, false)
        super.onViewCreated(view,savedInstanceState)

        recyclerview = view.findViewById<View>(R.id.recyclerview) as RecyclerView
        recyclerview!!.layoutManager = LinearLayoutManager(activity)
        val data = ArrayList<ItemsViewModel>()
        for (i in 1..20) {
            data.add(ItemsViewModel("R.drawable.ic_action_name", "Item ",
            "k","k","h"))
        }
        val adapter = CustomAdapter(data, view.context)
        recyclerview!!.adapter = adapter
        return view//inflater.inflate(R.layout.fragment_profile, container, false)
    }

}