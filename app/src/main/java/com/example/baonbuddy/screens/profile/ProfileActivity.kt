package com.example.baonbuddy.screens.profile

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.baonbuddy.R
import com.example.baonbuddy.screens.addlog.AddLogActivity
import com.example.baonbuddy.screens.history.HistoryActivity
import com.example.baonbuddy.screens.home.HomeActivity
import com.example.baonbuddy.screens.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfileActivity : AppCompatActivity(), ProfileContract.View {
    private lateinit var presenter: ProfileContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        window.statusBarColor = android.graphics.Color.parseColor("#00B58B")

        presenter = ProfilePresenter(this, ProfileModel(this))

        findViewById<TextView>(R.id.tv_logout).setOnClickListener {
            presenter.onLogoutClicked()
        }

        findViewById<TextView>(R.id.btn_clear_data).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Clear Data")
                .setMessage("Are you sure you want to clear all transactions and reset your balance?")
                .setPositiveButton("Yes") { _, _ -> presenter.onClearDataClicked() }
                .setNegativeButton("No", null)
                .show()
        }

        val nav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        nav.selectedItemId = R.id.profile
        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    presenter.onHomeSelected()
                    true
                }
                R.id.history -> {
                    presenter.onHistorySelected()
                    true
                }
                R.id.profile -> true
                else -> false
            }
        }

        findViewById<FloatingActionButton>(R.id.fab_add).setOnClickListener {
            presenter.onAddLogSelected()
        }

        findViewById<androidx.cardview.widget.CardView>(R.id.card_savings_goal).setOnClickListener {
            showEditDialog("Set Savings Goal", "Enter amount") { amount ->
                presenter.onUpdateSavingsGoal(amount)
            }
        }

        presenter.onViewInitialized()
    }

    private fun showEditDialog(title: String, hint: String, onConfirm: (Int) -> Unit) {
        val input = android.widget.EditText(this)
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        input.hint = hint
        
        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val value = input.text.toString().toIntOrNull() ?: 0
                onConfirm(value)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun displayUserInfo(name: String, email: String) {
        findViewById<TextView>(R.id.tv_profile_name_header).text = name
        findViewById<TextView>(R.id.tv_profile_email).text = email
    }

    override fun displayStats(totalSpent: Int, totalBaon: Int) {
        findViewById<TextView>(R.id.tv_total_spent).text = "₱$totalSpent"
        findViewById<TextView>(R.id.tv_total_baon).text = "₱$totalBaon"
    }

    override fun displayGoals(savingsGoal: Int) {
        findViewById<TextView>(R.id.tv_savings_goal).text = "₱$savingsGoal"
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToLogin() {
        val intent = Intent(this, com.example.baonbuddy.screens.onboarding.OnboardingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        overridePendingTransition(0, 0)
    }

    override fun navigateToHistory() {
        startActivity(Intent(this, HistoryActivity::class.java))
        overridePendingTransition(0, 0)
    }

    override fun navigateToAddLog() {
        startActivity(Intent(this, AddLogActivity::class.java))
    }
}