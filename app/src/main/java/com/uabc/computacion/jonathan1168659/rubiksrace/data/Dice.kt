package com.uabc.computacion.jonathan1168659.rubiksrace.data

import kotlin.random.Random

/**
 * Clase que representa un dado de colores
 */
class Dice
{
    lateinit var topFace : DiceColor
        private set

    /**
     * Tira el dado y regresa la cara que salió
     */
    fun roll(): DiceColor
    {
        topFace = colors[randomNumber()]
        return topFace
    }

    companion object
    {
        private val random = Random(System.currentTimeMillis())
        val colors = DiceColor.values()

        fun randomNumber() = random.nextInt(colors.size)
    }
}