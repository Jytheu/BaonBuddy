package com.example.baonbuddy.screens.home

import android.content.Context
import com.example.baonbuddy.screens.history.Transaction
import com.example.baonbuddy.utils.BaonBuddy
import org.json.JSONArray

class HomeModel(private val context: Context) : HomeContract.Model {
    override fun getCurrentBalance(): String {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        val balance = sharedPref.getInt("BALANCE", 0)
        return "₱$balance"
    }

    override fun getGreeting(): String {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        val userName = sharedPref.getString("USER_NAME", "User")
        return "Good day, $userName."
    }

    override fun getUserName(): String {
        return BaonBuddy.getSharedPrefs(context).getString("USER_NAME", "User") ?: "User"
    }

    override fun getRecentTransactions(): List<Transaction> {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        val transactionsJson = sharedPref.getString("TRANSACTIONS", "[]")
        val jsonArray = JSONArray(transactionsJson)
        val transactions = mutableListOf<Transaction>()

        // Get only the last 2 transactions
        val count = jsonArray.length()
        val start = (count - 1).coerceAtLeast(0)
        val end = (count - 2).coerceAtLeast(0)

        for (i in start downTo end) {
            if (count == 0) break
            val obj = jsonArray.getJSONObject(i)
            transactions.add(
                Transaction(
                    type = obj.getString("type"),
                    amount = obj.getInt("amount"),
                    timestamp = obj.getLong("timestamp"),
                    category = obj.optString("category", "General")
                )
            )
            if (i == end && count > 0) break // safety
        }
        
        // get all and take 2
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
        return BaonBuddy.getSharedPrefs(context).getInt("SAVINGS_GOAL", 0)
    }

    override fun getRawBalance(): Int {
        return BaonBuddy.getSharedPrefs(context).getInt("BALANCE", 0)
    }
}