package com.example.baonbuddy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class AddLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_log)
        window.statusBarColor = android.graphics.Color.parseColor("#FFFFFF")
        supportActionBar?.hide()

        findViewById<AppCompatButton>(R.id.btnLogExpense).setOnClickListener {
            finish()
        }

        findViewById<AppCompatButton>(R.id.btnLogBaon).setOnClickListener {
            finish()
        }
    }
}