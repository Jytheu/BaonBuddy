package com.example.baonbuddy.screens.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.baonbuddy.R
import com.example.baonbuddy.screens.home.HomeActivity
import com.example.baonbuddy.utils.showToast

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.statusBarColor = android.graphics.Color.parseColor("#FFFFFF")
        supportActionBar?.hide()

        presenter = LoginPresenter(this, LoginModel(this))

        val btnLogin = findViewById<Button>(R.id.btn_login)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnBack = findViewById<ImageView>(R.id.btn_back)

        btnBack.setOnClickListener {
            finish()
        }

        btnLogin.setOnClickListener {
            presenter.onLoginClicked(
                etEmail.text.toString(),
                etPassword.text.toString()
            )
        }
    }

    override fun showError(message: String) {
        showToast(message)
    }

    override fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}