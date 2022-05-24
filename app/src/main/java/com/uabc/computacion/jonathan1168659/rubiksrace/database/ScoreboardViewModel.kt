package com.uabc.computacion.jonathan1168659.rubiksrace.database

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ScoreboardViewModel(private val repository: ScoreboardRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allEntries: LiveData<List<ScoreboardEntry>> = repository.allEntries.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(entry: ScoreboardEntry) = viewModelScope.launch {
        repository.insert(entry)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }
}

class WordViewModelFactory(private val repository: ScoreboardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScoreboardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}