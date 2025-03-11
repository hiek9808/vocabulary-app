package com.sumaqada.vocabulary.data

import androidx.room.TypeConverter
import java.util.Date


class Converters {

    @TypeConverter
    fun convertFromLongToDate(time: Long?): Date? {
        return time?.let { Date(it) }
    }

    @TypeConverter
    fun convertFromDateToLong(date: Date?): Long? {
        return date?.time
    }
}