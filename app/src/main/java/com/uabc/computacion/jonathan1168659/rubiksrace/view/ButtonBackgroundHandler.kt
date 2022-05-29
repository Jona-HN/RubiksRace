package com.uabc.computacion.jonathan1168659.rubiksrace.view

import android.content.res.Resources
import android.widget.ImageButton
import com.uabc.computacion.jonathan1168659.rubiksrace.R
import com.uabc.computacion.jonathan1168659.rubiksrace.data.*
import com.uabc.computacion.jonathan1168659.rubiksrace.user.settings.ColorMode

class ButtonBackgroundHandler
{
    companion object
    {
        fun changeBackgroundColor(resources: Resources, colorMode: String, button: ImageButton, color: Int)
        {
            if (color == 0)
            {
                return
            }

            when (colorMode)
            {
                ColorMode.SHINY.key ->
                {
                    button.setBackgroundColor(resources.getColor(color, null))
                }
                ColorMode.PASTEL.key ->
                {
                    val equivalentColor = DiceColor.equivalentColor(color)
                    button.setBackgroundColor(resources.getColor(equivalentColor, null))
                }
                ColorMode.COLORBLIND.key ->
                {
                    val figure = Figure.getResourceFile(color)
                    button.setBackgroundResource(figure)
                }
            }

            button.setImageResource(R.drawable.button_selector)
        }
    }
}