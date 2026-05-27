package com.example.baonbuddy.screens.signup

interface SignupContract {
    interface View {
        fun showError(message: String)
        fun navigateToHome()
    }

    interface Presenter {
        fun onSignupClicked(name: String, email: String, pass: String)
    }

    interface Model {
        fun createAccount(name: String, email: String, pass: String)
        fun accountExists(email: String): Boolean
    }
}