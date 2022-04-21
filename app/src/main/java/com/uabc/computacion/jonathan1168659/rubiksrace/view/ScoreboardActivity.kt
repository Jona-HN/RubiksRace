package com.uabc.computacion.jonathan1168659.rubiksrace.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uabc.computacion.jonathan1168659.rubiksrace.controller.ScoreboardController
import com.uabc.computacion.jonathan1168659.rubiksrace.data.ScoreboardEntry
import com.uabc.computacion.jonathan1168659.rubiksrace.databinding.ActivityScoreboardBinding
import com.uabc.computacion.jonathan1168659.rubiksrace.view.recyclerview.RecyclerAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ScoreboardActivity : AppCompatActivity()
{
    // Controlador
    private val controller = ScoreboardController(this)
    // Binding
    private lateinit var binding : ActivityScoreboardBinding
    // Recycler view y su adaptador
    lateinit var recyclerView : RecyclerView
    lateinit var adapter : RecyclerAdapter
    // Bandera del modo daltónico
    private var colorBlindMode = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        colorBlindMode = intent.getBooleanExtra("colorBlindMode", false)

        // Se recibe el nuevo registro del scoreboard
        val scoreboardEntryJson = intent.getStringExtra("newEntry").toString()
        val scoreboardEntry = Json.decodeFromString<ScoreboardEntry>(scoreboardEntryJson)
        controller.onNewEntry(scoreboardEntry)
    }

    /**
     * Recibe el set actualizado de
     * información para actualizar el
     * RecyclerView
     */
    fun updateRecyclerViewData(entries : ArrayList<ScoreboardEntry>)
    {
        adapter = RecyclerAdapter(entries, colorBlindMode)
        recyclerView.adapter = adapter
    }
}