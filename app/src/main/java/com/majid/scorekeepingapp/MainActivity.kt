package com.majid.scorekeepingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton

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
            }
        }
    }

}