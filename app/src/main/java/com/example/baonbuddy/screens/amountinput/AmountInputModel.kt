package com.example.baonbuddy.screens.amountinput

import android.content.Context
import com.example.baonbuddy.utils.BaonBuddy
import org.json.JSONArray
import org.json.JSONObject

class AmountInputModel(private val context: Context) : AmountInputContract.Model {

    private fun getCurrentEmail(): String {
        return BaonBuddy.getSharedPrefs(context).getString("CURRENT_USER_EMAIL", "") ?: ""
    }

    override fun saveTransaction(type: String, amount: Int, category: String): Boolean {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        val email = getCurrentEmail()
        
        val balanceKey = "${email}_BALANCE"
        val currentBalance = sharedPref.getInt(balanceKey, 0)
        val newBalance = if (type == "EXPENSE") currentBalance - amount else currentBalance + amount
        
        val transactionsKey = "${email}_TRANSACTIONS"
        val transactionsJson = sharedPref.getString(transactionsKey, "[]")
        val jsonArray = JSONArray(transactionsJson)
        
        val newTransaction = JSONObject().apply {
            put("type", type)
            put("amount", amount)
            put("timestamp", System.currentTimeMillis())
            put("category", category)
        }
        
        jsonArray.put(newTransaction)

        return sharedPref.edit().apply {
            putInt(balanceKey, newBalance)
            putString(transactionsKey, jsonArray.toString())
        }.commit()
    }

    override fun getCurrentBalance(): Int {
        val email = getCurrentEmail()
        return BaonBuddy.getSharedPrefs(context).getInt("${email}_BALANCE", 0)
    }
}