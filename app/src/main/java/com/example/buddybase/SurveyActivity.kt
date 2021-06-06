package com.example.buddybase

import android.os.Bundle
import android.os.UserManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.buddybase.databinding.ActivitySurveyBinding
import com.google.firebase.firestore.FirebaseFirestore

class SurveyActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySurveyBinding
    private val userApp: UserApplication by lazy { application as UserApplication }
    private lateinit var manager: com.example.buddybase.manager.UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySurveyBinding.inflate(layoutInflater).apply { setContentView(root) }
        this.manager = userApp.userManager

        with (binding) {
            email.text = manager.email
            fullName.text = manager.fullName
        }

//        Log.i("test", "${manager.matchedUids}")

    }
}