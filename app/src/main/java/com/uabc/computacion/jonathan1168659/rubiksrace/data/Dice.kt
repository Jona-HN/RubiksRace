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
    val FACES = 6

    /**
     * Tira el dado y regresa la cara que salió
     */
    fun roll() : String
    {
        val face = colors[Random.nextInt(0, (FACES - 1))]
        topFace = face.color
        return topFace
    }
}