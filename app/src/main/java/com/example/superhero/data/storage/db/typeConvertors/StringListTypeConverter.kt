package com.example.superhero.data.storage.db.typeConvertors

import androidx.room.TypeConverter

/**ეუბნება room-ს როგორ ჩაწეროს column-ში ველში კონკრეტული კლასი ან ტიპი**/

class StringListTypeConverter {

    @TypeConverter
    fun toStringList(string: String?): List<String>? {
        return string?.split("|")?.toList()
    }

    @TypeConverter
    fun listToString(string: List<String>?): String? {
        return string?.joinToString("|")
    }
}





