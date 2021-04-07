package com.example.superhero.data.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.superhero.data.models.superhero.SuperheroCard
import com.example.superhero.data.models.user.UserProfile
import com.example.superhero.data.storage.db.entities.SavedCardIdEntity
import com.example.superhero.data.storage.db.typeConvertors.*



@Database(
    entities = [SuperheroCard::class, SavedCardIdEntity::class, UserProfile::class],
    version = 2, exportSchema = false
)
@TypeConverters(
    StringListTypeConverter::class,
    AppearanceTypeConverter::class,
    BiographyTypeConverter::class,
    ConnectionsTypeConverter::class,
    PowerstatsTypeConverter::class,
    WorkTypeConverter::class
)


abstract class SuperheroDB : RoomDatabase() {
    abstract fun getCardDAO(): CardDao
    abstract fun getSavedCardsDao(): SavedCardIdDao
    abstract fun getUserProfileDAO(): UserProfileDao
}