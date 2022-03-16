package com.uabc.computacion.jonathan1168659.rubiksrace.controller

import com.uabc.computacion.jonathan1168659.rubiksrace.view.MainActivity
import com.uabc.computacion.jonathan1168659.rubiksrace.model.RubiksRaceGameModel

class RubiksRaceGameController(val view : MainActivity, val playersGridController: PlayersGridController)
{
    private val model = RubiksRaceGameModel()

    /**
     * Pregunta al modelo si el jugador ganó,
     * dependiendo el resultado, muestra un
     * le indica a la vista que muestre un
     * mensaje en pantalla
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

        for ((i, color) in scramblerGrid.withIndex())
        {
            view.updateColorOfScramblerBox(i, color)
        }
    }
}