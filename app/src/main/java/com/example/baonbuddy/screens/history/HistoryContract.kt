package com.example.baonbuddy.screens.history

interface HistoryContract {
    interface View {
        fun displayTransactions(transactions: List<Transaction>)
        fun showEmptyState(show: Boolean)
        fun navigateToHome()
        fun navigateToProfile()
        fun navigateToAddLog()
    }

    interface Presenter {
        fun onViewInitialized()
        fun onDeleteTransaction(position: Int)
        fun onHomeSelected()
        fun onProfileSelected()
        fun onAddLogSelected()
    }

    interface Model {
        fun getTransactions(): List<Transaction>
        fun deleteTransaction(position: Int): Boolean
    }
}
