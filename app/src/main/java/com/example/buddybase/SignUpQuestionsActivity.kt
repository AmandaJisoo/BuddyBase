package com.example.buddybase

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.example.buddybase.databinding.ActivitySignupQuestionsBinding

//TODO: fix this later
@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivitySignupQuestionsBinding

class SignUpQuestionsActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_questions)

        binding = ActivitySignupQuestionsBinding.inflate(layoutInflater)
        val adapter = ColorBaseAdapter()

        // Set the grid view adapter
        binding.gridView.adapter = adapter

        // Configure the grid view
        binding.gridView.numColumns = 2
        binding.gridView.horizontalSpacing = 15
        binding.gridView.verticalSpacing = 15
        binding.gridView.stretchMode = GridView.STRETCH_COLUMN_WIDTH
        Log.i("AmandaSignUp", "SignUpQuestionsActivity")

    }

}