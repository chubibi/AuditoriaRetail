package com.example.auditoriaretail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.auditoriaretail.Home.Presentation.ActHome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//Codigo para quitar la barra, duracion de la pantalla y el intento del cambio de pantalla
        supportActionBar?.hide()
        CoroutineScope(Dispatchers.Main).launch {
            delay(6000L)
            startActivity(Intent(this@MainActivity, ActHome::class.java))
            finish()
        }
    }
}

/* ESTOS SON MIS CAMBIOS*/

