package com.example.baonbuddy.screens.history

class HistoryPresenter(
    private val view: HistoryContract.View,
    private val model: HistoryContract.Model
) : HistoryContract.Presenter {
    override fun onViewInitialized() {
        refreshData()
    }

    override fun onDeleteTransaction(position: Int) {
        if (model.deleteTransaction(position)) {
            refreshData()
        }
    }

    private fun refreshData() {
        val data = model.getTransactions()
        view.displayTransactions(data)
        view.showEmptyState(data.isEmpty())
    }

    override fun onHomeSelected() {
        view.navigateToHome()
    }

    override fun onProfileSelected() {
        view.navigateToProfile()
    }

    override fun onAddLogSelected() {
        view.navigateToAddLog()
    }
}