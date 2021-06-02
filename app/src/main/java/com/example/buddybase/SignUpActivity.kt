package com.example.buddybase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.buddybase.manager.UserManager
import com.facebook.*
import com.facebook.AccessToken
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*


class SignUpActivity : AppCompatActivity() {
    var callbackManager: CallbackManager? = null
    lateinit var facebookSignInButton: LoginButton
    var firebaseAuth: FirebaseAuth? = null
    lateinit var userApp: UserApplication
    lateinit var db: FirebaseFirestore
    lateinit var docRef: DocumentReference
    lateinit var manager: UserManager
    lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //manager
        this.userApp = this.applicationContext as UserApplication

        FacebookSdk.sdkInitialize(getApplicationContext())
        AppEventsLogger.activateApp(this@SignUpActivity)

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        callbackManager = CallbackManager.Factory.create()
        facebookSignInButton = findViewById<View>(R.id.btnFacebookSignUp) as LoginButton
        facebookSignInButton.setReadPermissions("email")

        // Callback registration
        facebookSignInButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // App code
                handleFacebookAccessToken(loginResult.accessToken);
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("CreateAccountActivity", "signInWithCredential:success")
                    Log.i("token", token.token.toString())
                    user = firebaseAuth!!.currentUser!!
                    user?.let {
                        manager = this.userApp.userManager
                        manager.setEmail(user.email.toString())
                        manager.setFullName(user.displayName.toString())
                        manager.setUid(user.uid)
                        Log.i("eugene", user.uid)

                        docRef = db.collection("Users").document(user.uid)
                        docRef.get()
                            .addOnSuccessListener { document ->
                                if (document != null) {
                                    Log.i("eugene", "create user in firestore: user already exists")
                                    Log.i("eugene", "data: ${document.data}")
//                                    //TODO later move this to appropriate spot which is after running "algorithm"
//                                    matchedWith = document.data!!["Matched"] as List<String>
                                } else {
                                    Log.i("eugene", "started the doc creation")
                                    addNewUserToFirestore(user)
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.i("eugene", "something wrong:", exception)
                            }
                    }
                    startActivity(Intent(this@SignUpActivity, SurveyActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("CreateAccountActivity", "signInWithCredential:failure", task.getException())
                    Toast.makeText(this@SignUpActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addNewUserToFirestore(user: FirebaseUser) {
        val userDetails = hashMapOf(
            "FullName" to user.displayName,
            "Email" to user.email
        )
        db.collection("Users").document(user.uid).set(userDetails)
            .addOnSuccessListener { Log.i("eugene", "New User Added to Firestore: DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.i("eugene", "New User Error Adding to Firestore: Error writing document", e) }
    }
}