package com.uabc.computacion.jonathan1168659.rubiksrace.user.settings

import android.content.Context
import android.util.*
import com.uabc.computacion.jonathan1168659.rubiksrace.R
import java.io.*

class UserSettings
{
    companion object
    {
        private const val SETTINGS_FILE = "rubiksRaceSettings.json"
        private const val COLOR_MODE = "colorMode"
        private const val BACKGROUND_COLOR = "backgroundColor"
        var colorMode = ColorMode.SHINY.key
        var backgroundColor = R.color.white

        fun fileNotExist(context: Context): Boolean
        {
            val path = "${context.filesDir.path}/${SETTINGS_FILE}"
            val settingsFile = File(path)

            return !settingsFile.exists()
        }

        fun createSettingsFile(context: Context)
        {
            val path = "${context.filesDir.path}/${SETTINGS_FILE}"
            val settingsFile = File(path)
            settingsFile.createNewFile()
        }

        fun saveSettings(context: Context)
        {
            val out = context.openFileOutput(SETTINGS_FILE, Context.MODE_PRIVATE)
            val writer = JsonWriter(OutputStreamWriter(out, "UTF-8"))
            try
            {
                writer.setIndent("  ")
                writer.beginObject()
                writer.name(COLOR_MODE).value(colorMode)
                writer.name(BACKGROUND_COLOR).value(backgroundColor)
                writer.endObject()
                Log.i("settings", "Settings guardados")
                Log.i("settings", "Modo de color = ${this.colorMode}")
            }
            catch (e: IOException)
            {
                e.printStackTrace()
                Log.i("settings", "Hubo un error salvando los settings")
            }
            finally
            {
                writer.close()
            }
        }

        fun loadSettings(context: Context)
        {
            val `in` = context.openFileInput(SETTINGS_FILE)
            val reader = JsonReader(InputStreamReader(`in`, "UTF-8"))
            try
            {
                reader.beginObject()
                while (reader.hasNext())
                {
                    val name = reader.nextName()

                    when
                    {
                        name.equals(COLOR_MODE)       -> colorMode = reader.nextString()
                        name.equals(BACKGROUND_COLOR) -> backgroundColor = reader.nextInt()
                        else                          -> reader.skipValue()
                    }
                }
                reader.endObject()
                Log.i("settings", "Settings cargados")
                Log.i("settings", "Modo de color = $colorMode")
            }
            catch (e: IOException)
            {
                e.printStackTrace()
                Log.i("settings", "Hubo un error cargando los settings")
            }
            finally
            {
                reader.close()
            }
        }
    }
}