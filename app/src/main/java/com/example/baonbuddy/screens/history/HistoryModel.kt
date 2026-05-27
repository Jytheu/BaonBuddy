package com.example.baonbuddy.screens.history

import android.content.Context
import com.example.baonbuddy.utils.BaonBuddy
import org.json.JSONArray

class HistoryModel(private val context: Context) : HistoryContract.Model {

    private fun getCurrentEmail(): String {
        return BaonBuddy.getSharedPrefs(context).getString("CURRENT_USER_EMAIL", "") ?: ""
    }

    override fun getTransactions(): List<Transaction> {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        val email = getCurrentEmail()
        val transactionsJson = sharedPref.getString("${email}_TRANSACTIONS", "[]")
        val jsonArray = JSONArray(transactionsJson)
        val transactions = mutableListOf<Transaction>()

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            transactions.add(
                Transaction(
                    type = obj.getString("type"),
                    amount = obj.getInt("amount"),
                    timestamp = obj.getLong("timestamp"),
                    category = obj.optString("category", "General")
                )
            )
        }
        return transactions.asReversed()
    }

    override fun deleteTransaction(position: Int): Boolean {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        val email = getCurrentEmail()
        val transactionsJson = sharedPref.getString("${email}_TRANSACTIONS", "[]")
        val jsonArray = JSONArray(transactionsJson)
        
        val actualIndex = jsonArray.length() - 1 - position
        
        if (actualIndex < 0 || actualIndex >= jsonArray.length()) return false

        val itemToRemove = jsonArray.getJSONObject(actualIndex)
        val type = itemToRemove.getString("type")
        val amount = itemToRemove.getInt("amount")

        val balanceKey = "${email}_BALANCE"
        val currentBalance = sharedPref.getInt(balanceKey, 0)
        val newBalance = if (type == "EXPENSE") {
            currentBalance + amount
        } else {
            currentBalance - amount
        }

        val newList = JSONArray()
        for (i in 0 until jsonArray.length()) {
            if (i != actualIndex) {
                newList.put(jsonArray.get(i))
            }
        }

        return sharedPref.edit().apply {
            putInt(balanceKey, newBalance)
            putString("${email}_TRANSACTIONS", newList.toString())
        }.commit()
    }
}