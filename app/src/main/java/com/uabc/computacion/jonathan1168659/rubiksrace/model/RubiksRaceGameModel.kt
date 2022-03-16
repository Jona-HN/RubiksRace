package com.uabc.computacion.jonathan1168659.rubiksrace.model

import kotlin.properties.Delegates

class RubiksRaceGameModel
{
	private val scrambler = ScramblerModel()
	var playerWon = false
		private set
	var totalTime by Delegates.notNull<Long>()
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

	/**
	 * Toma el tiempo en el que
	 * empieza el temporizador
	 */
	fun startTimer()
	{
		totalTime = System.currentTimeMillis()
	}

	/**
	 * Toma el tiempo en el que parar
	 * el temporizador, y calcula la
	 * diferencia con el tiempo inicial
	 */
	fun stopTimer()
	{
		val currentTime = System.currentTimeMillis()
		totalTime = (currentTime - totalTime) / 1000
	}
}