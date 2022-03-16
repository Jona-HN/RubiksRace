package com.uabc.computacion.jonathan1168659.rubiksrace.controller

import android.graphics.Point
import com.uabc.computacion.jonathan1168659.rubiksrace.view.MainActivity
import com.uabc.computacion.jonathan1168659.rubiksrace.model.PlayersGridModel

class PlayersGridController(private val view: MainActivity)
{
    private val model = PlayersGridModel(this)
    private var firstBoxCoords = Point(-1, -1)
    private var secondBoxCoords = Point(-1, -1)

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

    /**
     * Guarda las coordenadas (índices)
     * de las casillas seleccionadas por el jugador.
     * Al momento de seleccionar la segunda casilla,
     * le indica al modelo que haga el movimiento de casillas
     */
    // TODO: corregir bug
    // (sólo permite hacer el primer movimiento, el segundo dice que
    // el orden es incorrecto).
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
                view.updateBoxColor(firstBoxCoords, model.getBoxColor(firstBoxCoords))
                view.updateBoxColor(secondBoxCoords, model.getBoxColor(secondBoxCoords))
            }
            else
            {
                view.showInvalidMoveToast()
            }

            // Se reinician los valores
            firstBoxCoords.set(-1, -1)
            secondBoxCoords.set(-1, -1)
        }
    }
}