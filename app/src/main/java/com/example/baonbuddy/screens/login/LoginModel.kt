package com.example.baonbuddy.screens.login

import android.content.Context
import com.example.baonbuddy.utils.BaonBuddy

class LoginModel(private val context: Context) : LoginContract.Model {
    override fun loginUser(email: String, pass: String): Boolean {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        val savedEmail = sharedPref.getString("USER_EMAIL", "")
        val savedPass = sharedPref.getString("USER_PASSWORD", "")
        
        return if (savedEmail.equals(email, ignoreCase = true) && savedPass == pass) {
            sharedPref.edit().putBoolean("IS_LOGGED_IN", true).apply()
            true
        } else {
            false
        }
    }
}