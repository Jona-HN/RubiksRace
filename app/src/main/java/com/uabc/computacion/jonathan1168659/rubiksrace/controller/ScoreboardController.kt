package com.uabc.computacion.jonathan1168659.rubiksrace.controller

import android.widget.TableRow
import android.widget.TextView
import com.uabc.computacion.jonathan1168659.rubiksrace.data.ScoreboardEntry
import com.uabc.computacion.jonathan1168659.rubiksrace.model.ScoreboardModel
import com.uabc.computacion.jonathan1168659.rubiksrace.view.ScoreboardActivity

/**
 * Controlador del scoreboard
 */
class ScoreboardController(val view : ScoreboardActivity)
{
    private val model = ScoreboardModel()

    /**
     * Por un lado, le indica al modelo que
     * guarde la nueva entrada. Por otro,
     * llama al método encargado de transformar
     * los datos de la entrada a un formato
     * comprensible para la vista
     */
    fun onNewEntry(entry : ScoreboardEntry)
    {
        model.addEntry(entry)
        generateScoreboardEntry(entry)
    }

    /**
     * Genera una nueva entrada para
     * el scoreboard y la añade a la
     * tabla de la vista
     */
    private fun generateScoreboardEntry(scoreboardEntry: ScoreboardEntry)
    {
        // Se crea una nueva fila
        val entry = TableRow(view)
        entry.layoutParams = view.rowParams

        // Creación de los componentes
        val gameNumberCell = TextView(view)
        val timeCell = TextView(view)
        val movesCell = TextView(view)
//        val combinationCell : /* por definir */

        // Asignación de valores
        gameNumberCell.text = scoreboardEntry.gameNumber.toString()
        timeCell.text = scoreboardEntry.time.toString()
        movesCell.text = scoreboardEntry.moves.toString()
//        combinationCell =  /* magia pokemón */;

        // Se agregan los componentes a la fila
        entry.addView(gameNumberCell)
        entry.addView(timeCell)
        entry.addView(movesCell)
//        entry.addView(combinationCell)

        // Se agrega la fila a la tabla
        view.scoreboardTableLayout.addView(entry, view.rowParams)
    }
}