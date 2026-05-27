package com.example.baonbuddy.screens.home

import com.example.baonbuddy.screens.history.Transaction

interface HomeContract {
    interface View {
        fun displayBalance(balance: String)
        fun displayGreeting(greeting: String)
        fun displayRecentTransactions(transactions: List<Transaction>)
        fun displaySavingsProgress(current: Int, goal: Int)
        fun navigateToHistory()
        fun navigateToProfile(userName: String)
        fun navigateToAddLog()
    }

    interface Presenter {
        fun onViewInitialized()
        fun onHistorySelected()
        fun onProfileSelected()
        fun onAddLogSelected()
    }

    interface Model {
        fun getCurrentBalance(): String
        fun getGreeting(): String
        fun getUserName(): String
        fun getRecentTransactions(): List<Transaction>
        fun getSavingsGoal(): Int
        fun getRawBalance(): Int
    }
}