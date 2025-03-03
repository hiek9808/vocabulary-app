package com.sumaqada.vocabulary.ui.remove

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RemoveRoute(
    viewModel: RemoveViewModel = viewModel(factory = RemoveViewModel.factory),
    goToUp: () -> Unit,
    goToHomeRoute: () -> Unit
) {

    val removeUiState by viewModel.removeUiState.collectAsStateWithLifecycle()

    RemoveScreen(
        removeUiState = removeUiState,
        onDismissRequest = goToUp,
        onConfirmButtonClicked = {
            viewModel.removeWord { goToHomeRoute() }
        }
    )
}