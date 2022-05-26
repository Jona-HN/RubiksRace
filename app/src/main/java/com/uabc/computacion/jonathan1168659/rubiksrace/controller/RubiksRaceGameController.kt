package com.uabc.computacion.jonathan1168659.rubiksrace.controller

import android.widget.Toast.LENGTH_LONG
import com.uabc.computacion.jonathan1168659.rubiksrace.data.Combination
import com.uabc.computacion.jonathan1168659.rubiksrace.database.ScoreboardEntry
import com.uabc.computacion.jonathan1168659.rubiksrace.view.MainActivity
import com.uabc.computacion.jonathan1168659.rubiksrace.model.RubiksRaceGameModel

class RubiksRaceGameController(val view : MainActivity, val playersGridController: PlayersGridController)
{
    private val model = RubiksRaceGameModel()
    lateinit var lastScoreboardEntry : ScoreboardEntry
        private set

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
            model.stopTimer()
            view.showMessage("¡Felicidades! Has ganado y has tardado ${model.totalTime} segundos", LENGTH_LONG)
            generateGameScoreboardEntry()
        }
        else
        {
            view.showMessage("No se ha ingresado la combinación necesaria", LENGTH_LONG)
        }

        return model.playerWon
    }


    /**
     * Actualiza la vista del
     * grid del scrambler
     */
    suspend fun updateScramblerGridView()
    {
        model.generateNewCombination()
        refreshGridInView()
    }

    /**
     * Actualiza el color de las casillas
     * del scrambler
     */
    @Suppress("RedundantSuspendModifier")
    suspend fun refreshGridInView()
    {
        val scramblerGrid = model.getCombination()

        for ((i, color) in scramblerGrid.withIndex())
        {
            view.updateColorOfScramblerBox(i, color)
        }
    }

    /**
     * Indica al modelo que empiece a tomar
     * el tiempo
     */
    fun startTimer()
    {
        model.startTimer()
    }

    /**
     * Genera una entrada para el scoreboard
     */
    private fun generateGameScoreboardEntry()
    {
        model.incrementGameNumber()
        val newEntry = ScoreboardEntry(model.gameNumber, model.totalTime,
            playersGridController.getNumOfMoves(), Combination(model.getCombination()))
        model.resetTimer()

        lastScoreboardEntry = newEntry
    }
}