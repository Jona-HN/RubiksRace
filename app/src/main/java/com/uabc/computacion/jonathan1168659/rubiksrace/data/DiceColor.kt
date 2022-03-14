package com.uabc.computacion.jonathan1168659.rubiksrace.data

import android.graphics.Color

/**
 * Enumerador que contiene los colores
 * permitidos dentro del juego
 */
enum class DiceColor(val color : Int)
{
    RED(Color.RED),
    GREEN(Color.GREEN),
    YELLOW(Color.YELLOW),
    ORANGE(Color.rgb(240, 112, 14)),
    WHITE(Color.WHITE),
    // Color.BLUE no funciona en mi teléfono
    // por alguna extraña razón
    BLUE(Color.rgb(19, 115, 212))
}