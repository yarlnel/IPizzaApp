package com.example.data.models

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_table")
data class LocalImageEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "image_bitmap")
    val bitmap: Bitmap
)
