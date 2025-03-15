package com.sumaqada.vocabulary.ui.remove

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sumaqada.vocabulary.R
import com.sumaqada.vocabulary.data.WordEntity
import com.sumaqada.vocabulary.ui.theme.VocabularyTheme

@Composable
fun RemoveScreen(
    removeUiState: RemoveUiState,
    onConfirmButtonClicked: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {

    when (removeUiState) {
        is RemoveUiState.Loading -> {}
        is RemoveUiState.Success -> {
            val word = removeUiState.word
            AlertDialog(
                onDismissRequest = onDismissRequest,
                icon = {
                    Icon(imageVector = Icons.Outlined.Warning, contentDescription = null)
                },
                confirmButton = {
                    TextButton(onClick = onConfirmButtonClicked) {
                        Text(stringResource(R.string.delete_text_button), color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = onDismissRequest) {
                        Text(stringResource(R.string.cancel_text_button))
                    }
                },
                title = {
                    Text(stringResource(R.string.title_remove_word))
                },
                text = {
                    Text(stringResource(R.string.question_delete_word, word.word))
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
        RemoveScreen(
            removeUiState = RemoveUiState.Success(WordEntity(word = "Word")),
            onConfirmButtonClicked = {  },
            onDismissRequest = {  }
        )
    }
}