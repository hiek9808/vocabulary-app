package com.sumaqada.vocabulary

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sumaqada.vocabulary.navigation.VocabularyNavHost
import com.sumaqada.vocabulary.ui.theme.VocabularyTheme


@Composable
fun VocabularyApp(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    VocabularyTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
        ) { innerPadding ->
            VocabularyNavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController
            )
        }
    }
}