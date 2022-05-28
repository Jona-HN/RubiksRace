package com.uabc.computacion.jonathan1168659.rubiksrace.view.dialog

import android.app.*
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.uabc.computacion.jonathan1168659.rubiksrace.R

class CreditsDialog : AppCompatDialogFragment()
{
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {
        val factory = LayoutInflater.from(activity)
        val view = factory.inflate(R.layout.credits_dialog, null)

        val builder = AlertDialog.Builder(activity)
            .setPositiveButton("OK") { _, _->
                Toast.makeText(
                    activity,
                    R.string.message_for_player,
                    Toast.LENGTH_LONG).show()
            }
            .setView(view)

        return builder.create()
    }
}