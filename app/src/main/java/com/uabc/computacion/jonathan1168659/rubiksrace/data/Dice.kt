package com.uabc.computacion.jonathan1168659.rubiksrace.data

import kotlin.random.Random

/**
 * Clase que representa un dado de colores
 */
class Dice
{
    private val colors = DiceColor.values()
    var topFace = roll()
        private set

    /**
     * Tira el dado y regresa la cara que salió
     */
    fun roll() : DiceColor
    {
        val face = colors[Random.nextInt(0, (colors.size - 1))]
        topFace = face
        return topFace
    }
}