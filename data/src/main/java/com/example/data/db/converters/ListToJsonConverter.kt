package com.example.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListToJsonConverter {
    @TypeConverter
    fun fromListToJsonString (list: List<String>) : String
        = Gson().toJson(list)

    @TypeConverter
    fun fromJsonStringToList (jsonString: String) : List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}