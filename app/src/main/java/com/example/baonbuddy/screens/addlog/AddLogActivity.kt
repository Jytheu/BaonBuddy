package com.example.baonbuddy.screens.addlog

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.baonbuddy.R
import com.example.baonbuddy.screens.amountinput.AmountInputActivity

class AddLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_log)
        window.statusBarColor = android.graphics.Color.parseColor("#FFFFFF")
        supportActionBar?.hide()

        findViewById<AppCompatButton>(R.id.btnLogExpense).setOnClickListener {
            startActivity(Intent(this, CategoryPickerActivity::class.java))
            finish()
        }

        findViewById<AppCompatButton>(R.id.btnLogBaon).setOnClickListener {
            navigateToAmountInput("BAON")
        }

        findViewById<android.widget.ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }
    }

    private fun navigateToAmountInput(type: String) {
        val intent = Intent(this, AmountInputActivity::class.java)
        intent.putExtra("TRANSACTION_TYPE", type)
        startActivity(intent)
        finish() // Close the picker screen
    }
}