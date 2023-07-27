package com.majid.scorekeepingapp

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

/********
 *
 * I have used sharedPreference to maintain the state of app in night mode or light mode accordingly.
 * I have created and changed dark theme background by giving and using LinearLayout View by ID
 * Please go down at the end of code to see the changed code for LAB 7 Styles
 **********/

class MainActivity : AppCompatActivity() {
    //used lateinit to keep to property from being initialized
    private lateinit var team1Button1Pointer: Button
    private lateinit var team2Button1Pointer: Button
    private lateinit var team1Button2Pointer: Button
    private lateinit var team2Button2Pointer: Button
    private lateinit var team1Button3Pointer: Button
    private lateinit var team2Button3Pointer: Button
    private lateinit var reset: Button
    private lateinit var team1Score: TextView
    private lateinit var team2Score: TextView
    private lateinit var toggleButton: ToggleButton
    private lateinit var mode_switch: Switch
    private lateinit var linear :LinearLayout
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
        toggleButton = findViewById(R.id.toggleButton)
        team1Score = findViewById(R.id.team1Score)
        team2Score = findViewById(R.id.team2Score)
        team1Button1Pointer.isEnabled = false
        team1Button2Pointer.isEnabled = false
        team1Button3Pointer.isEnabled = false
        team2Button1Pointer.isEnabled = false
        team2Button2Pointer.isEnabled = false
        team2Button3Pointer.isEnabled = false
        reset.isEnabled= false

        mode_switch = findViewById(R.id.mode_switch)

        val sharedPref = getSharedPreferences("scorekeepingapp",Context.MODE_PRIVATE)
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
            team1Button1Pointer.isEnabled = false
            team1Button2Pointer.isEnabled = false
            team1Button3Pointer.isEnabled = false
            team2Button1Pointer.isEnabled = false
            team2Button2Pointer.isEnabled = false
            team2Button3Pointer.isEnabled = false
            reset.isEnabled= false
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
        linear= findViewById(R.id.linearLayout)
        mode_switch.setOnCheckedChangeListener{_, isChecked ->

            //Checking switch is on-off and accordingly setting night mode and light mode
            val newNightMode = if(isChecked){
                AppCompatDelegate.MODE_NIGHT_YES
            }
            else{
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(newNightMode)

            //using sharedPreference to store the state
            with(sharedPref.edit()){
                putInt("night_mode",newNightMode)
                apply()
            }
        }
        //Using Main LinearLayout View to set background to invert mode when nightmode is on
        if(mode_switch.isChecked)
            {
                linear.setBackgroundResource(R.drawable.bassinvert1)
            }

    }


}