package com.example.baonbuddy.screens.history

data class Transaction(
    val type: String,
    val amount: Int,
    val timestamp: Long,
    val category: String = "General"
)
