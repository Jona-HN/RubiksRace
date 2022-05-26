package com.uabc.computacion.jonathan1168659.rubiksrace.core

import android.app.Application
import com.uabc.computacion.jonathan1168659.rubiksrace.database.*
import kotlinx.coroutines.*

class ScoreboardApplication : Application()
{
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { ScoreboardRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ScoreboardRepository(database.entryDao()) }
}