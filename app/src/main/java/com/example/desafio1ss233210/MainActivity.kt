package com.example.desafio1ss233210

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEjercicio1 = findViewById<Button>(R.id.btnEjercicio1)
        val btnEjercicio2 = findViewById<Button>(R.id.btnEjercicio2)
        val btnEjercicio3 = findViewById<Button>(R.id.btnEjercicio3)

        btnEjercicio1.setOnClickListener {
            val intent = Intent(this, PromedioActivity::class.java)
            startActivity(intent)
        }

        btnEjercicio2.setOnClickListener {
            val intent = Intent(this, SalarioActivity::class.java)
            startActivity(intent)
        }

        btnEjercicio3.setOnClickListener {
            val intent = Intent(this, CalculadoraActivity::class.java)
            startActivity(intent)
        }
    }
}