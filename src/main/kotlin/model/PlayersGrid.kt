package model

import data.DiceColor
import kotlin.random.Random

/**
 * Clase que representa el grid del jugador (su campo de juego)
 */
class PlayersGrid
{
    private val GRID_SIZE = 25;
    var grid = arrayOfNulls<String>(GRID_SIZE)
        private set
    private var blackBoxindex = 0

    init
    {
        generateNewGrid()
    }

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
        var nextColorIndex : Int
        var colorReachedMaxRepetitions : Boolean
        var blackBoxAppeared = false

        for (i in grid.indices)
        {
            if (!blackBoxAppeared && Random.nextInt(1, 26) == 1)
            {
                blackBoxindex = i
                grid[blackBoxindex] = "Black"
                blackBoxAppeared = true
            }
            else
            {
                do
                {
                    nextColorIndex = Random.nextInt(colors.size)
                    colorReachedMaxRepetitions = repetitions[nextColorIndex] > 3
                } while (colorReachedMaxRepetitions)

                repetitions[nextColorIndex]++
                grid[i] = colors[nextColorIndex].color
            }
        }
    }

    /**
     * Mueve la casilla indicada
     */
    fun moveGridBox(firstBoxIndex : Int, secondBoxIndex : Int) : Boolean
    {
        // Se comprueba que primero se haya seleccionado
        // la casilla que se quiere mover. Y que la segunda casilla
        // seleccionada sea la casilla negra
        if (firstBoxIndex == blackBoxindex || secondBoxIndex != blackBoxindex)
        {
            return false
        }
        // Una vez validado el movimiento, se realiza
        else
        {
            val firstBoxIndexColor = grid[firstBoxIndex]
            grid[secondBoxIndex] = firstBoxIndexColor
            grid[firstBoxIndex] = "Black"
            blackBoxindex = firstBoxIndex

            return true
        }
    }

    /**
     * Devuelve el grid del jugador en
     * forma de String
     */
    fun gridToString() : String
    {
        val result = StringBuilder()

        for ((i, color) in grid.withIndex())
        {
            if ((i + 1) % 5 == 0)
            {
                result.append("$color \n")
            }
            else
            {
                result.append("$color \t")
            }
        }

        return result.toString()
    }
}