package com.sumaqada.vocabulary.ui.remove

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sumaqada.vocabulary.ui.theme.VocabularyTheme

@Composable
fun RemoveScreen(
    removeUiState: RemoveUiState = RemoveUiState.Loading,
    onConfirmButtonClicked: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {

    when (removeUiState) {
        is RemoveUiState.Loading -> {}
        is RemoveUiState.Success -> {
            val word = removeUiState.word
            AlertDialog(
                onDismissRequest = onDismissRequest,
                confirmButton = {
                    TextButton(onClick = onConfirmButtonClicked) {
                        Text("Delete", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = onDismissRequest) {
                        Text("Cancel")
                    }
                },
                title = {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(imageVector = Icons.Outlined.Warning, contentDescription = null)
                    }
                },
                text = {
                    Text("Delete \"${word.word}\" word?")
                }
            )
        }
        is RemoveUiState.Error -> {}
    }

}

@Preview
@Composable
fun RemoveScreenPreview() {
    VocabularyTheme {
        RemoveScreen()
    }
}