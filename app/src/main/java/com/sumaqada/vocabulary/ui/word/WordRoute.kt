package com.sumaqada.vocabulary.ui.word

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WordRoute(
    goToRemoveRoute: (Int) -> Unit,
    goToEntryRoute: (Int?) -> Unit,
    viewModel: WordViewModel = viewModel(factory = WordViewModel.factory)
) {

    val wordUiState by viewModel.wordUiState.collectAsStateWithLifecycle()

    WordScreen(
        wordUiState = wordUiState,
        onDeleteButtonClicked = goToRemoveRoute,
        onEditButtonClicked = goToEntryRoute,
        onFloatingActionButtonClicked = { goToEntryRoute(null) }
    )
}