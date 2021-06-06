package com.example.buddybase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.buddybase.manager.UserManager
import com.facebook.*
import com.facebook.AccessToken
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
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
    private val userApp: UserApplication by lazy { application as UserApplication }
    lateinit var db: FirebaseFirestore
    lateinit var docRef: DocumentReference
    private lateinit var manager: UserManager
    lateinit var user: FirebaseUser
    lateinit var createAccountInputsArray: Array<EditText>
    lateinit var btnCreateAccount: Button
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        this.manager = userApp.userManager

        btnCreateAccount = findViewById<Button>(R.id.btnCreateAccount)
        etEmail = findViewById<EditText>(R.id.etEmail)
        etPassword = findViewById<EditText>(R.id.etPassword)
        etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)

        var switchToLogIn = findViewById<TextView>(R.id.tvSwitchToLogIn)

        createAccountInputsArray = arrayOf(etEmail, etPassword, etConfirmPassword)
        btnCreateAccount.setOnClickListener {
            signIn()
        }

        switchToLogIn.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
            finish()
        }

        FacebookSdk.sdkInitialize(getApplicationContext())
        AppEventsLogger.activateApp(this@SignUpActivity)

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        callbackManager = CallbackManager.Factory.create()
        facebookSignInButton = findViewById<View>(R.id.btnFacebookSignUp) as LoginButton
        facebookSignInButton.setReadPermissions("email")

        val initAccessToken = AccessToken.getCurrentAccessToken()
        if (initAccessToken != null && !initAccessToken.isExpired) {
            LoginManager.getInstance().logOut()
        }

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
                        manager.setEmail(user.email.toString())
                        manager.setFullName(user.displayName.toString())
                        manager.setUid(user.uid)
                        Log.i("eugene", user.uid)

                        docRef = db.collection("Users").document(user.uid)

                        docRef.get()
                            .addOnSuccessListener { document ->
                                if (document.data != null) {
//                                    //TODO: make this route to the home page instead of going to SurveyActivity
//                                    Log.i("eugene", "create user in firestore: user already exists")
//                                    Log.i("eugene", "data: ${document.data}")
////                                    //TODO later move this to appropriate spot which is after running "algorithm"
//                                    if (document.data!!["Matched"] != null) {
//                                        manager.setMatchedUids(document.data!!["Matched"] as List<String>)
//                                    }
//                                    startActivity(Intent(this@SignUpActivity, SurveyActivity::class.java))
//                                    finish()
                                    val initAccessToken2 = AccessToken.getCurrentAccessToken()
                                    if (initAccessToken2 != null && !initAccessToken2.isExpired) {
                                        LoginManager.getInstance().logOut()
                                    }
                                    Toast.makeText(this@SignUpActivity, "User already exists, please log in instead.", Toast.LENGTH_SHORT).show()
                                } else {
                                    Log.i("eugene", "started the doc creation")
                                    addNewUserToFirestore(user)
                                    startActivity(Intent(this@SignUpActivity, SignUpStartActivity::class.java))
                                    finish()
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.i("eugene", "something wrong:", exception)
                            }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("CreateAccountActivity", "signInWithCredential:failure", task.getException())
                    Toast.makeText(this@SignUpActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()
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

    private fun signIn() {
        if (identicalPassword()) {
            // identicalPassword() returns true only  when inputs are not empty and passwords are identical
            var userEmail = etEmail.text.toString().trim()
            var userPassword = etPassword.text.toString().trim()

            /*create a user*/
            firebaseAuth!!.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            var emailUser = firebaseAuth!!.currentUser!!
                            addNewUserToFirestore(emailUser)
                            user = firebaseAuth!!.currentUser!!
                            user?.let {
                                manager.setEmail(user.email.toString())
                                manager.setFullName(user.displayName.toString())
                                manager.setUid(user.uid)
                            }
                            startActivity(Intent(this, SignUpStartActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@SignUpActivity, "failed to Authenticate !", Toast.LENGTH_SHORT).show()
                        }
                    }
        }
    }

    private fun notEmpty(): Boolean = etEmail.text.toString().trim().isNotEmpty() &&
            etPassword.text.toString().trim().isNotEmpty() &&
            etConfirmPassword.text.toString().trim().isNotEmpty()

    private fun identicalPassword(): Boolean {
        var identical = false
        if (notEmpty() &&
                etPassword.text.toString().trim() == etConfirmPassword.text.toString().trim()
        ) {
            identical = true
        } else if (!notEmpty()) {
            createAccountInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        } else {
            Toast.makeText(this@SignUpActivity, "passwords are not matching !", Toast.LENGTH_SHORT).show()
        }
        return identical
    }
}