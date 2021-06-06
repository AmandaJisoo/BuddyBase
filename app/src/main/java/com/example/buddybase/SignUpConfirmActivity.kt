package com.example.buddybase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.buddybase.databinding.ActivitySignUpConfirmBinding
import com.example.buddybase.databinding.ActivitySignUpStartBinding

class SignUpConfirmActivity  : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpConfirmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpConfirmBinding.inflate(layoutInflater).apply { setContentView(root) }

        with(binding) {
            confirmSignUpBtn.setBackgroundColor(resources.getColor(R.color.com_facebook_blue))

            confirmSignUpBtn.setOnClickListener {
                startActivity(Intent(this@SignUpConfirmActivity, HomeActivity::class.java))
                finish()
            }
        }
    }
}