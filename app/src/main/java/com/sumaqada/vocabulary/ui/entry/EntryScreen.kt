package com.sumaqada.vocabulary.ui.entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumaqada.vocabulary.data.WordEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreen(
    entryUiState: EntryUiState = EntryUiState.Loading,
    onWordValueChange: (WordEntity) -> Unit = {},
    onCheckButtonClicked: () -> Unit = {},
    onClearButtonClicked: () -> Unit = {}
) {


    Scaffold(
        topBar = {
            val title: String = if (entryUiState is EntryUiState.Entry) {
                if (entryUiState.word.id == 0) "New Word" else "Update Word"
            } else ""
            TopAppBar(title = { Text(title) })
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = onClearButtonClicked) {
                        Icon(imageVector = Icons.Outlined.Clear, contentDescription = null)
                    }
                },
                floatingActionButton = {
                    IconButton(onClick = onCheckButtonClicked) {
                        Icon(imageVector = Icons.Outlined.Check, contentDescription = null)
                    }
                }
            )
        }

    ) { innerPadding ->

        when (entryUiState) {
            is EntryUiState.Loading -> {}
            is EntryUiState.Entry -> {
                val word = entryUiState.word
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    TextField(
                        value = word.word,
                        onValueChange = { onWordValueChange(word.copy(word = it))},
                        singleLine = true,
                        maxLines = 1,
                        label = { Text("Word") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = word.translated,
                        onValueChange = { onWordValueChange(word.copy(translated = it)) },
                        singleLine = true,
                        maxLines = 1,
                        label = { Text("Translated") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = word.description,
                        onValueChange = { onWordValueChange(word.copy(description = it)) },
                        singleLine = false,
                        maxLines = 5,
                        minLines = 3,
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            is EntryUiState.Success -> {}
            is EntryUiState.Error -> {}
        }




    }
}

@Preview
@Composable
fun EntryScreenPreview() {
    MaterialTheme {
        EntryScreen()
    }
}