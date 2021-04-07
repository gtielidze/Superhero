package com.example.superhero.data.storage.db.typeConvertors

import androidx.room.TypeConverter
import com.example.superhero.data.models.superhero.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory



private object Serializer {
    val instance: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


    val appearanceAdapter: JsonAdapter <Appearance> = instance.adapter<Appearance>(
        Types.newParameterizedType(
            List::class.java,
            Appearance::class.java
        )
    )

    val biographyAdapter: JsonAdapter<Biography> = instance.adapter<Biography>(
        Types.newParameterizedType(
            List::class.java,
            Biography::class.java
        )
    )
    val connectionsAdapter: JsonAdapter<Connections> = instance.adapter<Connections>(
        Types.newParameterizedType(
            List::class.java,
            Connections::class.java
        )
    )
    val powerstatsAdapter: JsonAdapter<Powerstats> = instance.adapter<Powerstats>(
        Types.newParameterizedType(
            List::class.java,
            Powerstats::class.java
        )
    )
    val workAdapter: JsonAdapter<Work> = instance.adapter<Work>(
        Types.newParameterizedType(
            List::class.java,
            Work::class.java
        )
    )
}

class AppearanceTypeConverter {

    @TypeConverter
    fun toString(ability: Appearance?): String? {
        return ability?.let { Serializer.appearanceAdapter.toJson(it) }
    }

    @TypeConverter
    fun fromString(string: String?) = string?.let { Serializer.appearanceAdapter.fromJson(it) }

}

class BiographyTypeConverter {

    @TypeConverter
    fun toString(ability: Biography?): String? {
        return ability?.let { Serializer.biographyAdapter.toJson(it) }
    }

    @TypeConverter
    fun fromString(string: String?) = string?.let { Serializer.biographyAdapter.fromJson(it) }

}

class ConnectionsTypeConverter {

    @TypeConverter
    fun toString(ability: Connections?): String? {
        return ability?.let { Serializer.connectionsAdapter.toJson(it) }
    }

    @TypeConverter
    fun fromString(string: String?) = string?.let { Serializer.connectionsAdapter.fromJson(it) }

}

class PowerstatsTypeConverter {

    @TypeConverter
    fun toString(ability: Powerstats?): String? {
        return ability?.let { Serializer.powerstatsAdapter.toJson(it) }
    }

    @TypeConverter
    fun fromString(string: String?) = string?.let { Serializer.powerstatsAdapter.fromJson(it) }

}

class WorkTypeConverter {

    @TypeConverter
    fun toString(ability: Work?): String? {
        return ability?.let { Serializer.workAdapter.toJson(it) }
    }

    @TypeConverter
    fun fromString(string: String?) = string?.let { Serializer.workAdapter.fromJson(it) }

}



//val appearanceAdapter: JsonAdapter<List<Appearance>> = instance.adapter<List<Appearance>>(
//    Types.newParameterizedType(
//        List::class.java,
//        Appearance::class.java
//    )
//)
//val biographyAdapter: JsonAdapter<List<Biography>> = instance.adapter<List<Biography>>(
//    Types.newParameterizedType(
//        List::class.java,
//        Biography::class.java
//    )
//)
//val connectionsAdapter: JsonAdapter<List<Connections>> = instance.adapter<List<Connections>>(
//    Types.newParameterizedType(
//        List::class.java,
//        Connections::class.java
//    )
//)
//val powerstatsAdapter: JsonAdapter<List<Powerstats>> =instance.adapter<List<Powerstats>>(
//    Types.newParameterizedType(
//        List::class.java,
//        Powerstats::class.java
//    )
//)
//val workAdapter: JsonAdapter<List<Work>> = instance.adapter<List<Work>>(
//    Types.newParameterizedType(
//        List::class.java,
//        Work::class.java
//    )
//)
//}
//
//class AppearanceTypeConverter {
//
//    @TypeConverter
//    fun toString(appearance:List<Appearance>?): String? {
//        return appearance?.let { Serializer.appearanceAdapter.toJson(it) }
//    }
//
//    @TypeConverter
//    fun fromString(string: String?) = string?.let { Serializer.appearanceAdapter.fromJson(it) }
//
//}
//
//class BiographyTypeConverter {
//
//    @TypeConverter
//    fun toString(ability: List<Biography>?): String? {
//        return ability?.let { Serializer.biographyAdapter.toJson(it) }
//    }
//
//    @TypeConverter
//    fun fromString(string: String?) = string?.let { Serializer.biographyAdapter.fromJson(it) }
//
//}
//
//class ConnectionsTypeConverter {
//
//    @TypeConverter
//    fun toString(ability: List<Connections>?): String? {
//        return ability?.let { Serializer.connectionsAdapter.toJson(it) }
//    }
//
//    @TypeConverter
//    fun fromString(string: String?) = string?.let { Serializer.connectionsAdapter.fromJson(it) }
//
//}
//
//class PowerstatsTypeConverter {
//
//    @TypeConverter
//    fun toString(ability: List<Powerstats>?): String? {
//        return ability?.let { Serializer.powerstatsAdapter.toJson(it) }
//    }
//
//    @TypeConverter
//    fun fromString(string: String?) = string?.let { Serializer.powerstatsAdapter.fromJson(it) }
//
//}
//
//class WorkTypeConverter {
//
//    @TypeConverter
//    fun toString(ability: List<Work>?): String? {
//        return ability?.let { Serializer.workAdapter.toJson(it) }
//    }
//
//    @TypeConverter
//    fun fromString(string: String?) = string?.let { Serializer.workAdapter.fromJson(it) }
//
//}