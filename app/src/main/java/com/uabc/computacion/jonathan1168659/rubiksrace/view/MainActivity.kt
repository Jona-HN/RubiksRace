package com.uabc.computacion.jonathan1168659.rubiksrace.view

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import com.uabc.computacion.jonathan1168659.rubiksrace.R
import com.uabc.computacion.jonathan1168659.rubiksrace.controller.PlayersGridController

class MainActivity : AppCompatActivity()
{
    // Número de filas y columnas
    private val NO_OF_ROWS_N_COLS = 5
    private lateinit var gridOfButtons : Array<Array<Int>>

    // Controladores
    private val playersGridController = PlayersGridController(this)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        // Forza el fondo claro para que la casilla negra se note
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeGridOfButtons()

        val newGridButton = findViewById<Button>(R.id.buttonNewGrid)
        newGridButton.setOnClickListener{
            updateGrid(it)
        }
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
    fun updateGrid(view : View)
    {
        playersGridController.generateNewGrid()
        val playersGrid = playersGridController.getPlayersGrid()
        /* testing */
        println("Generando un nuevo grid")
        /* testing */
        var b : Button
        var nextColor : Int
        for (row in 0 until NO_OF_ROWS_N_COLS)
        {
            for (col in 0 until NO_OF_ROWS_N_COLS)
            {
                b = findViewById(gridOfButtons[row][col])
                nextColor = playersGrid[row][col]
                b.setBackgroundColor(nextColor)
            }
        }
    }
}