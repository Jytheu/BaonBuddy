package com.example.baonbuddy.screens.signup

import android.util.Patterns

class SignupPresenter(
    private val view: SignupContract.View,
    private val model: SignupContract.Model
) : SignupContract.Presenter {
    override fun onSignupClicked(name: String, email: String, pass: String) {
        when {
            name.trim().isEmpty() -> view.showError("Please enter your name")
            email.trim().isEmpty() -> view.showError("Please enter your email")
            !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> view.showError("Please enter a valid email address")
            pass.trim().isEmpty() -> view.showError("Please enter a password")
            pass.length < 6 -> view.showError("Password must be at least 6 characters")
            model.accountExists(email.trim()) -> view.showError("Account already exists with this email")
            else -> {
                model.createAccount(name.trim(), email.trim(), pass)
                view.navigateToHome()
            }
        }
    }
}