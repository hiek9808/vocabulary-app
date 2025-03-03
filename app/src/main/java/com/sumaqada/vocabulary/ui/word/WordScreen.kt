package com.sumaqada.vocabulary.ui.word

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumaqada.vocabulary.ui.theme.VocabularyTheme

@Composable
fun WordScreen(
    wordUiState: WordUiState = WordUiState.Loading,
    onDeleteButtonClicked: (Int) -> Unit = {},
    onEditButtonClicked: (Int) -> Unit = {},
    onFloatingActionButtonClicked: () -> Unit = {}
) {

    Scaffold(
        bottomBar = {
            if (wordUiState is WordUiState.Success) {
                val word = wordUiState.word
                BottomAppBar(
                    modifier = Modifier,
                    actions = {
                        IconButton(onClick = {onDeleteButtonClicked(word.id)}) {
                            Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                        }
                        IconButton(onClick = {onEditButtonClicked(word.id)}) {
                            Icon(imageVector = Icons.Outlined.Edit, contentDescription = null)
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = onFloatingActionButtonClicked) {
                            Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                        }
                    }
                )
            }

        }
    ) { innerPadding ->

        when (wordUiState) {
            is WordUiState.Loading -> {}
            is WordUiState.Success -> {
                val word = wordUiState.word
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        text = word.word,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = word.translated,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(text = word.description, style = MaterialTheme.typography.bodyLarge)

                }
            }

            is WordUiState.Error -> {}
        }

    }
}

@Preview
@Composable
fun WordScreenPreview() {

    VocabularyTheme {
        WordScreen()
    }
}