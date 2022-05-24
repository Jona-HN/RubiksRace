package com.uabc.computacion.jonathan1168659.rubiksrace.database

import androidx.room.TypeConverter
import com.uabc.computacion.jonathan1168659.rubiksrace.data.Combination

class CombinationConverter
{
    @TypeConverter
    fun combinationToString(c: Combination): String
    {
        val stringBuilder = StringBuilder()

        c.elements.forEach { element ->
            stringBuilder.append(element).append(",")
        }

        return stringBuilder.toString()
    }

    @TypeConverter
    fun stringToCombination(elements: String): Combination
    {
        val elementsList = elements.split("\\s*,\\s*")
        val colors = IntArray(elementsList.size)

        elementsList.indices.forEach { colors[it] = elementsList[it].toInt() }

        return Combination(colors)
    }
}