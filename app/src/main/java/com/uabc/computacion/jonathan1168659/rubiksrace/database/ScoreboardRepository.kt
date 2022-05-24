package com.uabc.computacion.jonathan1168659.rubiksrace.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class ScoreboardRepository(private val scoreboardDao : ScoreboardDao)
{
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allEntries: Flow<List<ScoreboardEntry>> = scoreboardDao.getOrderedEntries()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(entry: ScoreboardEntry) {
        scoreboardDao.insert(entry)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(id: Int) {
        scoreboardDao.delete(id)
    }
}