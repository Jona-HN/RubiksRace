package com.uabc.computacion.jonathan1168659.rubiksrace.data

import kotlinx.serialization.Serializable

@Serializable
data class ScoreboardEntry(
    val gameNumber : Int,
    val time : Long,
    val moves : Int,
    val combination : IntArray
)

object Scoreboard
{
    val entries = arrayListOf<ScoreboardEntry>()
}