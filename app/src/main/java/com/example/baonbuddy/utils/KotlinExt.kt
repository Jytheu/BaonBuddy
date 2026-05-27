package com.example.baonbuddy.utils

import android.content.Context
import android.widget.TextView
import android.widget.Toast

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun TextView.setTextSafely(text: String) {
    this.text = text
}