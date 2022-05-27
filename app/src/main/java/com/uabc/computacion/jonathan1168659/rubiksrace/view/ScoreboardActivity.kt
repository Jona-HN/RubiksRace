package com.uabc.computacion.jonathan1168659.rubiksrace.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.uabc.computacion.jonathan1168659.rubiksrace.core.ScoreboardApplication
import com.uabc.computacion.jonathan1168659.rubiksrace.database.*
import com.uabc.computacion.jonathan1168659.rubiksrace.databinding.ActivityScoreboardBinding
import com.uabc.computacion.jonathan1168659.rubiksrace.view.recyclerview.RecyclerAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ScoreboardActivity : AppCompatActivity()
{
    // Controlador
//    private val controller = ScoreboardController(this)
    // Binding
    private lateinit var bind : ActivityScoreboardBinding
    // ViewModel
    private val scoreboardViewModel: ScoreboardViewModel by viewModels {
        WordViewModelFactory((application as ScoreboardApplication).repository)
    }
    // Adapter del RecyclerView
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        bind = ActivityScoreboardBinding.inflate(layoutInflater)
        setContentView(bind.root)

        adapter = RecyclerAdapter()
        adapter.view = this
        bind.recyclerView.adapter = adapter
        bind.recyclerView.layoutManager = LinearLayoutManager(this)

        // Se recibe el nuevo registro del scoreboard
        val scoreboardEntryJson = intent.getStringExtra("newEntry").toString()
        val scoreboardEntry = Json.decodeFromString<ScoreboardEntry>(scoreboardEntryJson)

        scoreboardViewModel.insert(scoreboardEntry)
        scoreboardViewModel.allEntries.observe(this) { entries ->
            entries?.let { adapter.submitList(it) }
        }
    }

    fun removeItem(position: Int)
    {
        scoreboardViewModel.delete(position)
    }
}