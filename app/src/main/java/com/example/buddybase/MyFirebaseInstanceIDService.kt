//package com.example.buddybase
//
//import android.util.Log
//import com.google.firebase.messaging.FirebaseMessaging
//import com.google.firebase.messaging.FirebaseMessagingService
//
////import android.app.Service
////import android.content.Intent
////import android.os.IBinder
////
////class MyFirebaseInstanceIDService : Service() {
////
////    override fun onBind(intent: Intent): IBinder {
////        TODO("Return the communication channel to the service.")
////    }
////}
////FirebaseInstanceIdService
//
//class MyFirebaseInstanceIDService : FirebaseMessagingService() {
//    fun onTokenRefresh() {
//        /*
//          This method is invoked whenever the token refreshes
//          OPTIONAL: If you want to send messages to this application instance
//          or manage this apps subscriptions on the server side,
//          you can send this token to your server.
//        */
//        val token: String = FirebaseInstanceId.getInstance().getToken()
//
//        // Once the token is generated, subscribe to topic with the userId
//        FirebaseMessaging.getInstance().subscribeToTopic(SUBSCRIBE_TO)
//        Log.i(
//            TAG,
//            "onTokenRefresh completed with token: $token"
//        )
//    }
//
//    companion object {
//        private const val TAG = "mFirebaseIIDService"
//        private const val SUBSCRIBE_TO = "userABC"
//    }
//}