package com.example.clicker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SecondActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second)
    }
    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, SecondActivity::class.java)
        }
    }
}