package com.sumaqada.vocabulary.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumaqada.vocabulary.data.WordEntity
import com.sumaqada.vocabulary.ui.theme.VocabularyTheme

@Composable
fun HomeItem(
    modifier: Modifier = Modifier,
    word: WordEntity = WordEntity(),
    onClick: (Int) -> Unit = {}
) {

    var showTranslated by remember { mutableStateOf(false) }

    OutlinedCard(
        modifier = modifier.fillMaxWidth()
            .clickable { onClick(word.id) },
        border = BorderStroke(0.dp, Color.Transparent),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        shape = RectangleShape
    ) {

        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(word.word, modifier = Modifier.weight(1f).padding(8.dp))
                IconButton(onClick = { showTranslated = !showTranslated }) {
                    Icon(imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = null)
                }


            }
            AnimatedVisibility(showTranslated) {
                Text(word.translated, modifier = Modifier.padding(8.dp))
            }

        }

    }
}

@Preview
@Composable
private fun HomeItemPreview() {
    VocabularyTheme {
        HomeItem()
    }
}