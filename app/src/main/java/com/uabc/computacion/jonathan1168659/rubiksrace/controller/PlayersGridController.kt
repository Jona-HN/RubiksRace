package com.uabc.computacion.jonathan1168659.rubiksrace.controller

import android.graphics.Point
import com.uabc.computacion.jonathan1168659.rubiksrace.view.MainActivity
import com.uabc.computacion.jonathan1168659.rubiksrace.model.PlayersGridModel

class PlayersGridController(private val view: MainActivity)
{
    private val model = PlayersGridModel(this)
    private var firstBoxCoords = Point(-1, -1)
    private var secondBoxCoords = Point(-1, -1)

    val NO_OF_ROWS_N_COLS = 5

    /**
     * Guarda las coordenadas (índices)
     * de las casillas seleccionadas por el jugador.
     * Al momento de seleccionar la segunda casilla,
     * le indica al modelo que haga el movimiento de casillas
     */
    fun onBoxClick(clickedBoxCoords : Point)
    {
        if (firstBoxCoords.equals(-1, -1))
        {
            firstBoxCoords = clickedBoxCoords
        }
        else if (secondBoxCoords.equals(-1, -1))
        {
            secondBoxCoords = clickedBoxCoords
            /* testing */
            println("$firstBoxCoords & $secondBoxCoords")
            /* testing */
            val validMove = model.swapGridBoxes(firstBoxCoords, secondBoxCoords)

            if (validMove)
            {
                view.updateColorOfPlayersGridBox(firstBoxCoords, model.getBoxColor(firstBoxCoords))
                view.updateColorOfPlayersGridBox(secondBoxCoords, model.getBoxColor(secondBoxCoords))
            }
            else
            {
                view.showMessage("Movimiento inválido")
            }

            // Se reinician los valores
            firstBoxCoords.set(-1, -1)
            secondBoxCoords.set(-1, -1)
        }
    }

    /**
     * Regresa la combinación del jugador
     */
    fun getCombination() : IntArray
    {
        return model.getCombination()
    }

    /**
     * Actualiza el grid del jugador en la vista
     */
    fun updatePlayersGridView()
    {
        model.generateNewGrid()
        val playersGrid = model.grid
        /* testing */
        println("Generando un nuevo grid del jugador")
        /* testing */

        var nextColor : Int
        for (row in 0 until NO_OF_ROWS_N_COLS)
        {
            for (col in 0 until NO_OF_ROWS_N_COLS)
            {
                nextColor = playersGrid[row][col]
                view.updateColorOfPlayersGridBox(Point(row, col), nextColor)
            }
        }
    }
}