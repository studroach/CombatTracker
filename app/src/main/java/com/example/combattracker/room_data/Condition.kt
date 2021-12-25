package com.example.combattracker.room_data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Condition(
    @PrimaryKey(autoGenerate = true) var cid: Int,
    var name: String?,
    var description: String?,
    var shortHand: String?
)
