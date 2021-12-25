package com.example.combattracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.combattracker.room_data.*
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var drawer:DrawerLayout
    private lateinit var encounterContainer:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener{this.onNavigationItemSelected(it)}

        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, EncounterFragment()).commit()
            supportFragmentManager.beginTransaction().replace(R.id.alteration_container, AlterEncounterFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_encounter)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_encounter -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, EncounterFragment()).commit()
                supportFragmentManager.beginTransaction().replace(R.id.alteration_container, AlterEncounterFragment()).commit()
            }
            R.id.nav_monster -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MonsterFragment()).commit()
                supportFragmentManager.beginTransaction().replace(R.id.alteration_container, AlterMonsterFragment()).commit()
            }
            R.id.nav_player -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PlayerFragment()).commit()
                supportFragmentManager.beginTransaction().replace(R.id.alteration_container, AlterPlayerFragment()).commit()
            }
            R.id.nav_condition -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ConditionFragment()).commit()
                supportFragmentManager.beginTransaction().replace(R.id.alteration_container, AlterConditionFragment()).commit()
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        } else if(drawer.isDrawerOpen(GravityCompat.END)){
            drawer.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }

    fun onOpenAlterationDrawer(view: View){
        drawer.openDrawer(GravityCompat.END)
    }

    //Players////////////////////////////////////////
    fun addPlayer(view: View){
        val playerName = findViewById<EditText>(R.id.playerName)
        val playerHP = findViewById<EditText>(R.id.playerHP)
        val playerAC = findViewById<EditText>(R.id.playerAC)
        val newbie = Player(0, playerName.text.toString(), playerHP.text.toString().toInt(), playerAC.text.toString().toInt())

        val dao = EncounterDatabase.getDatabase(applicationContext).encounterDao()

        GlobalScope.launch {
            dao.insertPlayer(newbie)
            updatePlayers()
        }
    }

    fun removePlayer(view: View){
        val playerName = findViewById<EditText>(R.id.rmPlayerName)
        val dao = EncounterDatabase.getDatabase(applicationContext).encounterDao()

        GlobalScope.launch {
            val forgotten = dao.playerByName(playerName.text.toString())
            if(forgotten != null) {
                dao.deletePlayer(forgotten)
            }else{
                runOnUiThread(Runnable() {
                    run() {
                        Toast.makeText(applicationContext, "Player not found", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            updatePlayers()
        }
    }

    fun refreshPlayers(view: View){
        updatePlayers()
    }

    private fun updatePlayers(){
        val dao = EncounterDatabase.getDatabase(applicationContext).encounterDao()

        GlobalScope.launch {
            var recs = ""
            dao.getAllPlayer().forEach{ recs += it.name.toString() + "  HP:" + it.HP.toString() + "  AC:" + it.AC.toString() + "\n"}
            val output = findViewById<TextView>(R.id.playerList)

            runOnUiThread(Runnable() {
                run() {
                    output.text = recs
                }
            })
        }
    }

    //Monsters///////////////////////////////
    fun addMonster(view: View){
        val monsterName = findViewById<EditText>(R.id.monsterName)
        val monsterHP = findViewById<EditText>(R.id.monsterHP)
        val monsterAC = findViewById<EditText>(R.id.monsterAC)
        val newbie = Monster(0, monsterName.text.toString(), monsterHP.text.toString().toInt(), monsterAC.text.toString().toInt())

        val dao = EncounterDatabase.getDatabase(applicationContext).encounterDao()

        GlobalScope.launch {
            dao.insertMonster(newbie)
            updateMonsters()
        }
    }

    fun removeMonster(view: View){
        val monsterName = findViewById<EditText>(R.id.rmMonsterName)
        val dao = EncounterDatabase.getDatabase(applicationContext).encounterDao()

        GlobalScope.launch {
            val forgotten = dao.monsterByName(monsterName.text.toString())
            if(forgotten != null) {
                dao.deleteMonster(forgotten)
            }else{
                runOnUiThread(Runnable() {
                    run() {
                        Toast.makeText(applicationContext, "Monster not found", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            updateMonsters()
        }
    }

    fun refreshMonsters(view: View){
        updateMonsters()
    }

    private fun updateMonsters(){
        val dao = EncounterDatabase.getDatabase(applicationContext).encounterDao()

        GlobalScope.launch {
            var recs = ""
            dao.getAllMonster().forEach{ recs += it.name.toString() + "  HP:" + it.HP.toString() + "  AC:" + it.AC.toString() + "\n"}
            val output = findViewById<TextView>(R.id.monsterList)

            runOnUiThread(Runnable() {
                run() {
                    output.text = recs
                }
            })
        }
    }

    //Conditions///////////////////////////////////

    fun addCondition(view: View){
        val conditionName = findViewById<EditText>(R.id.conditionName)
        val conditionDesc = findViewById<EditText>(R.id.conditionDesc)
        val conditionSH = findViewById<EditText>(R.id.conditionSH)
        val newCon = Condition(0, conditionName.text.toString(), conditionDesc.text.toString(), conditionSH.text.toString())

        val dao = EncounterDatabase.getDatabase(applicationContext).encounterDao()

        GlobalScope.launch {
            dao.insertCondition(newCon)
            updateCondition()
        }
    }

    fun removeCondition(view: View){
        val conditionName = findViewById<EditText>(R.id.rmConditionName)
        val dao = EncounterDatabase.getDatabase(applicationContext).encounterDao()

        GlobalScope.launch {
            val forgotten = dao.conditionByName(conditionName.text.toString())
            if(forgotten != null) {
                dao.deleteCondition(forgotten)
            }else{
                runOnUiThread(Runnable() {
                    run() {
                        Toast.makeText(applicationContext, "Condition not found", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            updateCondition()
        }
    }

    fun refreshCondition(view: View){
        updateCondition()
    }

    private fun updateCondition(){
        val dao = EncounterDatabase.getDatabase(applicationContext).encounterDao()

        GlobalScope.launch {
            var recs = ""
            dao.getAllCondition().forEach{ recs += it.name.toString() + ":   " + it.shortHand.toString() + "\n--" + it.description.toString() + "\n"}
            val output = findViewById<TextView>(R.id.conditionList)

            runOnUiThread(Runnable() {
                run() {
                    output.text = recs
                }
            })
        }
    }

    fun addEncounter(view: View){
        val encounterName = findViewById<EditText>(R.id.encounterName).text.toString()
        var encounterPlayersString = findViewById<EditText>(R.id.encounterPlayers).text.toString().replace(", ", "/").replace(",", "/")
        val encounterMonstersString = findViewById<EditText>(R.id.encounterMonsters).text.toString().replace(", ", "/").replace(",", "/")

        val dao = EncounterDatabase.getDatabase(applicationContext).encounterDao()
        var flag = 0

        GlobalScope.launch {
            if(findViewById<EditText>(R.id.encounterPlayers).text.toString() == "ALL"){
                encounterPlayersString = ""
                dao.getAllPlayer().forEach {
                    encounterPlayersString += it.name.toString() + "/"
                }
                encounterPlayersString = encounterPlayersString.substring(0, encounterPlayersString.length - 1)
            }

            //Check if players and monsters entered exist
            val players = encounterPlayersString.split("/")
            val monsters = encounterMonstersString.split("/")

            players.forEach {if(dao.playerByName(it) == null){flag++}}
            monsters.forEach {if(dao.monsterByName(it) == null){flag++}}

            if(flag == 0) {
                val newEncounter = Encounter(0, encounterName, encounterPlayersString, encounterMonstersString)
                dao.insertEncounter(newEncounter)
                updateEncounter()
            }else{
                runOnUiThread(Runnable() {
                    run() {
                        Toast.makeText(applicationContext, flag.toString() + " Entries were invalid", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

    }

    fun removeEncounter(view: View){
        val encounterName = findViewById<EditText>(R.id.rmEncounterName)
        val dao = EncounterDatabase.getDatabase(applicationContext).encounterDao()

        GlobalScope.launch {
            val forgotten = dao.encounterByName(encounterName.text.toString())
            if(forgotten != null) {
                dao.deleteEncounter(forgotten)
            }else{
                runOnUiThread(Runnable() {
                    run() {
                        Toast.makeText(applicationContext, "Encounter not found", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            updateEncounter()
        }
    }

    fun refreshEncounter(view: View){
        updateEncounter()
    }

    private fun updateEncounter(){
        val dao = EncounterDatabase.getDatabase(applicationContext).encounterDao()

        encounterContainer = findViewById(R.id.encounterContainer)

        runOnUiThread(Runnable() {
            run() {
                encounterContainer.removeAllViews()
            }
        })

        GlobalScope.launch {
            dao.getAllEncounter().forEach{
                val textbox = TextView(applicationContext)
                textbox.setTextSize(24f)
                var capture = it.name.toString() + ":\nPlayers: "

                if(it.players != null){
                    if(it.players!!.toString().last() == '/') {
                        capture += it.players!!.toString().substring(0, it.players!!.length - 1).replace("/", ", ")
                    }else{
                        capture += it.players!!.toString().replace("/", ", ")
                    }
                }

                capture += "\nMonsters: "

                if(it.monsters != null){
                    if(it.monsters!!.toString().last() == '/') {
                        capture += it.monsters!!.toString().substring(0, it.monsters!!.length - 1).replace("/", ", ")
                    }else{
                        capture += it.monsters!!.toString().replace("/", ", ")
                    }
                }

                textbox.setText(capture)
                runOnUiThread(Runnable() {
                    run() {
                        encounterContainer.addView(textbox)
                    }
                })
            }
        }
    }

}