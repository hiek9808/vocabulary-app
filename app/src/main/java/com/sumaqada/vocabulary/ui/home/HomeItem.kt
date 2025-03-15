package com.sumaqada.vocabulary.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumaqada.vocabulary.ui.theme.VocabularyTheme

@Composable
fun HomeItem(
    modifier: Modifier = Modifier,
    word: WordHomeUI,
    onClick: (Int) -> Unit = {}
) {

    var showTranslated by remember { mutableStateOf(false) }


    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick(word.id) },
        border = BorderStroke(0.dp, Color.Transparent),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        shape = RoundedCornerShape(2.dp, 16.dp, 16.dp, 16.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(start = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    word.word.capitalize(Locale.current),
                    modifier = Modifier
                        .weight(1f),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
                IconButton(onClick = { showTranslated = !showTranslated }) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = null
                    )
                }


            }
            AnimatedVisibility(showTranslated) {
                Text(
                    word.translated,
                    modifier = Modifier.padding(end = 16.dp),
                    style = MaterialTheme.typography.labelLarge
                )
            }

        }


    }


}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun HomeItemPreview() {
    VocabularyTheme {
        HomeItem(
            word = WordHomeUI(0, "Word", "Palabra")
        )
    }
}