package com.uabc.computacion.jonathan1168659.rubiksrace.view.dialog

import android.app.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

class InfoDialog(
    private val title: String,
    private val message: String
) : AppCompatDialogFragment()
{
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { _, _-> }

        return builder.create()
    }
}