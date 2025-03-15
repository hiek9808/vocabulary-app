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
            HomeRoute(
                goToWordRoute = navController::navigateToWordRoute,
                goToEntryRoute = navController::navigateToEntryRoute
            )
        }

        composable(route = Entry.route, arguments = Entry.args) {
            EntryRoute(
                goToUp = navController::navigateUp
            )
        }

        composable(route = Word.route, arguments = Word.args) {
            WordRoute(
                goToEntryRoute = navController::navigateToEntryRoute,
                goToRemoveRoute = navController::navigateToRemoveRoute,
                goToUp = navController::navigateUp
            )
        }

        dialog(route = Remove.route, arguments = Remove.args) {
            RemoveRoute(
                goToUp = navController::navigateUp,
                goToHomeRoute = navController::navigateToHomeRoute
            )
        }
    }

}

fun NavHostController.navigateToWordRoute(wordId: Int) {
    this.navigate(route = Word.routeWithArgs(wordId)) {
        launchSingleTop = true

    }
}

fun NavHostController.navigateToEntryRoute(wordId: Int?) {
    this.navigate(route = Entry.routeWithArgs(wordId ?: 0)) {
        if (wordId == null) {
            popUpTo(Word.route) {
                inclusive = true
            }
        }
        launchSingleTop = true
    }
}

fun NavHostController.navigateToEntryRoute() {
    this.navigateToEntryRoute(null)
}

fun NavHostController.navigateToRemoveRoute(wordId: Int) {
    this.navigate(route = Remove.routeWithArgs(wordId)) {
        launchSingleTop = true
    }
}

fun NavHostController.navigateToHomeRoute() {
    this.navigate(route = Home.route) {
        popUpTo(route = Home.route) {
            inclusive = true
        }
        launchSingleTop = true
    }
}