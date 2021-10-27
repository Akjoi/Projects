package com.example.letstalkabout

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity(){


    // Инициализация переменных. В целом всё понятно по названиям
    private lateinit var randomButton:
            Button
    private lateinit var themeTextView:
            TextView
    private lateinit var userButton:
            ImageButton
    private lateinit var theme:
            Theme
    private val themeRepository = ThemeRepository.get()


    // Функция создания Активити
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        supportActionBar?.hide()
        theme = Theme()
        setContentView(R.layout.activity_main)
        themeTextView =
            findViewById(R.id.theme)


        userButton =
            findViewById(R.id.userButton)
        userButton.setOnClickListener {
            val intent = ThemeActivity.newIntent(this@MainActivity)
            startActivity(intent)
        }

        randomButton =
            findViewById(R.id.randomButton)
        randomButton.setOnClickListener{
            updateTheme()
        }

        updateTheme()

    }

    // Функция обновления темы для разговора
    private fun updateTheme() {

            themeRepository.getTheme().observe(this, {
                if (it != null) {
                    themeTextView.text = it.theme
                    startAnim()
                }
            })
    }
    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, MainActivity::class.java)
        }
    }
    private fun startAnim(){
        val anim = AnimationUtils.loadAnimation(this,R.anim.anim)
        themeTextView.startAnimation(anim)
    }
}




