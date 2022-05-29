package com.uabc.computacion.jonathan1168659.rubiksrace.model

import com.uabc.computacion.jonathan1168659.rubiksrace.data.Dice

/**
 * Clase que representa un scrambler del juego Rubik's Race
 */
class ScramblerModel
{
	private var NO_OF_DICES = 9
	private val dices = Array(NO_OF_DICES) { Dice() }
	val combination = IntArray(NO_OF_DICES)

	/**
	 * Genera una nueva combinación con la condición
	 * de que cada color no puede repetirse más
	 * de 4 veces
	 */
	suspend fun scramble()
	{
		val repetitions = IntArray(Dice.colors.size) { 0 }
		var nextColorReachedMaxRepetitions : Boolean
		var indexOfColor : Int

		for ((index, dice) in dices.withIndex())
		{
			do
			{
				dice.roll()
				indexOfColor = Dice.colors.indexOf(dice.topFace)
				nextColorReachedMaxRepetitions = repetitions[indexOfColor] > 3
			} while (nextColorReachedMaxRepetitions)

			repetitions[indexOfColor]++
			combination[index] = dice.topFace.shiny_color
		}
	}
}