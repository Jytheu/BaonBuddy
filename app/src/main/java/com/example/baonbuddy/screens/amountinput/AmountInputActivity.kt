package com.example.baonbuddy.screens.amountinput

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.baonbuddy.R

class AmountInputActivity : AppCompatActivity(), AmountInputContract.View {
    private lateinit var presenter: AmountInputContract.Presenter
    private lateinit var etAmount: EditText
    private lateinit var tvLabel: TextView
    private var transactionType: String = "EXPENSE"
    private var category: String = "General"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amount_input)
        window.statusBarColor = android.graphics.Color.parseColor("#FFFFFF")
        supportActionBar?.hide()

        transactionType = intent.getStringExtra("TRANSACTION_TYPE") ?: "EXPENSE"
        category = intent.getStringExtra("CATEGORY") ?: "General"
        
        presenter = AmountInputPresenter(this, AmountInputModel(this))
        
        etAmount = findViewById(R.id.etAmount)
        tvLabel = findViewById(R.id.tvInputLabel)

        findViewById<android.widget.ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        presenter.onViewInitialized(transactionType)

        findViewById<AppCompatButton>(R.id.btnConfirm).setOnClickListener {
            presenter.onConfirm(transactionType, etAmount.text.toString(), category)
        }

        findViewById<TextView>(R.id.btnCancel).setOnClickListener {
            finish()
        }
    }

    override fun setLabel(label: String) {
        tvLabel.text = label
        
        if (transactionType == "BAON") {
            val green = android.graphics.Color.parseColor("#00B58B")
            val white = android.graphics.Color.parseColor("#FFFFFF")
            val lightWhite = android.graphics.Color.parseColor("#CCFFFFFF")
            
            findViewById<android.widget.LinearLayout>(R.id.root_layout).setBackgroundColor(green)
            findViewById<android.widget.ImageView>(R.id.btn_back).apply {
                backgroundTintList = android.content.res.ColorStateList.valueOf(white)
                imageTintList = android.content.res.ColorStateList.valueOf(green)
            }
            tvLabel.setTextColor(lightWhite)
            etAmount.apply {
                setTextColor(white)
                setHintTextColor(lightWhite)
            }
            findViewById<android.view.View>(R.id.v_separator).setBackgroundColor(lightWhite)
            findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btnConfirm).apply {
                setBackgroundResource(R.drawable.rounded_bg)
                setTextColor(green)
            }
            findViewById<TextView>(R.id.btnCancel).setTextColor(white)
            window.statusBarColor = green
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun finishActivity() {
        finish()
    }
}