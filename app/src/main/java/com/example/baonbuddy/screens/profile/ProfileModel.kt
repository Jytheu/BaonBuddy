package com.example.baonbuddy.screens.profile

import android.content.Context
import com.example.baonbuddy.utils.BaonBuddy
import org.json.JSONArray

class ProfileModel(private val context: Context) : ProfileContract.Model {

    private fun getCurrentEmail(): String {
        return BaonBuddy.getSharedPrefs(context).getString("CURRENT_USER_EMAIL", "") ?: ""
    }

    override fun getUserName(): String {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        val email = getCurrentEmail()
        return sharedPref.getString("${email}_NAME", "User") ?: "User"
    }

    override fun getUserEmail(): String {
        return getCurrentEmail()
    }

    override fun logout() {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        sharedPref.edit().putBoolean("IS_LOGGED_IN", false).apply()
    }

    override fun clearAllData() {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        val email = getCurrentEmail()
        sharedPref.edit().apply {
            putInt("${email}_BALANCE", 0)
            putString("${email}_TRANSACTIONS", "[]")
            apply()
        }
    }

    override fun getStats(): Pair<Int, Int> {
        val sharedPref = BaonBuddy.getSharedPrefs(context)
        val email = getCurrentEmail()
        val transactionsJson = sharedPref.getString("${email}_TRANSACTIONS", "[]")
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
        val email = getCurrentEmail()
        return BaonBuddy.getSharedPrefs(context).getInt("${email}_SAVINGS_GOAL", 0)
    }

    override fun updateSavingsGoal(amount: Int) {
        val email = getCurrentEmail()
        BaonBuddy.getSharedPrefs(context).edit().putInt("${email}_SAVINGS_GOAL", amount).apply()
    }
}