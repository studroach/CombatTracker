package com.example.combattracker.room_data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Encounter(
    @PrimaryKey(autoGenerate = true) var eid: Int,
    var name: String?,
    var players: String?,
    var monsters: String?,
)