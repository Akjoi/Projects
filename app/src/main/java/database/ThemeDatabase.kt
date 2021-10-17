package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.letstalkabout.Theme


@Database(entities = [ Theme::class ],
    version=2,exportSchema = false)
abstract class ThemeDatabase: RoomDatabase() {
    abstract fun themeDao() : ThemeDao
}
