package com.example.auditoriaretail.Dashboard.domain.repository

import androidx.lifecycle.MutableLiveData
import com.example.auditoriaretail.Dashboard.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class DashboardRepository @Inject constructor() {
    private var db = FirebaseFirestore.getInstance()

    //El repositorio es la capa encargada de determinar de donde obtendra los datos
    fun getUserData():MutableLiveData<MutableList<User>>{
        val mutableData = MutableLiveData<MutableList<User>>()

        db.collection("user")
            .get()
            .addOnSuccessListener { result ->
                val listUser = mutableListOf<User>()
                for (document in result) {
                    listUser.add(
                        User(
                            document.get("height")!!.toString().toInt(),
                            document.getString("name")!!,
                            "",
                            ""
                            //document.get("peso")!!.toString().toInt(),
                            //document.get("retos")!!.toString().toInt(),
                        ),
                    )
                }
                mutableData.value = listUser
            }
        return mutableData
    }
}