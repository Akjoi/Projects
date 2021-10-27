package com.example.letstalkabout

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class WriteThemeActivity: AppCompatActivity() {

    private lateinit var writeTheme: EditText
    private lateinit var safeButton: ImageButton
    private lateinit var themesButton: Button
    private val themeRepository = ThemeRepository.get()
    private lateinit var warning: TextView

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.write_theme)
        warning =
            findViewById(R.id.warning)

        // Реализация инактивности кнопки
        safeButton =
            findViewById(R.id.safeThemeButton)
        safeButton.setImageResource(R.drawable.safe_button_2)
        safeButton.isEnabled = false
        // Клик сохранения
        safeButton.setOnClickListener {
            val content = writeTheme.text.toString()
            runBlocking {
                launch {
                    themeRepository.insertTheme(Theme(theme = content, tag = 1))
                }
            }
            val intent = ThemeActivity.newIntent(this@WriteThemeActivity)
            intent.flags = FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        themesButton =
            findViewById(R.id.themeScreenButton)
        themesButton.setOnClickListener {
            val intent = MainActivity.newIntent(this@WriteThemeActivity)
            startActivity(intent)
        }

        writeTheme =
            findViewById(R.id.writeTheme)
        writeTheme.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) {
                    if (s != null) {
                        if (s.length > 140)
                        {
                            warning.text = resources.getString(R.string.warning)
                            safeButton.setImageResource(R.drawable.safe_button_2)
                            safeButton.isEnabled = false
                        }
                        else {
                            warning.text = ""
                            safeButton.setImageResource(R.drawable.safe_button_1)
                            safeButton.isEnabled = true
                        }
                    }
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
    }


        companion object {
            fun newIntent(packageContext: Context): Intent {
                return Intent(packageContext, WriteThemeActivity::class.java)
            }
        }
    }
