package com.cbitss.reccylerviewcomponent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(var itemlist:MutableList<ItemModel>,var contextt:Context):RecyclerView.Adapter<RecyclerAdapter.MyViewHOlder>() {




    class MyViewHOlder(itemview: View):RecyclerView.ViewHolder(itemview) {
          var imageView:ImageView
          var textview:TextView
init {
    imageView= itemview.findViewById(R.id.recyclerimageview)
    textview = itemview.findViewById(R.id.recyclertextview)
}

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapter.MyViewHOlder {
       var view =LayoutInflater.from(contextt).inflate(R.layout.recycler_formate,parent,false)
       return MyViewHOlder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.MyViewHOlder, position: Int) {
       var data =itemlist.get(position)

        holder.imageView.setImageResource(data.imageurl!!)
        holder.textview.text = data.numbertext
    }

    override fun getItemCount(): Int {
       return itemlist.size
    }
}