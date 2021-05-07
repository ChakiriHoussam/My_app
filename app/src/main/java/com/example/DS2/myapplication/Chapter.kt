package com.example.DS2.myapplication

class Chapter {
    var name: String? = null


    constructor() {}
    constructor(
        name: String?,
    ) {
        this.name = name

    }

    @JvmName("getAnswerNr1")
     fun getName():String? {
        return name
    }
}