package com.example.baonbuddy.screens.home

import android.content.Context
import com.example.baonbuddy.screens.history.Transaction
import com.example.baonbuddy.utils.BaonBuddy
import org.json.JSONArray

class HomeModel(private val context: Context) : HomeContract.Model {
    private fun getCurrentEmail(): String {
        return BaonBuddy.getSharedPrefs(context).getString("CURRENT_USER_EMAIL", "") ?: ""
    }

    override fun getCurrentBalance(): String {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        val email = getCurrentEmail()
        val balance = sharedPref.getInt("${email}_BALANCE", 0)
        return "₱$balance"
    }

    override fun getGreeting(): String {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        val email = getCurrentEmail()
        val userName = sharedPref.getString("${email}_NAME", "User")
        return "Good day, $userName."
    }

    override fun getUserName(): String {
        val email = getCurrentEmail()
        return BaonBuddy.getSharedPrefs(context).getString("${email}_NAME", "User") ?: "User"
    }

    override fun getRecentTransactions(): List<Transaction> {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        val email = getCurrentEmail()
        val transactionsJson = sharedPref.getString("${email}_TRANSACTIONS", "[]")
        val jsonArray = JSONArray(transactionsJson)
        
        val allTransactions = mutableListOf<Transaction>()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            allTransactions.add(
                Transaction(
                    type = obj.getString("type"),
                    amount = obj.getInt("amount"),
                    timestamp = obj.getLong("timestamp"),
                    category = obj.optString("category", "General")
                )
            )
        }
        return allTransactions.asReversed().take(2)
    }

    override fun getSavingsGoal(): Int {
        val email = getCurrentEmail()
        return BaonBuddy.getSharedPrefs(context).getInt("${email}_SAVINGS_GOAL", 0)
    }

    override fun getRawBalance(): Int {
        val email = getCurrentEmail()
        return BaonBuddy.getSharedPrefs(context).getInt("${email}_BALANCE", 0)
    }
}