package com.example.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel() {
    private val bank = listOf(
        Question(R.string.question_australia,
            true),
        Question(R.string.question_oceans,
            true),
        Question(R.string.question_mideast,
            false),
        Question(R.string.question_africa,
            false),
        Question(R.string.question_americas
            , true),
        Question(R.string.question_asia,
            true))

    var currentQuestion:Int = 0
    val currentQuestionAnswer:Boolean
        get() = bank[currentQuestion].answer
    val currentQuestionText: Int
        get() = bank[currentQuestion].textResId

    fun moveToNext() {
        currentQuestion = (currentQuestion + 1) %
                bank.size
    }
    fun moveToPrevious(){
        currentQuestion = (currentQuestion - 1) %
                bank.size
    }






}