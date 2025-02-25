package com.sumaqada.vocabulary.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.sumaqada.vocabulary.ui.entry.EntryRoute
import com.sumaqada.vocabulary.ui.home.HomeRoute
import com.sumaqada.vocabulary.ui.remove.RemoveRoute
import com.sumaqada.vocabulary.ui.word.WordRoute

@Composable
fun VocabularyNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {


    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home.route
    ) {

        composable(route = Home.route) {
            HomeRoute()
        }

        composable(route = Entry.route) {
            EntryRoute()
        }

        composable(route = Word.route) {
            WordRoute()
        }

        dialog(route = Remove.route) {
            RemoveRoute()
        }
    }

}