package com.sumaqada.vocabulary.navigation


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

    val routeArgs: (wordId: Int) -> String = { wordId ->  "word/$wordId"}
}

object Entry : VocabularyDestination {
    override val route: String
        get() = "entry/?$argName={$argName}"

    val argName: String = "wordId"

    val routeWithArgs: (Int) -> String = { wordId: Int -> "entry/?$argName=$wordId" }
    val routeWithoutArgs = "entry"
}

object Remove : VocabularyDestination {
    override val route: String
        get() = "remove/{$argName}"
    val argName = "wordId"
    val routeWithArgs: (Int) -> String = { wordId: Int -> "remove/$wordId"}
}