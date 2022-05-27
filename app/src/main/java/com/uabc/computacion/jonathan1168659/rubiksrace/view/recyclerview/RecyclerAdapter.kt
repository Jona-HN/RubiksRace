package com.uabc.computacion.jonathan1168659.rubiksrace.view.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.*
import androidx.recyclerview.widget.DiffUtil.*
import androidx.recyclerview.widget.ListAdapter
import com.uabc.computacion.jonathan1168659.rubiksrace.R
import com.uabc.computacion.jonathan1168659.rubiksrace.database.ScoreboardEntry
import com.uabc.computacion.jonathan1168659.rubiksrace.databinding.ScoreboardEntryRowBinding
import com.uabc.computacion.jonathan1168659.rubiksrace.user.settings.UserSettings
import com.uabc.computacion.jonathan1168659.rubiksrace.view.ButtonBackgroundHandler.Companion.changeBackgroundColor
import com.uabc.computacion.jonathan1168659.rubiksrace.view.ButtonBackgroundHandler.Companion.changeImage
import com.uabc.computacion.jonathan1168659.rubiksrace.view.ScoreboardActivity

class RecyclerAdapter : ListAdapter<ScoreboardEntry, RecyclerAdapter.EntryViewHolder>(EntriesComparator())
{
    lateinit var view: ScoreboardActivity

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EntryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.scoreboard_entry_row, parent, false)

        return EntryViewHolder(view)
    }

    override fun onBindViewHolder(holder : EntryViewHolder, position : Int)
    {
        val current = getItem(position)
        holder.render(current)
    }

    inner class EntryViewHolder(itemView : View) : ViewHolder(itemView)
    {
        private val binding = ScoreboardEntryRowBinding.bind(itemView)

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
                view.removeItem(binding.buttonDelete.tag as Int)
            }
        }

        fun render(entry: ScoreboardEntry)
        {
            binding.textViewGame.text = entry.gameNumber.toString()
            binding.textViewTime.text = entry.time.toString()
            binding.textViewMoves.text = entry.moves.toString()
            binding.buttonDelete.tag = entry.gameNumber
            var nextColor : Int

            for ((index, button) in combination.withIndex())
            {
                nextColor = entry.combination.elements[index]
                changeBackgroundColor(button, nextColor)

                if (UserSettings.colorBlindMode)
                {
                    changeImage(button, nextColor)
                }
            }
        }
    }

    class EntriesComparator : ItemCallback<ScoreboardEntry>() {
        override fun areItemsTheSame(oldItem: ScoreboardEntry, newItem: ScoreboardEntry): Boolean
        {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ScoreboardEntry, newItem: ScoreboardEntry): Boolean
        {
            return oldItem.combination == newItem.combination
        }
    }
}