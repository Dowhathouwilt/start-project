package com.example.geoquiz

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {
    private lateinit var negativeButton:Button
    private lateinit var positiveButton:Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var cheatButton: Button
    private lateinit var text:TextView
    private val quizViewModel : QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentQuestion = savedInstanceState?.getInt(KEY_INDEX,0)?:0
        quizViewModel.currentQuestion = currentQuestion

        negativeButton = findViewById(R.id.tv_negative_button)
        positiveButton = findViewById(R.id.tv_positive_button)
        nextButton = findViewById(R.id.tv_next_button)
        previousButton = findViewById(R.id.tv_previous_button)
        cheatButton = findViewById(R.id.cheat_button)

        text = findViewById(R.id.tv_text)
        updateQuestion()

        text.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }
        positiveButton.setOnClickListener{
           checkAnswer(true)
        }

        negativeButton.setOnClickListener{
            checkAnswer(false)
        }
        nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }
        previousButton.setOnClickListener{
            quizViewModel.moveToPrevious()
            updateQuestion()
        }
        cheatButton.setOnClickListener{
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatingActivity.newIntent(this, answerIsTrue)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, quizViewModel.currentQuestion)
    }

    private fun updateQuestion(){
        text.setText(quizViewModel.currentQuestionText)
    }
    private fun checkAnswer(userAnswer:Boolean){
        if (quizViewModel.currentQuestionAnswer == userAnswer){
            Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,"Not correct",Toast.LENGTH_SHORT).show()
        }
    }
}