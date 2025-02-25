package com.sumaqada.vocabulary

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sumaqada.vocabulary.navigation.VocabularyNavHost
import com.sumaqada.vocabulary.ui.theme.VocabularyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VocabularyApp(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    VocabularyTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(title = { Text("VocabularyApp") })
            }
        ) { innerPadding ->
            VocabularyNavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController
            )
        }
    }
}