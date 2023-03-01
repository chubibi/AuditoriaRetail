package com.example.auditoriaretail.Login.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.auditoriaretail.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavLoginHost : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_login)
    }
}