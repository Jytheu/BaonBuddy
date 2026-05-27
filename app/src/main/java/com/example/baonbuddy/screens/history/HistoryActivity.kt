package com.example.baonbuddy.screens.history

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baonbuddy.R
import com.example.baonbuddy.screens.addlog.AddLogActivity
import com.example.baonbuddy.screens.home.HomeActivity
import com.example.baonbuddy.screens.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HistoryActivity : AppCompatActivity(), HistoryContract.View {
    private lateinit var presenter: HistoryContract.Presenter
    private lateinit var rvTransactions: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        window.statusBarColor = android.graphics.Color.parseColor("#00B58B")

        presenter = HistoryPresenter(this, HistoryModel(this))
        rvTransactions = findViewById(R.id.rvTransactions)
        rvTransactions.layoutManager = LinearLayoutManager(this)

        val nav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        nav.selectedItemId = R.id.history
        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    presenter.onHomeSelected()
                    true
                }
                R.id.history -> true
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

    override fun displayTransactions(transactions: List<Transaction>) {
        rvTransactions.adapter = TransactionAdapter(transactions) { position ->
            presenter.onDeleteTransaction(position)
        }
    }

    override fun showEmptyState(show: Boolean) {
        findViewById<android.widget.TextView>(R.id.tvEmptyState).visibility = if (show) android.view.View.VISIBLE else android.view.View.GONE
        rvTransactions.visibility = if (show) android.view.View.GONE else android.view.View.VISIBLE
    }

    override fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        overridePendingTransition(0, 0)
    }

    override fun navigateToProfile() {
        startActivity(Intent(this, ProfileActivity::class.java))
        overridePendingTransition(0, 0)
    }

    override fun navigateToAddLog() {
        startActivity(Intent(this, AddLogActivity::class.java))
    }
}
