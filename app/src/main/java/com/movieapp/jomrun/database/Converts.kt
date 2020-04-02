package com.movieapp.jomrun.database

import androidx.room.TypeConverter
import java.util.*

class Converts {
    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? {
        return value?.let{
            Date(it)
        }
    }

    @TypeConverter
    fun toTimeStamp(date: Date?): Long? {
        return date?.time
    }
}