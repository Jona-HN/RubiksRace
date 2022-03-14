package com.uabc.computacion.jonathan1168659.rubiksrace.model

import android.graphics.Color
import com.uabc.computacion.jonathan1168659.rubiksrace.data.DiceColor
import android.graphics.Point
import com.uabc.computacion.jonathan1168659.rubiksrace.controller.PlayersGridController
import kotlin.random.Random

/**
 * Clase que representa el grid del jugador (su campo de juego)
 */
class PlayersGridModel(controller : PlayersGridController)
{
    // Número de filas y columnas
    private val NO_OF_ROWS_N_COLS = 5
    var grid = Array (5) { IntArray(NO_OF_ROWS_N_COLS) }
        private set
    private lateinit var blackBoxIndices : Point

//    init
//    {
//        generateNewGrid()
//    }

    /**
     * Genera un nuevo grid de colores de forma aleatoria
     * considerando las siguientes condiciones:
     * 1. Los colores del dado no pueden repetirse más de 4 veces
     * 2. Debe haber una casilla vacía (indicada con el color negro),
     *    la cual aparece en una posición aleatoria
     */
    fun generateNewGrid()
    {
        val colors = DiceColor.values()
        val repetitions = IntArray(colors.size)
        var nextColor : Int
        var nextColorIndex : Int
        var nextColorReachedMaxRepetitions : Boolean
        var blackBoxAppeared = false

        for (row in 0 until NO_OF_ROWS_N_COLS)
        {
            for (col in 0 until NO_OF_ROWS_N_COLS)
            {
                if (!blackBoxAppeared && Random.nextInt(1, 26) == 1)
                {
                    blackBoxIndices = Point(row, col)
                    nextColor = Color.BLACK
                    blackBoxAppeared = true
                }
                else
                {
                    do
                    {
                        nextColorIndex = Random.nextInt(colors.size)
                        nextColorReachedMaxRepetitions = repetitions[nextColorIndex] > 3
                    } while (nextColorReachedMaxRepetitions)

                    repetitions[nextColorIndex]++
                    nextColor = colors[nextColorIndex].color
                }

                grid[row][col] = nextColor
            }
        }
    }

    /**
     * Mueve la casilla indicada
     */
    fun moveGridBox(firstBoxIndices : Point, secondBoxIndices : Point) : Boolean
    {
        // Se comprueba que primero se haya seleccionado
        // la casilla que se quiere mover. Y que la segunda casilla
        // seleccionada sea la casilla negra
        if (firstBoxIndices == blackBoxIndices || secondBoxIndices != blackBoxIndices)
        {
            return false
        }
        // Se comprueba que la casilla seleccionada sea adyacente
        // a la casilla negra
        else if (!boxIsAdjacentToBlackBox(firstBoxIndices))
        {
            return false
        }
        // Una vez validado el movimiento, se realiza
        else
        {
            val firstBoxIndexColor = grid[firstBoxIndices.x][firstBoxIndices.y]
            grid[secondBoxIndices.x][secondBoxIndices.y] = firstBoxIndexColor
            grid[firstBoxIndices.x][firstBoxIndices.y] = Color.BLACK
            blackBoxIndices = firstBoxIndices

            return true
        }
    }

    /**
     * Verifica si la casilla que se quiere mover
     * es adyacente a la casilla negra
     */
    private fun boxIsAdjacentToBlackBox(boxIndices : Point) : Boolean
    {
        val adjacentBoxesIndices = ArrayList<Point>(4);

        // Casilla superior
        val upperRow : Int = blackBoxIndices.x - 1
        if (upperRow >= 0)
        {
            adjacentBoxesIndices.add(Point(upperRow, blackBoxIndices.y))
        }
        // Casilla inferior
        val lowerRow = blackBoxIndices.x + 1
        if (lowerRow <= NO_OF_ROWS_N_COLS - 1)
        {
            adjacentBoxesIndices.add(Point(lowerRow, blackBoxIndices.y))
        }
        // Casilla a la izquierda
        val leftCol = blackBoxIndices.y - 1
        if (leftCol >= 0)
        {
            adjacentBoxesIndices.add(Point(blackBoxIndices.x, leftCol))
        }
        // Casilla a la derecha
        val rightCol = blackBoxIndices.y + 1
        if (rightCol <= NO_OF_ROWS_N_COLS - 1)
        {
            adjacentBoxesIndices.add(Point(blackBoxIndices.x, rightCol))
        }

        return adjacentBoxesIndices.contains(boxIndices)
    }

    /**
     * Genera la combinación del usuario
     * (la cuadrícula de 3x3 del centro)
     * en forma de un arreglo de Int
     */
    fun getCombination() : IntArray
    {
        val combination = IntArray(9)
        var i = 0

        for (row in 1 until 4)
        {
            for (col in 1 until 4)
            {
                combination[i++] = grid[row][col];
            }
        }

        return combination
    }
}