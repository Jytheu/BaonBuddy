package com.example.baonbuddy.screens.login

interface LoginContract {
    interface View {
        fun showError(message: String)
        fun navigateToHome()
    }

    interface Presenter {
        fun onLoginClicked(email: String, pass: String)
    }

    interface Model {
        fun loginUser(email: String, pass: String): Boolean
    }
}