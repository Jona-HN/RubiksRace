package com.uabc.computacion.jonathan1168659.rubiksrace.controller

import com.uabc.computacion.jonathan1168659.rubiksrace.data.ScoreboardEntry
import com.uabc.computacion.jonathan1168659.rubiksrace.model.ScoreboardModel
import com.uabc.computacion.jonathan1168659.rubiksrace.view.ScoreboardActivity

/**
 * Controlador del scoreboard
 */
class ScoreboardController(val view : ScoreboardActivity)
{
    private val model = ScoreboardModel()

    /**
     * Por un lado, le indica al modelo que
     * guarde la nueva entrada. Le pasa la
     * información actualizada a la vista
     * para que actualice el RecyclerView
     */
    fun onNewEntry(entry : ScoreboardEntry)
    {
        model.addEntry(entry)
        println("Número de entradas: ${model.getEntries().size}")
        view.updateRecyclerViewData(model.getEntries())
    }
}