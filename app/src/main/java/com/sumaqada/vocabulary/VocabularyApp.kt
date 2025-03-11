package com.sumaqada.vocabulary

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sumaqada.vocabulary.navigation.VocabularyNavHost
import com.sumaqada.vocabulary.ui.theme.VocabularyTheme


@Composable
fun VocabularyApp(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    VocabularyTheme {
        Surface {
            VocabularyNavHost(
                modifier = Modifier,
                navController = navController
            )
        }


    }
}
