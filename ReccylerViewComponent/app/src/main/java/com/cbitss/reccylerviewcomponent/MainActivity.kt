package com.cbitss.reccylerviewcomponent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

   lateinit var itemlist:MutableList<ItemModel>
   lateinit var recyclercompointer:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclercompointer = findViewById(R.id.recyclervew)

        var layoutManager =LinearLayoutManager(applicationContext)
        recyclercompointer.layoutManager = layoutManager
        itemlist = ArrayList<ItemModel>()
        intiselist()


var adapteris = RecyclerAdapter(itemlist,applicationContext)
recyclercompointer.adapter = adapteris

    }

    private fun intiselist() {
        itemlist.add(ItemModel(R.drawable.bookone,"5435345"))
        itemlist.add(ItemModel(R.drawable.booktwo,"s45345"))
        itemlist.add(ItemModel(R.drawable.bookthree,"087987"))
        itemlist.add(ItemModel(R.drawable.sky,"5435345"))
        itemlist.add(ItemModel(R.drawable.univers,"5435345"))
        itemlist.add(ItemModel(R.drawable.bookone,"5435345"))
        itemlist.add(ItemModel(R.drawable.booktwo,"s45345"))
        itemlist.add(ItemModel(R.drawable.bookthree,"087987"))
        itemlist.add(ItemModel(R.drawable.sky,"5435345"))
        itemlist.add(ItemModel(R.drawable.univers,"5435345"))
        itemlist.add(ItemModel(R.drawable.bookone,"5435345"))
        itemlist.add(ItemModel(R.drawable.booktwo,"s45345"))
        itemlist.add(ItemModel(R.drawable.bookthree,"087987"))
        itemlist.add(ItemModel(R.drawable.sky,"5435345"))
        itemlist.add(ItemModel(R.drawable.univers,"5435345"))
        itemlist.add(ItemModel(R.drawable.bookone,"5435345"))
        itemlist.add(ItemModel(R.drawable.booktwo,"s45345"))
        itemlist.add(ItemModel(R.drawable.bookthree,"087987"))
        itemlist.add(ItemModel(R.drawable.sky,"5435345"))
        itemlist.add(ItemModel(R.drawable.univers,"5435345"))
    }
}