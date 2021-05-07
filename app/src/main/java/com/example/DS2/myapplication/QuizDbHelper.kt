package com.example.DS2.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
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

        fillQuestionsTable()
        fillChapterTable();
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME)
        db.execSQL("DROP TABLE IF EXISTS " +  QuizContract.ChapterTable.TABLE_NAME)
        onCreate(db)
    }

    private fun fillQuestionsTable() {
        val q1 = Question("A is correct", "A", "B", "C", 1,"Android Fragments")
        addQuestion(q1)
        val q2 = Question("B is correct", "A", "B", "C", 2,"Android Fragments")
        addQuestion(q2)
        val q3 = Question("C is correct", "A", "B", "C", 3,"Android Intents")
        addQuestion(q3)
        val q4 = Question("A is correct again", "A", "B", "C", 1,"Android Intents")
        addQuestion(q4)
        val q5 = Question("B is correct again", "A", "B", "C", 2,"Android Sqlite")
        addQuestion(q5)
    }

    private fun fillChapterTable() {
        val c1 = Chapter("Android Fragments")
        val c2 = Chapter("Android Sqlite")
        val c3 = Chapter("Android Intents")
        addChapter(c1)
        addChapter(c2)
        addChapter(c3)
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
        private const val DATABASE_VERSION = 11

    }
}
