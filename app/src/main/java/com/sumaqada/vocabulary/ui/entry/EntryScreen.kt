package com.sumaqada.vocabulary.ui.entry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumaqada.vocabulary.data.WordEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreen(
    entryUiState: EntryUiState,
    onWordValueChange: (WordEntity) -> Unit = {},
    onCheckButtonClicked: (WordEntity) -> Unit = {},
    onClearButtonClicked: () -> Unit = {}
) {


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondaryContainer,
                        MaterialTheme.colorScheme.tertiaryContainer,
                    )
                )
            ),
        containerColor = Color.Transparent,
        topBar = {
            val title: String = if (entryUiState is EntryUiState.Success) {
                if (entryUiState.word.id == 0) "New Word" else "Update Word"
            } else ""
            TopAppBar(
                title = { Text(title) }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        bottomBar = {
            if (entryUiState is EntryUiState.Success) {
                BottomAppBar(
                    actions = {
                        IconButton(onClick = onClearButtonClicked) {
                            Icon(imageVector = Icons.Outlined.Clear, contentDescription = null)
                        }
                    },
                    floatingActionButton = {
                        IconButton(
                            onClick = {
                                if (!entryUiState.isSaving) {
                                    onCheckButtonClicked(entryUiState.word)
                                }
                            },
                            enabled = entryUiState.isValid
                        ) {
                            if (entryUiState.isSaving) {
                                CircularProgressIndicator()
                            } else {
                                Icon(imageVector = Icons.Outlined.Check, contentDescription = null)
                            }
                        }
                    }
                )
            }

        }

    ) { innerPadding ->

        when (entryUiState) {
            is EntryUiState.Loading -> {}
            is EntryUiState.Success -> {
                val word = entryUiState.word
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    OutlinedTextField(
                        value = word.word,
                        onValueChange = { onWordValueChange(word.copy(word = it)) },
                        singleLine = true,
                        maxLines = 1,
                        label = { Text("Word") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )
                    OutlinedTextField(
                        value = word.translated,
                        onValueChange = { onWordValueChange(word.copy(translated = it)) },
                        singleLine = true,
                        maxLines = 1,
                        label = { Text("Translated") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )
                    OutlinedTextField(
                        value = word.description,
                        onValueChange = { onWordValueChange(word.copy(description = it)) },
                        singleLine = false,
                        maxLines = 5,
                        minLines = 3,
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )
                }
            }

            is EntryUiState.Error -> {}
        }


    }
}

@Preview
@Composable
fun EntryScreenPreview() {
    MaterialTheme {
        EntryScreen(
            entryUiState = EntryUiState.Success(WordEntity())
        )
    }
}