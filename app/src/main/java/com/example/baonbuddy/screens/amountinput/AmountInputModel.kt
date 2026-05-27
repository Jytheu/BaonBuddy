package com.example.baonbuddy.screens.amountinput

import android.content.Context
import com.example.baonbuddy.utils.BaonBuddy
import org.json.JSONArray
import org.json.JSONObject

class AmountInputModel(private val context: Context) : AmountInputContract.Model {

    override fun saveTransaction(type: String, amount: Int, category: String): Boolean {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        val currentBalance = sharedPref.getInt("BALANCE", 0)
        val newBalance = if (type == "EXPENSE") {
            currentBalance - amount
        } else {
            currentBalance + amount
        }
        
        val transactionsJson = sharedPref.getString("TRANSACTIONS", "[]")
        val jsonArray = JSONArray(transactionsJson)
        
        val newTransaction = JSONObject().apply {
            put("type", type)
            put("amount", amount)
            put("timestamp", System.currentTimeMillis())
            put("category", category)
        }
        
        jsonArray.put(newTransaction)

        return sharedPref.edit().apply {
            putInt("BALANCE", newBalance)
            putString("TRANSACTIONS", jsonArray.toString())
        }.commit()
    }

    override fun getCurrentBalance(): Int {
        return BaonBuddy.getSharedPrefs(context).getInt("BALANCE", 0)
    }
}