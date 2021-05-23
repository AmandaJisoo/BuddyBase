package com.example.buddybase

import android.app.Application
import com.example.buddybase.manager.UserManager

class UserApplication : Application() {
    lateinit var userManager: UserManager


    override fun onCreate() {
        super.onCreate()

        this.userManager = UserManager()

    }

}