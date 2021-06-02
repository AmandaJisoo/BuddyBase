package com.example.buddybase

import android.os.Bundle
import android.os.UserManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.buddybase.databinding.ActivitySurveyBinding
import com.google.firebase.firestore.FirebaseFirestore

class SurveyActivity: AppCompatActivity() {
    private lateinit var binding:ActivitySurveyBinding
    lateinit var userApp: UserApplication
    lateinit var manager: com.example.buddybase.manager.UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey);
        Log.i("SurveyActivity", "hello works")

        this.binding = ActivitySurveyBinding.inflate(layoutInflater).apply { setContentView(root) }
        this.userApp =  this.applicationContext as UserApplication

        manager = this.userApp.userManager

        with (binding) {
            email.text = manager.email
            fullName.text = manager.fullName
        }
    }
}