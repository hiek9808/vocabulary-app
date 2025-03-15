package com.sumaqada.vocabulary.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument


interface VocabularyDestination {

    val route: String
}

object Home : VocabularyDestination {
    override val route: String
        get() = "home"
}

object Word : VocabularyDestination {
    override val route: String
        get() = "word/{$wordId}"
    const val wordId: String = "wordId"

    val routeWithArgs: (wordId: Int) -> String = { wordId ->  "word/$wordId"}

    val args = listOf(
        navArgument(name = wordId) { type = NavType.IntType; nullable = false }
    )
}

object Entry : VocabularyDestination {
    override val route: String
        get() = "entry/{$wordIdArgName}"

    val wordIdArgName: String = "wordId"

    val routeWithArgs: (Int) -> String = { wordId: Int -> "entry/$wordId" }

    val routeWithoutArgs = "entry/${0}"

    val args = listOf(
        navArgument(name = Entry.wordIdArgName) { type = NavType.IntType; defaultValue = 0 }
    )
}

object Remove : VocabularyDestination {
    override val route: String
        get() = "remove/{$argName}"
    val argName = "wordId"
    val routeWithArgs: (Int) -> String = { wordId: Int -> "remove/$wordId"}

    val args = listOf(
    navArgument(name = Remove.argName) { type = NavType.IntType; nullable = false }
    )
}