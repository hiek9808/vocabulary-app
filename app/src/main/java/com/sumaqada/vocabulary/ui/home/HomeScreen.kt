package com.sumaqada.vocabulary.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumaqada.vocabulary.ui.theme.VocabularyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeUiState: HomeUiState = HomeUiState.Loading,
    onHomeItemClicked: (Int) -> Unit = {},
    onFloatingActionButtonClicked: () -> Unit = {}
) {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Vocabulary") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onFloatingActionButtonClicked) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->

        when (homeUiState) {
            is HomeUiState.Loading -> {}
            is HomeUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(homeUiState.words, key = {it.id}) { word ->
                        HomeItem(
                            word = word,
                            onClick = onHomeItemClicked
                        )
                    }
                }
            }
            is HomeUiState.Error -> {}
        }



    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    VocabularyTheme {
        HomeScreen()
    }
}