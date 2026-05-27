package com.example.baonbuddy.screens.signup

import android.content.Context
import com.example.baonbuddy.utils.BaonBuddy

class SignupModel(private val context: Context) : SignupContract.Model {
    override fun createAccount(name: String, email: String, pass: String) {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        sharedPref.edit().apply {
            putBoolean("IS_LOGGED_IN", true)
            putString("USER_NAME", name)
            putString("USER_EMAIL", email)
            putString("USER_PASSWORD", pass)
            putInt("BALANCE", 0)
            putString("TRANSACTIONS", "[]")
            apply()
        }
    }

    override fun accountExists(email: String): Boolean {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        val savedEmail = sharedPref.getString("USER_EMAIL", "")
        return savedEmail.equals(email, ignoreCase = true)
    }
}