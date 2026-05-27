package com.example.baonbuddy.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class BaonBuddy : Application() {
    companion object {
        private const val PREF_NAME = "BaonPrefs"

        fun getSharedPrefs(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }
}