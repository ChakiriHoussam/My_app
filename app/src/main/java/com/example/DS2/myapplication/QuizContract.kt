package com.example.DS2.myapplication

import android.provider.BaseColumns


class QuizContract private constructor() {
    object QuestionsTable : BaseColumns {
        const val TABLE_NAME = "quiz_questions"
        //const val COLUMN_ID ="id"
        const val COLUMN_QUESTION = "question"
        const val COLUMN_CHAPTER = "chapterId"
        const val COLUMN_OPTION1 = "option1"
        const val COLUMN_OPTION2 = "option2"
        const val COLUMN_OPTION3 = "option3"
        const val COLUMN_ANSWER_NR = "answer_nr"
    }
    object ChapterTable : BaseColumns {
        const val TABLE_NAME = "chapter"
        //const val COLUMN_ID ="id"
        const val COLUMN_NAME = "Name"
    }
}