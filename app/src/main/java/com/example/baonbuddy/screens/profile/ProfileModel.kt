package com.example.baonbuddy.screens.profile

import android.content.Context
import com.example.baonbuddy.utils.BaonBuddy
import org.json.JSONArray

class ProfileModel(private val context: Context) : ProfileContract.Model {

    override fun getUserName(): String {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        return sharedPref.getString("USER_NAME", "User") ?: "User"
    }

    override fun getUserEmail(): String {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        return sharedPref.getString("USER_EMAIL", "No email") ?: "No email"
    }

    override fun logout() {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        sharedPref.edit().putBoolean("IS_LOGGED_IN", false).apply()
    }

    override fun clearAllData() {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        sharedPref.edit().apply {
            putInt("BALANCE", 0)
            putString("TRANSACTIONS", "[]")
            apply()
        }
    }

    override fun getStats(): Pair<Int, Int> {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        val transactionsJson = sharedPref.getString("TRANSACTIONS", "[]")
        val jsonArray = JSONArray(transactionsJson)
        var totalSpent = 0
        var totalBaon = 0

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            val type = obj.getString("type")
            val amount = obj.getInt("amount")
            if (type == "EXPENSE") totalSpent += amount
            else totalBaon += amount
        }
        return Pair(totalSpent, totalBaon)
    }

    override fun getSavingsGoal(): Int {
        return BaonBuddy.getSharedPrefs(context).getInt("SAVINGS_GOAL", 0)
    }

    override fun updateSavingsGoal(amount: Int) {
        BaonBuddy.getSharedPrefs(context).edit().putInt("SAVINGS_GOAL", amount).apply()
    }
}