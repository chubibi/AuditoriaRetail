package com.example.auditoriaretail.Register.domain.repository

import androidx.lifecycle.MutableLiveData
import com.example.auditoriaretail.FirebaseClient
import com.example.auditoriaretail.Login.data.model.UserModel
import com.example.auditoriaretail.State
import javax.inject.Inject

class DataUserRepository @Inject constructor(private val firebaseClient: FirebaseClient) {

    fun saveData(userData: UserModel): MutableLiveData<State> {
        val mutableData = MutableLiveData<State>()
        if (userData.singIn == 1) {
            firebaseClient.dbAuth.createUserWithEmailAndPassword(userData.email, userData.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val id = firebaseClient.dbAuth.currentUser!!.uid
                        firebaseClient.db.collection("users").document(id)
                            .set(createHashMap(userData)).addOnCompleteListener { resulDatabase ->
                                if (resulDatabase.isSuccessful) {
                                    mutableData.postValue(State.Successful)
                                } else {
                                    mutableData.value =
                                        (State.Error("Surgio un Error al guardar los datos "))
                                }
                            }
                    } else {
                        mutableData.value = (State.Error("Surgio un Error al guardar los datos "))
                    }
                }
        } else {
            val id = userData.uuid
            firebaseClient.db.collection("users").document(id).set(createHashMap(userData))
                .addOnCompleteListener { resulDatabase ->
                    if (resulDatabase.isSuccessful) {
                        mutableData.value = (State.Successful)
                    } else {
                        mutableData.value = (State.Error("Surgio un Error al guardar los datos "))
                    }
                }
        }
        return mutableData
    }

    private fun createHashMap(userData: UserModel): HashMap<String, Any> {
        val hashMapData: HashMap<String, Any> = HashMap()
        hashMapData["fullName"] = userData.fullName
        hashMapData["email"] = userData.email
        hashMapData["password"] = userData.password
        hashMapData["phone"] = userData.phone
        return hashMapData
    }
}
