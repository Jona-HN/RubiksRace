package com.uabc.computacion.jonathan1168659.rubiksrace.view

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import com.uabc.computacion.jonathan1168659.rubiksrace.R
import com.uabc.computacion.jonathan1168659.rubiksrace.controller.PlayersGridController
import com.uabc.computacion.jonathan1168659.rubiksrace.controller.RubiksRaceGameController

class MainActivity : AppCompatActivity()
{
    // Número de filas y columnas
    private val NO_OF_ROWS_N_COLS = 5
    private lateinit var playersGridButtons : Array<Array<Int>>
    private lateinit var scramblerButtons : Array<Int>

    // Controladores
    private val playersGridController = PlayersGridController(this)
    private val rubiksRaceGameController = RubiksRaceGameController(this)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        // Forza el fondo claro para que la casilla negra se note
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeGrids()

        val newGridButton = findViewById<Button>(R.id.buttonStart)
        newGridButton.setOnClickListener{
            updateGrids(it)
        }
    }

    /**
     * Inicializa los dos arreglos que
     * contienen los ids de los botones.
     * Uno para el grid del jugador y
     * otro para el scrambler.
     */
    private fun initializeGrids()
    {
        playersGridButtons = arrayOf(
            arrayOf(R.id.buttonGrid1, R.id.buttonGrid2, R.id.buttonGrid3, R.id.buttonGrid4, R.id.buttonGrid5),
            arrayOf(R.id.buttonGrid6, R.id.buttonGrid7, R.id.buttonGrid8, R.id.buttonGrid9, R.id.buttonGrid10),
            arrayOf(R.id.buttonGrid11, R.id.buttonGrid12, R.id.buttonGrid13, R.id.buttonGrid14, R.id.buttonGrid15),
            arrayOf(R.id.buttonGrid16, R.id.buttonGrid17, R.id.buttonGrid18, R.id.buttonGrid19, R.id.buttonGrid20),
            arrayOf(R.id.buttonGrid21, R.id.buttonGrid22, R.id.buttonGrid23, R.id.buttonGrid24, R.id.buttonGrid25)
        )

        scramblerButtons = arrayOf(
            R.id.buttonScrambler1, R.id.buttonScrambler2, R.id.buttonScrambler3,
            R.id.buttonScrambler4, R.id.buttonScrambler5, R.id.buttonScrambler6,
            R.id.buttonScrambler7, R.id.buttonScrambler8, R.id.buttonScrambler9,
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
     * Manda a llamar a los métodos encargados
     * de actualizar sus vistas correspondientes
     */
    fun updateGrids(view : View)
    {
        updatePlayersGridView()
        updateScramblerGridView()
    }

    /**
     * Actualiza la vista del grid
     * del jugador
     */
    private fun updatePlayersGridView()
    {
        playersGridController.generateNewGrid()
        val playersGrid = playersGridController.getPlayersGrid()
        /* testing */
        println("Generando un nuevo grid del jugador")
        /* testing */
        var b : Button
        var nextColor : Int
        for (row in 0 until NO_OF_ROWS_N_COLS)
        {
            for (col in 0 until NO_OF_ROWS_N_COLS)
            {
                b = findViewById(playersGridButtons[row][col])
                nextColor = playersGrid[row][col]
                b.setBackgroundColor(nextColor)
            }
        }
    }

    /**
     * Actualiza la vista del
     * grid del scrambler
     */
    private fun updateScramblerGridView()
    {
        rubiksRaceGameController.generateNewCombination()
        val scramblerGrid = rubiksRaceGameController.getCombination()
        /* testing */
        println("Generando nueva combinación (scramble)")
        /* testing */
        var b : Button

        for ((i, color) in scramblerGrid.withIndex())
        {
            b = findViewById(scramblerButtons[i])
            b.setBackgroundColor(color)
        }
    }
}