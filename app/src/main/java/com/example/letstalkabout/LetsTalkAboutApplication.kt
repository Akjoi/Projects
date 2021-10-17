package com.example.letstalkabout

import android.app.Application
class LetsTalkAboutApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ThemeRepository.initialize(this)}}
