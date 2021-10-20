package com.example.clicker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

const val wasNightKey = "0"
class NightActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first)
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("Покорм")
            .setPositiveButton("ОК, иду на кухню") {
                    dialog, _ ->  dialog.cancel()
            }
        dialog.create().show()
        wasNight()
    }

    private fun wasNight() {
        val data = Intent().apply {
            putExtra(wasNightKey,
                "1")
        }
        setResult(Activity.RESULT_OK, data)
    }
}