package com.uabc.computacion.jonathan1168659.rubiksrace.data

import android.graphics.Color.*
import com.uabc.computacion.jonathan1168659.rubiksrace.R.drawable.*

class Figure
{
    companion object
    {
        private val ORANGE = rgb(240, 112, 14)
        private val BLUE = rgb(19, 115, 212)

        private val colorShapeRelations : HashMap<Int, Int> =
            hashMapOf(
                RED to square,
                GREEN to circle,
                YELLOW to lightning,
                ORANGE to star,
                WHITE to gear,
                BLUE to diamond
            )

        fun getResourceFile(color : Int) : Int
        {
            return colorShapeRelations.getOrDefault(color, 0)
        }
    }
}