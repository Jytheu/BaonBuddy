package com.example.baonbuddy.screens.login

import android.util.Patterns

class LoginPresenter(
    private val view: LoginContract.View,
    private val model: LoginContract.Model
) : LoginContract.Presenter {
    override fun onLoginClicked(email: String, pass: String) {
        if (email.trim().isEmpty() || pass.isEmpty()) {
            view.showError("Please fill in all fields")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            view.showError("Please enter a valid email address")
        } else if (model.loginUser(email.trim(), pass)) {
            view.navigateToHome()
        } else {
            view.showError("Invalid email or password")
        }
    }
}