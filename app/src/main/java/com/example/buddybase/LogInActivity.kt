package com.example.buddybase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.buddybase.manager.UserManager
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class LogInActivity : AppCompatActivity() {
    var callbackManager: CallbackManager? = null
    lateinit var facebookSignInButton: LoginButton
    var firebaseAuth: FirebaseAuth? = null
    private val userApp: UserApplication by lazy { application as UserApplication }
    lateinit var db: FirebaseFirestore
    lateinit var docRef: DocumentReference
    private lateinit var manager: UserManager
    lateinit var user: FirebaseUser
    lateinit var signInInputsArray: Array<EditText>
    lateinit var btnSignIn: Button
    lateinit var etSignInEmail: EditText
    lateinit var etSignInPassword: EditText

    lateinit var firebaseStorageReference: StorageReference
    lateinit var llSpinner: LinearLayout
    lateinit var clSpinnerBackground: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        this.manager = userApp.userManager

        btnSignIn = findViewById<Button>(R.id.btnSignIn)
        etSignInEmail = findViewById<EditText>(R.id.etSignInEmail)
        etSignInPassword = findViewById<EditText>(R.id.etSignInPassword)
        llSpinner = findViewById<LinearLayout>(R.id.llSpinner)
        clSpinnerBackground = findViewById<ConstraintLayout>(R.id.clSpinnerBackground)

        var switchToSignUp = findViewById<TextView>(R.id.tvSwitchToSignUp)

        signInInputsArray = arrayOf(etSignInEmail, etSignInPassword)
        btnSignIn.setOnClickListener {
            llSpinner.visibility = View.VISIBLE
            clSpinnerBackground.visibility = View.VISIBLE
            signInUser()
        }

        switchToSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        FacebookSdk.sdkInitialize(getApplicationContext())
        AppEventsLogger.activateApp(this@LogInActivity)

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        callbackManager = CallbackManager.Factory.create()
        facebookSignInButton = findViewById<View>(R.id.btnFacebookSignUp) as LoginButton
        facebookSignInButton.setReadPermissions("email")

        firebaseStorageReference = Firebase.storage("gs://buddybase-efd0e.appspot.com/").reference
        manager.setStorageRef(firebaseStorageReference)

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
                            Log.i("currentUser", "user.uid: ${user.uid}")
                            manager.uid?.let { it1 -> Log.i("currentUser", "manager.uid: ${it1}") }

                            docRef = db.collection("Users").document(user.uid)

                            docRef.get()
                                    .addOnSuccessListener { document ->
                                        if (document.data != null && document.data!!["Matched"] != null) {
                                            manager.setMatchedUids(document.data!!["Matched"] as List<String>)

                                            val docRef1 = db.collection("Users")
                                            val matchedMap = mutableMapOf<String, Any>()
                                            docRef1.get()
                                                    .addOnSuccessListener { result ->
                                                        for (document in result) {
                                                            if (document.data.size > 8) {
                                                                matchedMap[document.data["FullName"] as String] = document.data
                                                            }
                                                        }
                                                        manager.setMatchedUsers(matchedMap)
                                                        startActivity(Intent(this@LogInActivity, HomeActivity::class.java))
                                                        finish()
                                                    }
                                                    .addOnFailureListener { exception ->
                                                        Log.d("rawr", "get failed with ", exception)
                                                    }
                                        } else if (document.data != null && document.data!!["Matched"] == null) {
                                            startActivity(Intent(this, SignUpStartActivity::class.java))
                                            finish()
                                        } else {
                                            Log.i("eugene", "user doesn't exist")
                                            Toast.makeText(this@LogInActivity, "User not found, please sign up or try again", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.i("eugene", "something wrong:", exception)
                                    }
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("CreateAccountActivity", "signInWithCredential:failure", task.getException())
                        Toast.makeText(this@LogInActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun signInUser() {
        if (notEmpty()) {
            firebaseAuth!!.signInWithEmailAndPassword(etSignInEmail.text.toString().trim(), etSignInPassword.text.toString().trim())
                    .addOnCompleteListener { signIn ->
                        if (signIn.isSuccessful) {

                            user = firebaseAuth!!.currentUser!!
                            docRef = db.collection("Users").document(user.uid)
                            docRef.get()
                                    .addOnSuccessListener { document ->
                                        if (document.data != null && document.data!!["Matched"] != null) {
                                            manager.setMatchedUids(document.data!!["Matched"] as List<String>)
                                            user?.let {
                                                manager.setEmail(user.email.toString())
                                                manager.setFullName(user.displayName.toString())
                                                manager.setUid(user.uid)
                                                Log.i("ahoy", "${user.displayName.toString()}")
                                            }
                                            val docRef1 = db.collection("Users")
                                            val matchedMap = mutableMapOf<String, Any>()
                                            docRef1.get()
                                                    .addOnSuccessListener { result ->
                                                        for (document in result) {
                                                            if (document.data.size > 8) {
                                                                matchedMap[document.data["FullName"] as String] = document.data
                                                            }
                                                        }
                                                        manager.setMatchedUsers(matchedMap)
                                                        startActivity(Intent(this@LogInActivity, HomeActivity::class.java))
                                                        finish()
                                                    }
                                                    .addOnFailureListener { exception ->
                                                        Log.d("rawr", "get failed with ", exception)
                                                    }
                                        } else if (document.data != null && document.data!!["Matched"] == null) {
                                            user?.let {
                                                manager.setEmail(user.email.toString())
                                                manager.setFullName(user.displayName.toString())
                                                manager.setUid(user.uid)
                                                Log.i("currentUser", "user.uid: ${user.uid}")
                                                manager.uid?.let { it1 -> Log.i("currentUser", "manager.uid: ${it1}") }

                                            }
                                            startActivity(Intent(this, SignUpStartActivity::class.java))
                                            finish()
                                        }else {
                                            Log.i("eugene", "user doesn't exist")
                                            Toast.makeText(this@LogInActivity, "User not found, please sign up or try again", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.i("eugene", "something wrong:", exception)
                                    }
                        } else {
                            Toast.makeText(this@LogInActivity, "sign in failed", Toast.LENGTH_SHORT).show()
                            llSpinner.visibility = View.INVISIBLE
                            clSpinnerBackground.visibility = View.INVISIBLE
                        }
                    }
        } else {
            signInInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        }
    }

    private fun notEmpty(): Boolean = etSignInEmail.text.toString().trim().isNotEmpty() && etSignInPassword.text.toString().trim().isNotEmpty()
}