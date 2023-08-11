package com.majid.scorekeepingapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

/********
 *
 * I have used sharedPreference to maintain the state of app in night mode or light mode accordingly.
 * I have created and changed dark theme background by giving and using LinearLayout View by ID
 * Please go down at the end of code to see the changed code for LAB 7 Styles
 *
 * For LAB 8 Menu and LocalStorage
 * I have implemented textChangeListner to retain the current score if saveScore toggle button is true
 * Using sharedPref i have stored the values of toggle button team scores and also night mode and light mode.
 *
 ********/

class MainActivity : AppCompatActivity() {
    //used lateinit to keep to property from being initialized
    private lateinit var team1Button1Pointer: Button
    private lateinit var team2Button1Pointer: Button
    private lateinit var team1Button2Pointer: Button
    private lateinit var team2Button2Pointer: Button
    private lateinit var team1Button3Pointer: Button
    private lateinit var team2Button3Pointer: Button
    private lateinit var reset: Button

    private lateinit var toolbar: Toolbar
    private lateinit var team1Score: TextView
    private lateinit var team2Score: TextView
    private lateinit var toggleButton: ToggleButton
    private lateinit var mode_switch: Switch
    private lateinit var linear: LinearLayout
    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //finding views by ID and storing them in Button object
        team1Button1Pointer = findViewById(R.id.teamone1pointer)
        team2Button1Pointer = findViewById(R.id.teamtwo1pointer)
        team1Button2Pointer = findViewById(R.id.teamone2pointer)
        team2Button2Pointer = findViewById(R.id.teamtwo2pointer)
        team1Button3Pointer = findViewById(R.id.teamone3pointer)
        team2Button3Pointer = findViewById(R.id.teamtwo3pointer)
        reset = findViewById(R.id.resetButton)

        toolbar = findViewById(R.id.myToolBar)
        toggleButton = findViewById(R.id.toggleButton)
        team1Score = findViewById(R.id.team1Score)
        team2Score = findViewById(R.id.team2Score)

        mode_switch = findViewById(R.id.mode_switch)

        sharedPref = getSharedPreferences("scorekeepingapp", Context.MODE_PRIVATE)
        val nightmode = sharedPref.getInt("night_mode", MODE_PRIVATE)
        AppCompatDelegate.setDefaultNightMode(nightmode)
        mode_switch.isChecked = (nightmode == AppCompatDelegate.MODE_NIGHT_YES)

        val editor = sharedPref.edit()
        //define score count for each time
        var team1Scored = 0
        var team2Scored = 0

        //onclick events
        team1Button1Pointer.setOnClickListener {

            team1Scored += 1
            team1Score.text = team1Scored.toString()
            reset.isEnabled = true
        }
        team2Button1Pointer.setOnClickListener {
            team2Scored += 1
            team2Score.text = team2Scored.toString()
            reset.isEnabled = true
        }
        team1Button2Pointer.setOnClickListener {

            team1Scored += 2
            team1Score.text = team1Scored.toString()
            reset.isEnabled = true
        }
        team2Button2Pointer.setOnClickListener {
            team2Scored += 2
            team2Score.text = team2Scored.toString()
            reset.isEnabled = true
        }
        team1Button3Pointer.setOnClickListener {

            team1Scored += 3
            team1Score.text = team1Scored.toString()
            reset.isEnabled = true
        }
        team2Button3Pointer.setOnClickListener {
            team2Scored += 3
            team2Score.text = team2Scored.toString()
            reset.isEnabled = true
        }

        //will reset the game
        reset.setOnClickListener {
            team1Scored = 0
            team2Scored = 0
            team1Score.text = team1Scored.toString()
            team2Score.text = team2Scored.toString()

        }
        toggleButton.setOnClickListener {
            var checked = toggleButton.isChecked

            if (checked) {
                Toast.makeText(this, "Match has started", Toast.LENGTH_SHORT).show()
                team1Scored = 0
                team2Scored = 0
                team1Score.text = team1Scored.toString()
                team2Score.text = team2Scored.toString()
                team1Button1Pointer.isEnabled = true
                team1Button2Pointer.isEnabled = true
                team1Button3Pointer.isEnabled = true
                team2Button1Pointer.isEnabled = true
                team2Button2Pointer.isEnabled = true
                team2Button3Pointer.isEnabled = true
            } else {
                Toast.makeText(
                    this,
                    "Match has ended, The final score was ${team1Scored}-${team2Scored}",
                    Toast.LENGTH_SHORT
                ).show()
                team1Scored = 0
                team2Scored = 0
                team1Score.text = team1Scored.toString()
                team2Score.text = team2Scored.toString()
                reset.isEnabled = false
                team1Button1Pointer.isEnabled = false
                team1Button2Pointer.isEnabled = false
                team1Button3Pointer.isEnabled = false
                team2Button1Pointer.isEnabled = false
                team2Button2Pointer.isEnabled = false
                team2Button3Pointer.isEnabled = false
            }
        }


        //LinearLayout view
        linear = findViewById(R.id.linearLayout)
        mode_switch.setOnCheckedChangeListener { _, isChecked ->

            //Checking switch is on-off and accordingly setting night mode and light mode
            val newNightMode = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(newNightMode)

            //using sharedPreference to store the state
            with(sharedPref.edit()) {
                putInt("night_mode", newNightMode)
                apply()
            }
        }
        //Using Main LinearLayout View to set background to invert mode when nightmode is on
        if (mode_switch.isChecked) {
            linear.setBackgroundResource(R.drawable.bassinvert1)
        }
        // Set the custom toolbar as the support action bar.
        setSupportActionBar(toolbar)
        // Set the title for the action bar.
        getSupportActionBar()?.setTitle("Basketball Score Tracking")
        // Retrieve whether to retain scores from shared preferences.
        val retainScores = sharedPref.getBoolean("Curr_State", false)

        // Add a text change listener for team1Score.
        team1Score.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val newText = team1Score.text.toString()
                // Save the new text to shared preferences.
                editor.putString("saved_text_team1", newText)
                editor.apply()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


        team2Score.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val newText = team2Score.text.toString()
                editor.putString("saved_text_team2", newText)
                editor.apply()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        // Retrieve saved texts for team scores from shared preferences.
        val savedTextTeam1 = sharedPref.getString("saved_text_team1", "")
        val savedTextTeam2 = sharedPref.getString("saved_text_team2", "")
        // Set the text of team1Score and team2Score based on the saved texts.
        if (retainScores) {
            team1Score.text = savedTextTeam1
            team2Score.text = savedTextTeam2
        } else {
            // If not retaining scores, set both scores to 0.
            team1Score.text = "0"
            team2Score.text = "0"
        }
    }

    // This function will inflate the menu xml file
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    // Handle options menu item selections.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.setting -> {
                // Start the SettingActivity when the "Setting" option is selected.
                startActivity(Intent(this, SettingActivity::class.java))
            }

            R.id.about -> {
                // Start the AboutActivity when the "About" option is selected.
                startActivity(Intent(this, AboutActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}