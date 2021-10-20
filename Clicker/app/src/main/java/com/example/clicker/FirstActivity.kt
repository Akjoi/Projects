package com.example.clicker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class FirstActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first)
    }
    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, FirstActivity::class.java)
        }
    }
}