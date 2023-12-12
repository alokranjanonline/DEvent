package com.example.devent

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.devent.MainActivity.Companion.adCounter

class CustomAdapter(private val mList: List<ItemsViewModel>, var context:Context) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //loadInterestitialAd(context, holder)
        val ItemsViewModel = mList[position]
        holder.textView1.text = ItemsViewModel.stockId
        holder.textView.text = ItemsViewModel.stockName
        holder.textView2.text = ItemsViewModel.stockDetails
        holder.itemView.setOnClickListener{
            if(adCounter==5){
                showInterestitialAd(context, holder, StockDetails(),ItemsViewModel.stockId.toInt(),
                    ItemsViewModel.stockName,ItemsViewModel.stockDetails,ItemsViewModel.stockExdate,
                    ItemsViewModel.stockRecordDate)
                adCounter=0
            }else{
                val intent = Intent(context, StockDetails::class.java)
                intent.putExtra("stockId", ItemsViewModel.stockId.toInt() )
                intent.putExtra("stockName", ItemsViewModel.stockName)
                intent.putExtra("stockDetails", ItemsViewModel.stockDetails)
                intent.putExtra("stockExdate", ItemsViewModel.stockExdate)
                intent.putExtra("stockRecordDate", ItemsViewModel.stockRecordDate)
                context.startActivity(intent)
            }
            adCounter++
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val textView1: TextView = itemView.findViewById(R.id.textView1)
        val textView2: TextView = itemView.findViewById(R.id.textView2)
    }
}

