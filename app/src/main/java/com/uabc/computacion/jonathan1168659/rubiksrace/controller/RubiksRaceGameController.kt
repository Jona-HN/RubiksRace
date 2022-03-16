package com.uabc.computacion.jonathan1168659.rubiksrace.controller

import com.uabc.computacion.jonathan1168659.rubiksrace.view.MainActivity
import com.uabc.computacion.jonathan1168659.rubiksrace.model.RubiksRaceGameModel

class RubiksRaceGameController(val view : MainActivity, val playersGridController: PlayersGridController)
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
     * Pregunta al modelo si el jugador ganó
     */
    fun checkIfPlayerWon() : Boolean
    {
        model.verifyIfPlayerWon(playersGridController.getCombination())

        if (model.playerWon)
        {
            view.showMessage("¡Felicidades! Has ganado")
        }
        else
        {
            view.showMessage("No se ha ingresado la combinación necesaria")
        }

        return model.playerWon
    }
}