package com.sumaqada.vocabulary

import androidx.room.Entity
import com.sumaqada.vocabulary.data.WordEntity

object TestUtil {

    val wordEntity: WordEntity = WordEntity(
        id = 0,
        word = "Word",
        translated = "Palabra",
        description = "This is a description"
    )

    val words: List<WordEntity> = listOf(WordEntity(), WordEntity(), WordEntity())

    fun compareWordEntity(word1: WordEntity, word2: WordEntity): Boolean {
        if (word1.word != word2.word) return false
        if (word1.translated != word2.translated) return false
        if (word1.description != word2.description) return false
        return true
    }
}