package com.sumaqada.vocabulary.ui.word

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumaqada.vocabulary.data.WordEntity
import com.sumaqada.vocabulary.ui.theme.VocabularyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordScreen(
    wordUiState: WordUiState,
    onDeleteButtonClicked: (Int) -> Unit = {},
    onEditButtonClicked: (Int) -> Unit = {},
    onFloatingActionButtonClicked: () -> Unit = {},
    onNavigationButtonClicked: () -> Unit = {}
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
        topBar = {
            TopAppBar(
                modifier = Modifier,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavigationButtonClicked) {
                        Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        containerColor = Color.Transparent,
        bottomBar = {
            if (wordUiState is WordUiState.Success) {
                val word = wordUiState.word
                BottomAppBar(
                    modifier = Modifier,
                    actions = {
                        IconButton(onClick = { onDeleteButtonClicked(word.id) }) {
                            Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                        }
                        IconButton(onClick = { onEditButtonClicked(word.id) }) {
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
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
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
                    }
                    Card(
                        modifier = Modifier.fillMaxWidth()
                            .heightIn(min= 100.dp),
                        border = BorderStroke(0.dp, Color.Transparent),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        )
                    ) {
                        Text(modifier = Modifier.padding(16.dp), text = word.description, style = MaterialTheme.typography.bodyLarge)

                    }

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
        WordScreen(
            wordUiState = WordUiState.Success(
                WordEntity(
                    0,
                    "",
                    "Word",
                    "Palabra",
                    "This is a description"
                )
            )
        )
    }
}