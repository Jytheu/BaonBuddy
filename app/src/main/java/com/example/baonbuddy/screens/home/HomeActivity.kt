package com.example.baonbuddy.screens.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.baonbuddy.R
import com.example.baonbuddy.screens.addlog.AddLogActivity
import com.example.baonbuddy.screens.history.HistoryActivity
import com.example.baonbuddy.screens.profile.ProfileActivity
import com.example.baonbuddy.utils.setTextSafely
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.TextView
import com.example.baonbuddy.screens.history.Transaction

class HomeActivity : AppCompatActivity(), HomeContract.View {
    private lateinit var presenter: HomeContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        window.statusBarColor = android.graphics.Color.parseColor("#FFFFFF")
        window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        presenter = HomePresenter(this, HomeModel(this))

        val nav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        nav.selectedItemId = R.id.home
        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> true
                R.id.history -> {
                    presenter.onHistorySelected()
                    true
                }
                R.id.profile -> {
                    presenter.onProfileSelected()
                    true
                }
                else -> false
            }
        }

        findViewById<FloatingActionButton>(R.id.fab_add).setOnClickListener {
            presenter.onAddLogSelected()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewInitialized()
    }

    override fun displayBalance(balance: String) {
        findViewById<TextView>(R.id.tv_balance).setTextSafely(balance)
    }

    override fun displayGreeting(greeting: String) {
        findViewById<TextView>(R.id.tv_greeting).setTextSafely(greeting)
    }

    override fun displayRecentTransactions(transactions: List<Transaction>) {
        val groupContainer = findViewById<android.widget.LinearLayout>(R.id.group_recent_transactions)
        val card1 = findViewById<android.view.View>(R.id.card_recent_1)
        val card2 = findViewById<android.view.View>(R.id.card_recent_2)
        val innerDivider = findViewById<android.view.View>(R.id.inner_divider)
        val tvNoRecent = findViewById<TextView>(R.id.tv_no_recent)

        if (transactions.isEmpty()) {
            groupContainer.visibility = android.view.View.GONE
            tvNoRecent.visibility = android.view.View.VISIBLE
        } else {
            tvNoRecent.visibility = android.view.View.GONE
            groupContainer.visibility = android.view.View.VISIBLE
            
            // First transaction
            card1.visibility = android.view.View.VISIBLE
            val t1 = transactions[0]
            findViewById<TextView>(R.id.tv_recent_name_1).text = if (t1.type == "BAON") "Allowance" else t1.category
            findViewById<TextView>(R.id.tv_recent_amount_1).apply {
                text = if (t1.type == "EXPENSE") "-₱${t1.amount}" else "+₱${t1.amount}"
                setTextColor(if (t1.type == "EXPENSE") android.graphics.Color.parseColor("#E53935") else android.graphics.Color.parseColor("#00B58B"))
            }

            // Second transaction
            if (transactions.size > 1) {
                card2.visibility = android.view.View.VISIBLE
                innerDivider.visibility = android.view.View.VISIBLE
                val t2 = transactions[1]
                findViewById<TextView>(R.id.tv_recent_name_2).text = if (t2.type == "BAON") "Allowance" else t2.category
                findViewById<TextView>(R.id.tv_recent_amount_2).apply {
                    text = if (t2.type == "EXPENSE") "-₱${t2.amount}" else "+₱${t2.amount}"
                    setTextColor(if (t2.type == "EXPENSE") android.graphics.Color.parseColor("#E53935") else android.graphics.Color.parseColor("#00B58B"))
                }
            } else {
                card2.visibility = android.view.View.GONE
                innerDivider.visibility = android.view.View.GONE
            }
        }
    }

    override fun displaySavingsProgress(current: Int, goal: Int) {
        val container = findViewById<android.widget.LinearLayout>(R.id.container_savings_progress)
        if (goal <= 0) {
            container.visibility = android.view.View.GONE
            return
        }

        container.visibility = android.view.View.VISIBLE
        val progressBar = findViewById<android.widget.ProgressBar>(R.id.pb_savings)
        val tvPercent = findViewById<TextView>(R.id.tv_savings_percent)
        val tvRemaining = findViewById<TextView>(R.id.tv_savings_remaining)

        val percent = ((current.toFloat() / goal.toFloat()) * 100).toInt().coerceIn(0, 100)
        progressBar.progress = percent
        tvPercent.text = "$percent%"
        
        val remaining = goal - current
        if (remaining > 0) {
            tvRemaining.text = "₱$remaining more to reach your ₱$goal goal"
        } else {
            tvRemaining.text = "Goal reached! You've saved ₱$current"
            tvRemaining.setTextColor(android.graphics.Color.parseColor("#00B58B"))
        }
    }

    override fun navigateToHistory() {
        startActivity(Intent(this, HistoryActivity::class.java))
        overridePendingTransition(0, 0)
    }

    override fun navigateToProfile(userName: String) {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("USER_NAME", userName)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    override fun navigateToAddLog() {
        startActivity(Intent(this, AddLogActivity::class.java))
    }
}