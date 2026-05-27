package com.example.baonbuddy.screens.amountinput

class AmountInputPresenter(
    private val view: AmountInputContract.View,
    private val model: AmountInputContract.Model
) : AmountInputContract.Presenter {

    override fun onViewInitialized(type: String) {
        val label = if (type == "EXPENSE") "Enter Expense Amount" else "Enter Baon Amount"
        view.setLabel(label)
    }

    override fun onConfirm(type: String, amountStr: String, category: String) {
        val amount = amountStr.toIntOrNull()
        
        if (amount == null || amount <= 0) {
            view.showMessage("Please enter a valid amount")
            return
        }

        if (type == "EXPENSE") {
            val currentBalance = model.getCurrentBalance()
            if (amount > currentBalance) {
                view.showMessage("Insufficient balance! Your current baon is ₱$currentBalance")
                return
            }
        }

        val success = model.saveTransaction(type, amount, category)
        if (success) {
            view.showMessage("${type.lowercase().replaceFirstChar { it.uppercase() }} logged!")
            view.finishActivity()
        } else {
            view.showMessage("Error saving log")
        }
    }
}