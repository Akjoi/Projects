package com.example.clicker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextSwitcher
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

private const val APP_PREFERENCES_COUNTER = "COUNTER"
class MainActivity: AppCompatActivity(){
    private lateinit var prefs: SharedPreferences
    private lateinit var clickButton: Button
    private lateinit var money: TextSwitcher
    private lateinit var games: Button
    private lateinit var night: Button
    private var counter = 0
    private val resultLauncher = registerForActivityResult(ActivityResultContracts
        .StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            if (data != null) {
                Log.i("MainActivity", "result from NightActivity " +
                        "${data.getStringExtra(wasNightKey)}")
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        night =
            findViewById(R.id.night)
        games =
            findViewById(R.id.games)
        games.setOnClickListener{
            val intent = GamesActivity.newIntent(this@MainActivity)
            startActivity(intent)
        }
        money =
            findViewById(R.id.money)
        money.setFactory {
            val textView = TextView(this@MainActivity)
            textView.gravity = Gravity.TOP
            textView
        }
        prefs =
            getSharedPreferences("settings", Context.MODE_PRIVATE)
        val shaking =
            AnimationUtils.loadAnimation(this, R.anim.shaking)


        val `in` = AnimationUtils
            .loadAnimation(this, android.R.anim.fade_in)
        money.inAnimation = `in`

        val out = AnimationUtils.
            loadAnimation(this, android.R.anim.fade_out)
        money.outAnimation = out


        clickButton =
            findViewById(R.id.clickButton)

        night.setOnClickListener {
            fun openSomeActivityForResult() {
                val intent = Intent(this, NightActivity::class.java)
                resultLauncher.launch(intent)
            }
            openSomeActivityForResult()
        }


        clickButton.setOnClickListener{
            clickButton.startAnimation(shaking)
            counter ++
            money.setText(counter.toString())
        }
    }
    override fun onPause() {
        super.onPause()
        // Запоминаем данные
        val editor = prefs.edit()
        editor.putInt(APP_PREFERENCES_COUNTER, counter).apply()
    }
    override fun onResume() {

        super.onResume()
        if(prefs.contains(APP_PREFERENCES_COUNTER)){
            // Получаем число из настроек
            counter = prefs.getInt(APP_PREFERENCES_COUNTER, 0)
        }
        money.setText(counter.toString())
    }
}

