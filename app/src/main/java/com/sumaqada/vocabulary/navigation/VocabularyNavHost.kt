package com.sumaqada.vocabulary.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavArgs
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
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
            HomeRoute(
                goToWordRoute = { wordId ->
                    navController.navigate(route = Word.routeArgs(wordId))
                },
                goToEntryRoute = {
                    navController.navigate(route = Entry.routeWithArgs(0))
                }
            )
        }

        composable(
            route = Entry.route,
            arguments = listOf(
                navArgument(name = Entry.argName) { type = NavType.IntType; defaultValue = 0 }
            )
        ) {
            EntryRoute(
                goToUp = { navController.navigateUp() }
            )
        }

        composable(
            route = Word.route,
            arguments = listOf(
                navArgument(name = Word.wordId) { type = NavType.IntType; nullable = false }
            )
        ) {
            WordRoute(
                goToEntryRoute = { worId ->
                    if (worId == null) {
                        navController.navigate(Entry.routeWithArgs(0))
                    } else {
                        navController.navigate(Entry.routeWithArgs(worId))
                    }
                },
                goToRemoveRoute = { wordId ->
                    navController.navigate(Remove.routeWithArgs(wordId))
                }
            )
        }

        dialog(
            route = Remove.route,
            arguments = listOf(
                navArgument(name = Remove.argName) { type = NavType.IntType; nullable = false }
            )
        ) {
            RemoveRoute(
                goToUp = { navController.navigateUp() },
                goToHomeRoute = {navController.navigate(route = Home.route)}
            )
        }
    }

}