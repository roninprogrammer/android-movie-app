package com.movieapp.jomrun.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices = [Index(value = ["key"], unique = true)])
data class MovieSearchKey constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val key: String,
    val date: Date
) {
    constructor(key: String, date: Date) : this(null, key, date)
}

fun List<MovieSearchKey>.asDomainModel(): List<SearchKeyModel> {
    return map {
        SearchKeyModel(
            id = it.id,
            key = it.key,
            date = it.date
        )
    }
}
