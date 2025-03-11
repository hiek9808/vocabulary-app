package com.sumaqada.vocabulary.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sumaqada.vocabulary.VocabularyApplication
import com.sumaqada.vocabulary.repository.AuthRepository
import com.sumaqada.vocabulary.repository.WordRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val words: List<WordHomeUI>) : HomeUiState

    data object Error : HomeUiState
}

enum class SyncStatus {
    NO_SYNC, SYNCHRONIZING, SYNCHRONIZED, ERROR
}

data class WordHomeUI(
    val id: Int,
    val word: String,
    val translated: String
)

const val TAG: String = "HomeViewModel"

class HomeViewModel(
    private val wordRepository: WordRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val userData = authRepository.userData
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            null
        )

    private val _syncStatus = MutableStateFlow<SyncStatus>(SyncStatus.NO_SYNC)
    val synStatus: StateFlow<SyncStatus>
        get() = _syncStatus


    val homeUiState: StateFlow<HomeUiState> = wordRepository.getAllWordHomeUI()
        .map { HomeUiState.Success(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            HomeUiState.Loading
        )

    private val _wordsNoSync = wordRepository.getAllNoSynchronized()

    init {

        //sync when remote is implemented
        syncWords()

    }

    private fun syncWords() {
        viewModelScope.launch {
            _wordsNoSync.collect {
                try {
                    Log.d(TAG, "syncWords: ${it.size}")
                    _syncStatus.emit(SyncStatus.SYNCHRONIZING)
                    val sync = async {
                        // sync when remote is implemented
                        //wordRepository.syncWordFromLocalToRemote(it)
                    }
                    delay(1000L)
                    sync.await()
                    _syncStatus.emit(SyncStatus.SYNCHRONIZED)
                } catch (e: Exception) {
                    _syncStatus.emit(SyncStatus.ERROR)
                }

            }
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as VocabularyApplication)
                val wordRepository = application.container.wordRepository
                HomeViewModel(wordRepository, application.container.authRepository)
            }
        }
    }
}
