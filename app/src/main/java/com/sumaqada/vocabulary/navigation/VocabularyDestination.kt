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
        get() = "word"
}

object Entry : VocabularyDestination {
    override val route: String
        get() = "entry"
}

object Remove : VocabularyDestination {
    override val route: String
        get() = "remove"
}