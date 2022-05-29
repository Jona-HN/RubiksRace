package com.uabc.computacion.jonathan1168659.rubiksrace.view.dialog

import android.app.*
import android.os.Bundle
import android.text.Html
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
            .setMessage(Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT))
            .setPositiveButton("OK") { _, _-> }

        return builder.create()
    }
}