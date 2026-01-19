package com.nkechinnaji.recipefinder.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val thumbnail: String,
    val category: String?,
    val area: String?,
    val addedAt: Long = System.currentTimeMillis()
)
