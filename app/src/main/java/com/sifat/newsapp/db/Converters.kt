package com.sifat.newsapp.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.sifat.newsapp.models.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }

    @TypeConverter
    fun toSource(name:String):Source{
        return Source(name, name)
    }
}