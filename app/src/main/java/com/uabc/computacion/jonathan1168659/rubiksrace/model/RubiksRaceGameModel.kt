package com.uabc.computacion.jonathan1168659.rubiksrace.model

class RubiksRaceGameModel
{
	private val scrambler = ScramblerModel()
	var playerWon = false
		private set

	/**
	 * Verifica si el jugador ganó (es decir, que la
	 * combinación generada concuerda con la del
	 * scrambler), en dado caso, actualiza la bandera correspondiente
	 */
	fun verifyIfPlayerWon(playersCombination : IntArray)
	{
		playerWon = playersCombination.contentEquals(scrambler.combination)
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