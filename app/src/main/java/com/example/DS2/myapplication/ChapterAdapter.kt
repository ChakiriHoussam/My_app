package com.example.DS2.myapplication

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class ChapterAdapter(val context: Context,val chapterList:List<Chapter>):BaseAdapter() {
    override fun getCount(): Int {
        return chapterList.size;
    }

    override fun getItem(position: Int): Any {
        return chapterList[position]
    }

    override fun getItemId(position: Int): Long {
            return position.toLong();
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        return null
    }
}