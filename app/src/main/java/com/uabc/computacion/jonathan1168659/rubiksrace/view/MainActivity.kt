package com.uabc.computacion.jonathan1168659.rubiksrace.view

import android.content.Intent
import android.graphics.Point
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils

import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

import com.uabc.computacion.jonathan1168659.rubiksrace.R
import com.uabc.computacion.jonathan1168659.rubiksrace.controller.PlayersGridController
import com.uabc.computacion.jonathan1168659.rubiksrace.controller.RubiksRaceGameController
import com.uabc.computacion.jonathan1168659.rubiksrace.databinding.ActivityMainBinding
import com.uabc.computacion.jonathan1168659.rubiksrace.user.settings.*
import com.uabc.computacion.jonathan1168659.rubiksrace.view.ButtonBackgroundHandler.Companion.changeBackgroundColor
import com.uabc.computacion.jonathan1168659.rubiksrace.view.dialog.*
// Serialization API
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class MainActivity : AppCompatActivity()
{
    private lateinit var playersGridButtons: Array<Array<ImageButton>>
    private lateinit var scramblerButtons: Array<ImageButton>
    private lateinit var bind: ActivityMainBinding
    private var buttonHasBeenPressed = false
    private lateinit var clickedButton: ImageButton
    private var playedAtLeastOnce = false

    // Controladores
    private val playersGridController = PlayersGridController(this)
    private val rubiksRaceGameController = RubiksRaceGameController(this, playersGridController)

    // Reproductores de sonido
    private lateinit var completeSound : MediaPlayer
    private lateinit var errorSound : MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?)
    {
        // Forza el fondo claro para que la casilla negra se note
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        initializeGrids()
        initializeMediaPlayers()

        val checkButton = bind.buttonCheckCombination
        checkButton.setOnClickListener {
            checkButton.isEnabled = !checkPlayerCombination()
        }

        if (UserSettings.fileNotExist(this))
        {
            UserSettings.createSettingsFile(this)
        }
        else
        {
            UserSettings.loadSettings(this)
            refreshSettingsInView()
        }

        // Men?? contextual del color del fondo
        registerForContextMenu(bind.root)
        // Men?? principal
        setSupportActionBar(bind.toolbar)
    }

    private fun initializeMediaPlayers()
    {
        completeSound = MediaPlayer.create(this, R.raw.hero_simple_celebration_03)
        errorSound = MediaPlayer.create(this, R.raw.alert_error_03)
    }

    /**
     * Refresca (o aplica) las preferencias del usuario
     * almacenadas en la vista
     */
    private fun refreshSettingsInView()
    {
        bind.root.setBackgroundColor(resources.getColor(UserSettings.backgroundColor, null))
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?)
    {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.setHeaderTitle(R.string.background_menu_title)
        menuInflater.inflate(R.menu.background_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean
    {
        val color = when (item.itemId)
        {
            R.id.white_background  -> R.color.white
            R.id.red_background    -> R.color.red
            R.id.purple_background -> R.color.purple_500
            else                   -> 0
        }

        if (color != 0)
        {
            UserSettings.backgroundColor = color
            UserSettings.saveSettings(this)
            refreshSettingsInView()
        }

        return super.onContextItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when (item.itemId)
        {
            R.id.rules         -> openSimpleDialog("Rules", getString(R.string.how_to_play))
            R.id.restart_game  -> startGame()
            R.id.pastel_colors -> changeColors(ColorMode.PASTEL)
            R.id.shiny_colors  -> changeColors(ColorMode.SHINY)
            R.id.colorblind    -> changeColors(ColorMode.COLORBLIND)
            R.id.scoreboard    -> goToScoreboard()
            R.id.credits       -> openCreditsDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun changeColors(mode: ColorMode)
    {
        UserSettings.colorMode = mode.key

        playersGridController.refreshGridInView()
        rubiksRaceGameController.refreshGridInView()

        UserSettings.saveSettings(this)
        refreshSettingsInView()
    }

    private fun startGame()
    {
        updateGrids()
        rubiksRaceGameController.startTimer()
        bind.buttonCheckCombination.isEnabled = true

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

    private fun openSimpleDialog(title: String, message: String)
    {
        val dialog = InfoDialog(title, message)
        dialog.show(supportFragmentManager, "infoDialog")
    }

    private fun openCreditsDialog()
    {
        CreditsDialog().show(supportFragmentManager, "creditsDialog")
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
     * M??todo al cu??l llaman todas las casillas
     * del grid del jugador. Recupera la tag que tiene
     * asignada cada casilla. La tag contiene
     * las coordenadas de la casilla.
     */
    fun onBoxClick(view: View)
    {
        // Para guardar el estado del bot??n presionado
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
     * Manda a llamar a los m??todos encargados
     * de actualizar sus vistas correspondientes
     */
    private fun updateGrids()
    {
        playersGridController.updatePlayersGridView()
        rubiksRaceGameController.updateScramblerGridView()
    }

    /**
     * Muestra un Toast con un mensaje para
     * indicarle el resultado de una acci??n al usuario
     */
    fun showMessage(msg: String, duration: Int)
    {
        Toast.makeText(this, msg, duration).show()
    }

    /**
     * Actualiza el color de una casilla
     * del grid del jugador
     */
    fun updateColorOfPlayersGridBox(boxCoords: Point, color: Int)
    {
        runOnUiThread {
            val colorMode = UserSettings.colorMode
            val button = playersGridButtons[boxCoords.x][boxCoords.y]

            changeBackgroundColor(resources, colorMode, button, color)
        }
    }

    /**
     * Actualiza el color de la casilla
     * del grid del scrambler
     */
    fun updateColorOfScramblerBox(index: Int, color: Int)
    {
        runOnUiThread {
            val colorMode = UserSettings.colorMode
            val button = scramblerButtons[index]

            changeBackgroundColor(resources, colorMode, button, color)
        }
    }

    /**
     * Le indica al controlador que verifique
     * la combinaci??n del jugador.
     * Si el jugador gan??, lo manda a la
     * vista del scoreboard
     * (es decir, inicia ScoreboardActivity y
     * le manda un scoreboardEntry en formato JSON,
     * todo esto por medio de un Intent)
     */
    private fun checkPlayerCombination() : Boolean
    {
        if (rubiksRaceGameController.checkIfPlayerWon())
        {
            completeSound.start()
            val intentScoreboard = Intent(this, ScoreboardActivity::class.java)
            val scoreboardEntryJson = Json.encodeToString(rubiksRaceGameController.lastScoreboardEntry)

            intentScoreboard.putExtra("newEntry", scoreboardEntryJson)
            startActivity(intentScoreboard)
            return true
        }
        else
        {
            errorSound.start()
            bind.playersGrid.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.shake
                )
            )
            return false
        }
    }

    /**
     * M??todo que contiene un intent (vac??o)
     * y dirige hacia la actividad del scoreboard
     */
    private fun goToScoreboard()
    {
        val intentScoreboard = Intent(this, ScoreboardActivity::class.java)
        intentScoreboard.putExtra("newEntry", "")
        startActivity(intentScoreboard)
    }
}