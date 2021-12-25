package com.example.combattracker.room_data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Monster::class, Player::class, Condition::class, Encounter::class], version = 5)
abstract class EncounterDatabase : RoomDatabase() {
    abstract fun encounterDao(): EncounterDao

    companion object {
        @Volatile
        private var INSTANCE: EncounterDatabase? = null

        fun getDatabase(context: Context): EncounterDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EncounterDatabase::class.java,
                    "encounterdb"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}