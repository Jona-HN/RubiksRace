package com.uabc.computacion.jonathan1168659.rubiksrace.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreboardDao
{
    @Query("SELECT * FROM scoreboard ORDER BY time")
    fun getOrderedEntries(): Flow<List<ScoreboardEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: ScoreboardEntry)

    @Query("DELETE FROM scoreboard WHERE gameNumber = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM scoreboard")
    suspend fun deleteAll()
}