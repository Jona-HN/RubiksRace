package com.uabc.computacion.jonathan1168659.rubiksrace.controller

import com.uabc.computacion.jonathan1168659.rubiksrace.data.ScoreboardEntry
import com.uabc.computacion.jonathan1168659.rubiksrace.model.ScoreboardModel
import com.uabc.computacion.jonathan1168659.rubiksrace.view.ScoreboardActivity
import com.uabc.computacion.jonathan1168659.rubiksrace.view.recyclerview.RecyclerAdapter

/**
 * Controlador del scoreboard
 */
class ScoreboardController(val view : ScoreboardActivity)
{
    private val model = ScoreboardModel()

    /**
     * Por un lado, le indica al modelo que
     * guarde la nueva entrada. Por otro,
     * realiza las instrucciones necesarias
     * para manejar el componente RecyclerView,
     * ubicado en la vista
     */
    fun onNewEntry(entry : ScoreboardEntry)
    {
        model.addEntry(entry)
        view.adapter = RecyclerAdapter(model.getEntries())
        view.recyclerView.adapter = view.adapter
    }
}