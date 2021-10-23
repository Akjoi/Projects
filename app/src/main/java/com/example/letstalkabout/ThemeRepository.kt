package com.example.letstalkabout

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import database.ThemeDatabase




// Название базы с которой работаем
private const val DATABASE_NAME = "theme-database.db"


// Все данные с базы выгружаются в этот класс и здесь мы с ними работаем
class ThemeRepository private constructor(context: Context){

    // Инициализация базы данных
    private val database : ThemeDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            ThemeDatabase::class.java,
            DATABASE_NAME
        ).createFromAsset(DATABASE_NAME)
            .build()

    // Инициализация Dao и его функций
    private val themeDao = database.themeDao()
    fun getThemes(): LiveData<List<Theme>> =
        themeDao.getThemes()

    fun getTheme(): LiveData<Theme?> =
        themeDao.getTheme()

    suspend fun insertTheme(theme: Theme) =
        themeDao.insertTheme(theme)

    suspend fun deleteTheme(theme: Theme) =
        themeDao.deleteTheme(theme)

    fun getSize(): LiveData<String> =
        themeDao.getSize()

    // Инициализируем ThemeRepository
    // companion object типа static метода в Java
    companion object {
        private var INSTANCE: ThemeRepository? =
            null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE =
                    ThemeRepository(context)
            }
        }
        fun get(): ThemeRepository {
            return INSTANCE ?:
            throw
            IllegalStateException("ThemeRepository must be " +
                    "initialized")
        }
    }
}