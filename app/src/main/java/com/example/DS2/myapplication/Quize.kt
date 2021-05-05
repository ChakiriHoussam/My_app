package com.example.DS2.myapplication



import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_quize.*
import java.util.*


public  class Quize: AppCompatActivity() {
    private val Extra_Score= "extraScore"

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

    private var backPressedTime: Long = 0

    //count how many question we have shown
    private var questionCounter: Int = 0
    //number of total question that we have
    private var questionCountTotal: Int = 0
    //la question courante
    private var currentQuestion: Question? = null

    private var score = 0
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


        Log.e("sting", questionCountTotal.toString())
        //  questionList.shuffle();
        Collections.shuffle(questionList)
        // questionList?.shuffled()

        showNextQuestion()

        buttonConfirmNext?.setOnClickListener {
            if (!answered) {
                if (rb1?.isChecked == true || rb2?.isChecked == true || rb3?.isChecked == true) {
                    checkAnswer()
                } else {
                    Toast.makeText(this@Quize, "Please select an answer", Toast.LENGTH_SHORT).show()
                }
            } else {
                showNextQuestion()
            }
        }


    }


    fun showNextQuestion() {
        rb1!!.setTextColor(textColorDefaultRb)
        rb2!!.setTextColor(textColorDefaultRb)
        rb3!!.setTextColor(textColorDefaultRb)
        rbGroup!!.clearCheck()
        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList!![questionCounter]
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



    private fun checkAnswer() {
        answered = true
        val rbSelected =radio_group.checkedRadioButtonId
        //val answerNr = rbGroup?.indexOfChild(rbSelected)?.plus(1)
        val answerNr=radio_group.indexOfChild(rbSelected).plus(1)
        if (answerNr == currentQuestion?.getAnswerNr())
        {
            score++
            //textViewScore?.setText("Score: " + score)
            text_view_score.setText("Score : " + score)
        }
        showSolution()
    }


    private fun showSolution() {
        rb1?.setTextColor(Color.RED)
        rb2?.setTextColor(Color.RED)
        rb3?.setTextColor(Color.RED)
        when (currentQuestion?.getAnswerNr()) {
            1 -> {
                rb1?.setTextColor(Color.GREEN)
                textViewQuestion?.setText("Answer 1 is correct")
            }
            2 -> {
                rb2?.setTextColor(Color.GREEN)
                textViewQuestion?.setText("Answer 2 is correct")
            }
            3 -> {
                rb3?.setTextColor(Color.GREEN)
                textViewQuestion?.setText("Answer 3 is correct")


            }
        }
        if (questionCounter < questionCountTotal)
        {
            buttonConfirmNext?.setText("Next")
        }
        else
        {
            buttonConfirmNext?.setText("Finish")
        }
    }
    fun finishQuiz() {
        val resultIntent = Intent()
        resultIntent.putExtra(Extra_Score, score)
        setResult(RESULT_OK, resultIntent)
        finish();
    }




}

private fun Any.indexOfChild(rbSelected: Int): Int {
    return 0

}

//private fun RadioGroup.indexOfChild(rbSelected: Int): Int {
    //return 0
//}


