package com.example.baonbuddy.screens.home

class HomePresenter(
    private val view: HomeContract.View,
    private val model: HomeContract.Model
) : HomeContract.Presenter {
    override fun onViewInitialized() {
        view.displayBalance(model.getCurrentBalance())
        view.displayGreeting(model.getGreeting())
        view.displayRecentTransactions(model.getRecentTransactions())
        view.displaySavingsProgress(model.getRawBalance(), model.getSavingsGoal())
    }

    override fun onHistorySelected() {
        view.navigateToHistory()
    }

    override fun onProfileSelected() {
        view.navigateToProfile(model.getUserName())
    }

    override fun onAddLogSelected() {
        view.navigateToAddLog()
    }
}