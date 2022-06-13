package com.example.geoquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

private const val EXTRA_ANSWER_IS_TRUE = "geoquiz.answer_is_true"

class CheatingActivity : AppCompatActivity() {
    private var answerIsTrue = false
    private lateinit var  answerTextView: TextView
    private lateinit var showAnswerButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheating)
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        showAnswerButton.setOnClickListener{
            val answerText = when{
                answerIsTrue -> R.string.tv_positive_button
                else -> R.string.tv_negative_button
            }
            answerTextView.setText(answerText)
        }
    }
    companion object{
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent{
            return  Intent(packageContext, CheatingActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}