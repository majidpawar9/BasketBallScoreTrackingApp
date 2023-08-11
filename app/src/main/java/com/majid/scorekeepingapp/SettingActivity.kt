package com.majid.scorekeepingapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Switch
import androidx.appcompat.widget.Toolbar

class SettingActivity : AppCompatActivity() {

    //used lateinit to keep to property from being initialized
    lateinit var saveSwitch: Switch
    var state: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        // Find and set the custom toolbar as the support action bar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Access the SharedPreferences for the app
        val sharedPref = getSharedPreferences("scorekeepingapp", Context.MODE_PRIVATE)
        // Enable the back arrow in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Retrieve the saved state of the switch from SharedPreferences
        val currState = sharedPref.getBoolean("Curr_State",false)
        // Find the Switch view and set its checked state.
        saveSwitch = findViewById(R.id.retainScore)
        saveSwitch.isChecked = currState

        // Set a listener to the switch to update the SharedPreferences when its state changes.
        saveSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Handle back arrow click by finishing the activity and returning to the previous one.
            sharedPref.edit().putBoolean("Curr_State",isChecked).apply()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Handle back arrow click
                finish() // This will close the current activity and return to the previous one
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}