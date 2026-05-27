package com.example.baonbuddy.screens.signup

import android.content.Context
import com.example.baonbuddy.utils.BaonBuddy
import org.json.JSONArray
import org.json.JSONObject

class SignupModel(private val context: Context) : SignupContract.Model {
    override fun createAccount(name: String, email: String, pass: String) {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        
        // 1. add to user registry
        val registryJson = sharedPref.getString("USER_REGISTRY", "[]")
        val registry = JSONArray(registryJson)
        
        val newUser = JSONObject().apply {
            put("name", name)
            put("email", email)
            put("password", pass)
        }
        registry.put(newUser)
        
        // 2. initialize
        sharedPref.edit().apply {
            putString("USER_REGISTRY", registry.toString())
            putString("CURRENT_USER_EMAIL", email)
            putBoolean("IS_LOGGED_IN", true)
            
            // User-specific keys prefixed with email
            putInt("${email}_BALANCE", 0)
            putString("${email}_TRANSACTIONS", "[]")
            putInt("${email}_SAVINGS_GOAL", 0)
            putString("${email}_NAME", name)
            
            apply()
        }
    }

    override fun accountExists(email: String): Boolean {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        val registryJson = sharedPref.getString("USER_REGISTRY", "[]")
        val registry = JSONArray(registryJson)
        
        for (i in 0 until registry.length()) {
            val user = registry.getJSONObject(i)
            if (user.getString("email").equals(email, ignoreCase = true)) {
                return true
            }
        }
        return false
    }
}