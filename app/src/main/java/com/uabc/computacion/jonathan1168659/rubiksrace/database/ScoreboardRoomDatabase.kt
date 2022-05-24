package com.uabc.computacion.jonathan1168659.rubiksrace.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.*

@Database(entities = [ScoreboardEntry::class], version = 1, exportSchema = false)
@TypeConverters(CombinationConverter::class)
abstract class ScoreboardRoomDatabase : RoomDatabase()
{
    abstract fun entryDao(): ScoreboardDao

    private class ScoreboardDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback()
    {
        override fun onCreate(db: SupportSQLiteDatabase)
        {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.entryDao())
                }
            }
        }

        suspend fun populateDatabase(scoreboardDao: ScoreboardDao)
        {
            // Descomentar para borrar todos los registros
            // de la base de datos
//             scoreboardDao.deleteAll()
        }
    }

    companion object
    {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ScoreboardRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ScoreboardRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScoreboardRoomDatabase::class.java,
                    "word_database"
                )
                    .addCallback(ScoreboardDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}