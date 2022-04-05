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

        holder.render(entry)
    }

    override fun getItemCount() = scoreboardEntries.size

    inner class EntryHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        private val binding = ScoreboardEntryRowBinding.bind(view)

        val combination = arrayOf(
            binding.buttonCombination1,
            binding.buttonCombination2,
            binding.buttonCombination3,
            binding.buttonCombination4,
            binding.buttonCombination5,
            binding.buttonCombination6,
            binding.buttonCombination7,
            binding.buttonCombination8,
            binding.buttonCombination9
        )

        init
        {
            binding.buttonDelete.setOnClickListener {
                removeAt(adapterPosition)
            }
        }

        fun render(entry: ScoreboardEntry)
        {
            binding.textViewGame.text = entry.gameNumber.toString()
            binding.textViewTime.text = entry.time.toString()
            binding.textViewMoves.text = entry.moves.toString()

            for ((index, button) in combination.withIndex())
            {
                button.setBackgroundColor(entry.combination[index])
            }
        }

        private fun removeAt(position : Int)
        {
            scoreboardEntries.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }
    }
}