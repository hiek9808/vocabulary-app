package com.sumaqada.vocabulary.ui.entry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun EntryRoute(
    viewModel: EntryViewModel = viewModel(factory = EntryViewModel.factory),
    goToUp: () -> Unit
) {

    val entryUiState by viewModel.entryUiState.collectAsStateWithLifecycle()

    EntryScreen(
        entryUiState = entryUiState,
        onWordValueChange = viewModel::onWordValueChange,
        onCheckButtonClicked = {
            viewModel.entryWord(it) {
                goToUp()
            }
        },
        onClearButtonClicked = goToUp
    )
}