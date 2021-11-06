package com.example.ipizzaapp.db.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class BitmapToStringConverter {
    @TypeConverter
    fun bitmapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
    }

    @TypeConverter
    fun encodeStringToBitmap(encodedString: String): Bitmap {
        val decodeByteArray = Base64.decode(encodedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodeByteArray, 0, decodeByteArray.size)
    }
}