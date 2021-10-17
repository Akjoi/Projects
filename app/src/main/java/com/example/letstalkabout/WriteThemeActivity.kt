package com.example.letstalkabout

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class WriteThemeActivity: AppCompatActivity() {

    private lateinit var writeTheme: EditText
    private lateinit var safeButton: ImageButton
    private lateinit var themesButton: Button
    private val themeRepository = ThemeRepository.get()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.write_theme)
        writeTheme =
            findViewById(R.id.writeTheme)
        safeButton =
            findViewById(R.id.safeThemeButton)

        // Реализация инактивности кнопки
        safeButton.setImageResource(R.drawable.safe_button_2)
        safeButton.isEnabled = false

        themesButton =
            findViewById(R.id.themeScreenButton)
        themesButton.setOnClickListener {
            val intent = MainActivity.newIntent(this@WriteThemeActivity)
            startActivity(intent)
        }
        writeTheme.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) {
                    safeButton.setImageResource(R.drawable.safe_button_1)
                    safeButton.isEnabled = true
                }
                else {
                    safeButton.setImageResource(R.drawable.safe_button_2)
                    safeButton.isEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }


            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        // Клик сохранения
        safeButton.setOnClickListener {
            val content = writeTheme.text.toString()
                runBlocking {
                    launch {
                        themeRepository.insertTheme(Theme(theme = content, tag = 1))
                    }
                }
            val intent = ThemeActivity.newIntent(this@WriteThemeActivity)
            finish()
            startActivity(intent)
        }
    }


        companion object {
            fun newIntent(packageContext: Context): Intent {
                return Intent(packageContext, WriteThemeActivity::class.java)
            }
        }
    }
