package com.uabc.computacion.jonathan1168659.rubiksrace.controller

import com.uabc.computacion.jonathan1168659.rubiksrace.view.MainActivity
import com.uabc.computacion.jonathan1168659.rubiksrace.model.PlayersGridModel

class PlayersGridController(view : MainActivity)
{
    private val model = PlayersGridModel(this)

    /**
     * Indica al modelo que genere un nuevo grid
     */
    fun generateNewGrid()
    {
        model.generateNewGrid()
    }

    /**
     * Regresa a la vista el grid generado
     */
    fun getPlayersGrid() : Array<IntArray>
    {
        return model.grid
    }
}