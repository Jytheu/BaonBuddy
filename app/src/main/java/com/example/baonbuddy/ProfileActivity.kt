package com.example.baonbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val passedName = intent.getStringExtra("USER_NAME")
        println("Data passing: -> $passedName")

        if (passedName != null) {
            val nameTextView = findViewById<TextView>(R.id.tv_profile_name)
            nameTextView.text = "$passedName"
        }

        val nav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        nav.itemActiveIndicatorColor = null
        nav.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
        nav.selectedItemId = R.id.profile
        window.statusBarColor = android.graphics.Color.parseColor("#00B58B")

        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                R.id.history -> {
                    startActivity(Intent(this, HistoryActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                R.id.profile -> true
                else -> false
            }
        }

        findViewById<FloatingActionButton>(R.id.fab_add).setOnClickListener {
            startActivity(Intent(this, AddLogActivity::class.java))
        }
    }
}