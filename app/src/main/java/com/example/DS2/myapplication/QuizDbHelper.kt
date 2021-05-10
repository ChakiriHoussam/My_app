package com.example.DS2.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.DS2.myapplication.QuizContract.ChapterTable
import com.example.DS2.myapplication.QuizContract.QuestionsTable
import java.util.*


class QuizDbHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private var db: SQLiteDatabase? = null
    override fun onCreate(db: SQLiteDatabase) {
        this.db = db
        val SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +

                "${BaseColumns._ID} integer PRIMARY KEY autoincrement,"+
                "${QuestionsTable.COLUMN_QUESTION}  TEXT, " +
                "${QuestionsTable.COLUMN_CHAPTER } TEXT, " +
                "${QuestionsTable.COLUMN_OPTION1 } TEXT, " +
                "${QuestionsTable.COLUMN_OPTION2 }   TEXT, " +
                "${QuestionsTable.COLUMN_OPTION3 }  TEXT, " +
                "${QuestionsTable.COLUMN_ANSWER_NR}  INTEGER" +
                ")"
        val SQL_CREATE_CHAPTER_TABLE = "CREATE TABLE " +
                QuizContract.ChapterTable.TABLE_NAME + " ( " +

                "${BaseColumns._ID} integer PRIMARY KEY autoincrement,"+
                "${QuizContract.ChapterTable.COLUMN_NAME}  TEXT " +
                ")"
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE)
        db.execSQL(SQL_CREATE_CHAPTER_TABLE)


        fillChapterTable()
        fillQuestionsTable()

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME)
        db.execSQL("DROP TABLE IF EXISTS " +  ChapterTable.TABLE_NAME)
        onCreate(db)
    }

    private fun fillQuestionsTable() {
        val q1 = Question("Which lifecycle method is called to make an activity visible?", "onPause()", "onVisible()", ".onStart()", 3,"Android Fragments")

        val q2 = Question("Which of the following is not an activity lifecycle state?", "Started", ".Waiting", "Created", 2,"Android Fragments")

        val q3 = Question("Which lifecycle method is called to give an activity focus?", ".onResume()", "onVisible()", "onFocus()", 1,"Android Fragments")

        val q4 = Question("Your app contains a physics simulation that requires heavy computation to display onscreen. Then the user gets a phone call. Which of the following is true?", "During the phone call, you should continue computing the positions of objects in the physics simulation.", ".During the phone call, you should continue computing the positions of objects in the physics simulation.", "onFocus()", 2,"Android Fragments")

        val q5 = Question("\n" +
                "To make a class lifecycle-aware through the Jetpack lifecycle library, which interface should the class implement?", "Lifecycle", "Lifecycle.Event", ".\n" +
                "LifecycleObserver", 3,"Android Fragments")

        val q6 = Question("Which lifecycle method should you override to pause the simulation when the app is not on the screen?", ".onStop()", "onSaveInstanceState()", "onDestroy()", 1,"Android Fragments")

        val q7 = Question("Who is the developer of Kotlin?", "Eclipse", "IntelliJ IDEA", ".JetBrains", 3,"general information on kotlin")

        val q8 = Question("Which version of Android Studio has adopted kotlin init?", "2.4", ".3.4", "2.", 2,"general information on kotlin")

        val q9 = Question("\n" +
                "Which is the Project Management Tool made up of Kotlin Language?","Zoho",".Trello ","Sales Force",2,"general information on kotlin")

        val q10 = Question("What is the current version of Kotlin?","1.1","1.2",".1.3.4",1,"general information on kotlin")

        val q11=Question("\n" +
                "Which was the first purely Object Oriented Programming language developed?","Java","Kotlin",".smallTalk",1,"general information on kotlin")

        val q12 = Question("C'est quoi un intent?", "R1: LayoutManager", "R2: pont entre les activités", "R3 : SDKManager", 2,"Intents")

        val q13=Question("objectif d'utilisation d'un intent?","R1: Invoquer une vue","R2: Avoir des vues identiques,","R3 : Invoquer une autre activité",3,"Intents")

        val q14=Question("Methode permettant de lancer une activité?","R1: StartActivity","R2: StartView","R3 : ViewStart",1,"Intents")

        val q15=Question("Methode putExtra permet de ?","R1: Transmettre données","R2: Copier données","R3 : Recuperer données",1,"Intents")

        val q16=Question("Comment peut-on stocker une donnee sur android ?","Serveur via Rest","SDPunchedCard","SQLite",3,"Android Sqlite")

        val q17=Question("Comment mettre a jour une BD ?","MigrateAdaapter","SQLiteOpenHelper","SQLIntent",2,"Android Sqlite")
        addQuestion(q1)
        addQuestion(q2)
        addQuestion(q3)
        addQuestion(q4)
        addQuestion(q5)
        addQuestion(q6)
        addQuestion(q7)
        addQuestion(q8)
        addQuestion(q9)
        addQuestion(q10)
        addQuestion(q11)
        addQuestion(q12)
        addQuestion(q13)
        addQuestion(q14)
        addQuestion(q15)
        addQuestion(q16)
        addQuestion(q17)


    }

    private fun fillChapterTable() {
        val c1 = Chapter("Android Fragments")
        val c2 = Chapter("Android Sqlite")
        val c3 = Chapter("general information on kotlin")
        val c4= Chapter("intents")

        addChapter(c1)
        addChapter(c2)
        addChapter(c3)
        addChapter(c4)
    }

    private fun addQuestion(question: Question) {
        val cv = ContentValues()
        cv.put(QuestionsTable.COLUMN_QUESTION, question.question)
        cv.put(QuestionsTable.COLUMN_OPTION1, question.option1)
        cv.put(QuestionsTable.COLUMN_OPTION2, question.option2)
        cv.put(QuestionsTable.COLUMN_OPTION3, question.option3)
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.answerNr)
        cv.put(QuestionsTable.COLUMN_CHAPTER, question.chapterId)

        db!!.insert(QuestionsTable.TABLE_NAME, null, cv)
    }
    private fun addChapter(chapter: Chapter) {
        val cv = ContentValues()
        cv.put( QuizContract.ChapterTable.COLUMN_NAME, chapter.name)
        db!!.insert( QuizContract.ChapterTable.TABLE_NAME, null, cv)
    }

    val allQuestions: List<Question>
        get() {
            val questionList: MutableList<Question> = ArrayList()

            val  db = readableDatabase
            val c = db.rawQuery("SELECT * FROM " + QuizContract.QuestionsTable.TABLE_NAME, null)
            if (c.moveToFirst()) {
                do {
                    val question = Question()
                    question.question =
                        c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION))
                    question.option1 = c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION1))
                    question.option2 = c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION2))
                    question.option3 = c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION3))
                    question.answerNr = c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_ANSWER_NR))
                    question.chapterId = c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_CHAPTER))
                    questionList.add(question)
                } while (c.moveToNext())
            }
            c.close()
            return questionList
        }

    fun getQuestionsBasedOnChapter(chapterId:String?):List<Question>{
        val questionList: MutableList<Question> = ArrayList()

        val  db = readableDatabase
        val query = "SELECT * FROM " + QuizContract.QuestionsTable.TABLE_NAME+" where "+QuizContract.QuestionsTable.COLUMN_CHAPTER+" = '"+chapterId+"'"
        val c = db.rawQuery(query, null)
        if (c.moveToFirst()) {
            do {
                val question = Question()
                question.question =
                    c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION))
                question.option1 = c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION1))
                question.option2 = c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION2))
                question.option3 = c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION3))
                question.answerNr = c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_ANSWER_NR))
                question.chapterId = c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_CHAPTER))
                questionList.add(question)
            } while (c.moveToNext())
        }
        c.close()
        return questionList
    }
    val allChapter: List<String?>
        get() {
            val chapterList: MutableList<String?> = ArrayList()
            val  db = readableDatabase
            val c = db.rawQuery("SELECT * FROM " + QuizContract.ChapterTable.TABLE_NAME, null)
            if (c.moveToFirst()) {
                do {
                    val chapter = Chapter()
                    chapter.name = c.getString(c.getColumnIndex(QuizContract.ChapterTable.COLUMN_NAME))
                    chapterList.add(chapter.name)
                } while (c.moveToNext())
            }
            c.close()
            return chapterList
        }

    companion object {
        private const val DATABASE_NAME = "MyAwesomeQuiz.db"
        private const val DATABASE_VERSION = 18

    }
}
