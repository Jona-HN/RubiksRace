package com.uabc.computacion.jonathan1168659.rubiksrace.view

import android.content.Intent
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.uabc.computacion.jonathan1168659.rubiksrace.R
import com.uabc.computacion.jonathan1168659.rubiksrace.controller.PlayersGridController
import com.uabc.computacion.jonathan1168659.rubiksrace.controller.RubiksRaceGameController
// Serialization API
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class MainActivity : AppCompatActivity()
{
    private lateinit var playersGridButtons : Array<Array<Int>>
    private lateinit var scramblerButtons : Array<Int>

    // Controladores
    private val playersGridController = PlayersGridController(this)
    private val rubiksRaceGameController = RubiksRaceGameController(this, playersGridController)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        // Forza el fondo claro para que la casilla negra se note
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeGrids()

        val checkButton = findViewById<Button>(R.id.buttonCheckCombination)
        checkButton.setOnClickListener{
            checkButton.isEnabled = !checkPlayerCombination()
        }

        val newGridButton = findViewById<Button>(R.id.buttonStart)
        newGridButton.setOnClickListener{
            updateGrids(it)
            newGridButton.text = "Restart"
            rubiksRaceGameController.startTimer()
            checkButton.isEnabled = true
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
        val clickedBoxCoordsList = view.tag.toString().split(" ")
        val x = Integer.valueOf(clickedBoxCoordsList[0])
        val y = Integer.valueOf(clickedBoxCoordsList[1])
        val clickedBoxCoords = Point(x, y)

        playersGridController.onBoxClick(clickedBoxCoords)
    }

    /**
     * Manda a llamar a los métodos encargados
     * de actualizar sus vistas correspondientes
     */
    private fun updateGrids(view : View)
    {
        playersGridController.updatePlayersGridView()
        rubiksRaceGameController.updateScramblerGridView()
    }

    /**
     * Muestra un Toast con un mensaje para
     * indicarle el resultado de una acción al usuario
     */
    fun showMessage(mensaje : String)
    {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    /**
     * Actualiza el color de una casilla
     * del grid del jugador
     */
    fun updateColorOfPlayersGridBox(boxCoords : Point, color : Int)
    {
        val button = findViewById<Button>(playersGridButtons[boxCoords.x][boxCoords.y])
        button.setBackgroundColor(color)
    }
    /**
     * Actualiza el color de la casilla
     * del grid del scrambler
     */
    fun updateColorOfScramblerBox(index : Int, color : Int)
    {
        val button = findViewById<Button>(scramblerButtons[index])
        button.setBackgroundColor(color)
    }

    /**
     * Le indica al controlador que verifique
     * la combinación del jugador.
     * Si el jugador ganó, lo manda a la
     * vista del scoreboard
     * (es decir, inicia ScoreboardActivity y
     * le manda un scoreboardEntry en formato JSON,
     * todo esto por medio de un Intent)
     */
    private fun checkPlayerCombination() : Boolean
    {
        if (rubiksRaceGameController.checkIfPlayerWon())
        {
            val intentScoreboard = Intent(this, ScoreboardActivity::class.java)
            val scoreboardEntryJson = Json.encodeToString(rubiksRaceGameController.lastScoreboardEntry)

            intentScoreboard.putExtra("newEntry", scoreboardEntryJson)
            startActivity(intentScoreboard)
            return true
        }

        return false
    }

    /* Para testear el bitmap */
    fun bitmapTesterButtonOnClick(view : View)
    {
        val intentScoreboard = Intent(this, ScoreboardActivity::class.java)
        // Se genera un entry custom
        val scoreboardEntryJson = Json.encodeToString(rubiksRaceGameController.scoreboardEntryTest())

        intentScoreboard.putExtra("entryTest", scoreboardEntryJson)
        startActivity(intentScoreboard)
    }
}