package com.example.letstalkabout

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
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
        setContentView(R.layout.frame_for_user_theme_list)
        themeTextView =
            findViewById(R.id.theme)
        userButton =
            findViewById(R.id.userButton)
        randomButton =
            findViewById(R.id.randomButton)




        updateTheme()


        userButton.setOnClickListener {
            val intent = ThemeActivity.newIntent(this@MainActivity)
            startActivity(intent)
        }

        randomButton.setOnClickListener{
            updateTheme()
        }

    }

    // Функция обновления темы для разговора
    private fun updateTheme() {

            themeRepository.getTheme().observe(this, {
                if (it != null) {
                    themeTextView.text = it.theme
                }
            })
    }
    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, MainActivity::class.java)
        }
    }
}




