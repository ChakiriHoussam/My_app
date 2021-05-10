package com.example.DS2.myapplication



import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_quize.*
import java.lang.String
import java.util.*


public  class Quize: AppCompatActivity() {
    private val Extra_Score= "extraScore"
    private val COUNTDOWN_IN_MILLIS: Long = 30000

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
    private var textColorDefaultCd: ColorStateList? = null


    private var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 0


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
        //textViewQuestion = findViewById(R.id.text_view_question)
        textViewQuestion=text_view_question
        textViewScore = findViewById(R.id.text_view_score)
        textViewQuestionCount = findViewById(R.id.text_view_question_count)
        textViewCountDown = findViewById(R.id.text_view_countdown)
        rbGroup = findViewById(R.id.radio_group)
        rb1 = findViewById(R.id.radio_button1)
        rb2 = findViewById(R.id.radio_button2)
        rb3 = findViewById(R.id.radio_button3)
        buttonConfirmNext = findViewById(R.id.button_confirm_next)


        textColorDefaultRb=this.rb1?.textColors
        textColorDefaultCd=this.textViewCountDown?.textColors

        val dbHelper = QuizDbHelper(this)
        val chapterName = intent.getStringExtra("CHAPTER_NAME");
        questionList = dbHelper.getQuestionsBasedOnChapter(chapterName);
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
            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        } else {
            finishQuiz()
        }
    }


    private fun startCountDown() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                timeLeftInMillis = 0
                updateCountDownText()
                checkAnswer()
            }
        }.start()
    }

    private fun updateCountDownText() {
        val minutes = (timeLeftInMillis / 1000).toInt() / 60
        val seconds = (timeLeftInMillis / 1000).toInt() % 60
        val timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        textViewCountDown!!.text = timeFormatted
        if (timeLeftInMillis < 10000) {
            textViewCountDown!!.setTextColor(Color.RED)
        } else {
            textViewCountDown!!.setTextColor(textColorDefaultCd)
        }
    }


    private fun checkAnswer() {
        answered = true
        countDownTimer?.cancel();

        val rbSelectedId =radio_group.checkedRadioButtonId
        val rb = findViewById<RadioButton>(rbSelectedId);
        val answerNr=radio_group.indexOfChild(rb)+1;
        if (answerNr == currentQuestion?.getAnswerNr())
        {
            score++
            //textViewScore?.setText("Score: " + score)
            text_view_score.text = "Score : " + score
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
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer!=null){

            countDownTimer?.cancel()
        }
    }





}

//private fun Any.indexOfChild(rbSelected: Int): Int {
//    return 0
//
//}

//private fun RadioGroup.indexOfChild(rbSelected: Int): Int {
    //return 0
//}


