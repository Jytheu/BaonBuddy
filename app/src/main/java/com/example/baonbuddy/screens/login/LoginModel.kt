package com.example.baonbuddy.screens.login

import android.content.Context
import com.example.baonbuddy.utils.BaonBuddy
import org.json.JSONArray

class LoginModel(private val context: Context) : LoginContract.Model {
    override fun loginUser(email: String, pass: String): Boolean {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        val registryJson = sharedPref.getString("USER_REGISTRY", "[]")
        val registry = JSONArray(registryJson)
        
        for (i in 0 until registry.length()) {
            val user = registry.getJSONObject(i)
            val savedEmail = user.getString("email")
            val savedPass = user.getString("password")
            
            if (savedEmail.equals(email, ignoreCase = true) && savedPass == pass) {
                sharedPref.edit().apply {
                    putBoolean("IS_LOGGED_IN", true)
                    putString("CURRENT_USER_EMAIL", savedEmail)
                    apply()
                }
                return true
            }
        }
        return false
    }
}