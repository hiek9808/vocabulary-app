package com.sumaqada.vocabulary

import androidx.room.Entity
import com.sumaqada.vocabulary.data.WordEntity
import com.sumaqada.vocabulary.repository.toWordModel
import com.sumaqada.vocabulary.service.WordModel
import java.util.Date

object TestUtil {

    val wordEntity: WordEntity = WordEntity(
        id = 0,
        uid = "",
        word = "Word",
        translated = "palabra",
        description = "This is a description",
        createAt = Date(),
        isAvailable = true,
        synchronized = true,
        userId = ""
    )

    val words: List<WordEntity> = listOf(WordEntity(), WordEntity(), WordEntity())

    fun compareWordEntity(word1: WordEntity, word2: WordEntity): Boolean {
        if (word1.word != word2.word) return false
        if (word1.translated != word2.translated) return false
        if (word1.description != word2.description) return false
        return true
    }

    val WordModel: WordModel = wordEntity.toWordModel()
}