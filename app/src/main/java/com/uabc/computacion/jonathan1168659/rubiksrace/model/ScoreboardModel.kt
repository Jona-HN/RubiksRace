package com.uabc.computacion.jonathan1168659.rubiksrace.model

//import com.uabc.computacion.jonathan1168659.rubiksrace.data.Scoreboard
import com.uabc.computacion.jonathan1168659.rubiksrace.database.ScoreboardEntry

/**
 * Modelo del scoreboard
 */
class ScoreboardModel
{
    /**
     * Agrega una nueva entrada
     * al scoreboard
     */
    fun addEntry(gameNumber : Int, time : Long, moves : Int, combination : IntArray)
    {
//        Scoreboard.entries.add(Entry(gameNumber, time, moves, combination))
    }

    /**
     * Agrega una nueva entrada
     * al scoreboard
     */
    fun addEntry(entry : ScoreboardEntry)
    {
//        Scoreboard.entries.add(entry)
    }

    /**
     * Regresa las entradas
     * del scoreboard
     */
//    fun getEntries() = Scoreboard.entries
}