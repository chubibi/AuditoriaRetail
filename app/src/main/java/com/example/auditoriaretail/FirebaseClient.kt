package com.example.auditoriaretail

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseClient @Inject constructor() {
    val db = FirebaseFirestore.getInstance()
    val dbAuth = FirebaseAuth.getInstance()
}