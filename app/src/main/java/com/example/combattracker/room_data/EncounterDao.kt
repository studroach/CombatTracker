package com.example.combattracker.room_data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EncounterDao {

    //Monster////////////////////////////////
    @Query("SELECT * FROM monster")
    fun getAllMonster(): List<Monster>

    @Query("SELECT * FROM monster WHERE name LIKE :name")
    fun monsterByName(name: String): Monster

    @Insert
    fun insertMonster(vararg monster: Monster)

    @Delete
    fun deleteMonster(monster: Monster)

    //Player////////////////////////////////////
    @Query("SELECT * FROM player")
    fun getAllPlayer(): List<Player>

    @Query("SELECT * FROM player WHERE name LIKE :name")
    fun playerByName(name: String): Player

    @Insert
    fun insertPlayer(vararg player: Player)

    @Delete
    fun deletePlayer(player: Player)

    //Condition////////////////////////////////
    @Query("SELECT * FROM condition")
    fun getAllCondition(): List<Condition>

    @Query("SELECT * FROM condition WHERE name LIKE :name")
    fun conditionByName(name: String): Condition

    @Insert
    fun insertCondition(vararg condition: Condition)

    @Delete
    fun deleteCondition(condition: Condition)

    //Encounter///////////////////////////////
    @Query("SELECT * FROM encounter")
    fun getAllEncounter(): List<Encounter>

    @Query("SELECT * FROM encounter WHERE name LIKE :name")
    fun encounterByName(name: String): Encounter

    @Insert
    fun insertEncounter(vararg encounter: Encounter)

    @Delete
    fun deleteEncounter(encounter: Encounter)
}