package com.example.DS2.myapplication


import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Quize: AppCompatActivity() {
    private var textViewQuestion: TextView? = null
    private var textViewScore: TextView? = null
    private var textViewQuestionCount: TextView? = null
    private var textViewCountDown: TextView? = null
    private var rbGroup: RadioGroup? = null
    private var rb1: RadioButton? = null
    private var rb2: RadioButton? = null
    private var rb3: RadioButton? = null
    private var buttonConfirmNext: Button? = null
    ////part5
    private var textColorDefaultRb: ColorStateList? = null


    private var questionList: List<Question>? = null

   //count how many question we have shown
   private var questionCounter: Int = 0
    //number of total question that we have
    private var questionCountTotal: Int = 0
    //la question courante
    private var currentQuestion: Question? = null

    private val score = 0
    private var answered = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quize)
        textViewQuestion = findViewById(R.id.text_view_question)
        textViewScore = findViewById(R.id.text_view_score)
        textViewQuestionCount = findViewById(R.id.text_view_question_count)
        textViewCountDown = findViewById(R.id.text_view_countdown)
        rbGroup = findViewById(R.id.radio_group)
        rb1 = findViewById(R.id.radio_button1)
        rb2 = findViewById(R.id.radio_button2)
        rb3 = findViewById(R.id.radio_button3)
        buttonConfirmNext = findViewById(R.id.button_confirm_next)


        textColorDefaultRb=this.rb1?.textColors

        val dbHelper = QuizDbHelper(this)
        questionList = dbHelper.allQuestions;
        questionCountTotal= questionList!!.size


        Log.e("sting",questionCountTotal.toString())
      //  questionList.shuffle();
        Collections.shuffle(questionList)
        // questionList?.shuffled()



        fun showNextQuestion() {
            rb1!!.setTextColor(textColorDefaultRb)
            rb2!!.setTextColor(textColorDefaultRb)
            rb3!!.setTextColor(textColorDefaultRb)
            rbGroup!!.clearCheck()
            if (questionCounter < questionCountTotal) {
                //currentQuestion = questionList!![questionCounter]
                textViewQuestion!!.text = currentQuestion!!.question
                rb1!!.text = currentQuestion!!.option1
                rb2!!.text = currentQuestion!!.option2
                rb3!!.text = currentQuestion!!.option3
                questionCounter++
                textViewQuestionCount!!.text = "Question: $questionCounter/$questionCountTotal"
                answered = false
                buttonConfirmNext!!.text = "Confirm"
            } else {
                finishQuiz()
            }
        }










    }

    fun finishQuiz() {
        finish();
    }

}