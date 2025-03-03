package com.sumaqada.vocabulary.ui.word

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sumaqada.vocabulary.VocabularyApplication
import com.sumaqada.vocabulary.data.WordEntity
import com.sumaqada.vocabulary.navigation.Word
import com.sumaqada.vocabulary.repository.WordRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed interface WordUiState {
    data object Loading : WordUiState
    data class Success(val word: WordEntity) : WordUiState
    data object Error : WordUiState
}

class WordViewModel(
    private val wordRepository: WordRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val wordId: Int = checkNotNull(savedStateHandle[Word.wordId])

    val wordUiState = wordRepository.getWordById(wordId)
        .filterNotNull()
        .map { WordUiState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = WordUiState.Loading
        )



    companion object {
        val factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as VocabularyApplication)
                val wordRepository = application.container.getWordRepository(application.applicationContext)
                WordViewModel(wordRepository, createSavedStateHandle())
            }
        }
    }
}