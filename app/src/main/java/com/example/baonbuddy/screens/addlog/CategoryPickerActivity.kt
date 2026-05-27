package com.example.baonbuddy.screens.addlog

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.baonbuddy.R
import com.example.baonbuddy.screens.amountinput.AmountInputActivity

class CategoryPickerActivity : AppCompatActivity() {
    private var selectedCategory: String = "Others"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_picker)
        window.statusBarColor = android.graphics.Color.parseColor("#FFFFFF")
        supportActionBar?.hide()

        val etCustom = findViewById<EditText>(R.id.etCustomCategory)
        val btnNext = findViewById<AppCompatButton>(R.id.btnNext)
        val btnBack = findViewById<android.widget.ImageView>(R.id.btn_back)

        btnBack.setOnClickListener {
            finish()
        }

        val btns = mapOf(
            R.id.btnFood to "Food",
            R.id.btnTranspo to "Transpo",
            R.id.btnAcads to "Acads",
            R.id.btnOthers to "Others"
        )

        btns.forEach { (id, category) ->
            findViewById<AppCompatButton>(id).setOnClickListener {
                selectedCategory = category
                // Visual feedback
                navigateToAmountInput(selectedCategory)
            }
        }

        btnNext.setOnClickListener {
            val custom = etCustom.text.toString()
            if (custom.isNotEmpty()) {
                selectedCategory = custom
            }
            navigateToAmountInput(selectedCategory)
        }
    }

    private fun navigateToAmountInput(category: String) {
        val intent = Intent(this, AmountInputActivity::class.java)
        intent.putExtra("TRANSACTION_TYPE", "EXPENSE")
        intent.putExtra("CATEGORY", category)
        startActivity(intent)
        finish()
    }
}