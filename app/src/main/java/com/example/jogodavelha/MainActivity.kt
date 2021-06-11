package com.example.jogodavelha

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var buttons: Array<Array<Button>>
    lateinit var textViewX: TextView
    lateinit var textViewO: TextView

    private var roundCount: Int = 0
    private var pointsX: Int = 0
    private var pointsO: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewX = findViewById(R.id.points_x)
        textViewO = findViewById(R.id.points_o)

        buttons = Array(3){ r->
            Array(3) { c->
                initButton(r, c)
            }
        }

        val btnReset: Button = findViewById(R.id.reset)
        btnReset.setOnClickListener{
            pointsX = 0
            pointsO = 0
            updateScore()
            clearBoard()
        }
    }

    private fun initButton(r: Int, c: Int): Button {
        val btn: Button = findViewById(resources.getIdentifier("btn$r$c", "id", packageName))
        btn.setOnClickListener{
            onBtnClick(btn)
        }
        return btn
    }

    private fun onBtnClick(btn: Button) {
        if(btn.text != "") return
        if(roundCount % 2 == 0) btn.text = "X"
        else btn.text = "O"
        roundCount++

        if(winCheck()) {
            if(roundCount % 2 != 0) win(1) else win(2)
        } else if(roundCount == 9) {
            draw()
        }
    }

    private fun winCheck(): Boolean {
        val fields = Array(3){r->
            Array(3){c->
                buttons[r][c].text
            }
        }

        for(i in 0..2)
            if((fields[i][0] == fields[i][1]) && (fields[i][0] == fields[i][2]) && (fields[i][0] != ""))
                return true

        for(i in 0..2)
            if((fields[0][i] == fields[1][i]) && (fields[0][i] == fields[2][i]) && (fields[0][i] != ""))
                return true
        if((fields[0][0] == fields[1][1]) && (fields[0][0] == fields[2][2]) && (fields[0][0] != ""))
            return true

        if((fields[0][2] == fields[1][1]) && (fields[0][2] == fields[2][0]) && (fields[0][2] != ""))
            return true

        return false
    }

    private fun win(player: Int) {
        if(player == 1) {
            pointsX++
            Toast.makeText(this, "X ganhou!", Toast.LENGTH_SHORT).show()
            updateScore()
            clearBoard()
        }
        else {
            pointsO++
            Toast.makeText(this, "O ganhou!", Toast.LENGTH_SHORT).show()
            updateScore()
            clearBoard()
        }

    }

    private fun draw() {
        Toast.makeText(this, "Empate :/", Toast.LENGTH_SHORT).show()
        clearBoard()
    }

    private fun clearBoard() {
        for(i in 0..2) {
            for(j in 0..2) {
                buttons[i][j].text = ""
            }
        }
        roundCount = 0
    }

    private fun updateScore() {
        textViewX.text = "X: $pointsX"
        textViewO.text = "O: $pointsO"
    }
}