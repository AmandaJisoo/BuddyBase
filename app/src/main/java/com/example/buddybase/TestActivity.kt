package com.example.buddybase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.buddybase.databinding.ActivityTestBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import org.json.JSONException
import org.json.JSONObject

class TestActivity : AppCompatActivity() {
    private val FCM_API = "https://fcm.googleapis.com/fcm/send"
    private val serverKey = "key=" + "AAAAqr-v7PI:APA91bHahHey6HoHc6XYQtj9gjxw7TiY3Xebiw78YHNeJxWAAr1ra6YafSJGAyMwKsQZ-9Up6rDzm7JXxeykdFEA6NV7vibc7QCWJJx5A-lfAW0NgnhIiUkhHW0MreZviu1QYl8wsRjd"
    private val contentType = "application/json"
    private val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(this.applicationContext) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(
                NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_LOW)
            )
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        intent.extras?.let {
            for (key in it.keySet()) {
                val value = intent.extras?.get(key)
                Log.d(TAG, "Key: $key Value: $value")
            }
        }
        // [END handle_data_extras]

//        binding.subscribeButton.setOnClickListener {
//            Log.d(TAG, "Subscribing to weather topic")
//            // [START subscribe_topics]
//            Firebase.messaging.subscribeToTopic("/topics/weather")
//                .addOnCompleteListener { task ->
//                    var msg = getString(R.string.msg_subscribed)
//                    if (!task.isSuccessful) {
//                        msg = getString(R.string.msg_subscribe_failed)
//                    }
//                    Log.d(TAG, msg)
//                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//                }
//            // [END subscribe_topics]
//        }
//
//        binding.logTokenButton.setOnClickListener {
//            // Get token
//            // [START log_reg_token]
//            Firebase.messaging.getToken().addOnCompleteListener(OnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
//                    return@OnCompleteListener
//                }
//
//                // Get new FCM registration token
//                val token = task.result
//
//                // Log and toast
//                val msg = getString(R.string.msg_token_fmt, token)
//                Log.d(TAG, msg)
//                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//            })
//            // [END log_reg_token]
//        }

        binding.sendNotificationButton.setOnClickListener {
            val topic = "/topics/weather" //topic has to match what the receiver subscribed to

            val notification = JSONObject()
            val notifcationBody = JSONObject()

            try {
                notifcationBody.put("title", "Firebase Notification")
                notifcationBody.put("message", "yoyoyo")
                notification.put("to", topic)
                notification.put("data", notifcationBody)
                Log.e("TAG", "try")
            } catch (e: JSONException) {
                Log.e("TAG", "onCreate: " + e.message)
            }

            sendNotification(notification)
        }

        Toast.makeText(this, "See README for setup instructions", Toast.LENGTH_SHORT).show()
    }

    private fun sendNotification(notification: JSONObject) {
        Log.e("TAG", "sendNotification")
        val jsonObjectRequest = object : JsonObjectRequest(FCM_API, notification,
            Response.Listener { response ->
                Log.i("TAG", "onResponse: $response")
            },
            Response.ErrorListener {
                Toast.makeText(this@TestActivity, "Request error", Toast.LENGTH_LONG).show()
                Log.i("TAG", "onErrorResponse: Didn't work")
            }) {

            override fun getHeaders(): Map<String, String> {
                val params = HashMap<String, String>()
                params["Authorization"] = serverKey
                params["Content-Type"] = contentType
                return params
            }
        }
        requestQueue.add(jsonObjectRequest)
    }

    companion object {

        private const val TAG = "MainActivity"
    }
}