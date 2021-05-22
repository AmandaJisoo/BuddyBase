package com.example.buddybase

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class SurveyActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey);
        Log.i("SurveyActivity", "hello works")
    }
}