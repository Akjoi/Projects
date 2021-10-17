package com.example.letstalkabout

import androidx.lifecycle.ViewModel

class ThemeListViewModel: ViewModel() {
    private val themeRepository =
        ThemeRepository.get()
    val themeListLiveData = themeRepository.getThemes()
}