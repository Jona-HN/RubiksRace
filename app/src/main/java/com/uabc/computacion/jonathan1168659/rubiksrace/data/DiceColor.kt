package com.uabc.computacion.jonathan1168659.rubiksrace.data

import com.uabc.computacion.jonathan1168659.rubiksrace.R.color.*

/**
 * Enumerador que contiene los colores
 * permitidos dentro del juego
 */
enum class DiceColor(val shiny_color: Int, val pastel_color: Int)
{
    RED(red, red_pastel),
    GREEN(green, green_pastel),
    YELLOW(yellow, yellow_pastel),
    ORANGE(orange, orange_pastel),
    WHITE(white, white),
    BLUE(blue, blue_pastel);

    companion object
    {
        fun equivalentColor(shinyColor: Int): Int
        {
            val color = values().find { it.shiny_color == shinyColor }

            return when
            {
                color != null       -> color.pastel_color
                shinyColor == black -> black_pastel
                else                -> shinyColor
            }
        }
    }
}