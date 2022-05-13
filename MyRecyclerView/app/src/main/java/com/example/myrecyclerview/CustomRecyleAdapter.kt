package com.example.myrecyclerview

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class CustomRecylerAdapter(userlist: ArrayList<RecylerModel>, context: Context) :
    RecyclerView.Adapter<CustomRecylerAdapter.MyViewHolder>() {
    var userlist: ArrayList<RecylerModel>
    var context: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.recyclerformate, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recylerModel: RecylerModel = userlist[position]
        holder.imageView.setImageResource(recylerModel.getImageurl())
        holder.numberview.setText(recylerModel.getName())
        holder.clickbtn.setOnClickListener {
            val random = Random()
            val color = Color.argb(
                255,
                random.nextInt(255),
                random.nextInt(255),
                random.nextInt(255)
            )
            val color2 = Color.argb(
                255,
                random.nextInt(255),
                random.nextInt(255),
                random.nextInt(255)
            )
            holder.relativeLayout.setBackgroundColor(color)
            holder.numberview.setTextColor(color2)
        }
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var numberedit: EditText
        var numberview: TextView
        var clickbtn: Button
        var relativeLayout: RelativeLayout

        init {
            imageView = itemView.findViewById(R.id.imageview)
            numberedit = itemView.findViewById(R.id.numbereidt)
            numberview = itemView.findViewById(R.id.numberview)
            clickbtn = itemView.findViewById(R.id.clickit)
            relativeLayout = itemView.findViewById(R.id.realivielayotu)
        }
    }

    init {
        this.userlist = userlist
        this.context = context
    }
}