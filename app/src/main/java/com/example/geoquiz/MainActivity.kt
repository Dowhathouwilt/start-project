package com.example.geoquiz

import android.app.Activity
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
private const val REQUEST_CODE_CHEAT = 0

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
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }
        updateQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, quizViewModel.currentQuestion)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT){
            quizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false)?:false
        }
    }

    private fun updateQuestion(){
        text.setText(quizViewModel.currentQuestionText)
    }
    private fun checkAnswer(userAnswer:Boolean){
       val correctAnswer: Boolean = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater && userAnswer == correctAnswer -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.tv_positive_button
            else -> R.string.tv_negative_button
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        if (messageResId == R.string.judgment_toast) {
            quizViewModel.isCheater = false
        }
    }
}