package com.uabc.computacion.jonathan1168659.rubiksrace.data

import com.uabc.computacion.jonathan1168659.rubiksrace.R.drawable.*
import com.uabc.computacion.jonathan1168659.rubiksrace.data.DiceColor.*

class Figure
{
    companion object
    {
        private val colorShapeRelations : HashMap<Int, Int> =
            hashMapOf(
                RED.shiny_color to heart,
                GREEN.shiny_color to pacman,
                YELLOW.shiny_color to lightning,
                ORANGE.shiny_color to star,
                WHITE.shiny_color to gear,
                BLUE.shiny_color to diamond
            )

        fun getResourceFile(color : Int) : Int
        {
            return colorShapeRelations.getOrDefault(color, 0)
        }
    }
}