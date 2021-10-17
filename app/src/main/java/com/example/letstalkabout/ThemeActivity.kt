package com.example.letstalkabout

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val TAG = "ThemeActivity"
class ThemeActivity: AppCompatActivity(), ThemeListFragment.Callbacks {


    private lateinit var writeButton: Button
    private lateinit var themesButton: Button
    private val themeRepository = ThemeRepository.get()
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            themeRepository.getSize().observe(this, {
                it?.let{
                    Log.i(TAG, it)
                    if (it == "0") {
                        val fragment = EmptyFragment.newInstance()
                        supportFragmentManager.beginTransaction()
                            .add((R.id.fragment_container),
                                fragment)
                            .commit()
                    }
                    else {
                        val fragment = ThemeListFragment.newInstance()
                        supportFragmentManager
                            .beginTransaction()
                            .add(R.id.fragment_container,
                                fragment)
                            .commit()
                    }
                }
            })
        }



        writeButton =
            findViewById(R.id.newThemeButton)
        writeButton.setOnClickListener {
            val intent = WriteThemeActivity.newIntent(this@ThemeActivity)
            startActivity(intent)
        }


        // По нажатию на "Экран тем" завершаем эту активити
        themesButton =
            findViewById(R.id.themeScreenButton)
        themesButton.setOnClickListener {
            finish()
        }
    }

    override fun onThemeSelected(themeId: Int)
    {
        runBlocking {
            launch {
                themeRepository.deleteTheme(Theme(themeId))
            }
        }
    }

    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, ThemeActivity::class.java)
        }
    }
}