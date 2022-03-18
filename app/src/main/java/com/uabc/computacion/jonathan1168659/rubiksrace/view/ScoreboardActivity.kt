package com.uabc.computacion.jonathan1168659.rubiksrace.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import com.uabc.computacion.jonathan1168659.rubiksrace.R
import com.uabc.computacion.jonathan1168659.rubiksrace.controller.ScoreboardController
import com.uabc.computacion.jonathan1168659.rubiksrace.data.ScoreboardEntry
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ScoreboardActivity : AppCompatActivity()
{
    // Controlador
    private val controller = ScoreboardController(this)
    // Referencia a la tabla en la vista
    lateinit var scoreboardTableLayout : TableLayout
    // Parámetros para las nuevas entradas de la tabla
    lateinit var rowParams : TableRow.LayoutParams

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        scoreboardTableLayout = findViewById(R.id.scoreboardTable)
        // TODO: comprobar que sirve
        rowParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)

        // Se recibe el nuevo registro del scoreboard
        val scoreboardEntryJson = intent.getStringExtra("newEntry").toString()
        val scoreboardEntry = Json.decodeFromString<ScoreboardEntry>(scoreboardEntryJson)
        controller.onNewEntry(scoreboardEntry)
    }
}