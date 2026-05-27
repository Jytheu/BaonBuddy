package com.example.baonbuddy.screens.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.baonbuddy.R
import com.example.baonbuddy.screens.home.HomeActivity
import com.example.baonbuddy.utils.showToast

class SignupActivity : AppCompatActivity(), SignupContract.View {

    private lateinit var presenter: SignupContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        window.statusBarColor = android.graphics.Color.parseColor("#FFFFFF")
        supportActionBar?.hide()

        presenter = SignupPresenter(this, SignupModel(this))

        val btnSignup = findViewById<Button>(R.id.btn_login) 
        val etName = findViewById<EditText>(R.id.et_name)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnBack = findViewById<ImageView>(R.id.btn_back)

        btnBack.setOnClickListener {
            finish()
        }

        btnSignup.setOnClickListener {
            presenter.onSignupClicked(
                etName.text.toString(),
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