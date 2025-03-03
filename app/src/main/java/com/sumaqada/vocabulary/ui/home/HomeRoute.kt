package com.sumaqada.vocabulary.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeRoute(
    goToWordRoute: (Int) -> Unit,
    goToEntryRoute: () -> Unit,
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.factory)
) {


    val homeUiState by homeViewModel.homeUiState.collectAsStateWithLifecycle()


    HomeScreen(
        homeUiState = homeUiState,
        onHomeItemClicked = goToWordRoute,
        onFloatingActionButtonClicked = goToEntryRoute
    )

}