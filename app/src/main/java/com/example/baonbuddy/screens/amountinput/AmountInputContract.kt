package com.example.baonbuddy.screens.amountinput

interface AmountInputContract {
    interface View {
        fun showMessage(message: String)
        fun finishActivity()
        fun setLabel(label: String)
    }

    interface Presenter {
        fun onViewInitialized(type: String)
        fun onConfirm(type: String, amountStr: String, category: String = "General")
    }

    interface Model {
        fun saveTransaction(type: String, amount: Int, category: String): Boolean
        fun getCurrentBalance(): Int
    }
}