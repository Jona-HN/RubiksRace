package com.uabc.computacion.jonathan1168659.rubiksrace.view

import android.widget.ImageButton
import com.uabc.computacion.jonathan1168659.rubiksrace.R
import com.uabc.computacion.jonathan1168659.rubiksrace.data.Figure

class ButtonBackgroundHandler
{
    companion object
    {
        fun changeBackgroundColor(button : ImageButton, color : Int)
        {
            button.setBackgroundColor(color)
            button.setImageResource(R.drawable.black_border)
        }

        fun changeImage(button : ImageButton, color : Int)
        {
            val figure = Figure.getResourceFile(color)
            button.setBackgroundResource(figure)
        }
    }
}