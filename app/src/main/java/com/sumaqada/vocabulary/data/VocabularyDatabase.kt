package com.sumaqada.vocabulary.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [WordEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class VocabularyDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao
}