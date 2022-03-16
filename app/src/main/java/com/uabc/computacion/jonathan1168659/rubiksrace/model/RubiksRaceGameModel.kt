package com.uabc.computacion.jonathan1168659.rubiksrace.model

import com.uabc.computacion.jonathan1168659.rubiksrace.controller.RubiksRaceGameController

class RubiksRaceGameModel(controller : RubiksRaceGameController)
{
	private val scrambler = ScramblerModel()
	var playerWon = false
		private set

	/**
	 * Verifica si el jugador ganó, en dado caso,
	 * actualiza la bandera correspondiente
	 */
	fun verifyIfPlayerWon(playersCombination : IntArray)
	{
		playerWon = scrambler.verifyCombination(playersCombination)
	}

	/**
	 * Genera una nueva combinación
	 * por medio del scrambler
	 */
	fun generateNewCombination()
	{
		scrambler.scramble()
	}

	/**
	 * Regresa la combinación generada
	 */
	fun getCombination() : IntArray
	{
		return scrambler.combination
	}
}