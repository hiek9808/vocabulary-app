package com.sumaqada.vocabulary.ui.remove

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
import com.sumaqada.vocabulary.repository.WordRepository
import com.sumaqada.vocabulary.ui.home.HomeViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


sealed interface RemoveUiState {
    data object Loading : RemoveUiState
    data class Success(val word: WordEntity) : RemoveUiState
    data object Error : RemoveUiState
}

class RemoveViewModel(
    private val wordRepository: WordRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val wordId: Int = checkNotNull(savedStateHandle["wordId"])

    val removeUiState = wordRepository.getWordById(wordId)
        .filterNotNull()
        .map { RemoveUiState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = RemoveUiState.Loading
        )

    fun removeWord(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                wordRepository.deleteWord((removeUiState.value as RemoveUiState.Success).word)
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =(this[APPLICATION_KEY] as VocabularyApplication)
                val wordRepository = application.container.wordRepository
                RemoveViewModel(wordRepository, createSavedStateHandle())
            }
        }
    }
}