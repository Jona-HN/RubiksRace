package com.uabc.computacion.jonathan1168659.rubiksrace.model

import com.uabc.computacion.jonathan1168659.rubiksrace.data.Dice

/**
 * Clase que representa un scrambler del juego Rubik's Race
 */
class ScramblerModel
{
	private var NO_OF_DICES = 9;
	private val dices = Array<Dice>(NO_OF_DICES) { Dice() }
	private var combination = arrayOfNulls<String>(NO_OF_DICES)

	/**
	 * Genera una nueva combinación
	 */
	fun scramble()
	{
		for ((index, dice) in dices.withIndex())
		{
			combination[index] = dice.roll()
			/* testing */
			println("Dice no. ${index + 1} is color ${dice.topFace}")
			/* testing */
		}
	}

	/**
	 * Verifica si la combinación del jugador concuerda
	 * con la generada por el scrambler
	 */
	fun verifyCombination(playersCombination : Array<String?>) : Boolean
	{
		/* testing */
		println("Combination received: ")
		for (color in playersCombination)
		{
			println(color)
		}
		/* testing */
		val allDicesMatch = playersCombination.contentEquals(combination);

		return allDicesMatch
	}
}