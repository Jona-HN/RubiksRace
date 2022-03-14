package com.uabc.computacion.jonathan1168659.rubiksrace.view

import android.graphics.Color
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.uabc.computacion.jonathan1168659.rubiksrace.R
import com.uabc.computacion.jonathan1168659.rubiksrace.controller.PlayersGridController

class MainActivity : AppCompatActivity()
{
    private lateinit var gridOfButtons : Array<Array<Int>>

    // Controladores
    private val playersGridController = PlayersGridController(this)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Grid inicial
        initializeGridOfButtons()
        updateGrid(null)
    }

    /**
     * Inicializa la matriz de casillas (representadas por
     * botones en la vista)
     */
    private fun initializeGridOfButtons()
    {
        gridOfButtons = arrayOf(
            arrayOf(R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5),
            arrayOf(R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10),
            arrayOf(R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15),
            arrayOf(R.id.button16, R.id.button17, R.id.button18, R.id.button19, R.id.button20),
            arrayOf(R.id.button21, R.id.button22, R.id.button23, R.id.button24, R.id.button25)
        )
    }

    /**
     * Método al cuál llaman todas las casillas
     * del grid del jugador. Recupera la tag que tiene
     * asignada cada casilla. La tag contiene
     * las coordenadas de la casilla.
     */
    fun onBoxClick(view : View)
    {
        val clickedBoxCordsList = view.tag.toString().split(" ")
        val x = Integer.valueOf(clickedBoxCordsList[0])
        val y = Integer.valueOf(clickedBoxCordsList[1])
        val clickedBoxCords = Point(x, y)

        println(clickedBoxCords.toString())
    }

    /**
     * Actualiza la vista del
     * grid del jugador
     */
    fun updateGrid(view : View?)
    {
        playersGridController.generateNewGrid()
        val playersGrid = playersGridController.getPlayersGrid()

        var b : Button
        for (row in 0 until 5)
        {
            for (col in 0 until 5)
            {
                b = findViewById(gridOfButtons[row][col])
                /* testing */
                b.setBackgroundColor(Color.RED)
                /* testing */
            }
        }
    }
}