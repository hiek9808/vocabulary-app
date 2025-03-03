package com.sumaqada.vocabulary.ui.entry

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sumaqada.vocabulary.VocabularyApplication
import com.sumaqada.vocabulary.data.WordEntity
import com.sumaqada.vocabulary.navigation.Entry
import com.sumaqada.vocabulary.repository.WordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed interface EntryUiState {

    data object Loading : EntryUiState
    data class Entry(val word: WordEntity) : EntryUiState
    data object Success : EntryUiState
    data object Error : EntryUiState
}

class EntryViewModel(
    private val wordRepository: WordRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val wordId: Int = checkNotNull(savedStateHandle[Entry.argName])

    private val _entryUiState: MutableStateFlow<EntryUiState> =
        MutableStateFlow(EntryUiState.Loading)

    val entryUiState: StateFlow<EntryUiState>
        get() = _entryUiState.asStateFlow()

    private val _entryUiState2: MutableState<EntryUiState> = mutableStateOf(EntryUiState.Loading)
    val entryUiState2: State<EntryUiState>
        get() = _entryUiState2


    init {
        viewModelScope.launch {
            if (wordId != 0) {
                getWord()
            } else {
                _entryUiState.emit(EntryUiState.Entry(WordEntity()))
                _entryUiState2.value = EntryUiState.Entry(WordEntity())
            }
        }

    }

    private suspend fun getWord() {
        wordId?.let {
            val word = wordRepository.getWordById(wordId)
                .filterNotNull()
                .first()
            onWordValueChange(word)
        }

    }



    fun onWordValueChange(wordEntity: WordEntity) {
        viewModelScope.launch {
            _entryUiState.emit(EntryUiState.Entry(word = wordEntity))

        }
        _entryUiState2.value = EntryUiState.Entry(word = wordEntity)

    }

    fun entryWord( onSuccess: () -> Unit) {
        if (entryUiState.value is EntryUiState.Entry) {
            val word = (entryUiState.value as EntryUiState.Entry).word
            viewModelScope.launch {
                try {
                    if (word.id != 0) {
                        wordRepository.updateWord(word)
                    } else {
                        wordRepository.insertWord(word)
                    }
                    onSuccess()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }


    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VocabularyApplication)
                val wordRepository =
                    application.container.getWordRepository(application.applicationContext)
                EntryViewModel(wordRepository, createSavedStateHandle())
            }
        }
    }
}