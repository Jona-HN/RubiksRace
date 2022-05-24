package com.uabc.computacion.jonathan1168659.rubiksrace.data

import kotlinx.serialization.Serializable

@Serializable
data class Combination(val elements : IntArray)
{
    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Combination

        if (!elements.contentEquals(other.elements)) return false

        return true
    }

    override fun hashCode(): Int
    {
        return elements.contentHashCode()
    }
}
