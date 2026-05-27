package com.example.baonbuddy.screens.profile

class ProfilePresenter(
    private val view: ProfileContract.View,
    private val model: ProfileContract.Model
) : ProfileContract.Presenter {

    override fun onViewInitialized() {
        view.displayUserInfo(model.getUserName(), model.getUserEmail())
        val stats = model.getStats()
        view.displayStats(stats.first, stats.second)
        view.displayGoals(model.getSavingsGoal())
    }

    override fun onUpdateSavingsGoal(amount: Int) {
        model.updateSavingsGoal(amount)
        view.displayGoals(amount)
    }

    override fun onLogoutClicked() {
        model.logout()
        view.navigateToLogin()
    }

    override fun onClearDataClicked() {
        model.clearAllData()
        view.showMessage("Data cleared!")
        onViewInitialized() // Refresh
    }

    override fun onHomeSelected() {
        view.navigateToHome()
    }

    override fun onHistorySelected() {
        view.navigateToHistory()
    }

    override fun onAddLogSelected() {
        view.navigateToAddLog()
    }
}