package com.uabc.computacion.jonathan1168659.rubiksrace.view

import android.content.Intent
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.uabc.computacion.jonathan1168659.rubiksrace.controller.PlayersGridController
import com.uabc.computacion.jonathan1168659.rubiksrace.controller.RubiksRaceGameController
import com.uabc.computacion.jonathan1168659.rubiksrace.databinding.ActivityMainBinding
import com.uabc.computacion.jonathan1168659.rubiksrace.user.settings.UserSettings
import com.uabc.computacion.jonathan1168659.rubiksrace.view.ButtonBackgroundHandler.Companion.changeBackgroundColor
import com.uabc.computacion.jonathan1168659.rubiksrace.view.ButtonBackgroundHandler.Companion.changeImage
import kotlinx.coroutines.*
// Serialization API
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class MainActivity : AppCompatActivity()
{
    private lateinit var playersGridButtons : Array<Array<ImageButton>>
    private lateinit var scramblerButtons : Array<ImageButton>
    private lateinit var bind : ActivityMainBinding
    private var buttonHasBeenPressed = false
    private lateinit var clickedButton : ImageButton
    private var playedAtLeastOnce = false

    // Controladores
    private val playersGridController = PlayersGridController(this)
    private val rubiksRaceGameController = RubiksRaceGameController(this, playersGridController)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        // Forza el fondo claro para que la casilla negra se note
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        initializeGrids()

        val checkButton = bind.buttonCheckCombination
        checkButton.setOnClickListener{
            checkButton.isEnabled = !checkPlayerCombination()
        }

        val newGridButton = bind.buttonStart
        newGridButton.setOnClickListener{view ->
            runBlocking { launch { updateGrids(view) } }
            newGridButton.text = "Restart"
            rubiksRaceGameController.startTimer()
            checkButton.isEnabled = true

            if (!playedAtLeastOnce)
            {
                playersGridButtons.forEach { row ->
                    row.forEach { btn ->
                        btn.setOnClickListener { onBoxClick(btn) }
                    }
                }
                playedAtLeastOnce = true
            }
        }

        if (UserSettings.fileNotExist(this)) {
            UserSettings.createSettingsFile(this)
        }
        else
        {
            UserSettings.loadSettings(this)
        }
        bind.switchMode.isChecked = UserSettings.colorBlindMode

        bind.switchMode.setOnCheckedChangeListener { _, isChecked ->
            UserSettings.saveSettings(this, isChecked)
            runBlocking {
                launch {
                    playersGridController.refreshGridInView()
                    rubiksRaceGameController.refreshGridInView()
                }
            }
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
            arrayOf(bind.buttonGrid1, bind.buttonGrid2, bind.buttonGrid3, bind.buttonGrid4, bind.buttonGrid5),
            arrayOf(bind.buttonGrid6, bind.buttonGrid7, bind.buttonGrid8, bind.buttonGrid9, bind.buttonGrid10),
            arrayOf(bind.buttonGrid11, bind.buttonGrid12, bind.buttonGrid13, bind.buttonGrid14, bind.buttonGrid15),
            arrayOf(bind.buttonGrid16, bind.buttonGrid17, bind.buttonGrid18, bind.buttonGrid19, bind.buttonGrid20),
            arrayOf(bind.buttonGrid21, bind.buttonGrid22, bind.buttonGrid23, bind.buttonGrid24, bind.buttonGrid25)
        )

        scramblerButtons = arrayOf(
            bind.buttonScrambler1, bind.buttonScrambler2, bind.buttonScrambler3,
            bind.buttonScrambler4, bind.buttonScrambler5, bind.buttonScrambler6,
            bind.buttonScrambler7, bind.buttonScrambler8, bind.buttonScrambler9,
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
        // Para guardar el estado del botón presionado
        if (!buttonHasBeenPressed)
        {
            view.isSelected = true
            clickedButton = view as ImageButton
            buttonHasBeenPressed = true
        }
        else
        {
            clickedButton.isSelected = false
            buttonHasBeenPressed = false
        }

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
    private suspend fun updateGrids(view : View) = withContext(Dispatchers.IO)
    {
        playersGridController.updatePlayersGridView()
        rubiksRaceGameController.updateScramblerGridView()
    }

    /**
     * Muestra un Toast con un mensaje para
     * indicarle el resultado de una acción al usuario
     */
    fun showMessage(msg : String, duration : Int)
    {
        Toast.makeText(this, msg, duration).show()
    }

    /**
     * Actualiza el color de una casilla
     * del grid del jugador
     */
    fun updateColorOfPlayersGridBox(boxCoords : Point, color : Int)
    {
        runOnUiThread {
            val button = playersGridButtons[boxCoords.x][boxCoords.y]
            changeBackgroundColor(button, color)

            if (UserSettings.colorBlindMode)
            {
                changeImage(button, color)
            }
        }
    }

    /**
     * Actualiza el color de la casilla
     * del grid del scrambler
     */
    fun updateColorOfScramblerBox(index : Int, color : Int)
    {
        runOnUiThread {
            val button = scramblerButtons[index]
            changeBackgroundColor(button, color)

            if (UserSettings.colorBlindMode)
            {
                changeImage(button, color)
            }
        }
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
}