package com.example.buddybase

import android.content.Intent
import android.os.Bundle
import android.os.UserManager
import android.text.Editable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.buddybase.databinding.ActivitySignUpStartBinding
import com.google.firebase.firestore.FirebaseFirestore

class SignUpStartActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySignUpStartBinding
    private val userApp: UserApplication by lazy { application as UserApplication }
    private lateinit var manager: com.example.buddybase.manager.UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpStartBinding.inflate(layoutInflater).apply { setContentView(root) }
        this.manager = userApp.userManager

        with (binding) {
            if (manager.email != null) {
                etEmail.setText(manager.email)
            }
            if (manager.fullName != null && manager.fullName != "null") {
                etEmail.setText(manager.email)
                etFullName.setText(manager.fullName)

            }

            btnStartSurvey.setOnClickListener {
                val email = etEmail.text.toString()
                val name = etFullName.text.toString()
                manager.setFullName(name)
                val userDetails = hashMapOf<String, Any>()
                userDetails["Email"] = email
                userDetails["FullName"] = name
                val firestore = FirebaseFirestore.getInstance()
                val docRef = manager.uid?.let { it1 -> firestore.collection("Users").document(it1) }
                if (docRef != null) {
                    docRef.update(userDetails)
                            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
                            .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }
                }

                startActivity(Intent(this@SignUpStartActivity, SignUpQuestionsActivity::class.java))
                finish()
            }
        }
    }
}