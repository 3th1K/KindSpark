package com.example.kindspark.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context

@Database(
    entities = [KindnessPrompt::class, KindnessCompletion::class, SkippedPrompt::class, UserProgress::class],
    version = 3,
    exportSchema = false
)
abstract class KindnessDatabase : RoomDatabase() {

    abstract fun kindnessDao(): KindnessPromptDao

    companion object {
        @Volatile
        private var INSTANCE: KindnessDatabase? = null

        fun getDatabase(context: Context): KindnessDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KindnessDatabase::class.java,
                    "kindness_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
