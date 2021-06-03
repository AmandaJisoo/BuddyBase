package com.example.buddybase

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class SignUpConfirmActivity  : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_confirm)

        val confirmBtn = findViewById<Button>(R.id.confirmSignUpBtn)

        confirmBtn.setBackgroundColor(resources.getColor(R.color.com_facebook_blue))
    }
}