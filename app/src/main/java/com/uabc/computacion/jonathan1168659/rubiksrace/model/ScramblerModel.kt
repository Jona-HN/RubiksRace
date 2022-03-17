package com.uabc.computacion.jonathan1168659.rubiksrace.model

import com.uabc.computacion.jonathan1168659.rubiksrace.data.Dice
import com.uabc.computacion.jonathan1168659.rubiksrace.data.DiceColor

/**
 * Clase que representa un scrambler del juego Rubik's Race
 */
class ScramblerModel
{
	private var NO_OF_DICES = 9;
	private val dices = Array(NO_OF_DICES) { Dice() }
	val combination = IntArray(NO_OF_DICES)

	/**
	 * Genera una nueva combinación con la condición
	 * de que cada color no puede repetirse más
	 * de 4 veces
	 */
	fun scramble()
	{
		val colors = DiceColor.values()
		val repetitions = IntArray(colors.size)
		var topFace : DiceColor
		var nextColorReachedMaxRepetitions : Boolean
		var nextColorIndex : Int

		for ((index, dice) in dices.withIndex())
		{
			do
			{
				topFace = dice.roll()
				nextColorIndex = colors.indexOf(topFace)
				nextColorReachedMaxRepetitions = repetitions[nextColorIndex] > 3
			} while (nextColorReachedMaxRepetitions)

			repetitions[nextColorIndex]++
			combination[index] = topFace.color
		}
	}
}