package com.example.DS2.myapplication
//MainActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.DS2.myapplication.Quize.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_quize.*
import java.util.*


class MainActivity : AppCompatActivity(), IChapterSelected {
    private val REQUEST_CODE_QUIZ = 1
    val SHARED_PREFS = "sharedPrefs"
    val KEY_HIGHSCORE = "keyHighscore"
    private var textViewHighscore: TextView? = null
    private var textViewChapteName: TextView? = null

    private var highscore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewHighscore=findViewById(R.id.text_view_highscore)
        textViewChapteName=findViewById(R.id.txt_chapter_name)

        supportFragmentManager.beginTransaction().add(R.id.fragmentHolder,ChapterFragment(this)).commit();
        loadHighscore()

        val buttonStartQuiz = findViewById<Button>(R.id.button_start_quiz)
        buttonStartQuiz.setOnClickListener { startQuiz() }
    }

    private fun startQuiz() {
        if(txt_chapter_name.text==="Select Chapter."){
            Toast.makeText(this, "Please Select Chapter First", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(this@MainActivity, Quize::class.java)
        intent.putExtra("CHAPTER_NAME",txt_chapter_name.text)
        startActivityForResult(intent, REQUEST_CODE_QUIZ)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                val score = data?.getIntExtra("extraScore",0)
                Toast.makeText(this, "score : $score", Toast.LENGTH_SHORT).show()
                if (score!! > highscore) {

                    updateHighscore(score)
                }
            }
        }
    }

    private fun loadHighscore() {
        val prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        highscore = prefs.getInt(KEY_HIGHSCORE, 0)
        textViewHighscore!!.text = "Highscore: $highscore"
    }

    private fun updateHighscore(highscoreNew: Int) {
        highscore = highscoreNew
        textViewHighscore!!.text = "Highscore: $highscore"
        val prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt(KEY_HIGHSCORE, highscore)
        editor.apply()
    }

    override fun selectedChapter(name: String?) {
        textViewChapteName?.text  = name;
    }


}