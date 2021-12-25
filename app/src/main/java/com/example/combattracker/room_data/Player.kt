package com.example.combattracker.room_data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Player(
    @PrimaryKey(autoGenerate = true) var pid: Int,
    var name: String?,
    var HP: Int,
    var AC: Int
)
