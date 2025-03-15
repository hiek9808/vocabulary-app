package com.sumaqada.vocabulary.ui.entry

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sumaqada.vocabulary.VocabularyApplication
import com.sumaqada.vocabulary.data.WordEntity
import com.sumaqada.vocabulary.navigation.Entry
import com.sumaqada.vocabulary.repository.WordRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed interface EntryUiState {

    data object Loading : EntryUiState
    data class Success(
        val word: WordEntity,
        val isValid: Boolean = false,
        val isSaving: Boolean = false,
        val errorMsg: String = ""
    ) : EntryUiState

    data object Error : EntryUiState
}

private const val TAG = "EntryViewModel"

class EntryViewModel(
    private val wordRepository: WordRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val wordId: Int = checkNotNull(savedStateHandle[Entry.wordIdArgName])

    private val _entryUiState: MutableStateFlow<EntryUiState> =
        MutableStateFlow(EntryUiState.Loading)

    val entryUiState: StateFlow<EntryUiState>
        get() = _entryUiState.asStateFlow()

    init {
        viewModelScope.launch {
            if (wordId != 0) {
                getWord()
            } else {
                _entryUiState.emit(EntryUiState.Success(WordEntity()))
            }
        }

    }

    private suspend fun getWord() {
        wordId.let {
            val word = wordRepository.getWordById(wordId)
                .filterNotNull()
                .first()
            onWordValueChange(word)
        }

    }


    fun onWordValueChange(wordEntity: WordEntity) {
        viewModelScope.launch {
            _entryUiState.emit(
                EntryUiState.Success(
                    word = wordEntity,
                    isValid = isValidWord(wordEntity)
                )
            )

        }

    }

    fun entryWord(word: WordEntity, onSuccess: () -> Unit) {
        if (_entryUiState.value is EntryUiState.Success) {
            val uiState = _entryUiState.value as EntryUiState.Success
            viewModelScope.launch {
                try {
                    _entryUiState.emit(uiState.copy(isSaving = true))
                    val end = if (word.id != 0) {
                        async { wordRepository.updateWord(word) }
                    } else {
                        async { wordRepository.insertWord(word) }
                    }
                    delay(1_000L)
                    end.await()
                    onSuccess()
                } catch (e: Exception) {
                    e.printStackTrace()
                    _entryUiState.emit(uiState.copy(isSaving = false, errorMsg = "Error"))
                }
            }
        }


    }

    private fun isValidWord(word: WordEntity): Boolean {
        return word.word.isNotBlank() && word.translated.isNotBlank()
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as VocabularyApplication)
                val wordRepository = application.container.wordRepository
                EntryViewModel(wordRepository, createSavedStateHandle())
            }
        }
    }
}