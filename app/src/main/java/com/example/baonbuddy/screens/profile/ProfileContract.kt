package com.example.baonbuddy.screens.profile

interface ProfileContract {
    interface View {
        fun displayUserInfo(name: String, email: String)
        fun displayStats(totalSpent: Int, totalBaon: Int)
        fun displayGoals(savingsGoal: Int)
        fun showMessage(message: String)
        fun navigateToLogin()
        fun navigateToHome()
        fun navigateToHistory()
        fun navigateToAddLog()
    }

    interface Presenter {
        fun onViewInitialized()
        fun onLogoutClicked()
        fun onClearDataClicked()
        fun onUpdateSavingsGoal(amount: Int)
        fun onHomeSelected()
        fun onHistorySelected()
        fun onAddLogSelected()
    }

    interface Model {
        fun getUserName(): String
        fun getUserEmail(): String
        fun getStats(): Pair<Int, Int>
        fun getSavingsGoal(): Int
        fun updateSavingsGoal(amount: Int)
        fun logout()
        fun clearAllData()
    }
}