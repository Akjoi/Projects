package database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.letstalkabout.Theme


@Database(
    version=2,
    entities = [ Theme::class ],
    exportSchema = true)
abstract class ThemeDatabase: RoomDatabase() {
    abstract fun themeDao() : ThemeDao
}
