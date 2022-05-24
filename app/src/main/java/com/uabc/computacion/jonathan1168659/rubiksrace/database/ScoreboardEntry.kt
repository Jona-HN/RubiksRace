package com.uabc.computacion.jonathan1168659.rubiksrace.database

import androidx.room.*
import com.uabc.computacion.jonathan1168659.rubiksrace.data.Combination
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "scoreboard")
data class ScoreboardEntry(
    @PrimaryKey(autoGenerate = true)
    val gameNumber : Int,
    val time : Long,
    val moves : Int,
    val combination : Combination
)