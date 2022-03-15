package com.uabc.computacion.jonathan1168659.rubiksrace.controller

import com.uabc.computacion.jonathan1168659.rubiksrace.view.MainActivity
import com.uabc.computacion.jonathan1168659.rubiksrace.model.RubiksRaceGameModel

class RubiksRaceGameController(view : MainActivity)
{
    private val model = RubiksRaceGameModel(this)

    /**
     * Indica al modelo que genere una nueva combinación
     */
    fun generateNewCombination()
    {
        model.generateNewCombination()
    }

    /**
     * Regresa a la vista el grid generado
     */
    fun getCombination() : IntArray
    {
        return model.getCombination()
    }

    /**
     * Pregunta al modelor si el jugador ganó
     */
    fun playerWon() : Boolean
    {
        return false
    }
}