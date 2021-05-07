package com.example.DS2.myapplication

class Question {
    var question: String? = null
    var option1: String? = null
    var option2: String? = null
    var option3: String? = null
    var chapterId: String? = null

    var answerNr = 0

    constructor() {}
    constructor(
        question: String?,
        option1: String?,
        option2: String?,
        option3: String?,
        answerNr: Int,
        chapterId: String?

        ) {
        this.question = question
        this.option1 = option1
        this.option2 = option2
        this.option3 = option3
        this.chapterId = chapterId
        this.answerNr = answerNr
    }

    @JvmName("getAnswerNr1")
     fun getAnswerNr():Int {
        return answerNr
    }
}