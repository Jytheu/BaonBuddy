package com.example.baonbuddy.screens.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.baonbuddy.R
import com.example.baonbuddy.screens.login.LoginActivity
import com.example.baonbuddy.screens.signup.SignupActivity

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        window.statusBarColor = android.graphics.Color.parseColor("#FFFFFF")
        supportActionBar?.hide()

        findViewById<AppCompatButton>(R.id.btn_signup).setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        findViewById<TextView>(R.id.btn_login_option).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}