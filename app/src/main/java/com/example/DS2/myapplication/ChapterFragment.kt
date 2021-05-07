package com.example.DS2.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment


class ChapterFragment(val iChapterSelected: IChapterSelected) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var chapterList:List<String?>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_chapter, container, false)
        val listView = view.findViewById<ListView>(R.id.lvChapters)
        val dbHelper = QuizDbHelper(activity)
        chapterList = dbHelper.allChapter;
        val adapter = ArrayAdapter(activity!!.applicationContext, android.R.layout.simple_list_item_1, chapterList)
        listView.adapter = adapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // This is your listview's selected item
            iChapterSelected.selectedChapter(chapterList[position]);
            Toast.makeText(activity, chapterList[position], Toast.LENGTH_SHORT).show()
        }
        return view;
    }
}