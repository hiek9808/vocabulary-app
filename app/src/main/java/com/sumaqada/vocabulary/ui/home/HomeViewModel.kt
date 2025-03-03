package com.sumaqada.vocabulary.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sumaqada.vocabulary.VocabularyApplication
import com.sumaqada.vocabulary.data.WordEntity
import com.sumaqada.vocabulary.repository.WordRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val words: List<WordEntity>) : HomeUiState
    data object Error : HomeUiState
}

class HomeViewModel(
    wordRepository: WordRepository
) : ViewModel() {

    val homeUiState = wordRepository.getAllWords()
        .map { words ->
            HomeUiState.Success(words)
        }
        .catch { e ->
            HomeUiState.Error
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HomeUiState.Loading
        )

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =(this[APPLICATION_KEY] as VocabularyApplication)
                val wordRepository = application.container.getWordRepository(application.applicationContext)
                HomeViewModel(wordRepository)
            }
        }
    }
}