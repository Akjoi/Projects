package database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.letstalkabout.Theme

//Интерфейс для реализации SQL запросов,
//сами запросы не комментирую тут вроде всё понятно
@Dao
interface ThemeDao {
    @Query("SELECT * FROM theme WHERE tag=1 ")
    fun getThemes(): LiveData<List<Theme>>
    @Query("SELECT * FROM theme ORDER BY RANDOM() LIMIT 1")
    fun getTheme(): LiveData<Theme?>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTheme(theme: Theme)
    @Delete
    suspend fun deleteTheme(theme: Theme)
    @Query("SELECT COUNT(*) FROM theme WHERE tag=1")
    fun getSize(): LiveData<String>
}