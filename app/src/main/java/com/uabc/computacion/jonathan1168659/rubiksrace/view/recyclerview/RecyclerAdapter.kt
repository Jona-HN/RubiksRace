package com.uabc.computacion.jonathan1168659.rubiksrace.view.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uabc.computacion.jonathan1168659.rubiksrace.R
import com.uabc.computacion.jonathan1168659.rubiksrace.data.ScoreboardEntry
import com.uabc.computacion.jonathan1168659.rubiksrace.databinding.ScoreboardEntryRowBinding

class RecyclerAdapter(private val scoreboardEntries : ArrayList<ScoreboardEntry>) : RecyclerView.Adapter<RecyclerAdapter.EntryHolder>()
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EntryHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.scoreboard_entry_row, parent, false)

        return EntryHolder(view)
    }

    override fun onBindViewHolder(holder : EntryHolder, position : Int)
    {
        val entry = scoreboardEntries[position]

        holder.game.text = entry.gameNumber.toString()
        holder.time.text = entry.time.toString()
        holder.moves.text = entry.moves.toString()
    }

    override fun getItemCount() = scoreboardEntries.size

    inner class EntryHolder(v : View) : RecyclerView.ViewHolder(v)
    {
        private val binding = ScoreboardEntryRowBinding.inflate(LayoutInflater.from(v.context))
        val game = binding.textViewGame
        val time = binding.textViewTime
        val moves = binding.textViewMoves
        val combination = binding.takeIf { itemId.toString().contains("Combination") }
        private val delete = binding.buttonDelete

        init
        {
            delete.setOnClickListener {
                removeAt(adapterPosition)
            }
        }

        private fun removeAt(position : Int)
        {
            scoreboardEntries.removeAt(position)
        }
    }
}