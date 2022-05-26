package com.uabc.computacion.jonathan1168659.rubiksrace.user.settings

import android.content.Context
import android.util.*
import java.io.*

class UserSettings
{
    companion object
    {
        private const val SETTINGS_FILE = "rubiksRaceSettings.json"
        private const val COLOR_BLIND_MODE = "colorBlindMode"
        var colorBlindMode = false
        private set

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

        fun saveSettings(context: Context, colorBlindMode: Boolean)
        {
            val out = context.openFileOutput(SETTINGS_FILE, Context.MODE_PRIVATE)
            val writer = JsonWriter(OutputStreamWriter(out, "UTF-8"))
            try
            {
                writer.setIndent("  ")
                writer.beginObject()
                writer.name(COLOR_BLIND_MODE).value(colorBlindMode)
                writer.endObject()
                this.colorBlindMode = colorBlindMode
                Log.i("settings", "Settings guardados")
                Log.i("settings", "Modo daltónico = ${this.colorBlindMode}")
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

                    if (name.equals(COLOR_BLIND_MODE))
                    {
                        colorBlindMode = reader.nextBoolean()
                    }
                    else
                    {
                        reader.skipValue()
                    }
                }
                reader.endObject()
                Log.i("settings", "Settings cargados")
                Log.i("settings", "Modo daltónico = $colorBlindMode")
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