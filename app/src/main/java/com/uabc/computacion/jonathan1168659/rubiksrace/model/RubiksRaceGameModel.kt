package com.uabc.computacion.jonathan1168659.rubiksrace.model

class RubiksRaceGameModel
{
	private val scrambler = ScramblerModel()
//	private val playersGrid = PlayersGridModel()
	var playerWon = false
		private set

	/**
	 * Verifica si el jugador ganó, en dado caso,
	 * actualiza la bandera correspondiente
	 */
	fun verifyIfPlayerWon()
	{
//		playerWon = scrambler.verifyCombination(playersGrid.getCombination())
	}

	/**
	 * Genera una nueva combinación
	 * por medio del scrambler
	 */
	fun generateNewGrid()
	{
		scrambler.scramble()
	}
}