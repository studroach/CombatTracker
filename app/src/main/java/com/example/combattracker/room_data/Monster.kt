package com.example.combattracker.room_data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Monster(
    @PrimaryKey(autoGenerate = true) var mid: Int,
    var name: String?,
    var HP: Int,
    var AC: Int
)
