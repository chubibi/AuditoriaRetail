package com.example.auditoriaretail.Login.domain.repository

import androidx.lifecycle.MutableLiveData
import com.example.auditoriaretail.FirebaseClient
import com.example.auditoriaretail.Login.data.model.LoginModel
import com.example.auditoriaretail.Login.data.model.UserModel
import com.example.auditoriaretail.SuccessResult
import com.example.auditoriaretail.Error
import com.example.auditoriaretail.Result
import com.example.auditoriaretail.util.ErrorApp.ERROR_DATABASE
import com.example.auditoriaretail.util.ErrorApp.ERROR_LOGIN
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class LoginRepository @Inject constructor(private val firebaseClient: FirebaseClient) {
    fun login(loginData:LoginModel): MutableLiveData<Result<UserModel>> {
        val mutableData = MutableLiveData<Result<UserModel>>()
        try {
            with(firebaseClient) {
                dbAuth.signInWithEmailAndPassword(loginData.email, loginData.password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            db.collection("users").document(it.result.user?.uid ?: "").get()
                                .addOnCompleteListener { result ->
                                    if (result.isSuccessful && result.result.exists()) {
                                        mutableData.value = SuccessResult(createUser(result))
                                    } else {
                                        mutableData.value = Error(ERROR_DATABASE)
                                        //mutableData.value = Error(ERROR_DATABASE)
                                    }
                                }
                        } else {
                            mutableData.value = Error(ERROR_LOGIN)
                        }
                    }
            }
        } catch (e: Exception) {
            mutableData.value = Error(e.message.toString())
        }
        return mutableData
    }

    private fun createUser(resul: Task<DocumentSnapshot>): UserModel {
        return UserModel(
            resul.result.data?.get(
                "email",
            ).toString(),
            resul.result.data?.get(
                "password",
            ).toString(),
        )
    }
}