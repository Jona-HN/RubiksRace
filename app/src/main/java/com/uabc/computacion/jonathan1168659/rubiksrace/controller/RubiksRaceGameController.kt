package com.uabc.computacion.jonathan1168659.rubiksrace.controller

import android.widget.Button
import com.uabc.computacion.jonathan1168659.rubiksrace.view.MainActivity
import com.uabc.computacion.jonathan1168659.rubiksrace.model.RubiksRaceGameModel

class RubiksRaceGameController(val view : MainActivity, val playersGridController: PlayersGridController)
{
    private val model = RubiksRaceGameModel(this)

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


    /**
     * Actualiza la vista del
     * grid del scrambler
     */
    fun updateScramblerGridView()
    {
        model.generateNewCombination()
        val scramblerGrid = model.getCombination()
        /* testing */
        println("Generando nueva combinación (scramble)")
        /* testing */

        for ((i, color) in scramblerGrid.withIndex())
        {
            view.updateColorOfScramblerBox(i, color)
        }
    }
}