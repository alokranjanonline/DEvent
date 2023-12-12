package com.example.devent

import android.widget.ImageView

//data class ItemsViewModel(val image: Int, val text: String) {
//}


data class ItemsViewModel( val stockId: String,
                           val stockName: String,
                           val stockDetails:String,
                           val stockExdate:String,
                           val stockRecordDate:String) {
}

